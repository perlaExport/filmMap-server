CREATE TABLE IF NOT EXISTS category
(
    category_id  bigserial not null,
    category_name varchar(255) not null,
    primary key (category_id)
);

CREATE TABLE IF NOT EXISTS users
(
    user_id  bigserial not null,
    auth_type varchar(255) not null,
    email varchar(255) not null,
    enabled boolean not null,
    name varchar(255) not null,
    password varchar(255),
    primary key (user_id)
);

CREATE TABLE IF NOT EXISTS movie
(
    movie_id varchar(255) not null,
    img_path varchar(255) not null,
    rating double precision not null,
    title varchar(255) not null,
    primary key (movie_id)
);

CREATE TABLE IF NOT EXISTS rating
(
    rating_id  bigserial not null,
    rating int not null,
    review varchar(255),
    movie_id varchar(255) references movie (movie_id),
    user_id int references users (user_id),
    primary key (rating_id)
);

CREATE TABLE IF NOT EXISTS favourites
(
    user_id int not null references users (user_id),
    movie_id varchar(255) not null references movie (movie_id)
);

CREATE TABLE IF NOT EXISTS movie_category
(
    category_id int not null references category (category_id),
    movie_id varchar(255) not null references movie (movie_id),
    primary key (category_id, movie_id)
);

CREATE TABLE IF NOT EXISTS watch_later
(
    user_id int not null references users (user_id),
    movie_id varchar(255) not null references movie (movie_id)
);