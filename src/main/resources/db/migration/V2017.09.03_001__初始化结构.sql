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
  `examination_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '试卷表ID',
  `examination_category_id` varchar(32)  COMMENT '所属类别  不启用',
  `examination_subject` varchar(999)  COMMENT '试卷标题',
  `examination_content` varchar(999)  COMMENT '试卷内容',
  `examination_start_time` datetime  COMMENT '开考时间',
  `examination_end_time` datetime  COMMENT '结束时间',
  `examination_time_length` int(11)  COMMENT '试卷时间',
  `examination_score` int(255)  COMMENT '试卷分数',
  `examination_status` int(255) COMMENT '状态	0出题中，1可以答题，2结束',
  PRIMARY KEY (`examination_id`)
) ENGINE=InnoDB ;

-- ----------------------------
-- Table structure for examination_question
-- ----------------------------
DROP TABLE IF EXISTS `examination_question`;
CREATE TABLE `examination_question` (
  `examination_question_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '试卷题目表ID  只有在0，1 模式下 生成(暂定)',
  `examination_question_examination_id` BIGINT  COMMENT '试卷id',
  `examination_question_headline_id` BIGINT  COMMENT '大题id',
  `examination_question_question_id` BIGINT  COMMENT '题目id',
  PRIMARY KEY (`examination_question_id`)
) ENGINE=InnoDB ;

-- ----------------------------
-- Table structure for headline
-- ----------------------------
DROP TABLE IF EXISTS `headline`;
CREATE TABLE `headline` (
  `headline_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '试卷大题ID',
  `headline_examination_id` BIGINT COMMENT '试卷id',
  `headline_headline_subject` varchar(999)  COMMENT '标题',
  `headline_examination_content` varchar(999)  COMMENT '题目说明',
  `headline_question_type` varchar(2)  COMMENT '题目类型',
  `headline_question_count` int  COMMENT '题目数量',
  `headline_score` int  COMMENT '每题分数',
  `headline_pattern` int  COMMENT '试卷模式	0.选题，1.系统随机，2.用户随机',
  `headline_answer_pattern` int  COMMENT '答案模式	1.固定模式，2随机模式',
  `headline_sort` int  COMMENT '顺序',
  PRIMARY KEY (`headline_id`)
) ENGINE=InnoDB ;

-- ----------------------------
-- Table structure for question
-- ----------------------------
DROP TABLE IF EXISTS `question`;
CREATE TABLE `question` (
  `question_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '题库表ID',
  `question_category_id` varchar(32)  COMMENT '所属类别  不启用',
  `question_type` varchar(2)  COMMENT '题目类型   00:判断，01:单选，02:多选',
  `question_subject` varchar(999)  COMMENT '题目',
  `question_answer` varchar(1000)  COMMENT '备选答案 用竖线分割',
  `question_right_answer` varchar(50)  COMMENT '正确答案索引，  竖线分割，如果是判断题，0代表假',
  `question_score` int  COMMENT '默认分数',
  `question_analysis` varchar(999)  COMMENT '题目解析',
  `question_status` int  COMMENT '题目状态   0:正常,1:不可用',
  `question_remark` varchar(999)  COMMENT '备注',
  PRIMARY KEY (`question_id`)
) ENGINE=InnoDB ;


