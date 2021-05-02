CREATE DATABASE "My_Easy_Job" WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'Spanish_Colombia.1252';

\c My_Easy_Job

-- Name: app_user; Type: TABLE; Schema: public; Owner: postgres
CREATE TABLE app_user
(
    id_app_user integer NOT NULL,
    name        text    NOT NULL,
    email       text    NOT NULL,
    password    text    NOT NULL,
    description text,
    phone       text,
    image       bytea
);

-- Name: app_user_id_app_user_seq; Type: SEQUENCE; Schema: public; Owner: postgres
ALTER TABLE app_user
    ALTER COLUMN id_app_user ADD GENERATED ALWAYS AS IDENTITY (
        SEQUENCE NAME app_user_id_app_user_seq
        START WITH 1
        INCREMENT BY 1
        NO MINVALUE
        NO MAXVALUE
        CACHE 1
        );

-- Name: category; Type: TABLE; Schema: public; Owner: postgres
CREATE TABLE category
(
    id_category   integer NOT NULL,
    category_name text    NOT NULL
);

-- Name: category_id_category_seq; Type: SEQUENCE; Schema: public; Owner: postgres
ALTER TABLE category
    ALTER COLUMN id_category ADD GENERATED ALWAYS AS IDENTITY (
        SEQUENCE NAME category_id_category_seq
        START WITH 1
        INCREMENT BY 1
        NO MINVALUE
        NO MAXVALUE
        CACHE 1
        );

-- Name: city; Type: TABLE; Schema: public; Owner: postgres
CREATE TABLE city
(
    id_city   integer NOT NULL,
    city_name text    NOT NULL
);

-- Name: city_id_city_seq; Type: SEQUENCE; Schema: public; Owner: postgres
ALTER TABLE city
    ALTER COLUMN id_city ADD GENERATED ALWAYS AS IDENTITY (
        SEQUENCE NAME city_id_city_seq
        START WITH 1
        INCREMENT BY 1
        NO MINVALUE
        NO MAXVALUE
        CACHE 1
        );

-- Name: post; Type: TABLE; Schema: public; Owner: postgres
CREATE TABLE post
(
    id_post            integer NOT NULL,
    id_user            integer NOT NULL,
    description        text,
    date               date    NOT NULL,
    id_category        integer NOT NULL,
    location_latitude  text,
    location_longitude text,
    id_city            integer NOT NULL,
    title              text    NOT NULL
);

-- Name: post_id_post_seq; Type: SEQUENCE; Schema: public; Owner: postgres
ALTER TABLE post
    ALTER COLUMN id_post ADD GENERATED ALWAYS AS IDENTITY (
        SEQUENCE NAME post_id_post_seq
        START WITH 1
        INCREMENT BY 1
        NO MINVALUE
        NO MAXVALUE
        CACHE 1
        );

-- Name: post_image; Type: TABLE; Schema: public; Owner: postgres
CREATE TABLE post_image
(
    id_post_image integer NOT NULL,
    id_post       integer NOT NULL,
    image         bytea   NOT NULL,
    priority      integer
);

-- Name: post_images_id_post_images_seq; Type: SEQUENCE; Schema: public; Owner: postgres
ALTER TABLE post_image
    ALTER COLUMN id_post_image ADD GENERATED ALWAYS AS IDENTITY (
        SEQUENCE NAME post_images_id_post_images_seq
        START WITH 1
        INCREMENT BY 1
        NO MINVALUE
        NO MAXVALUE
        CACHE 1
        );

-- Name: rating; Type: TABLE; Schema: public; Owner: postgres
CREATE TABLE rating
(
    id_rating         integer NOT NULL,
    score             integer,
    id_user           integer NOT NULL,
    id_qualifier_user integer NOT NULL,
    comment           text,
    date              date    NOT NULL
);

