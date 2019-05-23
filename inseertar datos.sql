CREATE DATABASE  IF NOT EXISTS `cdm` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `cdm`;
-- MySQL dump 10.13  Distrib 8.0.15, for Win64 (x86_64)
--
-- Host: localhost    Database: cdm
-- ------------------------------------------------------
-- Server version	8.0.15

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `clientes`
--

DROP TABLE IF EXISTS `clientes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `clientes` (
  `idCliente` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) DEFAULT NULL,
  `creditoDesayuno` int(10) unsigned NOT NULL,
  `creditoComida` int(10) unsigned NOT NULL,
  `creditoCena` int(10) unsigned NOT NULL,
  PRIMARY KEY (`idCliente`),
  UNIQUE KEY `idCliente_UNIQUE` (`idCliente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clientes`
--

LOCK TABLES `clientes` WRITE;
/*!40000 ALTER TABLE `clientes` DISABLE KEYS */;
/*!40000 ALTER TABLE `clientes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `entradasproductos`
--

DROP TABLE IF EXISTS `entradasproductos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `entradasproductos` (
  `idEntradasProductos` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `Productos_idProductos` int(11) NOT NULL,
  `cantidad` float DEFAULT NULL,
  `costo` float DEFAULT NULL,
  PRIMARY KEY (`idEntradasProductos`),
  UNIQUE KEY `idEntradasProductos_UNIQUE` (`idEntradasProductos`),
  KEY `fk_EntradasProductos_Productos1_idx` (`Productos_idProductos`),
  CONSTRAINT `fk_EntradasProductos_Productos1` FOREIGN KEY (`Productos_idProductos`) REFERENCES `productos` (`idProducto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `entradasproductos`
--

