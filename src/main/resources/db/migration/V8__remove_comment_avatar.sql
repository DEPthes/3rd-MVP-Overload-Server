ALTER TABLE comment
    DROP COLUMN avatar_image;

ALTER TABLE comment
    ADD COLUMN use_nickname BOOLEAN NOT NULL;