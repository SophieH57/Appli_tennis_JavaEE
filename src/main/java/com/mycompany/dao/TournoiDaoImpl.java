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
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listeTournois;
	}

	@Override
	public Tournoi lecture(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void ajouterTournoi(Tournoi nouveauTournoi) {
		try {
			connexion = daoFactory.getConnection();
	        PreparedStatement pstmt = connexion.prepareStatement("INSERT INTO tournoi (NOM, CODE) values (?, ?)");
	        pstmt.setString(1, nouveauTournoi.getNomTournoi());
	        pstmt.setString(2, nouveauTournoi.getCodeTournoi());
	        pstmt.executeUpdate();
	    } catch (Exception e) {
	            System.out.println(e);
	        }
		
	}

	@Override
	public void updateTournoi(Long id, String nom, String prenom, String sexe) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteTournoi(Long id) {
		// TODO Auto-generated method stub
		
	}

}
