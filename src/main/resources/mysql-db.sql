drop schema if exists test ;
create schema `test` collate utf8mb4_general_ci;
use test;
create table alpha
(
    id varchar(36) not null,
    ts timestamp not null,
    col1 varchar(36) not null default 'default',
    col2 tinyint default 0 not null,
    col3 int default 0 not null,
    constraint alpha_pk
        primary key (id,ts)
);
create table beta
(
    id varchar(36) not null,
    alpha_id varchar(36) not null,
    alpha_ts timestamp not null,
    col1 varchar(36) not null,
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
    id varchar(36) not null,
    col1 varchar(36) not null,
    col2 tinyint default 0 not null,
    col3 int default 0 not null,
    constraint gamma_pk
        primary key (id)
);
create table alpha_gamma
(
    alpha_id varchar(36) not null,
    alpha_ts timestamp not null,
    gamma_id varchar(36) not null,
    constraint alpha_gamma_pk
        primary key (alpha_id,alpha_ts,gamma_id)
);
alter table alpha_gamma
    add constraint alpha_gamma_alpha_id_ts_fk
        foreign key (alpha_id, alpha_ts) references alpha (id, ts);
alter table alpha_gamma
    add constraint alpha_gamma_gamma_id_fk
        foreign key (gamma_id) references gamma (id);
create table delta_type
(
    id varchar(10) not null,
    constraint delta_type_pk
        primary key (id)
);
insert into delta_type values ('ONE'),('TWO'),('THREE');
create table delta
(
    id varchar(36) not null,
    type varchar(10) not null,
    gamma_id varchar(36) not null,
    constraint delta_pk
        primary key (id)
);
alter table delta
    add constraint delta_gamma_id_fk
        foreign key (gamma_id) references gamma (id);
alter table delta
    add constraint delta_deltatype_type_fk
        foreign key (type) references delta_type (id);

create table appcontexts
(
    id varchar(36) not null,
    last_update timestamp null,
    locked tinyint not null,
    status varchar(36) not null,
    worker varchar(256) null,
    thread_id varchar(36) null,
    constraint appcontexts_pk
        primary key (id)
);
create table dynamicdata
(
    app_context_id varchar(36) not null,
    inserted_at timestamp not null,
    dyn_data_id varchar(36) not null,
    dyn_value integer not null,
    constraint dyn_data_pk
        primary key (dyn_data_id)
);
alter table dynamicdata
    add constraint dyn_data_appcontexts_fk
        foreign key (app_context_id) references appcontexts (id);

insert into appcontexts (id, last_update, locked, status, worker, thread_id)
values
    ('1', '2000-01-01T00:00:00', 0, 'IDLE', null, null),
    ('2', '2000-01-01T00:00:00', 0, 'IDLE', null, null),
    ('3', '2000-01-01T00:00:00', 0, 'IDLE', null, null),
    ('4', '2000-01-01T00:00:00', 0, 'IDLE', null, null),
    ('5', '2000-01-01T00:00:00', 0, 'IDLE', null, null),
    ('6', '2000-01-01T00:00:00', 0, 'IDLE', null, null),
    ('7', '2000-01-01T00:00:00', 0, 'IDLE', null, null),
    ('8', '2000-01-01T00:00:00', 0, 'IDLE', null, null),
    ('9', '2000-01-01T00:00:00', 0, 'IDLE', null, null),
    ('10', '2000-01-01T00:00:00', 0, 'IDLE', null, null),
    ('11', '2000-01-01T00:00:00', 0, 'IDLE', null, null),
    ('12', '2000-01-01T00:00:00', 0, 'IDLE', null, null),
    ('13', '2000-01-01T00:00:00', 0, 'IDLE', null, null),
    ('14', '2000-01-01T00:00:00', 0, 'IDLE', null, null),
    ('15', '2000-01-01T00:00:00', 0, 'IDLE', null, null),
    ('16', '2000-01-01T00:00:00', 0, 'IDLE', null, null),
    ('17', '2000-01-01T00:00:00', 0, 'IDLE', null, null),
    ('18', '2000-01-01T00:00:00', 0, 'IDLE', null, null),
    ('19', '2000-01-01T00:00:00', 0, 'IDLE', null, null),
    ('20', '2000-01-01T00:00:00', 0, 'IDLE', null, null),
    ('21', '2000-01-01T00:00:00', 0, 'IDLE', null, null),
    ('22', '2000-01-01T00:00:00', 0, 'IDLE', null, null),
    ('23', '2000-01-01T00:00:00', 0, 'IDLE', null, null),
    ('24', '2000-01-01T00:00:00', 0, 'IDLE', null, null),
    ('25', '2000-01-01T00:00:00', 0, 'IDLE', null, null),
    ('26', '2000-01-01T00:00:00', 0, 'IDLE', null, null),
    ('27', '2000-01-01T00:00:00', 0, 'IDLE', null, null),
    ('28', '2000-01-01T00:00:00', 0, 'IDLE', null, null),
    ('29', '2000-01-01T00:00:00', 0, 'IDLE', null, null),
    ('30', '2000-01-01T00:00:00', 0, 'IDLE', null, null),
    ('31', '2000-01-01T00:00:00', 0, 'IDLE', null, null),
    ('32', '2000-01-01T00:00:00', 0, 'IDLE', null, null),
    ('33', '2000-01-01T00:00:00', 0, 'IDLE', null, null),
    ('34', '2000-01-01T00:00:00', 0, 'IDLE', null, null),
    ('35', '2000-01-01T00:00:00', 0, 'IDLE', null, null),
    ('36', '2000-01-01T00:00:00', 0, 'IDLE', null, null),
    ('37', '2000-01-01T00:00:00', 0, 'IDLE', null, null),
    ('38', '2000-01-01T00:00:00', 0, 'IDLE', null, null),
    ('39', '2000-01-01T00:00:00', 0, 'IDLE', null, null),
    ('40', '2000-01-01T00:00:00', 0, 'IDLE', null, null)
;