-- Name: rating_id_rating_seq; Type: SEQUENCE; Schema: public; Owner: postgres
ALTER TABLE rating
    ALTER COLUMN id_rating ADD GENERATED ALWAYS AS IDENTITY (
        SEQUENCE NAME rating_id_rating_seq
        START WITH 1
        INCREMENT BY 1
        NO MINVALUE
        NO MAXVALUE
        CACHE 1
        );

-- Name: category category_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
ALTER TABLE ONLY category
    ADD CONSTRAINT category_pkey PRIMARY KEY (id_category);

-- Name: app_user constraint_email; Type: CONSTRAINT; Schema: public; Owner: postgres
ALTER TABLE ONLY app_user
    ADD CONSTRAINT constraint_email UNIQUE (email);

-- Name: city pk_city; Type: CONSTRAINT; Schema: public; Owner: postgres
ALTER TABLE ONLY city
    ADD CONSTRAINT pk_city PRIMARY KEY (id_city);

-- Name: post_image post_images_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
ALTER TABLE ONLY post_image
    ADD CONSTRAINT post_images_pkey PRIMARY KEY (id_post_image);

-- Name: post post_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
ALTER TABLE ONLY post
    ADD CONSTRAINT post_pkey PRIMARY KEY (id_post);

-- Name: rating rating_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
ALTER TABLE ONLY rating
    ADD CONSTRAINT rating_pkey PRIMARY KEY (id_rating);

-- Name: app_user user_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
ALTER TABLE ONLY app_user
    ADD CONSTRAINT user_pkey PRIMARY KEY (id_app_user);

-- Name: post ID_category_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
ALTER TABLE ONLY post
    ADD CONSTRAINT "ID_category_fkey" FOREIGN KEY (id_category) REFERENCES category (id_category) NOT VALID;

-- Name: post ID_city_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
ALTER TABLE ONLY post
    ADD CONSTRAINT "ID_city_fkey" FOREIGN KEY (id_city) REFERENCES city (id_city) NOT VALID;

-- Name: rating ID_rated_user_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
ALTER TABLE ONLY rating
    ADD CONSTRAINT "ID_rated_user_fkey" FOREIGN KEY (id_qualifier_user) REFERENCES app_user (id_app_user) NOT VALID;

-- Name: rating ID_user_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
ALTER TABLE ONLY rating
    ADD CONSTRAINT "ID_user_fkey" FOREIGN KEY (id_user) REFERENCES app_user (id_app_user) NOT VALID;

-- Name: post ID_user_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
ALTER TABLE ONLY post
    ADD CONSTRAINT "ID_user_fkey" FOREIGN KEY (id_user) REFERENCES app_user (id_app_user) NOT VALID;

-- Name: post_image post_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
ALTER TABLE ONLY post_image
    ADD CONSTRAINT post_fkey FOREIGN KEY (id_post) REFERENCES post (id_post) NOT VALID;


-- Default inserts

INSERT INTO category(category_name)
VALUES ('Carpinteria'),
       ('Electricidad'),
       ('Construcción'),
       ('Computación'),
       ('Plomeria'),
       ('Arquitectura'),
       ('Pintura'),
       ('Electronica'),
       ('Enseñanza'),
       ('Cuidado de niños')
;

INSERT INTO city(city_name)
VALUES ('Armenia'),
       ('Barranquilla'),
       ('Bello'),
       ('Bogotá¡'),
       ('Bucaramanga'),
       ('Cartagena'),
       ('Cúcuta'),
       ('Ibagué'),
       ('Manizales'),
       ('Medellín'),
       ('Montería'),
       ('Neiva'),
       ('Pasto'),
       ('Pereira'),
       ('Soacha'),
       ('Soledad'),
       ('Valledupar'),
       ('Villavicencio'),
       ('Santa Marta'),
       ('Cali')
;

INSERT INTO app_user(name, email, password, description, phone)
VALUES ('Admin',
        'admin@admin.com',
        '$2a$10$3lh8wBi5hdpZ4pBlNEMtBeAN83Qph3h2.Ddjk/85720z1VKpfx2wC',
        '',
        '555 1232244');
	
