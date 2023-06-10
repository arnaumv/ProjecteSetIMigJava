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
	id char(3) primary key,
    deck_name varchar(25)
);

create table cardgame (
	id int primary key AUTO_INCREMENT,
    players tinyint not null,
    rounds tinyint not null,
    start_hour datetime not null,
    end_hour datetime not null,
    deck_id char(3),
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
  id char(3) NOT NULL,
  card_value float(3,1) DEFAULT NULL,
  card_real_value float(2,1) DEFAULT NULL, 
  deck_id char(3) NOT NULL,
  card_name varchar(25) DEFAULT NULL,
 
  foreign key (deck_id) references deck(id)
);


CREATE TABLE card (
  id char(3) NOT NULL,
  card_value float(3,1) DEFAULT NULL,
  card_priority tinyint DEFAULT NULL,
  card_real_value float(2,1) DEFAULT NULL,
  deck_id char(3) DEFAULT NULL,
  card_name varchar(25) DEFAULT NULL);
  /* ME FALLA AL HACER LA FOREIGN KEY SIGUIENTE
  
  alter table card
 add foreign key (id) references deck(id);
  
  */
    
SELECT * FROM CARD;

INSERT INTO `deck` VALUES ('1','Spanish deck'),('2','Poker deck');
INSERT INTO card VALUES ('B01',1.0,1,1.0,'1','Ace of Clubs'),('B02',2.0,1,2.0,'1','Two of Clubs'),('B03',3.0,1,3.0,'1','Three of Clubs'),('B04',4.0,1,4.0,'1','Four of Clubs'),('B05',5.0,1,5.0,'1','Five of Clubs'),('B06',6.0,1,6.0,'1','Six of Clubs'),('B07',7.0,1,7.0,'1','Seven of Clubs'),('B08',8.0,1,0.5,'1','Eight of Clubs'),('B09',9.0,1,0.5,'1','Nine of Clubs'),('B10',10.0,1,0.5,'1','Jack of Clubs'),('B11',11.0,1,0.5,'1','Horse of Clubs'),('B12',12.0,1,0.5,'1','King of Clubs'),('C01',1.0,3,1.0,'1','Ace of Cups'),('C02',2.0,3,2.0,'1','Two of Cups'),('C03',3.0,3,3.0,'1','Three of Cups'),('C04',4.0,3,4.0,'1','Four of Cups'),('C05',5.0,3,5.0,'1','Five of Cups'),('C06',6.0,3,6.0,'1','Six of Cups'),('C07',7.0,3,7.0,'1','Seven of Cups'),('C08',8.0,3,0.5,'1','Eight of Cups'),('C09',9.0,3,0.5,'1','Nine of Cups'),('C10',10.0,3,0.5,'1','Jack of Cups'),('C11',11.0,3,0.5,'1','Horse of Cups'),('C12',12.0,3,0.5,'1','King of Cups'),('D01',1.0,1,1.0,'2','Ace of Diamond'),('D02',2.0,1,2.0,'2','Two of Diamond'),('D03',3.0,1,3.0,'2','Three of Diamond'),('D04',4.0,1,4.0,'2','Four of Diamond'),('D05',5.0,1,5.0,'2','Five of Diamond'),('D06',6.0,1,6.0,'2','Six of Diamond'),('D07',7.0,1,7.0,'2','Seven of Diamond'),('D08',8.0,1,0.5,'2','Eight of Diamond'),('D09',9.0,1,0.5,'2','Nine of Diamond'),('D10',10.0,1,0.5,'2','Ten of Diamond'),('D11',11.0,1,0.5,'2','Jack of Diamond'),('D12',12.0,1,0.5,'2','Queen of Diamond'),('D13',13.0,1,0.5,'2','King of Diamond'),('G01',1.0,4,1.0,'1','Ace of Coins'),('G02',2.0,4,2.0,'1','Two of Coins'),('G03',3.0,4,3.0,'1','Three of Coins'),('G04',4.0,4,4.0,'1','Four of Coins'),('G05',5.0,4,5.0,'1','Five of Coins'),('G06',6.0,4,6.0,'1','Six of Coins'),('G07',7.0,4,7.0,'1','Seven of Coins'),('G08',8.0,4,0.5,'1','Eight of Coins'),('G09',9.0,4,0.5,'1','Nine of Coins'),('G10',10.0,4,0.5,'1','Jack of Coins'),('G11',11.0,4,0.5,'1','Horse of Coins'),('G12',12.0,4,0.5,'1','King of Coins'),('H01',1.0,2,1.0,'2','Ace of Hearts'),('H02',2.0,2,2.0,'2','Two of Hearts'),('H03',3.0,2,3.0,'2','Three of Hearts'),('H04',4.0,2,4.0,'2','Four of Hearts'),('H05',5.0,2,5.0,'2','Five of Hearts'),('H06',6.0,2,6.0,'2','Six of Hearts'),('H07',7.0,2,7.0,'2','Seven of Hearts'),('H08',8.0,2,0.5,'2','Eight of Hearts'),('H09',9.0,2,0.5,'2','Nine of Hearts'),('H10',10.0,2,0.5,'2','Ten of Hearts'),('H11',11.0,2,0.5,'2','Jack of Hearts'),('H12',12.0,2,0.5,'2','Queen of Hearts'),('H13',13.0,2,0.5,'2','King of Hearts'),('P01',1.0,3,1.0,'2','Ace of Spades'),('P02',2.0,3,2.0,'2','Two of Spades'),('P03',3.0,3,3.0,'2','Three of Spades'),('P04',4.0,3,4.0,'2','Four of Spades'),('P05',5.0,3,5.0,'2','Five of Spades'),('P06',6.0,3,6.0,'2','Six of Spades'),('P07',7.0,3,7.0,'2','Seven of Spades'),('P08',8.0,3,0.5,'2','Eight of Spades'),('P09',9.0,3,0.5,'2','Nine of Spades'),('P10',10.0,3,0.5,'2','Ten of Spades'),('P11',11.0,3,0.5,'2','Jack of Spades'),('P12',12.0,3,0.5,'2','Queen of Spades'),('P13',13.0,3,0.5,'2','King of Spades'),('S01',1.0,2,1.0,'1','Ace of Swords'),('S02',2.0,2,2.0,'1','Two of Swords'),('S03',3.0,2,3.0,'1','Three of Swords'),('S04',4.0,2,4.0,'1','Four of Swords'),('S05',5.0,2,5.0,'1','Five of Swords'),('S06',6.0,2,6.0,'1','Six of Swords'),('S07',7.0,2,7.0,'1','Seven of Swords'),('S08',8.0,2,0.5,'1','Eight of Swords'),('S09',9.0,2,0.5,'1','Nine of Swords'),('S10',10.0,2,0.5,'1','Jack of Swords'),('S11',11.0,2,0.5,'1','Horse of Swords'),('S12',12.0,2,0.5,'1','King of Swords'),('T01',1.0,4,1.0,'2','Ace of Clubs'),('T02',2.0,4,2.0,'2','Two of Clubs'),('T04',4.0,4,4.0,'2','Four of Clubs'),('T05',5.0,4,5.0,'2','Five of Clubs'),('T06',6.0,4,6.0,'2','Six of Clubs'),('T07',7.0,4,7.0,'2','Seven of Clubs'),('T08',8.0,4,0.5,'2','Eight of Clubs'),('T09',9.0,4,0.5,'2','Nine of Clubs'),('T10',10.0,4,0.5,'2','Ten of Clubs'),('T11',11.0,4,0.5,'2','Jack of Clubs'),('T12',12.0,4,0.5,'2','Queen of Clubs'),('T13',13.0,4,0.5,'2','King of Clubs')
