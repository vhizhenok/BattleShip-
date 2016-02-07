package arei.sb.seafight;
import com.mysql.fabric.jdbc.FabricMySQLDriver;
import java.sql.*;

public class DataBaseConnector {
    private final String host;
    private final String user;
    private final String password;
    private Connection connection = null;

    public DataBaseConnector(String host, String user, String password) throws SQLException {
        this.host = host;
        this.password = password;
        this.user = user;
        connection();
    }

    public void connection() throws SQLException {
        Driver driver = new FabricMySQLDriver();
        DriverManager.registerDriver(driver);
        connection = DriverManager.getConnection(host, user, password);
    }

    public ResultSet query(String query) throws SQLException {
        if (connection == null) return null;
        else if (connection.isClosed()) return null;
        Statement statement = connection.createStatement();
        return statement.executeQuery(query);
    }

    public boolean select (String query) throws SQLException {
        if (connection == null) return false;
        else if (connection.isClosed()) return false;
        Statement statement = connection.createStatement();
        return statement.execute(query);
    }

    public void close() throws SQLException{
        if (connection != null)
           connection.close();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        close();
    }
}
