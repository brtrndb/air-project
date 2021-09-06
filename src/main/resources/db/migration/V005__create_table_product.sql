CREATE TABLE IF NOT EXISTS svc_flightplan.product
(
    -- Default part.
    id         uuid        NOT NULL DEFAULT public.uuid_generate_v4(),
    created_at timestamptz NOT NULL DEFAULT NOW(),
    updated_at timestamptz NOT NULL DEFAULT NOW(),
    -- Custom part.
    product_id text        NOT NULL UNIQUE,
    name       text        NOT NULL,
    -- Constraints.
    PRIMARY KEY (id)
);
