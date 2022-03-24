package com.mycompany.beans;

public class Match {
	private Long idMatch;
	private String typeEpreuve;
	private int annee;
	
	private Tournoi tournoi;
	private Joueur vainqueur;
	private Joueur finaliste;
	
	public Match() {};
	
	public Match(Long idMatch, String typeEpreuve, int annee, Tournoi tournoi, Joueur vainqueur, Joueur finaliste) {
		super();
		this.idMatch = idMatch;
		this.typeEpreuve = typeEpreuve;
		this.annee = annee;
		this.tournoi = tournoi;
		this.vainqueur = vainqueur;
		this.finaliste = finaliste;

	}

	public Long getIdMatch() {
		return idMatch;
	}

	public void setIdMatch(Long idMatch) {
		this.idMatch = idMatch;
	}

	public String getTypeEpreuve() {
		return typeEpreuve;
	}

	public void setTypeEpreuve(String typeEpreuve) {
		this.typeEpreuve = typeEpreuve;
	}

	public int getAnnee() {
		return annee;
	}

	public void setAnnee(int annee) {
		this.annee = annee;
	}


	public Tournoi getTournoi() {
		return tournoi;
	}

	public void setTournoi(Tournoi tournoi) {
		this.tournoi = tournoi;
	}

	public Joueur getVainqueur() {
		return vainqueur;
	}

	public void setVainqueur(Joueur vainqueur) {
		this.vainqueur = vainqueur;
	}

	public Joueur getFinaliste() {
		return finaliste;
	}

	public void setFinaliste(Joueur finaliste) {
		this.finaliste = finaliste;
	}

	

	
}
