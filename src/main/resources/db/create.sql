drop table if exists posts;
create table posts(
	post_id serial primary key,
	post_name text,
	post_text text,
	post_link text unique,
	post_date varchar(100)
);