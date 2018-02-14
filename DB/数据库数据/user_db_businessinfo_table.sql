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
-- Table structure for table `businessinfo_table`
--

DROP TABLE IF EXISTS `businessinfo_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `businessinfo_table` (
  `id` varchar(9) COLLATE utf8mb4_unicode_ci NOT NULL,
  `name` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `phone` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `address` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `description` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `photo` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `x` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `y` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `businessinfo_table`
--

LOCK TABLES `businessinfo_table` WRITE;
/*!40000 ALTER TABLE `businessinfo_table` DISABLE KEYS */;
INSERT INTO `businessinfo_table` VALUES ('000000000','孵鹰大厦店','025-12345678','江苏省南京市浦口区团结路100号孵鹰大厦D座一楼','服务至上','test.jpg',NULL,NULL),('111111111','南京第一家','025-11111111','卓越路与浦滨路交汇处东南150米','服务理念，一切为了客户，为了客户一切，为了一切客户','1.jpg','32.037301','118.642052'),('222222222','体育公园店','025-22222222','江苏省南京市浦口区江浦街道横江大道与海滨路交叉口东北','以人为本 以客为尊 卓越服务','4.jpg','32.047594','118.667151'),('333333333','浦口客运站店','025-33333333','江苏省南京市浦口区公园北路2号','保证服务品质，满足客户需求','5.jpg','32.071671','118.640786'),('444444444','工业园店','025-44444444','西华路2号','以客为尊 卓越服务 力争第一','6.jpg','32.023062','118.613083'),('555555555','江山荟店','025-55555555','荣庄路东150米','以诚相待，超越客户的需求;全心服务，为客户提供更多','7.jpg','32.023361','118.599602'),('666666666','南京北站店','025-66666666','浦口区津浦路30号(近义和庄村)',' 微笑挂在脸上，服务记在心里','8.jpg','32.10226','118.727507'),('777777777','开元店','025-77777777','浦口区文德东路35号鼎业开元大酒店内(近浦口区政府)','客户至上，服务周到;质量第一','9.jpg','32.066988','118.637305'),('888888888','浦口医院店','025-88888888','浦口区上河街166号','客户至上，技术争先，团结协作，求真务实','10.jpeg','32.055244','118.643634'),('999999999','南京东南铜业有限公司','025-99999999','江浦街道上河街城南路2-8号','用我们真诚的微笑，换取客户对我们服务的满意','11.jpeg','32.049618','118.643017');
/*!40000 ALTER TABLE `businessinfo_table` ENABLE KEYS */;
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
