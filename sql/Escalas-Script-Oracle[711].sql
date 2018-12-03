--Script simplificado para crear la BD del TFG Escalas

DROP TABLE IF EXISTS PUNTUACION; --CASCADE CONSTRAINTS;

DROP TABLE IF EXISTS EVALUACION; --CASCADE CONSTRAINTS;

DROP TABLE IF EXISTS ITEM; --CASCADE CONSTRAINTS;

DROP TABLE IF EXISTS CATEGORIZACION; --CASCADE CONSTRAINTS;

DROP TABLE IF EXISTS AREAFUNCIONAL; --CASCADE CONSTRAINTS;

DROP TABLE IF EXISTS ALUMNO; --CASCADE CONSTRAINTS;

DROP TABLE IF EXISTS PROFESOR;

DROP TABLE IF EXISTS AULA;

DROP SEQUENCE IF EXISTS seq_areafuncional;

DROP SEQUENCE IF EXISTS seq_categorizacion;

DROP SEQUENCE IF EXISTS seq_item;


CREATE SEQUENCE seq_areafuncional;

CREATE SEQUENCE seq_categorizacion;

CREATE SEQUENCE seq_item;

CREATE TABLE AREAFUNCIONAL(
    ID NUMERIC PRIMARY KEY,
    DESCRIPCION VARCHAR(100) NOT NULL,
    PUNTUACION_MAXIMA NUMERIC(3) NOT NULL
);

CREATE TABLE CATEGORIZACION(
    ID NUMERIC PRIMARY KEY,
    DESCRIPCION VARCHAR(100) NOT NULL,
    PUNTUACION_MAXIMA NUMERIC(3) NOT NULL,
    ID_AREAFUNCIONAL NUMERIC,
    CONSTRAINT FK_AREAFUNCIONAL FOREIGN KEY (ID_AREAFUNCIONAL) REFERENCES AREAFUNCIONAL    
);

CREATE TABLE ITEM(
    NUMERO NUMERIC PRIMARY KEY,
    DESCRIPCION VARCHAR(200),
    EDAD_ASIGNADA NUMERIC, 
    ID_CATEGORIZACION NUMERIC NOT NULL,
    CONSTRAINT FK_CATEGORIZACION FOREIGN KEY (ID_CATEGORIZACION) REFERENCES CATEGORIZACION
    
);

CREATE TABLE AULA (
    ID NUMERIC PRIMARY KEY,
    NOMBRE VARCHAR(10) UNIQUE NOT NULL,
    CAPACIDAD NUMERIC NOT NULL  
);

CREATE TABLE PROFESOR (
    ID NUMERIC PRIMARY KEY,
    NIF VARCHAR(10) UNIQUE NOT NULL,
    FECHA_NACIMIENTO DATE NOT NULL,
    NOMBRE VARCHAR(50) NOT NULL,
    APELLIDO1 VARCHAR(75) NOT NULL,
    APELLIDO2 VARCHAR(75),
	ID_AULA NUMERIC NOT NULL,
	CONSTRAINT FK_AULA_PROFESOR FOREIGN KEY (ID_AULA) REFERENCES AULA
	
);

CREATE TABLE ALUMNO (
    ID NUMERIC PRIMARY KEY,
    NIF VARCHAR(10) UNIQUE NOT NULL,
    FECHA_NACIMIENTO DATE NOT NULL,
    NOMBRE VARCHAR(50) NOT NULL,
    APELLIDO1 VARCHAR(75) NOT NULL,
    APELLIDO2 VARCHAR(75),
    DIRECCION VARCHAR(150),
	ID_AULA NUMERIC NOT NULL,
	CONSTRAINT FK_AULA_ALUMNO FOREIGN KEY (ID_AULA) REFERENCES AULA
);

CREATE TABLE EVALUACION(
    ID NUMERIC PRIMARY KEY,
    FECHA TIMESTAMP,
    ID_ALUMNO NUMERIC,
    CONSTRAINT K_EVALUACION UNIQUE (FECHA, ID_ALUMNO),
    CONSTRAINT FK_ALUMNO FOREIGN KEY (ID_ALUMNO) REFERENCES ALUMNO
);

