CREATE DATABASE IF NOT EXISTS `spring_test`;
USE `spring_test`;

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
	`id` MEDIUMINT UNSIGNED UNIQUE AUTO_INCREMENT,
    `user_id` VARCHAR(20) UNIQUE NOT NULL,
    `password` VARCHAR(60) NOT NULL,
    `name` VARCHAR(20) NOT NULL,
    `email` VARCHAR(40) NOT NULL,
    `contact` CHAR(13) NOT NULL,
    
    PRIMARY KEY(`id`)
);