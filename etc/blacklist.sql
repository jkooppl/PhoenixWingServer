CREATE TABLE iq2_blacklist (
    id integer DEFAULT nextval('iq2_blacklist_id_seq'::text) NOT NULL,
    phone_number character varying(50) NOT NULL
);

ALTER TABLE public.iq2_blacklist OWNER TO iqueso;

CREATE SEQUENCE iq2_blacklist_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.iq2_blacklist_id_seq OWNER TO iqueso;

ALTER SEQUENCE iq2_blacklist_id_seq OWNED BY iq2_blacklist.id;

ALTER TABLE ONLY iq2_blacklist
    ADD CONSTRAINT iq2_blacklist_phone_key UNIQUE (phone_number);

INSERT INTO iq2_blacklist (phone_number) VALUES ('7801234567');
INSERT INTO iq2_blacklist (phone_number) VALUES ('4032558277');
INSERT INTO iq2_blacklist (phone_number) VALUES ('4032004449');
INSERT INTO iq2_blacklist (phone_number) VALUES ('7804775416');
INSERT INTO iq2_blacklist (phone_number) VALUES ('7808636431');
INSERT INTO iq2_blacklist (phone_number) VALUES ('4038628975');
INSERT INTO iq2_blacklist (phone_number) VALUES ('4032073204');
INSERT INTO iq2_blacklist (phone_number) VALUES ('7809680504');
INSERT INTO iq2_blacklist (phone_number) VALUES ('7804185153');
INSERT INTO iq2_blacklist (phone_number) VALUES ('4039486881');
INSERT INTO iq2_blacklist (phone_number) VALUES ('4038364681');
INSERT INTO iq2_blacklist (phone_number) VALUES ('7802651314');
INSERT INTO iq2_blacklist (phone_number) VALUES ('7802224046');
INSERT INTO iq2_blacklist (phone_number) VALUES ('7807480274');
INSERT INTO iq2_blacklist (phone_number) VALUES ('7805544179');
INSERT INTO iq2_blacklist (phone_number) VALUES ('7802365772');
INSERT INTO iq2_blacklist (phone_number) VALUES ('4032567610');
INSERT INTO iq2_blacklist (phone_number) VALUES ('7802991295');
INSERT INTO iq2_blacklist (phone_number) VALUES ('7062499030');
INSERT INTO iq2_blacklist (phone_number) VALUES ('7807166070');
INSERT INTO iq2_blacklist (phone_number) VALUES ('4037089144');
INSERT INTO iq2_blacklist (phone_number) VALUES ('7807166070');
INSERT INTO iq2_blacklist (phone_number) VALUES ('7802322439');
INSERT INTO iq2_blacklist (phone_number) VALUES ('7802327120');
INSERT INTO iq2_blacklist (phone_number) VALUES ('7807099080');
INSERT INTO iq2_blacklist (phone_number) VALUES ('4032389774');
INSERT INTO iq2_blacklist (phone_number) VALUES ('4039691089');
INSERT INTO iq2_blacklist (phone_number) VALUES ('4036068951');
INSERT INTO iq2_blacklist (phone_number) VALUES ('7802446796');
INSERT INTO iq2_blacklist (phone_number) VALUES ('7807200339');
INSERT INTO iq2_blacklist (phone_number) VALUES ('7802987865');
INSERT INTO iq2_blacklist (phone_number) VALUES ('7809325321');
INSERT INTO iq2_blacklist (phone_number) VALUES ('7802443009');
INSERT INTO iq2_blacklist (phone_number) VALUES ('4036204441');
INSERT INTO iq2_blacklist (phone_number) VALUES ('7878886140');
INSERT INTO iq2_blacklist (phone_number) VALUES ('7066510911');
INSERT INTO iq2_blacklist (phone_number) VALUES ('4039787715');
INSERT INTO iq2_blacklist (phone_number) VALUES ('7804518729');
INSERT INTO iq2_blacklist (phone_number) VALUES ('7804448865');
INSERT INTO iq2_blacklist (phone_number) VALUES ('7804447798');
INSERT INTO iq2_blacklist (phone_number) VALUES ('7808855551');
INSERT INTO iq2_blacklist (phone_number) VALUES ('4032661219');
INSERT INTO iq2_blacklist (phone_number) VALUES ('7877838123');
INSERT INTO iq2_blacklist (phone_number) VALUES ('7809650156');
INSERT INTO iq2_blacklist (phone_number) VALUES ('7877847857');
INSERT INTO iq2_blacklist (phone_number) VALUES ('7804877321');
INSERT INTO iq2_blacklist (phone_number) VALUES ('7804791598');
INSERT INTO iq2_blacklist (phone_number) VALUES ('7509617531');
INSERT INTO iq2_blacklist (phone_number) VALUES ('7809815426');
INSERT INTO iq2_blacklist (phone_number) VALUES ('7808869685');
INSERT INTO iq2_blacklist (phone_number) VALUES ('4032519345');
INSERT INTO iq2_blacklist (phone_number) VALUES ('7804999260');
INSERT INTO iq2_blacklist (phone_number) VALUES ('7807582128');
INSERT INTO iq2_blacklist (phone_number) VALUES ('7807562290');
INSERT INTO iq2_blacklist (phone_number) VALUES ('7804673749');
INSERT INTO iq2_blacklist (phone_number) VALUES ('7809650156');
INSERT INTO iq2_blacklist (phone_number) VALUES ('7805043941');
INSERT INTO iq2_blacklist (phone_number) VALUES ('7804058783');
INSERT INTO iq2_blacklist (phone_number) VALUES ('7804597940');
INSERT INTO iq2_blacklist (phone_number) VALUES ('7802421378');
INSERT INTO iq2_blacklist (phone_number) VALUES ('4039420114');
INSERT INTO iq2_blacklist (phone_number) VALUES ('4035274550');
INSERT INTO iq2_blacklist (phone_number) VALUES ('4036998519');
INSERT INTO iq2_blacklist (phone_number) VALUES ('4039750099');
INSERT INTO iq2_blacklist (phone_number) VALUES ('4034525816');
INSERT INTO iq2_blacklist (phone_number) VALUES ('4032193345');
INSERT INTO iq2_blacklist (phone_number) VALUES ('7804364239');
INSERT INTO iq2_blacklist (phone_number) VALUES ('7805301141');
INSERT INTO iq2_blacklist (phone_number) VALUES ('7806671848');
INSERT INTO iq2_blacklist (phone_number) VALUES ('4034536012');