CREATE TABLE PUNTUACION(
    ID NUMERIC PRIMARY KEY,
    VALORACION NUMERIC,
    ID_ITEM NUMERIC NOT NULL,
    ID_EVALUACION NUMERIC,    
    CONSTRAINT FK_ITEM FOREIGN KEY (ID_ITEM) REFERENCES ITEM,
    CONSTRAINT FK_EVALUACION FOREIGN KEY (ID_EVALUACION) REFERENCES EVALUACION    
);


---------------------------------------------------------------------
INSERT INTO AREAFUNCIONAL VALUES (nextval('public.seq_areafuncional'), 'Autonomía en la alimentación',10);
INSERT INTO CATEGORIZACION VALUES(nextval('public.seq_categorizacion'), 'Textura en los alimentos', 5, currval('public.seq_areafuncional'));
INSERT INTO ITEM VALUES (nextval('public.seq_item'), 'Come alimentos de todo tipo de consistencias', 0, currval('public.seq_categorizacion'));
																											   
INSERT INTO CATEGORIZACION VALUES(nextval('public.seq_categorizacion'), 'Uso de utensilios', 30, currval('public.seq_areafuncional'));
INSERT INTO ITEM VALUES (nextval('public.seq_item'), 'Come con las manos', 0, currval('public.seq_categorizacion'));
INSERT INTO ITEM VALUES (nextval('public.seq_item'), 'Utiliza correctamente la cuchara', 0, currval('public.seq_categorizacion'));
INSERT INTO ITEM VALUES (nextval('public.seq_item'), 'Utiliza correctamente el tenedor', 0, currval('public.seq_categorizacion'));
INSERT INTO ITEM VALUES (nextval('public.seq_item'), 'Sostiene y usa una botella o vaso con pitorro', 0, currval('public.seq_categorizacion'));
INSERT INTO ITEM VALUES (nextval('public.seq_item'), 'Sujeta el vaso con las dos manos con seguridad', 0, currval('public.seq_categorizacion'));
INSERT INTO ITEM VALUES (nextval('public.seq_item'), 'Sujeta el vaso con una mano con seguridad', 0, currval('public.seq_categorizacion'));
		
-------------------------------------------------------------------------------------------------
INSERT INTO AREAFUNCIONAL VALUES (nextval('public.seq_areafuncional'), 'Cuidado e higiene personal',80);
INSERT INTO CATEGORIZACION VALUES(nextval('public.seq_categorizacion'), 'Higiene dental', 20, currval('public.seq_areafuncional'));
INSERT INTO ITEM VALUES (nextval('public.seq_item'), 'Abre la boca para que le laven los dientes', 0, currval('public.seq_categorizacion'));
INSERT INTO ITEM VALUES (nextval('public.seq_item'), 'Sostiene el cepillo de dientes con la mano', 0, currval('public.seq_categorizacion'));
INSERT INTO ITEM VALUES (nextval('public.seq_item'), 'Prepara el cepillo y la pasta de dientes', 0, currval('public.seq_categorizacion'));
INSERT INTO ITEM VALUES (nextval('public.seq_item'), 'Se lava los dientes minuciosamente', 0, currval('public.seq_categorizacion'));
																	
INSERT INTO CATEGORIZACION VALUES(nextval('public.seq_categorizacion'), 'Peinado', 15, currval('public.seq_areafuncional'));
INSERT INTO ITEM VALUES (nextval('public.seq_item'), 'Se lava los dientes minuciosamente', 0, currval('public.seq_categorizacion'));
INSERT INTO ITEM VALUES (nextval('public.seq_item'), 'Sujeta la cabeza mientras le peinan', 0, currval('public.seq_categorizacion'));
INSERT INTO ITEM VALUES (nextval('public.seq_item'), 'Se lleva el cepillo o peine al pelo', 0, currval('public.seq_categorizacion'));
																	
