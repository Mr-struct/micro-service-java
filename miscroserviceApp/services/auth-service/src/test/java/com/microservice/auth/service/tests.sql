

--
-- Table structure for table `app_user`
--

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
