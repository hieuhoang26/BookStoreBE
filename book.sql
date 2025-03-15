-- MySQL dump 10.13  Distrib 8.0.32, for Win64 (x86_64)
--
-- Host: localhost    Database: book_new
-- ------------------------------------------------------
-- Server version	8.0.32

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
-- Table structure for table `book`
--

DROP TABLE IF EXISTS `book`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `book` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `delete_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `author` varchar(255) NOT NULL,
  `current_quantity` int NOT NULL,
  `price` double NOT NULL,
  `sold_quantity` int NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `book_detail_id` int DEFAULT NULL,
  `shop_id` int DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  `create_by` int DEFAULT NULL,
  `updated_by` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_g0286ag1dlt4473st1ugemd0m` (`title`),
  UNIQUE KEY `UK_irgh8wpt0i2c1uv9650vf2g4m` (`book_detail_id`),
  KEY `FKnn8ggiy70jb2fxtq5ej0fuvga` (`shop_id`),
  KEY `FK1wxwagv6cm3vjrxqhmv884hir` (`user_id`),
  CONSTRAINT `FK1wxwagv6cm3vjrxqhmv884hir` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKio72xbateqjgsts3w2fwwr8l2` FOREIGN KEY (`book_detail_id`) REFERENCES `book_detail` (`id`),
  CONSTRAINT `FKnn8ggiy70jb2fxtq5ej0fuvga` FOREIGN KEY (`shop_id`) REFERENCES `shop` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `book`
--

LOCK TABLES `book` WRITE;
/*!40000 ALTER TABLE `book` DISABLE KEYS */;
INSERT INTO `book` VALUES (6,'2024-07-21 00:20:27.514000','2024-07-21 00:20:27.514000','2024-07-21 00:20:27.523000','Rabindranath Tagore',40,50,24,'Nationalism',9,3,NULL,NULL,NULL),(7,'2024-07-21 00:21:39.642000','2024-07-21 00:21:39.642000','2024-07-21 00:21:39.651000','Tite Kubo',40,20,23,'Bleach Volume 1',10,3,NULL,NULL,NULL),(8,'2024-07-28 18:06:41.115000','2024-07-28 18:06:41.115000','2024-07-28 18:06:41.137000','Tite Kubo',60,10000,40,'DragonBall',11,3,NULL,NULL,NULL),(26,'2024-09-30 00:17:05.432000','2024-09-30 00:17:05.432000','2024-09-30 00:17:05.443000','Txxxx',60,99,40,'Testt',36,3,NULL,NULL,NULL),(27,'2024-09-30 00:31:58.342000','2024-09-30 00:31:58.342000','2024-09-30 08:25:27.969000','Tite Kubo',60,99000,23,'dhtlxx',39,3,NULL,NULL,NULL);
/*!40000 ALTER TABLE `book` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `book_category`
--

DROP TABLE IF EXISTS `book_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `book_category` (
  `book_id` int NOT NULL,
  `category_id` int NOT NULL,
  PRIMARY KEY (`book_id`,`category_id`),
  KEY `FKam8llderp40mvbbwceqpu6l2s` (`category_id`),
  CONSTRAINT `FKam8llderp40mvbbwceqpu6l2s` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`),
  CONSTRAINT `FKnyegcbpvce2mnmg26h0i856fd` FOREIGN KEY (`book_id`) REFERENCES `book` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `book_category`
--

LOCK TABLES `book_category` WRITE;
/*!40000 ALTER TABLE `book_category` DISABLE KEYS */;
INSERT INTO `book_category` VALUES (6,2),(8,2),(27,2),(26,4),(7,5);
/*!40000 ALTER TABLE `book_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `book_detail`
--

DROP TABLE IF EXISTS `book_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `book_detail` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `delete_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `cover_type` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `dimensions` varchar(255) DEFAULT NULL,
  `number_pages` int DEFAULT NULL,
  `publication_date` date NOT NULL,
  `publisher` varchar(255) DEFAULT NULL,
  `create_by` int DEFAULT NULL,
  `updated_by` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `book_detail`
--

LOCK TABLES `book_detail` WRITE;
/*!40000 ALTER TABLE `book_detail` DISABLE KEYS */;
INSERT INTO `book_detail` VALUES (9,'2024-07-21 00:20:27.512000','2024-07-21 00:20:27.512000','2024-07-21 00:20:27.512000','hardcover','And yet I will persist in believing-that there is such a thing as the harmony of completeness in humanity','20x30 cm',123,'2018-05-08','Pearson',NULL,NULL),(10,'2024-07-21 00:21:39.641000','2024-07-21 00:21:39.641000','2024-07-21 00:21:39.641000','hardcover','Ichigo Kurosaki has always been able to see ghosts, but this ability doesn’t change his life nearly as much as his close encounter with Rukia Kuchiki, a Soul Reaper and member of the mysterious Soul Society.','20x30 cm',30,'2018-05-08','Kim Đồng',NULL,NULL),(11,'2024-07-28 18:06:41.091000','2024-07-28 18:06:41.091000','2024-07-28 18:06:41.091000','plastic','Ichigo Kurosaki has always been able to see ghosts, but this ability doesn’t change his life nearly as much as his close encounter with Rukia Kuchiki, a Soul Reaper and member of the mysterious Soul Society.','20x30 cm',30,'2018-05-08','Kim Đồng',NULL,NULL),(36,'2024-09-30 00:17:05.424000','2024-09-30 00:17:05.424000','2024-09-30 00:17:05.424000','paperback','Ducimus culpa sint ','20x30 cm',416,'2024-09-18','Pearson',NULL,NULL),(39,'2024-09-30 00:31:58.340000','2024-09-30 00:31:58.340000','2024-09-30 00:31:58.340000','hardcover','Sint consequuntur no','20x30 cm',30,'2024-10-03','Kim Đồng',NULL,NULL);
/*!40000 ALTER TABLE `book_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `book_image`
--

DROP TABLE IF EXISTS `book_image`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `book_image` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `delete_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `image_path` tinytext NOT NULL,
  `book_id` int DEFAULT NULL,
  `create_by` int DEFAULT NULL,
  `updated_by` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKh020sno0rk91annvrd960urno` (`book_id`),
  CONSTRAINT `FKh020sno0rk91annvrd960urno` FOREIGN KEY (`book_id`) REFERENCES `book` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `book_image`
--

LOCK TABLES `book_image` WRITE;
/*!40000 ALTER TABLE `book_image` DISABLE KEYS */;
INSERT INTO `book_image` VALUES (13,'2024-07-21 00:20:27.517000','2024-07-21 00:20:27.517000','2024-07-21 00:20:27.517000','image_Nationalism.jpg',6,NULL,NULL),(14,'2024-07-21 00:21:39.644000','2024-07-21 00:21:39.644000','2024-07-21 00:21:39.644000','image_bleach.jpg',7,NULL,NULL),(15,'2024-07-28 18:06:41.118000','2024-07-28 18:06:41.118000','2024-07-28 18:06:41.118000','image_dragon-ball-vol-1.jpg',8,NULL,NULL),(16,'2024-07-28 18:06:41.121000','2024-07-28 18:06:41.121000','2024-07-28 18:06:41.121000','image_drangon_ball.jpg',8,NULL,NULL),(41,'2024-09-30 00:17:05.434000','2024-09-30 00:17:05.434000','2024-09-30 00:17:05.434000','image_othello.jpg',26,NULL,NULL),(42,'2024-09-30 00:27:08.358000','2024-09-30 00:27:08.358000','2024-09-30 00:27:08.358000','image_relativity.jpg',26,NULL,NULL),(44,'2024-09-30 00:31:58.344000','2024-09-30 00:31:58.344000','2024-09-30 00:31:58.344000','image_The_Sherlock_Holmes.jpg',27,NULL,NULL);
/*!40000 ALTER TABLE `book_image` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cart`
--

DROP TABLE IF EXISTS `cart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `create_by` int DEFAULT NULL,
  `delete_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `updated_by` int DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_9emlp6m95v5er2bcqkjsw48he` (`user_id`),
  CONSTRAINT `FKl70asp4l4w0jmbm1tqyofho4o` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart`
--

LOCK TABLES `cart` WRITE;
/*!40000 ALTER TABLE `cart` DISABLE KEYS */;
INSERT INTO `cart` VALUES (4,'2024-08-12 16:41:21.016000',NULL,'2024-08-12 16:41:21.016000','2024-08-12 16:41:21.016000',NULL,14);
/*!40000 ALTER TABLE `cart` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cart_item`
--

DROP TABLE IF EXISTS `cart_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart_item` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `create_by` int DEFAULT NULL,
  `delete_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `updated_by` int DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `book_id` int DEFAULT NULL,
  `cart_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKis5hg85qbs5d91etr4mvd4tx6` (`book_id`),
  KEY `FK1uobyhgl1wvgt1jpccia8xxs3` (`cart_id`),
  CONSTRAINT `FK1uobyhgl1wvgt1jpccia8xxs3` FOREIGN KEY (`cart_id`) REFERENCES `cart` (`id`),
  CONSTRAINT `FKis5hg85qbs5d91etr4mvd4tx6` FOREIGN KEY (`book_id`) REFERENCES `book` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart_item`
--

LOCK TABLES `cart_item` WRITE;
/*!40000 ALTER TABLE `cart_item` DISABLE KEYS */;
INSERT INTO `cart_item` VALUES (10,'2024-08-12 23:38:07.367000',NULL,'2024-08-12 23:38:07.367000','2024-10-01 23:50:59.538000',NULL,1,7,4),(13,'2024-10-01 19:16:18.965000',NULL,'2024-10-01 19:16:18.965000','2024-10-01 19:16:18.965000',NULL,1,26,4);
/*!40000 ALTER TABLE `cart_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `delete_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `category_name` varchar(255) DEFAULT NULL,
  `parent_id` int DEFAULT NULL,
  `create_by` int DEFAULT NULL,
  `updated_by` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,NULL,NULL,NULL,'Book',NULL,NULL,NULL),(2,NULL,NULL,NULL,'Fiction',1,NULL,NULL),(3,NULL,NULL,NULL,'Mystery',2,NULL,NULL),(4,NULL,NULL,NULL,'Science Fiction',2,NULL,NULL),(5,NULL,NULL,NULL,'Non-Fiction',1,NULL,NULL),(6,NULL,NULL,NULL,'Biographies',5,NULL,NULL),(7,NULL,NULL,NULL,'Self-Help',5,NULL,NULL);
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `group`
--

DROP TABLE IF EXISTS `group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `group` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `create_by` int DEFAULT NULL,
  `delete_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `updated_by` int DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `role_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_fhgv9i3bnf12838dk54ry3mtq` (`role_id`),
  CONSTRAINT `FKi9geljq2lw244ua8nmgmhv5tu` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `group`
--

LOCK TABLES `group` WRITE;
/*!40000 ALTER TABLE `group` DISABLE KEYS */;
INSERT INTO `group` VALUES (1,NULL,NULL,NULL,NULL,NULL,'Admin','Admin',4),(2,NULL,NULL,NULL,NULL,NULL,'Shop','Shop',5),(3,NULL,NULL,NULL,NULL,NULL,'User','User',6);
/*!40000 ALTER TABLE `group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `group_has_user`
--

DROP TABLE IF EXISTS `group_has_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `group_has_user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `create_by` int DEFAULT NULL,
  `delete_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `updated_by` int DEFAULT NULL,
  `group_id` int DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKrtr1p6blsu8sd541v7nyiqdcc` (`user_id`),
  KEY `FK117umjqhygkb3heesq5ut0w09` (`group_id`),
  CONSTRAINT `FK117umjqhygkb3heesq5ut0w09` FOREIGN KEY (`group_id`) REFERENCES `group` (`id`),
  CONSTRAINT `FKrtr1p6blsu8sd541v7nyiqdcc` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `group_has_user`
--

LOCK TABLES `group_has_user` WRITE;
/*!40000 ALTER TABLE `group_has_user` DISABLE KEYS */;
/*!40000 ALTER TABLE `group_has_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_item`
--

DROP TABLE IF EXISTS `order_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_item` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `create_by` int DEFAULT NULL,
  `delete_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `updated_by` int DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `book_id` int DEFAULT NULL,
  `order_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKb033an1f8qmpbnfl0a6jb5njs` (`book_id`),
  KEY `FKt4dc2r9nbvbujrljv3e23iibt` (`order_id`),
  CONSTRAINT `FKb033an1f8qmpbnfl0a6jb5njs` FOREIGN KEY (`book_id`) REFERENCES `book` (`id`),
  CONSTRAINT `FKt4dc2r9nbvbujrljv3e23iibt` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_item`
--

LOCK TABLES `order_item` WRITE;
/*!40000 ALTER TABLE `order_item` DISABLE KEYS */;
INSERT INTO `order_item` VALUES (1,'2024-10-02 08:36:08.092000',NULL,'2024-10-02 08:36:08.092000','2024-10-02 08:36:08.092000',NULL,1,26,6);
/*!40000 ALTER TABLE `order_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `delete_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `order_status` varchar(255) DEFAULT NULL,
  `shopping_address` varchar(255) DEFAULT NULL,
  `total_price` double NOT NULL,
  `shop_id` int DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  `create_by` int DEFAULT NULL,
  `updated_by` int DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `phone` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKqn03kko0738sehaal2gr2uxl6` (`shop_id`),
  KEY `FKel9kyl84ego2otj2accfd8mr7` (`user_id`),
  CONSTRAINT `FKel9kyl84ego2otj2accfd8mr7` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKqn03kko0738sehaal2gr2uxl6` FOREIGN KEY (`shop_id`) REFERENCES `shop` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (6,'2024-10-02 08:36:08.074000','2024-10-02 08:36:08.074000','2024-10-04 17:45:11.544000','Processing','1600 Fake Street',99,3,14,NULL,NULL,'hhh123','0123456789');
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `permission`
--

DROP TABLE IF EXISTS `permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `permission` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `create_by` int DEFAULT NULL,
  `delete_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `updated_by` int DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permission`
--

LOCK TABLES `permission` WRITE;
/*!40000 ALTER TABLE `permission` DISABLE KEYS */;
INSERT INTO `permission` VALUES (1,NULL,NULL,NULL,NULL,NULL,'Grants full access to all resources','Full access'),(2,NULL,NULL,NULL,NULL,NULL,'Allows viewing of resources','View'),(3,NULL,NULL,NULL,NULL,NULL,'Allows adding new resources','Add'),(4,NULL,NULL,NULL,NULL,NULL,'Allows updating existing resources','Update'),(5,NULL,NULL,NULL,NULL,NULL,'Allows deleting resources','Delete');
/*!40000 ALTER TABLE `permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rates`
--

DROP TABLE IF EXISTS `rates`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rates` (
  `book_id` int NOT NULL,
  `user_id` int NOT NULL,
  `rating` int NOT NULL,
  PRIMARY KEY (`book_id`,`user_id`),
  KEY `FKewuhni7bb944dnw9ntjiby1n` (`user_id`),
  CONSTRAINT `FKewuhni7bb944dnw9ntjiby1n` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKha6umd2wnbx636ryqtpoufgiu` FOREIGN KEY (`book_id`) REFERENCES `book` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rates`
--

LOCK TABLES `rates` WRITE;
/*!40000 ALTER TABLE `rates` DISABLE KEYS */;
/*!40000 ALTER TABLE `rates` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `review`
--

DROP TABLE IF EXISTS `review`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `review` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `delete_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `comment` varchar(255) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `book_id` int DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  `create_by` int DEFAULT NULL,
  `updated_by` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK70yrt09r4r54tcgkrwbeqenbs` (`book_id`),
  KEY `FKiyf57dy48lyiftdrf7y87rnxi` (`user_id`),
  CONSTRAINT `FK70yrt09r4r54tcgkrwbeqenbs` FOREIGN KEY (`book_id`) REFERENCES `book` (`id`),
  CONSTRAINT `FKiyf57dy48lyiftdrf7y87rnxi` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `review`
--

LOCK TABLES `review` WRITE;
/*!40000 ALTER TABLE `review` DISABLE KEYS */;
/*!40000 ALTER TABLE `review` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `delete_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `role_name` varchar(255) DEFAULT NULL,
  `create_by` int DEFAULT NULL,
  `updated_by` int DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (4,NULL,NULL,NULL,NULL,NULL,NULL,'Admin','ROLE_Admin'),(5,NULL,NULL,NULL,NULL,NULL,NULL,'Shop','ROLE_Shop'),(6,NULL,NULL,NULL,NULL,NULL,NULL,'User','ROLE_User');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role_has_permission`
--

DROP TABLE IF EXISTS `role_has_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role_has_permission` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `create_by` int DEFAULT NULL,
  `delete_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `updated_by` int DEFAULT NULL,
  `permission_id` int DEFAULT NULL,
  `role_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKc616yaiie179glys9ee1gwsod` (`role_id`),
  KEY `FK2h8xukv5c6o207f1iyj555146` (`permission_id`),
  CONSTRAINT `FK2h8xukv5c6o207f1iyj555146` FOREIGN KEY (`permission_id`) REFERENCES `permission` (`id`),
  CONSTRAINT `FKc616yaiie179glys9ee1gwsod` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_has_permission`
--

LOCK TABLES `role_has_permission` WRITE;
/*!40000 ALTER TABLE `role_has_permission` DISABLE KEYS */;
INSERT INTO `role_has_permission` VALUES (1,NULL,NULL,NULL,NULL,NULL,1,4),(2,NULL,NULL,NULL,NULL,NULL,2,5),(3,NULL,NULL,NULL,NULL,NULL,3,5),(4,NULL,NULL,NULL,NULL,NULL,4,5),(5,NULL,NULL,NULL,NULL,NULL,5,5),(6,NULL,NULL,NULL,NULL,NULL,2,6),(7,NULL,NULL,NULL,NULL,NULL,3,6),(8,NULL,NULL,NULL,NULL,NULL,4,6);
/*!40000 ALTER TABLE `role_has_permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shop`
--

DROP TABLE IF EXISTS `shop`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `shop` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `delete_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `contact_email` varchar(255) DEFAULT NULL,
  `contact_phone` varchar(255) DEFAULT NULL,
  `shop_address` varchar(255) DEFAULT NULL,
  `shop_logo` varchar(255) DEFAULT NULL,
  `shop_name` varchar(255) DEFAULT NULL,
  `shop_detail_id` int DEFAULT NULL,
  `create_by` int DEFAULT NULL,
  `updated_by` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_2i1bwugm56085yf5usxavhq7n` (`shop_detail_id`),
  CONSTRAINT `FK8agwksl7s1e21k86p6ks878t6` FOREIGN KEY (`shop_detail_id`) REFERENCES `shop_detail` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shop`
--

LOCK TABLES `shop` WRITE;
/*!40000 ALTER TABLE `shop` DISABLE KEYS */;
INSERT INTO `shop` VALUES (1,NULL,NULL,NULL,'contact@sample.com','123-456-7890','123 Sample Street','path/to/logo.jpg','Sample Shop',NULL,NULL,NULL),(3,'2024-05-24 01:45:34.329000','2024-05-24 01:45:34.329000','2024-05-24 01:45:34.329000','nhanam@gmail.edu.vn','0198876543','Ngõ 460 Đ. Khương Đình','image_1716496613529_fahasa.png','Fahasa',2,NULL,NULL);
/*!40000 ALTER TABLE `shop` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shop_detail`
--

DROP TABLE IF EXISTS `shop_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `shop_detail` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `delete_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `operating_hours` varchar(255) DEFAULT NULL,
  `return_policy` varchar(255) DEFAULT NULL,
  `shipping_policy` varchar(255) DEFAULT NULL,
  `create_by` int DEFAULT NULL,
  `updated_by` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shop_detail`
--

LOCK TABLES `shop_detail` WRITE;
/*!40000 ALTER TABLE `shop_detail` DISABLE KEYS */;
INSERT INTO `shop_detail` VALUES (2,'2024-05-24 01:45:34.311000','2024-05-24 01:45:34.311000','2024-05-24 01:45:34.311000','Nhã Nam, tên đầy đủ là Công ty Cổ phần Văn hóa và Truyền thông Nhã Nam, gia nhập thị trường sách Việt Nam vào tháng 2 năm 2005 với tác phẩm \"Balzac và cô bé thợ may Trung hoa\" của Đới Tư Kiệt','10am','none','none',NULL,NULL);
/*!40000 ALTER TABLE `shop_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `delete_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `user_name` varchar(30) DEFAULT NULL,
  `shop_id` int DEFAULT NULL,
  `create_by` int DEFAULT NULL,
  `updated_by` int DEFAULT NULL,
  `cart_id` int DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `date_of_birth` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ob8kqyqqgmefl0aco34akdtpe` (`email`),
  UNIQUE KEY `UK_lqjrcobrh9jc8wpcar64q1bfh` (`user_name`),
  UNIQUE KEY `UK_esehgck2vxe3t6t4qrby70y47` (`shop_id`),
  UNIQUE KEY `UK_47dq8urpj337d3o65l3fsjph3` (`cart_id`),
  CONSTRAINT `FK1j4n360b2mo5rw5mu1reki7jq` FOREIGN KEY (`cart_id`) REFERENCES `orders` (`id`),
  CONSTRAINT `FKophlo4m4uodyxvpnqf1a6d4vn` FOREIGN KEY (`shop_id`) REFERENCES `shop` (`id`),
  CONSTRAINT `FKtqa69bib34k2c0jhe7afqsao6` FOREIGN KEY (`cart_id`) REFERENCES `cart` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'2024-05-12 21:13:10.047000','2024-05-12 21:13:10.047000','2024-09-12 16:00:43.823000','dz@gmail.com','$2y$10$wQ.aUteVWBHtNVkkYq9vCuKGc5JxdSgeN21730IKeA8gL5KTx.y6K','0198876543','hieu123',3,NULL,NULL,NULL,'','2021-04-03 00:00:00.000000'),(9,'2024-06-16 17:08:11.497000','2024-06-16 17:08:11.497000','2024-06-16 17:08:11.497000','tesst@gmail.com','$2a$10$2JYEyIXhjdvGtCHsvZY0K.kGR/BTZcgu5XM3K5Q1XiMj0HL2Zfe1y','0198876543','tesst',NULL,NULL,NULL,NULL,NULL,NULL),(14,'2024-07-22 15:54:48.581000','2024-07-22 15:54:48.581000','2024-09-13 01:00:57.840000',NULL,'$2y$10$KGrfw2rHVN70P5KDqBtTXuJ2D9/sRIPYrNfZeNweKa91e.eypm1KW','0123456789','hhh123',NULL,NULL,NULL,NULL,'14_125px-flag_of_vietnam_svg.png','2022-09-11 00:00:00.000000');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_has_role`
--

DROP TABLE IF EXISTS `user_has_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_has_role` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `create_by` int DEFAULT NULL,
  `delete_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `updated_by` int DEFAULT NULL,
  `role_id` int DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKc1m07gjgx777ukpfw6wa94dfh` (`role_id`),
  KEY `FKdtkvc2iy3ph1rkvd67yl2t13m` (`user_id`),
  CONSTRAINT `FKc1m07gjgx777ukpfw6wa94dfh` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
  CONSTRAINT `FKdtkvc2iy3ph1rkvd67yl2t13m` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_has_role`
--

LOCK TABLES `user_has_role` WRITE;
/*!40000 ALTER TABLE `user_has_role` DISABLE KEYS */;
INSERT INTO `user_has_role` VALUES (1,NULL,NULL,NULL,NULL,NULL,4,1),(7,'2024-06-16 17:08:11.512000',NULL,'2024-06-16 17:08:11.512000','2024-06-16 17:08:11.512000',NULL,6,9),(12,'2024-07-22 15:54:48.582000',NULL,'2024-07-22 15:54:48.582000','2024-07-22 15:54:48.582000',NULL,6,14),(14,NULL,NULL,NULL,NULL,NULL,5,1);
/*!40000 ALTER TABLE `user_has_role` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-03-15 20:51:53
