# java-spring-website

sql:
--password is 'q1w2e3'
INSERT INTO `projects_kevin.user` (`id`,`email`,`password`,`username`) VALUES (1,'khu6244@gmail.com','$2a$10$.BU0h2psmOPcwa8p4FLhw.vCLLpeap91IYZWmFLQ2REBxk3P9UsOK','khu6244');
INSERT INTO `projects_kevin.user` (`id`,`email`,`password`,`username`) VALUES (58,'guest@email.com','$2a$10$.BU0h2psmOPcwa8p4FLhw.vCLLpeap91IYZWmFLQ2REBxk3P9UsOK','guest');

INSERT INTO `role` (`id`,`name`) VALUES (1,'ADMIN');
INSERT INTO `role` (`id`,`name`) VALUES (2,'USER');

INSERT INTO project (id, name, link)
values (1, 'Mechanical Keyboard', 'keyboard');
INSERT INTO project (id, name, link)
values (2, 'Wooden Coffee Table', 'table');