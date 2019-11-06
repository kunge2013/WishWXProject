/*
Navicat MySQL Data Transfer

Source Server         : 愿礼  wish_platf
Source Server Version : 80017
Source Host           : localhost:3306
Source Database       : wish_platf

Target Server Type    : MYSQL
Target Server Version : 80017
File Encoding         : 65001

Date: 2019-11-05 17:53:54
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for banner
-- ----------------------------
DROP TABLE IF EXISTS `banner`;
CREATE TABLE `banner` (
  `bannerid` int(8) NOT NULL AUTO_INCREMENT COMMENT '轮播图ID',
  `bannertype` int(2) DEFAULT '0' COMMENT '10 广告 20 活动推广',
  `bannerimagesrc` varchar(150) COLLATE utf8_bin DEFAULT '""' COMMENT '图片地址',
  `createtime` bigint(20) DEFAULT NULL COMMENT '添加时间',
  `bannerstatus` int(2) DEFAULT '0' COMMENT '10 隐藏 20 显示',
  `relgoods` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '关联商品用分号;分隔',
  `jumpurl` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '跳转url 地址 800 * 800 px',
  `display` int(11) DEFAULT NULL COMMENT '顺序索引 按照从小到大顺序排列',
  PRIMARY KEY (`bannerid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of banner
-- ----------------------------

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
  `categoryid` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '品类ID',
  `categoryname` varchar(100) DEFAULT NULL COMMENT '品类名称',
  `bindgoodsnum` int(11) DEFAULT '0' COMMENT '绑定商品个数',
  `memberId` bigint(20) DEFAULT NULL COMMENT '创建用户',
  `createtime` bigint(20) DEFAULT '0' COMMENT '创建时间',
  PRIMARY KEY (`categoryid`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES ('1', '张安', '0', '0', '0');
INSERT INTO `category` VALUES ('2', '扎根三  ', '0', '0', '0');
INSERT INTO `category` VALUES ('6', '王二', '0', '0', '0');

-- ----------------------------
-- Table structure for customer
-- ----------------------------
DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer` (
  `customerid` int(8) NOT NULL AUTO_INCREMENT COMMENT '客户id',
  `createtime` bigint(20) DEFAULT '0' COMMENT '创建时间 (注册时间)',
  `consumptionno` bigint(20) DEFAULT '0' COMMENT '消费次数',
  `consumptionamount` bigint(20) DEFAULT '0' COMMENT '消费金额',
  `lastconsumption` bigint(20) DEFAULT '0' COMMENT '上次消费时间',
  `wechatnickname` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '""' COMMENT '微信昵称',
  `customertel` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '""' COMMENT '电话号码',
  `customername` varchar(20) DEFAULT '""' COMMENT '姓名',
  `customersex` int(2) DEFAULT '2' COMMENT '0 女 1男 2 未知',
  `customerlabels` varchar(255) DEFAULT '""' COMMENT '客户标签',
  `customerbirth` bigint(20) DEFAULT '0' COMMENT '客户生日',
  `customerconstella` varchar(255) DEFAULT '""' COMMENT '客户星座',
  `anniversariesday` bigint(20) DEFAULT NULL COMMENT '纪念日',
  `wxopenid` varchar(50) DEFAULT '' COMMENT '微信openId',
  `wxsessionkey` varchar(50) DEFAULT '' COMMENT '微信 sessionkey',
  PRIMARY KEY (`customerid`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of customer
-- ----------------------------
INSERT INTO `customer` VALUES ('1', '1572931938257', '0', '0', '0', '\"\"', '\"\"', 'administrator', '2', '\"\"', '0', '\"\"', null, 'olidX4zWcjgN4Sg8bJSOGi3Qk2N0', 'eRs6Nx022mp6rh78YWgwew==');
INSERT INTO `customer` VALUES ('2', '1572932896927', '0', '0', '0', 'administrator', '13044889046', '', '2', '', '0', '', '0', 'otDha5NlJr2FZLiFcyNCLX24TYiw', 'AnWUHr0X9ONUcarsvTwbMg==');
INSERT INTO `customer` VALUES ('3', '1572933835717', '0', '0', '0', 'administrator', '13044889026', '', '2', '', '0', '', '0', 'otDha5NlJr2FZLiFcyNCLX24TYiw', 'sPe6n4hyErrBSSChEWpPBA==');

-- ----------------------------
-- Table structure for dictionary
-- ----------------------------
DROP TABLE IF EXISTS `dictionary`;
CREATE TABLE `dictionary` (
  `dictionaryid` int(8) NOT NULL AUTO_INCREMENT COMMENT '字典ID',
  `code` varchar(50) DEFAULT '""' COMMENT '编码',
  `desc` varchar(100) DEFAULT '""' COMMENT '描述',
  `type` varchar(50) DEFAULT '""' COMMENT '类型',
  `parentcode` varchar(50) DEFAULT '"-1"' COMMENT '父类型',
  PRIMARY KEY (`dictionaryid`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of dictionary
-- ----------------------------
INSERT INTO `dictionary` VALUES ('1', '10', '单品', 'goodstype', '\"-1\"');
INSERT INTO `dictionary` VALUES ('2', '20', '礼包', 'goodstype', '\"-1\"');
INSERT INTO `dictionary` VALUES ('3', '30', '福袋', 'goodstype', '\"-1\"');
INSERT INTO `dictionary` VALUES ('4', '10', '不可退换', 'returnpolicy', '\"-1\"');
INSERT INTO `dictionary` VALUES ('5', '20', '七天无理由退款', 'returnpolicy', '\"-1\"');
INSERT INTO `dictionary` VALUES ('6', '30', '质量问题退换', 'returnpolicy', '\"-1\"');
INSERT INTO `dictionary` VALUES ('7', '10', '上架', 'goodsstatus', '\"-1\"');
INSERT INTO `dictionary` VALUES ('8', '20', '下架', 'goodsstatus', '\"-1\"');
INSERT INTO `dictionary` VALUES ('9', '30', '预上架', 'goodsstatus', '\"-1\"');
INSERT INTO `dictionary` VALUES ('10', '10', '广告', 'bannertype', '\"-1\"');
INSERT INTO `dictionary` VALUES ('11', '20', '活动推广', 'bannertype', '\"-1\"');

-- ----------------------------
-- Table structure for giftbasketinfo
-- ----------------------------
DROP TABLE IF EXISTS `giftbasketinfo`;
CREATE TABLE `giftbasketinfo` (
  `giftbasketid` int(8) NOT NULL AUTO_INCREMENT COMMENT '礼物篮ID',
  `goodsid` int(8) NOT NULL COMMENT '商品ID',
  `customerid` int(8) NOT NULL COMMENT '客户 ID',
  `createtime` bigint(20) NOT NULL COMMENT '添加时间',
  `currentprice` int(8) DEFAULT '0' COMMENT '加入时的售价',
  `purchasenum` int(8) DEFAULT '0' COMMENT '购买数量',
  `modifytime` bigint(20) DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`giftbasketid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of giftbasketinfo
-- ----------------------------

-- ----------------------------
-- Table structure for goodsinfo
-- ----------------------------
DROP TABLE IF EXISTS `goodsinfo`;
CREATE TABLE `goodsinfo` (
  `goodsid` int(20) NOT NULL AUTO_INCREMENT COMMENT '商品ID',
  `goodsname` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '""' COMMENT '商品名称',
  `goodsdesc` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '""' COMMENT '商品简介',
  `coverimage` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '""' COMMENT '商品封面图片',
  `introduceimage` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '""' COMMENT '商品介绍图片',
  `goodscategory` bigint(20) DEFAULT '0' COMMENT '商品品类',
  `goodsprice` bigint(20) DEFAULT '0' COMMENT '有规格按照规格计算无规格按照划线价去计算',
  `markprice` bigint(20) DEFAULT '0' COMMENT '划线价',
  `specifications` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '"{}"' COMMENT '价格规格信息',
  `goodsspecificdetails` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '"{}"' COMMENT '商品明细',
  `goodstype` int(11) DEFAULT '10' COMMENT '商品类型  10 单品 20 礼包 30 福袋',
  `deliveryarea` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '""' COMMENT '快递范围',
  `deliverycycleday` int(11) DEFAULT '0' COMMENT '发货周期 天',
  `deliverycyclehour` int(11) DEFAULT '0' COMMENT '发货周期 小时',
  `businessname` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '""' COMMENT '归属商家',
  `brand` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '""' COMMENT '品牌',
  `deliveryplace` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '""' COMMENT '发货地',
  `returnpolicy` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '""' COMMENT '退款退货说明',
  `goodsdetails` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '""' COMMENT '商品详情',
  `memberid` int(8) DEFAULT '-1' COMMENT '创建人ID',
  `createtime` bigint(20) DEFAULT '0' COMMENT '创建时间',
  `goodsstatus` int(11) DEFAULT '-1' COMMENT '商品状态 10上架 20 下架',
  `trafficvolume` bigint(20) DEFAULT '-1' COMMENT '访问量',
  `totalselesvolume` bigint(20) DEFAULT '-1' COMMENT '总销售量',
  `goodsno` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '商品编号 随机生成商品编号  MZ + 单号 + 9伪随机数',
  `goodslabels` varchar(100) DEFAULT '""' COMMENT '商品标签',
  PRIMARY KEY (`goodsid`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of goodsinfo
-- ----------------------------
INSERT INTO `goodsinfo` VALUES ('11', '阿迪商品2', '', 'ae.jpg', 'ae.jpg;ac.jpg;', '0', '0', '10001', null, null, '10', '湖北武汉', '1', '10', '商家a', '阿迪212', '', '不能退款!!!', '商品详情。。。。', '10000', '1572664724692', '10', '1001', '2001', 'MZ10604833942', null);
INSERT INTO `goodsinfo` VALUES ('12', 'AAAA', '', 'ae.jpg', 'ae.jpg;ac.jpg;', '0', '0', '10001', '{\"color\":\"红色\"}', '{\"color\":\"红色\"}', '10', '湖北武汉', '1', '10', '商家a', '阿迪222A', '发货地址', '不能退款!!!', '商品详情。。。。', '10000', '1572664770434', '10', '1001', '2001', 'MZ10763780935', 'A;B;C;');
INSERT INTO `goodsinfo` VALUES ('13', 'AAAA', '', 'ae.jpg', 'ae.jpg;ac.jpg;', '0', '0', '10001', '{\"color\":\"红色\"}', '{\"color\":\"红色\"}', '10', '湖北武汉', '1', '10', '商家a', '阿迪22A', '', '不能退款!!!', '商品详情。。。。', '10000', '1572664890920', '10', '1001', '2001', 'MZ10321478027', 'A;B;C;');

-- ----------------------------
-- Table structure for label
-- ----------------------------
DROP TABLE IF EXISTS `label`;
CREATE TABLE `label` (
  `labelid` int(20) NOT NULL AUTO_INCREMENT COMMENT '标签ID',
  `labelname` varchar(50) NOT NULL COMMENT '标签名称',
  `createtime` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `modifytime` bigint(20) DEFAULT NULL COMMENT '修改时间',
  `bindgoodsnum` bigint(20) DEFAULT NULL COMMENT '绑定商品个数',
  `memberId` int(8) DEFAULT NULL COMMENT '创建用户',
  PRIMARY KEY (`labelid`)
) ENGINE=InnoDB AUTO_INCREMENT=65 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of label
-- ----------------------------
INSERT INTO `label` VALUES ('6', '修改', '1572577747332', '1572609305470', '0', '0');
INSERT INTO `label` VALUES ('7', '张三', '1572577784940', '0', '0', '0');
INSERT INTO `label` VALUES ('8', '张三', '1572577822948', '0', '0', '0');
INSERT INTO `label` VALUES ('9', '张三', '1572577831613', '0', '0', '0');
INSERT INTO `label` VALUES ('10', '张三', '1572577933468', '0', '0', '0');
INSERT INTO `label` VALUES ('19', '杀杀杀', '1572578612772', '0', '0', '0');
INSERT INTO `label` VALUES ('20', '张三', '1572578826855', '0', '0', '0');
INSERT INTO `label` VALUES ('21', '张三', '1572578871952', '0', '0', '0');
INSERT INTO `label` VALUES ('26', '也一样', '1572579255030', '0', '0', '0');
INSERT INTO `label` VALUES ('27', '威威', '1572579498610', '0', '0', '0');
INSERT INTO `label` VALUES ('28', 'result', '1572579513069', '0', '0', '0');
INSERT INTO `label` VALUES ('29', '杀杀杀', '1572579636467', '0', '0', '0');
INSERT INTO `label` VALUES ('30', '顶顶顶', '1572579699269', '0', '0', '0');
INSERT INTO `label` VALUES ('31', '王炸', '1572587173331', '0', '0', '0');
INSERT INTO `label` VALUES ('32', '一层皮哦您拍哪怕', '1572587251461', '0', '0', '0');
INSERT INTO `label` VALUES ('33', '浑浑噩噩', '1572587321726', '0', '0', '0');
INSERT INTO `label` VALUES ('34', '水水水水', '1572587344301', '0', '0', '0');
INSERT INTO `label` VALUES ('35', '三分天下', '1572587353397', '0', '0', '0');
INSERT INTO `label` VALUES ('36', 'admin', '1572587624414', '0', '0', '0');
INSERT INTO `label` VALUES ('43', '王小二', '1572598172602', '1572690576521', '0', '0');
INSERT INTO `label` VALUES ('44', '里斯', '1572598724122', '0', '0', '0');
INSERT INTO `label` VALUES ('45', '王二', '1572599028899', '0', '0', '0');
INSERT INTO `label` VALUES ('46', '张思', '1572604529348', '1572609182501', '0', '0');
INSERT INTO `label` VALUES ('47', '4554', '1572607100451', '0', '0', '0');
INSERT INTO `label` VALUES ('48', '我去饿我去', '1572607273676', '0', '0', '0');
INSERT INTO `label` VALUES ('49', '大撒大撒', '1572607310428', '0', '0', '0');
INSERT INTO `label` VALUES ('50', '而我却', '1572607328716', '0', '0', '0');
INSERT INTO `label` VALUES ('51', '发生的', '1572607359588', '0', '0', '0');
INSERT INTO `label` VALUES ('52', '发士大夫', '1572607390865', '0', '0', '0');
INSERT INTO `label` VALUES ('53', '张三。。', '1572607413020', '0', '0', '0');
INSERT INTO `label` VALUES ('54', '张三', '1572607476194', '0', '0', '0');
INSERT INTO `label` VALUES ('55', '111', '1572607532406', '0', '0', '0');
INSERT INTO `label` VALUES ('56', '好丽友', '1572609098480', '0', '0', '0');
INSERT INTO `label` VALUES ('57', '张三', '1572609170893', '0', '0', '0');
INSERT INTO `label` VALUES ('58', '添加', '1572609287338', '0', '0', '0');
INSERT INTO `label` VALUES ('59', '顶顶顶顶', '1572609336423', '0', '0', '0');
INSERT INTO `label` VALUES ('60', '邓丽筠', '1572668846553', '0', '0', '0');
INSERT INTO `label` VALUES ('61', '大大大', '1572690086527', '0', '0', '0');
INSERT INTO `label` VALUES ('62', '杀杀杀', '1572690099136', '0', '0', '0');
INSERT INTO `label` VALUES ('63', '水水水水', '1572690108032', '0', '0', '0');
INSERT INTO `label` VALUES ('64', '11656516', '1572690116873', '0', '0', '0');

-- ----------------------------
-- Table structure for merchant
-- ----------------------------
DROP TABLE IF EXISTS `merchant`;
CREATE TABLE `merchant` (
  `merchantid` int(8) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `merchanttel` varchar(13) DEFAULT NULL COMMENT '商家电话',
  `salesscope` varchar(255) DEFAULT NULL COMMENT '销售范围',
  `goodsnum` bigint(20) DEFAULT NULL COMMENT '商品发布件数',
  `createtime` bigint(20) DEFAULT NULL COMMENT '入住时间',
  `password` varchar(100) DEFAULT NULL COMMENT '密码',
  KEY `merchantid` (`merchantid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of merchant
-- ----------------------------

-- ----------------------------
-- Table structure for noticerecord
-- ----------------------------
DROP TABLE IF EXISTS `noticerecord`;
CREATE TABLE `noticerecord` (
  `noticeid` varchar(64) NOT NULL DEFAULT '' COMMENT '消息ID 值=create36time(9位)+UUID(32位)',
  `userid` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户ID',
  `status` smallint(6) NOT NULL DEFAULT '0' COMMENT '状态; 20:未读;60:已读;70:过期',
  `appos` varchar(16) NOT NULL DEFAULT '' COMMENT 'APP的设备系统(小写); android/ios',
  `apptoken` varchar(128) NOT NULL DEFAULT '' COMMENT '设备推送ID',
  `content` varchar(4096) NOT NULL DEFAULT '' COMMENT '消息体',
  `resultdesc` varchar(4096) NOT NULL DEFAULT '' COMMENT '消息体',
  `createtime` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建时间',
  PRIMARY KEY (`noticeid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='消息推送表';

-- ----------------------------
-- Records of noticerecord
-- ----------------------------

-- ----------------------------
-- Table structure for payaction
-- ----------------------------
DROP TABLE IF EXISTS `payaction`;
CREATE TABLE `payaction` (
  `payactid` varchar(64) NOT NULL DEFAULT '' COMMENT '记录ID 值=create36time(9位)+UUID(32位)',
  `payno` varchar(128) NOT NULL DEFAULT '' COMMENT '支付编号',
  `paytype` smallint(6) NOT NULL DEFAULT '10' COMMENT '//支付类型:  10: 信用/虚拟支付; 11:人工支付; 12:银联支付; 13:微信支付; 14:支付宝支付;15:易宝支付;',
  `acturl` varchar(1024) NOT NULL DEFAULT '' COMMENT '请求的URL',
  `requestjson` varchar(2048) NOT NULL DEFAULT '' COMMENT '支付接口请求对象',
  `responsetext` varchar(5120) NOT NULL DEFAULT '' COMMENT '支付接口返回的原始结果',
  `createtime` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建时间，单位毫秒',
  PRIMARY KEY (`payactid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='支付接口结果表';

-- ----------------------------
-- Records of payaction
-- ----------------------------

-- ----------------------------
-- Table structure for payrecord
-- ----------------------------
DROP TABLE IF EXISTS `payrecord`;
CREATE TABLE `payrecord` (
  `payno` varchar(64) NOT NULL DEFAULT '' COMMENT '支付编号; 值=orderno+createtime36进制(9位)',
  `thirdpayno` varchar(128) NOT NULL DEFAULT '' COMMENT '第三方支付订单号',
  `appid` varchar(128) NOT NULL DEFAULT '' COMMENT '支付APP应用ID',
  `paytype` smallint(6) NOT NULL DEFAULT '10' COMMENT '//支付类型:  10: 信用/虚拟支付; 11:人工支付; 12:银联支付; 13:微信支付; 14:支付宝支付;15:易宝支付;',
  `payway` smallint(6) NOT NULL DEFAULT '10' COMMENT '支付渠道:  10: 信用/虚拟支付; 20:人工支付; 30:APP支付; 40:网页支付; 50:机器支付;',
  `userno` varchar(64) NOT NULL DEFAULT '' COMMENT '付款人用户信息',
  `paytitle` varchar(128) NOT NULL DEFAULT '' COMMENT '订单标题',
  `paybody` varchar(255) NOT NULL DEFAULT '' COMMENT '订单内容描述',
  `notifyurl` varchar(255) NOT NULL DEFAULT '' COMMENT '支付回调连接',
  `ordertype` smallint(6) NOT NULL DEFAULT '0' COMMENT '订单类型',
  `orderno` varchar(64) NOT NULL DEFAULT '' COMMENT '订单编号',
  `paystatus` smallint(6) NOT NULL DEFAULT '10' COMMENT '支付状态; 10:待支付; 30:已支付; 50:待退款; 70:已退款; 90:已关闭;',
  `payedmoney` bigint(20) NOT NULL DEFAULT '0' COMMENT '实际支付金额 单位人民币分；',
  `money` bigint(20) NOT NULL DEFAULT '0' COMMENT '订单金额，单位人民币分；',
  `requestjson` varchar(1024) NOT NULL DEFAULT '' COMMENT '支付接口请求对象',
  `responsetext` varchar(10240) NOT NULL DEFAULT '' COMMENT '支付接口返回的原始结果',
  `createtime` bigint(20) NOT NULL DEFAULT '0' COMMENT '支付开始时间，单位毫秒',
  `finishtime` bigint(20) NOT NULL DEFAULT '0' COMMENT '支付结束时间，单位毫秒',
  `clienthost` varchar(64) NOT NULL DEFAULT '' COMMENT '客户端请求的HOST',
  `clientaddr` varchar(128) NOT NULL DEFAULT '' COMMENT '客户端生成时的IP',
  PRIMARY KEY (`payno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='支付表';

-- ----------------------------
-- Records of payrecord
-- ----------------------------

-- ----------------------------
-- Table structure for randomcode
-- ----------------------------
DROP TABLE IF EXISTS `randomcode`;
CREATE TABLE `randomcode` (
  `randomcode` varchar(128) NOT NULL DEFAULT '' COMMENT '手机-验证码数据对',
  `userid` bigint(20) NOT NULL DEFAULT '0' COMMENT '[所属用户ID]',
  `type` smallint(5) NOT NULL DEFAULT '0' COMMENT '[类型]: 10:手机号码注册;20:短信重置密码;30:修改手机号码;40:用户验证码登录;50:发送原手机号码;60:邮件重置密码;70:更改邮箱绑定;',
  `createtime` bigint(20) NOT NULL DEFAULT '0' COMMENT '[创建时间]',
  PRIMARY KEY (`randomcode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of randomcode
-- ----------------------------

-- ----------------------------
-- Table structure for randomcodehis
-- ----------------------------
DROP TABLE IF EXISTS `randomcodehis`;
CREATE TABLE `randomcodehis` (
  `seqid` varchar(64) NOT NULL DEFAULT '' COMMENT '记录ID 值=create36time(9位)+UUID(32位)',
  `randomcode` varchar(128) NOT NULL DEFAULT '' COMMENT '[验证码]',
  `userid` bigint(20) NOT NULL DEFAULT '0' COMMENT '[所属用户ID]',
  `type` smallint(5) NOT NULL DEFAULT '0' COMMENT '[类型]: 10:手机号码注册;20:短信重置密码;30:修改手机号码; ;60:邮件重置密码;70:更改邮箱绑定;',
  `createtime` bigint(20) NOT NULL DEFAULT '0' COMMENT '[创建时间]',
  `retcode` int(11) NOT NULL DEFAULT '0' COMMENT '[结果]: 2: 过期; 4已处理;',
  `updatetime` bigint(20) NOT NULL DEFAULT '0' COMMENT '[更新时间]',
  PRIMARY KEY (`seqid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of randomcodehis
-- ----------------------------

-- ----------------------------
-- Table structure for region
-- ----------------------------
DROP TABLE IF EXISTS `region`;
CREATE TABLE `region` (
  `regionid` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '' COMMENT '区域名称',
  `parentcode` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '86' COMMENT '默认中国',
  `code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `regionlevel` int(2) DEFAULT '1' COMMENT '级别 1 国家 2 省 3 市  4 县',
  PRIMARY KEY (`regionid`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of region
-- ----------------------------
INSERT INTO `region` VALUES ('1', '湖北省', '-1', '42', '2');
INSERT INTO `region` VALUES ('2', '武汉市', '42', '4201', '3');
INSERT INTO `region` VALUES ('3', '荆门市', '42', '4208', '3');

-- ----------------------------
-- Table structure for smsrecord
-- ----------------------------
DROP TABLE IF EXISTS `smsrecord`;
CREATE TABLE `smsrecord` (
  `smsid` varchar(64) NOT NULL DEFAULT '' COMMENT '短信ID 值=create36time(9位)+UUID(32位)',
  `smstype` smallint(6) NOT NULL DEFAULT '0' COMMENT '短信类型; 10:验证码；20:营销短信；',
  `codetype` smallint(6) NOT NULL DEFAULT '0' COMMENT '验证码类型; 10:手机注册；20:重置密码；30:修改手机；40:登录；',
  `status` smallint(6) NOT NULL DEFAULT '10' COMMENT '状态; 10:未发送; 20:已发送; 30:发送失败;',
  `smscount` int(11) NOT NULL DEFAULT '0' COMMENT '群发的短信条数',
  `mobcount` int(11) NOT NULL DEFAULT '1' COMMENT '群发的手机号码数',
  `mobile` varchar(32) NOT NULL DEFAULT '' COMMENT '手机号码',
  `mobiles` varchar(2048) NOT NULL DEFAULT '' COMMENT '群发的手机号码集合，多个用;隔开,最多100条',
  `content` varchar(1024) NOT NULL DEFAULT '' COMMENT '短信内容',
  `resultdesc` varchar(1024) NOT NULL DEFAULT '' COMMENT '返回结果',
  `createtime` bigint(20) NOT NULL DEFAULT '0' COMMENT '生成时间，单位毫秒',
  PRIMARY KEY (`smsid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='短信发送记录表';

-- ----------------------------
-- Records of smsrecord
-- ----------------------------

-- ----------------------------
-- Table structure for userdetail
-- ----------------------------
DROP TABLE IF EXISTS `userdetail`;
CREATE TABLE `userdetail` (
  `userid` int(11) NOT NULL DEFAULT '0' COMMENT '[用户ID] 值从2_0000_0001开始; 36进制固定长度为6位',
  `account` varchar(128) NOT NULL DEFAULT '' COMMENT '[用户账号]',
  `username` varchar(128) NOT NULL DEFAULT '' COMMENT '[用户昵称]',
  `type` smallint(5) NOT NULL DEFAULT '0' COMMENT '[用户类型]',
  `password` varchar(128) NOT NULL DEFAULT '' COMMENT '密码',
  `mobile` varchar(128) NOT NULL DEFAULT '' COMMENT '[手机号码]',
  `mobnet` int(11) NOT NULL DEFAULT '0' COMMENT '运营商; 2:移动; 4:联通; 8:电信;',
  `email` varchar(128) NOT NULL DEFAULT '' COMMENT '[邮箱地址]',
  `wxunionid` varchar(255) NOT NULL DEFAULT '' COMMENT '微信openid',
  `qqopenid` varchar(255) NOT NULL DEFAULT '' COMMENT 'QQ openid',
  `appos` varchar(16) NOT NULL DEFAULT '' COMMENT 'APP的设备系统(小写); android/ios/web/wap',
  `apptoken` varchar(255) NOT NULL DEFAULT '' COMMENT 'APP的设备ID',
  `status` smallint(5) NOT NULL DEFAULT '0' COMMENT '[状态]: 10:正常;20:待审批;30:审批不通过;40:冻结;50:隐藏;60:关闭;70:过期;80:删除;',
  `gender` smallint(5) NOT NULL DEFAULT '0' COMMENT '[性别]：2：男； 4:女；',
  `updatetime` bigint(20) NOT NULL DEFAULT '0' COMMENT '[更新时间]',
  `remark` varchar(255) NOT NULL DEFAULT '' COMMENT '[备注]',
  `regtype` smallint(6) NOT NULL DEFAULT '0' COMMENT '[注册类型]: 10:账号注册; 20:手机注册; 30:邮箱注册; 40:微信注册; 50:QQ注册',
  `createtime` bigint(20) NOT NULL DEFAULT '0' COMMENT '[创建时间]',
  `regagent` varchar(255) NOT NULL DEFAULT '' COMMENT '[注册终端]',
  `regaddr` varchar(64) NOT NULL DEFAULT '' COMMENT '[注册IP]',
  PRIMARY KEY (`userid`),
  KEY `m` (`mobile`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户信息表';

-- ----------------------------
-- Records of userdetail
-- ----------------------------
INSERT INTO `userdetail` VALUES ('0', '', '', '0', '', '', '0', '', '', '', '', '', '0', '0', '0', '', '0', '0', '', '');
INSERT INTO `userdetail` VALUES ('1', 'yang5434321', '', '0', '7b18b90c9bf0e73cf4b876da92667cc67b8647eb', '', '0', '', '', '', '', '', '10', '0', '0', '', '10', '1572334615831', '', '');

-- ----------------------------
-- Table structure for usermember
-- ----------------------------
DROP TABLE IF EXISTS `usermember`;
CREATE TABLE `usermember` (
  `memberid` int(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `account` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '用户账号',
  `membername` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '用户昵称',
  `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '密码',
  `mobile` varchar(15) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '手机号码',
  PRIMARY KEY (`memberid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of usermember
-- ----------------------------
INSERT INTO `usermember` VALUES ('1', '13044889046', 'test001', '123456', '13044889046');

-- ----------------------------
-- Table structure for wishlistinfo
-- ----------------------------
DROP TABLE IF EXISTS `wishlistinfo`;
CREATE TABLE `wishlistinfo` (
  `wishlistid` int(8) NOT NULL AUTO_INCREMENT COMMENT '心愿清单id',
  `goodsid` int(8) DEFAULT NULL COMMENT '商品ID',
  `customerid` int(8) DEFAULT NULL COMMENT '客户ID',
  `createtime` bigint(20) DEFAULT NULL COMMENT '收藏时间',
  PRIMARY KEY (`wishlistid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of wishlistinfo
-- ----------------------------
