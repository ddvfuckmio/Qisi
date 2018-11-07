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
	`type` varchar(50) NOT NULL,
	`createdAt` datetime DEFAULT CURRENT_TIMESTAMP,
	 PRIMARY KEY (`id`),
	 UNIQUE KEY `courseId` (`courseId`),
   UNIQUE KEY `courseName` (`courseName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '课程表';

CREATE TABLE `course_chapters`(
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `courseId` varchar(50) NOT NULL,
  `chapterId` varchar(50) NOT NULL,
  `chapterName` varchar(50) NOT NULL,
  `chapterIndex` int(11) NOT NULL,
  `createdAt` datetime DEFAULT CURRENT_TIMESTAMP,
   PRIMARY KEY (`id`),
	 UNIQUE KEY `chapterId` (`chapterId`),
	 UNIQUE KEY `courseId_chapterIndex` (`courseId`,`chapterIndex`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '课程章节表';

CREATE TABLE `course_chapter_lessons` (
	`id` int(11) unsigned NOT NULL AUTO_INCREMENT,
	`chapterId` varchar(50) NOT NULL,
	`lessonId` varchar(50) NOT NULL,
	`lessonName` varchar(50) NOT NULL,
	`lessonIndex` int(11) unsigned NOT NULL,
	`introduction` varchar(255),
	`originCode` varchar(255),
	`createdAt` datetime DEFAULT CURRENT_TIMESTAMP,
	 PRIMARY KEY (`id`),
	 UNIQUE KEY `lessonId` (`lessonId`),
	 UNIQUE KEY `chapterId_lessonIndex` (`chapterId`,`lessonIndex`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '章节课程列表';

CREATE TABLE `course_chapter_lesson_tasks` (
	`id` int(11) unsigned NOT NULL AUTO_INCREMENT,
	`lessonId` varchar(50) NOT NULL,
	`taskId` varchar(50) NOT NULL,
	`taskIndex` int(11) NOT NULL,
	`introduction` text ,
	`firstCode`  text,
	`secondCode` text,
	`maxTime` int(11) unsigned NOT NULL,
	`maxMemory` int(11) unsigned NOT NULL,
	`createdAt` datetime DEFAULT CURRENT_TIMESTAMP,
   PRIMARY KEY (`id`),
   UNIQUE KEY `taskId` (`taskId`),
	 UNIQUE KEY `lessonId_taskIndex` (`lessonId`,`taskIndex`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '课程任务表';

CREATE TABLE `course_chapter_lesson_task_cases` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `taskId` varchar(50) NOT NULL,
  `caseId` varchar(50) NOT NULL,
  `input` varchar(255) NOT NULL,
  `output` varchar(255) NOT NULL,
  `createdAt` datetime DEFAULT CURRENT_TIMESTAMP,
   PRIMARY KEY (`id`),
   UNIQUE KEY `taskId` (`taskId`),
   UNIQUE KEY `caseId` (`caseId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '测试用例表';

CREATE TABLE `user_codes` (
	`id` int(11) unsigned NOT NULL AUTO_INCREMENT,
	`codeId` varchar(50) NOT NULL,
	`username` varchar(50) NOT NULL,
	`taskId` varchar(50) NOT NULL,
	`code` text NOT NULL,
	`pass` tinyint(1) NOT NULL DEFAULT 0 COMMENT '代码是否通过',
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
	`taskId` varchar(50) NOT NULL,
	`createdAt` datetime DEFAULT CURRENT_TIMESTAMP,
	 PRIMARY KEY (`id`),
	 UNIQUE KEY `username_courseId` (`username`,`courseId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '用户课程进度表';



