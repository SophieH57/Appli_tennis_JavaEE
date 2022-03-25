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
	
	//R�cup�rer la liste de tous les matchs
	@Override
	public List<Match> lister() {
		ArrayList<Match> listeMatchs= new ArrayList<Match>();
		try {
			connexion = daoFactory.getConnection();
			String sql1 = "select match_tennis.ID, epreuve.TYPE_EPREUVE, epreuve.ANNEE, tournoi.ID, tournoi.NOM, tournoi.code, match_tennis.ID_VAINQUEUR, match_tennis.ID_FINALISTE, joueur.ID, joueur.NOM, joueur.PRENOM "
					+ "from match_tennis inner join joueur inner join epreuve inner join tournoi "
					+ "where match_tennis.ID_EPREUVE = epreuve.ID and epreuve.ID_TOURNOI = tournoi.ID and match_tennis.ID_VAINQUEUR = joueur.ID";
			PreparedStatement matchTournoiVainqueur = connexion.prepareStatement(sql1);
			ResultSet rs = matchTournoiVainqueur.executeQuery();
			PreparedStatement matchFinaliste = connexion.prepareStatement("select * from joueur where ID = ?");
			while (rs.next()) {
				Match m = new Match();
				Joueur v = new Joueur();
				Joueur f = new Joueur();
				Tournoi t = new Tournoi();
				//r�cup�ration infos match
				m.setIdMatch(rs.getLong("match_tennis.id"));
				m.setTypeEpreuve(rs.getString("epreuve.type_epreuve"));
				m.setAnnee(rs.getInt("epreuve.annee"));
				
				//r�cup�ration infos tournoi
				t.setIdTournoi(rs.getInt("tournoi.id"));
				t.setNomTournoi(rs.getString("tournoi.nom"));
				t.setCodeTournoi(rs.getString("tournoi.code"));
				
				//r�cup�ration infos vainqueur
				v.setId(rs.getLong("match_tennis.id_vainqueur"));
				v.setNom(rs.getString("joueur.nom"));
				v.setPrenom(rs.getString("joueur.prenom"));
				
				//r�cup�ration infos finaliste
				f.setId(rs.getLong("match_tennis.id_finaliste"));
				matchFinaliste.setLong(1, f.getId());
				ResultSet rsF = matchFinaliste.executeQuery();
				if (rsF.next()) {
					f.setNom(rsF.getString("joueur.nom"));
					f.setPrenom(rsF.getString("joueur.prenom"));
					f.setSexe(rsF.getString("joueur.sexe"));
				}
				
				//ajout infos tournoi, vainqueur et id finaliste au match
				m.setTournoi(t);
				m.setVainqueur(v);
				m.setFinaliste(f);
				
				//ajout du match � la liste
				listeMatchs.add(m);
				}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
					
		return listeMatchs;
	}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//R�cup�rer les information d'un match par son id
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
			PreparedStatement infoMatch = connexion.prepareStatement(sql);
			infoMatch.setLong(1, id);
			PreparedStatement infoFinaliste = connexion.prepareStatement("select * from joueur where ID = ?");
			ResultSet rs = infoMatch.executeQuery();
			while (rs.next()) {
				//r�cup�ration infos match
				m.setIdMatch(rs.getLong("match_tennis.id"));
				m.setTypeEpreuve(rs.getString("epreuve.type_epreuve"));
				m.setAnnee(rs.getInt("epreuve.annee"));
				
				//r�cup�ration infos tournoi
				t.setIdTournoi(rs.getInt("tournoi.id"));
				t.setNomTournoi(rs.getString("tournoi.nom"));
				t.setCodeTournoi(rs.getString("tournoi.code"));
				
				//r�cup�ration infos vainqueur
				v.setId(rs.getLong("match_tennis.id_vainqueur"));
				v.setNom(rs.getString("joueur.nom"));
				v.setPrenom(rs.getString("joueur.prenom"));
				
				//r�cup�ration id finaliste
				f.setId(rs.getLong("match_tennis.id_finaliste"));
				infoFinaliste.setLong(1, f.getId());
				ResultSet rsF = infoFinaliste.executeQuery();
				if (rsF.next()) {
					f.setNom(rsF.getString("joueur.nom"));
					f.setPrenom(rsF.getString("joueur.prenom"));
					f.setSexe(rsF.getString("joueur.sexe"));
				}
				
				//ajout infos tournoi, vainqueur et id finaliste au match
				m.setTournoi(t);
				m.setVainqueur(v);
				m.setFinaliste(f);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
				
		return m;
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//Ajouter un nouveau match
	@Override
	public void ajouterMatch(Match nouveauMatch) {
		int idNouvelleEpreuve = 0;
		//cr�ation d'une nouvelle �preuve avec ann�e, type d'�preuve et id du tournoi
		try {
			connexion = daoFactory.getConnection();
			String sqlTableEpreuve = "insert into epreuve (annee, type_epreuve, id_tournoi) values (?,?,?)";
			PreparedStatement modifTableEpreuve = connexion.prepareStatement(sqlTableEpreuve);
			modifTableEpreuve.setInt(1, nouveauMatch.getAnnee());
			modifTableEpreuve.setString(2, nouveauMatch.getTypeEpreuve());
			modifTableEpreuve.setInt(3, nouveauMatch.getTournoi().getIdTournoi());
			modifTableEpreuve.executeUpdate();
		
		//r�cup�ration de l'id de l'�preuve nouvellement cr��e
			PreparedStatement idEpreuve = connexion.prepareStatement("select epreuve.id from epreuve where annee = ? and type_epreuve = ? and id_tournoi = ?");
			idEpreuve.setInt(1, nouveauMatch.getAnnee());
			idEpreuve.setString(2, nouveauMatch.getTypeEpreuve());
			idEpreuve.setInt(3, nouveauMatch.getTournoi().getIdTournoi());
			ResultSet rsE = idEpreuve.executeQuery();
			if (rsE.next()) {
				idNouvelleEpreuve = rsE.getInt("epreuve.id");
			}
		
		//cr�ation nouveau match avec id_epreuve, id_vainqueur, id_finaliste
			String sqlTableMatchTennis = "insert into match_tennis (ID_EPREUVE, ID_VAINQUEUR, ID_FINALISTE) values (?,?,?)";
			PreparedStatement modifTableMatch = connexion.prepareStatement(sqlTableMatchTennis);
			modifTableMatch.setInt(1, idNouvelleEpreuve);
			modifTableMatch.setLong(2, nouveauMatch.getVainqueur().getId());
			modifTableMatch.setLong(3, nouveauMatch.getFinaliste().getId());
			modifTableMatch.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//Modifier les informations d'un match par son id
	@Override
	public void updateMatch(Long id, Tournoi tournoi, int annee, String typeEpreuve, Joueur vainqueur, Joueur finaliste) {
		Long IdEpreuve = null;
		try {
			//r�cup�ration id de l'�preuve
			connexion = daoFactory.getConnection();
			String sqlIdEpreuve = "select match_tennis.ID_EPREUVE from match_tennis where ID = ?";
			PreparedStatement recupIdEpreuve = connexion.prepareStatement(sqlIdEpreuve);
			recupIdEpreuve.setLong(1, id);
			ResultSet rs = recupIdEpreuve.executeQuery();
			if (rs.next()) {
				IdEpreuve = rs.getLong("match_tennis.ID_EPREUVE");
			}
		
			//update de la table epreuve
			String sqlUpdateEpreuve = "UPDATE epreuve set annee = ?, type_epreuve = ?, id_tournoi = ? where id = ? ";
			PreparedStatement modifTableEpreuve = connexion.prepareStatement(sqlUpdateEpreuve);
			modifTableEpreuve.setInt(1, annee);
			modifTableEpreuve.setString(2, typeEpreuve);
			modifTableEpreuve.setLong(3, tournoi.getIdTournoi());
			modifTableEpreuve.setLong(4, IdEpreuve);
			modifTableEpreuve.executeUpdate();

			//update de la table match_tennis
			String sqlUpdateMatchTennis = "UPDATE match_tennis set id_epreuve = ?, id_vainqueur = ?, id_finaliste = ? where id= ?";
			PreparedStatement modifTableMatchTennis  = connexion.prepareStatement(sqlUpdateMatchTennis);
			modifTableMatchTennis.setLong(1, IdEpreuve);
			modifTableMatchTennis.setLong(2, vainqueur.getId());
			modifTableMatchTennis.setLong(3, finaliste.getId());
			modifTableMatchTennis.setLong(4, id);
			modifTableMatchTennis.executeUpdate();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}


///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//Supprimer les informations d'un match
	@Override
	public void deleteMatch(Long id) {
		Long IdEpreuve = null;
		
		try {
			//r�cup�rer id de l'�preuve
			connexion = daoFactory.getConnection();
			String sqlIdEpreuve = "select match_tennis.ID_EPREUVE from match_tennis where ID = ?";
			PreparedStatement recupIdEpreuve = connexion.prepareStatement(sqlIdEpreuve);
			recupIdEpreuve.setLong(1, id);
			ResultSet rs = recupIdEpreuve.executeQuery();
			if (rs.next()) {
				IdEpreuve = rs.getLong("match_tennis.ID_EPREUVE");
			}
			
			//supprimer le match
			PreparedStatement deleteInfoMatch = connexion.prepareStatement("delete from match_tennis where id = ?");
			deleteInfoMatch.setLong(1, id);
			deleteInfoMatch.executeUpdate();
			
			//supprimer l'epreuve
			PreparedStatement deleteInfoEpreuve = connexion.prepareStatement("delete from epreuve where id= ?");
			deleteInfoEpreuve.setLong(1, IdEpreuve);
			deleteInfoEpreuve.executeUpdate();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	//A impl�menter
	@Override
	public List<Match> rechercher(String txt) {
		ArrayList<Match> listeMatchRechercher = new ArrayList<Match>();
//		try {
//			connexion = daoFactory.getConnection();
//			String sql = "select match_tennis.ID, epreuve.TYPE_EPREUVE, epreuve.ANNEE, tournoi.ID, tournoi.NOM, tournoi.code, match_tennis.ID_VAINQUEUR, match_tennis.ID_FINALISTE, joueur.ID, joueur.NOM, joueur.PRENOM "
//					+ "from match_tennis inner join joueur inner join epreuve inner join tournoi "
//					+ "where match_tennis.ID_EPREUVE = epreuve.ID and epreuve.ID_TOURNOI = tournoi.ID and match_tennis.ID_VAINQUEUR = joueur.ID and "
//					+ "(tournoi.nom like '%' ? '%' or tournoi.code like '%' ? '%' or joueur.nom like '%' ? '%' or joueur.prenom like '%' ? '%')";
//			PreparedStatement pstmt = connexion.prepareStatement(sql);
//			pstmt.setString(1, txt);
//			pstmt.setString(2, txt);
//			pstmt.setString(3, txt);
//			pstmt.setString(4, txt);
//			ResultSet rs = pstmt.executeQuery();
//			while (rs.next()) {
//				Match m = new Match();
//				Joueur v = new Joueur();
//				Joueur f = new Joueur();
//				Tournoi t = new Tournoi();
//				//r�cup�ration infos match
//				m.setIdMatch(rs.getLong("match_tennis.id"));
//				m.setTypeEpreuve(rs.getString("epreuve.type_epreuve"));
//				m.setAnnee(rs.getInt("epreuve.annee"));
//				
//				//r�cup�ration infos tournoi
//				t.setIdTournoi(rs.getInt("tournoi.id"));
//				t.setNomTournoi(rs.getString("tournoi.nom"));
//				t.setCodeTournoi(rs.getString("tournoi.code"));
//				
//				//r�cup�ration infos vainqueur
//				v.setId(rs.getLong("match_tennis.id_vainqueur"));
//				v.setNom(rs.getString("joueur.nom"));
//				v.setPrenom(rs.getString("joueur.prenom"));
//				
//				//r�cup�ration id finaliste
//				f.setId(rs.getLong("match_tennis.id_finaliste"));
//				
//				//ajout infos tournoi, vainqueur et id finaliste au match
//				m.setTournoi(t);
//				m.setVainqueur(v);
//				m.setFinaliste(f);
//				
//				//ajout du match � la liste
//				listeMatchRechercher.add(m);
//				}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//		//recherche et ajout des infos du finaliste du match � partir de l'id
//		for (Match match : listeMatchRechercher) {
//			Long idFinaliste = match.getFinaliste().getId();
//			try {
//				connexion = daoFactory.getConnection();
//				String sql = "select * from joueur where ID = " + idFinaliste;
//				statement = connexion.prepareStatement(sql);
//				ResultSet rs = statement.executeQuery();
//				if (rs.next()) {
//					match.getFinaliste().setNom(rs.getString("joueur.nom"));
//					match.getFinaliste().setPrenom(rs.getString("joueur.prenom"));
//					match.getFinaliste().setSexe(rs.getString("joueur.sexe"));
//				}
//				
//			}catch (Exception e) {
//				e.printStackTrace();			
//		}
//			
		return listeMatchRechercher;
	
	}
}
