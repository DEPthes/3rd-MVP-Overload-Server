ALTER TABLE comment
    ADD COLUMN member_id BIGINT NULL;

ALTER TABLE comment
    ADD CONSTRAINT fk_comment_to_member
        FOREIGN KEY (member_id)
            REFERENCES member (id);