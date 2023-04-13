package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLHandler {
    Connection connection;
    String path = "jdbc:sqlite:filmDB.db";
    public boolean establishConnection() {
        connection = null;
        try {
            connection = DriverManager.getConnection(path);
        }
        catch (SQLException e) {
            return false;
        }
        return true;
    }

    public boolean closeConnection() {
        if (connection!=null) {
            try {
                connection.close();
            }
            catch (SQLException e) {
                return false;
            }
        }
        return true;
    }

    public boolean checkTablesExistence() {

    }

    public boolean createTables() {

    }

    public boolean selectAllFilms() {

    }
    public boolean selectAllReviews() {

    }
    public boolean selectAllCrewMembers() {

    }
    public boolean selectAllRelationships() {

    }

    public boolean truncateAllTables() {

    }

    public boolean insertFilm() {

    }
    public boolean insertReview() {

    }
    public boolean insertCrewMember() {

    }
    public boolean insertRelationship() {

    }

}
