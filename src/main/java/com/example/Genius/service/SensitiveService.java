package com.example.Genius.service;


import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

@Service
public class SensitiveService implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(SensitiveService.class);

    private static final String DEFAULT_REPLACEMENT = "***";

    private class TrieNode {

        // 判断是否是关键词的终结
        private boolean end = false;

        // 用Map构造节点信息
        private Map<Character, TrieNode> subNodes = new HashMap<>();

        /**
         * 向指定位置添加节点树
         */
        void addSubNode(Character key, TrieNode value) {
            subNodes.put(key,value);
        }

        /**
         * 向当前节点获取其下个节点
         */
        TrieNode getSubNode(Character key) { return subNodes.get(key); }
        /**
         * 判断当前节点是否是结尾
         */
        boolean isKeywordEnd() { return end; }

        void setKeywordEnd(boolean end) { this.end = end;}

        public int getSubNodeCount() { return subNodes.size();}
    }

    /**
     * 根节点
     */
    private TrieNode rootNode = new TrieNode();

    boolean isSympol(char c) {
        int ic = (int) c;
        // 0x2E80-0x9FFF 东亚文字范围
        /**
         * 非法字符，不是Ascall码中的字符且不是东亚文字
         */
        boolean isAscii = (((c >= 'A') && (c <= 'Z')) || ((c >= 'a') && (c <= 'z')) ||
                ((c >= '0') && (c <= '9')));

        return !isAscii && (ic < 0x2E80 || ic > 0x9FFF);
    }

    /**
     * 增加敏感词
     */
    private void addWord(String lineTxt) {
        TrieNode tempNode = rootNode;
        for(int i = 0;i < lineTxt.length();++ i) {
            Character c = lineTxt.charAt(i);
            // 过滤空格
            if(isSympol(c)) {
                continue;
            }

            TrieNode node = tempNode.getSubNode(c);

            if(node == null) {     // 添加节点
                node = new TrieNode();
                tempNode.addSubNode(c,node);
            }

            tempNode = node;

            if(i == lineTxt.length() - 1) {  // 将最后一个词设为结束词
                tempNode.setKeywordEnd(true);
            }
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            InputStream is = Thread.currentThread().getContextClassLoader()
                    .getResourceAsStream("SensitiveWords.txt");
            InputStreamReader read = new InputStreamReader(is);
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineTxt;
            while ((lineTxt = bufferedReader.readLine()) != null) {
                lineTxt = lineTxt.trim();
                addWord(lineTxt);
            }
            read.close();
        } catch (Exception e) {
            logger.error("读取敏感词文件失败" + e.getMessage());
        }
    }

    public String filter(String text) {
        if(StringUtils.isEmpty(text)) { return text; }

        String replacement = DEFAULT_REPLACEMENT;
        StringBuilder result = new StringBuilder();

        /**
         * 扫描过程中的三个指针
         */
        TrieNode tempNode = rootNode;
        int begin = 0;   //回滚数
        int position = 0; //当前比较的位置

        while(position < text.length()) {
            char c = text.charAt(position);

            if(isSympol(c)) {
                if(tempNode == rootNode) {
                    result.append(c);
                    begin ++;
                }
                position ++;
                continue;
            }

            tempNode = tempNode.getSubNode(c);

            if(tempNode == null) {
                // 以begin开始的字符串并不存在敏感词
                result.append(text.charAt(begin));

                position = begin + 1;
                begin = position;
                tempNode = rootNode;
            } else if(tempNode.isKeywordEnd()) {

                result.append(replacement);
                position = position + 1;
                begin = position;
                tempNode = rootNode;
            }else {
                ++ position;
            }
        }

        result.append(text.substring(begin));

        return result.toString();
    }

    public static void main(String[] argv) {
        SensitiveService s = new SensitiveService();
        s.addWord("色情");
        s.addWord("暴力");
        System.out.print(s.filter("你好X色666情XX"));
    }
}
