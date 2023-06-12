package sieteymedio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import BO.DeckBO;
import conexion.DatabaseConnection;

public class Deck {

   public List<DeckBO> retrieveAvailableDecksFromDatabase() {
    	
		List<DeckBO> deckList = new ArrayList();
		Connection connection;
		
		try {
			connection = DatabaseConnection.getConnection();
			
			String query = "SELECT * from deck";
			PreparedStatement ps = connection.prepareStatement(query);
			ResultSet rst = ps.executeQuery();
			
			while (rst.next()) {
				DeckBO deck = new DeckBO();
				deck.setId(rst.getInt("id"));
				deck.setDeck_name(rst.getString("deck_name"));
				deckList.add(deck);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
        return deckList;
    }
}
