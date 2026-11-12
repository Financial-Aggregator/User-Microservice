CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TYPE user_status AS ENUM ('ACTIVE', 'INACTIVE', 'BLOCKED');

CREATE TABLE users (
                       user_id        UUID         PRIMARY KEY DEFAULT gen_random_uuid(),
                       first_name     VARCHAR(100) NOT NULL,
                       last_name      VARCHAR(100) NOT NULL,
                       middle_name    VARCHAR(100),
                       status         user_status,
                       inn            INTEGER
);

CREATE TABLE users_contacts (
                                id           UUID         PRIMARY KEY DEFAULT gen_random_uuid(),
                                phone_number VARCHAR(20),
                                email        VARCHAR(255),
                                user_id      UUID         NOT NULL UNIQUE REFERENCES users(user_id) ON DELETE CASCADE
);

CREATE TABLE users_addresses (
                                 id           UUID         PRIMARY KEY DEFAULT gen_random_uuid(),
                                 index        INTEGER,
                                 reg_address  VARCHAR(500),
                                 fact_address VARCHAR(500),
                                 user_id      UUID         NOT NULL UNIQUE REFERENCES users(user_id) ON DELETE CASCADE
);

CREATE TABLE users_documents (
                                 id               UUID    PRIMARY KEY DEFAULT gen_random_uuid(),
                                 inn              INTEGER,
                                 passport_series  INTEGER,
                                 passport_number  INTEGER,
                                 user_id          UUID    NOT NULL UNIQUE REFERENCES users(user_id) ON DELETE CASCADE
);