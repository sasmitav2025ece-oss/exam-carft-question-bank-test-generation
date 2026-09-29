CREATE DATABASE IF NOT EXISTS examcraft;
USE examcraft;

-- The application uses JPA ddl-auto=update to create/update tables.
-- This file is a reference matching the UML/ER design.

-- users
-- id BIGINT PK
-- name VARCHAR
-- email VARCHAR UNIQUE
-- password VARCHAR
-- role VARCHAR

-- topics
-- id BIGINT PK
-- name VARCHAR UNIQUE
-- description TEXT

-- questions
-- id BIGINT PK
-- question_text TEXT
-- topic_id BIGINT FK -> topics.id
-- difficulty VARCHAR
-- correct_option VARCHAR
-- created_at TIMESTAMP

-- question_options
-- question_id BIGINT FK -> questions.id
-- option_text VARCHAR

-- test_papers
-- id BIGINT PK
-- title VARCHAR
-- total_questions INT
-- difficulty_distribution VARCHAR
-- created_at TIMESTAMP

-- test_paper_questions
-- id BIGINT PK
-- paper_id BIGINT FK -> test_papers.id
-- question_id BIGINT FK -> questions.id
-- sequence_no INT
-- UNIQUE(paper_id, question_id)

-- test_attempts
-- id BIGINT PK
-- user_id BIGINT FK -> users.id
-- test_paper_id BIGINT FK -> test_papers.id
-- start_time TIMESTAMP
-- end_time TIMESTAMP
-- score DECIMAL
