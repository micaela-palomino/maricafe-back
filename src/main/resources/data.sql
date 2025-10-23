-- Sample data for Maricafe database
-- This file will be executed when the application starts

-- Insert categories (ignore duplicates)
INSERT IGNORE INTO categories (name) VALUES 
('Tortas'),
('Tazas'),
('Catering');

-- Insert products (ignore duplicates)
INSERT IGNORE INTO products (title, description, price, category_id, stock) VALUES 
-- Tortas
('Rainbow Cake Clásica', 'Torta de capas del arcoíris con crema de vainilla. Perfecta para celebraciones especiales.', 65000, 1, 10),
('Rainbow Cake Vegana', 'Versión 100% vegana de nuestra famosa torta arcoíris. Mismos colores vibrantes, sabor increíble sin ingredientes de origen animal.', 75000, 1, 8),
('Rainbow Cake Sin TACC', 'Torta arcoíris libre de gluten, perfecta para celíacos. Elaborada con harinas alternativas sin comprometer el sabor.', 80000, 1, 5),
('Carrot Cake Artesanal', 'Torta de zanahoria húmeda con especias y frosting de queso crema. Un clásico reinventado con nuestro toque especial.', 55000, 1, 7),
('Torta Red Velvet Pride', 'Red velvet con decoración Pride. Bizcocho rojo aterciopelado con crema cheese y detalles del arcoíris.', 70000, 1, 6),
('Cheesecake Arcoíris', 'Cheesecake cremoso con capas de colores naturales. Base de galletas y topping de frutas frescas.', 68000, 1, 4),

-- Tazas
('Taza Pride "Love is Love"', 'Taza cerámica blanca con diseño Pride y el mensaje "Love is Love". Perfecta para tu café matutino con amor.', 12000, 2, 15),
('Taza Arcoíris Personalizada', 'Taza con diseño arcoíris personalizable. Agrega tu nombre o mensaje especial para hacerla única.', 15000, 2, 12),
('Set de 4 Tazas Diversidad', 'Set de 4 tazas con diferentes banderas de la diversidad: Pride, Trans, Bisexual y Pansexual.', 45000, 2, 8),
('Taza "Be Yourself"', 'Taza con mensaje motivacional y diseño inclusivo. Perfecta para recordarte que eres único y especial.', 13000, 2, 10),
('Taza Trans Pride', 'Taza con los colores de la bandera trans. Diseñada para celebrar la identidad y el orgullo trans.', 14000, 2, 6),
('Taza Bisexual Pride', 'Taza con los colores de la bandera bisexual. Celebra la diversidad en la orientación sexual.', 14000, 2, 6),

-- Catering
('Catering Arcoíris - 20 personas', 'Servicio completo de catering con temática arcoíris. Incluye tortas, bebidas y decoración temática.', 180000, 3, 3),
('Catering Vegano - 15 personas', 'Catering 100% vegano con opciones variadas. Perfecto para eventos inclusivos y conscientes.', 220000, 3, 2),
('Catering Sin TACC - 12 personas', 'Catering completamente libre de gluten. Ideal para eventos donde hay personas celíacas.', 200000, 3, 2),
('Catering Pride - 25 personas', 'Catering temático Pride con decoración y comida colorida. Celebra la diversidad en tu evento.', 250000, 3, 1),
('Catering Personalizado', 'Catering adaptado a tus necesidades específicas. Consulta por opciones veganas, sin TACC y más.', 150000, 3, 5),

-- Products with zero stock for testing admin vs user visibility
-- Tortas with zero stock
('Torta Chocolate Premium', 'Torta de chocolate belga con ganache y decoración artesanal. Edición limitada agotada.', 85000, 1, 0),
('Torta Tiramisu Italiana', 'Auténtica tiramisu italiana con mascarpone y café. Temporada agotada.', 72000, 1, 0),
('Torta Lemon Meringue', 'Torta de limón con merengue italiano. Sabor cítrico y refrescante.', 58000, 1, 0),

-- Tazas with zero stock
('Taza Edición Limitada Pride 2024', 'Taza conmemorativa Pride 2024 con diseño exclusivo. Edición agotada.', 18000, 2, 0),
('Taza Termo Arcoíris', 'Termo de acero inoxidable con diseño arcoíris. Perfecto para mantener la temperatura.', 25000, 2, 0),
('Set VIP Pride Collection', 'Set premium de 6 tazas con diseños exclusivos de la colección Pride. Agotado.', 75000, 2, 0),

