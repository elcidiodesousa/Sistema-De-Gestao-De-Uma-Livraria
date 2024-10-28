CREATE DATABASE  IF NOT EXISTS `bdlivraria` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `bdlivraria`;
-- MySQL dump 10.13  Distrib 8.0.30, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: bdlivraria
-- ------------------------------------------------------
-- Server version	8.0.30

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
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
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clientes` (
  `id_cliente` int NOT NULL,
  `nome` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `telefone` varchar(15) NOT NULL,
  `endereco` varchar(200) NOT NULL,
  `sexo` varchar(10) NOT NULL,
  `tipo_cliente` varchar(20) NOT NULL,
  `vendas` int DEFAULT '0',
  PRIMARY KEY (`id_cliente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clientes`
--

LOCK TABLES `clientes` WRITE;
/*!40000 ALTER TABLE `clientes` DISABLE KEYS */;
INSERT INTO `clientes` VALUES (2138,'Amiel Guepson','amiel@gmail.com','841561758','Maxaquene','MASCULINO','NORMAL',0),(2324,'Paulo Quive','paulo@gmail.com','841561758','Chokwe','MASCULINO','NORMAL',0),(2332,'Ivone Chongo','ivone590@gmail.com','840026492','Maputo','FEMININO','NORMAL',0),(2345,'Ecineta Mate','tina@gmail.com','840152309','Chokwe','MASCULINO','NORMAL',0),(6497,'Zidane Guambe','zidas@gmail.com','8715320725','Patrice Lumumba','MASCULINO','VIP',5),(6914,'Elcidio De Sousa Paulo Quive','elcidiospq@gmail.com','860182671','Malhangalene','MASCULINO','VIP',0);
/*!40000 ALTER TABLE `clientes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fornecedores`
--

DROP TABLE IF EXISTS `fornecedores`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fornecedores` (
  `idFornecedor` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `telefone` varchar(20) NOT NULL,
  `endereco` varchar(255) NOT NULL,
  PRIMARY KEY (`idFornecedor`)
) ENGINE=InnoDB AUTO_INCREMENT=8062 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fornecedores`
--

LOCK TABLES `fornecedores` WRITE;
/*!40000 ALTER TABLE `fornecedores` DISABLE KEYS */;
INSERT INTO `fornecedores` VALUES (8061,'UNI','ffe','455','dfs');
/*!40000 ALTER TABLE `fornecedores` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `funcionarios`
--

DROP TABLE IF EXISTS `funcionarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `funcionarios` (
  `idFuncionario` varchar(10) NOT NULL,
  `nome` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `telefone` varchar(15) DEFAULT NULL,
  `usuario` varchar(50) NOT NULL,
  `senha` decimal(12,0) NOT NULL,
  `status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idFuncionario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `funcionarios`
--

LOCK TABLES `funcionarios` WRITE;
/*!40000 ALTER TABLE `funcionarios` DISABLE KEYS */;
INSERT INTO `funcionarios` VALUES ('4973','Elcidio De Sousa','elcidiospq@gmail.com','860182671','Elcidio De Sousa',12345,'Admnistrador'),('6189','Amiel Quive','amiel@gmail.com','841561758','Amiel',123,'Admnistrador'),('8371','Helder','h@gmail.com','852836283','helder',123,'Vendedor');
/*!40000 ALTER TABLE `funcionarios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `livros`
--

DROP TABLE IF EXISTS `livros`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `livros` (
  `id_livro` varchar(255) NOT NULL,
  `titulo` varchar(255) NOT NULL,
  `autor` varchar(255) NOT NULL,
  `genero` varchar(100) DEFAULT NULL,
  `isbn` varchar(20) NOT NULL,
  `editora` varchar(255) DEFAULT NULL,
  `ano_publicacao` int DEFAULT NULL,
  `preco` decimal(10,2) NOT NULL,
  `quantidade_estoque` int NOT NULL,
  `data_cadastro` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `fornecedor` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_livro`),
  UNIQUE KEY `isbn` (`isbn`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `livros`
--

LOCK TABLES `livros` WRITE;
/*!40000 ALTER TABLE `livros` DISABLE KEYS */;
INSERT INTO `livros` VALUES ('3','A arte Da Guerra','Sun Tzun','Tratado','836326484','Novo Seculo',2012,2500.00,176,'2024-10-22 09:32:03',NULL),('LIV3800','De Onde Vem As Estrelas','Yuri Lopes','Drama','714383523','Plural Editores',2024,3000.00,6,'2024-10-22 17:55:08','UNI');
/*!40000 ALTER TABLE `livros` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vendas`
--

DROP TABLE IF EXISTS `vendas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vendas` (
  `id` varchar(20) NOT NULL,
  `cliente` varchar(100) NOT NULL,
  `livro` varchar(200) NOT NULL,
  `quantidade` int NOT NULL,
  `preco` decimal(10,2) NOT NULL,
  `pagamento` varchar(50) NOT NULL,
  `desconto` decimal(10,2) DEFAULT '0.00',
  `iva` decimal(10,2) NOT NULL,
  `total` varchar(200) NOT NULL,
  `data_venda` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vendas`
--

LOCK TABLES `vendas` WRITE;
/*!40000 ALTER TABLE `vendas` DISABLE KEYS */;
INSERT INTO `vendas` VALUES ('','Paulo Quive','De Onde Vem As Estrelas',3,3000.00,'CARTAO ',0.00,2.50,'9225.00 MZN','2024-10-23 15:20:58'),('COMP1209','Paulo Quive','De Onde Vem As Estrelas',2,3000.00,'CASH',0.00,2.50,'6150.00 MZN','2024-10-23 15:56:41'),('COMP1750','Elcidio De Sousa Paulo Quive','De Onde Vem As Estrelas',2,3000.00,'CASH',20.00,2.50,'4920.00 MZN','2024-10-22 21:46:39'),('COMP2001','Zidane Guambe','De Onde Vem As Estrelas',2,3000.00,'CASH',20.00,2.50,'4920.00 MZN','2024-10-26 12:04:07'),('COMP2703','Ivone Chongo','De Onde Vem As Estrelas',3,3000.00,'CASH',0.00,2.50,'9225.00 MZN','2024-10-22 21:56:39'),('COMP3084','Zidane Guambe','De Onde Vem As Estrelas',1,3000.00,'CASH',0.00,2.50,'3075.00 MZN','2024-10-26 12:02:51'),('COMP3099','Paulo Quive','De Onde Vem As Estrelas',2,3000.00,'CASH',0.00,2.50,'6150.00 MZN','2024-10-23 15:54:28'),('COMP3281','Ivone Chongo','De Onde Vem As Estrelas',1,3000.00,'CASH',0.00,2.50,'3075.00 MZN','2024-10-23 15:48:24'),('COMP4553','Zidane Guambe','De Onde Vem As Estrelas',1,3000.00,'CASH',0.00,2.50,'3075.00 MZN','2024-10-26 12:00:28'),('COMP4664','Amiel Guepson','A arte Da Guerra',2,2500.00,'CARTEIRA MOVEL',0.00,2.50,'5125.00 MZN','2024-10-23 15:32:48'),('COMP4864','Amiel Guepson','A arte Da Guerra',1,0.00,'CASH',0.00,1.20,'0.00 MZN','2024-10-23 15:42:40'),('COMP6125','Elcidio De Sousa Paulo Quive','A arte Da Guerra',1,2500.00,'CASH',20.00,2.50,'2050.00 MZN','2024-10-23 15:41:20'),('COMP6203','Elcidio De Sousa Paulo Quive','A arte Da Guerra',1,2500.00,'CASH',20.00,2.50,'2050.00 MZN','2024-10-26 11:18:38'),('COMP6645','Ivone Chongo','A arte Da Guerra',4,0.00,'CASH',0.00,1.20,'0.00 MZN','2024-10-23 18:37:28'),('COMP7008','Elcidio De Sousa Paulo Quive','De Onde Vem As Estrelas',140,3000.00,'CASH',20.00,2.50,'344400.00 MZN','2024-10-26 11:37:35'),('COMP7032','Ecineta Mate','A arte Da Guerra',1,2500.00,'CASH',0.00,2.50,'2562.50 MZN','2024-10-22 21:53:22'),('COMP8307','Paulo Quive','De Onde Vem As Estrelas',1,3000.00,'CASH',0.00,2.50,'3075.00 MZN','2024-10-26 11:11:43'),('COMP8409','Zidane Guambe','A arte Da Guerra',2,2500.00,'CASH',0.00,2.50,'5125.00 MZN','2024-10-26 12:01:57'),('COMP9022','Zidane Guambe','A arte Da Guerra',1,2500.00,'CASH',0.00,2.50,'2562.50 MZN','2024-10-26 12:03:32'),('COMP9564','Ivone Chongo','De Onde Vem As Estrelas',2,3000.00,'CASH',0.00,2.50,'6150.00 MZN','2024-10-23 15:46:12');
/*!40000 ALTER TABLE `vendas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'bdlivraria'
--

--
-- Dumping routines for database 'bdlivraria'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-10-26 13:05:47
