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

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GeniusApplicationTests {

	@Autowired
	UserDAO userDAO;

	@Autowired
	ArticleDAO articleDAO;

	@Autowired
	LikeDAO likeDAO;

	@Autowired
	ReplyDAO replyDAO;

	@Test
	public void contextLoads(){


		UserLike userLike = new UserLike();
		userLike.setCreateTime(new Date());
		userLike.setEntityId(1);
		userLike.setEntityType(1);
		userLike.setStatus(0);
		userLike.setUserId(1);

	//	likeDAO.like(userLike);

		if(likeDAO.getStatus(2,1,1).isEmpty())
			System.out.print("无状态");
		else
			System.out.print("有状态");

		System.out.println(likeDAO.getStatus(2,1,1));

	}
}
