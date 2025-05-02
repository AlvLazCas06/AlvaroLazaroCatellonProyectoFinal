INSERT INTO consola (console_id, nombre, description, fabricante, precio, cantidad, ventas,fecha_lanzamiento, llegada_al_mercado, imagen) VALUES (1, 'Wii', 'Nintendo', 'Nintendo', 99.99,3, 2, '2006-11-12', '2021-12-08', 'https://upload.wikimedia.org/wikipedia/commons/thumb/8/83/Wii_console.png/250px-Wii_console.png');
INSERT INTO consola (console_id, nombre, description, fabricante, precio, cantidad, ventas,fecha_lanzamiento, llegada_al_mercado, imagen) VALUES (2, 'Nintendo DS', 'Nintendo', 'Nintendo', 99.99,3, 2, '2004-11-05', '2021-12-08','https://upload.wikimedia.org/wikipedia/commons/thumb/5/56/Nintendo-DS-Lite-Black-Open.png/250px-Nintendo-DS-Lite-Black-Open.png');

ALTER SEQUENCE consola_seq RESTART WITH 1000;

INSERT INTO juego (id, nombre, description, precio, cantidad, ventas, genero, num_jugadores, console_id, fecha_lanzamiento, llegada_al_mercado, ruta_imagen) VALUES (1, 'New Super Mario Bros Wii', 'Plataforma', 59.99, 1, 0, 'Plataforma', 1, 1, '2009-11-12', '2021-12-08','https://art.gametdb.com/wii/cover3D/ES/SMNP01.png?1317736131');
INSERT INTO juego (id, nombre, description, precio, cantidad, ventas, genero, num_jugadores, console_id, fecha_lanzamiento, llegada_al_mercado, ruta_imagen) VALUES (2, 'Inazuma Eleven 2 Tormenta de Fuego', 'J-RPG', 59.99, 2, 0, 'J-RPG', 1, 2, '2012-03-12', '2021-12-08','https://art.gametdb.com/ds/box/EN/BEES.png?1502910585');

ALTER SEQUENCE juego_seq RESTART WITH 1000;