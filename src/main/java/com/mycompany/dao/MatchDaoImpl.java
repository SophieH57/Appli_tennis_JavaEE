package com.mycompany.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.mycompany.beans.Joueur;
import com.mycompany.beans.Match;
import com.mycompany.beans.Tournoi;

public class MatchDaoImpl implements MatchDao{
	private DaoFactory daoFactory;

	private Connection connexion = null;
	JoueurDaoImpl jdi;
	TournoiDaoImpl tdi;

	public MatchDaoImpl(DaoFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
		jdi = new JoueurDaoImpl(daoFactory);
		tdi = new TournoiDaoImpl(daoFactory);
	}
	
	//Récupérer la liste de tous les matchs
	@Override
	public List<Match> lister() {
		ArrayList<Match> listeMatchs= new ArrayList<Match>();
		Long nombreMatchs = 0l;
		
		try {
			connexion = daoFactory.getConnection();
			
			//récupération du nombre de match dans la base de données
			String ReqNbMatch = "select count(id) from match_tennis";
			PreparedStatement nbMatch = connexion.prepareStatement(ReqNbMatch);
			ResultSet rsNbMatch = nbMatch.executeQuery();
			if (rsNbMatch.next()) {
				nombreMatchs = rsNbMatch.getLong(1);
			}
			
			//Pour chaque match, récupération des infos du tournoi et des joueurs
			for (Long i = 1l; i<=nombreMatchs; i++) {
				listeMatchs.add(lecture(i));
				
			}
			connexion.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
		return listeMatchs;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//Récupérer les information d'un match par son id
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
			connexion.close();
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
		//création d'une nouvelle épreuve avec année, type d'épreuve et id du tournoi
		try {
			connexion = daoFactory.getConnection();
			String sqlTableEpreuve = "insert into epreuve (annee, type_epreuve, id_tournoi) values (?,?,?)";
			PreparedStatement modifTableEpreuve = connexion.prepareStatement(sqlTableEpreuve);
			modifTableEpreuve.setInt(1, nouveauMatch.getAnnee());
			modifTableEpreuve.setString(2, nouveauMatch.getTypeEpreuve());
			modifTableEpreuve.setInt(3, nouveauMatch.getTournoi().getIdTournoi());
			modifTableEpreuve.executeUpdate();
		
		//récupération de l'id de l'épreuve nouvellement créée
			PreparedStatement idEpreuve = connexion.prepareStatement("select epreuve.id from epreuve where annee = ? and type_epreuve = ? and id_tournoi = ?");
			idEpreuve.setInt(1, nouveauMatch.getAnnee());
			idEpreuve.setString(2, nouveauMatch.getTypeEpreuve());
			idEpreuve.setInt(3, nouveauMatch.getTournoi().getIdTournoi());
			ResultSet rsE = idEpreuve.executeQuery();
			if (rsE.next()) {
				idNouvelleEpreuve = rsE.getInt("epreuve.id");
			}
		
		//création nouveau match avec id_epreuve, id_vainqueur, id_finaliste
			String sqlTableMatchTennis = "insert into match_tennis (ID_EPREUVE, ID_VAINQUEUR, ID_FINALISTE) values (?,?,?)";
			PreparedStatement modifTableMatch = connexion.prepareStatement(sqlTableMatchTennis);
			modifTableMatch.setInt(1, idNouvelleEpreuve);
			modifTableMatch.setLong(2, nouveauMatch.getVainqueur().getId());
			modifTableMatch.setLong(3, nouveauMatch.getFinaliste().getId());
			modifTableMatch.executeUpdate();
			
			connexion.close();
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
			//récupération id de l'épreuve
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
			
			connexion.close();
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
			//récupérer id de l'épreuve
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
			
			connexion.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//Rechercher les infos d'un match par présence d'une chaine de caractère dans le nom ou le prénom d'un joueur, le nom ou le code d'un tournoi ou dans l'année de l'épreuve
	@Override
	public List<Match> rechercher(String txt) {
		ArrayList<Match> listeMatchRechercher = new ArrayList<Match>();
		ArrayList<Long> listeIndexMatch = new ArrayList<Long>();
		ArrayList<Tournoi> listeTournois;
		ArrayList<Joueur> listeJoueurs;
		
		try {
			connexion = daoFactory.getConnection();
			//recherche des tournois correspondant à la recherche
			listeTournois = (ArrayList<Tournoi>) tdi.rechercher(txt);
			
			for (Tournoi t : listeTournois) {
				PreparedStatement MatchParTournoi = connexion.prepareStatement("select match_tennis.ID from match_tennis inner join epreuve inner join tournoi where tournoi.ID = epreuve.ID_TOURNOI and epreuve.ID = match_tennis.ID_EPREUVE and tournoi.ID = ? ");
				MatchParTournoi.setLong(1, t.getIdTournoi());
				ResultSet rsMT = MatchParTournoi.executeQuery();
				while (rsMT.next()) {
					Long idM = rsMT.getLong("match_tennis.ID");
					listeIndexMatch.add(idM);
				}
			}
			
			//recherche des joueurs correspondant à la recherche
			listeJoueurs = (ArrayList<Joueur>) jdi.rechercher(txt);
			
			for (Joueur j : listeJoueurs) {
				PreparedStatement JoueurParTournoi = connexion.prepareStatement("select match_tennis.ID from match_tennis inner join joueur where (joueur.ID = match_tennis.ID_FINALISTE or joueur.ID = match_tennis.ID_VAINQUEUR) and joueur.id = ? ");
				JoueurParTournoi.setLong(1, j.getId());
				ResultSet rsJT = JoueurParTournoi.executeQuery();
				while (rsJT.next()) {
					Long idMj = rsJT.getLong("match_tennis.id");
					if (!listeIndexMatch.contains(idMj)) listeIndexMatch.add(idMj);
				}
			}
			
			//recherche des années correspondant à la recherche
			PreparedStatement rechercheParAnnee = connexion.prepareStatement("select match_tennis.ID from match_tennis inner join epreuve where match_tennis.ID_EPREUVE = epreuve.ID and epreuve.ANNEE like '%' ? '%'");
			rechercheParAnnee.setString(1, txt);
			ResultSet rsAnnee = rechercheParAnnee.executeQuery();
			while (rsAnnee.next()) {
				Long idMa = rsAnnee.getLong("match_tennis.ID");
				if (!listeIndexMatch.contains(idMa)) listeIndexMatch.add(idMa);
			}
			
			//recherche des matchs par leur id et ajout à la liste des matchs
			for (Long id : listeIndexMatch) {
				listeMatchRechercher.add(lecture(id));
			}
						
			connexion.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
					
		return listeMatchRechercher;
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//Récupérer les années
	public Set<String> listingAnnees(){
		Set<String> listeAnnees = new HashSet<String>();
		try {
			connexion = daoFactory.getConnection();
			PreparedStatement ps = connexion.prepareStatement("select annee from epreuve");
			ResultSet rsListeAnnees = ps.executeQuery();
			while(rsListeAnnees.next()) {
				Integer annee = rsListeAnnees.getInt("annee");
				listeAnnees.add(Integer.toString(annee));
			}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
				
	return listeAnnees;
}
}