INSERT INTO CATEGORIZACION VALUES(nextval('public.seq_categorizacion'), 'Cuidado nasal', 10, currval('public.seq_areafuncional'));
INSERT INTO ITEM VALUES (nextval('public.seq_item'), 'Deja que le limpien la nariz', 0, currval('public.seq_categorizacion'));
INSERT INTO ITEM VALUES (nextval('public.seq_item'), 'Se suena y se limpia la nariz con un pañuelo de forma independiente', 0, currval('public.seq_categorizacion'));
																	
INSERT INTO CATEGORIZACION VALUES(nextval('public.seq_categorizacion'), 'Lavado de manos', 20, currval('public.seq_areafuncional'));
INSERT INTO ITEM VALUES (nextval('public.seq_item'), 'Coloca las manos para que se las laven', 0, currval('public.seq_categorizacion'));
INSERT INTO ITEM VALUES (nextval('public.seq_item'), 'Abre y cierra el grifo y coge el jabón', 0, currval('public.seq_categorizacion'));
INSERT INTO ITEM VALUES (nextval('public.seq_item'), 'Se limpia las manos minuciosamente', 0, currval('public.seq_categorizacion'));
INSERT INTO ITEM VALUES (nextval('public.seq_item'), 'Se seca las manos minuciosamente', 0, currval('public.seq_categorizacion'));
																	
INSERT INTO CATEGORIZACION VALUES(nextval('public.seq_categorizacion'), 'Lavado de cara y cuerpo', 15, currval('public.seq_areafuncional'));
INSERT INTO ITEM VALUES (nextval('public.seq_item'), 'Se lava alguna parte del cuerpo', 0, currval('public.seq_categorizacion'));
INSERT INTO ITEM VALUES (nextval('public.seq_item'), 'Se enjabona', 0, currval('public.seq_categorizacion'));
INSERT INTO ITEM VALUES (nextval('public.seq_item'), 'Se seca', 0, currval('public.seq_categorizacion'));
		
-------------------------------------------------------------------------------------------------
INSERT INTO AREAFUNCIONAL VALUES (nextval('public.seq_areafuncional'), 'Autonomía vestido y desvestido',75);
INSERT INTO CATEGORIZACION VALUES(nextval('public.seq_categorizacion'), 'Vestido y desvestido (parte superior)', 25, currval('public.seq_areafuncional'));
INSERT INTO ITEM VALUES (nextval('public.seq_item'), 'Ayuda metiendo el brazo por la manga de la camisa', 0, currval('public.seq_categorizacion'));
INSERT INTO ITEM VALUES (nextval('public.seq_item'), 'Se quita camisetas sin cierres', 0, currval('public.seq_categorizacion'));
INSERT INTO ITEM VALUES (nextval('public.seq_item'), 'Intenta abrochar/desabrochar cierres', 0, currval('public.seq_categorizacion'));
INSERT INTO ITEM VALUES (nextval('public.seq_item'), 'Se quita prendas con cierres', 0, currval('public.seq_categorizacion'));
INSERT INTO ITEM VALUES (nextval('public.seq_item'), 'Se pone camisetas sin cierres', 0, currval('public.seq_categorizacion'));
																	
INSERT INTO CATEGORIZACION VALUES(nextval('public.seq_categorizacion'), 'Vestido y desvestido (parte inferior)', 50, currval('public.seq_areafuncional'));
INSERT INTO ITEM VALUES (nextval('public.seq_item'), 'Se pone camisetas con cierres', 0, currval('public.seq_categorizacion'));
INSERT INTO ITEM VALUES (nextval('public.seq_item'), 'Ayuda metiendo la pierna por los pantalones', 0, currval('public.seq_categorizacion'));
INSERT INTO ITEM VALUES (nextval('public.seq_item'), 'Se quita pantalones con cintura elástica', 0, currval('public.seq_categorizacion'));
INSERT INTO ITEM VALUES (nextval('public.seq_item'), 'Se quita pantalones con cierre', 0, currval('public.seq_categorizacion'));
INSERT INTO ITEM VALUES (nextval('public.seq_item'), 'Se pone los pantalones con cintura elástica', 0, currval('public.seq_categorizacion'));
INSERT INTO ITEM VALUES (nextval('public.seq_item'), 'Se pone pantalones con cierres', 0, currval('public.seq_categorizacion'));
INSERT INTO ITEM VALUES (nextval('public.seq_item'), 'Se quita zapatos sin desatar y calcetines', 0, currval('public.seq_categorizacion'));
INSERT INTO ITEM VALUES (nextval('public.seq_item'), 'Se pone calcetines', 0, currval('public.seq_categorizacion'));
INSERT INTO ITEM VALUES (nextval('public.seq_item'), 'Se pone zapatos con velcro', 0, currval('public.seq_categorizacion'));
INSERT INTO ITEM VALUES (nextval('public.seq_item'), 'Se pone zapatos sin atar', 0, currval('public.seq_categorizacion'));
																	
