package com.mycompany.beans;

public class Joueur {
	private Long id;
	private String nom;
	private String prenom;
	private String sexe;
	
	public Joueur(Long id, String nom, String prenom, String sexe) {
		super();
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.sexe = sexe;
	}
	
	public Joueur(String nom, String prenom, String sexe) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.sexe = sexe;
	}
	
	public Joueur() {};
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getSexe() {
		return sexe;
	}
	public void setSexe(String sexe) {
		this.sexe = sexe;
	}
	
	
}
