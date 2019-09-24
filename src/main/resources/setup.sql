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
    PRIMARY KEY (`city_id`),
    UNIQUE INDEX `city_id_UNIQUE` (`city_id` ASC)
)
    ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `notenverwaltung`.`subject`
-- -----------------------------------------------------
create table if not exists `notenverwaltung`.`subject`
(
    `subject_id` SERIAL,
    `name`       varchar(50) not null,
    unique index `subject_id_UNIQUE` (`subject_id` ASC)
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
    `username`  VARCHAR(45)     NOT NULL,
    `number`    INT             NOT NULL,
    `street`    VARCHAR(45)     NOT NULL,
    `city_id`   BIGINT UNSIGNED NOT NULL,
    PRIMARY KEY (`user_id`),
    UNIQUE INDEX `idPersonTable_UNIQUE` (`user_id` ASC),
    INDEX `adress_id_idx` (`city_id` ASC),
    CONSTRAINT `fk_city_id_user`
        FOREIGN KEY (`city_id`)
            REFERENCES `notenverwaltung`.`city` (`city_id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `notenverwaltung`.`user_credential`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `notenverwaltung`.`user_credential`
(
    `user_id`  SERIAL,
    `password` VARCHAR(1000) NOT NULL,
    INDEX `user_id_idx` (`user_id` ASC),
    CONSTRAINT `fk_user_id_user_credential`
        FOREIGN KEY (`user_id`)
            REFERENCES `notenverwaltung`.`user` (`user_id`)
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
    `schoolname` VARCHAR(45)     NOT NULL,
    `number`     INT             NOT NULL,
    `street`     VARCHAR(45)     NOT NULL,
    `city_id`    BIGINT UNSIGNED NOT NULL,
    PRIMARY KEY (`school_id`),
    INDEX `city_id_idx` (`city_id` ASC),
    CONSTRAINT `fk_city_id_school`
        FOREIGN KEY (`city_id`)
            REFERENCES `notenverwaltung`.`city` (`city_id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `notenverwaltung`.`class`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `notenverwaltung`.`class`
(
    `class_id`  SERIAL,
    `name`      VARCHAR(45)     NOT NULL,
    `school_id` BIGINT UNSIGNED NOT NULL,
    PRIMARY KEY (`class_id`),
    INDEX `school_id_idx` (`school_id` ASC),
    CONSTRAINT `fk_school_id_class`
        FOREIGN KEY (`school_id`)
            REFERENCES `notenverwaltung`.`school` (`school_id`)
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
    `mark`    INT             NOT NULL,
    `date`    DATE            NOT NULL,
    `user_id` BIGINT UNSIGNED NOT NULL,
    PRIMARY KEY (`exam_id`),
    UNIQUE INDEX `exam_id_UNIQUE` (`exam_id` ASC),
    INDEX `user_id_idx` (`user_id` ASC),
    CONSTRAINT `fk_user_id_exam`
        FOREIGN KEY (`user_id`)
            REFERENCES `notenverwaltung`.`user` (`user_id`)
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
    UNIQUE INDEX `teacher_id_UNIQUE` (`teacher_id` ASC),
    INDEX `user_id_idx` (`user_id` ASC),
    CONSTRAINT `fk_user_id_teacher`
        FOREIGN KEY (`user_id`)
            REFERENCES `notenverwaltung`.`user` (`user_id`)
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
    `user_id`    BIGINT UNSIGNED NOT NULL,
    PRIMARY KEY (`student_id`),
    UNIQUE INDEX `student_id_UNIQUE` (`student_id` ASC),
    UNIQUE INDEX `user_id_UNIQUE` (`user_id` ASC),
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
    `from`       date NOT NULL,
    `to`         date NOT NULL,
    UNIQUE INDEX `user_id_UNIQUE` (`student_id` ASC),
    INDEX `class_id_idx` (`class_id` ASC),
    CONSTRAINT `fk_teacher_id_class_member`
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
    `teacher_id` BIGINT UNSIGNED NOT NULL,
    `class_id`   BIGINT UNSIGNED NOT NULL,
    `subject_id` BIGINT UNSIGNED NOT NULL,
    `from`       date NOT NULL,
    `to`         date NOT NULL,
    INDEX `tcs_class_id_idx` (`class_id` ASC),
    INDEX `tcs_teacher_id_idx` (`teacher_id` ASC),
    INDEX `tcs_subject_id_idx` (`subject_id` ASC),
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
-- Table `notenverwaltung`.`primary_info`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `notenverwaltung`.`primary_info`
(
    `teacher_id`      BIGINT UNSIGNED NOT NULL,
    `class_id`        BIGINT UNSIGNED NOT NULL,
    `from`            date NOT NULL,
    `to`              date NOT NULL,
    INDEX `primary_info_teacher_id_idx` (`teacher_id` ASC),
    INDEX `primary_info_class_id_idx` (`class_id` ASC),
    CONSTRAINT `fk_primary_info_teacher_id`
        FOREIGN KEY (`teacher_id`)
            REFERENCES `notenverwaltung`.`teacher` (`teacher_id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `fk_primary_info_class_id`
        FOREIGN KEY (`class_id`)
            REFERENCES `notenverwaltung`.`class` (`class_id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB;

COMMIT;