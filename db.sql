/*
Navicat MySQL Data Transfer

Source Server         : rongcloud
Source Server Version : 50720
Source Host           : 172.16.248.81:3306
Source Database       : backend

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2017-11-28 16:46:28
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `card`
-- ----------------------------
DROP TABLE IF EXISTS `card`;
CREATE TABLE `card` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(1000) DEFAULT NULL,
  `context` varchar(1000) DEFAULT NULL,
  `create_id` int(255) DEFAULT NULL COMMENT '创建人',
  `create_time` varchar(1000) DEFAULT NULL COMMENT '创建时间',
  `looker` json DEFAULT NULL COMMENT '看的人',
  `type_id` int(255) DEFAULT NULL COMMENT '类型的id',
  `important` varchar(1000) DEFAULT NULL,
  `spare` json DEFAULT NULL,
  `status` int(10) NOT NULL DEFAULT '0' COMMENT '0都可看见1不可看见',
  `remove` int(11) DEFAULT '1' COMMENT '0是已删除 1是未删除',
  PRIMARY KEY (`id`),
  KEY `type_id` (`type_id`),
  CONSTRAINT `t_id` FOREIGN KEY (`type_id`) REFERENCES `card_type` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of card
-- ----------------------------
INSERT INTO `card` VALUES ('5', '12', '3123', '27', '123', null, '4', null, null);
INSERT INTO `card` VALUES ('6', '111', '222', '27', '2017-11-27-16-59-45', null, '4', null, null);
INSERT INTO `card` VALUES ('7', '111', '222', '27', '2017-11-27-16-59-49', null, '4', null, null);
INSERT INTO `card` VALUES ('8', '111', '222', '27', '2017-11-27-17-01-13', null, '4', null, null);
INSERT INTO `card` VALUES ('10', '888', 'uuu', '27', '2017-11-28-09-00-53', null, '4', null, null);
INSERT INTO `card` VALUES ('11', '888', 'uuu', '27', '2017-11-28-09-03-36', null, '4', null, null);
INSERT INTO `card` VALUES ('12', '888', 'uuu', '27', '2017-11-28-09-33-54', null, '4', null, null);
INSERT INTO `card` VALUES ('13', '1', '2', '27', '2017-11-28-09-38-22', null, '4', null, null);
INSERT INTO `card` VALUES ('14', '1', '2', '27', '2017-11-28-09-38-41', null, '4', null, null);
INSERT INTO `card` VALUES ('15', '1', '2', '27', '2017-11-28-09-39-25', null, '4', null, null);
INSERT INTO `card` VALUES ('16', '1', '2', '27', '2017-11-28-09-39-40', null, '4', null, null);
INSERT INTO `card` VALUES ('17', '1', '2', '27', '2017-11-28-09-40-33', null, '4', null, null);
INSERT INTO `card` VALUES ('18', '1', '2', '27', '2017-11-28-09-42-15', null, '4', null, null);
INSERT INTO `card` VALUES ('19', '1', '2', '27', '2017-11-28-09-44-48', null, '4', null, null);
INSERT INTO `card` VALUES ('20', '1', '2', '27', '2017-11-28-09-46-20', null, '4', null, null);
INSERT INTO `card` VALUES ('21', '1', '2', '27', '2017-11-28-09-46-56', null, '4', null, null);
INSERT INTO `card` VALUES ('22', '1', '2', '27', '2017-11-28-09-47-55', null, '4', null, null);
INSERT INTO `card` VALUES ('23', '1', '2', '27', '2017-11-28-09-53-03', null, '4', null, null);
INSERT INTO `card` VALUES ('24', '1', '2', '27', '2017-11-28-10-51-44', null, '4', null, null);
INSERT INTO `card` VALUES ('25', '1', '2', '27', '2017-11-28-10-58-54', null, '4', null, null);
INSERT INTO `card` VALUES ('26', '222', '3232', '27', '2017-11-28-12-03-22', null, '4', null, null);
INSERT INTO `card` VALUES ('27', '222', '3232', '27', '2017-11-28-12-03-29', null, '4', null, null);
INSERT INTO `card` VALUES ('28', '222', '3232', '27', '2017-11-28-12-05-42', null, '4', null, null);
INSERT INTO `card` VALUES ('29', '222', '3232', '27', '2017-11-28-12-05-55', null, '4', null, null);
INSERT INTO `card` VALUES ('30', '222', '3232', '27', '2017-11-28-12-05-56', null, '4', null, null);
INSERT INTO `card` VALUES ('31', '222', '3232', '27', '2017-11-28-12-05-57', null, '4', null, null);
INSERT INTO `card` VALUES ('32', '222', '3232', '27', '2017-11-28-12-06-08', null, '4', null, null);
INSERT INTO `card` VALUES ('33', '222', '3232', '27', '2017-11-28-13-41-51', null, '4', null, null);
INSERT INTO `card` VALUES ('34', '1', '2', '27', '2017-11-28-14-46-09', null, '4', null, null);
INSERT INTO `card` VALUES ('36', '888', 'uuu', '27', '2017-11-28-14-49-40', null, '4', null, null);
INSERT INTO `card` VALUES ('38', '888', 'uuu', '27', '2017-11-28-14-49-45', null, '4', null, null);
INSERT INTO `card` VALUES ('39', '888', 'uuu', '27', '2017-11-28-14-49-59', null, '4', null, null);
INSERT INTO `card` VALUES ('40', '888', 'uuu', '27', '2017-11-28-14-50-46', null, '4', null, null);

-- ----------------------------
-- Table structure for `card_people_status`
-- ----------------------------
DROP TABLE IF EXISTS `card_people_status`;
CREATE TABLE `card_people_status` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(255) DEFAULT NULL,
  `cid` int(255) DEFAULT NULL,
  `status` int(255) DEFAULT '0' COMMENT '0未读1已读',
  PRIMARY KEY (`id`),
  KEY `cid` (`cid`),
  CONSTRAINT `tt_id` FOREIGN KEY (`cid`) REFERENCES `card` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of card_people_status
-- ----------------------------
INSERT INTO `card_people_status` VALUES ('1', '27', '5', '0');
INSERT INTO `card_people_status` VALUES ('2', '27', '6', '1');
INSERT INTO `card_people_status` VALUES ('3', '27', '7', '1');
INSERT INTO `card_people_status` VALUES ('4', '27', '8', '1');
INSERT INTO `card_people_status` VALUES ('5', '1', '11', '1');
INSERT INTO `card_people_status` VALUES ('6', '1', '11', '1');
INSERT INTO `card_people_status` VALUES ('7', '1', '12', '1');
INSERT INTO `card_people_status` VALUES ('8', '1', '12', '1');
INSERT INTO `card_people_status` VALUES ('9', '1', '17', '1');
INSERT INTO `card_people_status` VALUES ('10', '1', '21', '1');
INSERT INTO `card_people_status` VALUES ('11', '2', '21', '1');
INSERT INTO `card_people_status` VALUES ('12', '1', '22', '1');
INSERT INTO `card_people_status` VALUES ('13', '2', '22', '1');
INSERT INTO `card_people_status` VALUES ('14', '1', '23', '1');
INSERT INTO `card_people_status` VALUES ('15', '2', '23', '1');
INSERT INTO `card_people_status` VALUES ('16', '1', '24', '1');
INSERT INTO `card_people_status` VALUES ('17', '2', '24', '1');
INSERT INTO `card_people_status` VALUES ('18', '1', '25', '1');
INSERT INTO `card_people_status` VALUES ('19', '2', '25', '1');
INSERT INTO `card_people_status` VALUES ('20', '1', '34', '1');
INSERT INTO `card_people_status` VALUES ('21', '2', '34', '1');

-- ----------------------------
-- Table structure for `card_type`
-- ----------------------------
DROP TABLE IF EXISTS `card_type`;
CREATE TABLE `card_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(1000) DEFAULT NULL,
  `create_id` int(255) DEFAULT NULL,
  `create_time` varchar(1000) DEFAULT NULL,
  `remove` int(255) DEFAULT '1' COMMENT '0 已删除 1是 未删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of card_type
-- ----------------------------
INSERT INTO `card_type` VALUES ('4', '123', '27', '123', '1');

-- ----------------------------
-- Table structure for `comment`
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` varchar(50) DEFAULT NULL COMMENT '评论内容',
  `uid` int(11) DEFAULT NULL COMMENT '用户id',
  `nid` int(11) DEFAULT NULL COMMENT '通知id',
  `create_time` varchar(20) DEFAULT NULL COMMENT '评论时间',
  `remove` int(11) DEFAULT '1' COMMENT '是否删除（0:已删除，1:未删除）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of comment
-- ----------------------------
INSERT INTO `comment` VALUES ('1', '哈', '33', '4', '2017-11-27-17-12-34', '1');
INSERT INTO `comment` VALUES ('2', null, '33', '2', '2017-11-28-13-47-36', '0');

-- ----------------------------
-- Table structure for `department`
-- ----------------------------
DROP TABLE IF EXISTS `department`;
CREATE TABLE `department` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `pid` int(11) DEFAULT NULL COMMENT '父级部门或组织id，0表示组织',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of department
-- ----------------------------
INSERT INTO `department` VALUES ('1', '奥维信科', '0');
INSERT INTO `department` VALUES ('2', '研发部门', '1');
INSERT INTO `department` VALUES ('3', 'CEO', '2');
INSERT INTO `department` VALUES ('4', '财务', '1');
INSERT INTO `department` VALUES ('5', '行政', '1');
INSERT INTO `department` VALUES ('6', 'BOOS', '2');
INSERT INTO `department` VALUES ('7', 'Emoji', '2');

-- ----------------------------
-- Table structure for `departments_users`
-- ----------------------------
DROP TABLE IF EXISTS `departments_users`;
CREATE TABLE `departments_users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL,
  `did` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of departments_users
-- ----------------------------
INSERT INTO `departments_users` VALUES ('1', '4', '1');
INSERT INTO `departments_users` VALUES ('20', '26', '3');
INSERT INTO `departments_users` VALUES ('21', '27', '3');
INSERT INTO `departments_users` VALUES ('22', '31', '6');
INSERT INTO `departments_users` VALUES ('23', '32', '6');
INSERT INTO `departments_users` VALUES ('24', '34', '6');
INSERT INTO `departments_users` VALUES ('25', '28', '2');
INSERT INTO `departments_users` VALUES ('26', '29', '2');
INSERT INTO `departments_users` VALUES ('27', '32', '7');
INSERT INTO `departments_users` VALUES ('28', '33', '7');
INSERT INTO `departments_users` VALUES ('29', '35', '3');

-- ----------------------------
-- Table structure for `notice`
-- ----------------------------
DROP TABLE IF EXISTS `notice`;
CREATE TABLE `notice` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `title` varchar(20) NOT NULL COMMENT '通知标题',
  `content` varchar(100) DEFAULT NULL COMMENT '通知内容',
  `type` int(11) DEFAULT NULL COMMENT '通知类型',
  `create_id` int(50) DEFAULT NULL COMMENT '创建人',
  `create_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `repeal` int(11) DEFAULT '1' COMMENT '是否撤销(0:已撤销，1：未撤销)',
  `remove` int(11) DEFAULT '1' COMMENT '是否删除(0:已删除，1：未删除)',
  `comment` int(11) DEFAULT '1' COMMENT '(0：不允许评论，1：允许评论)默认1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of notice
-- ----------------------------
INSERT INTO `notice` VALUES ('5', '系统通知', '开会', '2', '1', '2017-11-27-17-41-49', '0', '0', '1');
INSERT INTO `notice` VALUES ('6', '消息通知', '嗯嗯', '2', '1', '2017-11-27-17-41-49', '1', '1', '1');
INSERT INTO `notice` VALUES ('7', '申请通知', '哈哈', '2', '1', '2017-11-27-17-41-49', '1', '1', '1');
INSERT INTO `notice` VALUES ('11', '嗯', '嗯嗯', '2', '33', '2017-11-28-13-55-54', '1', '1', '1');
INSERT INTO `notice` VALUES ('14', '通知', '会', '1', '33', '2017-11-28-15-55-21', '1', '1', '1');
INSERT INTO `notice` VALUES ('15', '哈哈', '内容', '2', '33', '2017-11-28-16-02-27', '1', '1', '1');
INSERT INTO `notice` VALUES ('16', '通知', '会', '2', '33', '2017-11-28-16-11-59', '1', '1', '1');
INSERT INTO `notice` VALUES ('17', 'ccc', 'ccc', '2', '33', '2017-11-28-16-17-05', '1', '1', '1');

-- ----------------------------
-- Table structure for `notice_people`
-- ----------------------------
DROP TABLE IF EXISTS `notice_people`;
CREATE TABLE `notice_people` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `uid` int(11) DEFAULT NULL COMMENT '被通知人id',
  `notice_id` int(11) DEFAULT NULL COMMENT '通知id',
  `status` int(11) DEFAULT '0' COMMENT '(0：已读，1：未读)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=155 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of notice_people
-- ----------------------------
INSERT INTO `notice_people` VALUES ('26', '33', '5', '1');
INSERT INTO `notice_people` VALUES ('27', '34', '5', '1');
INSERT INTO `notice_people` VALUES ('28', '35', '5', '1');
INSERT INTO `notice_people` VALUES ('29', '26', '5', '1');
INSERT INTO `notice_people` VALUES ('30', '27', '5', '1');
INSERT INTO `notice_people` VALUES ('31', '28', '5', '1');
INSERT INTO `notice_people` VALUES ('32', '29', '5', '1');
INSERT INTO `notice_people` VALUES ('33', '31', '5', '1');
INSERT INTO `notice_people` VALUES ('34', '32', '5', '1');
INSERT INTO `notice_people` VALUES ('35', '33', '6', '1');
INSERT INTO `notice_people` VALUES ('36', '34', '6', '1');
INSERT INTO `notice_people` VALUES ('37', '35', '6', '1');
INSERT INTO `notice_people` VALUES ('38', '4', '6', '1');
INSERT INTO `notice_people` VALUES ('39', '26', '6', '1');
INSERT INTO `notice_people` VALUES ('40', '27', '6', '1');
INSERT INTO `notice_people` VALUES ('41', '28', '6', '1');
INSERT INTO `notice_people` VALUES ('42', '29', '6', '1');
INSERT INTO `notice_people` VALUES ('43', '31', '6', '1');
INSERT INTO `notice_people` VALUES ('44', '32', '6', '1');
INSERT INTO `notice_people` VALUES ('45', '33', '7', '1');
INSERT INTO `notice_people` VALUES ('46', '34', '7', '1');
INSERT INTO `notice_people` VALUES ('47', '35', '7', '1');
INSERT INTO `notice_people` VALUES ('48', '4', '7', '1');
INSERT INTO `notice_people` VALUES ('49', '26', '7', '1');
INSERT INTO `notice_people` VALUES ('50', '27', '7', '1');
INSERT INTO `notice_people` VALUES ('51', '28', '7', '1');
INSERT INTO `notice_people` VALUES ('52', '29', '7', '1');
INSERT INTO `notice_people` VALUES ('53', '31', '7', '1');
INSERT INTO `notice_people` VALUES ('54', '32', '7', '1');
INSERT INTO `notice_people` VALUES ('85', '33', '11', '1');
INSERT INTO `notice_people` VALUES ('86', '34', '11', '1');
INSERT INTO `notice_people` VALUES ('87', '35', '11', '1');
INSERT INTO `notice_people` VALUES ('88', '4', '11', '1');
INSERT INTO `notice_people` VALUES ('89', '26', '11', '1');
INSERT INTO `notice_people` VALUES ('90', '27', '11', '1');
INSERT INTO `notice_people` VALUES ('91', '28', '11', '1');
INSERT INTO `notice_people` VALUES ('92', '29', '11', '1');
INSERT INTO `notice_people` VALUES ('93', '31', '11', '1');
INSERT INTO `notice_people` VALUES ('94', '32', '11', '1');
INSERT INTO `notice_people` VALUES ('115', '33', '14', '1');
INSERT INTO `notice_people` VALUES ('116', '34', '14', '1');
INSERT INTO `notice_people` VALUES ('117', '35', '14', '1');
INSERT INTO `notice_people` VALUES ('118', '4', '14', '1');
INSERT INTO `notice_people` VALUES ('119', '26', '14', '1');
INSERT INTO `notice_people` VALUES ('120', '27', '14', '1');
INSERT INTO `notice_people` VALUES ('121', '28', '14', '1');
INSERT INTO `notice_people` VALUES ('122', '29', '14', '1');
INSERT INTO `notice_people` VALUES ('123', '31', '14', '1');
INSERT INTO `notice_people` VALUES ('124', '32', '14', '1');
INSERT INTO `notice_people` VALUES ('125', '33', '15', '1');
INSERT INTO `notice_people` VALUES ('126', '34', '15', '1');
INSERT INTO `notice_people` VALUES ('127', '35', '15', '1');
INSERT INTO `notice_people` VALUES ('128', '4', '15', '1');
INSERT INTO `notice_people` VALUES ('129', '26', '15', '1');
INSERT INTO `notice_people` VALUES ('130', '27', '15', '1');
INSERT INTO `notice_people` VALUES ('131', '28', '15', '1');
INSERT INTO `notice_people` VALUES ('132', '29', '15', '1');
INSERT INTO `notice_people` VALUES ('133', '31', '15', '1');
INSERT INTO `notice_people` VALUES ('134', '32', '15', '1');
INSERT INTO `notice_people` VALUES ('135', '33', '16', '1');
INSERT INTO `notice_people` VALUES ('136', '34', '16', '1');
INSERT INTO `notice_people` VALUES ('137', '35', '16', '1');
INSERT INTO `notice_people` VALUES ('138', '4', '16', '1');
INSERT INTO `notice_people` VALUES ('139', '26', '16', '1');
INSERT INTO `notice_people` VALUES ('140', '27', '16', '1');
INSERT INTO `notice_people` VALUES ('141', '28', '16', '1');
INSERT INTO `notice_people` VALUES ('142', '29', '16', '1');
INSERT INTO `notice_people` VALUES ('143', '31', '16', '1');
INSERT INTO `notice_people` VALUES ('144', '32', '16', '1');
INSERT INTO `notice_people` VALUES ('145', '33', '17', '1');
INSERT INTO `notice_people` VALUES ('146', '34', '17', '1');
INSERT INTO `notice_people` VALUES ('147', '35', '17', '1');
INSERT INTO `notice_people` VALUES ('148', '4', '17', '1');
INSERT INTO `notice_people` VALUES ('149', '26', '17', '1');
INSERT INTO `notice_people` VALUES ('150', '27', '17', '1');
INSERT INTO `notice_people` VALUES ('151', '28', '17', '1');
INSERT INTO `notice_people` VALUES ('152', '29', '17', '1');
INSERT INTO `notice_people` VALUES ('153', '31', '17', '1');
INSERT INTO `notice_people` VALUES ('154', '32', '17', '1');

-- ----------------------------
-- Table structure for `notice_type`
-- ----------------------------
DROP TABLE IF EXISTS `notice_type`;
CREATE TABLE `notice_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL COMMENT '类型名称',
  `create_id` int(11) DEFAULT NULL,
  `create_time` varchar(20) DEFAULT NULL,
  `remove` int(11) DEFAULT '1' COMMENT '是否删除（0：已删除，1：未删除）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of notice_type
-- ----------------------------
INSERT INTO `notice_type` VALUES ('2', '系统名称', '1', '2017-11-27-16-23-56', '1');
INSERT INTO `notice_type` VALUES ('3', '类型', '33', '2017-11-28-13-37-24', '0');

-- ----------------------------
-- Table structure for `opinion`
-- ----------------------------
DROP TABLE IF EXISTS `opinion`;
CREATE TABLE `opinion` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `yijian` varchar(1000) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `date` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of opinion
-- ----------------------------
INSERT INTO `opinion` VALUES ('1', 'zouyuanhao', '意见', '2017-11-22 14:28:52');

-- ----------------------------
-- Table structure for `roles_permissions`
-- ----------------------------
DROP TABLE IF EXISTS `roles_permissions`;
CREATE TABLE `roles_permissions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `permission` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=824 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of roles_permissions
-- ----------------------------
INSERT INTO `roles_permissions` VALUES ('548', 'card', 'card:add');
INSERT INTO `roles_permissions` VALUES ('549', 'card', 'card:del');
INSERT INTO `roles_permissions` VALUES ('550', 'card', 'card:set');
INSERT INTO `roles_permissions` VALUES ('551', 'card', 'card:getCards');
INSERT INTO `roles_permissions` VALUES ('552', 'cardtype', 'cardtype:del');
INSERT INTO `roles_permissions` VALUES ('553', 'cardtype', 'cardtype:set');
INSERT INTO `roles_permissions` VALUES ('554', 'cardtype', 'cardtype:getCardTypes');
INSERT INTO `roles_permissions` VALUES ('555', 'cardtype', 'cardtype:add');
INSERT INTO `roles_permissions` VALUES ('556', 'notice', 'notice:add');
INSERT INTO `roles_permissions` VALUES ('557', 'notice', 'notice:del');
INSERT INTO `roles_permissions` VALUES ('558', 'notice', 'notice:setRepeal');
INSERT INTO `roles_permissions` VALUES ('559', 'notice', 'notice:getNotices');
INSERT INTO `roles_permissions` VALUES ('560', 'noticetype', 'noticetype:add');
INSERT INTO `roles_permissions` VALUES ('561', 'noticetype', 'noticetype:del');
INSERT INTO `roles_permissions` VALUES ('562', 'noticetype', 'noticetype:set');
INSERT INTO `roles_permissions` VALUES ('563', 'noticetype', 'noticetype:getNoticeTypes');
INSERT INTO `roles_permissions` VALUES ('573', 'comment', 'comment:add');
INSERT INTO `roles_permissions` VALUES ('574', 'comment', 'comment:del');
INSERT INTO `roles_permissions` VALUES ('575', 'department', 'department:get');
INSERT INTO `roles_permissions` VALUES ('576', 'comment', 'comment:setComment');
INSERT INTO `roles_permissions` VALUES ('577', 'department', 'department:del');
INSERT INTO `roles_permissions` VALUES ('578', 'comment', 'comment:getComments');
INSERT INTO `roles_permissions` VALUES ('579', 'department', 'department:set');
INSERT INTO `roles_permissions` VALUES ('580', 'department', 'department:add');
INSERT INTO `roles_permissions` VALUES ('624', 'team', 'team:get');
INSERT INTO `roles_permissions` VALUES ('625', 'team', 'team:del');
INSERT INTO `roles_permissions` VALUES ('626', 'team', 'team:set');
INSERT INTO `roles_permissions` VALUES ('627', 'team', 'team:add');
INSERT INTO `roles_permissions` VALUES ('633', 'opinion', 'opinion:add');
INSERT INTO `roles_permissions` VALUES ('664', 'notice', 'notice:getNoticePeoples');
INSERT INTO `roles_permissions` VALUES ('665', 'notice', 'notice:getNotice');
INSERT INTO `roles_permissions` VALUES ('675', 'notice', 'notice:setStatus');
INSERT INTO `roles_permissions` VALUES ('676', 'card', 'card:getCard');
INSERT INTO `roles_permissions` VALUES ('677', 'card', 'card:getCardPeoples');
INSERT INTO `roles_permissions` VALUES ('678', 'card', 'card:setStatus');
INSERT INTO `roles_permissions` VALUES ('679', 'noticetype', 'noticetype:getNoticeType');
INSERT INTO `roles_permissions` VALUES ('680', 'department', 'department:top');
INSERT INTO `roles_permissions` VALUES ('681', 'department', 'department:list');
INSERT INTO `roles_permissions` VALUES ('792', 'test', 'pc:add');
INSERT INTO `roles_permissions` VALUES ('793', 'test', 'pc:del');
INSERT INTO `roles_permissions` VALUES ('821', 'admin', 'user:create');
INSERT INTO `roles_permissions` VALUES ('822', 'admin', 'user:delete');
INSERT INTO `roles_permissions` VALUES ('823', 'user', 'content:view');

-- ----------------------------
-- Table structure for `team`
-- ----------------------------
DROP TABLE IF EXISTS `team`;
CREATE TABLE `team` (
  `id` int(128) NOT NULL AUTO_INCREMENT,
  `no` varchar(128) NOT NULL COMMENT '群号',
  `name` varchar(255) NOT NULL COMMENT '群组名',
  `owner` varchar(255) NOT NULL COMMENT '群主，创建人，对应username',
  `icon` varchar(255) DEFAULT NULL COMMENT '头像',
  `create_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `no` (`no`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of team
-- ----------------------------
INSERT INTO `team` VALUES ('1', '11', '请问', '12', null, null);
INSERT INTO `team` VALUES ('13', '209501465', 'luzhijie22', 'luzhijie3', null, null);

-- ----------------------------
-- Table structure for `user_roles`
-- ----------------------------
DROP TABLE IF EXISTS `user_roles`;
CREATE TABLE `user_roles` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `role_name` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=515 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of user_roles
-- ----------------------------
INSERT INTO `user_roles` VALUES ('361', 'liuxiaopeng', 'card');
INSERT INTO `user_roles` VALUES ('369', 'zhangxueping', 'notice');
INSERT INTO `user_roles` VALUES ('382', 'liuxiaopeng', 'department');
INSERT INTO `user_roles` VALUES ('389', 'liuxiaopeng', 'notice');
INSERT INTO `user_roles` VALUES ('411', 'zhangxueping', 'noticetype');
INSERT INTO `user_roles` VALUES ('412', 'zhangxueping', 'comment');
INSERT INTO `user_roles` VALUES ('439', 'zouyuanhao', 'department');
INSERT INTO `user_roles` VALUES ('440', 'zouyuanhao', 'admin');
INSERT INTO `user_roles` VALUES ('441', 'zouyuanhao', 'user');
INSERT INTO `user_roles` VALUES ('442', 'zouyuanhao', 'card');
INSERT INTO `user_roles` VALUES ('443', 'zouyuanhao', 'notice');
INSERT INTO `user_roles` VALUES ('444', 'zhangyun', 'department');
INSERT INTO `user_roles` VALUES ('445', 'zhangyun', 'admin');
INSERT INTO `user_roles` VALUES ('446', 'zhangyun', 'user');
INSERT INTO `user_roles` VALUES ('447', 'zhangyun', 'card');
INSERT INTO `user_roles` VALUES ('448', 'zhangyun', 'notice');
INSERT INTO `user_roles` VALUES ('477', 'liuxiaopeng', 'cardtype');
INSERT INTO `user_roles` VALUES ('513', 'admin', 'admin');
INSERT INTO `user_roles` VALUES ('514', 'admin', 'user');

-- ----------------------------
-- Table structure for `user_team`
-- ----------------------------
DROP TABLE IF EXISTS `user_team`;
CREATE TABLE `user_team` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) DEFAULT NULL COMMENT '用户id',
  `tno` varchar(255) DEFAULT NULL COMMENT '群号',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uid_tno` (`uid`,`tno`),
  KEY `usersid_uid` (`uid`),
  KEY `tno_no` (`tno`),
  CONSTRAINT `tno_no` FOREIGN KEY (`tno`) REFERENCES `team` (`no`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `usersid_uid` FOREIGN KEY (`uid`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_team
-- ----------------------------
INSERT INTO `user_team` VALUES ('9', '1', '209501465');
INSERT INTO `user_team` VALUES ('1', '4', null);
INSERT INTO `user_team` VALUES ('10', '30', '209501465');

-- ----------------------------
-- Table structure for `users`
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户登录名，范围邮箱或手机。',
  `password` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `password_salt` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `name` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '姓名',
  `nickname` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '昵称',
  `token` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '网易云信token',
  `state` int(11) DEFAULT '1' COMMENT '0禁用 、1正常',
  PRIMARY KEY (`id`),
  UNIQUE KEY `users_username_uindex` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=306 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES ('1', 'luzhijie2', '123123', null, '鹿志洁111', '鹿志洁', '123456', '1');
INSERT INTO `users` VALUES ('4', 'admin', '2674cf277d8e850f8115cdf51399c9ec', 'platform:admin', '管理员', '管理员', '123456', '1');
INSERT INTO `users` VALUES ('26', 'zouyuanhao', 'aa0f973a957f6968bbd040bd293259c3', 'platform:zouyuanhao', '邹元浩', '邹元浩', '4f2c95acee2518f639d317ef103186d4', '1');
INSERT INTO `users` VALUES ('27', 'liuxiaopeng', 'bde78030c09f6ca55b6810313afa9d3f', 'platform:liuxiaopeng', '刘晓鹏', '刘晓鹏', '2e93ac59bd5186e2c2b94aa978ecf928', '1');
INSERT INTO `users` VALUES ('28', 'xuqing', 'bdeb485408366f79bfd3d0cc2add59e8', 'platform:xuqing', '徐庆', '徐庆', '5ef6690461ce15914e1241b677efc65c', '1');
INSERT INTO `users` VALUES ('29', 'jiweibo', 'd9f1b95259cdb64bbda8c102cb4ec855', 'platform:jiweibo', '季炜博', '季炜博', 'e36bcc30fd501f650cc0ddb08b16c7ee', '1');
INSERT INTO `users` VALUES ('30', 'luzhijie', 'b8708340d1e2c59a796aee07f721ac13', 'platform:luzhijie', '鹿志杰', '鹿志杰', '9c597fbeb44d17c427e81266f3ff9bfb', '1');
INSERT INTO `users` VALUES ('31', 'xufengjie', '002c8f6ffdb510ad95f005e18a448e4b', 'platform:xufengjie', '徐风杰', '徐风杰', '24fb7acb4eb1086ae00093e2f17ba71b', '1');
INSERT INTO `users` VALUES ('32', 'zhangyun', '8a808fb789b77646a7636cd2d2c0fafa', 'platform:zhangyun', '张赟', '张赟', '4f934654c7e2489fcbb20bf6eb4e3030', '1');
INSERT INTO `users` VALUES ('33', 'zhangxueping', 'b35322b19a6cf6b4e87d09c2b8b12db0', 'platform:zhangxueping', '张雪萍', '张雪萍', '688f9cf0dd29e41a0362c01c81780280', '1');
INSERT INTO `users` VALUES ('34', 'konglanping', 'a44fd369b66ee859e5c76aa9462e9589', 'platform:konglanping', '孔岚平', '孔岚平', 'd3f4942005d27bdfb9a4afeeae59ed93', '1');
INSERT INTO `users` VALUES ('35', 'panyuanyuan', '628a1e3a10fef620199fffd7b502aaf2', 'platform:panyuanyuan', '泮圆圆', '泮圆圆', '0e93e146b315d299f9a2360cd5305751', '1');
INSERT INTO `users` VALUES ('60', 'luzhijie3', '6747f10445dbf14e9637a58d98cc1f8a', 'platform:luzhijie3', '鹿志洁2', 'luzhiie3', 'fe3e3c08870dfb10e6dc956f9b9dce0e', '1');
