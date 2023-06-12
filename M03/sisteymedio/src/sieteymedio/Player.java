package sieteymedio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import conexion.DatabaseConnection;

public class Player {

    public Map<String, Object> getPlayers (int numJugadores) {
    HashMap jugadoresNombres = new HashMap<>();

    try {
        // Conexión a la base de datos
        Connection connection = DatabaseConnection.getConnection();

        // Consulta SQL para obtener los nombres de los jugadores y sus "player_risk"
        String sql = "SELECT id, player_name, player_risk, human FROM players LIMIT ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, numJugadores);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String nombre = resultSet.getString("player_name");
                int id = resultSet.getInt("id");
                int playerRisk = resultSet.getInt("player_risk");
                int human = resultSet.getInt("human");
                jugadoresNombres.put(nombre, new HashMap<String, Object>() {{
                	put("id", id);
                    put("player_risk", playerRisk);
                    put("human", human);
                }});
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return jugadoresNombres;

            


}
	
}
