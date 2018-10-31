CREATE TABLE `users` (
	`id` int(11) unsigned NOT NULL AUTO_INCREMENT,
	`username` varchar(50) NOT NULL,
	`password` varchar(50) NOT NULL,
	`age` varchar(50) DEFAULT NULL,
	`sex` varchar(10) DEFAULT NULL,
	`job` varchar(50) DEFAULT NULL,
	`phone` varchar(50) DEFAULT NULL,
	`email` varchar(50) DEFAULT NULL,
	`role` varchar(10) DEFAULT NULL,
	`createdAt` datetime DEFAULT CURRENT_TIMESTAMP,
	 PRIMARY KEY (`id`),
	 UNIQUE KEY `username_UNIQUE` (`username`),
	 UNIQUE KEY `phoneNumber_UNIQUE` (`phone`),
	 UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '用户表';

CREATE TABLE `courses` (
	`id` int(11) unsigned NOT NULL AUTO_INCREMENT,
	`courseId` varchar(50) NOT NULL,
	`courseName` varchar(50) NOT NULL,
	`introduction` varchar(100) DEFAULT NULL,
	`totalLessons` int(11) unsigned DEFAULT 0,
	`totalExercises` int(11) unsigned DEFAULT 0,
	`createdAt` datetime DEFAULT CURRENT_TIMESTAMP,
	 PRIMARY KEY (`id`),
	 UNIQUE KEY `courseId` (`courseId`),
   UNIQUE KEY `courseName` (`courseName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '课程表';

CREATE TABLE `course_lessons` (
	`id` int(11) unsigned NOT NULL AUTO_INCREMENT,
	`courseId` varchar(50) NOT NULL,
	`lessonId` varchar(50) NOT NULL,
	`lessonName` varchar(50) NOT NULL,
	`lessonIndex` int(11) unsigned NOT NULL,
	`comment` varchar(255),
	`originCode` varchar(255),
	`totalExercises` int(11) unsigned DEFAULT 0,
	`createdAt` datetime DEFAULT CURRENT_TIMESTAMP,
	 PRIMARY KEY (`id`),
	 UNIQUE KEY `lessonId` (`lessonId`),
	 UNIQUE KEY `courseId_lessonId_lessonIndex` (`courseId`,`lessonId`,`lessonIndex`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '课程列表';

CREATE TABLE `course_lesson_exercises` (
	`id` int(11) unsigned NOT NULL AUTO_INCREMENT,
	`courseId` varchar(50) NOT NULL,
	`lessonId` varchar(50) NOT NULL,
	`exerciseId` varchar(50) NOT NULL,
	`exerciseIndex` int(11) NOT NULL,
	`paper` paper ,
	`firstCode`  paper,
	`secondCode` paper,
	`createdAt` datetime DEFAULT CURRENT_TIMESTAMP,
   PRIMARY KEY (`id`),
   UNIQUE KEY `exerciseId` (`exerciseId`),
	 UNIQUE KEY `courseId_lessonId_exerciseIndex` (`courseId`,`lessonId`,`exerciseIndex`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '章节练习表';

CREATE TABLE `user_codes` (
	`id` int(11) unsigned NOT NULL AUTO_INCREMENT,
	`codeId` varchar(50) NOT NULL,
	`username` varchar(50) NOT NULL,
	`exerciseId` varchar(50) NOT NULL,
	`code` text NOT NULL,
	`pass` tinyint(1) NOT NULL DEFAULT '0' COMMENT '代码是否通过',
	`createdAt` datetime DEFAULT CURRENT_TIMESTAMP,
	 PRIMARY KEY (`id`),
	 KEY `username` (`username`),
	 UNIQUE KEY `codeId` (`codeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '用户代码表';




CREATE TABLE `user_course_progress` (
	`id` int(11) unsigned NOT NULL AUTO_INCREMENT,
	`username` varchar(50) NOT NULL,
	`courseId` varchar(50) NOT NULL,
	`lessonId` varchar(50) NOT NULL,
	`exerciseIndex` int(11) NOT NULL,
	`createdAt` datetime DEFAULT CURRENT_TIMESTAMP,
	 PRIMARY KEY (`id`),
	 UNIQUE KEY `account_courseName` (`username`,`courseId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '用户课程进度表';


CREATE TABLE `user_roles` (
	`id` int(11) unsigned NOT NULL AUTO_INCREMENT,
	`username` varchar(50) NOT NULL,
  `role` varchar(10) DEFAULT NULL,
	`createdAt` datetime DEFAULT CURRENT_TIMESTAMP,
	 PRIMARY KEY (`id`),
	 UNIQUE KEY `account_UNIQUE` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '用户权限表';



