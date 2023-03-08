DROP TABLE IF EXISTS remote_platform,edition_module,course_module,enrollment,competence,skill,category,lesson,
					student,teacher,person,address,course_edition,classroom,course;

DROP TYPE IF EXISTS level,sex;

CREATE TYPE sex					AS ENUM ('MALE','FEMALE','UNDECIDED');	--crea un enum
CREATE TYPE level				AS ENUM ('BASE','INTERMEDIATE','ADVANCED','GURU');	--crea un enum
--CREATE INDEX categoryname ON category(categoryname);

CREATE TABLE remote_platform
(
	id_remote_platform 		BIGINT NOT NULL,
	name					VARCHAR(32) NOT NULL,
	annual_cost				DECIMAL NOT NULL,
	adoptionDate			DATE,
	CONSTRAINT PK_remote_platform PRIMARY KEY(id_remote_platform)

);

CREATE SEQUENCE remote_platform_sequence
  start 1
  increment 1
  OWNED BY remote_platform.id_remote_platform;

--create table course
CREATE TABLE course
(
  	id_course			BIGINT NOT NULL,
  	title				VARCHAR(32) NOT NULL,
  	description			VARCHAR(100) NOT NULL,
	program  			VARCHAR(500) ,
  	duration			DECIMAL NOT NULL,
  	is_active       	BOOLEAN NOT NULL,
  	created_at      	DATE NOT NULL DEFAULT NOW(),
  	CONSTRAINT PK_course PRIMARY KEY(id_course),
	CONSTRAINT CHK_duration CHECK(duration >= 0.25)
);

CREATE SEQUENCE course_sequence
  start 1
  increment 1
  OWNED BY course.id_course;

--create table classroom
CREATE TABLE classroom
(
  	id_classroom			BIGINT NOT NULL,
  	class_name				VARCHAR(32) NOT NULL,
  	max_capacity			INT NOT NULL,
  	is_virtual       		BOOLEAN NOT NULL,
  	is_computerized			BOOLEAN NOT NULL,
	has_projector       	BOOLEAN NOT NULL,
	id_remote_platform		BIGINT,
  	CONSTRAINT PK_classroom PRIMARY KEY(id_classroom),
	CONSTRAINT FK_classroom_remote_platform FOREIGN KEY(id_remote_platform)
		REFERENCES remote_platform(id_remote_platform),
	CONSTRAINT CHK_max_capacity CHECK(max_capacity >= 1)
);

CREATE SEQUENCE classroom_sequence
  start 1
  increment 1
  OWNED BY classroom.id_classroom;

--create table courseEditions
CREATE TABLE course_edition
(
  	id_course_edition	BIGINT NOT NULL,
  	id_course			BIGINT NOT NULL,
  	started_at			DATE,
  	price				DECIMAL NOT NULL,
  	id_classroom		BIGINT,
  	CONSTRAINT PK_course_edition PRIMARY KEY(id_course_edition),
	CONSTRAINT FK_course_edition_course FOREIGN KEY(id_course)
		REFERENCES course(id_course),
	CONSTRAINT FK_course_edition_classroom FOREIGN KEY(id_classroom)
		REFERENCES classroom(id_classroom),
	CONSTRAINT CHK_price CHECK(price >= 0)
);

CREATE SEQUENCE course_edition_sequence
  start 1
  increment 1
  OWNED BY course_edition.id_course_edition;

--create table address
CREATE TABLE address
(
  	id_address			BIGINT NOT NULL,
  	street				VARCHAR(32) NOT NULL,
	house_number		INT NOT NULL,
	city				VARCHAR(50) NOT NULL,
	country				VARCHAR(50) NOT NULL,
  	CONSTRAINT PK_address PRIMARY KEY(id_address),
	CONSTRAINT CHK_house_number CHECK(house_number > 0)
);

CREATE SEQUENCE address_sequence
  start 1
  increment 1
  OWNED BY address.id_address;

--create table person
CREATE TABLE person
(
  	id_person			BIGINT NOT NULL,
  	firstname			VARCHAR(32) NOT NULL,
  	lastname			VARCHAR(32) NOT NULL,
	dob					DATE NOT NULL,
	sex					sex NOT NULL,
	email				VARCHAR(50) NOT NULL,
	cell_number			VARCHAR(20),
	id_address			BIGINT NOT NULL,
	username			VARCHAR(32) NOT NULL,
	password			VARCHAR(32) NOT NULL,
  	CONSTRAINT PK_person PRIMARY KEY(id_person),
	CONSTRAINT FK_person_address FOREIGN KEY(id_address)
		REFERENCES address(id_address)
);

CREATE SEQUENCE person_sequence
  start 1
  increment 1
  OWNED BY person.id_person;

--create table teacher
CREATE TABLE teacher
(
  	id_teacher			BIGINT NOT NULL,
	p_IVA				CHAR(11),
	is_employee       	BOOLEAN NOT NULL,
	hire_date			DATE,
	fire_date			DATE,
	level				level NOT NULL,
	id_person			BIGINT NOT NULL,
  	CONSTRAINT PK_teacher PRIMARY KEY(id_teacher),
	CONSTRAINT FK_teacher_person FOREIGN KEY(id_person)
		REFERENCES person(id_person)
);

CREATE SEQUENCE teacher_sequence
  start 1
  increment 1
  OWNED BY teacher.id_teacher;