-- ----------------------------
-- Table structure for system_config
-- ----------------------------
DROP TABLE IF EXISTS `system_config`;
CREATE TABLE `system_config` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `type` varchar(30)  COMMENT '类型',
  `key` varchar(30)  COMMENT '唯一的键',
  `value` varchar(100)  COMMENT '值',
  `memo` varchar(2000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB ;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `userid` BIGINT NOT NULL AUTO_INCREMENT,
  `name` varchar(20)  COMMENT '用户姓名',
  `gender` smallint  COMMENT '用户性别 0男 1女',
  `username` varchar(20)  COMMENT '用户名',
  `permission` varchar(3000)  COMMENT '权限',
  `password` varchar(36)  COMMENT '用户密码(md5(name+password))',
  `age` int  COMMENT '年龄',
  `idnumber` varchar(18)  COMMENT '身份证号',
  `identity` varchar(20)  COMMENT '身份(1管理员，2用户)',
  `mobilenumber` varchar(11)  COMMENT '手机号',
  `area` varchar(100)  COMMENT '地区',
  `school` varchar(50)  COMMENT '所属学校',
  `grade` int  COMMENT '年级',
  `classname` varchar(50)  COMMENT '所在班级',
  `major` varchar(255)  COMMENT '专业',
  `verifyquestion` varchar(200)  COMMENT '找回密码问题',
  `verifyanswer` varchar(20)  COMMENT '答案',
  `create_time` datetime ,
  `update_time` datetime ,
  `wx_openid` varchar(64)  COMMENT '用于微信绑定',
  headimgurl varchar(255)  COMMENT '微信头像',
  unionid varchar(64)  COMMENT '全局id',
  nickname varchar(64)  COMMENT '微信昵称',
  status int  COMMENT '状态0：没有完善资料，1：已经完善资料',
  PRIMARY KEY (`userid`)
) ENGINE=InnoDB ;

-- ----------------------------
-- Table structure for user_examination
-- ----------------------------
DROP TABLE IF EXISTS `user_examination`;
CREATE TABLE `user_examination` (
  `user_examination_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '用户试卷表ID',
  `user_examination_userid` BIGINT  COMMENT '用户编号',
  `user_examination_examination_id` BIGINT  COMMENT '试卷编号',
  `create_time` datetime  COMMENT '题库生成时间',
  `user_examination_ip` varchar(255)  COMMENT '生成地点',
  `user_examination_submit_time` datetime  COMMENT '交卷时间',
  `user_examination_time_length` double  COMMENT '答卷耗时',
  `user_examination_score` int  COMMENT '得分',
  `user_examination_borwseinfo` varchar(255)  COMMENT '浏览器信息',
  `user_examination_systeminfo` varchar(255)  COMMENT '系统信息(1.pc,2.微信)',
  `user_examination_status` varchar(255)  COMMENT '状态	0有效，1答题完成，2作废',
  PRIMARY KEY (`user_examination_id`),
  KEY `i_user_examination_userid` (`user_examination_userid`),
  KEY `i_user_examination_examination_id` (`user_examination_examination_id`)
) ENGINE=InnoDB ;

-- ----------------------------
-- Table structure for user_prize
-- ----------------------------
DROP TABLE IF EXISTS `user_prize`;
CREATE TABLE `user_prize` (
  `user_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '用户的id',
  `user_name` varchar(255)  COMMENT '用户名称',
  `valid_code` varchar(255)  COMMENT '验证码',
  `create_time` datetime ,
  `flag` varchar(255)  COMMENT '标记',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB ;

-- ----------------------------
-- Table structure for user_question
-- ----------------------------
DROP TABLE IF EXISTS `user_question`;
CREATE TABLE `user_question` (
  `user_question_id` BIGINT NOT NULL AUTO_INCREMENT,
  `user_question_examination_id` BIGINT  COMMENT '试卷编号',
  `user_question_headline_id` BIGINT  COMMENT '大题编号',
  `user_question_userid` BIGINT  COMMENT '用户id',
  `user_question_question_id` BIGINT  COMMENT '题目id',
  `user_question_score` int  COMMENT '分数',
  `user_question_right_answer` varchar(50)  COMMENT '正确答案',
  `user_question_user_answer` varchar(50)  COMMENT '用户答案',
  `user_question_sort` int  COMMENT '排序用的',
  PRIMARY KEY (`user_question_id`,`user_question_userid`),
  KEY `inx_qeqq` (`user_question_examination_id`,`user_question_userid`,`user_question_question_id`),
  KEY `inx_hd` (`user_question_headline_id`,`user_question_userid`)
) ENGINE=InnoDB  PARTITION BY hash(user_question_userid) PARTITIONS 40;
