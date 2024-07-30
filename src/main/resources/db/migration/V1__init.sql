CREATE TABLE IF NOT EXISTS member (
    id              BIGINT AUTO_INCREMENT                               NOT NULL,
    email           VARCHAR(255)                                        NOT NULL,
    password        VARCHAR(255)                                        NOT NULL,
    name            VARCHAR(255)                                        NOT NULL,
    role            ENUM ('APPLICANT', 'ADMIN', 'MEMBER')               NOT NULL,
    part            ENUM ('PLAN', 'DESIGN', 'ANDROID', 'WEB', 'SERVER') NOT NULL,
    generation      TINYINT                                             NOT NULL DEFAULT 1 CHECK (generation >= 1),
    avatar_image    TEXT                                                NULL,
    created_date    DATETIME                                            NOT NULL,
    modified_date   DATETIME                                            NOT NULL,
    status          ENUM ('DELETE','ACTIVE')                            NOT NULL,
    CONSTRAINT pk_member PRIMARY KEY (id)
) engine=InnoDB;

CREATE TABLE IF NOT EXISTS refresh_token (
    member_email    VARCHAR(255)                NOT NULL,
    refresh_token   TEXT                        NOT NULL,
    created_date    DATETIME                    NOT NULL,
    modified_date   DATETIME                    NOT NULL,
    status          ENUM ('DELETE','ACTIVE')    NOT NULL,
    CONSTRAINT pk_refresh_token PRIMARY KEY (member_email)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS block (
    id              BIGINT AUTO_INCREMENT       NOT NULL,
    post_id         BIGINT                      NOT NULL,
    content         TEXT                        NULL,
    sequence        INT                         NOT NULL DEFAULT 1 CHECK (sequence >= 1),
    is_image        BOOLEAN                     NOT NULL,
    created_date    DATETIME                    NOT NULL,
    modified_date   DATETIME                    NOT NULL,
    status          ENUM ('DELETE','ACTIVE')    NOT NULL,
    CONSTRAINT pk_block PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS comment (
    id                 BIGINT AUTO_INCREMENT        NOT NULL,
    parent_comment_id  BIGINT                       NULL,
    post_id            BIGINT                       NOT NULL,
    content            TEXT                         NOT NULL,
    nickname           VARCHAR(255)                 NULL,
    depth              INT                          NOT NULL DEFAULT 1 CHECK (depth >= 1),
    created_date       DATETIME                     NOT NULL,
    modified_date      DATETIME                     NOT NULL,
    status             ENUM ('DELETE','ACTIVE')     NOT NULL,
    CONSTRAINT pk_comment PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS likes (
    id                 BIGINT AUTO_INCREMENT        NOT NULL,
    member_id          BIGINT                       NOT NULL,
    post_id            BIGINT                       NOT NULL,
    created_date       DATETIME                     NOT NULL,
    modified_date      DATETIME                     NOT NULL,
    status             ENUM ('DELETE','ACTIVE')     NOT NULL,
    CONSTRAINT pk_likes PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS post (
    id              BIGINT AUTO_INCREMENT       NOT NULL,
    member_id       BIGINT                      NOT NULL,
    title           VARCHAR(255)                NULL,
    like_count      INT                         DEFAULT 0 CHECK (like_count >= 0),
    scrap_count     INT                         DEFAULT 0 CHECK (scrap_count >= 0),
    view_count      INT                         DEFAULT 0 CHECK (view_count >= 0),
    stage           ENUM ('TEMP', 'PUBLISHED')  NOT NULL,
    created_date    DATETIME                    NOT NULL,
    modified_date   DATETIME                    NOT NULL,
    status          ENUM ('DELETE','ACTIVE')    NOT NULL,
    CONSTRAINT pk_post PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS scrap (
    id              BIGINT                   NOT NULL AUTO_INCREMENT,
    member_id       BIGINT                   NOT NULL,
    post_id         BIGINT                   NOT NULL,
    created_date    DATETIME                 NOT NULL,
    modified_date   DATETIME                 NOT NULL,
    status          ENUM ('DELETE','ACTIVE') NOT NULL,
    CONSTRAINT pk_scrap PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS tag (
    id              BIGINT                   NOT NULL AUTO_INCREMENT,
    name            VARCHAR(255)             NOT NULL,
    created_date    DATETIME                 NOT NULL,
    modified_date   DATETIME                 NOT NULL,
    status          ENUM ('DELETE','ACTIVE') NOT NULL,
    CONSTRAINT pk_tag_id PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS tagging (
    id              BIGINT                   NOT NULL AUTO_INCREMENT,
    post_id         BIGINT                   NOT NULL,
    tag_id          BIGINT                   NOT NULL,
    created_date    DATETIME                 NOT NULL,
    modified_date   DATETIME                 NOT NULL,
    status          ENUM ('DELETE','ACTIVE') NOT NULL,
    CONSTRAINT pk_tagging_id PRIMARY KEY (id)
) ENGINE=InnoDB;


ALTER TABLE member
    ADD CONSTRAINT uk_member_email UNIQUE (email);

ALTER TABLE likes
    ADD CONSTRAINT uq_likes UNIQUE (member_id, post_id);

ALTER TABLE scrap
    ADD CONSTRAINT uq_scrap UNIQUE (member_id, post_id);

ALTER TABLE tag
    ADD CONSTRAINT uq_tag_name UNIQUE (name);

# block
ALTER TABLE block
    ADD CONSTRAINT fk_block_to_post
        FOREIGN KEY (post_id)
            REFERENCES post (id);

# comment
ALTER TABLE comment
    ADD CONSTRAINT fk_comment_to_parent_comment
        FOREIGN KEY (parent_comment_id)
            REFERENCES comment (id);

ALTER TABLE comment
    ADD CONSTRAINT fk_comment_to_post
        FOREIGN KEY (post_id)
            REFERENCES post (id);

# likes
ALTER TABLE likes
    ADD CONSTRAINT fk_likes_to_member
        FOREIGN KEY (member_id)
            REFERENCES member (id);

ALTER TABLE likes
    ADD CONSTRAINT fk_likes_to_post
        FOREIGN KEY (post_id)
            REFERENCES post (id);

# post
ALTER TABLE post
    ADD CONSTRAINT fk_post_to_member
        FOREIGN KEY (member_id)
            REFERENCES member (id);

# scrap
ALTER TABLE scrap
    ADD CONSTRAINT fk_scrap_to_member
        FOREIGN KEY (member_id)
            REFERENCES member (id);

ALTER TABLE scrap
    ADD CONSTRAINT fk_scrap_to_post
        FOREIGN KEY (post_id)
            REFERENCES post (id);

# tagging
ALTER TABLE tagging
    ADD CONSTRAINT fk_tagging_to_post
        FOREIGN KEY (post_id)
            REFERENCES post (id);

ALTER TABLE tagging
    ADD CONSTRAINT fk_tagging_to_tag
        FOREIGN KEY (tag_id)
            REFERENCES tag (id);
