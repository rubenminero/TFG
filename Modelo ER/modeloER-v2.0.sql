-- Database generated with pgModeler (PostgreSQL Database Modeler).
-- pgModeler version: 1.0.2
-- PostgreSQL version: 15.0
-- Project Site: pgmodeler.io
-- Model Author: ---
-- -- object: pg_database_owner | type: ROLE --
-- -- DROP ROLE IF EXISTS pg_database_owner;
-- CREATE ROLE pg_database_owner WITH 
-- 	INHERIT
-- 	 PASSWORD '********';
-- -- ddl-end --
-- 

-- Database creation must be performed outside a multi lined SQL file. 
-- These commands were put in this file only as a convenience.
-- 
-- object: tfg | type: DATABASE --
-- DROP DATABASE IF EXISTS tfg;
CREATE DATABASE tfg
	ENCODING = 'UTF8'
	LC_COLLATE = 'Spanish_Spain.1252'
	LC_CTYPE = 'Spanish_Spain.1252'
	TABLESPACE = pg_default
	OWNER = postgres;
-- ddl-end --


-- object: public.token_seq | type: SEQUENCE --
-- DROP SEQUENCE IF EXISTS public.token_seq CASCADE;
CREATE SEQUENCE public.token_seq
	INCREMENT BY 50
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START WITH 1
	CACHE 1
	NO CYCLE
	OWNED BY NONE;

-- ddl-end --
ALTER SEQUENCE public.token_seq OWNER TO postgres;
-- ddl-end --

-- object: public.users_seq | type: SEQUENCE --
-- DROP SEQUENCE IF EXISTS public.users_seq CASCADE;
CREATE SEQUENCE public.users_seq
	INCREMENT BY 50
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START WITH 1
	CACHE 1
	NO CYCLE
	OWNED BY NONE;

-- ddl-end --
ALTER SEQUENCE public.users_seq OWNER TO postgres;
-- ddl-end --

-- object: public.admins | type: TABLE --
-- DROP TABLE IF EXISTS public.admins CASCADE;
CREATE TABLE public.admins (
	valid_from timestamp(6),
	valid_to timestamp(6),
	id bigint NOT NULL,
	CONSTRAINT admins_pkey PRIMARY KEY (id)
);
-- ddl-end --
ALTER TABLE public.admins OWNER TO postgres;
-- ddl-end --

-- object: public.athletes | type: TABLE --
-- DROP TABLE IF EXISTS public.athletes CASCADE;
CREATE TABLE public.athletes (
	disabled_at timestamp(6),
	enabled boolean,
	"phone number" character varying(255),
	id bigint NOT NULL,
	CONSTRAINT athletes_pkey PRIMARY KEY (id)
);
-- ddl-end --
ALTER TABLE public.athletes OWNER TO postgres;
-- ddl-end --

-- object: public.inscription_id_seq | type: SEQUENCE --
-- DROP SEQUENCE IF EXISTS public.inscription_id_seq CASCADE;
CREATE SEQUENCE public.inscription_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START WITH 1
	CACHE 1
	NO CYCLE
	OWNED BY NONE;

-- ddl-end --
ALTER SEQUENCE public.inscription_id_seq OWNER TO postgres;
-- ddl-end --

-- object: public.inscription | type: TABLE --
-- DROP TABLE IF EXISTS public.inscription CASCADE;
CREATE TABLE public.inscription (
	id bigint NOT NULL DEFAULT nextval('public.inscription_id_seq'::regclass),
	enabled boolean,
	id_tournament bigint,
	id_user bigint,
	CONSTRAINT inscription_pkey PRIMARY KEY (id)
);
-- ddl-end --
ALTER TABLE public.inscription OWNER TO postgres;
-- ddl-end --

-- object: public.organisers | type: TABLE --
-- DROP TABLE IF EXISTS public.organisers CASCADE;
CREATE TABLE public.organisers (
	address character varying(255),
	company_name character varying(255),
	disabled_at timestamp(6),
	enabled boolean,
	id bigint NOT NULL,
	CONSTRAINT organisers_pkey PRIMARY KEY (id)
);
-- ddl-end --
ALTER TABLE public.organisers OWNER TO postgres;
-- ddl-end --

-- object: public.sports_types_id_seq | type: SEQUENCE --
-- DROP SEQUENCE IF EXISTS public.sports_types_id_seq CASCADE;
CREATE SEQUENCE public.sports_types_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START WITH 1
	CACHE 1
	NO CYCLE
	OWNED BY NONE;

-- ddl-end --
ALTER SEQUENCE public.sports_types_id_seq OWNER TO postgres;
-- ddl-end --

-- object: public.sports_types | type: TABLE --
-- DROP TABLE IF EXISTS public.sports_types CASCADE;
CREATE TABLE public.sports_types (
	id bigint NOT NULL DEFAULT nextval('public.sports_types_id_seq'::regclass),
	enabled boolean,
	name character varying(255),
	CONSTRAINT sports_types_pkey PRIMARY KEY (id)
);
-- ddl-end --
ALTER TABLE public.sports_types OWNER TO postgres;
-- ddl-end --

