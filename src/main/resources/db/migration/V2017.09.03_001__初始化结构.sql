/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50714
Source Host           : localhost:3306
Source Database       : exam2

Target Server Type    : MYSQL
Target Server Version : 50714
File Encoding         : 65001

Date: 2017-09-03 00:50:56
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for examination
-- ----------------------------
DROP TABLE IF EXISTS `examination`;
CREATE TABLE `examination` (
  `examination_id` varchar(32) NOT NULL COMMENT '试卷表ID',
  `examination_category_id` varchar(32) DEFAULT NULL COMMENT '所属类别  不启用',
  `examination_subject` varchar(999) DEFAULT NULL COMMENT '试卷标题',
  `examination_content` varchar(999) DEFAULT NULL COMMENT '试卷内容',
  `examination_start_time` datetime DEFAULT NULL COMMENT '开考时间',
  `examination_end_time` datetime DEFAULT NULL COMMENT '结束时间',
  `examination_time_length` int(11) DEFAULT NULL COMMENT '试卷时间',
  `examination_score` int(255) DEFAULT NULL COMMENT '试卷分数',
  `examination_status` int(255) DEFAULT NULL COMMENT '状态	0出题中，1可以答题，2结束',
  PRIMARY KEY (`examination_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for examination_question
-- ----------------------------
DROP TABLE IF EXISTS `examination_question`;
CREATE TABLE `examination_question` (
  `examination_question_id` varchar(32) NOT NULL COMMENT '试卷题目表ID  只有在0，1 模式下 生成(暂定)',
  `examination_question_examination_id` varchar(32) DEFAULT NULL COMMENT '试卷id',
  `examination_question_headline_id` varchar(32) DEFAULT NULL COMMENT '大题id',
  `examination_question_question_id` varchar(32) DEFAULT NULL COMMENT '题目id',
  PRIMARY KEY (`examination_question_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for headline
-- ----------------------------
DROP TABLE IF EXISTS `headline`;
CREATE TABLE `headline` (
  `headline_id` varchar(32) NOT NULL COMMENT '试卷大题ID',
  `headline_examination_id` varchar(32) DEFAULT NULL COMMENT '试卷id',
  `headline_headline_subject` varchar(999) DEFAULT NULL COMMENT '标题',
  `headline_examination_content` varchar(999) DEFAULT NULL COMMENT '题目说明',
  `headline_question_type` varchar(2) DEFAULT NULL COMMENT '题目类型',
  `headline_question_count` int(255) DEFAULT NULL COMMENT '题目数量',
  `headline_score` int(255) DEFAULT NULL COMMENT '每题分数',
  `headline_pattern` int(255) DEFAULT NULL COMMENT '试卷模式	0.选题，1.系统随机，2.用户随机',
  `headline_answer_pattern` int(255) DEFAULT NULL COMMENT '答案模式	1.固定模式，2随机模式',
  `headline_sort` int(255) DEFAULT NULL COMMENT '顺序',
  PRIMARY KEY (`headline_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for question
-- ----------------------------
DROP TABLE IF EXISTS `question`;
CREATE TABLE `question` (
  `question_id` varchar(32) NOT NULL COMMENT '题库表ID',
  `question_category_id` varchar(32) DEFAULT NULL COMMENT '所属类别  不启用',
  `question_type` varchar(2) DEFAULT NULL COMMENT '题目类型   00:判断，01:单选，02:多选',
  `question_subject` varchar(999) DEFAULT NULL COMMENT '题目',
  `question_answer` varchar(1000) DEFAULT NULL COMMENT '备选答案 用竖线分割',
  `question_right_answer` varchar(50) DEFAULT NULL COMMENT '正确答案索引，  竖线分割，如果是判断题，0代表假',
  `question_score` int(255) DEFAULT NULL COMMENT '默认分数',
  `question_analysis` varchar(999) DEFAULT NULL COMMENT '题目解析',
  `question_status` int(255) DEFAULT NULL COMMENT '题目状态   0:正常,1:不可用',
  `question_remark` varchar(999) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`question_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for system_config
-- ----------------------------
DROP TABLE IF EXISTS `system_config`;
CREATE TABLE `system_config` (
  `id` varchar(32) NOT NULL,
  `type` varchar(30) DEFAULT NULL COMMENT '类型',
  `key` varchar(30) DEFAULT NULL COMMENT '唯一的键',
  `value` varchar(100) DEFAULT NULL COMMENT '值',
  `memo` varchar(2000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `userid` varchar(32) NOT NULL,
  `name` varchar(20) DEFAULT NULL COMMENT '用户姓名',
  `gender` smallint(6) DEFAULT NULL COMMENT '用户性别 0男 1女',
  `username` varchar(20) DEFAULT NULL COMMENT '用户名',
  `permission` varchar(3000) DEFAULT NULL COMMENT '权限',
  `password` varchar(36) DEFAULT NULL COMMENT '用户密码(md5(name+password))',
  `age` int(11) DEFAULT NULL COMMENT '年龄',
  `idnumber` varchar(18) DEFAULT NULL COMMENT '身份证号',
  `identity` varchar(20) DEFAULT NULL COMMENT '身份(1管理员，2用户)',
  `mobilenumber` varchar(11) DEFAULT NULL COMMENT '手机号',
  `area` varchar(100) DEFAULT NULL COMMENT '地区',
  `school` varchar(50) DEFAULT NULL COMMENT '所属学校',
  `grade` int(1) DEFAULT NULL COMMENT '年级',
  `classname` varchar(50) DEFAULT NULL COMMENT '所在班级',
  `major` varchar(255) DEFAULT NULL COMMENT '专业',
  `verifyquestion` varchar(200) DEFAULT NULL COMMENT '找回密码问题',
  `verifyanswer` varchar(20) DEFAULT NULL COMMENT '答案',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `wx_openid` varchar(64) DEFAULT NULL COMMENT '用于微信绑定',
  PRIMARY KEY (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user_examination
-- ----------------------------
DROP TABLE IF EXISTS `user_examination`;
CREATE TABLE `user_examination` (
  `user_examination_id` varchar(32) NOT NULL COMMENT '用户试卷表ID',
  `user_examination_userid` varchar(32) DEFAULT NULL COMMENT '用户编号',
  `user_examination_examination_id` varchar(32) DEFAULT NULL COMMENT '试卷编号',
  `create_time` datetime DEFAULT NULL COMMENT '题库生成时间',
  `user_examination_ip` varchar(255) DEFAULT NULL COMMENT '生成地点',
  `user_examination_submit_time` datetime DEFAULT NULL COMMENT '交卷时间',
  `user_examination_time_length` double DEFAULT NULL COMMENT '答卷耗时',
  `user_examination_score` int(255) DEFAULT NULL COMMENT '得分',
  `user_examination_borwseinfo` varchar(255) DEFAULT NULL COMMENT '浏览器信息',
  `user_examination_systeminfo` varchar(255) DEFAULT NULL COMMENT '系统信息(1.pc,2.微信)',
  `user_examination_status` varchar(255) DEFAULT NULL COMMENT '状态	0有效，1答题完成，2作废',
  PRIMARY KEY (`user_examination_id`),
  KEY `i_user_examination_userid` (`user_examination_userid`),
  KEY `i_user_examination_examination_id` (`user_examination_examination_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user_prize
-- ----------------------------
DROP TABLE IF EXISTS `user_prize`;
CREATE TABLE `user_prize` (
  `user_id` varchar(32) NOT NULL COMMENT '用户的id',
  `user_name` varchar(255) DEFAULT NULL COMMENT '用户名称',
  `valid_code` varchar(255) DEFAULT NULL COMMENT '验证码',
  `create_time` datetime DEFAULT NULL,
  `flag` varchar(255) DEFAULT NULL COMMENT '标记',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user_question
-- ----------------------------
DROP TABLE IF EXISTS `user_question`;
CREATE TABLE `user_question` (
  `user_question_id` varchar(32) NOT NULL,
  `user_question_examination_id` varchar(32) DEFAULT NULL COMMENT '试卷编号',
  `user_question_headline_id` varchar(32) DEFAULT NULL COMMENT '大题编号',
  `user_question_userid` varchar(32) DEFAULT NULL COMMENT '用户id',
  `user_question_question_id` varchar(32) DEFAULT NULL COMMENT '题目id',
  `user_question_score` int(255) DEFAULT NULL COMMENT '分数',
  `user_question_right_answer` varchar(50) DEFAULT NULL COMMENT '正确答案',
  `user_question_user_answer` varchar(50) DEFAULT NULL COMMENT '用户答案',
  `user_question_sort` int(255) DEFAULT NULL COMMENT '排序用的',
  PRIMARY KEY (`user_question_id`),
  KEY `inx_qeqq` (`user_question_examination_id`,`user_question_userid`,`user_question_question_id`),
  KEY `inx_hd` (`user_question_headline_id`,`user_question_userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
