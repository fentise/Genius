-- MySQL Script generated by MySQL Workbench
-- Tue Dec 18 14:46:46 2018
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema forum
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema forum
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `forum` DEFAULT CHARACTER SET utf8 ;
USE `forum` ;

-- -----------------------------------------------------
-- Table `forum`.`announce`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `forum`.`announce` (
  `oId` INT(11) NOT NULL AUTO_INCREMENT,
  `announceContent` MEDIUMTEXT NULL DEFAULT NULL COMMENT '公告内容',
  `createTime` DATETIME NOT NULL,
  `status` INT(11) NOT NULL,
  `targetType` INT(11) NOT NULL COMMENT '公告对象类型',
  PRIMARY KEY (`oId`))
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `forum`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `forum`.`user` (
  `oId` INT(11) NOT NULL AUTO_INCREMENT,
  `userNickname` VARCHAR(20) NOT NULL COMMENT '用户昵称',
  `userPassword` VARCHAR(32) NOT NULL COMMENT 'md5值加密的密码',
  `userSalt` VARCHAR(16) NOT NULL COMMENT 'MD5加盐的盐',
  `userEmail` VARCHAR(255) NOT NULL COMMENT '用户注册/登录邮箱',
  `userHomePageURL` VARCHAR(100) NOT NULL COMMENT '个人主页URL',
  `userRole` INT(11) NOT NULL COMMENT '用户（权限）角色',
  `userStatus` INT(11) NOT NULL,
  `userProfilePhoto` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`oId`),
  UNIQUE INDEX `userEmail_UNIQUE` (`userEmail` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 51
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `forum`.`topic`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `forum`.`topic` (
  `oId` INT(11) NOT NULL AUTO_INCREMENT,
  `topicName` VARCHAR(10) NOT NULL COMMENT '主题区名',
  `topicStatus` INT(11) NULL DEFAULT NULL,
  `topicDescription` TEXT NULL DEFAULT NULL COMMENT '主题区描述',
  PRIMARY KEY (`oId`))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `forum`.`article`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `forum`.`article` (
  `oId` INT(11) NOT NULL AUTO_INCREMENT COMMENT '代理主键',
  `articleTitle` VARCHAR(255) NOT NULL COMMENT '帖子标题',
  `articleAuthorId` INT(11) NOT NULL COMMENT '发帖者id',
  `articleContent` MEDIUMTEXT NULL DEFAULT NULL COMMENT '帖子正文内容',
  `articleURL` VARCHAR(255) NULL DEFAULT NULL COMMENT '帖子主页地址',
  `articleReplyCount` INT(11) NULL DEFAULT NULL COMMENT '回帖数',
  `articleLikeCount` INT(11) NULL DEFAULT NULL COMMENT '点赞数',
  `articleViewCount` INT(11) NULL DEFAULT NULL COMMENT '浏览数',
  `articleTopicId` INT(11) NOT NULL COMMENT '主题区id',
  `articleStatus` INT(11) NULL DEFAULT NULL COMMENT '状态码',
  `createTime` DATETIME NULL DEFAULT NULL COMMENT '创建时间',
  `latestUpdateTime` DATETIME NULL DEFAULT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`oId`),
  UNIQUE INDEX `oId_UNIQUE` (`oId` ASC),
  INDEX `articleAuthorId_idx` (`articleAuthorId` ASC),
  INDEX `articleTopicId_f_idx` (`articleTopicId` ASC),
  CONSTRAINT `articleAuthorId`
    FOREIGN KEY (`articleAuthorId`)
    REFERENCES `forum`.`user` (`oId`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `articleTopicId_f`
    FOREIGN KEY (`articleTopicId`)
    REFERENCES `forum`.`topic` (`oId`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 103
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `forum`.`tags`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `forum`.`tags` (
  `oId` INT(11) NOT NULL AUTO_INCREMENT,
  `tagName` VARCHAR(45) NOT NULL,
  `status` INT(11) NULL DEFAULT NULL,
  `tagDescription` VARCHAR(255) NULL DEFAULT NULL,
  `tagReferenceCount` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`oId`),
  UNIQUE INDEX `oId_UNIQUE` (`oId` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `forum`.`article_tags`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `forum`.`article_tags` (
  `oId` INT(11) NOT NULL AUTO_INCREMENT,
  `tagId` INT(11) NULL DEFAULT NULL,
  `articleTagStatus` INT(11) NULL DEFAULT NULL,
  `articleId` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`oId`),
  UNIQUE INDEX `oId_UNIQUE` (`oId` ASC),
  INDEX `articleId_idx` (`articleId` ASC),
  INDEX `tagId_idx` (`tagId` ASC),
  CONSTRAINT `articleId`
    FOREIGN KEY (`articleId`)
    REFERENCES `forum`.`article` (`oId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `tagId`
    FOREIGN KEY (`tagId`)
    REFERENCES `forum`.`tags` (`oId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `forum`.`comment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `forum`.`comment` (
  `oId` INT(11) NOT NULL AUTO_INCREMENT,
  `commentToId` INT(11) NULL DEFAULT NULL COMMENT '评论对象id',
  `commentType` INT(11) NULL DEFAULT NULL COMMENT '评论类型（给主帖/回帖）',
  `commentContent` TEXT NULL DEFAULT NULL,
  `commentAuthorId` INT(11) NULL DEFAULT NULL,
  `createTime` DATETIME NULL DEFAULT NULL,
  `status` INT(11) NULL DEFAULT NULL,
  `likeCount` INT(11) NULL DEFAULT NULL,
  `commentFloor` INT(11) NULL DEFAULT NULL COMMENT '评论在评论区的楼层',
  PRIMARY KEY (`oId`),
  UNIQUE INDEX `oId_UNIQUE` (`oId` ASC),
  INDEX `commentAuthorId_idx` (`commentAuthorId` ASC),
  CONSTRAINT `commentAuthorId`
    FOREIGN KEY (`commentAuthorId`)
    REFERENCES `forum`.`user` (`oId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `forum`.`followed`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `forum`.`followed` (
  `oId` INT(11) NOT NULL AUTO_INCREMENT,
  `userId1` INT(11) NULL DEFAULT NULL COMMENT '被关注者id',
  `userId2` INT(11) NULL DEFAULT NULL COMMENT '关注别人者id',
  PRIMARY KEY (`oId`),
  UNIQUE INDEX `oId_UNIQUE` (`oId` ASC),
  INDEX `userId1_idx` (`userId1` ASC),
  INDEX `userId2_idx` (`userId2` ASC),
  CONSTRAINT `userId1`
    FOREIGN KEY (`userId1`)
    REFERENCES `forum`.`user` (`oId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `userId2`
    FOREIGN KEY (`userId2`)
    REFERENCES `forum`.`user` (`oId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `forum`.`following`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `forum`.`following` (
  `oId` INT(11) NOT NULL AUTO_INCREMENT,
  `userId1` INT(11) NOT NULL COMMENT '关注别人者',
  `userId2` INT(11) NOT NULL COMMENT '被关注者id',
  PRIMARY KEY (`oId`),
  UNIQUE INDEX `oId_UNIQUE` (`oId` ASC),
  INDEX `userId1_idx` (`userId1` ASC),
  INDEX `userId2_idx` (`userId2` ASC),
  CONSTRAINT `user1`
    FOREIGN KEY (`userId1`)
    REFERENCES `forum`.`user` (`oId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `user2`
    FOREIGN KEY (`userId2`)
    REFERENCES `forum`.`user` (`oId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `forum`.`login_ticket`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `forum`.`login_ticket` (
  `oId` INT(11) NOT NULL AUTO_INCREMENT,
  `userId` INT(11) NOT NULL,
  `ticket` VARCHAR(45) NOT NULL COMMENT '分发到用户cookie的标识token',
  `expired` DATETIME NOT NULL COMMENT 'token过期时间',
  `status` INT(11) NOT NULL,
  PRIMARY KEY (`oId`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `forum`.`session`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `forum`.`session` (
  `oId` INT(11) NOT NULL AUTO_INCREMENT COMMENT '代理主键',
  `userId1` INT(11) NOT NULL,
  `userId2` INT(11) NOT NULL,
  PRIMARY KEY (`oId`),
  UNIQUE INDEX `oId_UNIQUE` (`oId` ASC),
  INDEX `userId1_idx` (`userId1` ASC),
  INDEX `userId2_idx` (`userId2` ASC),
  CONSTRAINT `userId1_session`
    FOREIGN KEY (`userId1`)
    REFERENCES `forum`.`user` (`oId`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `userId2_session`
    FOREIGN KEY (`userId2`)
    REFERENCES `forum`.`user` (`oId`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 210
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `forum`.`message`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `forum`.`message` (
  `oId` INT(11) NOT NULL AUTO_INCREMENT,
  `senderId` INT(11) NOT NULL COMMENT '私信发送者id',
  `messageContent` TEXT NULL DEFAULT NULL,
  `createTime` DATETIME NULL DEFAULT NULL,
  `status` INT(11) NULL DEFAULT NULL,
  `receiverId` INT(11) NOT NULL COMMENT '接收者id（保险起见保存的，实际上并不会用到这个字段）',
  `sessionId` INT(11) NOT NULL,
  PRIMARY KEY (`oId`),
  UNIQUE INDEX `oId_UNIQUE` (`oId` ASC),
  INDEX `senderId_idx` (`senderId` ASC),
  INDEX `recepterIdd_idx` (`receiverId` ASC),
  INDEX `sessionId_idx` (`sessionId` ASC),
  CONSTRAINT `recepterId`
    FOREIGN KEY (`receiverId`)
    REFERENCES `forum`.`user` (`oId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `senderId`
    FOREIGN KEY (`senderId`)
    REFERENCES `forum`.`user` (`oId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `sessionId_message`
    FOREIGN KEY (`sessionId`)
    REFERENCES `forum`.`session` (`oId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 6
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `forum`.`reminder`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `forum`.`reminder` (
  `oId` INT(11) NOT NULL AUTO_INCREMENT,
  `senderId` INT(11) NOT NULL COMMENT '提醒触发者/发送者id',
  `targetId` INT(11) NOT NULL COMMENT '触发动作对象id',
  `targetType` INT(11) NOT NULL COMMENT '触发动作对象类型（主帖/回帖/评论）',
  `action` INT(11) NOT NULL COMMENT '评论/点赞/新回帖',
  `createTime` DATETIME NOT NULL,
  `status` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`oId`),
  UNIQUE INDEX `oId_UNIQUE` (`oId` ASC),
  INDEX `senderId_idx` (`senderId` ASC),
  CONSTRAINT `senderIdf`
    FOREIGN KEY (`senderId`)
    REFERENCES `forum`.`user` (`oId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 96
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `forum`.`reply`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `forum`.`reply` (
  `oId` INT(11) NOT NULL AUTO_INCREMENT,
  `replyToId` INT(11) NOT NULL COMMENT '被回的主帖oId',
  `replyFloor` INT(11) NOT NULL COMMENT '该回帖处于的楼层',
  `replyAuthorId` INT(11) NOT NULL,
  `replyContent` MEDIUMTEXT NULL DEFAULT NULL,
  `likeCount` INT(11) NULL DEFAULT NULL,
  `commentCount` INT(11) NULL DEFAULT NULL,
  `createTime` DATETIME NOT NULL,
  `status` INT(11) NOT NULL,
  PRIMARY KEY (`oId`),
  UNIQUE INDEX `oId_UNIQUE` (`oId` ASC),
  INDEX `replyToId_f_idx` (`replyToId` ASC),
  INDEX `replyAuthorId_f_idx` (`replyAuthorId` ASC),
  CONSTRAINT `replyAuthorId_f`
    FOREIGN KEY (`replyAuthorId`)
    REFERENCES `forum`.`user` (`oId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `replyToId_f`
    FOREIGN KEY (`replyToId`)
    REFERENCES `forum`.`user` (`oId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `forum`.`user_favorites`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `forum`.`user_favorites` (
  `oId` INT(11) NOT NULL AUTO_INCREMENT,
  `userId` INT(11) NOT NULL,
  `articleId` INT(11) NOT NULL COMMENT '用户收藏的主帖id',
  PRIMARY KEY (`oId`),
  UNIQUE INDEX `oId_UNIQUE` (`oId` ASC),
  INDEX `userId_f_idx` (`userId` ASC),
  INDEX `articleId_f_idx` (`articleId` ASC),
  CONSTRAINT `articleId_f`
    FOREIGN KEY (`articleId`)
    REFERENCES `forum`.`user` (`oId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `userId_f`
    FOREIGN KEY (`userId`)
    REFERENCES `forum`.`user` (`oId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `forum`.`user_like`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `forum`.`user_like` (
  `oId` INT(11) NOT NULL AUTO_INCREMENT,
  `userId` INT(11) NOT NULL,
  `objId` INT(11) NOT NULL COMMENT '用户点赞对象id',
  `objType` INT(11) NOT NULL COMMENT '用户点赞对象类型',
  PRIMARY KEY (`oId`),
  UNIQUE INDEX `oId_UNIQUE` (`oId` ASC),
  INDEX `userId_f_idx` (`userId` ASC),
  INDEX `userId_f_onlike_idx` (`userId` ASC),
  CONSTRAINT `userId_f_onlike`
    FOREIGN KEY (`userId`)
    REFERENCES `forum`.`user` (`oId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `forum`.`user_notify`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `forum`.`user_notify` (
  `oId` INT(11) NOT NULL AUTO_INCREMENT,
  `userId` INT(11) NOT NULL COMMENT '消息接收者id',
  `createTime` DATETIME NOT NULL COMMENT '用于排序筛选的消息创建时间（与Notify系列表中的createTime字段一致）',
  `hasRead` INT(11) NOT NULL COMMENT '该消息是否已读',
  `notifyId` INT(11) NOT NULL COMMENT '消息内容id',
  `notifyType` INT(11) NOT NULL COMMENT '消息类型',
  PRIMARY KEY (`oId`),
  UNIQUE INDEX `oId_UNIQUE` (`oId` ASC),
  INDEX `userId_f_onnotify_idx` (`userId` ASC),
  CONSTRAINT `userId_f_onnotify`
    FOREIGN KEY (`userId`)
    REFERENCES `forum`.`user` (`oId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 29
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `forum`.`user_subscription`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `forum`.`user_subscription` (
  `oId` INT(11) NOT NULL AUTO_INCREMENT,
  `userId` INT(11) NOT NULL,
  `targetType` INT(11) NOT NULL COMMENT '订阅规则的动作对象类型',
  `action` INT(11) NOT NULL COMMENT '订阅规则的动作类型',
  `createTime` DATETIME NULL DEFAULT NULL,
  PRIMARY KEY (`oId`),
  UNIQUE INDEX `oId_UNIQUE` (`oId` ASC),
  INDEX `userId_f_onsubscription_idx` (`userId` ASC),
  CONSTRAINT `userId_f_onsubscription`
    FOREIGN KEY (`userId`)
    REFERENCES `forum`.`user` (`oId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 166
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