-- object: public.token | type: TABLE --
-- DROP TABLE IF EXISTS public.token CASCADE;
CREATE TABLE public.token (
	id integer NOT NULL,
	expired boolean NOT NULL,
	revoked boolean NOT NULL,
	token character varying(255),
	token_type character varying(255),
	user_id bigint,
	CONSTRAINT token_pkey PRIMARY KEY (id),
	CONSTRAINT uk_pddrhgwxnms2aceeku9s2ewy5 UNIQUE (token)
);
-- ddl-end --
ALTER TABLE public.token OWNER TO postgres;
-- ddl-end --

-- object: public.tournaments_id_seq | type: SEQUENCE --
-- DROP SEQUENCE IF EXISTS public.tournaments_id_seq CASCADE;
CREATE SEQUENCE public.tournaments_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START WITH 1
	CACHE 1
	NO CYCLE
	OWNED BY NONE;

-- ddl-end --
ALTER SEQUENCE public.tournaments_id_seq OWNER TO postgres;
-- ddl-end --

-- object: public.tournaments | type: TABLE --
-- DROP TABLE IF EXISTS public.tournaments CASCADE;
CREATE TABLE public.tournaments (
	id bigint NOT NULL DEFAULT nextval('public.tournaments_id_seq'::regclass),
	address character varying(255),
	description character varying(255),
	enabled boolean,
	location character varying(255),
	name character varying(255),
	id_organizers bigint,
	id_sports_type bigint,
	CONSTRAINT tournaments_pkey PRIMARY KEY (id)
);
-- ddl-end --
ALTER TABLE public.tournaments OWNER TO postgres;
-- ddl-end --

-- object: public.users_id_seq | type: SEQUENCE --
-- DROP SEQUENCE IF EXISTS public.users_id_seq CASCADE;
CREATE SEQUENCE public.users_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START WITH 1
	CACHE 1
	NO CYCLE
	OWNED BY NONE;

-- ddl-end --
ALTER SEQUENCE public.users_id_seq OWNER TO postgres;
-- ddl-end --

-- object: public.users | type: TABLE --
-- DROP TABLE IF EXISTS public.users CASCADE;
CREATE TABLE public.users (
	id bigint NOT NULL DEFAULT nextval('public.users_id_seq'::regclass),
	email character varying(255),
	first_name character varying(255),
	last_name character varying(255),
	nif character varying(255),
	password character varying(255),
	role character varying(255),
	username character varying(255),
	CONSTRAINT users_pkey PRIMARY KEY (id)
);
-- ddl-end --
ALTER TABLE public.users OWNER TO postgres;
-- ddl-end --

-- object: public.watchlist_id_seq | type: SEQUENCE --
-- DROP SEQUENCE IF EXISTS public.watchlist_id_seq CASCADE;
CREATE SEQUENCE public.watchlist_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START WITH 1
	CACHE 1
	NO CYCLE
	OWNED BY NONE;

-- ddl-end --
ALTER SEQUENCE public.watchlist_id_seq OWNER TO postgres;
-- ddl-end --

-- object: public.watchlist | type: TABLE --
-- DROP TABLE IF EXISTS public.watchlist CASCADE;
CREATE TABLE public.watchlist (
	id bigint NOT NULL DEFAULT nextval('public.watchlist_id_seq'::regclass),
	enabled boolean,
	id_tournament bigint,
	id_user bigint,
	CONSTRAINT watchlist_pkey PRIMARY KEY (id)
);
-- ddl-end --
ALTER TABLE public.watchlist OWNER TO postgres;
-- ddl-end --

-- object: fkanhsicqm3lc8ya77tr7r0je18 | type: CONSTRAINT --
-- ALTER TABLE public.admins DROP CONSTRAINT IF EXISTS fkanhsicqm3lc8ya77tr7r0je18 CASCADE;
ALTER TABLE public.admins ADD CONSTRAINT fkanhsicqm3lc8ya77tr7r0je18 FOREIGN KEY (id)
REFERENCES public.users (id) MATCH SIMPLE
ON DELETE NO ACTION ON UPDATE NO ACTION;
-- ddl-end --

-- object: fktkjdbtevny2xu80xxyqjr1ulq | type: CONSTRAINT --
-- ALTER TABLE public.athletes DROP CONSTRAINT IF EXISTS fktkjdbtevny2xu80xxyqjr1ulq CASCADE;
ALTER TABLE public.athletes ADD CONSTRAINT fktkjdbtevny2xu80xxyqjr1ulq FOREIGN KEY (id)
REFERENCES public.users (id) MATCH SIMPLE
ON DELETE NO ACTION ON UPDATE NO ACTION;
-- ddl-end --

