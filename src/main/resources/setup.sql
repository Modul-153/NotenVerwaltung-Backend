BEGIN;

drop database if exists notenverwaltung;
create database notenverwaltung;
use notenverwaltung;

-- -----------------------------------------------------
-- Table `notenverwaltung`.`city`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `notenverwaltung`.`city`
(
    `city_id` SERIAL,
    `name`    VARCHAR(45) NOT NULL,
    `zipcode` INT         NOT NULL,
    PRIMARY KEY (`city_id`)
)
    ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `notenverwaltung`.`subject`
-- -----------------------------------------------------
create table if not exists `notenverwaltung`.`subject`
(
    `subject_id` SERIAL,
    `name`       varchar(50) not null unique,
    primary key (subject_id)
)
    ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `notenverwaltung`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `notenverwaltung`.`user`
(
    `user_id`   SERIAL,
    `firstname` VARCHAR(45)     NOT NULL,
    `lastname`  VARCHAR(45)     NOT NULL,
    `username`  VARCHAR(45)     NOT NULL unique,
    `number`    INT             NOT NULL,
    `street`    VARCHAR(45)     NOT NULL,
    `city_id`   BIGINT UNSIGNED NOT NULL,
    `password`  VARCHAR(1000)   NOT NULL,
    PRIMARY KEY (`user_id`),
    CONSTRAINT `fk_city_id_user`
        FOREIGN KEY (`city_id`)
            REFERENCES `notenverwaltung`.`city` (`city_id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `notenverwaltung`.`school`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `notenverwaltung`.`school`
(
    `school_id`  SERIAL,
    `schoolname` VARCHAR(45)     NOT NULL unique,
    `number`     INT             NOT NULL,
    `street`     VARCHAR(45)     NOT NULL,
    `city_id`    BIGINT UNSIGNED NOT NULL,
    PRIMARY KEY (`school_id`),
    CONSTRAINT `fk_city_id_school`
        FOREIGN KEY (`city_id`)
            REFERENCES `notenverwaltung`.`city` (`city_id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB;



-- -----------------------------------------------------
-- Table `notenverwaltung`.`teacher`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `notenverwaltung`.`teacher`
(
    `teacher_id` SERIAL,
    `user_id`    BIGINT UNSIGNED NOT NULL,
    PRIMARY KEY (`teacher_id`),
    INDEX `user_id_idx` (`user_id` ASC),
    CONSTRAINT `fk_user_id_teacher`
        FOREIGN KEY (`user_id`)
            REFERENCES `notenverwaltung`.`user` (`user_id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `notenverwaltung`.`class`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `notenverwaltung`.`class`
(
    `class_id`        SERIAL,
    `name`            VARCHAR(45)     NOT NULL UNIQUE,
    `school_id`       BIGINT UNSIGNED NOT NULL,
    `primary_teacher` BIGINT UNSIGNED NOT NULL,
    PRIMARY KEY (`class_id`),
    CONSTRAINT `fk_school_id_class`
        FOREIGN KEY (`school_id`)
            REFERENCES `notenverwaltung`.`school` (`school_id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `fk_class_primary_teacher`
        FOREIGN KEY (`primary_teacher`)
            REFERENCES `notenverwaltung`.`teacher` (`teacher_id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `notenverwaltung`.`student`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `notenverwaltung`.`student`
(
    `student_id` SERIAL,
    `user_id`    BIGINT UNSIGNED NOT NULL unique,
    PRIMARY KEY (`student_id`),
    CONSTRAINT `fk_user_id_student`
        FOREIGN KEY (`user_id`)
            REFERENCES `notenverwaltung`.`user` (`user_id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `notenverwaltung`.`class_member`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `notenverwaltung`.`class_member`
(
    `student_id` BIGINT UNSIGNED NOT NULL,
    `class_id`   BIGINT UNSIGNED NOT NULL,
    INDEX `class_id_idx` (`class_id` ASC),
    CONSTRAINT `fk_student_id_class_member`
        FOREIGN KEY (`student_id`)
            REFERENCES `notenverwaltung`.`student` (`student_id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `fk_class_id_class_member`
        FOREIGN KEY (`class_id`)
            REFERENCES `notenverwaltung`.`class` (`class_id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `notenverwaltung`.`teacher_class_subject`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `notenverwaltung`.`teacher_class_subject`
(
    tcs_id       serial,
    `teacher_id` BIGINT UNSIGNED NOT NULL,
    `class_id`   BIGINT UNSIGNED NOT NULL,
    `subject_id` BIGINT UNSIGNED NOT NULL,
    primary key (tcs_id),
    constraint u_tcs unique (teacher_id, class_id, subject_id),
    CONSTRAINT `fk_tcs_teacher_id`
        FOREIGN KEY (`teacher_id`)
            REFERENCES `notenverwaltung`.`teacher` (`teacher_id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `fk_tcs_class_id`
        FOREIGN KEY (`class_id`)
            REFERENCES `notenverwaltung`.`class` (`class_id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `fk_tcs_subject_id`
        FOREIGN KEY (`subject_id`)
            REFERENCES `notenverwaltung`.`subject` (`subject_id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `notenverwaltung`.`exam`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `notenverwaltung`.`exam`
(
    `exam_id` SERIAL,
    `date`    DATE            NOT NULL,
    `tcs_id`  BIGINT UNSIGNED NOT NULL,
    PRIMARY KEY (`exam_id`),
    CONSTRAINT `fk_exam_tcs_id`
        FOREIGN KEY (`tcs_id`)
            REFERENCES `teacher_class_subject` (`tcs_id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB;


create table `notenverwaltung`.`exam_result`
(
    exam_id    bigint unsigned        not null,
    student_id bigint unsigned        not null,
    mark       decimal(3, 2) unsigned not null,
    constraint u_exam_student unique (exam_id, student_id),
    constraint fk_exam_result_exam foreign key (exam_id) references exam (exam_id),
    constraint fk_exam_result_student foreign key (student_id) references student (student_id)
);

COMMIT;