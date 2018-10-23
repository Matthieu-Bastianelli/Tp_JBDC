package objectDao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import object.Book;
import object.Client;

public class ClientDao {

	public static void ajouterClient(Client client) {

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
				stmt = conn.prepareStatement("INSERT INTO Client(firstname,lastname,gender) VALUES (? , ?, ?)", Statement.RETURN_GENERATED_KEYS);

				conn.setAutoCommit(false);

				stmt.setString(1, client.getFirstName());
				stmt.setString(2, client.getLastName());
				stmt.setString(3, client.getGender().gender);
				stmt.executeUpdate();
				ResultSet resultSet = stmt.getGeneratedKeys();
				resultSet.next();
				client.setId(resultSet.getInt("id"));
				

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

	public static void ajouterClients(List<Client> clients) {

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
				stmt = conn.prepareStatement("INSERT INTO Client(firstname,lastname,gender) VALUES (? , ?, ?)",Statement.RETURN_GENERATED_KEYS);

				conn.setAutoCommit(false);

				for (Client client : clients) {
					stmt.setString(1, client.getFirstName());
					stmt.setString(2, client.getLastName());
					stmt.setString(3, client.getGender().gender);
					stmt.executeUpdate();
					ResultSet resultSet = stmt.getGeneratedKeys();
					resultSet.next();
					client.setId(resultSet.getInt("id"));

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
	 * Achat par un client d'un livre
	 * @param clientAcheteur
	 * @param bookAchete
	 */
	public static void acheterBook(Client clientAcheteur, Book bookAchete) {

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

				stmt = conn.prepareStatement("INSERT INTO achat_client_book(id_book, id_client) VALUES (? , ?)");

				conn.setAutoCommit(false);

				stmt.setInt(1, bookAchete.getId());
				stmt.setInt(2, clientAcheteur.getId());

				stmt.execute();

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
	}
	
	/**
	 * Ensemble des livres achetés par un client donné
	 */
	public static void livresAchete(Client client) {

		System.out.println("------------------------------------");
		System.out.println("Livre Achetés par "+client.getFirstName()+" "+client.getLastName()+" : \r\n");
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
//						stmt = conn.prepareStatement("SELECT title FROM book JOIN achat_client_book  //=> Bonne requête à faire
//																		ON book.id = achat_client_book.id_book
//																		WHERE id_book = ?");
						stmt = conn.prepareStatement("SELECT id_book FROM achat_client_book WHERE id_client = ?");
						stmt.setInt(1, client.getId());
					
						ResultSet result = stmt.executeQuery();
						while(result.next()) { // retourne la valeur suivante du ResultSet s'il existe, renvoie false sinon. 
//							result.getInt("id_book");

							PreparedStatement stmt2 = conn.prepareStatement("SELECT title FROM book WHERE id = ?");
							stmt2.setInt(1, result.getInt("id_book"));
							
							ResultSet resultBook = stmt2.executeQuery();
							while (resultBook.next()) {
								System.out.println(resultBook.getString("title"));
															
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
