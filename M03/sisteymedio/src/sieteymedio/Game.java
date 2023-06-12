package sieteymedio;

import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import BO.CardBO;
import BO.PlayerBO;
import conexion.DatabaseConnection;

public class Game {
	
	float totalPlayer[] = new float[4];
	JFrame gameWindow;
	List<PlayerBO> playerList;
	JTextArea textArea;
	
	public void print(String msg) {
		print(msg, Color.BLACK, null);
	}
	
	public void print(String msg , Color color) {
		print(msg, color, null);
	}
	
	public void print(String msg, Color color, String font) {
		
		Font msgFont = null;
		if(font==null) {
			msgFont = new Font("Arial", Font.PLAIN, 14);
		} else if (font.equals("TITULAR")) {
			msgFont = new Font("Arialt", Font.BOLD, 18);
		}
		
		textArea.append(msg + "\n");
		
		/*
		textArea.setFont(msgFont);
		textArea.setForeground(Color.green);
		*/
	}
	
	public void startGame(HashMap<String, Object> gameSettings){
		
		// start Game
		Timestamp startGame =  new Timestamp(System.currentTimeMillis());
		int cardgame_id = saveCardGame(startGame);
		
		
		int numRounds = (int) gameSettings.get("maxRounds");
		int numPlayers = (int) gameSettings.get("gamePlayers");
		
		
        // Crear una instancia de la ventana del juego
        gameWindow = new JFrame("Game: " + numPlayers + " Players");
        gameWindow.setSize(500, 800);
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        gameWindow.add(scrollPane);

        // Mostrar la ventana del juego
        gameWindow.setVisible(true);
        

        // AQUI ES DONDE SE DEBERIA DE IMPLEMENTAR EL DESARROLLO DEL JUEGO  EN LA VENTANA "MI JUEGO "  
		// 1.1 number of players
		Player player = new Player();
		HashMap players = (HashMap) player.getPlayers(numPlayers);
		
		
		print("Nº Jugadores: " + players.size());
		print("Nº Rondas: " + numRounds);
		print("");
		
		// Get the players with priority
		playerList = initGame(players, cardgame_id);
		playerList.get(playerList.size()-1).setIsBank(1);
		
		
        int i = 1;
        Points points = new Points();
        points.setTextArea(textArea);
        while(numRounds>0) {
        	gameWindow.setTitle( " Ronda - " + i);
        	print("");
        	print("");
        	print("Round: " + i);
        	print("");
        	System.out.println("Round: " + i);
        	i=i+1;
        	playerList = cardRound(gameSettings, playerList);
        	// llamar método repartir puntos
        	numRounds=numRounds-1;
        	playerList = points.distributePoints(playerList, textArea);
        	
        	for (PlayerBO playerBO : playerList) {
        		update_player_game(cardgame_id, playerBO);
			}
        	
        }
        
		print("");
		print("Game Over");
		print("");
		
		
		Timestamp endGame =  new Timestamp(System.currentTimeMillis());
		updateCardGame(cardgame_id, endGame);
	}
	
	
	
