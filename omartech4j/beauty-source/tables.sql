CREATE TABLE taotu (
  id       INT NOT NULL AUTO_INCREMENT,
  meizi_id INT NOT NULL,
  title    VARCHAR(200) DEFAULT "",
  total    INT          DEFAULT 0,
  PRIMARY KEY (id)
);

ALTER TABLE taotu ADD INDEX meizi_id(meizi_id);

ALTER TABLE taotu ADD CONSTRAINT t_unique_key UNIQUE (meizi_id);