-------------------------------------------------------------------------------------------------
INSERT INTO AREAFUNCIONAL VALUES (nextval('public.seq_areafuncional'), 'Control esfinteres',35);
INSERT INTO CATEGORIZACION VALUES(nextval('public.seq_categorizacion'), 'Control de esfinteres', 35, currval('public.seq_areafuncional'));
INSERT INTO ITEM VALUES (nextval('public.seq_item'), 'Avisa cuando hay que cambiarle el pañal', 0, currval('public.seq_categorizacion'));
INSERT INTO ITEM VALUES (nextval('public.seq_item'), 'Avisa con tiempo de querer orinar (durante el día)', 0, currval('public.seq_categorizacion'));
INSERT INTO ITEM VALUES (nextval('public.seq_item'), 'Orina solo', 0, currval('public.seq_categorizacion'));
INSERT INTO ITEM VALUES (nextval('public.seq_item'), 'Avisa con tiempo de que necesita hacer de vientre(durante el día)', 0, currval('public.seq_categorizacion'));
INSERT INTO ITEM VALUES (nextval('public.seq_item'), 'Va al baño solo a hacer de vientre', 0, currval('public.seq_categorizacion'));
INSERT INTO ITEM VALUES (nextval('public.seq_item'), 'Diferencia entre ganas de orinar y hacer de vientre', 0, currval('public.seq_categorizacion'));
INSERT INTO ITEM VALUES (nextval('public.seq_item'), 'Se mantiene seco durante la noche', 0, currval('public.seq_categorizacion'));
												
--------------------------------------------------------------------------------------------------
INSERT INTO AREAFUNCIONAL VALUES (nextval('public.seq_areafuncional'), 'Movilidad funcional',150);
INSERT INTO CATEGORIZACION VALUES(nextval('public.seq_categorizacion'), 'Extremidad superior', 25, currval('public.seq_areafuncional'));
INSERT INTO ITEM VALUES (nextval('public.seq_item'), 'Coge un jugete con la mano izquierda, situado en frente en el lado derecho', 0, currval('public.seq_categorizacion'));
INSERT INTO ITEM VALUES (nextval('public.seq_item'), 'Coge u juguete con la mano derecha, situado en frente en el lado izquierdo', 0, currval('public.seq_categorizacion'));
INSERT INTO ITEM VALUES (nextval('public.seq_item'), 'Sentado con los pies al frente, toca un juguete a 45º detrás a la derecha', 0, currval('public.seq_categorizacion'));
INSERT INTO ITEM VALUES (nextval('public.seq_item'), 'Sentado con los pies al frente, toca un juguete a 45º detrás a la izquierda', 0, currval('public.seq_categorizacion'));
INSERT INTO ITEM VALUES (nextval('public.seq_item'), 'Sentado con los pies al frente, toca un juguete que está delante y vuelve a la posición inicial', 0, currval('public.seq_categorizacion'));
																	