-- Catering with zero stock
('Catering Premium Pride - 50 personas', 'Catering de lujo para eventos Pride grandes. Incluye decoración completa y servicio premium.', 450000, 3, 0),
('Catering Corporativo Inclusivo', 'Catering especializado para empresas con políticas de diversidad e inclusión.', 320000, 3, 0),
('Catering Wedding Pride', 'Catering especializado para bodas con temática Pride. Servicio completo de lujo.', 380000, 3, 0);

-- Insert product attributes (ignore duplicates)
INSERT IGNORE INTO product_attributes (name, data_type, description, required, select_options, category_id) VALUES 
-- Attributes for Tortas (category_id = 1)
('Vegano', 'boolean', 'Indica si la torta es vegana (sin ingredientes de origen animal)', false, null, 1),
('Sin TACC', 'boolean', 'Indica si la torta es libre de gluten', false, null, 1),
('Tamaño', 'select', 'Tamaño de la torta', true, 'Pequeña,Mediana,Grande,Extra Grande', 1),
('Sabor', 'select', 'Sabor principal de la torta', true, 'Vainilla,Chocolate,Frutilla,Limón,Red Velvet,Cheesecake', 1),
('Decoración', 'text', 'Descripción de la decoración especial', false, null, 1),

-- Attributes for Tazas (category_id = 2)
('Material', 'select', 'Material de la taza', true, 'Cerámica,Porcelana,Vidrio,Plástico', 2),
('Capacidad', 'select', 'Capacidad de la taza en ml', true, '200,250,300,350,400', 2),
('Personalizable', 'boolean', 'Indica si la taza puede ser personalizada', false, null, 2),
('Diseño', 'text', 'Descripción del diseño o mensaje', false, null, 2),

-- Attributes for Catering (category_id = 3)
('Número de Personas', 'number', 'Número de personas que puede atender el catering', true, null, 3),
('Tipo de Evento', 'select', 'Tipo de evento para el cual está diseñado', true, 'Cumpleaños,Boda,Corporativo,Pride,Otro', 3),
('Incluye Decoración', 'boolean', 'Indica si incluye decoración temática', false, null, 3),
('Opciones Dietéticas', 'select', 'Opciones dietéticas disponibles', true, 'Regular,Vegano,Sin TACC,Mixto', 3);

-- Insert sample attribute values for products
-- Note: Attribute IDs are assigned in the order they appear in the INSERT statement above
-- Tortas attributes: 1=Vegano, 2=Sin TACC, 3=Tamaño, 4=Sabor, 5=Decoración
-- Tazas attributes: 6=Material, 7=Capacidad, 8=Personalizable, 9=Diseño  
-- Catering attributes: 10=Número de Personas, 11=Tipo de Evento, 12=Incluye Decoración, 13=Opciones Dietéticas

INSERT IGNORE INTO product_attribute_values (product_id, attribute_id, value) VALUES 
-- Rainbow Cake Clásica (product_id = 1)
(1, 1, 'false'), -- No es vegana
(1, 2, 'false'), -- No es sin TACC
(1, 3, 'Grande'), -- Tamaño grande
(1, 4, 'Vainilla'), -- Sabor vainilla
(1, 5, 'Capas del arcoíris con crema de vainilla'), -- Decoración

-- Rainbow Cake Vegana (product_id = 2)
(2, 1, 'true'), -- Es vegana
(2, 2, 'false'), -- No es sin TACC
(2, 3, 'Grande'), -- Tamaño grande
(2, 4, 'Vainilla'), -- Sabor vainilla
(2, 5, 'Capas del arcoíris 100% veganas'), -- Decoración

-- Rainbow Cake Sin TACC (product_id = 3)
(3, 1, 'false'), -- No es vegana
(3, 2, 'true'), -- Es sin TACC
(3, 3, 'Grande'), -- Tamaño grande
(3, 4, 'Vainilla'), -- Sabor vainilla
(3, 5, 'Capas del arcoíris sin gluten'), -- Decoración

