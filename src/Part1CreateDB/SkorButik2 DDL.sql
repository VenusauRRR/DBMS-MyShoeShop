drop database if exists skorbutik2;
create database skorbutik2;
use skorbutik2;

create Table Ort(
ID int not null auto_increment primary key,
Ortnummer int not null,
Ortnamn varchar(100) not null
);

create Table Kund(
ID int not null auto_increment primary key,
Förnamn varchar(100) not null,
Efternamn varchar(100) not null,
OrtID int not null,
foreign key (OrtID) references Ort(id)
);

create Table Kategori(
ID int not null auto_increment primary key,
Namn varchar(100) not null
);

create Table Märke(
ID int not null auto_increment primary key,
Namn varchar(100) not null
);

create Table Färg(
ID int not null auto_increment primary key,
Namn varchar(100) not null
);

create Table Storlek(
ID int not null auto_increment primary key,
Size int not null
);

create Table Model(
ID int not null auto_increment primary key,
MärkeID int not null,
FärgID int not null,
Pris int not null,
foreign key (märkeID) references Märke(id) on delete cascade,
foreign key (färgID) references Färg(id) on delete cascade
);

create Table Produkt(
ID int not null auto_increment primary key,
ModelID int not null,
StorlekID int not null,
AntalExample int not null,
foreign key (modelID) references Model(id) on delete cascade,
foreign key (storlekID) references Storlek(id) on delete cascade
);

create Table Kategorization(
ID int not null auto_increment primary key,
ModelID int not null,
KategoriID int not null,
foreign key (ModelID) references Model(id) on delete cascade,
foreign key (KategoriID) references Kategori(id) on delete cascade
);

create Table Kvitto(
ID int not null auto_increment primary key,
kundID int not null,
Created timestamp default current_timestamp,
foreign key (kundID) references Kund(id) on delete set null
);

create Table Beställning(
ID int not null auto_increment primary key,
KvittoID int not null,
ProduktID int not null,
BeställAntal int not null,
foreign key (KvittoID) references Kvitto(id) on delete cascade,
foreign key (ProduktID) references Produkt(id) on delete set null
);

INSERT INTO `skorbutik2`.`Ort` (`Ortnummer`, `Ortnamn`) VALUES ('20110', 'Malmö');
INSERT INTO `skorbutik2`.`Ort` (`Ortnummer`, `Ortnamn`) VALUES ('31100', 'Falkenberg');
INSERT INTO `skorbutik2`.`Ort` (`Ortnummer`, `Ortnamn`) VALUES ('10004', 'Stockholm');
INSERT INTO `skorbutik2`.`Ort` (`Ortnummer`, `Ortnamn`) VALUES ('16103', 'Bromma');
INSERT INTO `skorbutik2`.`Ort` (`Ortnummer`, `Ortnamn`) VALUES ('36544', 'Kosta');

INSERT INTO `skorbutik2`.`Kund` (`Förnamn`, `Efternamn`, `OrtID`) VALUES ('Peter', 'Peterson', '1');
INSERT INTO `skorbutik2`.`Kund` (`Förnamn`, `Efternamn`, `OrtID`) VALUES ('Daniel', 'Danielson', '2');
INSERT INTO `skorbutik2`.`Kund` (`Förnamn`, `Efternamn`, `OrtID`) VALUES ('Mary', 'Rolin', '3');
INSERT INTO `skorbutik2`.`Kund` (`Förnamn`, `Efternamn`, `OrtID`) VALUES ('Joy', 'Wang', '4');
INSERT INTO `skorbutik2`.`Kund` (`Förnamn`, `Efternamn`, `OrtID`) VALUES ('Kathy', 'Chan', '5');

INSERT INTO `skorbutik2`.`Kategori` (`Namn`) VALUES ('Sport');
INSERT INTO `skorbutik2`.`Kategori` (`Namn`) VALUES ('Dam');
INSERT INTO `skorbutik2`.`Kategori` (`Namn`) VALUES ('Herr');
INSERT INTO `skorbutik2`.`Kategori` (`Namn`) VALUES ('Promenad');
INSERT INTO `skorbutik2`.`Kategori` (`Namn`) VALUES ('Barn');
INSERT INTO `skorbutik2`.`Kategori` (`Namn`) VALUES ('Sandal');

