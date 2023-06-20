import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ThirdPartyChecker {
    public static boolean isThirdPartyUsed(String url) {
        try {
            // Open a connection to the URL and read the response
            URL thirdPartyUrl = new URL(url);
            BufferedReader reader = new BufferedReader(new InputStreamReader(thirdPartyUrl.openStream()));
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // Check if the response contains expected data indicating the third-party service
            // Modify this logic based on the specific characteristics of the service
            boolean isUsed = response.toString().contains("third-party content");
            return isUsed;
        } catch (IOException e) {
            // Handle exceptions if the connection or reading fails
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isMySQLUsed(String jdbcUrl, String username, String password) {
        try {
            // Establish a connection to the MySQL database
            Connection connection = DriverManager.getConnection(jdbcUrl, username, password);

            // Execute a query to check if any data is present in a specific table
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM my_table");

            // Check if any rows are returned, indicating the usage of the MySQL database
            boolean isUsed = resultSet.next() && resultSet.getInt(1) > 0;

            // Close the result set, statement, and connection
            resultSet.close();
            statement.close();
            connection.close();

            return isUsed;
        } catch (SQLException e) {
            // Handle exceptions if the connection or query fails
            e.printStackTrace();
        }
        return false;
    }

    // Assuming you have a Java database like H2 embedded database
    public static boolean isJavaDatabaseUsed() {
        try {
            // Establish a connection to the Java database
            Connection connection = DriverManager.getConnection("jdbc:h2:~/test", "sa", "");

            // Execute a query to check if any data is present in a specific table
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM my_table");

            // Check if any rows are returned, indicating the usage of the Java database
            boolean isUsed = resultSet.next() && resultSet.getInt(1) > 0;

            // Close the result set, statement, and connection
            resultSet.close();
            statement.close();
            connection.close();

            return isUsed;
        } catch (SQLException e) {
            // Handle exceptions if the connection or query fails
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        String googleUrl = "https://www.google.com";
        boolean isGoogleUsed = isThirdPartyUsed(googleUrl);
        System.out.println("Google.com is used: " + isGoogleUsed);

        String mysqlJdbcUrl = "jdbc:mysql://localhost:3306/mydb";
        String mysqlUsername = "root";
        String mysqlPassword = "password";
        boolean isMySQLUsed = isMySQLUsed(mysqlJdbcUrl, mysqlUsername, mysqlPassword);
        System.out.println("MySQL database is used: " + isMySQLUsed);

