insert into role (id, name) values(1, 'ADMIN');
insert into role (id, name) values(2, 'USER');
insert into user (id, username, email, password) values(1, 'admin', 'admin@mail.com', '$2a$10$csIVj5k8CB74npTTstUQuO3ObjmaM6y8cRMxvsKqpHmT3zNQxY/IK');
insert into user (id, username, email, password) values(2, 'user', 'user@mail.com', '$2a$10$m7BBzoGFKzb8e3/q5AK/SuC.qNKnyh.GcHkKnJYXsvJGQREhZe0Zu');
insert into user_role (user_id, role_id) values(1,1);
insert into user_role (user_id, role_id) values(1,2);
insert into user_role (user_id, role_id) values(2,2);