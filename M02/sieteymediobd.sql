DROP DATABASE IF EXISTS sieteymedioDB;
CREATE DATABASE sieteymedioDB CHARACTER SET utf8mb4;
USE sieteymedioDB;


CREATE TABLE jugadores (
    id_jugador VARCHAR(25) PRIMARY KEY,
    player_name VARCHAR(40) NOT NULL,
    player_risk tinyint NOT NULL, 
	human tinyint NOT NULL
);


create table player_game (
	cardgame_id int ,
    id_jugador varchar(25),
	initial_card_id char(3) not null,
    starting_points tinyint,
    ending_points tinyint,
    foreign key (cardgame_id ) references cardgame(cardgame_id),
	foreign key (id_jugador) references jugadores(id_jugador) );

create table cardgame (
	cardgame_id int primary key,
    players tinyint not null,
    rounds tinyint not null,
    start_hour datetime not null,
    end_hour datetime not null,
    deck_id char(3),
    foreign key (deck_id) references deck(deck_id));

create table player_game_round (
	cardgame_id int,
	round_num tinyint primary key,
    player_id varchar(25),
    is_bank tinyint(1),
    bet_points tinyint,
    cards_value decimal(4,1),
    starting_round_points tinyint,
    ending_round_points tinyint,
    foreign key (cardgame_id) references cardgame(cardgame_id),
    foreign key (id_jugador) references jugadores(id_jugador));

create table deck (
	deck_id char(3) primary key,
    deck_name varchar(25));

CREATE TABLE card (
  card_id char(3) NOT NULL,
  card_value float(3,1) DEFAULT NULL,
  card_priority tinyint DEFAULT NULL,
  card_real_value float(2,1) DEFAULT NULL,
  deck_id char(3) DEFAULT NULL,
  card_name varchar(25) DEFAULT NULL;
    foreign key (deck_id) references deck(deck_id));
    