-- object: fkd73huifg852gh9f9sx25qfg7a | type: CONSTRAINT --
-- ALTER TABLE public.inscription DROP CONSTRAINT IF EXISTS fkd73huifg852gh9f9sx25qfg7a CASCADE;
ALTER TABLE public.inscription ADD CONSTRAINT fkd73huifg852gh9f9sx25qfg7a FOREIGN KEY (id_tournament)
REFERENCES public.tournaments (id) MATCH SIMPLE
ON DELETE NO ACTION ON UPDATE NO ACTION;
-- ddl-end --

-- object: fkrauyotql993k9skjsx0s5r64c | type: CONSTRAINT --
-- ALTER TABLE public.inscription DROP CONSTRAINT IF EXISTS fkrauyotql993k9skjsx0s5r64c CASCADE;
ALTER TABLE public.inscription ADD CONSTRAINT fkrauyotql993k9skjsx0s5r64c FOREIGN KEY (id_user)
REFERENCES public.athletes (id) MATCH SIMPLE
ON DELETE NO ACTION ON UPDATE NO ACTION;
-- ddl-end --

-- object: fks62f11x72fwao4fe1u71tpxq8 | type: CONSTRAINT --
-- ALTER TABLE public.organisers DROP CONSTRAINT IF EXISTS fks62f11x72fwao4fe1u71tpxq8 CASCADE;
ALTER TABLE public.organisers ADD CONSTRAINT fks62f11x72fwao4fe1u71tpxq8 FOREIGN KEY (id)
REFERENCES public.users (id) MATCH SIMPLE
ON DELETE NO ACTION ON UPDATE NO ACTION;
-- ddl-end --

-- object: fkj8rfw4x0wjjyibfqq566j4qng | type: CONSTRAINT --
-- ALTER TABLE public.token DROP CONSTRAINT IF EXISTS fkj8rfw4x0wjjyibfqq566j4qng CASCADE;
ALTER TABLE public.token ADD CONSTRAINT fkj8rfw4x0wjjyibfqq566j4qng FOREIGN KEY (user_id)
REFERENCES public.users (id) MATCH SIMPLE
ON DELETE NO ACTION ON UPDATE NO ACTION;
-- ddl-end --

-- object: fkd55qp0dtk4jpd3vigwaorift9 | type: CONSTRAINT --
-- ALTER TABLE public.tournaments DROP CONSTRAINT IF EXISTS fkd55qp0dtk4jpd3vigwaorift9 CASCADE;
ALTER TABLE public.tournaments ADD CONSTRAINT fkd55qp0dtk4jpd3vigwaorift9 FOREIGN KEY (id_organizers)
REFERENCES public.organisers (id) MATCH SIMPLE
ON DELETE NO ACTION ON UPDATE NO ACTION;
-- ddl-end --

-- object: fkgj6dhl95x7bdlry2yvo3cswm9 | type: CONSTRAINT --
-- ALTER TABLE public.tournaments DROP CONSTRAINT IF EXISTS fkgj6dhl95x7bdlry2yvo3cswm9 CASCADE;
ALTER TABLE public.tournaments ADD CONSTRAINT fkgj6dhl95x7bdlry2yvo3cswm9 FOREIGN KEY (id_sports_type)
REFERENCES public.sports_types (id) MATCH SIMPLE
ON DELETE NO ACTION ON UPDATE NO ACTION;
-- ddl-end --

-- object: fk2xlcd7wmuk8vbq32iy5rkx9kw | type: CONSTRAINT --
-- ALTER TABLE public.watchlist DROP CONSTRAINT IF EXISTS fk2xlcd7wmuk8vbq32iy5rkx9kw CASCADE;
ALTER TABLE public.watchlist ADD CONSTRAINT fk2xlcd7wmuk8vbq32iy5rkx9kw FOREIGN KEY (id_tournament)
REFERENCES public.tournaments (id) MATCH SIMPLE
ON DELETE NO ACTION ON UPDATE NO ACTION;
-- ddl-end --

-- object: fks4wfkmqsvockurndo9cc2b63w | type: CONSTRAINT --
-- ALTER TABLE public.watchlist DROP CONSTRAINT IF EXISTS fks4wfkmqsvockurndo9cc2b63w CASCADE;
ALTER TABLE public.watchlist ADD CONSTRAINT fks4wfkmqsvockurndo9cc2b63w FOREIGN KEY (id_user)
REFERENCES public.athletes (id) MATCH SIMPLE
ON DELETE NO ACTION ON UPDATE NO ACTION;
-- ddl-end --

-- object: "grant_CU_26541e8cda" | type: PERMISSION --
GRANT CREATE,USAGE
   ON SCHEMA public
   TO pg_database_owner;
-- ddl-end --

-- object: "grant_U_cd8e46e7b6" | type: PERMISSION --
GRANT USAGE
   ON SCHEMA public
   TO PUBLIC;
-- ddl-end --


