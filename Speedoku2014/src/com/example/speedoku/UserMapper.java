package com.example.speedoku;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;


public class UserMapper {
	
	private static UserMapper userMapper=null;
	
	
	protected UserMapper(){
	}
	public static UserMapper userMapper(){
		if(userMapper==null){
			userMapper=new UserMapper();
		}
		return userMapper;
	}
	
	public User anlegen(User user) throws Exception {
		Connection con = DBVerbindung.connection();
		Statement stmt = null;
		
		try{
			stmt = con.createStatement();
			
			/*
		       * Zunächst schauen wir nach, welches der momentan höchste
		       * Primärschlüsselwert ist.
		       */
		      ResultSet rs = stmt.executeQuery("SELECT * "
		          + "FROM Player ");
		      
		      // Wenn wir etwas zurückerhalten, kann dies nur einzeilig sein
		      if (rs.next()) {
		        /*
		         * a erhält den bisher maximalen, nun um 1 inkrementierten
		         * Primärschlüssel.
		         */
//		        user.setId(rs.getInt("maxid") + 1);
		        
		        stmt = con.createStatement();
			
			stmt.executeUpdate("INSERT INTO Player (Player, Points, Time) "
					+ "VALUES ("
			        + user.getPlayer()
			        + ",'"
			        + user.getPoints()
					+ "','"
					+ user.getTime() 
					+")");
		}
		}
		catch (SQLException e2) {
			e2.printStackTrace();
			throw new Exception("Datenbank fehler!" + e2.toString());
		}
//		finally {
//			DBVerbindung.closeAll(null, stmt, con);
//		}
		return user;
	}
	}
	
	


