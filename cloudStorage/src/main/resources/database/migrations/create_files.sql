create table files
(
    id       bigserial primary key,
    filename varchar(255) unique,
    size     bigint,
    bytes    oid
);