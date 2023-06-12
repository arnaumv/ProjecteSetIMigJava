package sieteymedio;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import BO.DeckBO;
import conexion.DatabaseConnection;




public class Main {
	
    private static HashMap<String, Object> gameSettings = new HashMap<String, Object>(); // DICCIONARIO QUE ALMAZENA DATOS DE LA PARTIDA

    private static int partidaCounter = 0;


    private static DefaultTableModel tableModel;
    private static int maxRounds;
    private static int gamePlayers;
    private static String cardDeck;

    public static void main(String[] args) {
    	
    	Menu menu = new Menu();
    	menu.initializeMenu();
    

    	menu.getStartGameItem().addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	
                    // Check if the values are correct
                    if (!gameSettings.containsKey("gamePlayers") ||
                            !gameSettings.containsKey("deck_id") ||
                            !gameSettings.containsKey("maxRounds")) {
                        JOptionPane.showMessageDialog(null, "Please set game players, card deck and max rounds before starting the game.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    
                    int numPlayers =  (int) gameSettings.get("gamePlayers");
                    
                    Game game = new Game();
                    game.startGame(gameSettings);
                }
			
        });

        
    	menu.getAddPlayerItem().addActionListener(new ActionListener() {
    	    public void actionPerformed(ActionEvent e) {
    	        // Crear ventana de di�logo para elegir el tipo de jugador
    	        String[] options = { "Human", "Bot" };
    	        int choice = JOptionPane.showOptionDialog(menu.getFrame(), "Select player type:", "Add Player",
    	                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

    	        if (choice == 0) {
    	            // Agregar jugador humano
    	            String nif = JOptionPane.showInputDialog(menu.getFrame(), "Enter NIF:");
    	            if (Utils.isValidNIF(nif)) {
    	                // Verificar si el DNI ya existe en la base de datos
    	                if (Utils.isNIFAlreadyExists(nif)) {
    	                    JOptionPane.showMessageDialog(menu.getFrame(), "DNI already exists in the database!", "Error", JOptionPane.ERROR_MESSAGE);
    	                } else {
    	                    int risk = getRiskFromUser();
    	                    addPlayerToDatabase(nif, risk, true);
    	                    JOptionPane.showMessageDialog(menu.getFrame(), "Human player added successfully!");
    	                }
    	            } else {
    	                JOptionPane.showMessageDialog(menu.getFrame(), "Invalid NIF!", "Error", JOptionPane.ERROR_MESSAGE);
    	            }
    	        } else if (choice == 1) {
    	            // Agregar bot
    	            String dni = Utils.generateRandomDNI();
    	            int risk = Utils.getRandomRisk();
    	            addPlayerToDatabase(dni, risk, false);
    	            JOptionPane.showMessageDialog(menu.getFrame(), "Bot player added successfully!");
    	        }
    	    }
    	});
        

        menu.getShowPlayersItem().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showPlayersFromDatabase();
            }
        });

        menu.getRemovePlayerItem().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removePlayerFromDatabase();
            }
        });

        menu.getSetGamePlayersItem().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
					setGamePlayers();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
            }
        });

        menu.getSetCardsDeckItemm().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setCardDeck();
            }
        });

        menu.getSetMaxRoundsItem().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setMaxRounds();
            }
        });

        menu.getExitMenuItem().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    

    private static int getRiskFromUser() {
        String[] options = { "Audacious", "Normal", "Prudent" };
        int choice = JOptionPane.showOptionDialog(null, "Select risk level:", "Risk Level",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

        switch (choice) {
            case 0:
                return 50;
            case 1:
                return 40;
            case 2:
                return 30;
            default:
                return 0;
        }
    }




    private static void addPlayerToDatabase(String id, int risk, boolean isHuman) {

        try {
        	Connection connection = DatabaseConnection.getConnection();
        	
            String insertQuery = "INSERT INTO players (player_name, player_risk, human) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(insertQuery);
            statement.setString(1, id);
            statement.setInt(2, risk);
            statement.setBoolean(3, isHuman);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    

    private static void showPlayersFromDatabase() {
        
    	try {
        	
    		Connection connection = DatabaseConnection.getConnection();
            String selectQuery = "SELECT id, player_name, player_risk, human FROM players";
            PreparedStatement statement = connection.prepareStatement(selectQuery);
            ResultSet resultSet = statement.executeQuery();

            // Crear el modelo de tabla con los datos de los jugadores
            tableModel = new DefaultTableModel(new Object[]{"ID", "Name", "Risk", "Human"}, 0);

            // Agregar los jugadores al modelo de tabla
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("player_name");
                int risk = resultSet.getInt("player_risk");
                boolean isHuman = resultSet.getBoolean("human");
                tableModel.addRow(new Object[]{id, name, risk, isHuman});
            }

            // Crear la tabla y mostrarla en un diálogo
            JTable playersTable = new JTable(tableModel);
            JScrollPane scrollPane = new JScrollPane(playersTable);
            JOptionPane.showMessageDialog(null, scrollPane, "Players List", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void removePlayerFromDatabase() {
        try { 
        
        	Connection connection = DatabaseConnection.getConnection();
            showPlayersFromDatabase();

            // Pedir la ID del jugador a eliminar
            String playerId = JOptionPane.showInputDialog(null, "Enter player ID to remove:");

            // Eliminar el jugador de la base de datos
            String deleteQuery = "DELETE FROM players WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(deleteQuery);
            statement.setString(1, playerId);
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Player removed successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "Player not found!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    
    private static void setGamePlayers() throws SQLException {
        int minimumPlayers = 6;

        Connection connection = null;
        try {
        	connection = DatabaseConnection.getConnection();
        
            // Obtener el número de jugadores actual en la base de datos
            String countQuery = "SELECT COUNT(*) AS playerCount FROM players";
            PreparedStatement countStatement = connection.prepareStatement(countQuery);
            ResultSet countResult = countStatement.executeQuery();
            countResult.next();
            int playerCount = countResult.getInt("playerCount");

            if (playerCount < minimumPlayers) {
                JOptionPane.showMessageDialog(null, "Tienen que haber " + minimumPlayers + " jugadores añadidos!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String input = JOptionPane.showInputDialog(null, "Enter the number of game players:");
            int gamePlayers = Integer.parseUnsignedInt(input);

            if (gamePlayers < 2 || gamePlayers > 6) {
                JOptionPane.showMessageDialog(null, "El número de jugadores debe estar entre 2 y 6.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            DeckBO cardDeck = setCardDeck();
            if (cardDeck == null) {
                return;
            }
            int deck_id = getGameTypeValue(cardDeck.getId());

            
            input = JOptionPane.showInputDialog(null, "Enter the maximum number of rounds:");
            int maxRounds = Integer.parseInt(input);

         // Generar un ID de partida único
            int gameID = partidaCounter + 1;

            // Almacenar los valores en el diccionario
            gameSettings.put("gameID", gameID);

            // Almacenar los valores en el diccionario
            gameSettings.put("gameID", gameID);
            gameSettings.put("gamePlayers", gamePlayers);
            gameSettings.put("deck_id", deck_id);
            gameSettings.put("maxRounds", maxRounds);

            JOptionPane.showMessageDialog(null, "Game players set successfully!");
            
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid input! Please enter a number.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        	if(!connection.isClosed()) {
        		connection.close();
        	}
        }
    }

    private static DeckBO setCardDeck() {
        // Retrieve the available card decks from the database
    	Deck deck = new Deck();
    	
        List<DeckBO> availableDecks = deck.retrieveAvailableDecksFromDatabase();

        if (availableDecks.size() == 0) {
            JOptionPane.showMessageDialog(null, "No card decks available!");
            return null;
        }

        
        
        // Show a dialog with the available card decks and let the user choose one
        DeckBO selectedDeck = (DeckBO) JOptionPane.showInputDialog(null, "Select a card deck:", "Set Card's Deck",
                JOptionPane.PLAIN_MESSAGE, null, availableDecks.toArray(), "-");

        
        if (selectedDeck != null) {
            // Save game info to database
        	int deck_id = selectedDeck.getId();

             // Almacenar el valor en el diccionario
             gameSettings.put("deck_id", deck_id);
             return selectedDeck;
        }
        
        return null;
    }
    
    
    
    
    
    
    private static int getGameTypeValue(int selectedDeck) {
        if (selectedDeck == 0) {
            return -1;
        }
        if (selectedDeck==1) {
            return 1;
        } else if (selectedDeck==2) {
            return 2;
        } else {
            return -1;
        }
    }

    

    private static void setMaxRounds() {
        String input = JOptionPane.showInputDialog(null, "Enter the maximum number of rounds:");
        try {
            int maxRounds = Integer.parseInt(input);
            // Perform the necessary actions with the maxRounds value
            // ...


            // Almacenar los valores en el diccionario
            gameSettings.put("maxRounds", maxRounds);

            // Save game info to database
            UUID gameID = (UUID) gameSettings.get("gameID");
            int gamePlayers = (int) gameSettings.get("gamePlayers");
            String cardDeck = (String) gameSettings.get("cardDeck");
            JOptionPane.showMessageDialog(null, "Max rounds set successfully!");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid input! Please enter a number.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
	public static HashMap<String, Object> getGameSettings() {
		return gameSettings;
	}
    
    
}