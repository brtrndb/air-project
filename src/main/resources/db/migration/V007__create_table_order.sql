CREATE TABLE IF NOT EXISTS svc_flightplan.order
(
    -- Default part.
    id          uuid        NOT NULL DEFAULT public.uuid_generate_v4(),
    created_at  timestamptz NOT NULL DEFAULT NOW(),
    updated_at  timestamptz NOT NULL DEFAULT NOW(),
    -- Custom part.
    order_id    text        NOT NULL UNIQUE,
    customer_id text        NOT NULL,
    -- Constraints.
    UNIQUE (order_id, customer_id),
    FOREIGN KEY (customer_id) REFERENCES svc_flightplan.customer (customer_id)
);
