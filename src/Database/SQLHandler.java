package Database;

import java.sql.*;

public class SQLHandler {
    private Connection connection;
    private PreparedStatement insertFilm, insertReview, insertCrew;
    private String path = "jdbc:sqlite:filmDB.db";
    private String sqlTableFilm = "CREATE TABLE IF NOT EXISTS Film (\n" +
            "    id INT,\n" +
            "    name TEXT,\n" +
            "    release_year SMALLINT,\n" +
            "    recommended_age TINYINT,\n" +
            "    film_type VARCHAR" +
            ");";
    private String sqlTableReviews = "CREATE TABLE IF NOT EXISTS Review (\n" +
            "    film_id INT,\n" +
            "    score TINYINT,\n" +
            "    comment TEXT\n" +
            ");";
    private String sqlTableCrew = "CREATE TABLE IF NOT EXISTS Crew (\n" +
            "    id INT,\n" +
            "    film_id INT,\n" +
            "    name TEXT,\n" +
            "    occupation TEXT\n" +
            ");";
    private String sqlTruncateTableFilm = "DELETE FROM Film;";
    private String sqlTruncateTableCrew = "DELETE FROM Crew;";
    private String sqlTruncateTableReview = "DELETE FROM Review;";

    private String sqlSelectTableFilm = "SELECT * FROM Film;";
    private String sqlSelectTableCrew = "SELECT * FROM Crew;";
    private String sqlSelectTableReview = "SELECT * FROM Review;";

    private String sqlInsertTableFilm = "INSERT INTO Film " +
            "(id, name, release_year, recommended_age, film_type) " +
            "VALUES(?,?,?,?,?);";
    private String sqlInsertTableCrew = "INSERT INTO Crew " +
            "(id, film_id, name, occupation) " +
            "VALUES(?,?,?,?);";
    private String sqlInsertTableReview = "INSERT INTO Review " +
            "(film_id, score, comment) " +
            "VALUES(?,?,?);";


    public void establishConnection() throws SQLException {
        this.connection = null;
        this.connection = DriverManager.getConnection(path);
    }

    public void createStatements() throws SQLException {
        this.insertFilm = connection.prepareStatement(this.sqlInsertTableFilm);
        this.insertCrew = connection.prepareStatement(this.sqlInsertTableCrew);
        this.insertReview = connection.prepareStatement(this.sqlInsertTableReview);
    }

    public void closeConnection() throws SQLNullConnection, SQLException {
        if (connection==null)
            throw new SQLNullConnection();
        this.connection.close();
    }


    public void createTablesIfNotExist() throws SQLNullConnection, SQLException {
        if (this.connection==null)
            throw new SQLNullConnection();
        Statement statement = this.connection.createStatement();
        statement.execute(this.sqlTableFilm);
        statement.execute(this.sqlTableCrew);
        statement.execute(this.sqlTableReviews);
    }

    public ResultSet selectAllFilms() throws SQLNullConnection, SQLException {
        if (this.connection==null)
            throw new SQLNullConnection();
        Statement statement = this.connection.createStatement();
        return statement.executeQuery(this.sqlSelectTableFilm);
    }
    public ResultSet selectAllReviews() throws SQLNullConnection, SQLException {
        if (this.connection==null)
            throw new SQLNullConnection();
        Statement statement = this.connection.createStatement();
        return statement.executeQuery(this.sqlSelectTableReview);
    }
    public ResultSet selectAllCrewMembers() throws SQLNullConnection, SQLException {
        if (this.connection==null)
            throw new SQLNullConnection();
        Statement statement = this.connection.createStatement();
        return statement.executeQuery(this.sqlSelectTableCrew);
    }

    public void truncateAllTables() throws SQLNullConnection, SQLException {
        if (this.connection==null)
            throw new SQLNullConnection();
        Statement statement = this.connection.createStatement();
        statement.execute(this.sqlTruncateTableFilm);
        statement.execute(this.sqlTruncateTableCrew);
        statement.execute(this.sqlTruncateTableReview);
    }

    public void insertFilm(int id, String name, short releaseYear, byte recommendedAge, String filmType) throws SQLNullConnection, SQLException {
        if (this.connection==null)
            throw new SQLNullConnection();
        this.insertFilm.setInt(1, id);
        this.insertFilm.setString(2, name);
        this.insertFilm.setShort(3, releaseYear);
        this.insertFilm.setByte(4, recommendedAge);
        this.insertFilm.setString(5, filmType);
        this.insertFilm.executeUpdate();
    }
    public void insertReview(int filmId, byte score, String comment) throws SQLNullConnection, SQLException {
        if (this.connection==null)
            throw new SQLNullConnection();
        this.insertReview.setInt(1, filmId);
        this.insertReview.setByte(2, score);
        this.insertReview.setString(3, comment);
        this.insertReview.executeUpdate();
    }
    public void insertCrewMember(int id, int filmId, String name, String occupation) throws SQLNullConnection, SQLException {
        if (this.connection==null)
            throw new SQLNullConnection();
        this.insertCrew.setInt(1, id);
        this.insertCrew.setInt(2, filmId);
        this.insertCrew.setString(3, name);
        this.insertCrew.setString(4, occupation);
        this.insertCrew.executeUpdate();
    }
}
