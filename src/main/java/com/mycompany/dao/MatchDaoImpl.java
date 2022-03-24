package com.mycompany.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mycompany.beans.Joueur;
import com.mycompany.beans.Match;
import com.mycompany.beans.Tournoi;

public class MatchDaoImpl implements MatchDao{
	private DaoFactory daoFactory;
	private Connection connexion = null;
	private PreparedStatement statement = null;
	JoueurDaoImpl jdi = new JoueurDaoImpl(daoFactory);
	
	public MatchDaoImpl(DaoFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}
	
	@Override
	public List<Match> lister() {
		ArrayList<Match> listeMatchs= new ArrayList<Match>();
		try {
			connexion = daoFactory.getConnection();
			String sql = "select match_tennis.ID, epreuve.TYPE_EPREUVE, epreuve.ANNEE, tournoi.ID, tournoi.NOM, tournoi.code, match_tennis.ID_VAINQUEUR, match_tennis.ID_FINALISTE, joueur.ID, joueur.NOM, joueur.PRENOM "
					+ "from match_tennis inner join joueur inner join epreuve inner join tournoi "
					+ "where match_tennis.ID_EPREUVE = epreuve.ID and epreuve.ID_TOURNOI = tournoi.ID and match_tennis.ID_VAINQUEUR = joueur.ID";
			statement = connexion.prepareStatement(sql);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				Match m = new Match();
				Joueur v = new Joueur();
				Joueur f = new Joueur();
				Tournoi t = new Tournoi();
				//récupération infos match
				m.setIdMatch(rs.getLong("match_tennis.id"));
				m.setTypeEpreuve(rs.getString("epreuve.type_epreuve"));
				m.setAnnee(rs.getInt("epreuve.annee"));
				
				//récupération infos tournoi
				t.setIdTournoi(rs.getInt("tournoi.id"));
				t.setNomTournoi(rs.getString("tournoi.nom"));
				t.setCodeTournoi(rs.getString("tournoi.code"));
				
				//récupération infos vainqueur
				v.setId(rs.getLong("match_tennis.id_vainqueur"));
				v.setNom(rs.getString("joueur.nom"));
				v.setPrenom(rs.getString("joueur.prenom"));
				
				//récupération id finaliste
				f.setId(rs.getLong("match_tennis.id_finaliste"));
				
				//ajout infos tournoi, vainqueur et id finaliste au match
				m.setTournoi(t);
				m.setVainqueur(v);
				m.setFinaliste(f);
				
				//ajout du match à la liste
				listeMatchs.add(m);
				}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//recherche et ajout des infos du finaliste du match à partir de l'id
		for (Match match : listeMatchs) {
			Long idFinaliste = match.getFinaliste().getId();
			try {
				connexion = daoFactory.getConnection();
				String sql = "select * from joueur where ID = " + idFinaliste;
				statement = connexion.prepareStatement(sql);
				ResultSet rs = statement.executeQuery();
				if (rs.next()) {
					match.getFinaliste().setNom(rs.getString("joueur.nom"));
					match.getFinaliste().setPrenom(rs.getString("joueur.prenom"));
					match.getFinaliste().setSexe(rs.getString("joueur.sexe"));
				}
				
			}catch (Exception e) {
				e.printStackTrace();
			}
			
		}
			
		return listeMatchs;
	}

