package com.mycompany.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mycompany.beans.Tournoi;

public class TournoiDaoImpl implements TournoiDao{
	private DaoFactory daoFactory;
	private Connection connexion = null;
	private PreparedStatement statement = null;
	
	public TournoiDaoImpl(DaoFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}

	//Récupération liste de tous les tournois
	@Override
	public List <Tournoi> lister() {
		ArrayList<Tournoi> listeTournois= new ArrayList<Tournoi>();
		try {
			connexion = daoFactory.getConnection();
			statement = connexion.prepareStatement("select * from tournoi");
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				Tournoi t = new Tournoi();
				t.setIdTournoi(rs.getInt("tournoi.id"));
				t.setNomTournoi(rs.getString("tournoi.nom"));
				t.setCodeTournoi(rs.getString("tournoi.code"));
				listeTournois.add(t);
				}
			
			connexion.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listeTournois;
	}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//Récupération des infos d'un tournoi par son id
	@Override
	public Tournoi lecture(Long id) {
		Tournoi tournoiModif = new Tournoi();
		try {
			connexion = daoFactory.getConnection();
			PreparedStatement pstmt = connexion.prepareStatement("select * from tournoi where tournoi.id = ?");
			pstmt.setLong(1, id);
			ResultSet rs =  pstmt.executeQuery();
			if (rs.next()) {
				tournoiModif.setIdTournoi(rs.getInt("tournoi.id"));
				tournoiModif.setNomTournoi(rs.getString("tournoi.nom"));
				tournoiModif.setCodeTournoi(rs.getString("tournoi.code"));
			}
			
			connexion.close();
		}
		catch (Exception e){	
		}
		return tournoiModif;
	}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//Ajouter un nouveau tournoi
	@Override
	public void ajouterTournoi(Tournoi nouveauTournoi) {
		try {
			connexion = daoFactory.getConnection();
	        PreparedStatement pstmt = connexion.prepareStatement("INSERT INTO tournoi (NOM, CODE) values (?, ?)");
	        pstmt.setString(1, nouveauTournoi.getNomTournoi());
	        pstmt.setString(2, nouveauTournoi.getCodeTournoi());
	        pstmt.executeUpdate();
	        
	        connexion.close();
	    } catch (Exception e) {
	            System.out.println(e);
	        }
		
	}

	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//Modifier infos d'un tournoi
	@Override
	public void updateTournoi(Long id, String nomT, String codeT) {
		try {
			connexion = daoFactory.getConnection();
			PreparedStatement pstmt = connexion.prepareStatement("UPDATE tournoi SET NOM = ?, CODE = ? where ID = ?");
			pstmt.setString(1, nomT);
			pstmt.setString(2, codeT);
			pstmt.setLong(3, id);
			pstmt.executeUpdate();
			
			connexion.close();
		}
		catch (Exception e){
			System.err.println(e);
		}
		
	}

	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//Supprimer un tournoi
	@Override
	public void deleteTournoi(Long id) {
		try {
			connexion = daoFactory.getConnection();
			PreparedStatement pstmt = connexion.prepareStatement("DELETE from tournoi where ID = ?");
			pstmt.setLong(1, id);
			pstmt.executeUpdate();
			
			connexion.close();
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}

	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//Liste des tournois par recherche de texte dans le nom ou le code du tournoi
	@Override
	public List<Tournoi> rechercher(String txt) {
		ArrayList<Tournoi> listeRechercheTournois= new ArrayList<Tournoi>();
		try {
			connexion = daoFactory.getConnection();
			PreparedStatement pstmt = connexion.prepareStatement("select * from tournoi where nom like '%' ? '%' or code like '%' ? '%'");
			pstmt.setString(1, txt);
			pstmt.setString(2, txt);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Tournoi t = new Tournoi();
				t.setIdTournoi(rs.getInt("tournoi.id"));
				t.setNomTournoi(rs.getString("tournoi.nom"));
				t.setCodeTournoi(rs.getString("tournoi.code"));
				listeRechercheTournois.add(t);
				}
			
			connexion.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listeRechercheTournois;
	}

}
