

CREATE TABLE IF NOT EXISTS artists (
  id int(11) NOT NULL AUTO_INCREMENT,
  name varchar(30) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY name (name)
);

CREATE TABLE IF NOT EXISTS clients (
  id int(11) NOT NULL AUTO_INCREMENT,
  username varchar(255) NOT NULL,
  mail varchar(255) NOT NULL,
  password varchar(255) NOT NULL,
  first_name varchar(255) NOT NULL,
  last_name varchar(255) NOT NULL,
  admin int(11) NOT NULL DEFAULT '0',
  created datetime NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS concerts (
  id int(11) NOT NULL AUTO_INCREMENT,
  start_datetime datetime NOT NULL,
  end_datetime datetime NOT NULL,
  location varchar(50) NOT NULL,
  image varchar(50) NOT NULL,
  id_tarif int(11) NOT NULL,
  nb_seats int(11) NOT NULL,
  full tinyint(1) NOT NULL,
  id_creator int(11) NOT NULL,
  name_concert varchar(255) NOT NULL,
  online tinyint(1) NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS styles (
  id int(11) NOT NULL AUTO_INCREMENT,
  name varchar(30) NOT NULL,
  PRIMARY KEY (id)
); 



CREATE TABLE IF NOT EXISTS tarifs (
  id int(11) NOT NULL AUTO_INCREMENT,
  label varchar(30) NOT NULL,
  price double NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS assoc_tarifs (
  id int(11) NOT NULL AUTO_INCREMENT,
  id_tarif int(11) NOT NULL,
  id_concert int(11) NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (id_tarif)
	REFERENCES tarifs(id),
	FOREIGN KEY (id_concert)
		REFERENCES concerts (id) ON DELETE CASCADE
		
); 


CREATE TABLE IF NOT EXISTS assoc_styles (
  id int(11) NOT NULL AUTO_INCREMENT,
  id_style int(11) NOT NULL,
  id_concert int(11) NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (id_style)
	REFERENCES styles(id),
	FOREIGN KEY (id_concert)
		REFERENCES concerts (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS assoc_artists (
  id int(11) NOT NULL AUTO_INCREMENT,
  id_artist int(11) NOT NULL,
  id_concert int(11) NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (id_artist)
	REFERENCES artists(id),
	FOREIGN KEY (id_concert)
		REFERENCES concerts (id) ON DELETE CASCADE
);


CREATE TABLE IF NOT EXISTS reservations (
  id int(11) NOT NULL AUTO_INCREMENT,
  id_client int(11) NOT NULL,
  id_concert int(11) NOT NULL,
  id_tarif int (11)  NOT NULL,
  scan int(1) NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (id_client)
	REFERENCES clients(id),
	FOREIGN KEY (id_tarif)
	REFERENCES tarifs(id),
	FOREIGN KEY (id_concert)
		REFERENCES concerts (id) ON DELETE CASCADE
)












