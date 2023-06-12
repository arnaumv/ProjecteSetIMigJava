package sieteymedio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import BO.CardBO;
import conexion.DatabaseConnection;

public class Cards {
	
	
	public List<CardBO> getCards(int numCards){
		
		List<CardBO> cardList = new ArrayList();
		Connection connection;
		
		try {
			connection = DatabaseConnection.getConnection();
			
			String query = "SELECT * from Card Where deck_id=? ORDER BY RAND() limit ?";
			PreparedStatement ps = connection.prepareStatement(query);
			Main main = new Main();
			HashMap<String, Object> gameSettings = main.getGameSettings();
			ps.setInt(1, (int) gameSettings.get("deck_id"));
			ps.setInt(2, numCards);
			ResultSet rst = ps.executeQuery();
			
			while (rst.next()) {
				CardBO card = new CardBO();
				card.setCard_id( rst.getString("id"));
				card.setCard_name(rst.getString("card_name"));
				card.setCard_value(rst.getFloat("card_value"));
				card.setCard_real_value(rst.getFloat("card_real_value"));
				
				cardList.add(card);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return cardList;
	}

}
