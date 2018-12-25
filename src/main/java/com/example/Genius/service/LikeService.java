package com.example.Genius.service;

import com.example.Genius.DAO.LikeDAO;
import com.example.Genius.model.UserLike;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeService {

    @Autowired
    LikeDAO likeDAO;

    public int getLikeCount(int entityType,int entityId) {                 // 获得实体的点赞数目

        return likeDAO.getLikeCountByEntity(entityId,entityType,0);
    }

    public int getLikeStatus(int userId,int entityType,int entityId) {         // 获得喜欢状态0(喜欢)， 1(不喜欢)
        if(likeDAO.getStatus(userId,entityId,entityType).isEmpty()) {
            return 0;            // 表明用户未点击或喜欢
        }
        return likeDAO.getStatus(userId,entityId,entityType).indexOf(0) == 0 ? 1 : 0;
    }

    public int like(UserLike userLike) {
        if(likeDAO.getStatus(userLike.getUserId(),userLike.getEntityId(),userLike.getEntityType()).isEmpty()) {
            likeDAO.like(userLike);
        }
        else {
            likeDAO.updateStatus(userLike.getUserId(),userLike.getEntityId(),userLike.getEntityType(),0);
        }
        return likeDAO.getLikeCountByEntity(userLike.getEntityId(),userLike.getEntityType(),0);
    }

    public int dislike(int userId,int entityType,int entityId) {
        if(!likeDAO.getStatus(userId,entityId,entityType).isEmpty()) {
            likeDAO.updateStatus(userId,entityId,entityType,1);
        }
        return likeDAO.getLikeCountByEntity(entityId,entityType,0);
    }
}
