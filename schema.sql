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

DROP TABLE IF EXISTS `product`;
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
	`id` SMALLINT UNSIGNED UNIQUE,
    `name` VARCHAR(20) NOT NULL,

    PRIMARY KEY(`id`)
);

DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
	`id` MEDIUMINT UNSIGNED UNIQUE AUTO_INCREMENT,
    `category_id` SMALLINT UNSIGNED DEFAULT 0 NOT NULL,
    `name` VARCHAR(30) NOT NULL,
    `desc` TEXT NOT NULL,
    `summary` VARCHAR(255) NOT NULL,
    `price` INT NOT NULL,

    PRIMARY KEY(`id`),
    FOREIGN KEY(`category_id`) REFERENCES `category`(`id`)
);