CREATE DATABASE weixin
  DEFAULT CHARSET = 'utf8';
USE weixin;

create table rule(
  id int not null AUTO_INCREMENT,
  ruleType VARCHAR(45) COMMENT '过滤器类型',
  priority INT COMMENT '优先级，越小越靠前',
  PRIMARY KEY (id)
);

CREATE TABLE rules (
  id         INT NOT NULL AUTO_INCREMENT,
  ruleType VARCHAR(45) COMMENT '过滤器类型',
  keyword    VARCHAR(45) COMMENT '关键词',
  replyId    INT COMMENT '反馈id',
  PRIMARY KEY (id)
);

CREATE TABLE replys (
  id          INT NOT NULL AUTO_INCREMENT,
  replyEnum   VARCHAR(45) COMMENT '反馈类型: 链接、文章',
  title       VARCHAR(200) COMMENT '文章标题',
  description VARCHAR(300) COMMENT '文章描述',
  url         VARCHAR(300) COMMENT '文章链接',
  picUrl VARCHAR(300) COMMENT '图片链接',
  content varchar(500) COMMENT '内容',
  array VARCHAR(200) COMMENT '多图文类型, id,id,',
  PRIMARY KEY (id)
);

CREATE TABLE logs (
  id     INT          NOT NULL AUTO_INCREMENT,
  uid    VARCHAR(100) NOT NULL
  COMMENT 'userdi',
  query  VARCHAR(500) NOT NULL
  COMMENT 'query',
  source VARCHAR(100) NOT NULL
  COMMENT '来源',
  PRIMARY KEY (id)
);

CREATE TABLE users (
  id             INT          NOT NULL AUTO_INCREMENT,
  uid            VARCHAR(100) NOT NULL,
  subscribe      INT          NOT NULL
  COMMENT '用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息',
  nickname       VARCHAR(200) NOT NULL,
  sex            INT          NOT NULL
  COMMENT '用户的性别，值为1时是男性，值为2时是女性，值为0时是未知',
  city           VARCHAR(100),
  country        VARCHAR(100),
  province       VARCHAR(100),
  language       VARCHAR(50) COMMENT '用户的语言，简体中文为zh_CN',
  headimgurl     VARCHAR(200) COMMENT '用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空',
  subscribe_time INT COMMENT '用户关注时间，为时间戳。',
  unionid        VARCHAR(200),
  PRIMARY KEY (id)
);
