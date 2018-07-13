DELETE FROM user_roles;
DELETE FROM meals;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO meals(date_time,description,calories,user_id) VALUES
  ('2018-07-10T10:00:00','завтрак юзера',500,100000),
  ('2018-07-10T13:00:00','обед юзера',500,100000),
  ('2018-07-10T18:00:00','ужин юзера',1000,100000),
  ('2018-07-11T10:00:00','завтрак юзера',500,100000),
  ('2018-07-11T13:00:00','обед юзера',600,100000),
  ('2018-07-11T18:00:00','ужин юзера',1000,100000),
  ('2018-07-11T10:00:00','завтрак админа',500,100001),
  ('2018-07-11T13:00:00','обед админа',600,100001),
  ('2018-07-11T18:00:00','ужин админа',1000,100001);