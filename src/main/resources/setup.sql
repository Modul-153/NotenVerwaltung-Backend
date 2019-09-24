BEGIN;

drop database if exists notenverwaltung;
create database notenverwaltung;
use notenverwaltung;

-- -----------------------------------------------------
-- Table `notenverwaltung`.`city`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `notenverwaltung`.`city`
(
    `city_id` INT         NOT NULL AUTO_INCREMENT,
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
    `subject_id` int not null,
    `name` varchar(50) not null,
    unique index `subject_id_UNIQUE` (`subject_id` ASC)
)
    ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `notenverwaltung`.`adress`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `notenverwaltung`.`adress`
(
    `adress_id` INT         NOT NULL AUTO_INCREMENT,
    `street`    VARCHAR(45) NULL,
    `number`    INT         NULL,
    `city_id`   INT         NOT NULL,
    PRIMARY KEY (`adress_id`),
    UNIQUE INDEX `adress_id_UNIQUE` (`adress_id` ASC),
    INDEX `city_id_idx` (`city_id` ASC),
    CONSTRAINT `fk_city_id_adress`
        FOREIGN KEY (`city_id`)
            REFERENCES `notenverwaltung`.`city` (`city_id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `notenverwaltung`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `notenverwaltung`.`user`
(
    `user_id`   INT         NOT NULL AUTO_INCREMENT,
    `firstname` VARCHAR(45) NOT NULL,
    `lastname`  VARCHAR(45) NOT NULL,
    `username`  VARCHAR(45) NOT NULL,
    `adress_id` INT         NOT NULL,
    PRIMARY KEY (`user_id`),
    UNIQUE INDEX `idPersonTable_UNIQUE` (`user_id` ASC),
    INDEX `adress_id_idx` (`adress_id` ASC),
    CONSTRAINT `fk_adress_id_user`
        FOREIGN KEY (`adress_id`)
            REFERENCES `notenverwaltung`.`adress` (`adress_id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `notenverwaltung`.`user_credential`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `notenverwaltung`.`user_credential`
(
    `user_id`  INT           NOT NULL,
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
    `school_id`  INT         NOT NULL,
    `schoolname` VARCHAR(45) NULL,
    `adress_id`  INT         NOT NULL,
    PRIMARY KEY (`school_id`),
    INDEX `adress_id_idx` (`adress_id` ASC),
    CONSTRAINT `fk_adress_id_school`
        FOREIGN KEY (`adress_id`)
            REFERENCES `notenverwaltung`.`adress` (`adress_id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `notenverwaltung`.`class`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `notenverwaltung`.`class`
(
    `class_id`  INT         NOT NULL AUTO_INCREMENT,
    `name`      VARCHAR(45) NOT NULL,
    `school_id` INT         NOT NULL,
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
    `exam_id` INT  NOT NULL AUTO_INCREMENT,
    `mark`    INT  NOT NULL,
    `date`    DATE NOT NULL,
    `user_id` INT  NOT NULL,
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
    `teacher_id` INT NOT NULL AUTO_INCREMENT,
    `user_id`    INT NOT NULL,
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
    `student_id` INT NOT NULL AUTO_INCREMENT,
    `user_id`    INT NOT NULL,
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
    `student_id`  INT NOT NULL,
    `class_id` INT NOT NULL,
    `from` date NOT NULL,
    `to` date NOT NULL,
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
    `tcs_id`  INT NOT NULL,
    `teacher_id` INT NOT NULL,
    `class_id` INT NOT NULL,
    `subject_id` INT NOT NULL,
    `from` date NOT NULL,
    `to` date NOT NULL,
    UNIQUE INDEX `tcs_id_UNIQUE` (`tcs_id` ASC),
    PRIMARY KEY (`tcs_id`),
    INDEX `tcs_id_idx` (`class_id` ASC),
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
    `primary_info_id`  INT NOT NULL,
    `teacher_id` INT NOT NULL,
    `class_id` INT NOT NULL,
    `from` date NOT NULL,
    `to` date NOT NULL,
    UNIQUE INDEX `primary_info_id_UNIQUE` (`primary_info_id` ASC),
    INDEX `primary_info_teacher_id_idx` (`teacher_id` ASC),
    INDEX `primary_info_class_id_idx` (`class_id` ASC),
    PRIMARY KEY (`primary_info_id`),
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