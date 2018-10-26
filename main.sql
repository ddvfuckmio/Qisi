CREATE TABLE `users` (
	`uid` int(11) unsigned NOT NULL AUTO_INCREMENT,
	`account` varchar(50) NOT NULL,
	`password` varchar(50) NOT NULL,
	`age` varchar(50) NOT NULL,
	`sex` varchar(10) DEFAULT NULL,
	`job` varchar(50) DEFAULT NULL,
	`phone` varchar(50) DEFAULT NULL,
	`email` varchar(50) DEFAULT NULL,
	`createdAt` datetime DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (`uid`),
	UNIQUE KEY `account_UNIQUE` (`account`),
	UNIQUE KEY `phoneNumber_UNIQUE` (`phone`),
	UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '用户表';

CREATE TABLE `admin_users` (
	`uid` int(11) unsigned NOT NULL AUTO_INCREMENT,
	`account` varchar(50) NOT NULL,
	`password` varchar(50) NOT NULL,
	`phone` varchar(50) DEFAULT NULL,
	`createdAt` datetime DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (`uid`),
	UNIQUE KEY `account_UNIQUE` (`account`),
	UNIQUE KEY `phoneNumber_UNIQUE` (`phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '管理员表';

CREATE TABLE `courses` (
	`id` int(11) unsigned NOT NULL AUTO_INCREMENT,
	`courseName` varchar(50) NOT NULL,
	`introduction` varchar(100) DEFAULT NULL,
	`totalExercises` int(11) unsigned DEFAULT 0,
	`createdAt` datetime DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (`id`),
	UNIQUE KEY `courseName` (`courseName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '课程表';

CREATE TABLE `course_lessons` (
	`id` int(11) unsigned NOT NULL AUTO_INCREMENT,
	`courseName` varchar(50) NOT NULL,
	`lessonName` varchar(50) NOT NULL,
	`lessonIndex` int(11) unsigned NOT NULL,
	`comment` text,
	`originCode` text,
	`totalExercises` int(11) unsigned DEFAULT 0,
	`createdAt` datetime DEFAULT CURRENT_TIMESTAMP,
	 PRIMARY KEY (`id`),
	 UNIQUE KEY `courseName_lessonName_lessonIndex` (`courseName`,`lessonName`,`lessonIndex`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '课程列表';

CREATE TABLE `course_lesson_exercises` (
	`id` int(11) unsigned NOT NULL AUTO_INCREMENT,
	`courseName` varchar(50) NOT NULL,
	`lessonName` varchar(50) NOT NULL,
	`exerciseIndex` int(11) NOT NULL,
	`paper` text ,
	`firstCode`  text,
	`secondCode` text,
	`createdAt` datetime DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (`id`),
	UNIQUE KEY `courseName_lessonName_exerciseIndex` (`courseName`,`lessonName`,`exerciseIndex`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '章节练习表';

CREATE TABLE `user_course_progress` (
	`id` int(11) unsigned NOT NULL AUTO_INCREMENT,
	`account` varchar(50) NOT NULL,
	`courseName` varchar(50) NOT NULL,
	`exerciseIndex` int(11) NOT NULL,
	`createdAt` datetime DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (`id`),
	UNIQUE KEY `account_courseName` (`account`,`courseName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '用户课程进度表';

CREATE TABLE `user_codes` (
	`id` int(11) unsigned NOT NULL AUTO_INCREMENT,
	`codeId` varchar(50) NOT NULL,
	`account` varchar(50) NOT NULL,
	`courseName` varchar(50) NOT NULL,
	`lessonName` varchar(50) NOT NULL,
	`exerciseIndex` int(11) NOT NULL,
	`code` text NOT NULL,
	`pass` varchar(10) DEFAULT NULL,
	`createdAt` datetime DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (`id`),
	 KEY `account` (`account`),
	UNIQUE KEY `codeId` (`codeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '用户代码表';

