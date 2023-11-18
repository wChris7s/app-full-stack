INSERT INTO "regiones" ("id", "nombre") VALUES (1, 'America');
INSERT INTO "regiones" ("id", "nombre") VALUES (2, 'Europa');
INSERT INTO "regiones" ("id", "nombre") VALUES (3, 'Asia');
INSERT INTO "regiones" ("id", "nombre") VALUES (4, 'Oceania');
INSERT INTO "regiones" ("id", "nombre") VALUES (5, 'Africa');


INSERT INTO "cliente" ("region_id", "apellido", "create_at", "email", "nombre") VALUES (4, 'Aranibar', '2020-03-12', 'cw.aranibar.solaligue@gmail.com', 'Christian');
INSERT INTO "cliente" ("region_id", "apellido", "create_at", "email", "nombre") VALUES (3, 'Gonzalez', '2019-08-25', 'gonzalez_23@example.com', 'Sofia');
INSERT INTO "cliente" ("region_id", "apellido", "create_at", "email", "nombre") VALUES (5, 'Smith', '2021-01-10', 'smith.j@examplemail.com', 'John');
INSERT INTO "cliente" ("region_id", "apellido", "create_at", "email", "nombre") VALUES (4, 'Martinez', '2018-11-30', 'm.martinez@emailprovider.com', 'Maria');
INSERT INTO "cliente" ("region_id", "apellido", "create_at", "email", "nombre") VALUES (1, 'Johnson', '2022-05-17', 'johnson_89@sampledomain.com', 'David');
INSERT INTO "cliente" ("region_id", "apellido", "create_at", "email", "nombre") VALUES (5, 'Garcia', '2017-04-22', 'garcia.alicia@emailhost.com', 'Alicia');
INSERT INTO "cliente" ("region_id", "apellido", "create_at", "email", "nombre") VALUES (3, 'Kim', '2019-12-05', 'kim_k@exampleemail.com', 'Daniel');
INSERT INTO "cliente" ("region_id", "apellido", "create_at", "email", "nombre") VALUES (2, 'Lopez', '2020-09-14', 'lopez.mia@samplemail.com', 'Mia');
INSERT INTO "cliente" ("region_id", "apellido", "create_at", "email", "nombre") VALUES (2, 'Chen', '2021-06-30', 'cchen@example.com', 'Chris');
INSERT INTO "cliente" ("region_id", "apellido", "create_at", "email", "nombre") VALUES (1, 'Brown', '2018-02-18', 'browndog@examplemail.com', 'Oliver');

INSERT INTO "usuarios" ("username", "password", "enabled", "nombre", "apellido", "email") VALUES ('andres', '$2a$10$vKwk/2VlNjqkXWH7.iwjfOueii4fHpllTlI14kV2aFAE/YsD5HEmO', true, 'Andres', 'Guzman', 'profesor@bolsa.idea.com');
INSERT INTO "usuarios" ("username", "password", "enabled", "nombre", "apellido", "email") VALUES ('admin', '$2a$10$wttBtQSbRHbPlscSitbcE.W.HlvsxCTnxjtlnfDQULrmfJcYJrfS.', true, 'John', 'Doe', 'jhon.doe@bolsa.idea.com');

INSERT INTO "roles" ("nombre") VALUES ('ROLE_USER');
INSERT INTO "roles" ("nombre") VALUES ('ROLE_ADMIN');

INSERT INTO "user_roles" ("user_id", "role_id") VALUES (1, 1);
INSERT INTO "user_roles" ("user_id", "role_id") VALUES (2, 2);
INSERT INTO "user_roles" ("user_id", "role_id") VALUES (2, 1);

