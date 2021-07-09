-- --------------------------------------------------------
-- Host:                         2.139.176.212
-- Versión del servidor:         10.4.17-MariaDB - mariadb.org binary distribution
-- SO del servidor:              Win64
-- HeidiSQL Versión:             11.2.0.6213
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Volcando estructura de base de datos para prbioguards
CREATE DATABASE IF NOT EXISTS `prbioguards` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;
USE `prbioguards`;

-- Volcando estructura para tabla prbioguards.admin
CREATE TABLE IF NOT EXISTS `admin` (
  `nombre` varchar(50) NOT NULL DEFAULT '0',
  `apellido` varchar(50) NOT NULL DEFAULT '',
  `dni` varchar(9) NOT NULL,
  `fechaNacimiento` date NOT NULL,
  `email` varchar(50) NOT NULL DEFAULT '',
  `telefono` int(11) NOT NULL,
  `ciudad` varchar(50) NOT NULL,
  `provincia` varchar(50) NOT NULL DEFAULT '',
  `codPostal` int(11) NOT NULL,
  `direccion` varchar(50) NOT NULL DEFAULT '',
  `tipo` int(11) NOT NULL,
  PRIMARY KEY (`dni`),
  KEY `dni` (`dni`),
  CONSTRAINT `admin_ibfk_1` FOREIGN KEY (`dni`) REFERENCES `usuario` (`dni`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Volcando datos para la tabla prbioguards.admin: ~8 rows (aproximadamente)
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
INSERT INTO `admin` (`nombre`, `apellido`, `dni`, `fechaNacimiento`, `email`, `telefono`, `ciudad`, `provincia`, `codPostal`, `direccion`, `tipo`) VALUES
	('Sergio', 'Montes Leiro', '07694351J', '1995-06-19', 'anagarciamartin01@gmail.com', 645438649, 'Madrid', 'Madrid', 42344, 'Sol, Calle 6, Casa 96', 2),
	('Juan', 'Hernandez', '12345678H', '2021-05-23', 'ana@gmail.com', 123456789, 'Leganes', 'Madrid', 21345, 'Calle Arbol', 2),
	('Manuel', 'Olmo Villanueva', '28647832L', '1970-08-08', 'manuel.olmo@hotmail.es', 645437773, 'Madrid', 'Madrid', 28670, 'Mostoles, Calle 4, Casa 4', 1),
	('Carlos', 'Martin', '32112103D', '2021-05-23', 'carlos@gmail.com', 612345678, 'Alcorcon', 'Madrid', 21345, 'Calle Mesa', 2),
	('Ana', 'Martinez Sarmiento', '35770954H', '1997-10-08', 'ana.ms23@gmail.com', 645437395, 'Avila', 'Avila', 12312, 'San Jose, Calle 5ta, Casa 7', 2),
	('Juan', 'Perez', '48553791D', '2021-05-26', 'ana@gmail.com', 123456789, 'ciudad 2', 'provincia 3', 12345, 'calle 1', 2),
	('Sara', 'Lara Ballesta', '56945777K', '1990-02-08', 'adobrasca@gmail.com', 645437659, 'Madrid', 'Madrid', 23444, 'Hospital Sofia, Calle 4, Casa 9', 1),
	('Juan', 'Hernandez', '87087088A', '2021-05-23', 'juan@gmail.com', 123456789, 'Mostoles', 'Madrid', 21345, 'Calle Arbol', 2);
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;

-- Volcando estructura para tabla prbioguards.asiste
CREATE TABLE IF NOT EXISTS `asiste` (
  `dni_pac` varchar(9) NOT NULL,
  `dni_fam` varchar(9) NOT NULL,
  PRIMARY KEY (`dni_pac`,`dni_fam`),
  KEY `dni_fam` (`dni_fam`),
  KEY `dni_pac` (`dni_pac`),
  CONSTRAINT `asiste_ibfk_1` FOREIGN KEY (`dni_pac`) REFERENCES `paciente` (`dni`),
  CONSTRAINT `asiste_ibfk_2` FOREIGN KEY (`dni_fam`) REFERENCES `cuidador_familiar` (`dni`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Volcando datos para la tabla prbioguards.asiste: ~10 rows (aproximadamente)
/*!40000 ALTER TABLE `asiste` DISABLE KEYS */;
INSERT INTO `asiste` (`dni_pac`, `dni_fam`) VALUES
	('22342344Q', '10772263Y'),
	('22346774Q', '01865731V'),
	('42342323L', '99941921K'),
	('52342237M', '24800428B'),
	('52342343K', '19535356F'),
	('52342356K', '24800428B'),
	('52342356K', '99941921K'),
	('62323426W', '19535356F'),
	('72343421J', '19535356F'),
	('82346777J', '24800428B');
/*!40000 ALTER TABLE `asiste` ENABLE KEYS */;

-- Volcando estructura para tabla prbioguards.clinico
CREATE TABLE IF NOT EXISTS `clinico` (
  `nombre` varchar(50) NOT NULL DEFAULT '',
  `apellido` varchar(50) NOT NULL DEFAULT '',
  `dni` varchar(9) NOT NULL,
  `fechaNacimiento` date NOT NULL,
  `email` varchar(50) NOT NULL DEFAULT '',
  `telefono` int(11) NOT NULL,
  `ciudad` varchar(50) NOT NULL DEFAULT '',
  `provincia` varchar(50) NOT NULL DEFAULT '',
  `codPostal` int(5) NOT NULL,
  `direccion` varchar(50) NOT NULL DEFAULT '',
  `especialidad` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`dni`),
  CONSTRAINT `clinico_ibfk_1` FOREIGN KEY (`dni`) REFERENCES `usuario` (`dni`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Volcando datos para la tabla prbioguards.clinico: ~4 rows (aproximadamente)
/*!40000 ALTER TABLE `clinico` DISABLE KEYS */;
INSERT INTO `clinico` (`nombre`, `apellido`, `dni`, `fechaNacimiento`, `email`, `telefono`, `ciudad`, `provincia`, `codPostal`, `direccion`, `especialidad`) VALUES
	('Silvia', 'Serrano Lopez', '09384832M', '1970-02-22', 'silvasicerr@hotmail.com', 693868294, 'Madrid', 'Madrid', 28002, 'Plaza Ntra. Sra. del Pilar, 5', 'Neurologo'),
	('Mateo', 'Cordoba Gonzales', '38488290Z', '1984-06-10', 'mate4243@outlook.com', 664456393, 'Madrid', 'Madrid', 28005, 'Ronda de Toledo, 26', 'Cardiologo'),
	('Jorge', 'Chong', '38734825J', '1979-05-17', 'Jorge.ch@gmail.com', 645434432, 'Madrid', 'Madrid', 28005, 'C/ Toledo, 125', 'General'),
	('Enrique', 'Paz Gutierres', '80594938Y', '1965-12-05', 'enriquepaz32@gmail.com', 693858384, 'Madrid', 'Madrid', 28012, 'C/ Atocha, 87', 'Neurologo');
/*!40000 ALTER TABLE `clinico` ENABLE KEYS */;

-- Volcando estructura para tabla prbioguards.cuidador_familiar
CREATE TABLE IF NOT EXISTS `cuidador_familiar` (
  `nombre` varchar(50) NOT NULL DEFAULT '',
  `apellido` varchar(50) NOT NULL DEFAULT '',
  `dni` varchar(9) NOT NULL,
  `fechaNacimiento` date NOT NULL,
  `email` varchar(50) NOT NULL DEFAULT '',
  `telefono` int(11) NOT NULL,
  `ciudad` varchar(50) NOT NULL DEFAULT '0',
  `provincia` varchar(50) NOT NULL DEFAULT '',
  `codPostal` int(5) NOT NULL,
  `direccion` varchar(50) NOT NULL DEFAULT '',
  PRIMARY KEY (`dni`),
  CONSTRAINT `cuidador_familiar_ibfk_1` FOREIGN KEY (`dni`) REFERENCES `usuario` (`dni`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Volcando datos para la tabla prbioguards.cuidador_familiar: ~5 rows (aproximadamente)
/*!40000 ALTER TABLE `cuidador_familiar` DISABLE KEYS */;
INSERT INTO `cuidador_familiar` (`nombre`, `apellido`, `dni`, `fechaNacimiento`, `email`, `telefono`, `ciudad`, `provincia`, `codPostal`, `direccion`) VALUES
	('Jaime', 'Arribas Valle', '01865731V', '1999-03-11', 'jaimeav@bioguards.com', 674522151, 'Alagon', 'Zaragoza', 32423, 'Cuzco, Calle 74, Casa 72'),
	('Maite', 'Pino Usera', '10772263Y', '1995-08-12', 'maitepu@bioguards.com', 632541789, 'Zaragoza', 'Zaragoza', 213123, 'Legazpi, Calle 54, Casa 65'),
	('Lucas', 'Socas Llanos', '19535356F', '2000-12-02', 'adobrasca@gmail.com', 695632154, 'Zaragoza', 'Zaragoza', 23423, 'Villaviciosa de Odon, Calle 30, Casa 31'),
	('Cristina', 'Fraile Selas', '24800428B', '1990-09-04', 'cristinafs@bioguards.com', 687542165, 'Caspe', 'Zaragoza', 23432, 'Santiago Bernabeu, Calle 78, Casa 12'),
	('Julio', 'Limo Ramos', '99941921K', '1965-05-07', 'juliolr@bioguards.com', 675896125, 'Zaragoza', 'Zaragoza', 212312, 'Plaza Castilla, Calle 80, Casa 42');
/*!40000 ALTER TABLE `cuidador_familiar` ENABLE KEYS */;

-- Volcando estructura para tabla prbioguards.evento
CREATE TABLE IF NOT EXISTS `evento` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `texto` varchar(50) NOT NULL DEFAULT '0',
  `tiempoInicio` varchar(50) NOT NULL,
  `TiempoFin` varchar(50) NOT NULL,
  `dni_pac` varchar(9) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `dni_pac` (`dni_pac`),
  CONSTRAINT `FK_evento_paciente` FOREIGN KEY (`dni_pac`) REFERENCES `paciente` (`dni`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;

-- Volcando datos para la tabla prbioguards.evento: ~5 rows (aproximadamente)
/*!40000 ALTER TABLE `evento` DISABLE KEYS */;
INSERT INTO `evento` (`id`, `texto`, `tiempoInicio`, `TiempoFin`, `dni_pac`) VALUES
	(1, 'Nuevo evento 1', '2021-01-13 12:30:00', '2021-01-13 13:00:00', '52342356K'),
	(2, 'Pastilla', '2021-05-26 16:15:00', '2021-05-26 17:15:00', '72343421J'),
	(3, 'Nuevo evento 3', '2021-01-14 01:30:00', '2021-01-14 01:40:00', '22346774Q'),
	(4, 'Nuevo evento 4', '2021-02-13 12:10:00', '2021-02-13 12:50:00', '82346777J'),
	(5, 'Nuevo Evento 1', '2021-05-26 16:15:00', '2021-05-26 17:15:00', '52342343K');
/*!40000 ALTER TABLE `evento` ENABLE KEYS */;

-- Volcando estructura para tabla prbioguards.infomedica
CREATE TABLE IF NOT EXISTS `infomedica` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `titulo` varchar(50) NOT NULL DEFAULT '',
  `fechaCreacion` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `descripcion` text NOT NULL,
  `tratamiento` text DEFAULT NULL,
  `dni_paciente` varchar(9) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `dni_paciente` (`dni_paciente`),
  CONSTRAINT `infomedica_ibfk_1` FOREIGN KEY (`dni_paciente`) REFERENCES `paciente` (`dni`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4;

-- Volcando datos para la tabla prbioguards.infomedica: ~3 rows (aproximadamente)
/*!40000 ALTER TABLE `infomedica` DISABLE KEYS */;
INSERT INTO `infomedica` (`id`, `titulo`, `fechaCreacion`, `descripcion`, `tratamiento`, `dni_paciente`) VALUES
	(1, 'Rotura de tibia', '2020-11-11 00:00:00', 'Rotura parcial de la tibia', 'Reposo durante dos meses, tras los cuales comenzar con sesiones de rehabilitación y añadir ejercicios con goma elástica.', '52342356K'),
	(2, 'Continuación de reposo tras la rotura de tibia', '2021-01-09 00:00:00', 'El hueso no se ha recuperado totalmente.', 'Debido al estado del hueso se continua con el reposo y sesiones de rehabilitación.', '52342356K'),
	(3, 'Rehabilitación', '2021-01-20 00:00:00', 'Rehabilitación completa de la tibia.', 'Se recomienda realizar algo de ejercicio diario para foratalecer la pierna.', '52342356K');
/*!40000 ALTER TABLE `infomedica` ENABLE KEYS */;

-- Volcando estructura para tabla prbioguards.medida
CREATE TABLE IF NOT EXISTS `medida` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `valor` float NOT NULL DEFAULT 0,
  `fecha` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `id_sensor` int(11) NOT NULL DEFAULT 0,
  `alerta` tinyint(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  KEY `id_sensor` (`id_sensor`),
  CONSTRAINT `FK_id_sensor` FOREIGN KEY (`id_sensor`) REFERENCES `sensor` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9232 DEFAULT CHARSET=utf8mb4;

-- Volcando datos para la tabla prbioguards.medida: ~128 rows (aproximadamente)
/*!40000 ALTER TABLE `medida` DISABLE KEYS */;
INSERT INTO `medida` (`id`, `valor`, `fecha`, `id_sensor`, `alerta`) VALUES
	(1, 38, '2021-05-26 16:48:03', 1, 0),
	(2, 37, '2021-04-09 07:00:00', 1, 0),
	(3, 37, '2021-04-09 08:00:00', 1, 0),
	(4, 38, '2021-04-09 09:00:00', 1, 0),
	(5, 38, '2021-04-09 10:00:00', 1, 0),
	(6, 38, '2021-04-09 11:00:00', 1, 0),
	(7, 38, '2021-04-09 12:00:00', 1, 0),
	(8, 34, '2021-04-09 13:00:00', 1, 0),
	(9, 38, '2021-04-09 14:00:00', 1, 0),
	(10, 38, '2021-04-09 15:00:00', 1, 0),
	(11, 36, '2021-04-09 16:00:00', 1, 0),
	(12, 37, '2021-04-09 17:00:00', 1, 0),
	(13, 38, '2021-04-09 18:00:00', 1, 0),
	(14, 37, '2021-04-09 19:00:00', 1, 0),
	(15, 38, '2021-04-09 20:00:00', 1, 0),
	(16, 34, '2021-04-09 21:00:00', 1, 0),
	(17, 38, '2021-04-09 22:00:00', 1, 0),
	(18, 35, '2021-04-09 23:00:00', 1, 0),
	(19, 38, '2021-04-09 23:59:59', 1, 0),
	(20, 0, '2021-04-09 06:00:00', 2, 0),
	(21, 0, '2021-04-09 07:00:00', 2, 0),
	(22, 1, '2021-04-09 08:00:00', 2, 0),
	(23, 1, '2021-04-09 09:00:00', 2, 0),
	(24, 1, '2021-04-09 10:00:00', 2, 0),
	(25, 0, '2021-04-09 11:00:00', 2, 0),
	(26, 0, '2021-04-09 12:00:00', 2, 0),
	(27, 0, '2021-04-09 13:00:00', 2, 0),
	(28, 0, '2021-04-09 14:00:00', 2, 0),
	(29, 1, '2021-04-09 15:00:00', 2, 0),
	(30, 0, '2021-04-09 16:00:00', 2, 0),
	(31, 1, '2021-04-09 17:00:00', 2, 0),
	(32, 0, '2021-04-09 18:00:00', 2, 0),
	(33, 1, '2021-04-09 19:00:00', 2, 0),
	(34, 0, '2021-04-09 20:00:00', 2, 0),
	(35, 1, '2021-04-09 21:00:00', 2, 0),
	(36, 0, '2021-04-09 22:00:00', 2, 0),
	(37, 1, '2021-04-09 23:00:00', 2, 0),
	(38, 0, '2021-04-09 23:59:59', 2, 0),
	(39, 99, '2021-04-09 06:00:00', 3, 0),
	(40, 98, '2021-04-09 07:00:00', 3, 0),
	(41, 96.5, '2021-04-09 08:00:00', 3, 0),
	(42, 97, '2021-04-09 09:00:00', 3, 0),
	(43, 95, '2021-04-09 10:00:00', 3, 0),
	(44, 93, '2021-04-09 11:00:00', 3, 0),
	(45, 94, '2021-04-09 12:00:00', 3, 0),
	(46, 91, '2021-04-09 13:00:00', 3, 0),
	(47, 92, '2021-04-09 14:00:00', 3, 0),
	(48, 97, '2021-04-09 15:00:00', 3, 0),
	(49, 94, '2021-04-09 16:00:00', 3, 0),
	(50, 95, '2021-04-09 17:00:00', 3, 0),
	(51, 99, '2021-04-09 18:00:00', 3, 0),
	(52, 97, '2021-04-09 19:00:00', 3, 0),
	(53, 98, '2021-04-09 20:00:00', 3, 0),
	(54, 94, '2021-04-09 21:00:00', 3, 0),
	(55, 98, '2021-04-09 22:00:00', 3, 0),
	(56, 95, '2021-04-09 23:00:00', 3, 0),
	(57, 98, '2021-04-09 23:59:59', 3, 0),
	(58, 38, '2021-04-09 06:00:00', 4, 0),
	(59, 37, '2021-04-09 07:00:00', 4, 0),
	(60, 37, '2021-04-09 08:00:00', 4, 0),
	(61, 38, '2021-04-09 09:00:00', 4, 0),
	(62, 38, '2021-04-09 10:00:00', 4, 0),
	(63, 38, '2021-04-09 11:00:00', 4, 0),
	(64, 38, '2021-04-09 12:00:00', 4, 0),
	(65, 34, '2021-04-09 13:00:00', 4, 0),
	(66, 38, '2021-04-09 14:00:00', 4, 0),
	(67, 38, '2021-04-09 15:00:00', 4, 0),
	(68, 36, '2021-04-09 16:00:00', 4, 0),
	(69, 37, '2021-04-09 17:00:00', 4, 0),
	(70, 38, '2021-04-09 18:00:00', 4, 0),
	(71, 37, '2021-04-09 19:00:00', 4, 0),
	(72, 38, '2021-04-09 20:00:00', 4, 0),
	(73, 34, '2021-04-09 21:00:00', 4, 0),
	(74, 38, '2021-04-09 22:00:00', 4, 0),
	(75, 35, '2021-04-09 23:00:00', 4, 0),
	(76, 26, '2021-05-26 17:00:05', 4, 0),
	(77, 0, '2021-04-09 06:00:00', 5, 0),
	(78, 0, '2021-04-09 07:00:00', 5, 0),
	(79, 1, '2021-04-09 08:00:00', 5, 0),
	(80, 1, '2021-04-09 09:00:00', 5, 0),
	(81, 1, '2021-04-09 10:00:00', 5, 0),
	(82, 0, '2021-04-09 11:00:00', 5, 0),
	(83, 0, '2021-04-09 12:00:00', 5, 0),
	(84, 0, '2021-04-09 13:00:00', 5, 0),
	(85, 0, '2021-04-09 14:00:00', 5, 0),
	(86, 1, '2021-04-09 15:00:00', 5, 0),
	(87, 0, '2021-04-09 16:00:00', 5, 0),
	(88, 1, '2021-04-09 17:00:00', 5, 0),
	(89, 0, '2021-04-09 18:00:00', 5, 0),
	(90, 1, '2021-04-09 19:00:00', 5, 0),
	(91, 0, '2021-04-09 20:00:00', 5, 0),
	(92, 1, '2021-04-09 21:00:00', 5, 0),
	(93, 0, '2021-04-09 22:00:00', 5, 0),
	(94, 1, '2021-04-09 23:00:00', 5, 0),
	(95, 0, '2021-04-09 23:59:59', 5, 0),
	(96, 99, '2021-04-09 06:00:00', 6, 0),
	(97, 98, '2021-04-09 07:00:00', 6, 0),
	(98, 96.5, '2021-04-09 08:00:00', 6, 0),
	(99, 97, '2021-04-09 09:00:00', 6, 0),
	(100, 95, '2021-04-09 10:00:00', 6, 0),
	(101, 93, '2021-04-09 11:00:00', 6, 0),
	(102, 94, '2021-04-09 12:00:00', 6, 0),
	(103, 91, '2021-04-09 13:00:00', 6, 0),
	(104, 92, '2021-04-09 14:00:00', 6, 0),
	(105, 97, '2021-04-09 15:00:00', 6, 0),
	(106, 94, '2021-04-09 16:00:00', 6, 0),
	(107, 95, '2021-04-09 17:00:00', 6, 0),
	(108, 99, '2021-04-09 18:00:00', 6, 0),
	(109, 97, '2021-04-09 19:00:00', 6, 0),
	(110, 98, '2021-04-09 20:00:00', 6, 0),
	(111, 94, '2021-04-09 21:00:00', 6, 0),
	(112, 98, '2021-04-09 22:00:00', 6, 0),
	(113, 95, '2021-04-09 23:00:00', 6, 0),
	(114, 98, '2021-04-09 23:59:59', 6, 0),
	(386, 55.45, '2021-05-23 14:58:47', 3, 0),
	(387, 68.03, '2021-05-23 14:58:57', 3, 0),
	(388, 39.4, '2021-05-23 14:59:07', 3, 0),
	(389, 130.15, '2021-05-23 14:59:18', 3, 0),
	(390, 175.95, '2021-05-23 14:59:28', 3, 0),
	(391, 80.97, '2021-05-23 14:59:38', 3, 0),
	(392, 96.62, '2021-05-23 15:25:06', 3, 0),
	(393, 74.81, '2021-05-23 15:25:16', 3, 0),
	(394, 106.95, '2021-05-23 15:25:26', 3, 0),
	(3000, 30, '2021-05-23 17:50:26', 3, 1),
	(9225, 1, '2021-05-23 20:54:12', 2, 0),
	(9226, 0, '2021-05-23 17:50:07', 2, 0),
	(9227, 27.8, '2021-05-23 20:25:19', 1, 0),
	(9228, -1, '2021-05-23 17:50:25', 1, 1),
	(9229, -1, '2021-05-27 13:44:33', 2, 0),
	(9230, -1, '2021-05-27 13:45:26', 1, 0),
	(9231, -1, '2021-05-27 13:45:14', 3, 0);
/*!40000 ALTER TABLE `medida` ENABLE KEYS */;

-- Volcando estructura para tabla prbioguards.mensaje
CREATE TABLE IF NOT EXISTS `mensaje` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `texto` text NOT NULL,
  `fecha` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `emisor` varchar(9) NOT NULL,
  `receptor` varchar(9) NOT NULL,
  `dni_paciente` varchar(9) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `emisor` (`emisor`),
  KEY `receptor` (`receptor`),
  KEY `dni_paciente` (`dni_paciente`),
  CONSTRAINT `mensaje_ibfk_1` FOREIGN KEY (`emisor`) REFERENCES `usuario` (`dni`),
  CONSTRAINT `mensaje_ibfk_2` FOREIGN KEY (`receptor`) REFERENCES `usuario` (`dni`),
  CONSTRAINT `mensaje_ibfk_3` FOREIGN KEY (`dni_paciente`) REFERENCES `paciente` (`dni`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4;

-- Volcando datos para la tabla prbioguards.mensaje: ~16 rows (aproximadamente)
/*!40000 ALTER TABLE `mensaje` DISABLE KEYS */;
INSERT INTO `mensaje` (`id`, `texto`, `fecha`, `emisor`, `receptor`, `dni_paciente`) VALUES
	(1, 'Buenos días, ¿La medicación se realiza en cada comida?', '2021-01-14 12:30:00', '99941921K', '38734825J', '52342356K'),
	(2, 'Sí, son 200ml en cada toma. ', '2021-01-14 12:45:00', '38734825J', '99941921K', '52342356K'),
	(3, 'Buenas tardes, ya no nos queda medicación, ¿cuándo se puede pasar a recoger más?', '2021-01-20 18:50:00', '72343421J', '38734825J', '72343421J'),
	(4, 'Le envío la receta por email para que puedan recogerla en la farmacia más cercana.', '2021-01-20 19:00:00', '38734825J', '72343421J', '72343421J'),
	(5, 'Buenos días, ¿se le ha bajado la inflamación?', '2021-01-21 09:30:00', '38734825J', '52342237M', '52342237M'),
	(6, 'Ha disminuido algo, pero sigue bastante inflamado.', '2021-01-21 10:00:00', '52342237M', '38734825J', '52342237M'),
	(7, 'Siga aplicando calor sobre la zona y si en un par de días no disminuye contacte conmigo. ', '2021-01-21 10:05:00', '38734825J', '52342237M', '52342237M'),
	(8, 'Buenos días, ¿hay ejercicios de memoria más complejos?', '2021-02-03 11:00:00', '01865731V', '38488290Z', '22346774Q'),
	(9, 'Si, dispone de ejercicios más completos en la web de la clínica. ', '2021-02-03 11:45:00', '38488290Z', '01865731V', '22346774Q'),
	(10, 'Buenos días, ¿cuántas pastillas de sintrón había que suministrar en la comida?', '2021-02-05 08:00:00', '99941921K', '38488290Z', '42342323L'),
	(11, 'Una pastilla y media. ', '2021-02-05 08:15:00', '38488290Z', '99941921K', '42342323L'),
	(12, '¿Qué alimentos conviene que tome el paciente?', '2021-02-10 13:30:00', '19535356F', '09384832M', '52342343K'),
	(13, 'Lo mejor es fruta y también algún cereal como la avena ', '2021-02-10 14:00:00', '09384832M', '19535356F', '52342343K'),
	(19, 'de acuerdo, gracias', '2021-05-26 02:05:37', '38734825J', '99941921K', '52342356K'),
	(21, 'hola', '2021-05-26 11:31:32', '38734825J', '24800428B', '52342356K'),
	(22, 'hola tengo fiebre', '2021-05-26 23:43:34', '24800428B', '52342237M', '52342237M');
/*!40000 ALTER TABLE `mensaje` ENABLE KEYS */;

-- Volcando estructura para tabla prbioguards.mensaje_paciente
CREATE TABLE IF NOT EXISTS `mensaje_paciente` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `texto` text NOT NULL,
  `fecha` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `emisor` varchar(9) NOT NULL,
  `receptor` varchar(9) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `receptor` (`receptor`),
  KEY `emisor` (`emisor`),
  CONSTRAINT `mensaje_paciente_ibfk_1` FOREIGN KEY (`receptor`) REFERENCES `usuario` (`dni`),
  CONSTRAINT `mensaje_paciente_ibfk_2` FOREIGN KEY (`emisor`) REFERENCES `usuario` (`dni`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4;

-- Volcando datos para la tabla prbioguards.mensaje_paciente: ~12 rows (aproximadamente)
/*!40000 ALTER TABLE `mensaje_paciente` DISABLE KEYS */;
INSERT INTO `mensaje_paciente` (`id`, `texto`, `fecha`, `emisor`, `receptor`) VALUES
	(1, 'Sí, he recibido la medicación de hoy mod', '2021-01-14 12:30:00', '52342356K', '38734825J'),
	(2, '¿Ha bajado la inflamación, o sigue igual?', '2021-01-21 09:30:00', '38734825J', '52342237M'),
	(3, 'No hemos recibido la receta', '2021-01-20 20:00:00', '72343421J', '38734825J'),
	(4, '¿Es bueno tomar leche?', '2021-02-10 14:30:00', '52342343K', '09384832M'),
	(8, 'hola', '2021-04-07 08:04:55', '52342356K', '38734825J'),
	(9, 'adios', '2021-04-07 08:05:01', '52342356K', '38734825J'),
	(10, 'Hola', '2021-04-07 08:45:00', '38734825J', '52342356K'),
	(11, 'test', '2021-05-25 00:05:17', '52342356K', '38734825J'),
	(12, 'buenas', '2021-05-25 01:47:10', '38734825J', '52342356K'),
	(13, 'buenas tardes', '2021-05-26 02:05:24', '38734825J', '52342356K'),
	(14, 'testç', '2021-05-26 11:31:22', '38734825J', '52342356K'),
	(15, 'si pero con moderacion', '2021-05-26 23:42:34', '52342343K', '09384832M');
/*!40000 ALTER TABLE `mensaje_paciente` ENABLE KEYS */;

-- Volcando estructura para tabla prbioguards.paciente
CREATE TABLE IF NOT EXISTS `paciente` (
  `nombre` varchar(50) NOT NULL DEFAULT '',
  `apellido` varchar(50) NOT NULL DEFAULT '',
  `dni` varchar(9) NOT NULL,
  `fechaNacimiento` date NOT NULL,
  `email` varchar(50) NOT NULL DEFAULT '',
  `telefono` int(11) NOT NULL,
  `ciudad` varchar(50) NOT NULL DEFAULT '',
  `provincia` varchar(50) NOT NULL DEFAULT '',
  `codPostal` int(5) NOT NULL,
  `direccion` varchar(50) NOT NULL DEFAULT '',
  `dni_clinico` varchar(9) NOT NULL,
  PRIMARY KEY (`dni`),
  KEY `dni_clinico` (`dni_clinico`),
  CONSTRAINT `paciente_ibfk_1` FOREIGN KEY (`dni_clinico`) REFERENCES `clinico` (`dni`),
  CONSTRAINT `paciente_ibfk_2` FOREIGN KEY (`dni`) REFERENCES `usuario` (`dni`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Volcando datos para la tabla prbioguards.paciente: ~9 rows (aproximadamente)
/*!40000 ALTER TABLE `paciente` DISABLE KEYS */;
INSERT INTO `paciente` (`nombre`, `apellido`, `dni`, `fechaNacimiento`, `email`, `telefono`, `ciudad`, `provincia`, `codPostal`, `direccion`, `dni_clinico`) VALUES
	('Carla', 'ODonnell Ng', '22342344Q', '1956-01-27', 'carla_odonell_ng@gmail.com', 944379465, 'Madrid', 'Madrid', 345344, 'Villaviciosa de Odon, Calle 58, Casa 12', '09384832M'),
	('Ana', 'Cervera Hernandez', '22346774Q', '1947-06-23', 'ana_cervera_her@gmail.com', 914341111, 'Madrid', 'Madrid', 23423, 'Tribunal, Calle 66, Casa 9', '38488290Z'),
	('Maria', 'Castellon Cordoba', '42342323L', '1945-08-21', 'maria_castellon_cor@gmail.com', 944336547, 'Madrid', 'Madrid', 23433, 'Principe pio, Calle 64, Casa 7', '38488290Z'),
	('Christian', 'Santiago Lopez', '52342237M', '1952-02-15', 'christian_santiago_lo@gmail.com', 924342344, 'Leganes', 'Madrid', 234234, 'Alonso Martinez, Calle 78, Casa 34', '38734825J'),
	('Mateo', 'Zhang Ng', '52342343K', '1978-04-01', 'mateo_zhang_ng@gmail.com', 944353785, 'Madrid', 'Madrid', 32423, 'Oria, Calle 55, Casa 2', '09384832M'),
	('Juan', 'Perez Vladimir', '52342356K', '1947-04-25', 'adobrasca@gmail.com', 944342323, 'Madrid', 'Madrid', 22343, 'Boadilla, Calle 30, Casa 82', '38734825J'),
	('Laura', 'West Cedeno', '62323426W', '1961-09-01', 'carlos_tetuan_gon@gmail.com', 954342355, 'Madrid', 'Madrid', 42343, 'Chamartin, Calle 54, Casa 23', '80594938Y'),
	('Carlos', 'Tetuan Gonzalez', '72343421J', '1949-08-12', 'carlos_tetuan_gon@gmail.com', 934442363, 'Getafe', 'Madrid', 23423, 'Mostoles, Calle 44, Casa 92', '38734825J'),
	('Ramon', 'Somoza Correa', '82346777J', '1972-11-09', 'ramon_somoza_co@gmail.com', 924326476, 'Madrid', 'Madrid', 23423, 'Ventilla, Calle 90, Casa 5', '80594938Y');
/*!40000 ALTER TABLE `paciente` ENABLE KEYS */;

-- Volcando estructura para tabla prbioguards.sensor
CREATE TABLE IF NOT EXISTS `sensor` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tipo` text NOT NULL,
  `dni_pac` varchar(9) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `dni_pac` (`dni_pac`),
  CONSTRAINT `sensor_ibfk_1` FOREIGN KEY (`dni_pac`) REFERENCES `paciente` (`dni`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4;

-- Volcando datos para la tabla prbioguards.sensor: ~6 rows (aproximadamente)
/*!40000 ALTER TABLE `sensor` DISABLE KEYS */;
INSERT INTO `sensor` (`id`, `tipo`, `dni_pac`) VALUES
	(1, 'Sensor 1', '52342356K'),
	(2, 'Sensor 2', '52342356K'),
	(3, 'Sensor 3', '52342356K'),
	(4, 'Sensor 1', '72343421J'),
	(5, 'Sensor 2', '72343421J'),
	(6, 'Sensor 3', '72343421J');
/*!40000 ALTER TABLE `sensor` ENABLE KEYS */;

-- Volcando estructura para tabla prbioguards.usuario
CREATE TABLE IF NOT EXISTS `usuario` (
  `dni` varchar(9) NOT NULL,
  `password` varchar(50) NOT NULL DEFAULT '',
  `rol` varchar(50) NOT NULL DEFAULT '',
  PRIMARY KEY (`dni`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Volcando datos para la tabla prbioguards.usuario: ~26 rows (aproximadamente)
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` (`dni`, `password`, `rol`) VALUES
	('01865731V', 'jaimeArribas', 'cuidador'),
	('07694351J', 'sergioMontes', 'administrador'),
	('09384832M', 'silviaCerrano', 'clinico'),
	('10772263Y', 'maitePino', 'cuidador'),
	('12345678H', 'Universidad1*', 'administrador'),
	('19535356F', 'lucasSocas', 'cuidador'),
	('22342344Q', 'carlaODonnell', 'paciente'),
	('22346774Q', 'anaCervera', 'paciente'),
	('24800428B', 'cristinaFraile', 'cuidador'),
	('28647832L', 'manuelOlmo', 'administrador'),
	('32112103D', 'Alcachofa1*', 'administrador'),
	('35770954H', 'anaMartinez', 'administrador'),
	('38488290Z', 'mateoCordoba', 'clinico'),
	('38734825J', 'jorgeChong', 'clinico'),
	('42342323L', 'mariaCastellon', 'paciente'),
	('48553791D', 'Europea1*', 'administrador'),
	('52342237M', 'christianSantiago', 'paciente'),
	('52342343K', 'mateoZhangNg', 'paciente'),
	('52342356K', 'juanPerez', 'paciente'),
	('56945777K', 'Etrasena5+', 'administrador'),
	('62323426W', 'lauraWest', 'paciente'),
	('72343421J', 'carlosTetuan', 'paciente'),
	('80594938Y', 'enriquePaz', 'clinico'),
	('82346777J', 'ramonSomoza', 'paciente'),
	('87087088A', 'Universidad1*', 'administrador'),
	('99941921K', 'julioLimo', 'cuidador');
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
