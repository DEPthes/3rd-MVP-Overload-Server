ALTER TABLE member
    DROP COLUMN avatar_image;

ALTER TABLE member
    ADD COLUMN avatar_face VARCHAR(255) NULL;

ALTER TABLE member
    ADD COLUMN avatar_body VARCHAR(255) NULL;

ALTER TABLE member
    ADD COLUMN avatar_eyes VARCHAR(255) NULL;

ALTER TABLE member
    ADD COLUMN avatar_nose VARCHAR(255) NULL;

ALTER TABLE member
    ADD COLUMN avatar_mouth VARCHAR(255) NULL;