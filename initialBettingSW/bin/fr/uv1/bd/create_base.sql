
-- Drop tables

drop table if exists estcompetiteurde;
drop table if exists estmembrede;
drop table if exists pari;
drop table if exists competition;
drop table if exists personne;

--Drop sequences
drop sequence if exists personne_id_seq; 
drop sequence if exists competition_id_seq; 
drop sequence if exists pari_id_seq; 



-- Create tables
CREATE SEQUENCE personne_id_seq;

CREATE TABLE personne
(
  id_personne integer NOT NULL DEFAULT NEXTVAL('personne_id_seq'),
  prenom character varying(30),
  nom character varying(30),
  borndate date,
  type character varying(40) NOT NULL,
  username character varying(30),
  password character varying(30),
  solde integer,
  CONSTRAINT id_personne PRIMARY KEY (id_personne),
  CONSTRAINT uniqie_username UNIQUE (username),
  CONSTRAINT unique_nom_prenom_borndate UNIQUE (prenom, nom, borndate)
);


CREATE TABLE estmembrede
(
  member_id bigint NOT NULL,
  team_id bigint NOT NULL,
  CONSTRAINT pk_estmembrede PRIMARY KEY (member_id, team_id),
  CONSTRAINT fk_membre FOREIGN KEY (member_id)
      REFERENCES personne (id_personne) MATCH SIMPLE
      ON UPDATE CASCADE ON DELETE CASCADE,
  CONSTRAINT fk_team FOREIGN KEY (team_id)
      REFERENCES personne (id_personne) MATCH SIMPLE
      ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE SEQUENCE competition_id_seq;

CREATE TABLE competition
(
  id_competition integer NOT NULL DEFAULT NEXTVAL('competition_id_seq'),
  nomcompetion character varying(30) NOT NULL,
  closingdate date NOT NULL,
  montanttotalmise bigint NOT NULL DEFAULT 0,
  CONSTRAINT "Competition_pkey" PRIMARY KEY (id_competition),
  CONSTRAINT uniq_nom_de_la_competion UNIQUE (nomcompetion)
);

CREATE TABLE estcompetiteurde
(
  competiteur_id  integer NOT NULL,
  competition_id integer NOT NULL,
  CONSTRAINT pk_estcompetiteurde PRIMARY KEY (competiteur_id, competition_id),
  CONSTRAINT fk_competiteur FOREIGN KEY (competiteur_id)
      REFERENCES personne (id_personne) MATCH SIMPLE
      ON UPDATE CASCADE ON DELETE CASCADE,
  CONSTRAINT fk_competition FOREIGN KEY (competition_id)
      REFERENCES competition (id_competition) MATCH SIMPLE
      ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE SEQUENCE pari_id_seq;

CREATE TABLE pari
(
  id_pari integer NOT NULL DEFAULT NEXTVAL('pari_id_seq'),
  id_joueur integer NOT NULL,
  mise bigint NOT NULL,
  competiteurPremiere_id integer,
  competiteurDeuxieme_id integer,
  competiteurTroisieme_id integer,
  type character varying NOT NULL,
  id_competition integer NOT NULL,
  CONSTRAINT pk_pari PRIMARY KEY (id_pari),
  CONSTRAINT fk_competiteur1 FOREIGN KEY (competiteurPremiere_id)
      REFERENCES personne (id_personne) MATCH SIMPLE
      ON UPDATE CASCADE ON DELETE CASCADE,
  CONSTRAINT fk_competiteur2 FOREIGN KEY (competiteurDeuxieme_id)
      REFERENCES personne (id_personne) MATCH SIMPLE
      ON UPDATE CASCADE ON DELETE CASCADE,
  CONSTRAINT fk_competiteur3 FOREIGN KEY (competiteurTroisieme_id)
      REFERENCES personne (id_personne) MATCH SIMPLE
      ON UPDATE CASCADE ON DELETE CASCADE,
  CONSTRAINT fk_comption FOREIGN KEY (id_competition)
      REFERENCES competition (id_competition) MATCH SIMPLE
      ON UPDATE CASCADE ON DELETE CASCADE,
  CONSTRAINT fk_joueur FOREIGN KEY (id_joueur)
      REFERENCES personne (id_personne) MATCH SIMPLE
      ON UPDATE CASCADE ON DELETE CASCADE
);

-- Sample data
	--4
