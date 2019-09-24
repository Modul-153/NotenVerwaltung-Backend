-- UserManager
# getAllComplex()
select user_id,
       firstname,
       lastname,
       username,
       street,
       number,
       city.city_id as city_id,
       city.name    as city_name,
       city.zipcode as zipcode
from `user`
         join city on `user`.city_id = city.city_id
order by user_id;

# getAllSimple()
select user_id, firstname, lastname, username, street, number, city_id
from user;

# getComplex(id)
select user_id,
       firstname,
       lastname,
       username,
       street,
       number,
       city.city_id as city_id,
       city.name    as city_name,
       city.zipcode as zipcode
from `user`
         join city on `user`.city_id = city.city_id
where user_id = 1;

# getSimple(id)
select user_id, firstname, lastname, username, street, number, city_id
from user
where user_id = 1;

# updateComplex(value)
BEGIN;
insert into city (city_id, name, zipcode)
VALUES (1, 'aca', 12345),
       (2, 'laaa', 54321)
on duplicate KEY UPDATE name=VALUES(name),
                        zipcode=VALUES(zipcode);
insert into user (user_id, firstname, lastname, username, number, street, city_id)
values (1, 'firstname', 'lastname', 'username', 10, 'mainstreet', 10)
on duplicate key update firstname=VALUES(firstname),
                        lastname=VALUES(lastname),
                        username=VALUES(username),
                        number=VALUES(number),
                        street=VALUES(street),
                        city_id=VALUES(city_id)
;
COMMIT;

# updateSimple(value)
insert into user (user_id, firstname, lastname, username, number, street, city_id)
values (1, 'firstname', 'lastname', 'username', 10, 'mainstreet', 10)
on duplicate key update firstname=VALUES(firstname),
                        lastname=VALUES(lastname),
                        username=VALUES(username),
                        number=VALUES(number),
                        street=VALUES(street),
                        city_id=VALUES(city_id)
;


-- teacherManager

# getAllTeachersComplex()
select teacher_id, firstname, lastname, username, number, street, city_id
from teacher
         join user u on teacher.user_id = u.user_id
order by teacher_id;

# getAllTeachersSimple()
select teacher_id, user_id from teacher order by teacher_id;


select teacher_id, firstname, lastname, username, number, street, city_id
from teacher
         join user u on teacher.user_id = u.user_id
where teacher_id = 1;