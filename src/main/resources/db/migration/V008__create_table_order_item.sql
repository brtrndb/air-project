CREATE TABLE IF NOT EXISTS svc_flightplan.order_item
(
    -- Custom part.
    order_id   text    NOT NULL,
    product_id text    NOT NULL,
    quantity   integer NOT NULL,
    -- Constraints.
    UNIQUE (order_id, product_id),
    FOREIGN KEY (order_id) REFERENCES svc_flightplan.order (order_id),
    FOREIGN KEY (product_id) REFERENCES svc_flightplan.product (product_id)
);