--create table student
CREATE TABLE student
(
  	id_student			BIGINT NOT NULL,
	last_contact		DATE,
	date_of_reg			DATE NOT NULL,
	id_person			BIGINT NOT NULL,
  	CONSTRAINT PK_student PRIMARY KEY(id_student),
	CONSTRAINT FK_student_person FOREIGN KEY(id_person)
		REFERENCES person(id_person)
);

CREATE SEQUENCE student_sequence
  start 1
  increment 1
  OWNED BY student.id_student;

--create table lesson
CREATE TABLE lesson
(
  	id_lesson			BIGINT NOT NULL,
  	title				VARCHAR(32) NOT NULL,
  	start_date			DATE NOT NULL,
	end_date			DATE NOT NULL,
	lesson_comment		VARCHAR(100),
	id_classroom		BIGINT NOT NULL,
	id_teacher			BIGINT NOT NULL,
  	CONSTRAINT PK_lesson PRIMARY KEY(id_lesson),
	CONSTRAINT FK_lesson_classroom FOREIGN KEY(id_classroom)
		REFERENCES classroom(id_classroom),
	CONSTRAINT FK_lesson_teacher FOREIGN KEY(id_teacher)
		REFERENCES teacher(id_teacher)
);

CREATE SEQUENCE lesson_sequence
  start 1
  increment 1
  OWNED BY lesson.id_lesson;

--create table category
CREATE TABLE category
(
  	id_category		BIGINT NOT NULL,
	name			VARCHAR(32) NOT NULL,
	description		VARCHAR(100),
  	CONSTRAINT PK_category PRIMARY KEY(id_category)
);

CREATE SEQUENCE category_sequence
  start 1
  increment 1
  OWNED BY category.id_category;

--create table skill
CREATE TABLE skill
(
  	id_skill		BIGINT NOT NULL,
	name			VARCHAR(32) NOT NULL,
	description		VARCHAR(100),
	id_category		BIGINT NOT NULL,
  	CONSTRAINT PK_skill PRIMARY KEY(id_skill),
	CONSTRAINT FK_skill_category FOREIGN KEY(id_category)
		REFERENCES category(id_category)
);

CREATE SEQUENCE skill_sequence
  start 1
  increment 1
  OWNED BY skill.id_skill;

--create table competence
CREATE TABLE competence
(
  	id_competence		BIGINT NOT NULL,
	id_person			BIGINT NOT NULL,
	id_skill			BIGINT NOT NULL,
	level				level NOT NULL,
  	CONSTRAINT PK_competence PRIMARY KEY(id_competence),
	CONSTRAINT FK_competence_person FOREIGN KEY(id_person)
		REFERENCES person(id_person),
	CONSTRAINT FK_competence_skill FOREIGN KEY(id_skill)
		REFERENCES skill(id_skill)
);

CREATE SEQUENCE competence_sequence
  start 1
  increment 1
  OWNED BY competence.id_competence;

--create table enrollment
CREATE TABLE enrollment
(
  	id_enrollment			BIGINT NOT NULL,
	start_date				DATE NOT NULL DEFAULT NOW(),
	dropout_date			DATE,
	id_student				BIGINT NOT NULL,
	id_course_edition		BIGINT NOT NULL,
	course_evaluation		VARCHAR(100),
	course_vote				DECIMAL,
	student_evaluation		VARCHAR(100),
	student_vote			DECIMAL,
	has_paid       			BOOLEAN NOT NULL,
  	CONSTRAINT PK_enrollment PRIMARY KEY(id_enrollment),
	CONSTRAINT FK_enrollment_student FOREIGN KEY(id_student)
		REFERENCES student(id_student),
	CONSTRAINT FK_enrollment_course_editions FOREIGN KEY(id_course_edition)
		REFERENCES course_edition(id_course_edition),
	CONSTRAINT CHK_course_vote CHECK(course_vote >= 0),
	CONSTRAINT CHK_student_vote CHECK(student_vote >= 0)
);

CREATE SEQUENCE enrollment_sequence
  start 1
  increment 1
  OWNED BY enrollment.id_enrollment;

--create table CourseModules
CREATE TABLE course_module
(
  	id_course_module		BIGINT NOT NULL,
	title					VARCHAR(32) NOT NULL,
	cm_content				VARCHAR(200) NOT NULL,
	id_course				BIGINT NOT NULL,
	duration				DECIMAL,
	level					level NOT NULL,
  	CONSTRAINT PK_course_module PRIMARY KEY(id_course_module),
	CONSTRAINT FK_course_module_course FOREIGN KEY(id_course)
		REFERENCES course(id_course),
	CONSTRAINT CHK_duration CHECK(duration >= 0.25)
);

CREATE SEQUENCE course_module_sequence
  start 1
  increment 1
  OWNED BY course_module.id_course_module;

--create table EditionModule
CREATE TABLE edition_module
(
  	id_edition_module		BIGINT NOT NULL,
	id_course_module		BIGINT NOT NULL,
	id_course_edition		BIGINT NOT NULL,
	id_teacher				BIGINT NOT NULL,
	start_date				DATE,
	end_date				DATE,
  	CONSTRAINT PK_edition_module PRIMARY KEY(id_edition_module),
	CONSTRAINT FK_course_module FOREIGN KEY(id_course_module)
		REFERENCES course_module(id_course_module),
	CONSTRAINT FK_edition_module_course_edition FOREIGN KEY(id_course_edition)
		REFERENCES course_edition(id_course_edition),
	CONSTRAINT FK_edition_module_teacher FOREIGN KEY(id_teacher)
		REFERENCES teacher(id_teacher)
);

CREATE SEQUENCE edition_module_sequence
  start 1
  increment 1
  OWNED BY edition_module.id_edition_module;
