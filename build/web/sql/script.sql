/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  PC
 * Created: 22 févr. 2024
 */

create database cannabis;

create sequence seq_utilisateur;
create table utilisateur(
    utilisateur_id VARCHAR(20) DEFAULT concat('USER' || nextval('seq_utilisateur')) PRIMARY KEY,
    nom VARCHAR(100) NOT NULL, 
    prenom VARCHAR(100) NOT NULL,
    pseudo VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) NOT NULL,
    password VARCHAR(255) NOT NULL
);

select utilisateur_id from utilisateur where pseudo = ?;
SELECT * FROM utilisateur where utilisateur_id = ?;
INSERT INTO utilisateur (nom,prenom,pseudo,email,password) VALUES (?;?,?,?,?);
UPDATE utilisateur SET nom = ?, prenom = ?, pseudo = ?, email = ? WHERE utilisateur_id = ?;
DELETE FROM utilisateur WHERE utilisateur_id = ?;

create sequence seq_salleCulture;
create table salleCulture(
    salleCulture_id VARCHAR(20) DEFAULT concat('SC' || nextval('seq_salleCulture')) PRIMARY KEY,
    nom_salle VARCHAR(255) NOT NULL,
    temperature INT,
    humidite INT
);

create sequence seq_plante;
create table plante (
    plante_id VARCHAR(20) DEFAULT concat('PL' || nextval('seq_plante')) PRIMARY KEY,
    espece VARCHAR(255) NOT NULL,
    variete VARCHAR(255) NOT NULL,
    salleCulture_id VARCHAR(20) REFERENCES salleCulture(salleCulture_id)
);

create sequence seq_journalCulture;
create table journalCulture (
    journalCulture_id VARCHAR(20) DEFAULT concat('JC' || nextval('seq_journalCulture')) PRIMARY KEY,
    plante_id VARCHAR(20) REFERENCES plante(plante_id),
    date DATE NOT NULL,
    etapeCroissance VARCHAR(255) NOT NULL, 
    notes TEXT
);

create sequence seq_recolte;
create table recolte (
    recolte_id VARCHAR(20) DEFAULT concat('RC' || nextval('seq_recolte')) PRIMARY KEY
    plante_id VARCHAR(20) REFERENCES plante(plante_id),
    date DATE NOT NULL,
    rendement INT NOT NULL, 
    qualite VARCHAR(255) NOT NULL
);