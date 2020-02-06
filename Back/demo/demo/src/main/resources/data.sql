insert into db_upp.korisnik (id, ime, prezime, username, password, email, drzava, grad, titula, recenzent, aktivan, tip, odobren_recenzent) 
	values (1, "Olga", "Savic", "olgaSavic",  "$2a$10$bFoT0UWjOFAIQoFRYCIicO2hwNwZy6qhWYq4eXmWqJevf7b2TWpae", "admin@admin.com", "Srbija", "Novi Sad", "student", false, true, "ADMIN", false);
insert into db_upp.korisnik (id, ime, prezime, username, password, email, drzava, grad, titula, recenzent, aktivan, tip, odobren_recenzent) 
	values (2, "Nemanja", "Vujovic", "neca1996",  "$2a$10$bFoT0UWjOFAIQoFRYCIicO2hwNwZy6qhWYq4eXmWqJevf7b2TWpae", "neca@urednik.com", "Srbija", "Novi Sad", "student", false, true, "UREDNIK", false);
insert into db_upp.korisnik (id, ime, prezime, username, password, email, drzava, grad, titula, recenzent, aktivan, tip, odobren_recenzent) 
	values (3, "Pavle", "Trifkovic", "paki1996",  "$2a$10$bFoT0UWjOFAIQoFRYCIicO2hwNwZy6qhWYq4eXmWqJevf7b2TWpae", "paki@urednik.com", "Srbija", "Novi Sad", "student", false, true, "UREDNIK", false);
insert into db_upp.korisnik (id, ime, prezime, username, password, email, drzava, grad, titula, recenzent, aktivan, tip, odobren_recenzent) 
	values (4, "Nikola", "Pavlovic", "pavlovic1996",  "$2a$10$bFoT0UWjOFAIQoFRYCIicO2hwNwZy6qhWYq4eXmWqJevf7b2TWpae", "pavlovic@urednik.com", "Srbija", "Novi Sad", "student", false, true, "UREDNIK", false);
insert into db_upp.korisnik (id, ime, prezime, username, password, email, drzava, grad, titula, recenzent, aktivan, tip, odobren_recenzent) 
	values (5, "Marina", "Gavric", "maki",  "$2a$10$bFoT0UWjOFAIQoFRYCIicO2hwNwZy6qhWYq4eXmWqJevf7b2TWpae", "maki@rec.com", "Srbija", "Novi Sad", "student", true, true, "RECENZENT", true);
insert into db_upp.korisnik (id, ime, prezime, username, password, email, drzava, grad, titula, recenzent, aktivan, tip, odobren_recenzent) 
	values (6, "Dragan", "Borkovac", "borkovac1996",  "$2a$10$bFoT0UWjOFAIQoFRYCIicO2hwNwZy6qhWYq4eXmWqJevf7b2TWpae", "borkovac@rec.com", "Srbija", "Novi Sad", "student", true, true, "RECENZENT", true);
insert into db_upp.korisnik (id, ime, prezime, username, password, email, drzava, grad, titula, recenzent, aktivan, tip, odobren_recenzent) 
	values (7, "Nikola", "Mandic", "manda1996",  "$2a$10$bFoT0UWjOFAIQoFRYCIicO2hwNwZy6qhWYq4eXmWqJevf7b2TWpae", "manda@rec.com", "Srbija", "Novi Sad", "student", true, true, "RECENZENT", true);
insert into db_upp.korisnik (id, ime, prezime, username, password, email, drzava, grad, titula, recenzent, aktivan, tip, odobren_recenzent) 
	values (8, "Olga", "Savicka", "olga1996",  "$2a$10$bFoT0UWjOFAIQoFRYCIicO2hwNwZy6qhWYq4eXmWqJevf7b2TWpae", "olga@autor.com", "Srbija", "Novi Sad", "student", false, true, "AUTOR", false);
insert into db_upp.korisnik (id, ime, prezime, username, password, email, drzava, grad, titula, recenzent, aktivan, tip, odobren_recenzent) 
	values (9, "Gandri", "Borkovac", "gandri1996",  "$2a$10$bFoT0UWjOFAIQoFRYCIicO2hwNwZy6qhWYq4eXmWqJevf7b2TWpae", "gandri@autor.com", "Srbija", "Novi Sad", "student", false, true, "AUTOR", false);
insert into db_upp.korisnik (id, ime, prezime, username, password, email, drzava, grad, titula, recenzent, aktivan, tip, odobren_recenzent) 
	values (10, "Petar", "Savic", "tata1996",  "$2a$10$bFoT0UWjOFAIQoFRYCIicO2hwNwZy6qhWYq4eXmWqJevf7b2TWpae", "tata@autor.com", "Srbija", "Novi Sad", "student", false, true, "AUTOR", false);

	
insert into db_upp.casopis(id, naziv, issn, open_access, aktivan, izabran)
	values(100, "Nacionalna Geografija", "100", true, true, false);
insert into db_upp.casopis(id, naziv, issn, open_access, aktivan, izabran)
	values(200, "Politikin zabavnik", "200", true, true, false);
insert into db_upp.casopis(id, naziv, issn, open_access, aktivan, izabran)
	values(300, "Oko", "300", true, true, false);	