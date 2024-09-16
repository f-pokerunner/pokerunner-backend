CREATE SCHEMA pokerunner;
USE pokerunner;


CREATE TABLE users
(
    id                 INT AUTO_INCREMENT PRIMARY KEY,
    nickname           VARCHAR(255),
    address            VARCHAR(255),
    created_at         DATETIME,
    uuid               VARCHAR(200),
    default_pokemon_id INT,
    comment            VARCHAR(200)
);

CREATE TABLE user_pokemon
(
    id               INT AUTO_INCREMENT PRIMARY KEY,
    user_id          INT,
    pokemon_id       INT,
    nickname         VARCHAR(255),
    health           INT,
    evolution_status VARCHAR(255),
    level            INT,
    experience       INT,
    created_at       DATETIME,
    user_uuid        VARCHAR(200),
    default_pokemon  BOOLEAN
);

CREATE TABLE pokemon
(
    id                  INT AUTO_INCREMENT PRIMARY KEY,
    pokemon_name        VARCHAR(255),
    evolution_status    VARCHAR(255),
    image_url           VARCHAR(255),
    created_dt          DATETIME,
    pre_evolution_name  VARCHAR(255),
    next_evolution_name VARCHAR(255)
);

CREATE TABLE user_running
(
    id             INT AUTO_INCREMENT PRIMARY KEY,
    user_id        INT,
    distance_meter VARCHAR(200),
    start_time     DATETIME,
    end_time       DATETIME,
    pace           VARCHAR(200),
    gu_address     VARCHAR(200)
);

CREATE TABLE seoul_gu_boss
(
    id           INT AUTO_INCREMENT PRIMARY KEY,
    seoul_gu_id  INT,
    user_id      INT,
    boss_comment VARCHAR(200)
);

CREATE TABLE seoul_gu
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    gu_name    VARCHAR(255),
    created_dt DATETIME
);

CREATE TABLE pokemon_location_real_time
(
    id           INT AUTO_INCREMENT PRIMARY KEY,
    pokemon_name VARCHAR(200) NOT NULL,
    coordinates  POINT        NOT NULL,
    owner_id     INT,
    SPATIAL INDEX (coordinates)
);