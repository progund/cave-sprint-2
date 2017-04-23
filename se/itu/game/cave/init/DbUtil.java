package se.itu.game.cave.init;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * A helper class for e.g. the initialization of a game from the
 * database.
 */
class DbUtil {

  private static final String DB_CLASS = "org.sqlite.JDBC";
  private static final String DB_URL   = "jdbc:sqlite:cavedatabas.db";
  
  static {
    try {
      Class.forName(DB_CLASS);
    } catch (ClassNotFoundException cnfe) {
      System.err.println("Couldn't load class: " + cnfe);
    }
  }

  private static Connection con;
  private static DbUtil instance;
  
  private DbUtil() {
    try {
      con = DriverManager.getConnection(DB_URL);
    } catch (SQLException sqle) {
      System.err.println("Couldn't get connection to " + DB_URL + ": " + sqle.getMessage());
    }
  }

  /**
   * Returns the instance of this singleton class.
   * @return The reference to the only DBUtil instance
   */
  static DbUtil getInstance() {
    if (instance == null) {
      instance = new DbUtil();
    }
    return instance;
  }

  ResultSet query(String sql) throws SQLException {
    Statement st = con.createStatement();
    return st.executeQuery(sql);
  }
}
