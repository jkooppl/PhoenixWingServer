CREATE TABLE product_outage (
    id integer DEFAULT nextval('product_outage_id_seq'::text) NOT NULL,
    product_id integer not null,
    shop_id integer not null,
    PRIMARY KEY(id)
);

ALTER TABLE public.product_outage OWNER TO iqueso;

CREATE SEQUENCE product_outage_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.product_outage_id_seq OWNER TO iqueso;

ALTER SEQUENCE product_outage_id_seq OWNED BY product_outage.id;

ALTER TABLE ONLY product_outage
    ADD CONSTRAINT product_outage_product_shop_key UNIQUE (product_id, shop_id);