-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: user_db
-- ------------------------------------------------------
-- Server version	5.7.18

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `businessservice_table`
--

DROP TABLE IF EXISTS `businessservice_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `businessservice_table` (
  `id` varchar(9) COLLATE utf8mb4_unicode_ci NOT NULL,
  `tyre` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `unlocking` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `water` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `electricity` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `gasoline` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `trailer` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `beauty` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT '0',
  `repair` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `businessservice_table`
--

LOCK TABLES `businessservice_table` WRITE;
/*!40000 ALTER TABLE `businessservice_table` DISABLE KEYS */;
INSERT INTO `businessservice_table` VALUES ('111111111','201','81','101','121','301','501','面议','面议'),('222222222','210','65','120','0','0','0','面议','面议'),('333333333','0','0','0','99','280','470','面议','面议'),('444444444','0','0','110','80','0','0','0','面议'),('555555555','185','60','0','0','285','405','面议','0'),('666666666','0','0','0','0','0','550','面议','面议'),('777777777','190','0','99','0','295','0','0','0'),('888888888','0','70','0','110','0','480','0','0'),('999999999','250','100','90','115','299','490','0','0');
/*!40000 ALTER TABLE `businessservice_table` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-08-16 16:59:21
