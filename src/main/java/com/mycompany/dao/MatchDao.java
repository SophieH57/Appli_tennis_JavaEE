package com.mycompany.dao;

import java.util.List;

import com.mycompany.beans.Joueur;
import com.mycompany.beans.Match;
import com.mycompany.beans.Tournoi;


public interface MatchDao {
	List <Match> lister();
	Match lecture(Long id);
	void ajouterMatch(Match nouveauMatch);
	public void updateMatch(Long id, Tournoi tournoi, int annee, String typeEpreuve, Joueur vainqueur, Joueur finaliste);
	void deleteMatch(Long id);
}
