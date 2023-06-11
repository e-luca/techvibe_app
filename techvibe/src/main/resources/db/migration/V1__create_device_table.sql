CREATE SEQUENCE device_seq INCREMENT BY 1;

CREATE TABLE device
(
    id                  BIGINT          DEFAULT nextval('device_seq') PRIMARY KEY,
    name                VARCHAR(255)    NOT NULL,
    type                VARCHAR(255)    NOT NULL,
    short_description   VARCHAR(255)    NOT NULL,
    long_description    VARCHAR         NOT NULL,
    price               NUMERIC         NOT NULL,
    available           BOOLEAN         NOT NULL,
    image_url           VARCHAR(255)    NOT NULL
);