INSERT INTO `skorbutik2`.`Märke` (`Namn`) VALUES ('Nike');
INSERT INTO `skorbutik2`.`Märke` (`Namn`) VALUES ('Addidas');
INSERT INTO `skorbutik2`.`Märke` (`Namn`) VALUES ('New Balance');
INSERT INTO `skorbutik2`.`Märke` (`Namn`) VALUES ('Ecco');
INSERT INTO `skorbutik2`.`Märke` (`Namn`) VALUES ('Timerland');

INSERT INTO `skorbutik2`.`Färg` (`Namn`) VALUES ('vit');
INSERT INTO `skorbutik2`.`Färg` (`Namn`) VALUES ('grön');
INSERT INTO `skorbutik2`.`Färg` (`Namn`) VALUES ('gul');
INSERT INTO `skorbutik2`.`Färg` (`Namn`) VALUES ('blå');
INSERT INTO `skorbutik2`.`Färg` (`Namn`) VALUES ('svart');

INSERT INTO `skorbutik2`.`Storlek` (`Size`) VALUES ('21');
INSERT INTO `skorbutik2`.`Storlek` (`Size`) VALUES ('22');
INSERT INTO `skorbutik2`.`Storlek` (`Size`) VALUES ('23');
INSERT INTO `skorbutik2`.`Storlek` (`Size`) VALUES ('24');
INSERT INTO `skorbutik2`.`Storlek` (`Size`) VALUES ('25');
INSERT INTO `skorbutik2`.`Storlek` (`Size`) VALUES ('26');
INSERT INTO `skorbutik2`.`Storlek` (`Size`) VALUES ('27');
INSERT INTO `skorbutik2`.`Storlek` (`Size`) VALUES ('28');
INSERT INTO `skorbutik2`.`Storlek` (`Size`) VALUES ('29');
INSERT INTO `skorbutik2`.`Storlek` (`Size`) VALUES ('30');
INSERT INTO `skorbutik2`.`Storlek` (`Size`) VALUES ('31');
INSERT INTO `skorbutik2`.`Storlek` (`Size`) VALUES ('32');
INSERT INTO `skorbutik2`.`Storlek` (`Size`) VALUES ('33');
INSERT INTO `skorbutik2`.`Storlek` (`Size`) VALUES ('34');
INSERT INTO `skorbutik2`.`Storlek` (`Size`) VALUES ('35');
INSERT INTO `skorbutik2`.`Storlek` (`Size`) VALUES ('36');
INSERT INTO `skorbutik2`.`Storlek` (`Size`) VALUES ('37');
INSERT INTO `skorbutik2`.`Storlek` (`Size`) VALUES ('38');
INSERT INTO `skorbutik2`.`Storlek` (`Size`) VALUES ('39');
INSERT INTO `skorbutik2`.`Storlek` (`Size`) VALUES ('40');
INSERT INTO `skorbutik2`.`Storlek` (`Size`) VALUES ('41');
INSERT INTO `skorbutik2`.`Storlek` (`Size`) VALUES ('42');
INSERT INTO `skorbutik2`.`Storlek` (`Size`) VALUES ('43');
INSERT INTO `skorbutik2`.`Storlek` (`Size`) VALUES ('44');

INSERT INTO `skorbutik2`.`Model` (`MärkeID`, `FärgID`, `Pris`) VALUES ('1', '1', '800');
INSERT INTO `skorbutik2`.`Model` (`MärkeID`, `FärgID`, `Pris`) VALUES ('2', '1', '850');
INSERT INTO `skorbutik2`.`Model` (`MärkeID`, `FärgID`, `Pris`) VALUES ('3', '3', '300');
INSERT INTO `skorbutik2`.`Model` (`MärkeID`, `FärgID`, `Pris`) VALUES ('3', '4', '500');
INSERT INTO `skorbutik2`.`Model` (`MärkeID`, `FärgID`, `Pris`) VALUES ('4', '3', '350');
INSERT INTO `skorbutik2`.`Model` (`MärkeID`, `FärgID`, `Pris`) VALUES ('4', '4', '550');

