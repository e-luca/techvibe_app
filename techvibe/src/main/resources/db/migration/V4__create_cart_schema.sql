CREATE SEQUENCE cart_seq INCREMENT BY 1;

CREATE TABLE cart
(
    id              BIGINT      DEFAULT nextval('cart_seq') PRIMARY KEY,
    user_id         BIGINT      NOT NULL,
    total_price     NUMERIC     NOT NULL,
    quantity        INT         NOT NULL,
    checked_out     BOOLEAN     NOT NULL,
    created         TIMESTAMP   NOT NULL,

    FOREIGN KEY (user_id) REFERENCES customer (id) ON DELETE CASCADE
);

CREATE SEQUENCE cart_item_seq INCREMENT BY 1;

CREATE TABLE cart_item
(
    id          BIGINT      DEFAULT nextval('cart_item_seq') PRIMARY KEY,
    cart_id     BIGINT      NOT NULL,
    item_id     BIGINT      NOT NULL,
    quantity    INT         NOT NULL,
    updated     TIMESTAMP   NOT NULL,

    FOREIGN KEY (cart_id) REFERENCES cart (id) ON DELETE CASCADE,
    FOREIGN KEY (item_id) REFERENCES device (id) ON DELETE CASCADE
);