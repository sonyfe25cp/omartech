create table proxy(
  id int not null auto_increment primary key,
  host varchar(45),
  port int not null,
  proxyType varchar(45) comment 'http, https, socks',
  status int not null default 0 comment '状态，0：可用；1：不可用',
  created_at datetime not null,
  COMMENT varchar(45) COMMENT '备注类型',
  latency BIGINT
);