CREATE SEQUENCE customer_seq INCREMENT BY 1;

CREATE TABLE customer
(
    id                  BIGINT          NOT NULL DEFAULT nextval('customer_seq') PRIMARY KEY,
    first_name          VARCHAR(255)    NOT NULL,
    last_name           VARCHAR(255)    NOT NULL,
    username            VARCHAR(255)    NOT NULL UNIQUE,
    email               VARCHAR(255)    NOT NULL UNIQUE,
    date_of_birth       DATE            NOT NULL,
    image_url           VARCHAR(255)    NOT NULL,
    registration_date   DATE            NOT NULL,
    last_login          TIMESTAMP       NOT NULL
);

CREATE SEQUENCE user_access_seq INCREMENT BY 1;

CREATE TABLE user_access
(
    id          BIGINT          NOT NULL DEFAULT nextval('user_access_seq') PRIMARY KEY,
    user_id     BIGINT          NOT NULL,
    email       VARCHAR(255)    NOT NULL,
    username    VARCHAR(255)    NOT NULL,
    password    VARCHAR(255)    NOT NULL,
    roles       TEXT            NOT NULL,
    locked      BOOLEAN         NOT NULL,
    question    TEXT            NOT NULL,
    answer      TEXT            NOT NULL,

    FOREIGN KEY (user_id) REFERENCES customer (id)
);

CREATE SEQUENCE user_address_seq INCREMENT BY 1;

CREATE TABLE user_address
(
    id              BIGINT          NOT NULL DEFAULT nextval('user_address_seq') PRIMARY KEY,
    user_id         BIGINT          NOT NULL,
    street_name     VARCHAR(255)    NOT NULL,
    street_number   VARCHAR(255)    NOT NULL,
    city            VARCHAR(255)    NOT NULL,
    state           VARCHAR(255)    NOT NULL,
    zip             VARCHAR(255)    NOT NULL,
    country         VARCHAR(255)    NOT NULL,
    latitude        VARCHAR(255)    NOT NULL,
    longitude       VARCHAR(255)    NOT NULL,
    type            VARCHAR(255)    NOT NULL,
    notes           VARCHAR(255)    NOT NULL,

    FOREIGN KEY (user_id) REFERENCES customer (id)
);

CREATE SEQUENCE confirmation_token_seq INCREMENT BY 1;

CREATE TABLE confirmation_token
(
    id              BIGINT          NOT NULL DEFAULT nextval('confirmation_token_seq') PRIMARY KEY,
    user_id         BIGINT          NOT NULL,
    token           VARCHAR(255)    NOT NULL,
    created_at      TIMESTAMP       NOT NULL,
    expired_at      TIMESTAMP       NOT NULL,
    confirmed_at    TIMESTAMP,

    FOREIGN KEY (user_id) REFERENCES customer (id)
);