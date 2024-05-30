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

UPDATE member
SET nick_name = 'efgh'
WHERE id = 19;

UPDATE board
SET member_id = 18
WHERE id % 2 = 0;

UPDATE board
SET member_id = 19
WHERE id % 2 = 1;

UPDATE board
SET title   = 'abc def',
    content = 'ghi jkl'
WHERE id % 3 = 0;
UPDATE board
SET title   = 'mno pqr',
    content = 'stu vwx'
WHERE id % 3 = 1;
UPDATE board
SET title   = 'yz1 234',
    content = '567 890'
WHERE id % 3 = 2;

CREATE TABLE board_file
(
    board_id INT          NOT NULL REFERENCES board (id),
    name     VARCHAR(500) NOT NULL,
    PRIMARY KEY (board_id, name)
);

SELECT *
FROM board_file;

CREATE TABLE board_like
(
    board_id  INT NOT NULL REFERENCES board (id),
    member_id INT NOT NULL REFERENCES member (id),
    PRIMARY KEY (board_id, member_id)
);

SELECT b.id, COUNT(DISTINCT f.name), COUNT(DISTINCT l.member_id)
FROM board b
         JOIN member m ON b.member_id = m.id
         LEFT JOIN board_file f ON b.id = f.board_id
         LEFT JOIN board_like l ON b.id = l.board_id
WHERE b.id = 5;


CREATE TABLE comment
(
    id        INT PRIMARY KEY KEY AUTO_INCREMENT,
    board_id  INT          NOT NULL REFERENCES board (id),
    member_id INT          NOT NULL REFERENCES member (id),
    comment   VARCHAR(500) NOT NULL,
    inserted  DATETIME     NOT NULL DEFAULT NOW()
);

SELECT *
FROM comment;