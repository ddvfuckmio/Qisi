CREATE TABLE `workers` (
	`id` int(11) unsigned NOT NULL AUTO_INCREMENT,
	`username` varchar(50) NOT NULL,
	`password` varchar(50) NOT NULL,
	`realName` varchar(50) NOT NULL,
	`age` int(11) DEFAULT NULL,
	`sex` varchar(10) DEFAULT NULL,
	`phone` varchar(50) DEFAULT NULL,
	`email` varchar(50) DEFAULT NULL,
	`department` varchar(10) DEFAULT NULL,
	`createdAt` datetime DEFAULT CURRENT_TIMESTAMP,
	 PRIMARY KEY (`id`),
	 UNIQUE KEY `username_UNIQUE` (`username`),
	 UNIQUE KEY `phoneNumber_UNIQUE` (`phone`),
	 UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '员工表';


CREATE TABLE `worker_checks` (
	`id` int(11) unsigned NOT NULL AUTO_INCREMENT,
	`username` varchar(50) NOT NULL,
	`signIn` datetime DEFAULT NULL,
	`signOut` datetime DEFAULT NULL,
	`checkDay` date DEFAULT NULL,
	 PRIMARY KEY (`id`),
	 UNIQUE KEY `username_checkDay_UNIQUE` (`username`,`checkDay`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '员工签到表';

CREATE TABLE `worker_dayOffs` (
	`id` int(11) unsigned NOT NULL AUTO_INCREMENT,
	`username` varchar(50) NOT NULL,
	`startDate` date DEFAULT NULL,
	`endDate` date DEFAULT NULL,
	`reason`  varchar(50) DEFAULT NULL,
	`state` int(11) DEFAULT 1,
	`createdAt` date DEFAULT NULL,
	 PRIMARY KEY (`id`),
	 UNIQUE KEY `username_startDate_UNIQUE` (`username`,`startDate`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '员工请假表';

CREATE TABLE `admin_users` (
	`id` int(11) unsigned NOT NULL AUTO_INCREMENT,
	`username` varchar(50) NOT NULL,
	`password` varchar(50) NOT NULL,
	`createdAt` datetime DEFAULT CURRENT_TIMESTAMP,
	 PRIMARY KEY (`id`),
	 UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '管理员表';

CREATE TABLE `worker_payrolls` (
	`id` int(11) unsigned NOT NULL AUTO_INCREMENT,
	`payrollDate` DATE DEFAULT NULL,
	`username` varchar(50) NOT NULL,
	`delayCount` int DEFAULT 0,
	`dayOffCount` int DEFAULT 0,
	`createdAt` datetime DEFAULT CURRENT_TIMESTAMP,
	 PRIMARY KEY (`id`),
	 UNIQUE KEY `payRollDate_username_UNIQUE` (`payrollDate`,`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '员工绩效表';