INSERT INTO CATEGORIZACION VALUES(nextval('public.seq_categorizacion'), 'Transferencias en el WC', 15, currval('public.seq_areafuncional'));
INSERT INTO ITEM VALUES (nextval('public.seq_item'), 'Se sienta y se levanta de un váter bajo y orinal', 0, currval('public.seq_categorizacion'));
INSERT INTO ITEM VALUES (nextval('public.seq_item'), 'Se sienta y se levanta de un váter de tamañp normal', 0, currval('public.seq_categorizacion'));
INSERT INTO ITEM VALUES (nextval('public.seq_item'), 'Se mantiene sentado sin ayuda en el váter u orinal', 0, currval('public.seq_categorizacion'));
																	
INSERT INTO CATEGORIZACION VALUES(nextval('public.seq_categorizacion'), 'Transferencias en una silla', 10, currval('public.seq_areafuncional'));
INSERT INTO ITEM VALUES (nextval('public.seq_item'), 'Sentado en una silla controlla la cabeza', 0, currval('public.seq_categorizacion'));
INSERT INTO ITEM VALUES (nextval('public.seq_item'), 'Se sienta y se levanta de una silla sin necesidad de ayudarse con las brazos', 0, currval('public.seq_categorizacion'));
																	
INSERT INTO CATEGORIZACION VALUES(nextval('public.seq_categorizacion'), 'Movilidad y transferencias en la cama', 20, currval('public.seq_areafuncional'));
INSERT INTO ITEM VALUES (nextval('public.seq_item'), 'Desde la posición de tumbado puede sentarse sólo en la cama o cuna', 0, currval('public.seq_categorizacion'));
INSERT INTO ITEM VALUES (nextval('public.seq_item'), 'Cambia de posición en la cama, de bocarriba a bocabajo y viceversa', 0, currval('public.seq_categorizacion'));
INSERT INTO ITEM VALUES (nextval('public.seq_item'), 'Se tumba y levanta de la cama sin ayuda', 0, currval('public.seq_categorizacion'));
																	
INSERT INTO CATEGORIZACION VALUES(nextval('public.seq_categorizacion'), 'Movilidad en la bañera', 10, currval('public.seq_areafuncional'));
INSERT INTO ITEM VALUES (nextval('public.seq_item'), 'Pies al frente, se mantiene sentado sin soporte de las manos', 0, currval('public.seq_categorizacion'));
INSERT INTO ITEM VALUES (nextval('public.seq_item'), 'Entra y sale de una bañera normal de manera independiente', 0, currval('public.seq_categorizacion'));
																	
INSERT INTO CATEGORIZACION VALUES(nextval('public.seq_categorizacion'), 'Movilidad en interiores', 25, currval('public.seq_areafuncional'));
INSERT INTO ITEM VALUES (nextval('public.seq_item'), 'Gatea o se arrastra por el suelo', 0, currval('public.seq_categorizacion'));
INSERT INTO ITEM VALUES (nextval('public.seq_item'), 'Camina con apoyo con ayudas técnicas', 0, currval('public.seq_categorizacion'));
INSERT INTO ITEM VALUES (nextval('public.seq_item'), 'Camina sin ayudas', 0, currval('public.seq_categorizacion'));
INSERT INTO ITEM VALUES (nextval('public.seq_item'), 'Se mueve entre varias habitaciones', 0, currval('public.seq_categorizacion'));
INSERT INTO ITEM VALUES (nextval('public.seq_item'), 'Abre y cierra puertas', 0, currval('public.seq_categorizacion'));

INSERT INTO CATEGORIZACION VALUES(nextval('public.seq_categorizacion'), 'Transporte de objetos', 20, currval('public.seq_areafuncional'));
INSERT INTO ITEM VALUES (nextval('public.seq_item'), 'Mueve un objeto de sitio de forma intencionada', 0, currval('public.seq_categorizacion'));
INSERT INTO ITEM VALUES (nextval('public.seq_item'), 'Arrastra un objeto por el suelo', 0, currval('public.seq_categorizacion'));
INSERT INTO ITEM VALUES (nextval('public.seq_item'), 'Transporta objetos pequeños con una mano', 0, currval('public.seq_categorizacion'));
INSERT INTO ITEM VALUES (nextval('public.seq_item'), 'Transporta objetos grandes para lo que necesita las dos manos', 0, currval('public.seq_categorizacion'));
																	
