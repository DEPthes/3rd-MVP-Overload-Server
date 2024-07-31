-- block 테이블에 대한 외래 키 제약 조건 삭제
ALTER TABLE block
    DROP FOREIGN KEY fk_block_to_post;

-- block 테이블 삭제
DROP TABLE IF EXISTS block;

-- post 테이블에 새로운 컬럼 추가
ALTER TABLE post
    ADD COLUMN content         TEXT         NULL,
    ADD COLUMN preview_content VARCHAR(255) NULL,
    ADD COLUMN preview_image   TEXT         NULL;