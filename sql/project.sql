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

ALTER TABLE board
    DROP COLUMN writer;

ALTER TABLE board
    ADD COLUMN member_id INT REFERENCES member (id) AFTER content;

UPDATE board
SET member_id = (SELECT id FROM member ORDER BY id DESC LIMIT 1)
WHERE id > 0;

ALTER TABLE board
    MODIFY COLUMN member_id INT NOT NULL;

# 권한 테이블
CREATE TABLE authority
(
    member_id      INT         NOT NULL REFERENCES member (id),
    authority_name VARCHAR(20) NOT NULL,
    PRIMARY KEY (member_id, authority_name)
);

INSERT INTO authority
VALUES (17, 'admin');

SELECT *
FROM authority;

INSERT INTO board
    (title, content, member_id)
SELECT title, content, member_id
FROM board;