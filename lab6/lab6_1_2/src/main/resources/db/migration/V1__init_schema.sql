DROP TABLE IF EXISTS student;

CREATE TABLE student(
    id BIGSERIAL Primary Key,
    name varchar(255) NOT NULL,
    email varchar(255) NOT NULL
);


insert into student (name, email) values ('Miguel', 'miguel@ua.pt');
insert into student (name, email) values ('Ricardo', 'ricardo@ua.pt');
insert into student (name, email) values ('João', 'joao@ua.pt');
