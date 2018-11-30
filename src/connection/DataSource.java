package connection;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DataSource {

	private static DataSource datasource;
	private ComboPooledDataSource cpds;

	private DataSource() throws PropertyVetoException {
		cpds = new ComboPooledDataSource();
		cpds.setDriverClass("org.postgres.jdbc.Driver");
		cpds.setJdbcUrl("jdbc:postgresql://localhost:5432/postgres");
		cpds.setUser("postgres");
		cpds.setPassword("pgp");
	}
	
	public static DataSource getInstance() throws PropertyVetoException {
		if(datasource == null) {
			datasource = new DataSource();
			return datasource;
		}else {
			return datasource;
		}
	}
	
	public Connection getConnection() throws SQLException{
		return this.cpds.getConnection();
	}
	
	
	
}