	@Override
	public Match lecture(Long id) {
		Match m = new Match();
		Joueur v = new Joueur();
		Joueur f = new Joueur();
		Tournoi t = new Tournoi();
		try {
			connexion = daoFactory.getConnection();
			String sql = "select match_tennis.ID, epreuve.TYPE_EPREUVE, epreuve.ANNEE, tournoi.ID, tournoi.NOM, tournoi.code, match_tennis.ID_VAINQUEUR, match_tennis.ID_FINALISTE, joueur.ID, joueur.NOM, joueur.PRENOM "
					+ "from match_tennis inner join joueur inner join epreuve inner join tournoi "
					+ "where match_tennis.ID_EPREUVE = epreuve.ID and epreuve.ID_TOURNOI = tournoi.ID and match_tennis.ID_VAINQUEUR = joueur.ID and match_tennis.ID = ?";
			PreparedStatement pstmt = connexion.prepareStatement(sql);
			pstmt.setLong(1, id);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				//récupération infos match
				m.setIdMatch(rs.getLong("match_tennis.id"));
				m.setTypeEpreuve(rs.getString("epreuve.type_epreuve"));
				m.setAnnee(rs.getInt("epreuve.annee"));
				
				//récupération infos tournoi
				t.setIdTournoi(rs.getInt("tournoi.id"));
				t.setNomTournoi(rs.getString("tournoi.nom"));
				t.setCodeTournoi(rs.getString("tournoi.code"));
				
				//récupération infos vainqueur
				v.setId(rs.getLong("match_tennis.id_vainqueur"));
				v.setNom(rs.getString("joueur.nom"));
				v.setPrenom(rs.getString("joueur.prenom"));
				
				//récupération id finaliste
				f.setId(rs.getLong("match_tennis.id_finaliste"));
				
				//ajout infos tournoi, vainqueur et id finaliste au match
				m.setTournoi(t);
				m.setVainqueur(v);
				m.setFinaliste(f);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//récupération des infos du finaliste grâce à son id
		Long idFinaliste = m.getFinaliste().getId();
		try {
			connexion = daoFactory.getConnection();
			String sql = "select * from joueur where ID = " + idFinaliste;
			statement = connexion.prepareStatement(sql);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				m.getFinaliste().setNom(rs.getString("joueur.nom"));
				m.getFinaliste().setPrenom(rs.getString("joueur.prenom"));
				m.getFinaliste().setSexe(rs.getString("joueur.sexe"));
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return m;
	}

	@Override
	public void ajouterMatch(Match nouveauMatch) {
		//création d'une nouvelle épreuve avec année, type d'épreuve et id du tournoi
		try {
			connexion = daoFactory.getConnection();
			String sqlTableEpreuve = "insert into epreuve (annee, type_epreuve, id_tournoi) values (?,?,?)";
			PreparedStatement pstmt = connexion.prepareStatement(sqlTableEpreuve);
			pstmt.setInt(1, nouveauMatch.getAnnee());
			pstmt.setString(2, nouveauMatch.getTypeEpreuve());
			pstmt.setInt(3, nouveauMatch.getTournoi().getIdTournoi());
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//récupération de l'id de l'épreuve nouvellement créée
		int idNouvelleEpreuve = 0;
		try {
			connexion = daoFactory.getConnection();
			String sqlIdEpreuve = "select epreuve.id from epreuve where annee = ? and type_epreuve = ? and id_tournoi = ?";
			PreparedStatement pstmt = connexion.prepareStatement(sqlIdEpreuve);
			pstmt.setInt(1, nouveauMatch.getAnnee());
			pstmt.setString(2, nouveauMatch.getTypeEpreuve());
			pstmt.setInt(3, nouveauMatch.getTournoi().getIdTournoi());
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				idNouvelleEpreuve = rs.getInt("epreuve.id");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//création nouveau match avec id_epreuve, id_vainqueur, id_finaliste
		try {
			connexion = daoFactory.getConnection();
			String sqlTableMatchTennis = "insert into match_tennis (ID_EPREUVE, ID_VAINQUEUR, ID_FINALISTE) values (?,?,?)";
			PreparedStatement pstmt = connexion.prepareStatement(sqlTableMatchTennis);
			pstmt.setInt(1, idNouvelleEpreuve);
			pstmt.setLong(2, nouveauMatch.getVainqueur().getId());
			pstmt.setLong(3, nouveauMatch.getFinaliste().getId());
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void updateMatch(Long id, Tournoi tournoi, int annee, String typeEpreuve, Joueur vainqueur, Joueur finaliste) {
		Long IdEpreuve = null;
		try {
			//récupération id de l'épreuve
			connexion = daoFactory.getConnection();
			String sqlIdEpreuve = "select match_tennis.ID_EPREUVE from match_tennis where ID = ?";
			PreparedStatement pstmt1 = connexion.prepareStatement(sqlIdEpreuve);
			pstmt1.setLong(1, id);
			ResultSet rs = pstmt1.executeQuery();
			if (rs.next()) {
				IdEpreuve = rs.getLong("match_tennis.ID_EPREUVE");
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			//update de la table epreuve
			connexion = daoFactory.getConnection();
			String sqlUpdateEpreuve = "UPDATE epreuve set annee = ?, type_epreuve = ?, id_tournoi = ? where id = ? ";
			PreparedStatement pstmt2 = connexion.prepareStatement(sqlUpdateEpreuve);
			pstmt2.setInt(1, annee);
			pstmt2.setString(2, typeEpreuve);
			pstmt2.setLong(3, tournoi.getIdTournoi());
			pstmt2.setLong(4, IdEpreuve);
			pstmt2.executeUpdate();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			//update de la table match_tennis
			connexion = daoFactory.getConnection();
			String sqlUpdateMatchTennis = "UPDATE match_tennis set id_epreuve = ?, id_vainqueur = ?, id_finaliste = ? where id= ?";
			PreparedStatement pstmt3  = connexion.prepareStatement(sqlUpdateMatchTennis);
			pstmt3.setLong(1, IdEpreuve);
			pstmt3.setLong(2, vainqueur.getId());
			pstmt3.setLong(3, finaliste.getId());
			pstmt3.setLong(4, id);
			pstmt3.executeUpdate();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
		
	@Override
	public void deleteMatch(Long id) {
		// TODO Auto-generated method stub
		
	}
	

}
