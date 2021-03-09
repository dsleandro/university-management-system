INSERT INTO roles (name) VALUES ('STUDENT');
INSERT INTO roles (name) VALUES ('ADMIN');

-- Students
INSERT INTO users (dni, first_name, last_name, academic_file, active) VALUES ('12345678','Maximo','Gutierrez','$2a$10$/fG/Fob8lV01Jjmz6DoA3O.baMzP2mwktn0iGCE8ePLQAJb/6xCqS', 1);

INSERT INTO users (dni, first_name, last_name, academic_file, active) VALUES ('11111111','Analia','Sosa', '$2a$10$8SHb6Y8m0Rnmkqt/RPD4YOj0rvKpOAaNvBSVPpmdBFIgxaJZ1mpDq', 1);

INSERT INTO users (dni, first_name, last_name, academic_file, active) VALUES ('22222222','Vanesa','Dunas', '$2a$10$DYVfqIj6O1cXdD5N0hMPvOzKNHgAhr0ht6/63AaHNldw5/Cj7OXcy', 1);

INSERT INTO users (dni, first_name, last_name, academic_file, active) VALUES ('33333333','Federico','Romero', '$2a$10$.L9GjyFmUhUF2oy2Z3L6y.5P3R4BemEPotvDXCQp./9F/lvl/M986', 1);

-- Admin
INSERT INTO users (dni, first_name, last_name, academic_file, active) VALUES ('0000','xx','yy', '$2a$10$gRTlhI62vGVt7K3ogBMDM.PC7O9Q3jpdGaehVd9mHuXucE6iXqHZS', 1);

-- Student Roles
INSERT INTO users_roles (user_id, role_id) VALUES (1, 1);
INSERT INTO users_roles (user_id, role_id) VALUES (2, 1);
INSERT INTO users_roles (user_id, role_id) VALUES (3, 1);
INSERT INTO users_roles (user_id, role_id) VALUES (4, 1);

-- Admin Role
INSERT INTO users_roles (user_id, role_id) VALUES (5, 2);
