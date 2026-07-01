-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema projeto
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema projeto
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `projeto` DEFAULT CHARACTER SET utf8mb3 ;
USE `projeto` ;

-- -----------------------------------------------------
-- Table `projeto`.`regiao`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projeto`.`regiao` (
  `id_regiao` INT NOT NULL AUTO_INCREMENT,
  `nome_regiao` VARCHAR(45) NULL DEFAULT NULL,
  `uf` VARCHAR(2) NULL DEFAULT NULL,
  `descricao_regiao` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id_regiao`),
  UNIQUE INDEX `id_regiao_UNIQUE` (`id_regiao` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 8
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `projeto`.`associacao`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projeto`.`associacao` (
  `id_associacao` INT NOT NULL AUTO_INCREMENT,
  `nome_associacao` VARCHAR(100) NULL DEFAULT NULL,
  `cnpj` VARCHAR(14) NULL DEFAULT NULL,
  `telefone` VARCHAR(13) NULL DEFAULT NULL,
  `email` VARCHAR(100) NULL DEFAULT NULL,
  `regiao_id_regiao` INT NOT NULL,
  PRIMARY KEY (`id_associacao`),
  UNIQUE INDEX `id_associacao_UNIQUE` (`id_associacao` ASC) VISIBLE,
  INDEX `fk_associacao_regiao1_idx` (`regiao_id_regiao` ASC) VISIBLE,
  CONSTRAINT `fk_associacao_regiao1`
    FOREIGN KEY (`regiao_id_regiao`)
    REFERENCES `projeto`.`regiao` (`id_regiao`))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `projeto`.`operadora`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projeto`.`operadora` (
  `id_operadora` INT NOT NULL AUTO_INCREMENT,
  `nome_operadora` VARCHAR(100) NULL DEFAULT NULL,
  `cnpj` VARCHAR(12) NULL DEFAULT NULL,
  `operadoracol` VARCHAR(45) NULL DEFAULT NULL,
  `registro_ans` INT NULL DEFAULT NULL,
  `telefone` VARCHAR(13) NULL DEFAULT NULL,
  `email` VARCHAR(155) NULL DEFAULT NULL,
  PRIMARY KEY (`id_operadora`),
  UNIQUE INDEX `id_operadora_UNIQUE` (`id_operadora` ASC) VISIBLE,
  UNIQUE INDEX `cnpj_UNIQUE` (`cnpj` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `projeto`.`vendedor`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projeto`.`vendedor` (
  `idvendedor` INT NOT NULL AUTO_INCREMENT,
  `nome_vendedor` VARCHAR(155) NULL DEFAULT NULL,
  PRIMARY KEY (`idvendedor`),
  UNIQUE INDEX `idvendedor_UNIQUE` (`idvendedor` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `projeto`.`config_comissao`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projeto`.`config_comissao` (
  `id_config_comissao` INT NOT NULL AUTO_INCREMENT,
  `descricao_config` VARCHAR(155) NULL DEFAULT NULL,
  `percentual_comissao` FLOAT NULL DEFAULT NULL,
  `operadora_idoperadora` INT NOT NULL,
  `vendedor_idvendedor` INT NOT NULL,
  PRIMARY KEY (`id_config_comissao`),
  UNIQUE INDEX `id_config_comissao_UNIQUE` (`id_config_comissao` ASC) VISIBLE,
  INDEX `fk_config_comissao_operadora1_idx` (`operadora_idoperadora` ASC) VISIBLE,
  INDEX `fk_config_comissao_vendedor1_idx` (`vendedor_idvendedor` ASC) VISIBLE,
  CONSTRAINT `fk_config_comissao_operadora1`
    FOREIGN KEY (`operadora_idoperadora`)
    REFERENCES `projeto`.`operadora` (`id_operadora`),
  CONSTRAINT `fk_config_comissao_vendedor1`
    FOREIGN KEY (`vendedor_idvendedor`)
    REFERENCES `projeto`.`vendedor` (`idvendedor`))
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `projeto`.`pessoa`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projeto`.`pessoa` (
  `id_pessoa` INT NOT NULL AUTO_INCREMENT,
  `nome_pessoa` VARCHAR(155) NULL DEFAULT NULL,
  `cpf_cnpj` VARCHAR(13) NULL DEFAULT NULL,
  `idade` INT NULL DEFAULT NULL,
  `telefone` VARCHAR(13) NULL DEFAULT NULL,
  `email` VARCHAR(155) NULL DEFAULT NULL,
  `tipo_pessoa` ENUM('J', 'P') NULL DEFAULT NULL,
  `regiao_id_regiao` INT NOT NULL,
  PRIMARY KEY (`id_pessoa`),
  UNIQUE INDEX `id_pessoa_UNIQUE` (`id_pessoa` ASC) VISIBLE,
  INDEX `fk_pessoa_regiao1_idx` (`regiao_id_regiao` ASC) VISIBLE,
  CONSTRAINT `fk_pessoa_regiao1`
    FOREIGN KEY (`regiao_id_regiao`)
    REFERENCES `projeto`.`regiao` (`id_regiao`))
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `projeto`.`plano`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projeto`.`plano` (
  `id_plano` INT NOT NULL AUTO_INCREMENT,
  `nome_plano` VARCHAR(55) NULL DEFAULT NULL,
  `tipo_plano` ENUM('O', 'M') NULL DEFAULT NULL,
  `cobertura` VARCHAR(55) NULL DEFAULT NULL,
  `acomodacao` VARCHAR(45) NULL DEFAULT NULL,
  `id_operadora` INT NULL DEFAULT NULL,
  PRIMARY KEY (`id_plano`),
  UNIQUE INDEX `id_plano_UNIQUE` (`id_plano` ASC) VISIBLE,
  INDEX `fk_plano_operadora_idx` (`id_operadora` ASC) VISIBLE,
  CONSTRAINT `fk_plano_operadora`
    FOREIGN KEY (`id_operadora`)
    REFERENCES `projeto`.`operadora` (`id_operadora`))
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `projeto`.`tabela_preco_operadora`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projeto`.`tabela_preco_operadora` (
  `id_tabela_preco_operadora` INT NOT NULL AUTO_INCREMENT,
  `data_inicio_vigencia` DATE NULL DEFAULT NULL,
  `data_final_vigencia` DATE NULL DEFAULT NULL,
  `valor_base` FLOAT NULL DEFAULT NULL,
  `tabela_preco_operadoracol` DECIMAL(10,0) NULL DEFAULT NULL,
  `faixa_etaria` VARCHAR(7) NULL DEFAULT NULL,
  `plano_id` INT NOT NULL,
  PRIMARY KEY (`id_tabela_preco_operadora`),
  UNIQUE INDEX `id_tabela_preco_operadora_UNIQUE` (`id_tabela_preco_operadora` ASC) VISIBLE,
  INDEX `fk_tabela_preco_operadora_plano1_idx` (`plano_id` ASC) VISIBLE,
  CONSTRAINT `fk_tabela_preco_operadora_plano1`
    FOREIGN KEY (`plano_id`)
    REFERENCES `projeto`.`plano` (`id_plano`))
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `projeto`.`contrato`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projeto`.`contrato` (
  `id_contrato` INT NOT NULL AUTO_INCREMENT,
  `numero_contrato` INT NULL DEFAULT NULL,
  `data_inicio` DATE NULL DEFAULT NULL,
  `data_fim` DATE NULL DEFAULT NULL,
  `pessoa_id` INT NOT NULL,
  `id_config_comissao` INT NOT NULL,
  `associacao_id_associacao` INT NOT NULL,
  `tabela_preco_operadora_id_tabela_preco_operadora` INT NOT NULL,
  PRIMARY KEY (`id_contrato`),
  UNIQUE INDEX `id_contrato_UNIQUE` (`id_contrato` ASC) VISIBLE,
  INDEX `fk_contrato_pessoa1_idx` (`pessoa_id` ASC) VISIBLE,
  INDEX `fk_contrato_config_comissao1_idx` (`id_config_comissao` ASC) VISIBLE,
  INDEX `fk_contrato_associacao1_idx` (`associacao_id_associacao` ASC) VISIBLE,
  INDEX `fk_contrato_tabela_preco_operadora1_idx` (`tabela_preco_operadora_id_tabela_preco_operadora` ASC) VISIBLE,
  CONSTRAINT `fk_contrato_associacao1`
    FOREIGN KEY (`associacao_id_associacao`)
    REFERENCES `projeto`.`associacao` (`id_associacao`),
  CONSTRAINT `fk_contrato_config_comissao1`
    FOREIGN KEY (`id_config_comissao`)
    REFERENCES `projeto`.`config_comissao` (`id_config_comissao`),
  CONSTRAINT `fk_contrato_pessoa1`
    FOREIGN KEY (`pessoa_id`)
    REFERENCES `projeto`.`pessoa` (`id_pessoa`),
  CONSTRAINT `fk_contrato_tabela_preco_operadora1`
    FOREIGN KEY (`tabela_preco_operadora_id_tabela_preco_operadora`)
    REFERENCES `projeto`.`tabela_preco_operadora` (`id_tabela_preco_operadora`))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `projeto`.`boleto`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projeto`.`boleto` (
  `id_boleto` INT NOT NULL AUTO_INCREMENT,
  `numero_boleto` VARCHAR(48) NULL DEFAULT NULL,
  `data_emissao` DATE NULL DEFAULT NULL,
  `data_vencimento` DATE NULL DEFAULT NULL,
  `valor` DECIMAL(10,0) NULL DEFAULT NULL,
  `status_pagamento` VARCHAR(10) NULL DEFAULT NULL,
  `boletocol` VARCHAR(45) NULL DEFAULT NULL,
  `contrato_id` INT NOT NULL,
  PRIMARY KEY (`id_boleto`),
  UNIQUE INDEX `id_boleto_UNIQUE` (`id_boleto` ASC) VISIBLE,
  INDEX `fk_boleto_contrato1_idx` (`contrato_id` ASC) VISIBLE,
  CONSTRAINT `fk_boleto_contrato1`
    FOREIGN KEY (`contrato_id`)
    REFERENCES `projeto`.`contrato` (`id_contrato`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
