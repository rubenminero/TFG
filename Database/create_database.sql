
BEGIN;


DROP DATABASE IF EXISTS SPM;
CREATE DATABASE SPM
	ENCODING = 'UTF8'
	LC_COLLATE = 'Spanish_Spain.UTF-8'
	LC_CTYPE = 'Spanish_Spain.UTF-8'
	TABLESPACE = pg_default
	OWNER = postgres;

CREATE TABLE IF NOT EXISTS public.admins
(
    valid_from timestamp(6) without time zone,
    valid_to timestamp(6) without time zone,
    id bigint NOT NULL,
    CONSTRAINT admins_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.athletes
(
    disabled_at timestamp(6) without time zone,
    enabled boolean,
    "phone number" character varying(255) COLLATE pg_catalog."default",
    id bigint NOT NULL,
    CONSTRAINT athletes_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.inscription
(
    id bigint NOT NULL DEFAULT nextval('inscription_id_seq'::regclass),
    enabled boolean,
    id_tournament bigint,
    id_user bigint,
    CONSTRAINT inscription_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.organisers
(
    address character varying(255) COLLATE pg_catalog."default",
    company_name character varying(255) COLLATE pg_catalog."default",
    disabled_at timestamp(6) without time zone,
    enabled boolean,
    id bigint NOT NULL,
    CONSTRAINT organisers_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.sports_types
(
    id bigint NOT NULL DEFAULT nextval('sports_types_id_seq'::regclass),
    enabled boolean,
    name character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT sports_types_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.token
(
    id integer NOT NULL,
    expired boolean NOT NULL,
    revoked boolean NOT NULL,
    token character varying(255) COLLATE pg_catalog."default",
    token_type character varying(255) COLLATE pg_catalog."default",
    user_id bigint,
    CONSTRAINT token_pkey PRIMARY KEY (id),
    CONSTRAINT uk_pddrhgwxnms2aceeku9s2ewy5 UNIQUE (token)
);

CREATE TABLE IF NOT EXISTS public.tournaments
(
    id bigint NOT NULL DEFAULT nextval('tournaments_id_seq'::regclass),
    address character varying(255) COLLATE pg_catalog."default",
    description character varying(255) COLLATE pg_catalog."default",
    enabled boolean,
    location character varying(255) COLLATE pg_catalog."default",
    name character varying(255) COLLATE pg_catalog."default",
    id_organizers bigint,
    id_sports_type bigint,
    capacity integer,
    inscription boolean,
    CONSTRAINT tournaments_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.users
(
    id bigint NOT NULL DEFAULT nextval('users_id_seq'::regclass),
    email character varying(255) COLLATE pg_catalog."default",
    first_name character varying(255) COLLATE pg_catalog."default",
    last_name character varying(255) COLLATE pg_catalog."default",
    nif character varying(255) COLLATE pg_catalog."default",
    password character varying(255) COLLATE pg_catalog."default",
    role character varying(255) COLLATE pg_catalog."default",
    username character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT users_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.watchlist
(
    id bigint NOT NULL DEFAULT nextval('watchlist_id_seq'::regclass),
    enabled boolean,
    id_tournament bigint,
    id_user bigint,
    CONSTRAINT watchlist_pkey PRIMARY KEY (id)
);

ALTER TABLE IF EXISTS public.admins
    ADD CONSTRAINT fkanhsicqm3lc8ya77tr7r0je18 FOREIGN KEY (id)
    REFERENCES public.users (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;
CREATE INDEX IF NOT EXISTS admins_pkey
    ON public.admins(id);


ALTER TABLE IF EXISTS public.athletes
    ADD CONSTRAINT fktkjdbtevny2xu80xxyqjr1ulq FOREIGN KEY (id)
    REFERENCES public.users (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;
CREATE INDEX IF NOT EXISTS athletes_pkey
    ON public.athletes(id);


ALTER TABLE IF EXISTS public.inscription
    ADD CONSTRAINT fkd73huifg852gh9f9sx25qfg7a FOREIGN KEY (id_tournament)
    REFERENCES public.tournaments (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS public.inscription
    ADD CONSTRAINT fkrauyotql993k9skjsx0s5r64c FOREIGN KEY (id_user)
    REFERENCES public.athletes (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS public.organisers
    ADD CONSTRAINT fks62f11x72fwao4fe1u71tpxq8 FOREIGN KEY (id)
    REFERENCES public.users (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;
CREATE INDEX IF NOT EXISTS organisers_pkey
    ON public.organisers(id);


ALTER TABLE IF EXISTS public.token
    ADD CONSTRAINT fkj8rfw4x0wjjyibfqq566j4qng FOREIGN KEY (user_id)
    REFERENCES public.users (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS public.tournaments
    ADD CONSTRAINT fkd55qp0dtk4jpd3vigwaorift9 FOREIGN KEY (id_organizers)
    REFERENCES public.organisers (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS public.tournaments
    ADD CONSTRAINT fkgj6dhl95x7bdlry2yvo3cswm9 FOREIGN KEY (id_sports_type)
    REFERENCES public.sports_types (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS public.watchlist
    ADD CONSTRAINT fk2xlcd7wmuk8vbq32iy5rkx9kw FOREIGN KEY (id_tournament)
    REFERENCES public.tournaments (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS public.watchlist
    ADD CONSTRAINT fks4wfkmqsvockurndo9cc2b63w FOREIGN KEY (id_user)
    REFERENCES public.athletes (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;

END;