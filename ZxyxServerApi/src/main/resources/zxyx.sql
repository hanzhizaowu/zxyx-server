/*
 Navicat MySQL Data Transfer

 Source Server         : myrds
 Source Server Type    : MySQL
 Source Server Version : 80016
 Source Host           : rm-uf660i5f52444245c2o.mysql.rds.aliyuncs.com:3306
 Source Schema         : zxyx

 Target Server Type    : MySQL
 Target Server Version : 80016
 File Encoding         : 65001

 Date: 14/06/2020 10:32:50
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_comment
-- ----------------------------
DROP TABLE IF EXISTS `t_comment`;
CREATE TABLE `t_comment`  (
  `id` bigint(32) NOT NULL COMMENT 'id',
  `userId` bigint(32) NOT NULL COMMENT '评论用户',
  `feedId` bigint(32) NOT NULL COMMENT '评论的帖子id',
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '评论内容',
  `createTime` bigint(13) NOT NULL COMMENT '评论的发布时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user`(`userId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_feed
-- ----------------------------
DROP TABLE IF EXISTS `t_feed`;
CREATE TABLE `t_feed`  (
  `id` bigint(32) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '帖子id',
  `userId` bigint(32) NOT NULL COMMENT '用户id',
  `feedTitle` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '帖子标题',
  `feedDescription` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '帖子描述',
  `createTime` bigint(13) NOT NULL COMMENT '创建时间',
  `updateTime` bigint(13) NULL DEFAULT NULL COMMENT '更新时间',
  `feedType` tinyint(2) NOT NULL COMMENT '帖子类型，0：图片；1：视频',
  `feedCover` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '帖子封面',
  `coverHeight` smallint(4) UNSIGNED NOT NULL,
  `coverWidth` smallint(4) UNSIGNED NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `feed_user_createtime`(`feedType`, `id`, `userId`, `createTime`) USING BTREE COMMENT '帖子分页查询'
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_feed_photo
-- ----------------------------
DROP TABLE IF EXISTS `t_feed_photo`;
CREATE TABLE `t_feed_photo`  (
  `id` bigint(32) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '资源id',
  `feedId` bigint(32) NOT NULL COMMENT '帖子id',
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '资源地址',
  `state` tinyint(2) NOT NULL COMMENT '存储资源状态，0：业务；1：oss',
  `createTime` bigint(13) NULL DEFAULT NULL COMMENT '创建时间',
  `updateTime` bigint(13) NOT NULL COMMENT '更新时间',
  `photoHeight` smallint(4) UNSIGNED NOT NULL COMMENT '图片高度',
  `photoWidth` smallint(4) UNSIGNED NOT NULL COMMENT '图片宽度',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_feed_video
-- ----------------------------
DROP TABLE IF EXISTS `t_feed_video`;
CREATE TABLE `t_feed_video`  (
  `id` bigint(32) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '视频资源id',
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '视频地址',
  `videoTime` int(16) UNSIGNED NOT NULL COMMENT '视频时长，单位为毫秒',
  `feedId` bigint(32) UNSIGNED NOT NULL COMMENT '对应的帖子id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_letter
-- ----------------------------
DROP TABLE IF EXISTS `t_letter`;
CREATE TABLE `t_letter`  (
  `id` bigint(32) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
  `postUserId` bigint(32) NOT NULL COMMENT '发送方用户id',
  `receiverUserId` bigint(32) NOT NULL COMMENT '接收方用户id',
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '内容',
  `createTime` bigint(13) NOT NULL COMMENT '发布时间',
  `state` tinyint(2) NOT NULL COMMENT '是否读过。0：未读；1：已读',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `post_receiver_time`(`createTime`, `postUserId`, `receiverUserId`) USING BTREE,
  INDEX `post_receiver_state`(`state`, `postUserId`, `receiverUserId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_letter_user
-- ----------------------------
DROP TABLE IF EXISTS `t_letter_user`;
CREATE TABLE `t_letter_user`  (
  `id` bigint(32) UNSIGNED NOT NULL AUTO_INCREMENT,
  `postUserId` bigint(32) UNSIGNED NOT NULL COMMENT '发送方id',
  `receiverUserId` bigint(32) UNSIGNED NOT NULL COMMENT '接收方id',
  `updateTime` bigint(13) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `receiver_time`(`id`, `receiverUserId`, `updateTime`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `id` bigint(32) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `userName` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户密码',
  `phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户手机号',
  `sex` tinyint(2) NULL DEFAULT 0 COMMENT '性别，默认0,1女2男',
  `avatar` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户头像',
  `signature` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户签名',
  `token` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户token',
  `login_time` bigint(13) NULL DEFAULT NULL COMMENT '登录时间',
  `create_time` bigint(13) NOT NULL COMMENT '创建时间',
  `update_time` bigint(13) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uname`(`userName`) USING BTREE,
  UNIQUE INDEX `uphone`(`phone`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
