CREATE DATABASE reviewquality
  DEFAULT CHARSET = 'utf8';

USE reviewquality;

CREATE TABLE review (
  id BIGINT AUTO_INCREMENT PRIMARY KEY ,
  productId     VARCHAR(45) COMMENT '产品id',
  reviewerId    VARCHAR(45),
  createdAt     VARCHAR(45),
  helpfulCount  INT COMMENT '有用数',
  feedbackCount INT COMMENT '反馈数',
  rating        INT COMMENT 'rating',
  title         VARCHAR(300),
  body          TEXT
);

ALTER TABLE review ADD INDEX productId(productId);

ALTER TABLE review ADD INDEX reviewerId(reviewerId);

CREATE TABLE product (
  id BIGINT AUTO_INCREMENT PRIMARY KEY ,
  productId    VARCHAR(45),
  name         VARCHAR(300),
  type         VARCHAR(300),
  brand        VARCHAR(100),
  salesPrice   VARCHAR(45),
  listPrice    VARCHAR(45),
  description  TEXT,
  categoryPath TEXT
);

ALTER TABLE product ADD INDEX productId(productId);

CREATE TABLE reviewer (
  id BIGINT AUTO_INCREMENT PRIMARY KEY ,
  reviewerId VARCHAR(45),
  name       VARCHAR(300),
  reviews    INT,
  summary    TEXT
);

ALTER TABLE reviewer ADD INDEX reviewerId(reviewerId);
