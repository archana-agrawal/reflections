import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JavaDatabaseChecker {
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
        boolean isJavaDatabaseUsed = isJavaDatabaseUsed();
        System.out.println("Java database is used: " + isJavaDatabaseUsed);
    }
}