	/*
	 * Play one round
	 */
	public List<PlayerBO> cardRound(HashMap<String, Object> gameSettings, List<PlayerBO> playerList) {
		
		// player turns
		for (PlayerBO playerBO : playerList) {
			
			String bankName="";
			if(playerBO.isBank()==1) {
				bankName="(Bank)";
			}
			
			print("");
			print("Player:" + bankName + " " + playerBO.getName());
			print("");
			
			// init total round
			playerBO.setTotalRound(0);
			
			// if player is bank or bot automatic mode
			if(playerBO.getHuman()==0 || playerBO==playerList.get(playerList.size()-1)) {
				// Automatic mode
				playerBO = turnAutomatic(playerBO);
			} else {
				// Manual mode
				playerBO = turnManual(playerBO);
			}
			
		}
		return playerList;
	}
	
	
	/*
	 * Manual mode
	 */
	public PlayerBO turnManual(PlayerBO playerBO) {
		
		boolean isBetNotValid = true;
		
		int bet = 0;
		
		// check if we have enough points
		while(isBetNotValid) {
			String input = JOptionPane.showInputDialog(null, playerBO.getName() + " How much do you like to bet (max "+ playerBO.getPoints() +") ?");
			bet = Integer.parseInt(input);
			if(bet > playerBO.getPoints()) {
				print("Maximum: " + playerBO.getPoints());
			} else {
				isBetNotValid = false;
			}
		}
		
		playerBO.setBet(bet);
		print("");
		print("Player: " + playerBO.getName() + " bet for: " + playerBO.getBet());
		
		boolean moreCards = true;
		while(moreCards) {
		
			// Get cards for player
			List<CardBO> cardList = getPlayerCard(1);
			CardBO card = cardList.get(0);
			
			save_player_game_round();
			
			// show card to player
			print("Card: " +card.getCard_value() + " " + card.getCard_name());
			playerBO.setTotalRound(playerBO.getTotalRound() + card.getCard_real_value());
			
			// check if the total > 7.5
			if(playerBO.getTotalRound() > 7.5) {
				print("You lost");
				moreCards = false;
				break;
			}
			
			// Ask for more cards if not moreCards=false
			int input = JOptionPane.showConfirmDialog(null, "Do you like to continue?");
			if(input==1) {
				moreCards = false;
				break;
			}
			
		}
		
		return playerBO;
	}
	
	
	/*
	 * Automatic mode
	 */
	public PlayerBO turnAutomatic(PlayerBO playerBO) {
		
		print("Player bank: " + playerBO.getBet());
		print("");
		
		// set number of bank rounds
		double r = Math.random();
		int numBankTurns = (int)(r * (5 - 1)) + 1;
		
		int i = 0;
		boolean moreCards = true;
		while(moreCards) {
			
			// Get cards for player
			List<CardBO> cardList = getPlayerCard(1);
			CardBO card = cardList.get(0);
			
			save_player_game_round();
			
			// show card to player
			print("Cad: " +card.getCard_value() + " " + card.getCard_name());
			playerBO.setTotalRound(playerBO.getTotalRound() + card.getCard_real_value());
			
			// check if the total > 7.5
			if(playerBO.getTotalRound() > 7.5) {
				String lost ="";
				if(playerBO.isBank()==1) {
					lost="The  bank lost";
				} else {
					lost="Player (BOT): " + playerBO.getName();
				}
				print(lost);
				print("");
				moreCards = false;
				break;
			}

			i=i+1;
			
			// Check max rounds
			if(i>=numBankTurns) {
				moreCards = false;
				break;
			}
			
		}
		
		return playerBO;
	}
	
	
	
	/*
	 * Return the list of players with priority
	 */
	public List<PlayerBO> initGame(HashMap<String, Object> players, int cardgame_id) {

		// fill the player with the priority
		List<PlayerBO> playerList = new ArrayList<>();
		
		players.forEach((key, value) -> {
		
			HashMap<String, Object> player =  (HashMap<String, Object>) value;
			PlayerBO playerBO = new PlayerBO();
			playerBO.setId((int) player.get("id"));
			playerBO.setName((String) key);
			playerBO.setPlayerRisk((int) player.get("player_risk"));
			playerBO.setHuman((int) player.get("human"));
			
			// get cards for the player
			List<CardBO> card = getPlayerCard(1);
			
			playerBO.setPriority(card.get(0).getCard_priority());
			playerList.add(playerBO);
		
			save_player_game(cardgame_id, playerBO, card.get(0).getCard_id());
		});
	
		
		
		
		return (List<PlayerBO>) playerList.stream()
				.sorted(Comparator.comparingInt(PlayerBO::getPriority))
				.collect(Collectors.toList());
	}
	
	
	
