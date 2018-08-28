CREATE DATABASE IF NOT EXISTS `dojo`;
USE `dojo`;
CREATE TABLE IF NOT EXISTS `users` (
  `user_id` int(11) NOT NULL,
  `balance_brl` decimal(10,2) NOT NULL default 0.0,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
