create table posts(
	post_id serial primary key,
	post_name varchar(100),
	post_text text,
	post_link varchar(100) unique,
	post_date date
);