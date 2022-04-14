package com.mycompany.dao;

import java.util.List;

import com.mycompany.beans.Joueur;

public interface JoueurDao {
	List <Joueur> lister() throws DaoException;
	Joueur lecture(Long id);
	void ajouterJoueur(Joueur nouveauJoueur) throws DaoException;
	void updateJoueur(Joueur j) throws DaoException;
	void deleteJoueur(Long id);
	List <Joueur> rechercher(String txt);
}