-- Carrot Cake Artesanal (product_id = 4)
(4, 1, 'false'), -- No es vegana
(4, 2, 'false'), -- No es sin TACC
(4, 3, 'Mediana'), -- Tamaño mediana
(4, 4, 'Chocolate'), -- Sabor chocolate (carrot cake con especias)
(4, 5, 'Frosting de queso crema con especias'), -- Decoración

-- Torta Red Velvet Pride (product_id = 5)
(5, 1, 'false'), -- No es vegana
(5, 2, 'false'), -- No es sin TACC
(5, 3, 'Grande'), -- Tamaño grande
(5, 4, 'Red Velvet'), -- Sabor red velvet
(5, 5, 'Decoración Pride con detalles del arcoíris'), -- Decoración

-- Cheesecake Arcoíris (product_id = 6)
(6, 1, 'false'), -- No es vegana
(6, 2, 'false'), -- No es sin TACC
(6, 3, 'Mediana'), -- Tamaño mediana
(6, 4, 'Cheesecake'), -- Sabor cheesecake
(6, 5, 'Capas de colores naturales con topping de frutas'), -- Decoración

-- Taza Pride "Love is Love" (product_id = 7)
(7, 6, 'Cerámica'), -- Material cerámica
(7, 7, '300'), -- Capacidad 300ml
(7, 8, 'false'), -- No personalizable
(7, 9, 'Diseño Pride con mensaje "Love is Love"'), -- Diseño

-- Taza Arcoíris Personalizada (product_id = 8)
(8, 6, 'Cerámica'), -- Material cerámica
(8, 7, '350'), -- Capacidad 350ml
(8, 8, 'true'), -- Es personalizable
(8, 9, 'Diseño arcoíris personalizable'), -- Diseño

-- Set de 4 Tazas Diversidad (product_id = 9)
(9, 6, 'Cerámica'), -- Material cerámica
(9, 7, '300'), -- Capacidad 300ml
(9, 8, 'false'), -- No personalizable (set predefinido)
(9, 9, 'Set con banderas Pride, Trans, Bisexual y Pansexual'), -- Diseño

-- Taza "Be Yourself" (product_id = 10)
(10, 6, 'Cerámica'), -- Material cerámica
(10, 7, '300'), -- Capacidad 300ml
(10, 8, 'false'), -- No personalizable
(10, 9, 'Mensaje motivacional "Be Yourself" con diseño inclusivo'), -- Diseño

-- Taza Trans Pride (product_id = 11)
(11, 6, 'Cerámica'), -- Material cerámica
(11, 7, '300'), -- Capacidad 300ml
(11, 8, 'false'), -- No personalizable
(11, 9, 'Diseño con colores de la bandera trans'), -- Diseño

-- Taza Bisexual Pride (product_id = 12)
(12, 6, 'Cerámica'), -- Material cerámica
(12, 7, '300'), -- Capacidad 300ml
(12, 8, 'false'), -- No personalizable
(12, 9, 'Diseño con colores de la bandera bisexual'), -- Diseño

-- Catering Arcoíris - 20 personas (product_id = 13)
(13, 10, '20'), -- 20 personas
(13, 11, 'Pride'), -- Tipo Pride
(13, 12, 'true'), -- Incluye decoración
(13, 13, 'Mixto'), -- Opciones mixtas

-- Catering Vegano - 15 personas (product_id = 14)
(14, 10, '15'), -- 15 personas
(14, 11, 'Otro'), -- Tipo otro
(14, 12, 'false'), -- No incluye decoración
(14, 13, 'Vegano'), -- Solo vegano

-- Catering Sin TACC - 12 personas (product_id = 15)
(15, 10, '12'), -- 12 personas
(15, 11, 'Otro'), -- Tipo otro
(15, 12, 'false'), -- No incluye decoración
(15, 13, 'Sin TACC'), -- Solo sin TACC

-- Catering Pride - 25 personas (product_id = 16)
(16, 10, '25'), -- 25 personas
(16, 11, 'Pride'), -- Tipo Pride
(16, 12, 'true'), -- Incluye decoración
(16, 13, 'Mixto'), -- Opciones mixtas

-- Catering Personalizado (product_id = 17)
(17, 10, '20'), -- 20 personas (estimado)
(17, 11, 'Otro'), -- Tipo otro
(17, 12, 'true'), -- Incluye decoración
(17, 13, 'Mixto'), -- Opciones mixtas