	/*
	 * Return the cards
	 */
	public List<CardBO> getPlayerCard (int numPlayers) {
		Cards cards = new Cards();
		List<CardBO> ListCards = cards.getCards(numPlayers);
		return ListCards;
	}
	
	
	/*
	 * Insert gameCard into db
	 */
	public int saveCardGame(Timestamp startGame) {
		
		Main main = new Main();
		HashMap<String, Object> gameSettings = main.getGameSettings();
		
		int id = 0;
        try {
        	Connection connection = DatabaseConnection.getConnection();
            String insertQuery = "INSERT INTO cardgame (players, rounds, start_hour, end_hour, deck_id) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement insertStatement = connection.prepareStatement(insertQuery,  Statement.RETURN_GENERATED_KEYS);
            insertStatement.setInt(1, (int) gameSettings.get("gamePlayers"));
            insertStatement.setInt(2, (int) gameSettings.get("maxRounds"));
            insertStatement.setTimestamp(3, startGame);
            insertStatement.setTimestamp(4, null);
            insertStatement.setInt(5, (int) gameSettings.get("deck_id"));
            insertStatement.executeUpdate();
            
            ResultSet rs=insertStatement.getGeneratedKeys();
            if(rs.next()){
              id = rs.getInt(1);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
		
        return id;
	}
	
	
	public void updateCardGame(int cardgame_id, Timestamp endGame) {
        try {
        	Connection connection = DatabaseConnection.getConnection();
            String insertQuery = "UPDATE cardgame SET end_hour=? WHERE id=?";
            PreparedStatement insertStatement = connection.prepareStatement(insertQuery,  Statement.RETURN_GENERATED_KEYS);
            insertStatement.setTimestamp(1, endGame);
            insertStatement.setInt(2, cardgame_id);
            insertStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}
	
	public void save_player_game(int cardgame_id, PlayerBO playerBO, String card_id) {

	        try {
	        	Connection connection = DatabaseConnection.getConnection();
	            String insertQuery = "INSERT INTO player_game (initial_card_id, starting_points, ending_points, cardgame_id, player_id) VALUES (?, ?, ?, ?, ?)";
	            PreparedStatement insertStatement = connection.prepareStatement(insertQuery,  Statement.RETURN_GENERATED_KEYS);
	            insertStatement.setString(1, card_id);
	            insertStatement.setInt(2, (int) playerBO.getPoints());
	            insertStatement.setInt(3, 0);
	            insertStatement.setInt(4, cardgame_id);
	            insertStatement.setInt(5, (int) playerBO.getId());
	            insertStatement.executeUpdate();
	            
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
		
	}
	
	public void update_player_game(int cardgame_id, PlayerBO playerBO) {
        try {
        	Connection connection = DatabaseConnection.getConnection();
            String insertQuery = "UPDATE player_game SET ending_points=? WHERE cardgame_id=? and player_id=?";
            PreparedStatement insertStatement = connection.prepareStatement(insertQuery,  Statement.RETURN_GENERATED_KEYS);
            insertStatement.setFloat(1, (float) playerBO.getPoints());
            insertStatement.setInt(2, cardgame_id);
            insertStatement.setInt(3, playerBO.getId());
            insertStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}
	
	
	
	/*
	 * Insert save_player_game_round into db
	 */
	public void save_player_game_round(){
		
		/*
        try {
        	Connection connection = DatabaseConnection.getConnection();
            String insertQuery = "INSERT INTO player_game_round (round_num, is_bank, bet_points, cards_value, starting_round_points, ending_round_points, cardgame_id, player_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
            insertStatement.setInt(1, (int) gameSettings.get("gamePlayers"));
            insertStatement.setInt(2, (int) gameSettings.get("maxRounds"));
            insertStatement.setTimestamp(3, startGame);
            insertStatement.setTimestamp(4, endGame);
            insertStatement.setInt(5, (int) gameSettings.get("deck_id"));
            insertStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
		*/
		
	}
	

}
