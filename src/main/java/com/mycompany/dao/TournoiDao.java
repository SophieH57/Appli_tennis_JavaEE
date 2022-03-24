package com.mycompany.dao;

import java.util.List;

import com.mycompany.beans.Tournoi;

public interface TournoiDao {
	List <Tournoi> lister();
	Tournoi lecture(Long id);
	void ajouterTournoi(Tournoi nouveauTournoi);
	void updateTournoi(Long id, String nom, String code);
	void deleteTournoi(Long id);
}
