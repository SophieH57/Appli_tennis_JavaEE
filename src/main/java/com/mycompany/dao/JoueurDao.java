package com.mycompany.dao;

import java.util.List;

import com.mycompany.beans.Joueur;

public interface JoueurDao {
	List <Joueur> lister();
	Joueur lecture(Long id);
	void ajouterJoueur(Joueur nouveauJoueur);
	void updateJoueur(Long id, String nom, String prenom, String sexe);
	void deleteJoueur(Long id);
}
