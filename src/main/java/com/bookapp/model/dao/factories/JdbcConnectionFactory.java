package com.bookapp.model.dao.factories;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JdbcConnectionFactory {
	private static Connection connection;

	private JdbcConnectionFactory() {
	}

	public static Connection getConnection() {
		InputStream is = JdbcConnectionFactory.class.getClassLoader().getResourceAsStream("db.properties");
		Properties properties = new Properties();
		try {
			properties.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}

		String driver = properties.getProperty("jdbc.driverName");
		String url = properties.getProperty("jdbc.url");
		String username = properties.getProperty("jdbc.username");
		String password = properties.getProperty("jdbc.password");

		try {
			Class.forName(driver);
		} catch (ClassNotFoundException ex) {
		}

		try {
			connection = DriverManager.getConnection(url, username, password);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		return connection;
	}

}
