package com.projeto.projetoFabinho.DAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.projeto.projetoFabinho.DatabaseConnection;

	public abstract class BaseDAO<T> {

	    protected Connection getConnection() throws SQLException {
	        return DatabaseConnection.getConnection();
	    }

	    protected void closeResources(Connection conn, PreparedStatement stmt, ResultSet rs) {
	        try {
	            if (rs != null) rs.close();
	            if (stmt != null) stmt.close();
	            if (conn != null) conn.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}

