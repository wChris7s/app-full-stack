INSERT INTO "regiones" ("id", "nombre") VALUES (1, 'America');
INSERT INTO "regiones" ("id", "nombre") VALUES (2, 'Europa');
INSERT INTO "regiones" ("id", "nombre") VALUES (3, 'Asia');
INSERT INTO "regiones" ("id", "nombre") VALUES (4, 'Oceania');
INSERT INTO "regiones" ("id", "nombre") VALUES (5, 'Africa');


INSERT INTO "clientes" ("region_id", "apellido", "create_at", "email", "nombre") VALUES (4, 'Aranibar', '2020-03-12', 'cw.aranibar.solaligue@gmail.com', 'Christian');
INSERT INTO "clientes" ("region_id", "apellido", "create_at", "email", "nombre") VALUES (3, 'Gonzalez', '2019-08-25', 'gonzalez_23@example.com', 'Sofia');
INSERT INTO "clientes" ("region_id", "apellido", "create_at", "email", "nombre") VALUES (5, 'Smith', '2021-01-10', 'smith.j@examplemail.com', 'John');
INSERT INTO "clientes" ("region_id", "apellido", "create_at", "email", "nombre") VALUES (4, 'Martinez', '2018-11-30', 'm.martinez@emailprovider.com', 'Maria');
INSERT INTO "clientes" ("region_id", "apellido", "create_at", "email", "nombre") VALUES (1, 'Johnson', '2022-05-17', 'johnson_89@sampledomain.com', 'David');
INSERT INTO "clientes" ("region_id", "apellido", "create_at", "email", "nombre") VALUES (5, 'Garcia', '2017-04-22', 'garcia.alicia@emailhost.com', 'Alicia');
INSERT INTO "clientes" ("region_id", "apellido", "create_at", "email", "nombre") VALUES (3, 'Kim', '2019-12-05', 'kim_k@exampleemail.com', 'Daniel');
INSERT INTO "clientes" ("region_id", "apellido", "create_at", "email", "nombre") VALUES (2, 'Lopez', '2020-09-14', 'lopez.mia@samplemail.com', 'Mia');
INSERT INTO "clientes" ("region_id", "apellido", "create_at", "email", "nombre") VALUES (2, 'Chen', '2021-06-30', 'cchen@example.com', 'Chris');
INSERT INTO "clientes" ("region_id", "apellido", "create_at", "email", "nombre") VALUES (1, 'Brown', '2018-02-18', 'browndog@examplemail.com', 'Oliver');

INSERT INTO "usuarios" ("username", "password", "enabled", "nombre", "apellido", "email") VALUES ('Chris', '$2a$10$vKwk/2VlNjqkXWH7.iwjfOueii4fHpllTlI14kV2aFAE/YsD5HEmO', true, 'Christian', 'Aranibar', 'user@bolsa.idea.com');
INSERT INTO "usuarios" ("username", "password", "enabled", "nombre", "apellido", "email") VALUES ('admin', '$2a$10$wttBtQSbRHbPlscSitbcE.W.HlvsxCTnxjtlnfDQULrmfJcYJrfS.', true, 'John', 'Doe', 'jhon.doe@bolsa.idea.com');

INSERT INTO "roles" ("nombre") VALUES ('ROLE_USER');
INSERT INTO "roles" ("nombre") VALUES ('ROLE_ADMIN');

INSERT INTO "user_roles" ("user_id", "role_id") VALUES (1, 1);
INSERT INTO "user_roles" ("user_id", "role_id") VALUES (2, 2);
INSERT INTO "user_roles" ("user_id", "role_id") VALUES (2, 1);

