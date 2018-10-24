CREATE TABLE `users` (
	`id` int(11) unsigned NOT NULL AUTO_INCREMENT,
	`account` varchar(50) NOT NULL,
	`password` varchar(50) NOT NULL,
	`sex` varchar(10) DEFAULT NULL,
	`phone` varchar(50) DEFAULT NULL,
	`email` varchar(50) DEFAULT NULL,
	`createdAt` datetime DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (`id`),
	UNIQUE KEY `account_UNIQUE` (`account`),
	UNIQUE KEY `phoneNumber_UNIQUE` (`phone`),
	UNIQUE KEY `email_UNIQUE` (`email`)
)
