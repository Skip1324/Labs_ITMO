
CREATE TYPE gender AS Enum('Мужчина','Женщина');

CREATE TYPE direction AS Enum('Вверх','Вниз', 'Вправо','Влево');

CREATE TABLE Human
(
	human_id int PRIMARY KEY,
	name varchar(50) NOT NULL,
	gender gender NOT NULL
);


CREATE TABLE Place
(
	place_id int PRIMARY KEY,
	name varchar(50) NOT NULL
);


CREATE TABLE Bridges
(
	bridges_id int PRIMARY KEY,
	description varchar(50) NOT NULL,
	size int NOT NULL
);



CREATE TABLE Ples
(
	ples_id int PRIMARY KEY,
	location_period varchar(50) NOT NULL
);


CREATE TABLE River
(
	river_id int PRIMARY KEY,
	description varchar(50) NOT NULL,
	size int NOT NULL,
	form varchar(50) NOT NULL,
	fk_place_id int REFERENCES Place(place_id)
);


CREATE TABLE Flow
( 
	flow_id int PRIMARY KEY,
	speed varchar(50) NOT NULL,
	direction direction NOT NULL,
	fk_river_id int REFERENCES River(river_id)
);


CREATE TABLE Diaspora
(
	diaspora_id int PRIMARY KEY,
	list_of_found_object varchar(50) NOT NULL,
	fk_place_id int REFERENCES Place(place_id)
);


CREATE TABLE Action
(
	name_action text NOT NULL,
	fk_Human_id int REFERENCES Human(human_id),
	fk_river_id int REFERENCES River(river_id),
	CONSTRAINT pk_Action PRIMARY KEY (fk_Human_id,fk_river_id)
); 

DROP TABLE IF EXISTS Action;
CREATE TABLE Action (
   name_action text NOT NULL,
   fk_Human_id int REFERENCES Human(human_id),
   fk_river_id int REFERENCES River(river_id),
   CONSTRAINT pk_Action PRIMARY KEY (fk_Human_id, fk_river_id)
);


CREATE TABLE Lapse
(
	fk_river_id1 int REFERENCES River(river_id),
	fk_river_id2 int REFERENCES River(river_id),
	CONSTRAINT pk_Lapse PRIMARY KEY (fk_river_id1, fk_river_id2)
); 


CREATE TABLE Break
(
	fk_ples_id int REFERENCES Ples(ples_id),
	fk_river_id int REFERENCES River(river_id),
	CONSTRAINT pk_Break PRIMARY KEY (fk_ples_id,fk_river_id)
);


CREATE TABLE Crossing
(
	fk_bridges int REFERENCES Bridges(bridges_id),
	fk_river_id int REFERENCES River(river_id),
	CONSTRAINT pk_Crossing PRIMARY KEY (fk_bridges,fk_river_id)
);

INSERT INTO Human VALUES (1, 'Олвин', 'Мужчина');
INSERT INTO Place VALUES(1, 'Парк');
INSERT INTO Ples VALUES (1,'время от времени');
INSERT INTO Bridges VALUES (2, 'маленькие' , 12);
INSERT INTO River VALUES (1, 'Широкий', 13 'геометрически правильное замкнутое кольцо',1);
INSERT INTO Flow VALUES(1,'Довольно быстрое', 'Вниз', 2);
INSERT INTO Action VALUES ('Не обратил внимания', 1, 2);
INSERT INTO Diaspora VALUES (1, 'Диковенная вещь', 1);
INSERT INTO Lapse VALUES ( 1, 1);
INSERT INTO Break VALUES( 1, 2);
INSERT INTO Crossing VALUES ( 1, 2);

INSERT INTO River VALUES (2, 'Широкий', 12, 'геометрически правильное замкнутое кольцо',1);
INSERT INTO River VALUES (5, 'Широкий', 11, 'геометрически правильное замкнутое кольцо',1);

INSERT INTO Lapse VALUES (3, 2);

DELETE FROM Crossing WHERE fk_bridges = 1;
DELETE FROM Crossing WHERE fk_bridges = 2;
DROP TYPE gender CASCADE;

DROP TYPE direction CASCADE;
DROP TABLE Human CASCADE;
DROP TABLE River CASCADE; 
DROP TABLE Flow CASCADE;
DROP TABLE Diaspora CASCADE;
DROP TABLE Place CASCADE;
DROP TABLE Action CASCADE;
DROP TABLE Lapse CASCADE;
DROP TABLE Ples CASCADE;
DROP TABLE Break CASCADE;
DROP TABlE Bridges CASCADE;
DROP TABlE Crossing CASCADE;

-- SELECT *
-- FROM Human;
-- SELECT *
-- FROM River;
-- SELECT *
-- FROM Flow;
-- SELECT *
-- FROM Action;
-- SELECT *
-- FROM Diaspora;
-- SELECT *
-- FROM Lapse;
-- SELECT *
-- FROM Ples;
-- SELECT *
-- FROM Break;
-- SELECT *
-- FROM Bridges;
-- SELECT *
-- FROM Crossing;
-- SELECT *
-- FROM Place;
SELECT Diaspora.list_of_found_object, Place.name FROM Diaspora join Place on place_id = fk_place_id
WHERE Place.name = 'Лес';
SELECT lapse_id FROM Lapse WHERE fk_river_id1 = fk_river_id2;
