create table users
(
    id        bigserial primary key,
    email     varchar(255) unique not null,
    password  varchar(255),
    name      varchar(255),
    surname   varchar(255),
    user_name varchar(255),
    role_id   int unique not null,
    foreign key (role_id) references roles (id)
);