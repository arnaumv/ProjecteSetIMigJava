package sieteymedio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import conexion.DatabaseConnection;

public class Utils {
	
	
    public static boolean isValidNIF(String nif) {
        // Validar el formato del NIF
        if (nif.length() != 9) {
            return false;
        }

        String letters = "TRWAGMYFPDXBNJZSQVHLCKE";
        char lastChar = Character.toUpperCase(nif.charAt(8));
        int number;

        try {
            number = Integer.parseInt(nif.substring(0, 8));
        } catch (NumberFormatException e) {
            return false;
        }

        int index = number % 23;
        char calculatedLastChar = letters.charAt(index);

        return lastChar == calculatedLastChar;
    }
    
    
    public static String generateRandomDNI() {
        Random random = new Random();
        int number = random.nextInt(90000000) + 10000000;
        char letter = "TRWAGMYFPDXBNJZSQVHLCKE".charAt(number % 23);
        return String.valueOf(number) + letter;
    }

    public static int getRandomRisk() {
        // Generar un valor de riesgo aleatorio
        Random random = new Random();
        int[] risks = { 30, 40, 50 };
        int index = random.nextInt(risks.length);
        return risks[index];
    }
    
    public static boolean isNIFAlreadyExists(String nif) {
        boolean exists = false;
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT COUNT(*) FROM tabla WHERE dni = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, nif);
                ResultSet resultSet = statement.executeQuery();
                resultSet.next();
                int count = resultSet.getInt(1);
                exists = (count > 0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exists;
    }
    

}
