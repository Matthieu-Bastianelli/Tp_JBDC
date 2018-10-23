package object;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Book {
	
	private int id;
	private String title;
	private String author;
	
	// Constructeur
	public Book(String title, String author) {
		super();
		this.title = title;
		this.author = author;
	}

	

	// Getter et Setter :
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}



	
	

}
