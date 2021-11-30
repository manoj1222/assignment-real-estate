drop table if exists property;

create table property (
    id int auto_increment primary key,
    name varchar(250) not null,
    email varchar(250) not null,
    num_of_bedrooms int not null,
    area_of_each_bedroom int not null,
    num_of_bathrooms int not null,
    area_of_each_bathroom int not null,
    total_area int not null,
    price float not null
);

create index price_idx on property(price);
create index num_of_bedrooms_idx on property(num_of_bedrooms);
create index num_of_bathrooms_idx on property(num_of_bathrooms);