-- Products with zero stock attribute values
-- Torta Chocolate Premium (product_id = 18)
(18, 1, 'false'), -- No es vegana
(18, 2, 'false'), -- No es sin TACC
(18, 3, 'Extra Grande'), -- Tamaño extra grande
(18, 4, 'Chocolate'), -- Sabor chocolate
(18, 5, 'Ganache de chocolate belga con decoración artesanal'), -- Decoración

-- Torta Tiramisu Italiana (product_id = 19)
(19, 1, 'false'), -- No es vegana
(19, 2, 'false'), -- No es sin TACC
(19, 3, 'Grande'), -- Tamaño grande
(19, 4, 'Chocolate'), -- Sabor chocolate (tiramisu)
(19, 5, 'Café italiano con mascarpone y cacao'), -- Decoración

-- Torta Lemon Meringue (product_id = 20)
(20, 1, 'false'), -- No es vegana
(20, 2, 'false'), -- No es sin TACC
(20, 3, 'Mediana'), -- Tamaño mediana
(20, 4, 'Limón'), -- Sabor limón
(20, 5, 'Merengue italiano con ralladura de limón'), -- Decoración

-- Taza Edición Limitada Pride 2024 (product_id = 21)
(21, 6, 'Porcelana'), -- Material porcelana
(21, 7, '350'), -- Capacidad 350ml
(21, 8, 'false'), -- No personalizable
(21, 9, 'Diseño exclusivo Pride 2024 conmemorativo'), -- Diseño

-- Taza Termo Arcoíris (product_id = 22)
(22, 6, 'Acero Inoxidable'), -- Material acero inoxidable
(22, 7, '500'), -- Capacidad 500ml
(22, 8, 'false'), -- No personalizable
(22, 9, 'Diseño arcoíris en acero inoxidable'), -- Diseño

-- Set VIP Pride Collection (product_id = 23)
(23, 6, 'Porcelana'), -- Material porcelana
(23, 7, '300'), -- Capacidad 300ml
(23, 8, 'false'), -- No personalizable
(23, 9, 'Set premium de 6 tazas con diseños exclusivos Pride'), -- Diseño

-- Catering Premium Pride - 50 personas (product_id = 24)
(24, 10, '50'), -- 50 personas
(24, 11, 'Pride'), -- Tipo Pride
(24, 12, 'true'), -- Incluye decoración
(24, 13, 'Mixto'), -- Opciones mixtas

-- Catering Corporativo Inclusivo (product_id = 25)
(25, 10, '30'), -- 30 personas
(25, 11, 'Corporativo'), -- Tipo corporativo
(25, 12, 'true'), -- Incluye decoración
(25, 13, 'Mixto'), -- Opciones mixtas

-- Catering Wedding Pride (product_id = 26)
(26, 10, '40'), -- 40 personas
(26, 11, 'Boda'), -- Tipo boda
(26, 12, 'true'), -- Incluye decoración
(26, 13, 'Mixto'); -- Opciones mixtas

-- Insert sample discounts (ignore duplicates)
INSERT IGNORE INTO discounts (producto_id, discount_percentage) VALUES 
-- Discounts for popular tortas
(1, 15.0), -- Rainbow Cake Clásica - 15% off
(2, 20.0), -- Rainbow Cake Vegana - 20% off (promoting vegan options)
(3, 10.0), -- Rainbow Cake Sin TACC - 10% off
(4, 25.0), -- Carrot Cake Artesanal - 25% off (special promotion)
(5, 18.0), -- Torta Red Velvet Pride - 18% off

-- Discounts for tazas
(7, 12.0), -- Taza Pride "Love is Love" - 12% off
(8, 15.0), -- Taza Arcoíris Personalizada - 15% off
(9, 30.0), -- Set de 4 Tazas Diversidad - 30% off (bulk discount)
(10, 10.0), -- Taza "Be Yourself" - 10% off

-- Discounts for catering services
(13, 20.0), -- Catering Arcoíris - 20 personas - 20% off
(14, 25.0), -- Catering Vegano - 15 personas - 25% off (promoting vegan catering)
(16, 15.0), -- Catering Pride - 25 personas - 15% off
(17, 35.0); -- Catering Personalizado - 35% off (flexible pricing promotion)