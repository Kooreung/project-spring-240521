USE project2;

CREATE TABLE board
(
    id       INT PRIMARY KEY AUTO_INCREMENT,
    title    VARCHAR(100)  NOT NULL,
    content  VARCHAR(1000) NOT NULL,
    writer   VARCHAR(100)  NOT NULL,
    inserted DATETIME      NOT NULL DEFAULT NOW()
);

SELECT *
FROM board
ORDER BY id DESC;

CREATE TABLE member
(
    id        INT PRIMARY KEY AUTO_INCREMENT,
    email     VARCHAR(50) NOT NULL UNIQUE,
    password  VARCHAR(20) NOT NULL,
    nick_name VARCHAR(20) NOT NULL UNIQUE,
    inserted  DATETIME    NOT NULL DEFAULT NOW()
);

SELECT *
FROM member
ORDER BY id DESC;