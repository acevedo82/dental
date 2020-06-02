DROP TABLE IF EXISTS LISTA_ESPERA;
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
	informacion TINYTEXT NULL DEFAULT NULL,
	PRIMARY KEY (id)
)
COLLATE='utf8_general_ci'; 
INSERT INTO PACIENTE(NOMBRE, APELLIDO1, APELLIDO2, telefono) VALUES ('Jose Miguel', 'Acevedo', 'Cerda', '449-000-0000');
INSERT INTO PACIENTE(NOMBRE, APELLIDO1, APELLIDO2, telefono) VALUES ('Miguelito', 'Acevedo', 'Ruiz Esparza', '449-000-0000');
INSERT INTO PACIENTE(NOMBRE, APELLIDO1, APELLIDO2, telefono) VALUES ('Abigail', 'Acevedo', 'Ruiz Esparza', '449-000-0000');


CREATE TABLE LISTA_ESPERA (
	id INT NOT NULL AUTO_INCREMENT,	
	id_paciente INT NOT NULL,
	informacion TINYTEXT NULL DEFAULT NULL,	
	PRIMARY KEY (id),
	INDEX IDX_ESPERA (id_paciente, id),
	CONSTRAINT FX_ESPERA_PACIENTE FOREIGN KEY (id_paciente) REFERENCES PACIENTE (id)
)
COLLATE='utf8_general_ci';


CREATE TABLE PACIENTE_BUSQUEDA (
	nombre_completo VARCHAR(255) NOT NULL,
	id_paciente INT NOT NULL DEFAULT 0,
	FULLTEXT INDEX IDX_NOMBRE_COMPLETO_TXT (nombre_completo)  
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

INSERT INTO TRATAMIENTOS(TRATAMIENTO, DURACION, COSTO) VALUES ('CONSULTA 1A VEZ', 60, 400);
INSERT INTO TRATAMIENTOS(TRATAMIENTO, DURACION, COSTO) VALUES ('CONSULTA 6M', 30, 250);
INSERT INTO TRATAMIENTOS(TRATAMIENTO, DURACION, COSTO) VALUES ('CURACION TEMPORAL', 60, 800);
INSERT INTO TRATAMIENTOS(TRATAMIENTO, DURACION, COSTO) VALUES ('APARATO ORTOPEDIA', 60, 800);
INSERT INTO TRATAMIENTOS(TRATAMIENTO, DURACION, COSTO) VALUES ('ORTODONCIA', 60, 800);
INSERT INTO TRATAMIENTOS(TRATAMIENTO, DURACION, COSTO) VALUES ('COLORACION ORTODONCIA', 60, 800);
INSERT INTO TRATAMIENTOS(TRATAMIENTO, DURACION, COSTO) VALUES ('REVISION 15MIN', 60, 800);
INSERT INTO TRATAMIENTOS(TRATAMIENTO, DURACION, COSTO) VALUES ('RESINA', 60, 800);
INSERT INTO TRATAMIENTOS(TRATAMIENTO, DURACION, COSTO) VALUES ('PULPOTOMIA Y CORONA METAL', 60, 800);
INSERT INTO TRATAMIENTOS(TRATAMIENTO, DURACION, COSTO) VALUES ('PULPOTOMIA Y CORONA ESTETICA', 60, 800);
INSERT INTO TRATAMIENTOS(TRATAMIENTO, DURACION, COSTO) VALUES ('PULPECTOMIA Y CORONA METAL', 60, 800);
INSERT INTO TRATAMIENTOS(TRATAMIENTO, DURACION, COSTO) VALUES ('PULPECTOMIA Y CORONA ESTETICA', 60, 800);
INSERT INTO TRATAMIENTOS(TRATAMIENTO, DURACION, COSTO) VALUES ('IMPRESION APARATO ORTOPEDIA', 60, 800);
INSERT INTO TRATAMIENTOS(TRATAMIENTO, DURACION, COSTO) VALUES ('IMPRESION PARA BANDA', 60, 800);
INSERT INTO TRATAMIENTOS(TRATAMIENTO, DURACION, COSTO) VALUES ('REPARACION APARATO', 60, 800);
INSERT INTO TRATAMIENTOS(TRATAMIENTO, DURACION, COSTO) VALUES ('IMPRESION TRAMPA', 60, 800);
INSERT INTO TRATAMIENTOS(TRATAMIENTO, DURACION, COSTO) VALUES ('CORONA METALICA', 60, 800);
INSERT INTO TRATAMIENTOS(TRATAMIENTO, DURACION, COSTO) VALUES ('CORONA CELULOIDE', 60, 800);
INSERT INTO TRATAMIENTOS(TRATAMIENTO, DURACION, COSTO) VALUES ('EXTRACCION', 60, 800);
INSERT INTO TRATAMIENTOS(TRATAMIENTO, DURACION, COSTO) VALUES ('URGENCIA', 60, 800);
INSERT INTO TRATAMIENTOS(TRATAMIENTO, DURACION, COSTO) VALUES ('CIRUGIA', 60, 800);


CREATE TABLE CITA (
	id INT NOT NULL AUTO_INCREMENT,
	id_paciente INT NOT NULL, 
	id_tratamiento INT NOT NULL,
	startDate BIGINT NOT NULL,
	endDate BIGINT NOT NULL,
	confirmacion int not null default 0,
	PRIMARY KEY (id),
	INDEX IDX_CITA (id_paciente, id_tratamiento),
		CONSTRAINT FK_CITA_PACIENTE FOREIGN KEY (id_paciente) REFERENCES PACIENTE (id)
)
COLLATE='utf8_general_ci';

insert into cita(id_paciente, id_tratamiento, startdate, endDate) values(1,1,UNIX_TIMESTAMP(NOW())*1000, UNIX_TIMESTAMP(NOW()+INTERVAL 30 MINUTE)*1000);
insert into cita(id_paciente, id_tratamiento, startdate, endDate) values(1,3,UNIX_TIMESTAMP(NOW()+ INTERVAL 30 MINUTE)*1000, UNIX_TIMESTAMP(NOW()+INTERVAL 60 MINUTE)*1000);
insert into cita(id_paciente, id_tratamiento, startdate, endDate) values(2,5,UNIX_TIMESTAMP(NOW()+ INTERVAL 60 MINUTE)*1000, UNIX_TIMESTAMP(NOW()+INTERVAL 90 MINUTE)*1000);
insert into cita(id_paciente, id_tratamiento, startdate, endDate) values(2,7,UNIX_TIMESTAMP(NOW()+ INTERVAL 90 MINUTE)*1000, UNIX_TIMESTAMP(NOW()+INTERVAL 120 MINUTE)*1000);
insert into cita(id_paciente, id_tratamiento, startdate, endDate) values(3,9,UNIX_TIMESTAMP(NOW()+ INTERVAL 120 MINUTE)*1000, UNIX_TIMESTAMP(NOW()+INTERVAL 150 MINUTE)*1000);
insert into cita(id_paciente, id_tratamiento, startdate, endDate) values(3,11,UNIX_TIMESTAMP(NOW()- INTERVAL 150 MINUTE)*1000, UNIX_TIMESTAMP(NOW()+INTERVAL 180 MINUTE)*1000);

