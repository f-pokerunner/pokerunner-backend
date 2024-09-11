CREATE TABLE users
(
    id         int AUTO_INCREMENT PRIMARY KEY,
    nickname   varchar(255),
    address    varchar(255),
    created_at datetime
);

ALTER TABLE users
    ADD COLUMN default_pokemon_id int NULL;

CREATE TABLE user_pokemon
(
    id               int AUTO_INCREMENT PRIMARY KEY,
    user_id          int,
    pokemon_id       int,
    nickname         varchar(255),
    health           int,
    evolution_status varchar(255),
    level            int,
    experience       int,
    created_dt       datetime
);

CREATE TABLE pokemon
(
    id               int AUTO_INCREMENT PRIMARY KEY,
    pokemon_name     varchar(255),
    evolution_status varchar(255),
    image_url        varchar(255),
    created_dt       datetime
);

CREATE TABLE user_running
(
    id             int AUTO_INCREMENT PRIMARY KEY,
    user_id        int,
    distance_meter int,
    start_time     datetime,
    end_time       datetime,
    pace           double
);

CREATE TABLE seoul_gu_boss
(
    id          int AUTO_INCREMENT PRIMARY KEY,
    seoul_gu_id int,
    user_id     int
);

CREATE TABLE seoul_gu
(
    id         int AUTO_INCREMENT PRIMARY KEY,
    gu_name    varchar(255),
    created_dt datetime
);

ALTER TABLE user_running
    MODIFY COLUMN distance_meter varchar(200) NULL;

ALTER TABLE user_running
    MODIFY COLUMN pace varchar(200) NULL;