LOCK TABLES `entradasproductos` WRITE;
/*!40000 ALTER TABLE `entradasproductos` DISABLE KEYS */;
/*!40000 ALTER TABLE `entradasproductos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `platillos`
--

DROP TABLE IF EXISTS `platillos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `platillos` (
  `idPlatillo` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`idPlatillo`),
  UNIQUE KEY `idPlatillo_UNIQUE` (`idPlatillo`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `platillos`
--

LOCK TABLES `platillos` WRITE;
/*!40000 ALTER TABLE `platillos` DISABLE KEYS */;
INSERT INTO `platillos` VALUES (1,'Huevos al gusto'),(2,'Omelets'),(3,'Chilaquiles'),(4,'Quesadillas sincronizadas'),(5,'Burritos de papa con machaca'),(6,'Pan francés'),(7,'Pan francés relleno'),(8,'Hot cake'),(9,'Sandwich de jamón y queso'),(10,'Nopales asados con queso y salsa bandera'),(11,'SOUFLE DE POLLO, ENSALADA VERDE Y FRIJOLES'),(12,'PECHUGA BORRACHA EN CREMA DE CILANTRO, ARROZ Y FRIJOLES (MARINADA EN CERVEZA Y A LA PLANCHA)'),(13,'PESCADO A LA GABARDINA, ARROZ Y FRIJOLES Y FRIJOLES'),(14,'TOSTADAS DE CARNE DE RES (3)'),(15,'PICADILLO DE RES, ESPAGUETI Y FRIJOLES'),(16,'SOPA DE PASTA'),(17,'CARNE EN SU JUGO Y SOPA FRÍA'),(18,'PECHUGA SUIZA (PECHUGA A LA PLANCHA BAÑADA EN CREMA SUIZA Y QUESO GRATINADO), ARROZ Y FRIJOLES'),(19,'FETUSINI DE POLLO, ENSALADA VERDE Y FRIJOLES'),(20,'COCHINITA PIBIL, SOPA FRÍA Y FRIJOLES'),(21,'CAMARONES EMPANIZADOS, PURÉ DE PAPA Y ENSALADA VERDE'),(22,'HAMBURGUESA CON PAPAS'),(23,'CHILES RELLENOS DE QUESO, ARROZ Y FRIJOLES'),(24,'PASTEL AZTECA, ENSALADA VERDE Y FRIJOLES'),(25,'POLLO PARMESANO (EMPANIZADO CON QUESO PARMESANO), ARROZ Y FRIJOLES'),(26,'PIERNA CON CHAMPIÑONES, PURÉ DE PAPA Y SOPA AL HORNO'),(27,'LASAÑA, ESPAGUETI Y ENSALADA VERDE'),(28,'PECHUGAS RELLENAS (JAMÓN Y QUESO), ARROZ Y FRIJOLES'),(29,'PECHUGA TEXANA (A LA PLANCHA CON TOCINO Y QUESO GRATINADO), ARROZ Y FRIJOLES '),(30,'PAPA RELLENA DE CARNE ASADA, QUESADILLA, ENSALADA VERDE Y FRIJOLES'),(31,'POZOLE DE PUERCO'),(32,'CAMARONES EMPANIZADOS AL COCO BAÑADOS EN SALSA DE DURAZNO), ARROZ Y FRIJOLES'),(33,'BIRRIA DE RES'),(34,'SOPES DE PUERCO (3)'),(35,'ENCHILADAS SUIZAS, ENSALADA VERDE Y FRIJOLES'),(36,'SOPA DE TORTILLA'),(37,'ENMOLADAS DE POLLO (4) Y FRIJOLES'),(38,'SALCHIPAPAS BBQ'),(39,'PAPAS CON MACHACA Y FRIJOLES'),(40,'NACHOS CON CARNE'),(41,'MENUDO'),(42,'ENSALADA DE POLLO Y FRIJOLES'),(43,'TORTA CUBANA Y PAPAS'),(44,'BONELES CON BBQ Y BUFALO'),(45,'CHICHARRONES EN SALSA VERDE Y FRIJOLES'),(46,'PIZZA DE CARNE ASADA Y ESPAGUETI'),(47,'CHILAQUILES Y FRIJOLES'),(48,'Ensalada de atún');
/*!40000 ALTER TABLE `platillos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `platillosmenu`
--

DROP TABLE IF EXISTS `platillosmenu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `platillosmenu` (
  `idPlatilloMenu` int(11) NOT NULL AUTO_INCREMENT,
  `Platillos_idPlatillo` int(11) NOT NULL,
  `diaSemana` int(11) DEFAULT NULL,
  `cantidad` int(11) DEFAULT NULL,
  `reservados` int(10) unsigned DEFAULT NULL,
  `categoria` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`idPlatilloMenu`),
  UNIQUE KEY `idPlatilloMenu_UNIQUE` (`idPlatilloMenu`),
  KEY `fk_PlatilloMenu_Platillos1` (`Platillos_idPlatillo`),
  CONSTRAINT `fk_PlatilloMenu_Platillos1` FOREIGN KEY (`Platillos_idPlatillo`) REFERENCES `platillos` (`idPlatillo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `platillosmenu`
--

LOCK TABLES `platillosmenu` WRITE;
/*!40000 ALTER TABLE `platillosmenu` DISABLE KEYS */;
/*!40000 ALTER TABLE `platillosmenu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `productos`
--

DROP TABLE IF EXISTS `productos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `productos` (
  `idProducto` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(20) DEFAULT NULL,
  `stock` float unsigned DEFAULT NULL,
  PRIMARY KEY (`idProducto`),
  UNIQUE KEY `idProductos_UNIQUE` (`idProducto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `productos`
--

LOCK TABLES `productos` WRITE;
/*!40000 ALTER TABLE `productos` DISABLE KEYS */;
/*!40000 ALTER TABLE `productos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reservasplatillo`
--

DROP TABLE IF EXISTS `reservasplatillo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `reservasplatillo` (
  `idReserva` int(11) NOT NULL AUTO_INCREMENT,
  `Clientes_idCliente` int(11) NOT NULL,
  `Platillos_idPlatillo` int(11) NOT NULL,
  `cantidad` int(11) DEFAULT NULL,
  `fechaReserva` date DEFAULT NULL,
  `TipoReserva_idTipoReserva` int(11) NOT NULL,
  PRIMARY KEY (`idReserva`,`TipoReserva_idTipoReserva`),
  UNIQUE KEY `idReserva_UNIQUE` (`idReserva`),
  KEY `fk_Reservas_Clientes1_idx` (`Clientes_idCliente`),
  KEY `fk_Reservas_TipoReserva1_idx` (`TipoReserva_idTipoReserva`),
  KEY `fk_Reservas_Platillos1_idx` (`Platillos_idPlatillo`),
  CONSTRAINT `fk_Reservas_Clientes1` FOREIGN KEY (`Clientes_idCliente`) REFERENCES `clientes` (`idCliente`),
  CONSTRAINT `fk_Reservas_Platillos1` FOREIGN KEY (`Platillos_idPlatillo`) REFERENCES `platillos` (`idPlatillo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reservasplatillo`
--

LOCK TABLES `reservasplatillo` WRITE;
/*!40000 ALTER TABLE `reservasplatillo` DISABLE KEYS */;
/*!40000 ALTER TABLE `reservasplatillo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `salidasproductos`
--

DROP TABLE IF EXISTS `salidasproductos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `salidasproductos` (
  `idSalidasProductos` int(11) NOT NULL AUTO_INCREMENT,
  `Productos_idProductos` int(11) NOT NULL,
  `cantidad` float DEFAULT NULL,
  PRIMARY KEY (`idSalidasProductos`),
  UNIQUE KEY `idSalidasProductos_UNIQUE` (`idSalidasProductos`),
  KEY `fk_SalidasProductos_Productos1_idx` (`Productos_idProductos`),
  CONSTRAINT `fk_SalidasProductos_Productos1` FOREIGN KEY (`Productos_idProductos`) REFERENCES `productos` (`idProducto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `salidasproductos`
--

LOCK TABLES `salidasproductos` WRITE;
/*!40000 ALTER TABLE `salidasproductos` DISABLE KEYS */;
/*!40000 ALTER TABLE `salidasproductos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tiporeserva`
--

DROP TABLE IF EXISTS `tiporeserva`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tiporeserva` (
  `idTipoReserva` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(30) NOT NULL,
  PRIMARY KEY (`idTipoReserva`),
  UNIQUE KEY `idTipoReserva_UNIQUE` (`idTipoReserva`),
  UNIQUE KEY `nombre_UNIQUE` (`nombre`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tiporeserva`
--

LOCK TABLES `tiporeserva` WRITE;
/*!40000 ALTER TABLE `tiporeserva` DISABLE KEYS */;
/*!40000 ALTER TABLE `tiporeserva` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `usuarios` (
  `idUsuario` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(30) DEFAULT NULL,
  `contra` varchar(20) DEFAULT NULL,
  `tipoAdmin` tinyint(3) unsigned DEFAULT NULL,
  PRIMARY KEY (`idUsuario`),
  UNIQUE KEY `idUsuario_UNIQUE` (`idUsuario`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES (1,'Magui','cocinamagui',1),(2,'Cajero 1','1234',0);
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ventas`
--

DROP TABLE IF EXISTS `ventas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `ventas` (
  `folio` int(11) NOT NULL AUTO_INCREMENT,
  `fechaHora` datetime DEFAULT NULL,
  `Usuarios_idUsuario` int(11) NOT NULL,
  PRIMARY KEY (`folio`,`Usuarios_idUsuario`),
  UNIQUE KEY `folio_UNIQUE` (`folio`),
  KEY `fk_Ventas_Usuarios1_idx` (`Usuarios_idUsuario`),
  CONSTRAINT `fk_Ventas_Usuarios1` FOREIGN KEY (`Usuarios_idUsuario`) REFERENCES `usuarios` (`idUsuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ventas`
--

LOCK TABLES `ventas` WRITE;
/*!40000 ALTER TABLE `ventas` DISABLE KEYS */;
/*!40000 ALTER TABLE `ventas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ventascredito`
--

DROP TABLE IF EXISTS `ventascredito`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `ventascredito` (
  `idVentaCredito` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `Ventas_folio` int(11) NOT NULL,
  `Clientes_idCliente` int(11) NOT NULL,
  `cantidadDesayunos` int(11) unsigned DEFAULT NULL,
  `cantidadComidas` int(11) unsigned DEFAULT NULL,
  `cantidadCenas` int(11) unsigned DEFAULT NULL,
  `monto` float unsigned NOT NULL,
  PRIMARY KEY (`idVentaCredito`),
  UNIQUE KEY `idVentaCredito_UNIQUE` (`idVentaCredito`),
  KEY `fk_Ventas_has_Clientes_Clientes1_idx` (`Clientes_idCliente`),
  KEY `fk_Ventas_has_Clientes_Ventas1_idx` (`Ventas_folio`),
  CONSTRAINT `fk_Ventas_has_Clientes_Clientes1` FOREIGN KEY (`Clientes_idCliente`) REFERENCES `clientes` (`idCliente`),
  CONSTRAINT `fk_Ventas_has_Clientes_Ventas1` FOREIGN KEY (`Ventas_folio`) REFERENCES `ventas` (`folio`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ventascredito`
--

LOCK TABLES `ventascredito` WRITE;
/*!40000 ALTER TABLE `ventascredito` DISABLE KEYS */;
/*!40000 ALTER TABLE `ventascredito` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ventasplatillos`
--

DROP TABLE IF EXISTS `ventasplatillos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `ventasplatillos` (
  `idVentaPlatillo` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `Ventas_folio` int(11) NOT NULL,
  `nombreCliente` varchar(45) NOT NULL,
  `Platillos_idPlatillo` int(11) NOT NULL,
  `costo` float unsigned NOT NULL,
  `cantidad` int(10) unsigned NOT NULL,
  PRIMARY KEY (`idVentaPlatillo`),
  UNIQUE KEY `idVentaPlatillo_UNIQUE` (`idVentaPlatillo`),
  KEY `fk_Ventas_has_Platillos_Platillos1_idx` (`Platillos_idPlatillo`),
  KEY `fk_Ventas_has_Platillos_Ventas1_idx` (`Ventas_folio`),
  CONSTRAINT `fk_Ventas_has_Platillos_Platillos1` FOREIGN KEY (`Platillos_idPlatillo`) REFERENCES `platillos` (`idPlatillo`),
  CONSTRAINT `fk_Ventas_has_Platillos_Ventas1` FOREIGN KEY (`Ventas_folio`) REFERENCES `ventas` (`folio`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ventasplatillos`
--

LOCK TABLES `ventasplatillos` WRITE;
/*!40000 ALTER TABLE `ventasplatillos` DISABLE KEYS */;
/*!40000 ALTER TABLE `ventasplatillos` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-05-23 15:44:49
