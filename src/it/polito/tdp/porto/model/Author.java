package it.polito.tdp.porto.model;

import java.util.ArrayList;
import java.util.List;

public class Author extends Nodo {

	private int id;
	private String lastname;
	private String firstname;
	private List<Paper> articoli;
	
		
	public Author(int id, String lastname, String firstname) {
		super();
		this.id = id;
		this.lastname = lastname;
		this.firstname = firstname;
		articoli=new ArrayList<>();
	}

	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getLastname() {
		return lastname;
	}


	public void setLastname(String lastname) {
		this.lastname = lastname;
	}


	public String getFirstname() {
		return firstname;
	}


	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	
	

	public List<Paper> getArticoli() {
		return articoli;
	}

	public void setArticoli(List<Paper> articoli) {
		this.articoli = articoli;
	}
	
	

	@Override
	public String toString() {
		return "Author [id=" + id + ", lastname=" + lastname + ", firstname=" + firstname + "]";
	}
}
