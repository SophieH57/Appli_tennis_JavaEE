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

	
	//récupérer la liste de tous les joueurs de la base de données
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
			connexion.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listeJoueurPerso;
	}
	
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	//ajouter un joueur à la base de données
	public void ajouterJoueur(Joueur nouveauJoueur) {
		try {
			connexion = daoFactory.getConnection();
	        PreparedStatement pstmt = connexion.prepareStatement("INSERT INTO joueur (NOM, PRENOM, SEXE) values (?, ?, ?)");
	        pstmt.setString(1, nouveauJoueur.getNom());
	        pstmt.setString(2, nouveauJoueur.getPrenom());
	        pstmt.setString(3, nouveauJoueur.getSexe());
	        pstmt.executeUpdate();
	        
	        connexion.close();
	    } catch (Exception e) {
	            System.out.println(e);
	        }
	}
	
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	//rechercher les infos d'un joueur grâce à son id
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
			connexion.close();
		}
		catch (Exception e){	
		}
		return joueurAModif;
	}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//mettre à jour les données d'un joueur
	public void updateJoueur(Long id, String nom, String prenom, String sexe) {
		try {
			connexion = daoFactory.getConnection();
			PreparedStatement pstmt = connexion.prepareStatement("UPDATE joueur SET NOM = ?, PRENOM = ? , SEXE= ? where ID = ?");
			pstmt.setString(1, nom);
			pstmt.setString(2, prenom);
			pstmt.setString(3, sexe);
			pstmt.setLong(4, id);
			pstmt.executeUpdate();
			
			connexion.close();
		}
		catch (Exception e){
			System.err.println(e);
		}
	}
	
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	//supprimer un joueur de la base de données
	public void deleteJoueur(Long id) {
		
//		//Rechercher id des matchs où le joueur est finaliste ou vainqueur
//		ArrayList<Long> listeIdMatchVainqueur = new ArrayList<Long>();
//		ArrayList<Long> listeIdMatchFinaliste = new ArrayList<Long>();
//		try {
//			connexion = daoFactory.getConnection();
//			PreparedStatement pstmt1 = connexion.prepareStatement("select id from match_tennis where ID_VAINQUEUR = ?");
//			pstmt1.setLong(1, id);
//			ResultSet rs =  pstmt1.executeQuery();
//			while(rs.next()) {
//				listeIdMatchVainqueur.add(rs.getLong("id"));
//			}
//		}
//		catch(Exception e) {
//			System.out.println(e);
//		}
//		
//		try {
//			connexion = daoFactory.getConnection();
//			PreparedStatement pstmt2 = connexion.prepareStatement("select id from match_tennis where ID_FINALISTE = ?");
//			pstmt2.setLong(1, id);
//			ResultSet rs =  pstmt2.executeQuery();
//			while(rs.next()) {
//				listeIdMatchFinaliste.add(rs.getLong("id"));
//			}
//		}
//		catch(Exception e) {
//			System.out.println(e);
//		}
//		
//		//Suppression de l'id du joueur dans la table match_tennis quand joueur est vainqueur
//		for (Long i : listeIdMatchVainqueur) {
//			try {
//				connexion = daoFactory.getConnection();
//				PreparedStatement pstmt3 = connexion.prepareStatement("UPDATE match_tennis SET ID_VAINQUEUR=0 where id = ?");
//				pstmt3.setLong(1, i);
//				pstmt3.executeUpdate();
//			}
//			catch(Exception e) {
//				System.out.println(e);
//			}
//		}
//		
//		//Suppression de l'id du joueur dans la table match_tennis quand joueur est finaliste
//		for (Long i : listeIdMatchFinaliste) {
//			try {
//				connexion = daoFactory.getConnection();
//				PreparedStatement pstmt4 = connexion.prepareStatement("UPDATE match_tennis SET ID_FINALISTE=0 where id = ?");
//				pstmt4.setLong(1, i);	
//				pstmt4.executeUpdate();
//			}
//			catch(Exception e) {
//				System.out.println(e);
//			}
//		}
		
		//suppression du joueur dans la table joueur
		try {
			connexion = daoFactory.getConnection();
			PreparedStatement pstmt5 = connexion.prepareStatement("DELETE from joueur where ID = ?");
			pstmt5.setLong(1, id);
			pstmt5.executeUpdate();
			
			connexion.close();
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//rechercher un joueur avec des lettres de son prénom ou de son nom
	@Override
	public List<Joueur> rechercher(String txt) {
		ArrayList<Joueur> listeJoueurRecherche= new ArrayList<Joueur>();
		try {
			connexion = daoFactory.getConnection();
			PreparedStatement pstmt = connexion.prepareStatement("select * from joueur where nom like '%' ? '%' or prenom like '%' ? '%'");
			pstmt.setString(1, txt);
			pstmt.setString(2, txt);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Joueur j = new Joueur();
				j.setId(rs.getLong("joueur.id"));
				j.setNom(rs.getString("joueur.nom"));
				j.setPrenom(rs.getString("joueur.prenom"));
				j.setSexe(rs.getString("joueur.sexe"));
				listeJoueurRecherche.add(j);
				}
			connexion.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listeJoueurRecherche;
	}


}
