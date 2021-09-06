CREATE TABLE IF NOT EXISTS svc_flightplan.stock
(
    -- Default part.
    id         uuid        NOT NULL DEFAULT public.uuid_generate_v4(),
    created_at timestamptz NOT NULL DEFAULT NOW(),
    updated_at timestamptz NOT NULL DEFAULT NOW(),
    -- Custom part.
    store_id   text,
    product_id text,
    quantity   integer     NOT NULL,
    -- Constraints.
    PRIMARY KEY (id),
    FOREIGN KEY (store_id) REFERENCES svc_flightplan.store (store_id),
    FOREIGN KEY (product_id) REFERENCES svc_flightplan.product (product_id)
);
