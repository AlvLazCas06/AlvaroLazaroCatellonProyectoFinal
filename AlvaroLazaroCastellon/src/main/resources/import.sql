
INSERT INTO Consola (console_id, nombre, description, fabricante, fecha_lanzamiento, llegadaAlMercado, imagen) VALUES
(1, 'Wii', 'Nintendo', 'Nintendo', '2006-11-12', '2021-12-08', 'https://example.com/wii.jpg'),
(2, 'Nintendo DS', 'Nintendo', 'Nintendo', '2004-11-05', '2021-12-08', 'https://example.com/nintendo-ds.jpg');


INSERT INTO Juego (id, nombre, description, precio, cantidad, ventas, nuevo, genero, numJugadores, consola_id, fecha_lanzamiento, llegadaAlMercado, rutaImagen) VALUES
(1, 'New Super Mario Bros Wii', 'Plataforma', 59.99, 1, 0, true, 'Plataforma', 1, 1, '2009-11-12', '2021-12-08', 'https://art.gametdb.com/wii/cover3D/ES/SMNP01.png?1317736131'),
(2, 'Inazuma Eleven 2 Tormenta de Fuego', 'J-RPG', 59.99, 2, 0, true, 'J-RPG', 1, 2, '2012-03-12', '2021-12-08', 'https://art.gametdb.com/ds/box/EN/BEES.png?1502910585');