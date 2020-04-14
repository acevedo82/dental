DROP TABLE IF EXISTS PACIENTE_BUSQUEDA;
DROP TABLE IF EXISTS CITA;
DROP TABLE IF EXISTS TRATAMIENTOS;
DROP TABLE IF EXISTS PACIENTE;


CREATE TABLE PACIENTE (
	id INT NOT NULL AUTO_INCREMENT,
	nombre VARCHAR(255) NOT NULL DEFAULT '',
	apellido1 VARCHAR(255) NOT NULL DEFAULT '',
	apellido2 VARCHAR(255) NULL DEFAULT '',
	telefono VARCHAR(255) NULL DEFAULT '449-000-0000',
	PRIMARY KEY (id)
)
COLLATE='utf8_general_ci';
INSERT INTO PACIENTE(NOMBRE, APELLIDO1, APELLIDO2, telefono) VALUES ('Jose', 'Acevedo', 'Cerda', '449-000-0000');
INSERT INTO PACIENTE(NOMBRE, APELLIDO1, APELLIDO2, telefono) VALUES ('Miguelito', 'Acevedo', 'Ruiz Esparza', '449-000-0000');
INSERT INTO PACIENTE(NOMBRE, APELLIDO1, APELLIDO2, telefono) VALUES ('Dylan Ramses', 'Perez', 'Gonzalez', '449-000-0000');
INSERT INTO PACIENTE(NOMBRE, APELLIDO1, APELLIDO2, telefono) VALUES ('Abigail', 'Acevedo', 'Ruiz Esparza', '449-000-0000');


CREATE TABLE PACIENTE_BUSQUEDA (
	nombre_completo VARCHAR(255) NOT NULL,
	id_paciente INT NOT NULL DEFAULT 0,
	FULLTEXT INDEX IDX_NOMBRE_COMPLETO_TXT (nombre_completo)  
	--CONSTRAINT FX_PACIENTE FOREIGN KEY (id_paciente) REFERENCES paciente (id)
)
COLLATE='utf8_general_ci';
INSERT INTO PACIENTE_BUSQUEDA (NOMBRE_COMPLETO, ID_PACIENTE) SELECT CONCAT(NOMBRE, ' ', APELLIDO1, ' ', APELLIDO2), ID FROM PACIENTE;

CREATE TABLE TRATAMIENTOS (
	id INT NOT NULL AUTO_INCREMENT,
	tratamiento VARCHAR(128) NOT NULL,
	duracion INT NOT NULL,
	costo INT NULL,
	PRIMARY KEY (id),
	FULLTEXT INDEX IDX_TRATAMIENTO (tratamiento)
)
COLLATE='utf8_general_ci';

INSERT INTO tratamientos(TRATAMIENTO, DURACION, COSTO) VALUES ('CONSULTA', 60, 400);
INSERT INTO tratamientos(TRATAMIENTO, DURACION, COSTO) VALUES ('REVISION', 30, 250);
INSERT INTO tratamientos(TRATAMIENTO, DURACION, COSTO) VALUES ('PULPEX', 60, 800);



CREATE TABLE CITA (
	id INT NOT NULL AUTO_INCREMENT,
	id_paciente INT NOT NULL DEFAULT '0',
	id_tratamiento INT NOT NULL DEFAULT '0',
	startDate BIGINT NOT NULL,
	endDate BIGINT NOT NULL,
	confirmacion int not null default 0,
	PRIMARY KEY (id),
	INDEX IDX_CITA (id_paciente, id_tratamiento),
	CONSTRAINT FK_PACIENTE FOREIGN KEY (id_paciente) REFERENCES paciente (id) on delete cascade,
	CONSTRAINT FK_TRATAMIENTO FOREIGN KEY (id_tratamiento) REFERENCES tratamientos (id) on delete cascade
)
COLLATE='utf8_general_ci';