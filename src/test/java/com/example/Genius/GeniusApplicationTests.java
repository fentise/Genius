//package com.example.Genius;
//
//import com.example.Genius.DAO.ArticleDAO;
//import com.example.Genius.DAO.UserDAO;
//import com.example.Genius.model.Article;
//import com.example.Genius.model.User;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.Date;
//import java.util.List;
//import java.util.Random;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class GeniusApplicationTests {
//
//	@Autowired
//    UserDAO userDAO;
//
//	@Autowired
//	ArticleDAO articleDAO;
//
//	@Test
//	public void contextLoads() {
//
//		Random random = new Random();
//
//		for(int i = 0;i < 20;++ i)
//		{
//			User user = new User();
//			user.setUserNickname(String.format("USER%d",i));
//			user.setUserEmail(String.format("User%d@qq.com",i));
//			user.setUserHomePageURL("");
//			user.setUserProfilePhoto(String.format("http://images.nowcoder.com/head/%dt.png", random.nextInt(1000)));
//			user.setUserPassword("");
//			user.setUserRole(1);
//			user.setUserStatus(-1);
//			user.setUserSalt("");
//
//			userDAO.add(user);
//
//			Article article = new Article();
//			article.setArticleAuthorId(i);
//			article.setArticleContent("balabalabala");
//			article.setArticleLikeCount(0);                     //点赞数
//			article.setArticleReplyCount(0);					//回复数
//			article.setArticleStatus(-1);
//			article.setArticleTitle(String.format("TITLE {%d}",i));
//			article.setArticleTopicId(-1);
//			article.setArticleURL("");
//			article.setArticleViewCount(-1);
//
//			Date date = new Date();
//			date.setTime(date.getTime() + 100 * 24 * 3600);
//			article.setCreateTime(date);
//			date.setTime(date.getTime() + 1000 * 24 * 3600);
//			article.setLatestUpdateTime(date);
//
//			articleDAO.addArticle(article);
//		}
//
//		List<Article> articles = articleDAO.selectLatestArticles(0,0,10);
//
//		System.out.print(articles);
//
//	}
//
//}
