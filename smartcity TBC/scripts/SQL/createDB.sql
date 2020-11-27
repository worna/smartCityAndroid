
DROP TABLE IF EXISTS customer CASCADE;
CREATE TABLE customer (
    id integer PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    firstName varchar,
    lastName varchar,
    birthDate date,
    gender integer,
    phoneNumber varchar,
    email varchar,
    password varchar,
    inscriptionDate date,
    isManager integer ,
    isInstructor integer ,
    language varchar
);
INSERT INTO customer (firstName, lastName, birthDate, gender, phoneNumber, email, password, inscriptionDate, isManager, isInstructor, "language") VALUES
('Vicky','Zagorski','2000-09-14',1,'0474235689','etu1234@henallux.be','motdepasse', '2020-11-25', 1, 0, 'french'),
('Arnaud','Lockman','2001-02-07',0,'0474025605','etu40153@henallux.be','password', '2020-11-10', 0, 1, 'english');

DROP TABLE IF EXISTS sportHall CASCADE;
CREATE TABLE sportHall (
    id integer PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name varchar,
    manager integer,
    phoneNumber varchar,
    email varchar
);

INSERT INTO sportHall (name, manager, phoneNumber, email) VALUES
('Jims', 1, '0123456789', 'lodsqz@udi.com'),
('Basic fit', 2, '7894561230', 'mail@hotmail.com'),
('aaaa', 1, '145236987', '12344598');

DROP TABLE IF EXISTS sportHallCustomer CASCADE;
CREATE TABLE sportHallCustomer (
    id_sportHall integer REFERENCES sportHall(id) DEFERRABLE INITIALLY IMMEDIATE,
    id_customer integer REFERENCES customer(id) DEFERRABLE INITIALLY IMMEDIATE,
    PRIMARY KEY(id_sportHall, id_customer)
);

DROP TABLE IF EXISTS course CASCADE;
CREATE TABLE course (
    id SERIAL,
    id_sportHall integer REFERENCES sportHall(id) DEFERRABLE INITIALLY IMMEDIATE,
    startingDateTime DATE,
    endingDateTime Date,
    level VARCHAR,
    activity VARCHAR,
    room VARCHAR,
    id_instructor integer REFERENCES customer(id) DEFERRABLE INITIALLY IMMEDIATE ,
    PRIMARY KEY(id_sportHall, id)
);
INSERT INTO course (id_sportHall, startingDateTime, endingDateTime, level, activity, room, id_instructor) VALUES
    (1, '2020-11-24', '2020-11-24', 'A voir si on a des problèmes', 'Développement de client Web avancé', 'A distance', 2),
    (1, '2020-11-23', '2020-11-24', 'Facile', 'HIP HOP', 'salle bleue', 2),
    (3, '2020-11-24', '2020-11-24', 'Enfant (10-14)', 'Break', 'salle n°2', 1);
