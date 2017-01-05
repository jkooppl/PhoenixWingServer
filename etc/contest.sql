CREATE TABLE iq2_contest (
    id integer DEFAULT nextval('iq2_contest_id_seq'::text) NOT NULL,
    name character varying(50) NOT NULL,
    start_date date not null,
    end_date date not null,
    cities character varying(256) default 0,
    active boolean not null default false,
    PRIMARY KEY(id)
);

ALTER TABLE public.iq2_contest OWNER TO iqueso;

CREATE SEQUENCE iq2_contest_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.iq2_contest_id_seq OWNER TO iqueso;

ALTER SEQUENCE iq2_contest_id_seq OWNED BY iq2_contest.id;

ALTER TABLE ONLY iq2_contest
    ADD CONSTRAINT iq2_contest_u_name_key UNIQUE (name);

    
    
CREATE TABLE iq2_contest_entry (
    id integer DEFAULT nextval('iq2_contest_entry_id_seq'::text) NOT NULL,
    contest_id integer not null,
    online_id integer not null,
    order_id integer,
    phone character varying(10) not null,
    entry_date date not null default now(),
    PRIMARY KEY(id),
    CONSTRAINT contest_exists FOREIGN KEY (contest_id) REFERENCES iq2_contest (id),
    CONSTRAINT customer_exists FOREIGN KEY (online_id) REFERENCES iq2_online_customer (online_id)
);

ALTER TABLE public.iq2_contest_entry OWNER TO iqueso;

CREATE SEQUENCE iq2_contest_entry_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.iq2_contest_entry_id_seq OWNER TO iqueso;

ALTER SEQUENCE iq2_contest_entry_id_seq OWNED BY iq2_contest_entry.id;

ALTER TABLE ONLY iq2_contest_entry
    ADD CONSTRAINT iq2_contest_entry_u_contestentry_key UNIQUE (contest_id,online_id, entry_date);
    
    
ALTER TABLE iq2_online_customer ADD COLUMN created_on timestamp with time zone not null default now(); 

insert into iq2_contest VALUES (1,'New Year''s Eve Flames Game & Pizza Party', '2010-12-06', '2010-12-22','2,3,5,12,16,23');