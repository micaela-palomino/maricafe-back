-- Sample data for Maricafe database
-- This file will be executed when the application starts

-- Insert categories
INSERT INTO categories (name) VALUES 
('tortas'),
('tazas'),
('catering');

-- Insert products
INSERT INTO products (title, description, price, category_id, stock) VALUES 
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
('Catering Personalizado', 'Catering adaptado a tus necesidades específicas. Consulta por opciones veganas, sin TACC y más.', 150000, 3, 5);