INSERT INTO CATEGORIZACION VALUES(nextval('public.seq_categorizacion'), 'Movilidad en exteriores', 30, currval('public.seq_areafuncional'));
INSERT INTO ITEM VALUES (nextval('public.seq_item'), 'Camina con ayuda o con productos de apoyo', 0, currval('public.seq_categorizacion'));
INSERT INTO ITEM VALUES (nextval('public.seq_item'), 'Usa silla de ruedas', 0, currval('public.seq_categorizacion'));
INSERT INTO ITEM VALUES (nextval('public.seq_item'), 'Camina sin ayudas', 0, currval('public.seq_categorizacion'));
INSERT INTO ITEM VALUES (nextval('public.seq_item'), 'Camina sin ayuda por todo tipo de superficies', 0, currval('public.seq_categorizacion'));
INSERT INTO ITEM VALUES (nextval('public.seq_item'), 'Sube escaleras', 0, currval('public.seq_categorizacion'));
INSERT INTO ITEM VALUES (nextval('public.seq_item'), 'Baja escaleras', 0, currval('public.seq_categorizacion'));
												
---------------------------------------------------------------------------------------------------
INSERT INTO AREAFUNCIONAL VALUES (nextval('public.seq_areafuncional'), 'Comunicación y Lenguaje',50);
INSERT INTO CATEGORIZACION VALUES(nextval('public.seq_categorizacion'), 'Comprensión de palabras', 15, currval('public.seq_areafuncional'));
INSERT INTO ITEM VALUES (nextval('public.seq_item'), 'Se orienta hacia el sonido', 0, currval('public.seq_categorizacion'));
INSERT INTO ITEM VALUES (nextval('public.seq_item'), 'Entiende el ''no''; reconoce su nombre o el de gente familiar ', 0, currval('public.seq_categorizacion'));
INSERT INTO ITEM VALUES (nextval('public.seq_item'), 'Entiende el significado cuando se habla acerca de las relaciones entre personas y objetos que son visibles', 0, currval('public.seq_categorizacion'));
																	
INSERT INTO CATEGORIZACION VALUES(nextval('public.seq_categorizacion'), 'Comprensión de frases', 15, currval('public.seq_areafuncional'));
INSERT INTO ITEM VALUES (nextval('public.seq_item'), 'Entiende frases cortas acerca de objetos y gente familiar', 0, currval('public.seq_categorizacion'));
INSERT INTO ITEM VALUES (nextval('public.seq_item'), 'Entiende órdenes sencillas con palabras que describen gente o cosas', 0, currval('public.seq_categorizacion'));
INSERT INTO ITEM VALUES (nextval('public.seq_item'), 'Entiende instrucciones que describen donde está algo', 0, currval('public.seq_categorizacion'));
																	
INSERT INTO CATEGORIZACION VALUES(nextval('public.seq_categorizacion'), 'Uso funcional de la comunicación', 20, currval('public.seq_areafuncional'));
INSERT INTO ITEM VALUES (nextval('public.seq_item'), 'Nombra cosas', 0, currval('public.seq_categorizacion'));
INSERT INTO ITEM VALUES (nextval('public.seq_item'), 'Usa palabras o gestos específicos para dirigirse o pedir algo a otra persona', 0, currval('public.seq_categorizacion'));
INSERT INTO ITEM VALUES (nextval('public.seq_item'), 'Hace preguntas', 0, currval('public.seq_categorizacion'));
INSERT INTO ITEM VALUES (nextval('public.seq_item'), 'Habla acerca de sus sentimientos', 0, currval('public.seq_categorizacion'));

