package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import object.Book;
import object.Client;
import objectDao.BookDao;
import objectDao.ClientDao;
import util.Gender;

public class Main {

	public static void main(String[] args) throws SQLException {
	 
//		Scanner scan = new Scanner(System.in);
		
		//format url : jdbc:<driver>://<host>:<port>/<databaseName>
		String url = "jdbc:postgresql://localhost:5432/baseDD";
		String username ="postgres";
		String password ="2detension";
//		System.out.println("Veuillez entrer le mots de passe de la baseDD :");
//		String password =scan.nextLine();
//		scan.close();
		
		//==========================================================================================
		//Connection à la base de données :
		try(Connection conn = DriverManager.getConnection(url, username, password)){ // autoClosable
			
		    conn.setAutoCommit(false);
		    
		    try(Statement stmt = conn.createStatement()){

				/**
				 * Création des tables Book(title, author) et client(firstname, lastname, gender) :
				 */

		    	stmt.executeUpdate("DROP TABLE if exists achat_client_book");
		    	stmt.executeUpdate("DROP TABLE if exists client");
		    	stmt.executeUpdate("DROP TABLE if exists book");
		    	
		    	stmt.executeUpdate("CREATE TABLE book(id serial CONSTRAINT fk_id_book PRIMARY KEY,"
		    										  + " title varchar(250) NOT NULL,"
		    										  + " author varchar(250))");

		    	
		    	stmt.executeUpdate("CREATE TABLE client(id serial CONSTRAINT pk_id_client PRIMARY KEY,"
		    											+ "firstname varchar(250),"
		    											+ "lastname varchar(250),"
		    											+ " gender varchar(1))" //L'énum du genre sera fait en java et non en SQL 
		    											+ " prefered_book varchar(250))");   
		    	

		    	stmt.executeUpdate("CREATE TABLE achat_client_book(id serial CONSTRAINT pk_id_ACL PRIMARY KEY,"
		    											+ "id_book integer CONSTRAINT fk_id_book REFERENCES book(id),"
		    											+ "id_client integer CONSTRAINT fk_id_client REFERENCES client(id))");
		    	
		    	conn.commit();	
		    	
		    }catch(Exception e){
		    	System.out.println(e.getMessage());
		        conn.rollback();		    	
		    }finally {
		    	conn.setAutoCommit(true);
		    }
		}
		//==========================================================================================
	
		Book book1 = new Book("What's new in Java 8?","Adams L. Davis");
		Book book2 = new Book("La vie devant soit","Romain Gary");
		Book book3 = new Book("Le joueur d'échec","S. Zweig");
		
		Client edouard = new Client("Edouard", "Bacels",Gender.M, book3);
		Client chloe = new Client("Chloé","Kaczmarek",Gender.F, book2);
		Client matthieu = new Client("Matthieu","Bastianelli", Gender.M,book3);
		
		
//		//Ajout d'un livre :
//		BookDao.ajouterBook(book1);
//		
//		//ajout d'un client
//		ClientDao.ajouterClient(edouard);
		

		//Ajout liste de Livre :
		List<Book> books = new ArrayList<Book>();
		books.add(book1);
		books.add(book2);
		books.add(book3);
		
		BookDao.ajouterBooks(books);


		//Ajout liste de Clients :
		List<Client> clients = new ArrayList<Client>();
		clients.add(edouard);
		clients.add(chloe);
		clients.add(matthieu);
		
		ClientDao.ajouterClients(clients);
		
		//Test récupération des id :
		System.out.println("Edouard est le number : " + edouard.getId());
		
		//Achat d'un livre
		ClientDao.acheterBook(chloe, book3);
		ClientDao.acheterBook(edouard, book3);
		ClientDao.acheterBook(chloe, book2);
		
		//Afficher les livres acheté par un client donné :
		ClientDao.livresAchete(chloe);
		ClientDao.livresAchete(edouard);
		ClientDao.livresAchete(matthieu);
		

		//Afficher les Clients ayant acheté un livre donné :
		BookDao.clientQuiOnAchete(book1);
		BookDao.clientQuiOnAchete(book3);
		
		
	}

}
