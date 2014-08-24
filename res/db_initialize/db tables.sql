
CREATE TABLE user (
  user_id int(12) NOT NULL auto_increment,
  email varchar(50) NOT NULL,
  nickname varchar(50) default NULL,
  password varchar(50) NOT NULL,
  is_email_verify char(3),
  email_verify_code varchar(50) default NULL,
  last_login_time bigint default NULL,
  last_login_ip varchar(15) default NULL,
  USER_PIC varchar(100),
  PRIMARY KEY  (USER_ID),
  UNIQUE KEY email (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE word (
  word varchar(50) NOT NULL,
  word_sen_en varchar(180),
  word_sen_cn varchar(100),
  word_pron varchar(40),
  word_mean varchar(100),
  word_type int(6) default 0,
  word_book int(6) default 0,
  PRIMARY KEY  (word)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE user_word (
  user_id int(12) NOT NULL auto_increment,
  word varchar(50) NOT NULL,
  last_time bigint default NULL,
  stage int(6) default 0,
  PRIMARY KEY  (user_id,word)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


