DROP DATABASE IF EXISTS sieteymedioDB;
CREATE DATABASE sieteymedioDB CHARACTER SET utf8mb4;
USE sieteymedioDB;


CREATE TABLE players (
    id int  PRIMARY KEY AUTO_INCREMENT,
    player_name VARCHAR(40) NOT NULL,
    player_risk tinyint NOT NULL, 
	human boolean NOT NULL
);

create table deck (
	id int primary key AUTO_INCREMENT,
    deck_name varchar(25)
);

create table cardgame (
	id int primary key AUTO_INCREMENT,
    players tinyint not null,
    rounds tinyint not null,
    start_hour datetime not null,
    end_hour datetime,
    deck_id int,
    foreign key (deck_id) references deck(id)
);

create table player_game (
	initial_card_id char(3) not null,
    starting_points tinyint,
    ending_points tinyint,
	cardgame_id int ,
	player_id int,
    foreign key (cardgame_id ) references cardgame(id),
	foreign key (player_id) references players(id) 
);


create table player_game_round (
	round_num tinyint primary key,
    is_bank tinyint(1),
    bet_points tinyint,
    cards_value decimal(4,1),
    starting_round_points tinyint,
    ending_round_points tinyint,
	cardgame_id int,
	player_id int,
    foreign key (cardgame_id) references cardgame(id),
    foreign key (player_id) references players(id)
);

CREATE TABLE card (
  id varchar(3) NOT NULL,
  card_value float(3,1) DEFAULT NULL,
  card_real_value float(2,1) DEFAULT NULL,
  card_name varchar(25) DEFAULT NULL,
  deck_id int NOT NULL,
  foreign key (deck_id) references deck(id)
);
  select * from players;  
  
