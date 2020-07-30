
DROP TABLE IF EXISTS `hostel_rooms`;
DROP TABLE IF EXISTS `reservation`;
DROP TABLE IF EXISTS `hostel`;
DROP TABLE IF EXISTS `room`;

DROP TABLE IF EXISTS `app_user`;

CREATE TABLE `app_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `password` varchar(255) NOT NULL,
  `role` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2;

--
-- Dumping data for table `app_user`
--

LOCK TABLES `app_user` WRITE;
INSERT INTO `app_user` VALUES (1,'$2a$10$lDgYGBdDuE7s2ohD0mLGr.dlQ4QZ44fVKd3wL//MIxZWa.a8PNSUe','admin','user');
UNLOCK TABLES;

--
-- Table structure for table `hostel`
--

 SET character_set_client = utf8mb4 ;
CREATE TABLE `hostel` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `address` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `phone_number` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2;

--
-- Table structure for table `hostel_rooms`
--

 SET character_set_client = utf8mb4 ;
CREATE TABLE `hostel_rooms` (
  `hostel_id` bigint(20) NOT NULL,
  `rooms_id` bigint(20) NOT NULL,
  UNIQUE KEY `UK_mjm40gdftj0s77jdjaenldben` (`rooms_id`),
  KEY `FKg23mtbme652l0c02rsnmc4ih` (`hostel_id`)
) ENGINE=MyISAM;

--
-- Table structure for table `reservation`
--

 SET character_set_client = utf8mb4 ;
CREATE TABLE `reservation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `reservation_date` datetime DEFAULT NULL,
  `hostel_id` bigint(20) DEFAULT NULL,
  `room_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKklo8g7s03mjy1xbcrahnkse4w` (`hostel_id`),
  KEY `FKm8xumi0g23038cw32oiva2ymw` (`room_id`)
) ENGINE=MyISAM;


--
-- Table structure for table `room`
--

 SET character_set_client = utf8mb4 ;
CREATE TABLE `room` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `capacity` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `price` float NOT NULL,
  `room_category` varchar(255) NOT NULL,
  `taken` bit(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2;


--
-- Dumping data for table `room`
--

LOCK TABLES `room` WRITE;
INSERT INTO `room` VALUES (22, 2,'TestRoom',50,'SR', 0);
INSERT INTO `room` VALUES (24, 2,'Room41',50,'SR', 0);
UNLOCK TABLES;

--
-- Dumping data for table `hostel`
--

LOCK TABLES `hostel` WRITE;
INSERT INTO `hostel` VALUES (22,'Rue de la mairie','TestHotel','0611111111');
INSERT INTO `hostel` VALUES (24,'Rue de la tente','Hotel45','0611111111');
UNLOCK TABLES;

--
-- Dumping data for table `hostel_rooms`
--

LOCK TABLES `hostel_rooms` WRITE;
INSERT INTO `hostel_rooms` VALUES (24, 24);
UNLOCK TABLES;
