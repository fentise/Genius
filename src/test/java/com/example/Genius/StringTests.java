package com.example.Genius;

import com.example.Genius.DAO.ArticleDAO;
import com.example.Genius.DAO.LikeDAO;
import com.example.Genius.DAO.ReplyDAO;
import com.example.Genius.DAO.UserDAO;
import com.example.Genius.model.UserLike;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StringTests {

	@Test
	public void contextLoads(){

		String userId = "";
		if(StringUtils.isEmpty(userId)) {
			System.out.println("空");
		}
		else{
			System.out.println("不空");
		}

	}
}
