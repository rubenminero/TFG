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
-- object: "TFG" | type: DATABASE --
-- DROP DATABASE IF EXISTS "TFG";
CREATE DATABASE "TFG"
	ENCODING = 'UTF8'
	LC_COLLATE = 'Spanish_Spain.1252'
	LC_CTYPE = 'Spanish_Spain.1252'
	TABLESPACE = pg_default
	OWNER = postgres;
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
	enabled boolean,
	first_name character varying(255),
	last_name character varying(255),
	nif character varying(255),
	password character varying(255),
	username character varying(255),
	CONSTRAINT users_pkey PRIMARY KEY (id)
);
-- ddl-end --
ALTER TABLE public.users OWNER TO postgres;
-- ddl-end --

-- object: public.organisers_id_seq | type: SEQUENCE --
-- DROP SEQUENCE IF EXISTS public.organisers_id_seq CASCADE;
CREATE SEQUENCE public.organisers_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START WITH 1
	CACHE 1
	NO CYCLE
	OWNED BY NONE;

-- ddl-end --
ALTER SEQUENCE public.organisers_id_seq OWNER TO postgres;
-- ddl-end --

-- object: public.organisers | type: TABLE --
-- DROP TABLE IF EXISTS public.organisers CASCADE;
CREATE TABLE public.organisers (
	id bigint NOT NULL DEFAULT nextval('public.organisers_id_seq'::regclass),
	email character varying(255),
	enabled boolean,
	nif character varying(255),
	password character varying(255),
	company_name character varying(255),
	CONSTRAINT organisers_pkey PRIMARY KEY (id)
);
-- ddl-end --
ALTER TABLE public.organisers OWNER TO postgres;
-- ddl-end --

-- object: public.admins_id_seq | type: SEQUENCE --
-- DROP SEQUENCE IF EXISTS public.admins_id_seq CASCADE;
CREATE SEQUENCE public.admins_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START WITH 1
	CACHE 1
	NO CYCLE
	OWNED BY NONE;

-- ddl-end --
ALTER SEQUENCE public.admins_id_seq OWNER TO postgres;
-- ddl-end --

-- object: public.admins | type: TABLE --
-- DROP TABLE IF EXISTS public.admins CASCADE;
CREATE TABLE public.admins (
	id bigint NOT NULL DEFAULT nextval('public.admins_id_seq'::regclass),
	email character varying(255),
	first_name character varying(255),
	last_name character varying(255),
	nif character varying(255),
	password character varying(255),
	username character varying(255),
	CONSTRAINT admins_pkey PRIMARY KEY (id)
);
-- ddl-end --
ALTER TABLE public.admins OWNER TO postgres;
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
	name character varying(255),
	inscription boolean,
	location character varying(255),
	adress character varying(255),
	description character varying(255),
	enabled boolean,
	id_sports_types bigint,
	id_organisers bigint,
	CONSTRAINT tournaments_pk PRIMARY KEY (id)
);
-- ddl-end --
ALTER TABLE public.tournaments OWNER TO postgres;
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
	name character varying(20),
	enabled boolean,
	CONSTRAINT sports_types_pk PRIMARY KEY (id)
);
-- ddl-end --
ALTER TABLE public.sports_types OWNER TO postgres;
-- ddl-end --


-- object: public.inscriptions_id_seq | type: SEQUENCE --
-- DROP SEQUENCE IF EXISTS public.inscriptions_id_seq CASCADE;
CREATE SEQUENCE public.inscriptions_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START WITH 1
	CACHE 1
	NO CYCLE
	OWNED BY NONE;

-- ddl-end --
ALTER SEQUENCE public.inscriptions_id_seq OWNER TO postgres;
-- ddl-end --

-- object: public.inscriptions | type: TABLE --
-- DROP TABLE IF EXISTS public.inscriptions CASCADE;
CREATE TABLE public.inscriptions (
	id bigint NOT NULL DEFAULT nextval('public.inscriptions_id_seq'::regclass),
	id_users bigint,
	id_tournaments bigint,
	CONSTRAINT inscriptions_pk PRIMARY KEY (id)
);
-- ddl-end --
ALTER TABLE public.inscriptions OWNER TO postgres;
-- ddl-end --

-- object: users_fk | type: CONSTRAINT --
-- ALTER TABLE public.inscriptions DROP CONSTRAINT IF EXISTS users_fk CASCADE;
ALTER TABLE public.inscriptions ADD CONSTRAINT users_fk FOREIGN KEY (id_users)
REFERENCES public.users (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: tournaments_fk | type: CONSTRAINT --
-- ALTER TABLE public.inscriptions DROP CONSTRAINT IF EXISTS tournaments_fk CASCADE;
ALTER TABLE public.inscriptions ADD CONSTRAINT tournaments_fk FOREIGN KEY (id_tournaments)
REFERENCES public.tournaments (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --


-- object: public.watch_list_id_seq | type: SEQUENCE --
-- DROP SEQUENCE IF EXISTS public.watch_list_id_seq CASCADE;
CREATE SEQUENCE public.watch_list_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START WITH 1
	CACHE 1
	NO CYCLE
	OWNED BY NONE;

-- ddl-end --
ALTER SEQUENCE public.watch_list_id_seq OWNER TO postgres;
-- ddl-end --

-- object: public.watch_list | type: TABLE --
-- DROP TABLE IF EXISTS public.watch_list CASCADE;
CREATE TABLE public.watch_list (
	id bigint NOT NULL DEFAULT nextval('public.watch_list_id_seq'::regclass),
	id_users bigint,
	id_tournaments bigint,
	CONSTRAINT watch_list_pk PRIMARY KEY (id)
);
-- ddl-end --
ALTER TABLE public.watch_list OWNER TO postgres;
-- ddl-end --

-- object: users_fk | type: CONSTRAINT --
-- ALTER TABLE public.watch_list DROP CONSTRAINT IF EXISTS users_fk CASCADE;
ALTER TABLE public.watch_list ADD CONSTRAINT users_fk FOREIGN KEY (id_users)
REFERENCES public.users (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: tournaments_fk | type: CONSTRAINT --
-- ALTER TABLE public.watch_list DROP CONSTRAINT IF EXISTS tournaments_fk CASCADE;
ALTER TABLE public.watch_list ADD CONSTRAINT tournaments_fk FOREIGN KEY (id_tournaments)
REFERENCES public.tournaments (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: sports_types_fk | type: CONSTRAINT --
-- ALTER TABLE public.tournaments DROP CONSTRAINT IF EXISTS sports_types_fk CASCADE;
ALTER TABLE public.tournaments ADD CONSTRAINT sports_types_fk FOREIGN KEY (id_sports_types)
REFERENCES public.sports_types (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: organisers_fk | type: CONSTRAINT --
-- ALTER TABLE public.tournaments DROP CONSTRAINT IF EXISTS organisers_fk CASCADE;
ALTER TABLE public.tournaments ADD CONSTRAINT organisers_fk FOREIGN KEY (id_organisers)
REFERENCES public.organisers (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
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


