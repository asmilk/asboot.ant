insert into users(username, password, enabled) values('admin@oauth.io', '{noop}123456', true);
insert into users(username, password, enabled) values('staff@oauth.io', '{noop}123456', true);
insert into users(username, password, enabled) values('user@oauth.io', '{noop}123456', true);
insert into users(username, password, enabled) values('guest@oauth.io', '{noop}123456', true);
insert into users(username, password, enabled) values('hacker@oauth.io', '{noop}123456', false);

insert into authorities(username, authority) values('admin@oauth.io', 'ROLE_ADMIN');
insert into authorities(username, authority) values('staff@oauth.io', 'ROLE_STAFF');
insert into authorities(username, authority) values('user@oauth.io', 'ROLE_USER');
insert into authorities(username, authority) values('guest@oauth.io', 'ROLE_GUEST');
insert into authorities(username, authority) values('hacker@oauth.io', 'ROLE_HACKER');

insert into groups(group_name) values('GROUP_ADMIN');
insert into groups(group_name) values('GROUP_STAFF');
insert into groups(group_name) values('GROUP_USER');
insert into groups(group_name) values('GROUP_GUEST');
insert into groups(group_name) values('GROUP_HACKER');

insert into group_members(group_id, username) values(1, 'admin@oauth.io');
insert into group_members(group_id, username) values(2, 'staff@oauth.io');
insert into group_members(group_id, username) values(3, 'user@oauth.io');
insert into group_members(group_id, username) values(4, 'guest@oauth.io');
insert into group_members(group_id, username) values(5, 'hacker@oauth.io');

insert into group_authorities (group_id, authority) values (1, 'ROLE_ADMIN');
insert into group_authorities (group_id, authority) values (2, 'ROLE_STAFF');
insert into group_authorities (group_id, authority) values (3, 'ROLE_USER');
insert into group_authorities (group_id, authority) values (4, 'ROLE_GUEST');
insert into group_authorities (group_id, authority) values (5, 'ROLE_HACKER');