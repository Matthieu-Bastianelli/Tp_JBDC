package objectDao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import object.Book;
import object.Client;

public class BookDao {

	// Méthode pour ajouter un livre/book

	public static void ajouterBook(Book book) {

		// ==========================================================================================
		// Paramètres de connection :
		// format url : jdbc:<driver>://<host>:<port>/<databaseName>
		String url = "jdbc:postgresql://localhost:5432/baseDD";
		String username = "postgres";
		String password = "2detension";

		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			// Connection à la base de données :
			try {
				conn = DriverManager.getConnection(url, username, password);
				stmt = conn.prepareStatement("INSERT INTO book(title,author) VALUES (? , ?)", Statement.RETURN_GENERATED_KEYS);

				conn.setAutoCommit(false);

				stmt.setString(1, book.getTitle());
				stmt.setString(2, book.getAuthor());
				stmt.executeUpdate();
				ResultSet resultSet = stmt.getGeneratedKeys();
				resultSet.next();
				book.setId(resultSet.getInt("id"));

				conn.commit();

			} catch (Exception e) {
				System.out.println(e.getMessage());
				conn.rollback();
			} finally {
				conn.setAutoCommit(true);

				stmt.close();
				conn.close();

			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		// ==========================================================================================
	}

	public static void ajouterBooks(List<Book> books) {

		// ==========================================================================================
		// Paramètres de connection :
		// format url : jdbc:<driver>://<host>:<port>/<databaseName>
		String url = "jdbc:postgresql://localhost:5432/baseDD";
		String username = "postgres";
		String password = "2detension";

		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			// Connection à la base de données :
			try {
				conn = DriverManager.getConnection(url, username, password);
				stmt = conn.prepareStatement("INSERT INTO book(title,author) VALUES (? , ?)", Statement.RETURN_GENERATED_KEYS);

				conn.setAutoCommit(false);

				for (Book book : books) {
					stmt.setString(1, book.getTitle());
					stmt.setString(2, book.getAuthor());
					stmt.executeUpdate();
					ResultSet resultSet = stmt.getGeneratedKeys();
					resultSet.next();
					book.setId(resultSet.getInt("id"));
					
				}

				conn.commit();

			} catch (Exception e) {
				System.out.println(e.getMessage());
				conn.rollback();
			} finally {
				conn.setAutoCommit(true);

				stmt.close();
				conn.close();

			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		// ==========================================================================================
		
	}
	/**
	 * Ensemble des clients ayant acheté un livre donné :
	 */
	public static void clientQuiOnAchete(Book book) {

		System.out.println("------------------------------------");
		System.out.println("Client ayant Acheté le livre '"+book.getTitle()+"' : r\n");
		// ==========================================================================================
				// Paramètres de connection :
				// format url : jdbc:<driver>://<host>:<port>/<databaseName>
				String url = "jdbc:postgresql://localhost:5432/baseDD";
				String username = "postgres";
				String password = "2detension";

				Connection conn = null;
				PreparedStatement stmt = null;

				try {
					// Connection à la base de données :
					try {
						conn = DriverManager.getConnection(url, username, password);
//						stmt = conn.prepareStatement("SELECT firstname FROM client JOIN achat_client_book  //=> Bonne requête à faire
//																		ON client.id = achat_client_book.id_client
//																		WHERE id_book = ?");
						stmt = conn.prepareStatement("SELECT id_client FROM achat_client_book WHERE id_book = ?");
						stmt.setInt(1, book.getId());
					
						ResultSet result = stmt.executeQuery();
						while(result.next()) { // retourne la valeur suivante du ResultSet s'il existe, renvoie false sinon. 
//							result.getInt("id_book");

							PreparedStatement stmt2 = conn.prepareStatement("SELECT firstname FROM client WHERE id = ?");
							stmt2.setInt(1, result.getInt("id_client"));
							
							ResultSet resultClient = stmt2.executeQuery();
							while (resultClient.next()) {
								System.out.println(resultClient.getString("firstname"));
															
							}
							
							stmt2.close();							
						}
						
						
						} catch (Exception e) {
							System.out.println(e.getMessage());
						} finally {
							stmt.close();
							conn.close();
						}
					
				}catch (Exception e) {
					System.out.println(e.getMessage());
				}
				

				System.out.println("------------------------------------\r\n");
	}
}
