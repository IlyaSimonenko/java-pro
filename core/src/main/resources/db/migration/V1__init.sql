create table items (id bigserial primary key, title varchar(255), secret varchar(255));

insert into items (title, secret) values ('item1', 'secret1'), ('item2', 'secret2');