INSERT INTO "productos" ("nombre", "precio", "create_at") VALUES ('Iphone 12 Pro Max', 5200, NOW());
INSERT INTO "productos" ("nombre", "precio", "create_at") VALUES ('Samsung Galaxy S21 Ultra', 4800, NOW());
INSERT INTO "productos" ("nombre", "precio", "create_at") VALUES ('Sony PlayStation 5', 4500, NOW());
INSERT INTO "productos" ("nombre", "precio", "create_at") VALUES ('Canon EOS R5', 12000, NOW());
INSERT INTO "productos" ("nombre", "precio", "create_at") VALUES ('LG OLED C1 65"', 9000, NOW());
INSERT INTO "productos" ("nombre", "precio", "create_at") VALUES ('Nike Air Max 270', 600, NOW());
INSERT INTO "productos" ("nombre", "precio", "create_at") VALUES ('Adidas Ultraboost 21', 200, NOW());
INSERT INTO "productos" ("nombre", "precio", "create_at") VALUES ('MacBook Pro 2023', 2500, NOW());
INSERT INTO "productos" ("nombre", "precio", "create_at") VALUES ('Samsung Galaxy S22 Ultra', 1200, NOW());
INSERT INTO "productos" ("nombre", "precio", "create_at") VALUES ('PlayStation 5', 500, NOW());
INSERT INTO "productos" ("nombre", "precio", "create_at") VALUES ('GoPro Hero 10', 400, NOW());
INSERT INTO "productos" ("nombre", "precio", "create_at") VALUES ('Nike Air Force 1', 150, NOW());
INSERT INTO "productos" ("nombre", "precio", "create_at") VALUES ('Canon EOS R5', 3500, NOW());
INSERT INTO "productos" ("nombre", "precio", "create_at") VALUES ('Apple Watch Series 7', 400, NOW());
INSERT INTO "productos" ("nombre", "precio", "create_at") VALUES ('Xbox Series X', 550, NOW());
INSERT INTO "productos" ("nombre", "precio", "create_at") VALUES ('DJI Mavic Air 2', 800, NOW());

INSERT INTO "facturas" ("cliente_id", "descripcion", "observacion", "create_at") VALUES (1, 'Factura de prueba', 'Esta es una factura de prueba', NOW());
INSERT INTO "facturas" ("cliente_id", "descripcion", "observacion", "create_at") VALUES (2, 'Factura de prueba', 'Esta es una factura de prueba', NOW());
INSERT INTO "facturas" ("cliente_id", "descripcion", "observacion", "create_at") VALUES (3, 'Factura de prueba', 'Esta es una factura de prueba', NOW());
INSERT INTO "facturas" ("cliente_id", "descripcion", "observacion", "create_at") VALUES (4, 'Factura de prueba', 'Esta es una factura de prueba', NOW());
INSERT INTO "facturas" ("cliente_id", "descripcion", "observacion", "create_at") VALUES (5, 'Factura de prueba', 'Esta es una factura de prueba', NOW());
INSERT INTO "facturas" ("cliente_id", "descripcion", "observacion", "create_at") VALUES (6, 'Factura de prueba', 'Esta es una factura de prueba', NOW());
INSERT INTO "facturas" ("cliente_id", "descripcion", "observacion", "create_at") VALUES (7, 'Factura de prueba', 'Esta es una factura de prueba', NOW());
INSERT INTO "facturas" ("cliente_id", "descripcion", "observacion", "create_at") VALUES (8, 'Factura de prueba', 'Esta es una factura de prueba', NOW());


INSERT INTO "facturas_items" ("cantidad", "factura_id", "producto_id") VALUES (1, 1, 1);
INSERT INTO "facturas_items" ("cantidad", "factura_id", "producto_id") VALUES (2, 1, 2);
INSERT INTO "facturas_items" ("cantidad", "factura_id", "producto_id") VALUES (3, 2, 3);
INSERT INTO "facturas_items" ("cantidad", "factura_id", "producto_id") VALUES (2, 2, 4);
INSERT INTO "facturas_items" ("cantidad", "factura_id", "producto_id") VALUES (1, 3, 5);
INSERT INTO "facturas_items" ("cantidad", "factura_id", "producto_id") VALUES (2, 3, 6);
INSERT INTO "facturas_items" ("cantidad", "factura_id", "producto_id") VALUES (3, 4, 7);
INSERT INTO "facturas_items" ("cantidad", "factura_id", "producto_id") VALUES (1, 4, 8);
INSERT INTO "facturas_items" ("cantidad", "factura_id", "producto_id") VALUES (2, 5, 9);
INSERT INTO "facturas_items" ("cantidad", "factura_id", "producto_id") VALUES (3, 5, 10);
INSERT INTO "facturas_items" ("cantidad", "factura_id", "producto_id") VALUES (2, 6, 11);
INSERT INTO "facturas_items" ("cantidad", "factura_id", "producto_id") VALUES (1, 6, 12);