Table creation script:
```
create table user_model(
userid int not null auto_increment,
username varchar(25) not null,
mobile varchar(25) not null,
address varchar(255) not null,
email varchar(25) not null,
password varchar(25) not null,
activated boolean default false,
role varchar(25) default 'user',
primary key (userid)
);
```

Admin user creation:
```
insert into user_model(username, mobile, address, email, password, activated, role) values ('admin', '12345', 'abcd', 'admin@admin.com', 'Admin@123',true, 'admin');
```
