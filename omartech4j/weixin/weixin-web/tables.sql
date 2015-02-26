CREATE TABLE weixinAccount (
  id             INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
  title          VARCHAR(200) NOT NULL,
  name           VARCHAR(200) NOT NULL,
  logo           VARCHAR(300) NOT NULL,
  erweima        VARCHAR(300) NOT NULL,
  description    VARCHAR(500),
  weixinrenzheng VARCHAR(500),
  isOffical      INT          NOT NULL,
  isLive         INT          NOT NULL,
  openId         VARCHAR(100) NOT NULL,
  createdAt      VARCHAR(45)
);

ALTER TABLE weixinAccount ADD INDEX openId(openId);

CREATE TABLE weixinPost (
  id         INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
  title      VARCHAR(100) NOT NULL,
  headImg    VARCHAR(300) NOT NULL,
  url        VARCHAR(300) NOT NULL,
  imgLink    VARCHAR(300) NOT NULL,
  siteLink   VARCHAR(300),
  content168 VARCHAR(500),
  content    TEXT,
  date7      VARCHAR(100) NOT NULL,
  html       LONGTEXT,
  readCount  INT,
  voteCount  INT,
  openId     VARCHAR(100) NOT NULL,
  createdAt  VARCHAR(45),
  updatedAt  VARCHAR(45)
);

ALTER TABLE weixinPost ADD INDEX openId(openId);