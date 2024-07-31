-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 25-07-2024 a las 22:53:33
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `gestion_de_tareas`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `avance`
--

CREATE TABLE `avance` (
  `Id_Avance` int(11) NOT NULL,
  `porcentaje` double NOT NULL,
  `fecha` date DEFAULT NULL,
  `tarea` int(11) NOT NULL,
  `tablero` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `avance`
--

INSERT INTO `avance` (`Id_Avance`, `porcentaje`, `fecha`, `tarea`, `tablero`) VALUES
(1, 50, '2024-02-25', 1, 2),
(2, 100, '2024-07-15', 2, 1),
(3, 100, '2024-06-14', 3, 1),
(4, 50, '2024-06-11', 4, 1),
(5, 100, '2024-05-20', 5, 1),
(6, 0, '2024-05-23', 6, 1),
(7, 100, '2024-04-23', 7, 2),
(8, 50, '2024-04-16', 8, 1),
(9, 100, '2024-07-14', 9, 2),
(10, 50, '2024-07-28', 10, 2),
(11, 100, '2024-07-18', 11, 4),
(12, 100, '2024-05-15', 12, 3),
(13, 100, '2024-05-10', 13, 3),
(14, 100, '2024-06-10', 14, 3),
(15, 100, '2024-05-22', 15, 3),
(16, 0, '2024-03-10', 16, 3),
(17, 100, '2024-02-03', 17, 4),
(18, 100, '2024-03-26', 18, 3),
(19, 50, '2024-05-10', 19, 4),
(20, 50, '2024-06-08', 20, 4);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `equipo`
--

CREATE TABLE `equipo` (
  `Id_Equipo` int(11) NOT NULL,
  `nombre_Equipo` varchar(30) NOT NULL,
  `proyecto` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `equipo`
--

INSERT INTO `equipo` (`Id_Equipo`, `nombre_Equipo`, `proyecto`) VALUES
(1, 'Wigou BackEnd', 1),
(2, 'Wigou FrontEnd', 1),
(3, 'Towgee BackEnd', 2),
(4, 'Towgee FrontEnd', 2);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `persona`
--

CREATE TABLE `persona` (
  `Id_Persona` int(11) NOT NULL,
  `nombre` varchar(40) NOT NULL,
  `ape_Paterno` varchar(25) NOT NULL,
  `ape_Materno` varchar(25) NOT NULL,
  `correo_Electronico` varchar(50) DEFAULT NULL,
  `edad` int(11) NOT NULL,
  `genero` varchar(30) NOT NULL,
  `puesto` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `persona`
--

INSERT INTO `persona` (`Id_Persona`, `nombre`, `ape_Paterno`, `ape_Materno`, `correo_Electronico`, `edad`, `genero`, `puesto`) VALUES
(1, 'Andres', 'Cuevas', 'Zapata', 'andres.cuevas@gmail.com', 24, 'Masculino', 1),
(2, 'Alejandro', 'Torres', 'García', 'alejandro.torres@gmail.com', 19, 'Masculino', 2),
(3, 'Guillermo', 'Martínez', 'Ramírez', 'guillermo.martínez@gmail.com', 20, 'Masculino', 2),
(4, 'Carlos', 'Pérez', 'Ramírez', 'carlos.pérez@gmail.com', 20, 'Masculino', 2),
(5, 'Carmen', 'Torres', 'Pérez', 'carmen.torres@gmail.com', 19, 'Femenino', 2),
(6, 'Luis', 'Hernández', 'Martínez', 'luis.hernández@gmail.com', 18, 'Masculino', 2),
(7, 'Beatriz', 'Pérez', 'Sánchez', 'beatriz.pérez@gmail.com', 19, 'Femenino', 2),
(8, 'Brandon', 'Gonzalez', 'Gonzalez', 'brandon.gonzalez@gmail.com', 20, 'Masculino', 1),
(9, 'Diego', 'Ortiz', 'Castillo', 'diego.ortiz@gmail.com', 20, 'Masculino', 2),
(10, 'Jorge', 'Hernández', 'Sánchez', 'jorge.hernández@gmail.com', 21, 'Masculino', 2),
(11, 'Francisco', 'Ortiz', 'Torres', 'francisco.ortiz@gmail.com', 21, 'Masculino', 2),
(12, 'Daniela', 'Castillo', 'Pérez', 'daniela.castillo@gmail.com', 20, 'Femenino', 2),
(13, 'Isabel', 'Martínez', 'Castillo', 'isabel.martínez@gmail.com', 19, 'Femenino', 2),
(14, 'Juan', 'Pérez', 'Torres', 'juan.pérez@gmail.com', 19, 'Masculino', 2),
(15, 'Jabes', 'Llamas', 'Zamudio', 'jabes.llamas@gmail.com', 19, 'Masculino', 2),
(16, 'Gustavo', 'Tello', 'Bernal', 'tello@gmail.com', 24, 'Masculino', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `proyecto`
--

CREATE TABLE `proyecto` (
  `Id_Proyecto` int(11) NOT NULL,
  `nombre` varchar(30) NOT NULL,
  `n_Equipos` int(11) NOT NULL,
  `avance` double NOT NULL,
  `fecha_Inicio` date NOT NULL,
  `fecha_Limite` date NOT NULL,
  `descripcion_Proyecto` varchar(100) NOT NULL,
  `usuario` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `proyecto`
--

INSERT INTO `proyecto` (`Id_Proyecto`, `nombre`, `n_Equipos`, `avance`, `fecha_Inicio`, `fecha_Limite`, `descripcion_Proyecto`, `usuario`) VALUES
(1, 'Wigou', 2, 60, '2024-01-01', '2024-01-03', 'App de renta de un lavador de autos a domicilio', 1),
(2, 'Towgee', 2, 80, '2024-03-15', '2024-11-06', 'App de renta de grúa a domicilio', 8);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `puesto`
--

CREATE TABLE `puesto` (
  `Id_Puesto` int(11) NOT NULL,
  `nombre` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `puesto`
--

INSERT INTO `puesto` (`Id_Puesto`, `nombre`) VALUES
(1, 'Administrador'),
(2, 'Desarrollador');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tablero`
--

CREATE TABLE `tablero` (
  `Id_Tablero` int(11) NOT NULL,
  `estado_No_Iniciado` int(11) DEFAULT NULL,
  `estado_Iniciado` int(11) DEFAULT NULL,
  `estado_Finalizado` int(11) DEFAULT NULL,
  `comentario` varchar(100) DEFAULT NULL,
  `equipo` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `tablero`
--

INSERT INTO `tablero` (`Id_Tablero`, `estado_No_Iniciado`, `estado_Iniciado`, `estado_Finalizado`, `comentario`, `equipo`) VALUES
(1, 1, 2, 3, 'Darle prioridad a la interfaz del cliente', 1),
(2, 1, 2, 1, 'Entregar el logo mañana', 2),
(3, 1, 0, 5, 'Vamos muy bien equipo, falta muy poco', 3),
(4, 1, 2, 1, 'Darle prioridad al slogan', 4);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tarea`
--

CREATE TABLE `tarea` (
  `Id_Tarea` int(11) NOT NULL,
  `nombre` varchar(30) NOT NULL,
  `descripcion` varchar(100) NOT NULL,
  `prioridad` varchar(20) NOT NULL,
  `n_Prioridad` int(11) NOT NULL,
  `proyecto` int(11) NOT NULL,
  `equipo` int(11) NOT NULL,
  `usuario` int(11) DEFAULT NULL,
  `creador` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `tarea`
--

INSERT INTO `tarea` (`Id_Tarea`, `nombre`, `descripcion`, `prioridad`, `n_Prioridad`, `proyecto`, `equipo`, `usuario`, `creador`) VALUES
(1, 'Diseñar logo', 'Diseñar logo de la app con colores verdes y el nom', 'Media', 2, 1, 2, 0, 1),
(2, 'Crear base de datos', 'Diseñar y crear la base de datos para toda la apli', 'Alta', 3, 1, 1, 0, 1),
(3, 'LogIn', 'Crear el LogIn para los usuarios. Incluir inicio d', 'Alta', 3, 1, 1, 0, 1),
(4, 'Interfaz clientes', 'Codificar las acciones que pueda realizar el clien', 'Alta', 3, 1, 1, 0, 1),
(5, 'Interfaz administrador', 'Codificar las acciones que pueda realizar el Admin', 'Alta', 1, 1, 1, 0, 1),
(6, 'Aplicación móvil', 'Codificar la aplicación principal en móvil. Deben ', 'Baja', 1, 1, 1, 0, 1),
(7, 'Crear slogan', 'Crear el slogan de la aplicación', 'Baja', 1, 1, 2, 0, 1),
(8, 'Conectar DB', 'Una vez creada la base de datos, hacer la conexión', 'Alta', 3, 1, 1, 0, 1),
(9, 'Hacer pruebas', 'Una vez listo, empezar a hacer pruebas', 'Media', 2, 1, 2, 0, 1),
(10, 'Reportar bugs', 'Tomar ss de la consola y enviar la descripción del', 'Media', 2, 1, 2, 0, 1),
(11, 'Diseñar logo', 'Diseñar logo de color azul incluyendo un Tow bar', 'Media', 2, 2, 4, 0, 8),
(12, 'Crear base de datos', 'Diseñar y crear la base de datos para toda la apli', 'Alta', 3, 2, 3, 0, 8),
(13, 'LogIn', 'Crear el LogIn para los usuarios. Incluir inicio d', 'Alta', 3, 2, 3, 0, 8),
(14, 'Interfaz clientes', 'Codificar las acciones que pueda realizar el clien', 'Alta', 3, 2, 3, 0, 8),
(15, 'Interfaz administrador', 'Codificar las acciones que pueda realizar el Admin', 'Alta', 3, 2, 3, 0, 8),
(16, 'Aplicación móvil', 'Codificar la aplicación principal en móvil. Deben ', 'Baja', 1, 2, 3, 0, 8),
(17, 'Crear slogan', 'Crear el slogan de la aplicación', 'Media', 2, 2, 4, 0, 8),
(18, 'Conectar DB', 'Una vez creada la base de datos, hacer la conexión', 'Alta', 3, 2, 3, 0, 8),
(19, 'Hacer pruebas', 'Una vez listo, empezar a hacer pruebas', 'Media', 2, 2, 4, 0, 8),
(20, 'Reportar bugs', 'Tomar ss de la consola y enviar la descripción del', 'Media', 2, 2, 4, 0, 8);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `Id_Usuario` int(11) NOT NULL,
  `privilegios` varchar(10) NOT NULL,
  `nombre_Usuario` varchar(20) NOT NULL,
  `contraseña` varchar(20) NOT NULL,
  `Id_Creador` int(11) NOT NULL,
  `persona` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`Id_Usuario`, `privilegios`, `nombre_Usuario`, `contraseña`, `Id_Creador`, `persona`) VALUES
(1, 'Si', 'andres.1', 'cuevas.174', 1, 1),
(2, 'No', 'alejandro.2', 'torres.450', 1, 2),
(3, 'No', 'guillermo.3', 'martínez.719', 1, 3),
(4, 'No', 'carlos.4', 'pérez.481', 1, 4),
(5, 'No', 'carmen.5', 'torres.808', 1, 5),
(6, 'No', 'luis.6', 'hernández.536', 1, 6),
(7, 'No', 'beatriz.7', 'pérez.426', 1, 7),
(8, 'Si', 'brandon.8', 'gonzalez.948', 8, 8),
(9, 'No', 'diego.9', 'ortiz.537', 8, 9),
(10, 'No', 'jorge.10', 'hernández.955', 8, 10),
(11, 'No', 'francisco.11', 'ortiz.159', 8, 11),
(12, 'No', 'daniela.12', 'castillo.756', 8, 12),
(13, 'No', 'isabel.13', 'martínez.119', 8, 13),
(14, 'No', 'juan.14', 'pérez.822', 8, 14),
(15, 'No', 'jabes.15', 'llamas.989', 8, 15),
(16, 'Si', 'gustavo.16', 'tello.455', 16, 16);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `avance`
--
ALTER TABLE `avance`
  ADD PRIMARY KEY (`Id_Avance`),
  ADD KEY `tarea` (`tarea`),
  ADD KEY `tablero` (`tablero`);

--
-- Indices de la tabla `equipo`
--
ALTER TABLE `equipo`
  ADD PRIMARY KEY (`Id_Equipo`),
  ADD KEY `equipo_ibfk_1` (`proyecto`);

--
-- Indices de la tabla `persona`
--
ALTER TABLE `persona`
  ADD PRIMARY KEY (`Id_Persona`),
  ADD KEY `puesto` (`puesto`);

--
-- Indices de la tabla `proyecto`
--
ALTER TABLE `proyecto`
  ADD PRIMARY KEY (`Id_Proyecto`),
  ADD KEY `usuario` (`usuario`);

--
-- Indices de la tabla `puesto`
--
ALTER TABLE `puesto`
  ADD PRIMARY KEY (`Id_Puesto`);

--
-- Indices de la tabla `tablero`
--
ALTER TABLE `tablero`
  ADD PRIMARY KEY (`Id_Tablero`),
  ADD KEY `equipo` (`equipo`);

--
-- Indices de la tabla `tarea`
--
ALTER TABLE `tarea`
  ADD PRIMARY KEY (`Id_Tarea`),
  ADD KEY `proyecto` (`proyecto`),
  ADD KEY `tarea_ibfk_2` (`equipo`),
  ADD KEY `tarea_ibfk_3` (`creador`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`Id_Usuario`),
  ADD KEY `persona` (`persona`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `avance`
--
ALTER TABLE `avance`
  MODIFY `Id_Avance` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT de la tabla `equipo`
--
ALTER TABLE `equipo`
  MODIFY `Id_Equipo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `persona`
--
ALTER TABLE `persona`
  MODIFY `Id_Persona` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT de la tabla `proyecto`
--
ALTER TABLE `proyecto`
  MODIFY `Id_Proyecto` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `puesto`
--
ALTER TABLE `puesto`
  MODIFY `Id_Puesto` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `tablero`
--
ALTER TABLE `tablero`
  MODIFY `Id_Tablero` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `tarea`
--
ALTER TABLE `tarea`
  MODIFY `Id_Tarea` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
  MODIFY `Id_Usuario` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `avance`
--
ALTER TABLE `avance`
  ADD CONSTRAINT `avance_ibfk_1` FOREIGN KEY (`tarea`) REFERENCES `tarea` (`Id_Tarea`) ON DELETE CASCADE,
  ADD CONSTRAINT `avance_ibfk_2` FOREIGN KEY (`tablero`) REFERENCES `tablero` (`Id_Tablero`) ON DELETE CASCADE;

--
-- Filtros para la tabla `equipo`
--
ALTER TABLE `equipo`
  ADD CONSTRAINT `equipo_ibfk_1` FOREIGN KEY (`proyecto`) REFERENCES `proyecto` (`Id_Proyecto`) ON DELETE CASCADE;

--
-- Filtros para la tabla `persona`
--
ALTER TABLE `persona`
  ADD CONSTRAINT `persona_ibfk_1` FOREIGN KEY (`puesto`) REFERENCES `puesto` (`Id_Puesto`);

--
-- Filtros para la tabla `proyecto`
--
ALTER TABLE `proyecto`
  ADD CONSTRAINT `proyecto_ibfk_1` FOREIGN KEY (`usuario`) REFERENCES `usuario` (`Id_Usuario`);

--
-- Filtros para la tabla `tablero`
--
ALTER TABLE `tablero`
  ADD CONSTRAINT `tablero_ibfk_1` FOREIGN KEY (`equipo`) REFERENCES `equipo` (`Id_Equipo`) ON DELETE CASCADE;

--
-- Filtros para la tabla `tarea`
--
ALTER TABLE `tarea`
  ADD CONSTRAINT `tarea_ibfk_1` FOREIGN KEY (`proyecto`) REFERENCES `proyecto` (`Id_Proyecto`) ON DELETE CASCADE,
  ADD CONSTRAINT `tarea_ibfk_2` FOREIGN KEY (`equipo`) REFERENCES `equipo` (`Id_Equipo`) ON DELETE CASCADE,
  ADD CONSTRAINT `tarea_ibfk_3` FOREIGN KEY (`creador`) REFERENCES `usuario` (`Id_Usuario`) ON DELETE CASCADE;

--
-- Filtros para la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD CONSTRAINT `usuario_ibfk_2` FOREIGN KEY (`persona`) REFERENCES `persona` (`Id_Persona`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
