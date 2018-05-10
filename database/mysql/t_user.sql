/*
 Navicat Premium Data Transfer

 Source Server         : bz12306
 Source Server Type    : MySQL
 Source Server Version : 50722
 Source Host           : 47.92.87.25
 Source Database       : jbizdemo

 Target Server Type    : MySQL
 Target Server Version : 50722
 File Encoding         : utf-8

 Date: 05/10/2018 19:16:45 PM
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `t_user`
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `userId` varchar(50) NOT NULL,
  `apiKey` varchar(50) NOT NULL,
  `nickName` varchar(255) DEFAULT NULL,
  `mobileNum` varchar(50) DEFAULT NULL,
  `emailAddr` varchar(50) DEFAULT NULL,
  `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updateTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

SET FOREIGN_KEY_CHECKS = 1;
