package com.mycompany.dao;

import java.util.List;

import com.mycompany.beans.Joueur;

public interface JoueurDao {
	void ajouter (Joueur joueur);
	List <Joueur> lister();
	Joueur lecture(Long id);
}
