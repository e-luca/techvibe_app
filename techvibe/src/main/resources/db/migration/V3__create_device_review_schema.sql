CREATE SEQUENCE device_review_seq INCREMENT BY 1;

CREATE TABLE device_review
(
    id          BIGINT      NOT NULL DEFAULT nextval('device_review_seq') PRIMARY KEY,
    device_id   BIGINT      NOT NULL,
    user_id     BIGINT      NOT NULL,
    rating      INT         NOT NULL,
    favorite    BOOLEAN     NOT NULL,
    comment     TEXT        NOT NULL,
    created     TIMESTAMP   NOT NULL,
    updated     TIMESTAMP   NOT NULL,

    FOREIGN KEY (device_id) REFERENCES device (id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES customer (id) ON DELETE CASCADE
);
