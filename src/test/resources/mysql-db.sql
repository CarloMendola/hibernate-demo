create schema `prova` collate utf8mb4_general_ci;
create table alpha
(
    id varchar(32) not null,
    ts timestamp not null,
    col1 varchar(32) not null,
    col2 tinyint default 0 not null,
    col3 int default 0 not null,
    constraint alpha_pk
        primary key (id,ts)
);
create table beta
(
    id varchar(32) not null,
    alpha_id varchar(32) not null,
    alpha_ts timestamp not null,
    col1 varchar(32) not null,
    col2 tinyint default 0 not null,
    col3 int default 0 not null,
    constraint beta_pk
        primary key (id)
);
alter table beta
    add constraint beta_alpha_id_ts_fk
        foreign key (alpha_id, alpha_ts) references alpha (id, ts);
create table gamma
(
    id varchar(32) not null,
    col1 varchar(32) not null,
    col2 tinyint default 0 not null,
    col3 int default 0 not null,
    constraint gamma_pk
        primary key (id)
);
create table alpha_gamma
(
    alpha_id varchar(32) not null,
    alpha_ts timestamp not null,
    gamma_id varchar(32) not null,
    constraint alpha_gamma_pk
        primary key (alpha_id,alpha_ts,gamma_id)
);
alter table alpha_gamma
    add constraint alpha_gamma_alpha_id_ts_fk
        foreign key (alpha_id, alpha_ts) references alpha (id, ts);
alter table alpha_gamma
    add constraint alpha_gamma_gamma_id_fk
        foreign key (gamma_id) references gamma (id);
create table delta
(
    id varchar(32) not null,
    gamma_id varchar(32) not null,
    constraint delta_pk
        primary key (id)
);
alter table delta
    add constraint delta_gamma_id_fk
        foreign key (gamma_id) references gamma (id);