package com.mycompany.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mycompany.beans.Joueur;

public class JoueurDaoImpl implements JoueurDao {
	private DaoFactory daoFactory;
	private Connection connexion = null;
	private PreparedStatement statement = null;

	public JoueurDaoImpl(DaoFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	@Override
	public List<Joueur> lister() {
		ArrayList<Joueur> listeJoueurPerso= new ArrayList<Joueur>();
		try {
			connexion = daoFactory.getConnection();
			statement = connexion.prepareStatement("select * from joueur");
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				Joueur j = new Joueur();
				j.setId(rs.getLong("joueur.id"));
				j.setNom(rs.getString("joueur.nom"));
				j.setPrenom(rs.getString("joueur.prenom"));
				j.setSexe(rs.getString("joueur.sexe"));
				listeJoueurPerso.add(j);
				}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listeJoueurPerso;
	}
	
	public void ajouterJoueur(Joueur nouveauJoueur) {
		try {
			connexion = daoFactory.getConnection();
	        PreparedStatement pstmt = connexion.prepareStatement("INSERT INTO joueur (NOM, PRENOM, SEXE) values (?, ?, ?)");
	        pstmt.setString(1, nouveauJoueur.getNom());
	        pstmt.setString(2, nouveauJoueur.getPrenom());
	        pstmt.setString(3, nouveauJoueur.getSexe());
	        pstmt.executeUpdate();
	        System.out.println("Joueur "+ nouveauJoueur.getNom() +" "+ nouveauJoueur.getPrenom() + " ajouté à la base de donnée");
	    } catch (Exception e) {
	            System.out.println(e);
	        }
	}
	
	public Joueur lecture(Long id) {
		Joueur joueurAModif = new Joueur();
		try {
			connexion = daoFactory.getConnection();
			PreparedStatement pstmt = connexion.prepareStatement("select * from joueur where joueur.id = ?");
			pstmt.setLong(1, id);
			ResultSet rs =  pstmt.executeQuery();
			if (rs.next()) {
				joueurAModif.setId(rs.getLong("joueur.id"));
				joueurAModif.setNom(rs.getString("joueur.nom"));
				joueurAModif.setPrenom(rs.getString("joueur.prenom"));
				joueurAModif.setSexe(rs.getString("joueur.sexe"));
			}
		}
		catch (Exception e){	
		}
		return joueurAModif;
	}
	
	public void updateJoueur(Long id, String nom, String prenom, String sexe) {
		try {
			connexion = daoFactory.getConnection();
			PreparedStatement pstmt = connexion.prepareStatement("UPDATE joueur SET NOM = ?, PRENOM = ? , SEXE= ? where ID = ?");
			pstmt.setString(1, nom);
			pstmt.setString(2, prenom);
			pstmt.setString(3, sexe);
			pstmt.setLong(4, id);
			pstmt.executeUpdate();
		}
		catch (Exception e){
			System.err.println(e);
		}
	}
	
	public void deleteJoueur(Long id) {
		try {
			connexion = daoFactory.getConnection();
			PreparedStatement pstmt = connexion.prepareStatement("DELETE from joueur where ID = ?");
			pstmt.setLong(1, id);
			pstmt.executeUpdate();
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}


}
