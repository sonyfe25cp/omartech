CREATE TABLE images (
  id                 BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
  col                VARCHAR(45)  NOT NULL,
  description        VARCHAR(100) NOT NULL,
  tags               VARCHAR(200) NOT NULL,
  date               VARCHAR(45)  NOT NULL,
  downloadUrl        VARCHAR(500) NOT NULL,
  imageUrl           VARCHAR(500) NOT NULL,
  imageWidth         INT,
  imageHeight        INT,
  thumbnailUrl       VARCHAR(500) NOT NULL,
  thumbnailWidth     INT,
  thumbnailHeight    INT,
  thumbLargeUrl      VARCHAR(500) NOT NULL,
  thumbLargeWidth    INT,
  thumbLargeHeight   INT,
  thumbLargeTnUrl    VARCHAR(500) NOT NULL,
  thumbLargeTnWidth  INT,
  thumbLargeTnHeight INT,
  siteName           VARCHAR(100),
  siteLogo           VARCHAR(200),
  siteUrl            VARCHAR(500),
  fromUrl            VARCHAR(500),
  objUrl             VARCHAR(500),
  shareUrl           VARCHAR(500),
  userId             VARCHAR(100),
  isVip              INT,
  objTag             VARCHAR(45),
  hostName           VARCHAR(100),
  pictureId          VARCHAR(100),
  title              VARCHAR(100)
);

CREATE TABLE mokoUser (
  id     INT NOT NULL PRIMARY KEY,
  name   VARCHAR(45),
  gender INT,
  tags   VARCHAR(150)
);

create table mokoPic(
  id int not null,
  thumbUrl varchar(200),
  downloadUrl varchar(200)
);