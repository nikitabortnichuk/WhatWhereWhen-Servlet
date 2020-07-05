CREATE DATABASE  IF NOT EXISTS `game_www` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;
USE `game_www`;
-- MySQL dump 10.13  Distrib 8.0.19, for macos10.15 (x86_64)
--
-- Host: localhost    Database: game_www
-- ------------------------------------------------------
-- Server version	5.7.26

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
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `admin` (
  `admin_id` int(11) NOT NULL AUTO_INCREMENT,
  `login` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  PRIMARY KEY (`admin_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin`
--

LOCK TABLES `admin` WRITE;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
INSERT INTO `admin` VALUES (1,'admin','admin');
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `games`
--

DROP TABLE IF EXISTS `games`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `games` (
  `game_id` int(11) NOT NULL AUTO_INCREMENT,
  `game_identification` varchar(40) NOT NULL,
  `round_time` int(11) NOT NULL,
  `players_number` int(11) NOT NULL,
  `rounds_number` int(11) NOT NULL,
  `expert_score` int(11) DEFAULT NULL,
  `opponent_score` int(11) DEFAULT NULL,
  `number_of_used_hints` int(11) DEFAULT NULL,
  `average_time_per_round` int(11) DEFAULT NULL,
  `average_score_per_round` int(11) DEFAULT NULL,
  `is_available` tinyint(4) NOT NULL,
  PRIMARY KEY (`game_id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `games`
--

LOCK TABLES `games` WRITE;
/*!40000 ALTER TABLE `games` DISABLE KEYS */;
INSERT INTO `games` VALUES (1,'7dQLD',25,2,3,0,0,0,0,0,0),(33,'lveN0',20,2,10,0,0,0,0,0,0),(34,'SQTan6t',20,2,5,0,0,0,0,0,0),(35,'jAQmp7lV',20,2,2,0,0,0,0,0,0),(36,'TXnAVLyYd',20,2,2,0,0,0,0,0,0),(37,'b5me9S',20,2,2,0,0,0,0,0,0),(38,'dJavYXI',20,2,1,0,1,NULL,NULL,NULL,0),(39,'mdfoclT',25,2,2,0,3,NULL,NULL,NULL,0),(40,'ahXgJ',20,2,3,NULL,NULL,NULL,NULL,NULL,1),(41,'JopI91Ym0',20,2,2,0,2,NULL,NULL,NULL,0),(42,'7AsI0ms0',20,2,2,0,2,NULL,NULL,NULL,0),(43,'k7R0HL0n',20,2,2,1,1,NULL,NULL,NULL,0);
/*!40000 ALTER TABLE `games` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hints`
--

DROP TABLE IF EXISTS `hints`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hints` (
  `hint_id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`hint_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hints`
--

LOCK TABLES `hints` WRITE;
/*!40000 ALTER TABLE `hints` DISABLE KEYS */;
/*!40000 ALTER TABLE `hints` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `questions`
--

DROP TABLE IF EXISTS `questions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `questions` (
  `question_id` int(11) NOT NULL AUTO_INCREMENT,
  `text` varchar(1000) NOT NULL,
  `question_type` enum('WITH_VARIANTS','NO_VARIANTS') NOT NULL,
  `answer` varchar(200) DEFAULT NULL,
  `game_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`question_id`),
  KEY `questions_game_id_idx_idx` (`game_id`),
  KEY `questions_answer_id_idx_idx` (`answer`),
  CONSTRAINT `questions_game_id_idx` FOREIGN KEY (`game_id`) REFERENCES `games` (`game_id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `questions`
--

LOCK TABLES `questions` WRITE;
/*!40000 ALTER TABLE `questions` DISABLE KEYS */;
INSERT INTO `questions` VALUES (1,'It was in Zagreb, Bucharest, Dresden, Berlin, Tbilisi, Munsk, Moscow and Kiev. Now it remains only in some of these cities, including Kyiv. Name their main competitor in Ukraine?','NO_VARIANTS','Shakhtar',NULL),(2,'Find the match: Kyiv - Shevchenko, Lviv - Franko, Kharkiv - Karazin. Who corresponds to Odessa?','NO_VARIANTS','Mechnikov',NULL),(3,'Initially, an ice chopper was used for HER, which was later replaced by a specially invented orbitoclast. In 1950, in the newspaper Pravda, SHE was called a pseudo-scientific method that demonstrates the powerlessness of bourgeois science. Name HER.','NO_VARIANTS','Lobotomy',NULL),(4,'English Admiral Edward Vernon hated when sailors got drunk. So he ordered to dilute what they drank (rum). What was the nickname of this admiral?','NO_VARIANTS','Grog',NULL),(5,'The first SHE refers to the speaker. We just used the third. What word did we replace with \"SHE\"?','NO_VARIANTS','Person',NULL),(6,'The first Ukrainian medical schools took in students from seminaries and theological academies, because they also had to deal with the dead. What word is omitted here?','NO_VARIANTS','Languages',NULL),(7,'In \"The Story of Igor\'s Regiment\" the names of animals and birds are used as symbols. Yes, a crow is a symbol of grief, a swan is a symbol of a beautiful girl, a falcon is a symbol of a good fellow. And what bird became a symbol of a grieving woman?','NO_VARIANTS','Cuckoo',NULL),(8,'It became popular among Europeans when the Industrial Revolution took place in the 12th century and most citizens began working in factories. SHE is considered one of the most dangerous health trends. Name it.','WITH_VARIANTS',NULL,NULL),(9,'In 2000, Elon Musk contracted malaria during an entertainment trip. Then he talked about it like \"SHE KILLS.\" The decree is its kind. Name HER.','WITH_VARIANTS',NULL,NULL),(10,'It is HIM that devours the night. It is HIS dualism that is studied in school. Peace, not HIM Master was awarded. What it is?','WITH_VARIANTS',NULL,NULL),(11,'A newspaper article on the history of Irish cuisine mentions \"white meat\". This product is obtained after a certain process of processing what was obtained from the cow.','WITH_VARIANTS',NULL,NULL),(12,'An Arabic proverb says that a brave man is tested by war, a friend is tested by need, and a sage is tested by what the English poet Alexander Pop called \"The displacement of the mistakes of another.\" What it is ?','WITH_VARIANTS',NULL,NULL),(13,'The goddess Hera, wishing the death of Hercules, appeared before him in the form of ALPHA. Most Peruvians call the upper part of ALPHA the word \"Rio de Janeiro\". What has been replaced by the word \"ALPHA\"?','WITH_VARIANTS',NULL,NULL),(14,'According to the teachings of Islam, at the second sound of the trumpet of the angel of Israel, everyone will die, except for a few wounded. The last to die is the angel Azrael. What is he considered an angel?','WITH_VARIANTS',NULL,NULL),(15,'French scientists have created a device consisting of fur that mimics the lungs, tubes and airways that mimic the trachea, larynx and nasopharynx. The device is designed to find ways to combat a phenomenon. What is this phenomenon?','WITH_VARIANTS',NULL,NULL);
/*!40000 ALTER TABLE `questions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(40) NOT NULL,
  `email` varchar(300) NOT NULL,
  `password` varchar(45) NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'beem','beem@mail.com','hello'),(2,'master','master@gmail.com','bye'),(3,'nikita','nikita@email.com','nikita'),(4,'expert','expert@email.com','expert'),(5,'antika','antika@email.com','qazwsxedc'),(6,'param','param@mail.com','paramparam');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users_games`
--

DROP TABLE IF EXISTS `users_games`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users_games` (
  `users_games_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `game_id` int(11) NOT NULL,
  PRIMARY KEY (`users_games_id`),
  KEY `users_games_user_id_idx_idx` (`user_id`),
  KEY `users_games_game_id_idx_idx` (`game_id`),
  CONSTRAINT `users_games_game_id_idx` FOREIGN KEY (`game_id`) REFERENCES `games` (`game_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `users_games_user_id_idx` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users_games`
--

LOCK TABLES `users_games` WRITE;
/*!40000 ALTER TABLE `users_games` DISABLE KEYS */;
INSERT INTO `users_games` VALUES (1,4,39),(2,4,38),(3,3,38),(4,1,41),(5,3,41),(6,6,42),(7,3,42),(8,1,43),(9,3,43);
/*!40000 ALTER TABLE `users_games` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `variants`
--

DROP TABLE IF EXISTS `variants`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `variants` (
  `variant_id` int(11) NOT NULL AUTO_INCREMENT,
  `text` varchar(200) NOT NULL,
  `question_id` int(11) NOT NULL,
  `is_correct` tinyint(4) NOT NULL,
  PRIMARY KEY (`variant_id`),
  KEY `variants_question_id_idx_idx` (`question_id`),
  CONSTRAINT `variants_question_id_idx` FOREIGN KEY (`question_id`) REFERENCES `questions` (`question_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `variants`
--

LOCK TABLES `variants` WRITE;
/*!40000 ALTER TABLE `variants` DISABLE KEYS */;
INSERT INTO `variants` VALUES (1,'Dress with a corset',8,0),(2,'Handkerchief',8,0),(3,'Tanning',8,1),(4,'Tie',8,0),(5,'Africa',9,0),(6,'Disease',9,0),(7,'Vacation',9,1),(8,'Negligence',9,0),(9,'Light',10,1),(10,'Fear',10,0),(11,'Rest',10,0),(12,'Life',10,0),(13,'Brisket',11,0),(14,'Cottage cheese',11,1),(15,'Salo',11,0),(16,'Milk',11,0),(17,'Silence',12,0),(18,'Time',12,0),(19,'Thoughts',12,0),(20,'Anger',12,1),(21,'Amazon',13,1),(22,'Brazil',13,0),(23,'Love',13,0),(24,'Aphrodite',13,0),(25,'Amour',14,0),(26,'Hope',14,0),(27,'Life',14,0),(28,'Death',14,1),(29,'Pneumonia',15,0),(30,'Lung Cancer',15,0),(31,'Snore',15,1),(32,'Cough',15,0);
/*!40000 ALTER TABLE `variants` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-07-05 19:27:28
