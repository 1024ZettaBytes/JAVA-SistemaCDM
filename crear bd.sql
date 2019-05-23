-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema cdm
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema cdm
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `cdm` DEFAULT CHARACTER SET utf8 ;
USE `cdm` ;

-- -----------------------------------------------------
-- Table `cdm`.`clientes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cdm`.`clientes` (
  `idCliente` INT(11) NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NULL DEFAULT NULL,
  `creditoDesayuno` INT(10) UNSIGNED NOT NULL,
  `creditoComida` INT(10) UNSIGNED NOT NULL,
  `creditoCena` INT(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`idCliente`),
  UNIQUE INDEX `idCliente_UNIQUE` (`idCliente` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `cdm`.`productos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cdm`.`productos` (
  `idProducto` INT(11) NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(20) NULL DEFAULT NULL,
  `stock` FLOAT UNSIGNED NULL DEFAULT NULL,
  PRIMARY KEY (`idProducto`),
  UNIQUE INDEX `idProductos_UNIQUE` (`idProducto` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `cdm`.`entradasproductos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cdm`.`entradasproductos` (
  `idEntradasProductos` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `Productos_idProductos` INT(11) NOT NULL,
  `cantidad` FLOAT NULL DEFAULT NULL,
  `costo` FLOAT NULL DEFAULT NULL,
  PRIMARY KEY (`idEntradasProductos`),
  UNIQUE INDEX `idEntradasProductos_UNIQUE` (`idEntradasProductos` ASC) VISIBLE,
  INDEX `fk_EntradasProductos_Productos1_idx` (`Productos_idProductos` ASC) VISIBLE,
  CONSTRAINT `fk_EntradasProductos_Productos1`
    FOREIGN KEY (`Productos_idProductos`)
    REFERENCES `cdm`.`productos` (`idProducto`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `cdm`.`platillos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cdm`.`platillos` (
  `idPlatillo` INT(11) NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(100) NULL DEFAULT NULL,
  PRIMARY KEY (`idPlatillo`),
  UNIQUE INDEX `idPlatillo_UNIQUE` (`idPlatillo` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 49
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `cdm`.`platillosmenu`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cdm`.`platillosmenu` (
  `idPlatilloMenu` INT(11) NOT NULL AUTO_INCREMENT,
  `Platillos_idPlatillo` INT(11) NOT NULL,
  `diaSemana` INT(11) NULL DEFAULT NULL,
  `cantidad` INT(11) NULL DEFAULT NULL,
  `reservados` INT(10) UNSIGNED NULL DEFAULT NULL,
  `categoria` VARCHAR(15) NULL DEFAULT NULL,
  PRIMARY KEY (`idPlatilloMenu`),
  UNIQUE INDEX `idPlatilloMenu_UNIQUE` (`idPlatilloMenu` ASC) VISIBLE,
  INDEX `fk_PlatilloMenu_Platillos1` (`Platillos_idPlatillo` ASC) VISIBLE,
  CONSTRAINT `fk_PlatilloMenu_Platillos1`
    FOREIGN KEY (`Platillos_idPlatillo`)
    REFERENCES `cdm`.`platillos` (`idPlatillo`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `cdm`.`reservasplatillo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cdm`.`reservasplatillo` (
  `idReserva` INT(11) NOT NULL AUTO_INCREMENT,
  `Clientes_idCliente` INT(11) NOT NULL,
  `Platillos_idPlatillo` INT(11) NOT NULL,
  `cantidad` INT(11) NULL DEFAULT NULL,
  `fechaReserva` DATE NULL DEFAULT NULL,
  `TipoReserva_idTipoReserva` INT(11) NOT NULL,
  PRIMARY KEY (`idReserva`, `TipoReserva_idTipoReserva`),
  UNIQUE INDEX `idReserva_UNIQUE` (`idReserva` ASC) VISIBLE,
  INDEX `fk_Reservas_Clientes1_idx` (`Clientes_idCliente` ASC) VISIBLE,
  INDEX `fk_Reservas_TipoReserva1_idx` (`TipoReserva_idTipoReserva` ASC) VISIBLE,
  INDEX `fk_Reservas_Platillos1_idx` (`Platillos_idPlatillo` ASC) VISIBLE,
  CONSTRAINT `fk_Reservas_Clientes1`
    FOREIGN KEY (`Clientes_idCliente`)
    REFERENCES `cdm`.`clientes` (`idCliente`),
  CONSTRAINT `fk_Reservas_Platillos1`
    FOREIGN KEY (`Platillos_idPlatillo`)
    REFERENCES `cdm`.`platillos` (`idPlatillo`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `cdm`.`salidasproductos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cdm`.`salidasproductos` (
  `idSalidasProductos` INT(11) NOT NULL AUTO_INCREMENT,
  `Productos_idProductos` INT(11) NOT NULL,
  `cantidad` FLOAT NULL DEFAULT NULL,
  PRIMARY KEY (`idSalidasProductos`),
  UNIQUE INDEX `idSalidasProductos_UNIQUE` (`idSalidasProductos` ASC) VISIBLE,
  INDEX `fk_SalidasProductos_Productos1_idx` (`Productos_idProductos` ASC) VISIBLE,
  CONSTRAINT `fk_SalidasProductos_Productos1`
    FOREIGN KEY (`Productos_idProductos`)
    REFERENCES `cdm`.`productos` (`idProducto`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `cdm`.`tiporeserva`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cdm`.`tiporeserva` (
  `idTipoReserva` INT(11) NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(30) NOT NULL,
  PRIMARY KEY (`idTipoReserva`),
  UNIQUE INDEX `idTipoReserva_UNIQUE` (`idTipoReserva` ASC) VISIBLE,
  UNIQUE INDEX `nombre_UNIQUE` (`nombre` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `cdm`.`usuarios`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cdm`.`usuarios` (
  `idUsuario` INT(11) NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(30) NULL DEFAULT NULL,
  `contra` VARCHAR(20) NULL DEFAULT NULL,
  `tipoAdmin` TINYINT(3) UNSIGNED NULL DEFAULT NULL,
  PRIMARY KEY (`idUsuario`),
  UNIQUE INDEX `idUsuario_UNIQUE` (`idUsuario` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `cdm`.`ventas`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cdm`.`ventas` (
  `folio` INT(11) NOT NULL AUTO_INCREMENT,
  `fechaHora` DATETIME NULL DEFAULT NULL,
  `Usuarios_idUsuario` INT(11) NOT NULL,
  PRIMARY KEY (`folio`, `Usuarios_idUsuario`),
  UNIQUE INDEX `folio_UNIQUE` (`folio` ASC) VISIBLE,
  INDEX `fk_Ventas_Usuarios1_idx` (`Usuarios_idUsuario` ASC) VISIBLE,
  CONSTRAINT `fk_Ventas_Usuarios1`
    FOREIGN KEY (`Usuarios_idUsuario`)
    REFERENCES `cdm`.`usuarios` (`idUsuario`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `cdm`.`ventascredito`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cdm`.`ventascredito` (
  `idVentaCredito` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `Ventas_folio` INT(11) NOT NULL,
  `Clientes_idCliente` INT(11) NOT NULL,
  `cantidadDesayunos` INT(11) UNSIGNED NULL DEFAULT NULL,
  `cantidadComidas` INT(11) UNSIGNED NULL DEFAULT NULL,
  `cantidadCenas` INT(11) UNSIGNED NULL DEFAULT NULL,
  `monto` FLOAT UNSIGNED NOT NULL,
  PRIMARY KEY (`idVentaCredito`),
  UNIQUE INDEX `idVentaCredito_UNIQUE` (`idVentaCredito` ASC) VISIBLE,
  INDEX `fk_Ventas_has_Clientes_Clientes1_idx` (`Clientes_idCliente` ASC) VISIBLE,
  INDEX `fk_Ventas_has_Clientes_Ventas1_idx` (`Ventas_folio` ASC) VISIBLE,
  CONSTRAINT `fk_Ventas_has_Clientes_Clientes1`
    FOREIGN KEY (`Clientes_idCliente`)
    REFERENCES `cdm`.`clientes` (`idCliente`),
  CONSTRAINT `fk_Ventas_has_Clientes_Ventas1`
    FOREIGN KEY (`Ventas_folio`)
    REFERENCES `cdm`.`ventas` (`folio`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `cdm`.`ventasplatillos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cdm`.`ventasplatillos` (
  `idVentaPlatillo` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `Ventas_folio` INT(11) NOT NULL,
  `nombreCliente` VARCHAR(45) NOT NULL,
  `Platillos_idPlatillo` INT(11) NOT NULL,
  `costo` FLOAT UNSIGNED NOT NULL,
  `cantidad` INT(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`idVentaPlatillo`),
  UNIQUE INDEX `idVentaPlatillo_UNIQUE` (`idVentaPlatillo` ASC) VISIBLE,
  INDEX `fk_Ventas_has_Platillos_Platillos1_idx` (`Platillos_idPlatillo` ASC) VISIBLE,
  INDEX `fk_Ventas_has_Platillos_Ventas1_idx` (`Ventas_folio` ASC) VISIBLE,
  CONSTRAINT `fk_Ventas_has_Platillos_Platillos1`
    FOREIGN KEY (`Platillos_idPlatillo`)
    REFERENCES `cdm`.`platillos` (`idPlatillo`),
  CONSTRAINT `fk_Ventas_has_Platillos_Ventas1`
    FOREIGN KEY (`Ventas_folio`)
    REFERENCES `cdm`.`ventas` (`folio`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
