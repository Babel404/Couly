-- MySQL dump 10.13  Distrib 5.7.23, for Linux (x86_64)
--
-- Host: localhost    Database: 3i017
-- ------------------------------------------------------
-- Server version	5.7.23

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
-- Table structure for table `friends`
--

DROP TABLE IF EXISTS `friends`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `friends` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `friend_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `friends`
--

LOCK TABLES `friends` WRITE;
/*!40000 ALTER TABLE `friends` DISABLE KEYS */;
INSERT INTO `friends` VALUES (2,7,2),(4,1,7),(5,1,2),(6,4,1),(7,1,10),(8,1,9),(9,9,1);
/*!40000 ALTER TABLE `friends` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `login_table`
--

DROP TABLE IF EXISTS `login_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `login_table` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `login` varchar(32) DEFAULT NULL,
  `password` varchar(32) DEFAULT NULL,
  `prenom` varchar(255) DEFAULT NULL,
  `nom` varchar(255) DEFAULT NULL,
  `userpp` varchar(255) NOT NULL,
  `color` varchar(7) NOT NULL,
  `description` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `login` (`login`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `login_table`
--

LOCK TABLES `login_table` WRITE;
/*!40000 ALTER TABLE `login_table` DISABLE KEYS */;
INSERT INTO `login_table` VALUES (1,'login0','password0','prenom0','nom0','https://lescreasdetiti.fr/52303-large_default/motif-thermocollant-point-d-interrogation.jpg','','Petite description du profil, rien de bien fou'),(2,'login2','password2','prenom2','nom2','https://lescreasdetiti.fr/52303-large_default/motif-thermocollant-point-d-interrogation.jpg','',''),(3,'login3','password3','prenom2','nom2','','',''),(4,'login4','password3','prenom2','nom2','','',''),(5,'login3000','password3','prenom2','nom2','','',''),(6,'login30','password3','prenom2','nom2','','',''),(7,'keraroo2a','kerarooo','rhl','ker','','',''),(8,'kerarooa','kerarooo','rhl','ker','','',''),(9,'babel','babel','Babel',NULL,'https://upload.wikimedia.org/wikipedia/commons/thumb/7/70/Cleve-van_construction-tower-babel.jpg/260px-Cleve-van_construction-tower-babel.jpg','#28601c','Je compile du javascript avec passion'),(10,'botsaa','password','PO',NULL,'https://pbs.twimg.com/profile_images/1114307014912618496/MOxKGHQL_bigger.png','#6695e2','Hey !');
/*!40000 ALTER TABLE `login_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sessions`
--

DROP TABLE IF EXISTS `sessions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sessions` (
  `connexion_key` varchar(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `date` datetime NOT NULL,
  `state` int(11) NOT NULL,
  PRIMARY KEY (`connexion_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sessions`
--

LOCK TABLES `sessions` WRITE;
/*!40000 ALTER TABLE `sessions` DISABLE KEYS */;
INSERT INTO `sessions` VALUES ('30',0,'2019-02-27 11:06:51',1),('31',0,'2019-02-27 11:08:02',1),('32',7,'2019-02-27 11:13:37',1),('AFv3FkZg',4,'2019-04-16 09:31:14',1),('EjGaweez',1,'2019-04-09 13:17:38',1),('FjLB50XT',1,'2019-04-16 09:31:03',1),('gbbGciXD',1,'2019-04-16 09:30:57',1),('ggAmRVQA',1,'2019-04-09 13:15:06',1),('gqTakIZO',1,'2019-04-09 13:23:45',1),('k7j4gq5K',7,'2019-03-05 11:13:03',1),('nO405fVL',1,'2019-04-16 10:01:46',1),('PYPE1682',1,'2019-04-16 09:20:55',1),('SvUcPbIn',1,'2019-03-05 10:52:14',0),('sz8PGfNG',1,'2019-04-09 13:14:31',1),('Tfh8W1uS',9,'2019-04-16 10:01:53',1),('wpLiEsoo',1,'2019-03-05 10:51:56',1),('xRMmgzkL',1,'2019-04-09 13:14:59',1);
/*!40000 ALTER TABLE `sessions` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-04-16 10:34:26
