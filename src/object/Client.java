package object;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import util.Gender;

public class Client {

	private int id;	
	private String firstName;
	private String lastName;
	private Gender gender;
	private Book livrePrefere;
	
	//Constructeur
	public Client(String firstName, String lastName, Gender gender, Book livrePrefere) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.livrePrefere = livrePrefere;
	}


	//Getter et Setter
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public Book getLivrePrefere() {
		return livrePrefere;
	}


	public void setLivrePrefere(Book livrePrefere) {
		this.livrePrefere = livrePrefere;
	}
	
	

}