--------------------------------------------------------------------------------------------
INSERT INTO AREAFUNCIONAL VALUES (nextval('public.seq_areafuncional'), 'Resolución de tareas en contextos sociales',25);
INSERT INTO CATEGORIZACION VALUES(nextval('public.seq_categorizacion'), 'Resolucion de problemas', 10, currval('public.seq_areafuncional'));
INSERT INTO ITEM VALUES (nextval('public.seq_item'), 'Intenta mostrarle el problema o comunicarte que hay que hacer para resolver el problema', 0, currval('public.seq_categorizacion'));
INSERT INTO ITEM VALUES (nextval('public.seq_item'), 'Hay que ayudarle inmediatamente si tiene algún problema para que su comportamiento no se altere', 0, currval('public.seq_categorizacion'));
																	
INSERT INTO CATEGORIZACION VALUES(nextval('public.seq_categorizacion'), 'Información de sí mismo', 15, currval('public.seq_areafuncional'));
INSERT INTO ITEM VALUES (nextval('public.seq_item'), 'Sabe decir su nombre y apellido', 0, currval('public.seq_categorizacion'));
INSERT INTO ITEM VALUES (nextval('public.seq_item'), 'Sabe el nombre y la descripción de los miembros de su familia', 0, currval('public.seq_categorizacion'));
INSERT INTO ITEM VALUES (nextval('public.seq_item'), 'Sabe dónde vive', 0, currval('public.seq_categorizacion'));
					
----------------------------------------------------------------------------------------------
INSERT INTO AREAFUNCIONAL VALUES (nextval('public.seq_areafuncional'), 'Juego interactivo y simbólico',50);
INSERT INTO CATEGORIZACION VALUES(nextval('public.seq_categorizacion'), 'Juego interactivo', 15, currval('public.seq_areafuncional'));
INSERT INTO ITEM VALUES (nextval('public.seq_item'), 'Inicia una rutina de juego con el que está familiarizado', 0, currval('public.seq_categorizacion'));
INSERT INTO ITEM VALUES (nextval('public.seq_item'), 'Participa en un juego simplemente respetando su turno', 0, currval('public.seq_categorizacion'));
INSERT INTO ITEM VALUES (nextval('public.seq_item'), 'Intenta imitar la acción previa de un adulto durante un juego', 0, currval('public.seq_categorizacion'));
																	
INSERT INTO CATEGORIZACION VALUES(nextval('public.seq_categorizacion'), 'Interacción con el grupo de iguales', 10, currval('public.seq_areafuncional'));
INSERT INTO ITEM VALUES (nextval('public.seq_item'), 'Es consciente de la presencia de otros niños, por lo que gesticula y vocaliza', 0, currval('public.seq_categorizacion'));
INSERT INTO ITEM VALUES (nextval('public.seq_item'), 'Interacciona con otros niños', 0, currval('public.seq_categorizacion'));
																	
INSERT INTO CATEGORIZACION VALUES(nextval('public.seq_categorizacion'), 'Juego con objetos', 25, currval('public.seq_areafuncional'));
INSERT INTO ITEM VALUES (nextval('public.seq_item'), 'Manipula juguetes, objetos y partes del cuerpo con intención', 0, currval('public.seq_categorizacion'));
INSERT INTO ITEM VALUES (nextval('public.seq_item'), 'Utiliza objetos reales o de juguete en juegos simples e imaginados', 0, currval('public.seq_categorizacion'));
INSERT INTO ITEM VALUES (nextval('public.seq_item'), 'Junta materiales para crear algo', 0, currval('public.seq_categorizacion'));
INSERT INTO ITEM VALUES (nextval('public.seq_item'), 'Realiza juegos simbólicos utilizando cosas que el niño conoce', 0, currval('public.seq_categorizacion'));
INSERT INTO ITEM VALUES (nextval('public.seq_item'), 'Realiza juegos simbólicos imaginados', 0, currval('public.seq_categorizacion'));

------------------------------------------------------------------------------------------------
INSERT INTO AREAFUNCIONAL VALUES (nextval('public.seq_areafuncional'), 'Rutinas en la vida diaria',15);
INSERT INTO CATEGORIZACION VALUES(nextval('public.seq_categorizacion'), 'Orientación del tiempo', 5, currval('public.seq_areafuncional'));
INSERT INTO ITEM VALUES (nextval('public.seq_item'), 'Tiene conciencia de manera general del horario de comidas y rutinas diarias', 0, currval('public.seq_categorizacion'));

