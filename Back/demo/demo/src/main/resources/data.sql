insert into db_upp.korisnik (id, ime, prezime, username, password, email, drzava, grad, titula, recenzent, aktivan, tip, odobren_recenzent) 
	values (100, "Olga", "Savic", "olgaSavic",  "$2a$10$bFoT0UWjOFAIQoFRYCIicO2hwNwZy6qhWYq4eXmWqJevf7b2TWpae", "admin@admin.com", "Srbija", "Novi Sad", "student", false, true, "ADMIN", false);
	
insert into db_upp.korisnik (id, ime, prezime, username, password, email, drzava, grad, titula, recenzent, aktivan, tip, odobren_recenzent) 
	values (200, "Nemanja", "Vujovic", "neca1996",  "$2a$10$bFoT0UWjOFAIQoFRYCIicO2hwNwZy6qhWYq4eXmWqJevf7b2TWpae", "vujovicnemanja670@gmail.com", "Srbija", "Novi Sad", "student", false, true, "UREDNIK", false);
insert into db_upp.korisnik (id, ime, prezime, username, password, email, drzava, grad, titula, recenzent, aktivan, tip, odobren_recenzent) 
	values (300, "Pavle", "Trifkovic", "paki1996",  "$2a$10$bFoT0UWjOFAIQoFRYCIicO2hwNwZy6qhWYq4eXmWqJevf7b2TWpae", "paki@urednik.com", "Srbija", "Novi Sad", "student", false, true, "UREDNIK", false);
insert into db_upp.korisnik (id, ime, prezime, username, password, email, drzava, grad, titula, recenzent, aktivan, tip, odobren_recenzent) 
	values (400, "Nikola", "Pavlovic", "pavlovic1996",  "$2a$10$bFoT0UWjOFAIQoFRYCIicO2hwNwZy6qhWYq4eXmWqJevf7b2TWpae", "pavlovic@urednik.com", "Srbija", "Novi Sad", "student", false, true, "UREDNIK", false);

insert into db_upp.korisnik (id, ime, prezime, username, password, email, drzava, grad, titula, recenzent, aktivan, tip, odobren_recenzent) 
	values (500, "Marina", "Gavric", "maki",  "$2a$10$bFoT0UWjOFAIQoFRYCIicO2hwNwZy6qhWYq4eXmWqJevf7b2TWpae", "maki@rec.com", "Srbija", "Novi Sad", "student", true, true, "RECENZENT", true);
insert into db_upp.korisnik (id, ime, prezime, username, password, email, drzava, grad, titula, recenzent, aktivan, tip, odobren_recenzent) 
	values (600, "Dragan", "Borkovac", "borkovac1996",  "$2a$10$bFoT0UWjOFAIQoFRYCIicO2hwNwZy6qhWYq4eXmWqJevf7b2TWpae", "borkovac@rec.com", "Srbija", "Novi Sad", "student", true, true, "RECENZENT", true);
insert into db_upp.korisnik (id, ime, prezime, username, password, email, drzava, grad, titula, recenzent, aktivan, tip, odobren_recenzent) 
	values (700, "Nikola", "Mandic", "manda1996",  "$2a$10$bFoT0UWjOFAIQoFRYCIicO2hwNwZy6qhWYq4eXmWqJevf7b2TWpae", "manda@rec.com", "Srbija", "Novi Sad", "student", true, true, "RECENZENT", true);

insert into db_upp.korisnik (id, ime, prezime, username, password, email, drzava, grad, titula, recenzent, aktivan, tip, odobren_recenzent) 
	values (800, "Olga", "Savicka", "olga1996",  "$2a$10$bFoT0UWjOFAIQoFRYCIicO2hwNwZy6qhWYq4eXmWqJevf7b2TWpae", "lola.savi4@gmail.com", "Srbija", "Novi Sad", "student", false, true, "AUTOR", false);
insert into db_upp.korisnik (id, ime, prezime, username, password, email, drzava, grad, titula, recenzent, aktivan, tip, odobren_recenzent) 
	values (900, "Gandri", "Borkovac", "gandri1996",  "$2a$10$bFoT0UWjOFAIQoFRYCIicO2hwNwZy6qhWYq4eXmWqJevf7b2TWpae", "gandri@autor.com", "Srbija", "Novi Sad", "student", false, true, "AUTOR", false);
insert into db_upp.korisnik (id, ime, prezime, username, password, email, drzava, grad, titula, recenzent, aktivan, tip, odobren_recenzent) 
	values (1000, "Petar", "Savic", "tata1996",  "$2a$10$bFoT0UWjOFAIQoFRYCIicO2hwNwZy6qhWYq4eXmWqJevf7b2TWpae", "tata@autor.com", "Srbija", "Novi Sad", "student", false, true, "AUTOR", false);

	
insert into db_upp.casopis(id, naziv, issn, open_access, aktivan, izabran, glavni_urednik_id)
	values(100, "Nacionalna Geografija", "100", false, true, false, 200);
insert into db_upp.casopis(id, naziv, issn, open_access, aktivan, izabran, glavni_urednik_id)
	values(200, "Politikin zabavnik", "200", false, true, false, 200);
insert into db_upp.casopis(id, naziv, issn, open_access, aktivan, izabran, glavni_urednik_id)
	values(300, "Oko", "300", false, true, false, 200);	
	

insert into db_upp.naucna_oblast_casopis(id, nazivNO)
	values(100, "Geografija");
insert into db_upp.naucna_oblast_casopis(id, nazivNO)
	values(200, "Istorija");
insert into db_upp.naucna_oblast_casopis(id, nazivNO)
	values(300, "Biologija");
insert into db_upp.naucna_oblast_casopis(id, nazivNO)
	values(400, "Ekologija");
insert into db_upp.naucna_oblast_casopis(id, nazivNO)
	values(500, "Knjizevnost");
insert into db_upp.naucna_oblast_casopis(id, nazivNO)
	values(600, "Umetnost");	
	
	
INSERT INTO db_upp.casopis_recenzent VALUES (100, 500), (100, 600), (100, 700);	
INSERT INTO db_upp.casopis_urednik VALUES (100, 300), (100, 400);

INSERT INTO db_upp.casopis_no VALUES (100, 100), (100, 200), (200, 300), (200, 400), (300, 500), (300, 600);	
	
INSERT INTO db_upp.nocas_recenzent VALUES (100, 500), (100, 600), (200, 700);	
INSERT INTO db_upp.nocas_urednik VALUES (100, 300), (200, 400);	
