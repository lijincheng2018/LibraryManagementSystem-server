/*
 Navicat Premium Data Transfer

 Source Server         : LJC MySQL
 Source Server Type    : MySQL
 Source Server Version : 50560
 Source Host           : localhost:3306
 Source Schema         : lms

 Target Server Type    : MySQL
 Target Server Version : 50560
 File Encoding         : 65001

 Date: 30/05/2023 16:06:17
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for book_class
-- ----------------------------
DROP TABLE IF EXISTS `book_class`;
CREATE TABLE `book_class`  (
  `id` int(8) NOT NULL AUTO_INCREMENT,
  `class` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of book_class
-- ----------------------------
INSERT INTO `book_class` VALUES (1, '科普');
INSERT INTO `book_class` VALUES (2, '现代文学');
INSERT INTO `book_class` VALUES (3, '专业文献');

-- ----------------------------
-- Table structure for book_info
-- ----------------------------
DROP TABLE IF EXISTS `book_info`;
CREATE TABLE `book_info`  (
  `id` int(8) NOT NULL AUTO_INCREMENT,
  `bookname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `ISBN` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `author` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `num` int(8) NULL DEFAULT NULL,
  `costNum` int(8) NULL DEFAULT 0,
  `classify` varchar(5) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图书类型',
  `isOpen` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '是否开放借阅',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of book_info
-- ----------------------------
INSERT INTO `book_info` VALUES (1, '软件设计师教程（第五版）', '9787302491224', '褚华、霍秋艳', 10, 1, '3', '1');
INSERT INTO `book_info` VALUES (2, '程序员5天修炼', '9787517096832', '施游、邹月平、曾哲军', 5, 1, '3', '1');
INSERT INTO `book_info` VALUES (3, '程序员教程（第五版）', '9787302491231', '张淑平、覃桂敏', 1, 1, '3', '1');
INSERT INTO `book_info` VALUES (4, 'Java核心技术 卷I', '9787111636663', '凯·S·霍斯特曼', 10, 0, '3', '0');

-- ----------------------------
-- Table structure for book_rent
-- ----------------------------
DROP TABLE IF EXISTS `book_rent`;
CREATE TABLE `book_rent`  (
  `id` int(8) NOT NULL AUTO_INCREMENT,
  `bookId` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `rentTime` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `returnTime` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `rentDuration` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `rentUser` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `rentStatus` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of book_rent
-- ----------------------------
INSERT INTO `book_rent` VALUES (1, '3', '2023-05-30 15:22:37', '2023-06-06 15:22:37', '7', 'test001', '1');
INSERT INTO `book_rent` VALUES (2, '2', '2023-05-30 15:23:27', '2023-06-06 15:23:27', '7', 'test001', '1');
INSERT INTO `book_rent` VALUES (3, '1', '2023-05-30 15:23:43', '2023-07-29 15:23:43', '60', 'admin', '1');

-- ----------------------------
-- Table structure for notice
-- ----------------------------
DROP TABLE IF EXISTS `notice`;
CREATE TABLE `notice`  (
  `id` int(8) NOT NULL AUTO_INCREMENT,
  `title` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `content` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `author` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `time` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of notice
-- ----------------------------
INSERT INTO `notice` VALUES (3, '欢迎进入图书管理系统！', 'Vue+Springboot图书管理系统开发完毕啦！', '李锦成', '2023-05-29 22:48:01');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `uid` int(8) NOT NULL AUTO_INCREMENT,
  `username` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `usergroup` varchar(5) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `rentNum` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`uid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'admin', 'axsz123456', '李锦成', '1', '1');
INSERT INTO `user` VALUES (2, 'test001', '123456', '测试者1', '6', '2');

-- ----------------------------
-- Table structure for usergroup
-- ----------------------------
DROP TABLE IF EXISTS `usergroup`;
CREATE TABLE `usergroup`  (
  `id` int(8) NOT NULL AUTO_INCREMENT,
  `groupName` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `maxDay` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `maxRent` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of usergroup
-- ----------------------------
INSERT INTO `usergroup` VALUES (1, '管理员', '4', '100');
INSERT INTO `usergroup` VALUES (2, '普通用户', '2', '10');
INSERT INTO `usergroup` VALUES (3, '中级用户', '3', '50');
INSERT INTO `usergroup` VALUES (4, '高级用户', '4', '100');
INSERT INTO `usergroup` VALUES (6, '测试用户组', '1', '2');

SET FOREIGN_KEY_CHECKS = 1;
