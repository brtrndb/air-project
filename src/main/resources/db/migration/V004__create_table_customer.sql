CREATE TABLE IF NOT EXISTS svc_flightplan.customer
(
    -- Default part.
    id          uuid        NOT NULL DEFAULT public.uuid_generate_v4(),
    created_at  timestamptz NOT NULL DEFAULT NOW(),
    updated_at  timestamptz NOT NULL DEFAULT NOW(),
    -- Custom part.
    customer_id text        NOT NULL UNIQUE,
    pos_x       float       NOT NULL,
    pos_y       float       NOT NULL,
    -- Constraints.
    PRIMARY KEY (id)
);
