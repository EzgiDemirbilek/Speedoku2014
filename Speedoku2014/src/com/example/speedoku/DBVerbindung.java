package com.example.speedoku;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DBVerbindung {

	
	public static Connection con;


	
//	cloud sql
//	private static String url = "jdbc:mysql://localhost:3306/social-media?user=root&password=";

	

//
	public static Connection connection() {  
		//Verbindung erstllen wenn es noch keine gibt
		if ( con == null ) {
		//	try {

			try
			{
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("...Treiber erfolgreich geladen");
			con = DriverManager.getConnection("jdbc:mysql://db4free.net:3306/speeddokudb", "speeddokuezsel", "ezsel2014");
			System.out.println("...Verbindung erfolgreich aufgebaut");
			}
			catch(Exception e)
			{
			System.out.println("Fehler " + e);
			}
				
			
		return con;	
	}
		return con;
}

}






