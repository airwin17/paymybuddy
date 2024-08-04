CREATE DATABASE IF NOT EXISTS `paymybuddydb`;
USE `paymybuddydb`;
DROP TABLE IF EXISTS `bank_account`;
CREATE TABLE `bank_account` (
  `id` int NOT NULL AUTO_INCREMENT,
  `balance` double NOT NULL,
  `iban` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `iban_UNIQUE` (`id`)
);

DROP TABLE IF EXISTS `connections`;
CREATE TABLE `connections` (
  `id` int NOT NULL AUTO_INCREMENT,
  `userID1` int NOT NULL,
  `userID2` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user1fk_idx` (`userID1`),
  KEY `user2fk_idx` (`userID2`),
  CONSTRAINT `user1fk` FOREIGN KEY (`userID1`) REFERENCES `user` (`id`),
  CONSTRAINT `user2fk` FOREIGN KEY (`userID2`) REFERENCES `user` (`id`)
);

DROP TABLE IF EXISTS `transaction`;
CREATE TABLE `transaction` (
  `id` int NOT NULL AUTO_INCREMENT,
  `sender` int DEFAULT NULL,
  `receiver` int DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  `amount` double NOT NULL,
  PRIMARY KEY (`id`),
  KEY `senderfk_idx` (`sender`),
  KEY `receiverfk_idx` (`receiver`),
  CONSTRAINT `receiverfk` FOREIGN KEY (`receiver`) REFERENCES `user` (`id`),
  CONSTRAINT `senderfk` FOREIGN KEY (`sender`) REFERENCES `user` (`id`)
);
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `password` varchar(100) DEFAULT NULL,
  `bank_acount_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `bafk_idx` (`bank_acount_id`),
  CONSTRAINT `bafk` FOREIGN KEY (`bank_acount_id`) REFERENCES `bank_account` (`id`)
);