INSERT INTO CATEGORIZACION VALUES(nextval('public.seq_categorizacion'), 'Tareas domesticas', 10, currval('public.seq_areafuncional'));
INSERT INTO ITEM VALUES (nextval('public.seq_item'), 'Ayuda en el cuidado de sus pertenencias si se le dan constantes instrucciones', 0, currval('public.seq_categorizacion'));
INSERT INTO ITEM VALUES (nextval('public.seq_item'), 'Ayuda en tareas domésticas simples si se le dan constantes instrucciones', 0, currval('public.seq_categorizacion'));

-------------------------------------------------------------------------------------------------
INSERT INTO AREAFUNCIONAL VALUES (nextval('public.seq_areafuncional'), 'Conducta adaptativa',45);
INSERT INTO CATEGORIZACION VALUES(nextval('public.seq_categorizacion'), 'Comportamiento autolesivo', 30, currval('public.seq_areafuncional'));
INSERT INTO ITEM VALUES (nextval('public.seq_item'), 'Se autolesiona en situaciones contextuales habituales', 0, currval('public.seq_categorizacion'));
INSERT INTO ITEM VALUES (nextval('public.seq_item'), 'Se autolesiona en situaciones de estrés o enfado para el niño', 0, currval('public.seq_categorizacion'));

INSERT INTO CATEGORIZACION VALUES(nextval('public.seq_categorizacion'), 'Heteroagresividad(daño a otros)', 30, currval('public.seq_areafuncional'));
INSERT INTO ITEM VALUES (nextval('public.seq_item'), 'Causa daño a otros', 0, currval('public.seq_categorizacion'));
INSERT INTO ITEM VALUES (nextval('public.seq_item'), 'En una situación de estrés o enfado tiene conductas agresivas hacia el mismo o los otros', 0, currval('public.seq_categorizacion'));
INSERT INTO ITEM VALUES (nextval('public.seq_item'), 'No causa daño a familiares o profesionales en momentos de ayuda', 0, currval('public.seq_categorizacion'));

INSERT INTO CATEGORIZACION VALUES(nextval('public.seq_categorizacion'), 'Destrucción de objetos', 30, currval('public.seq_areafuncional'));
INSERT INTO ITEM VALUES (nextval('public.seq_item'), 'Rompe intencionadamente objetos', 0, currval('public.seq_categorizacion'));

INSERT INTO CATEGORIZACION VALUES(nextval('public.seq_categorizacion'), 'Conduca disrruptiva(llorar, gritar, reirse sin motivo)', 30, currval('public.seq_areafuncional'));
INSERT INTO ITEM VALUES (nextval('public.seq_item'), 'Presenta conductas disfuncionales', 0, currval('public.seq_categorizacion'));

INSERT INTO CATEGORIZACION VALUES(nextval('public.seq_categorizacion'), 'Esteriotipias', 30, currval('public.seq_areafuncional'));
INSERT INTO ITEM VALUES (nextval('public.seq_item'), 'Presenta esteriotipias que no interfieren en sus actividades', 0, currval('public.seq_categorizacion'));
INSERT INTO ITEM VALUES (nextval('public.seq_item'), 'Presenta estereotipias que interfieren en su funcionamiento o desempeño de actividades', 0, currval('public.seq_categorizacion'));

---------------------------------------------------------------------------------------------
INSERT INTO AREAFUNCIONAL VALUES (nextval('public.seq_areafuncional'), 'Atención',10);
INSERT INTO CATEGORIZACION VALUES(nextval('public.seq_categorizacion'), 'Atención', 10, currval('public.seq_areafuncional'));
INSERT INTO ITEM VALUES (nextval('public.seq_item'), 'Se muestra activo durante la realización de juegos o actividades individuales', 0, currval('public.seq_categorizacion'));
INSERT INTO ITEM VALUES (nextval('public.seq_item'), 'Se muestra activo durante la realización de juegos o actividades en grupo', 0, currval('public.seq_categorizacion'));

