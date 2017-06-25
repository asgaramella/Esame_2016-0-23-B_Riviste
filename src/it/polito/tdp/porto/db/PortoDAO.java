package it.polito.tdp.porto.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import it.polito.tdp.porto.model.Author;
import it.polito.tdp.porto.model.Paper;

public class PortoDAO {

	/*
	 * Dato l'id ottengo l'autore.
	 */
	public Author getAutore(int id) {

		final String sql = "SELECT * FROM author where id=?";

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, id);

			ResultSet rs = st.executeQuery();

			if (rs.next()) {

				Author autore = new Author(rs.getInt("id"), rs.getString("lastname"), rs.getString("firstname"));
				return autore;
			}

			return null;

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}

	/*
	 * Dato l'id ottengo l'articolo.
	 */
	public Paper getArticolo(int eprintid) {

		final String sql = "SELECT * FROM paper where eprintid=?";

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, eprintid);

			ResultSet rs = st.executeQuery();

			if (rs.next()) {
				Paper paper = new Paper(rs.getInt("eprintid"), rs.getString("title"), rs.getString("issn"),
						rs.getString("publication"), rs.getString("type"), rs.getString("types"));
				return paper;
			}

			return null;

		} catch (SQLException e) {
			 e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}

	public List<Author> getAllAutori() {
		final String sql = "select * " + 
				"from author";

		List<Author> autori=new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			

			ResultSet rs = st.executeQuery();

			while(rs.next()) {

				autori.add(new Author(rs.getInt("id"), rs.getString("lastname"), rs.getString("firstname")));
				
			}
			conn.close();
			return autori;

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
		
	}

	public List<Paper> getAllArticle() {
		String sql="select * " + 
				"from paper " + 
				"where paper.`type`='article'";
		List<Paper> articoli=new ArrayList<Paper>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			

			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Paper paper = new Paper(rs.getInt("eprintid"), rs.getString("title"), rs.getString("issn"),
						rs.getString("publication"), rs.getString("type"), rs.getString("types"));
				articoli.add(paper);
			}
			conn.close();
			return articoli;

		} catch (SQLException e) {
			 e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
		
	}

	public List<Paper> getArticoliForAuthor(Author a, Map<Integer, Paper> mapArticoli) {
		String sql="SELECT p.* " + 
				"FROM creator as c, paper as p " + 
				"WHERE c.authorid=? " + 
				"AND p.`type`='article' " + 
				"AND c.eprintid=p.eprintid";
		List<Paper> articoli=new ArrayList<Paper>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, a.getId());
			

			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Paper ptemp=mapArticoli.get(rs.getInt("eprintid"));
				articoli.add(ptemp);
			}
			conn.close();
			return articoli;

		} catch (SQLException e) {
			 e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
		
		
	}

	public List<String> getAllRiviste() {
		String sql="SELECT DISTINCT p.publication " + 
				"FROM paper as p " + 
				"WHERE p.`type`='article' " ;
				
		List<String> riviste=new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
		
			

			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				riviste.add(rs.getString("p.publication"));
			}
			conn.close();
			return riviste;

		} catch (SQLException e) {
			 e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
		
	}

	public List<Paper> getArtForRivista(String s,Map<Integer, Paper> mapArticoli) {
	
		String sql="SELECT DISTINCT p.eprintid " + 
				"FROM paper as p, creator as c " + 
				"WHERE p.eprintid=c.eprintid " + 
				"AND p.`type`='article' " + 
				"AND p.publication=?";
		List<Paper> articoli=new ArrayList<Paper>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, s);
			

			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Paper ptemp=mapArticoli.get(rs.getInt("p.eprintid"));
				articoli.add(ptemp);
			}
			conn.close();
			return articoli;

		} catch (SQLException e) {
			 e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
		
	}

	
}