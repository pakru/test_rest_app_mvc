create table if not exists accounts
(
    id bigserial not null constraint accounts_pk primary key,
    name varchar(255) not null,
    created_at TIMESTAMP not null,
    email varchar(255) UNIQUE
);