INSERT INTO `skorbutik2`.`Produkt` (`ModelID`, `StorlekID`, `AntalExample`) VALUES ('1', '5', '12');
INSERT INTO `skorbutik2`.`Produkt` (`ModelID`, `StorlekID`, `AntalExample`) VALUES ('1', '12', '32');
INSERT INTO `skorbutik2`.`Produkt` (`ModelID`, `StorlekID`, `AntalExample`) VALUES ('2', '20', '10');
INSERT INTO `skorbutik2`.`Produkt` (`ModelID`, `StorlekID`, `AntalExample`) VALUES ('3', '13', '3');
INSERT INTO `skorbutik2`.`Produkt` (`ModelID`, `StorlekID`, `AntalExample`) VALUES ('4', '14', '4');
INSERT INTO `skorbutik2`.`Produkt` (`ModelID`, `StorlekID`, `AntalExample`) VALUES ('5', '2', '21');
INSERT INTO `skorbutik2`.`Produkt` (`ModelID`, `StorlekID`, `AntalExample`) VALUES ('6', '1', '3');
INSERT INTO `skorbutik2`.`Produkt` (`ModelID`, `StorlekID`, `AntalExample`) VALUES ('6', '2', '6');
INSERT INTO `skorbutik2`.`Produkt` (`ModelID`, `StorlekID`, `AntalExample`) VALUES ('6', '18', '18');

INSERT INTO `skorbutik2`.`Kategorization` (`ModelID`, `KategoriID`) VALUES ('1', '1');
INSERT INTO `skorbutik2`.`Kategorization` (`ModelID`, `KategoriID`) VALUES ('1', '2');
INSERT INTO `skorbutik2`.`Kategorization` (`ModelID`, `KategoriID`) VALUES ('2', '3');
INSERT INTO `skorbutik2`.`Kategorization` (`ModelID`, `KategoriID`) VALUES ('3', '1');
INSERT INTO `skorbutik2`.`Kategorization` (`ModelID`, `KategoriID`) VALUES ('5', '2');
INSERT INTO `skorbutik2`.`Kategorization` (`ModelID`, `KategoriID`) VALUES ('6', '3');
INSERT INTO `skorbutik2`.`Kategorization` (`ModelID`, `KategoriID`) VALUES ('5', '5');
INSERT INTO `skorbutik2`.`Kategorization` (`ModelID`, `KategoriID`) VALUES ('6', '6');
INSERT INTO `skorbutik2`.`Kategorization` (`ModelID`, `KategoriID`) VALUES ('4', '3');

INSERT INTO `skorbutik2`.`Kvitto` (`kundID`) VALUES ('1');
INSERT INTO `skorbutik2`.`Kvitto` (`kundID`) VALUES ('2');
INSERT INTO `skorbutik2`.`Kvitto` (`kundID`) VALUES ('3');
INSERT INTO `skorbutik2`.`Kvitto` (`kundID`) VALUES ('4');
INSERT INTO `skorbutik2`.`Kvitto` (`kundID`) VALUES ('1');
INSERT INTO `skorbutik2`.`Kvitto` (`kundID`) VALUES ('5');

UPDATE `skorbutik2`.`Kvitto` SET `created` = '2023-12-05 10:10:10' WHERE (`ID` = '6');
UPDATE `skorbutik2`.`Kvitto` SET `created` = '2019-09-02 10:10:10' WHERE (`ID` = '5');
UPDATE `skorbutik2`.`Kvitto` SET `created` = '2023-07-23 10:10:10' WHERE (`ID` = '4');
UPDATE `skorbutik2`.`Kvitto` SET `created` = '2023-07-21 10:10:10' WHERE (`ID` = '2');
UPDATE `skorbutik2`.`Kvitto` SET `created` = '2022-03-06 22:19:18' WHERE (`ID` = '1');

INSERT INTO `skorbutik2`.`Beställning` (`KvittoID`, `ProduktID`, `BeställAntal`) VALUES ('1', '1', '2');
INSERT INTO `skorbutik2`.`Beställning` (`KvittoID`, `ProduktID`, `BeställAntal`) VALUES ('1', '5', '1');
INSERT INTO `skorbutik2`.`Beställning` (`KvittoID`, `ProduktID`, `BeställAntal`) VALUES ('2', '6', '3');
INSERT INTO `skorbutik2`.`Beställning` (`KvittoID`, `ProduktID`, `BeställAntal`) VALUES ('3', '3', '2');
INSERT INTO `skorbutik2`.`Beställning` (`KvittoID`, `ProduktID`, `BeställAntal`) VALUES ('4', '4', '1');
INSERT INTO `skorbutik2`.`Beställning` (`KvittoID`, `ProduktID`, `BeställAntal`) VALUES ('5', '9', '2');
INSERT INTO `skorbutik2`.`Beställning` (`KvittoID`, `ProduktID`, `BeställAntal`) VALUES ('6', '5', '2');

select * from Kund;
select * from Ort;
select * from Kategori;
select * from Märke;
select * from Färg;
select * from Storlek;
select * from Model;
select * from Produkt;
select * from Kategorization;
select * from Beställning;
select * from Kvitto;