CREATE TABLE holiday_hours (
    id integer DEFAULT nextval('holiday_hours_id_seq'::text) NOT NULL,
    holiday date not null,
    open int not null,
    close int not null,
    PRIMARY KEY(id)
);

ALTER TABLE public.holiday_hours OWNER TO iqueso;

CREATE SEQUENCE holiday_hours_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.holiday_hours_id_seq OWNER TO iqueso;

ALTER SEQUENCE holiday_hours_id_seq OWNED BY holiday_hours.id;

ALTER TABLE ONLY holiday_hours
    ADD CONSTRAINT holiday_hours_u_date_key UNIQUE (holiday);

    
CREATE TABLE holiday_hours_city_exception (
    id integer DEFAULT nextval('holiday_hours_city_exception_id_seq'::text) NOT NULL,
    holiday_hours_id int not null,
    city_id int not null,
    PRIMARY KEY(id)
);

ALTER TABLE public.holiday_hours_city_exception OWNER TO iqueso;

CREATE SEQUENCE holiday_hours_city_exception_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.holiday_hours_city_exception_id_seq OWNER TO iqueso;

ALTER SEQUENCE holiday_hours_city_exception_id_seq OWNED BY holiday_hours_city_exception.id;