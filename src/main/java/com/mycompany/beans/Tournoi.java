package com.mycompany.beans;

public class Tournoi {
	private int IdTournoi;
	private String nomTournoi;
	private String codeTournoi;
	
	public Tournoi() {};
	
	public Tournoi(int id, String nomT, String codeT) {
		this.IdTournoi = id;
		this.nomTournoi = nomT;
		this.codeTournoi = codeT;
	}

	public Tournoi(String nomT, String codeT) {
		this.nomTournoi = nomT;
		this.codeTournoi = codeT;
	}

	public int getIdTournoi() {
		return IdTournoi;
	}

	public void setIdTournoi(int idTournoi) {
		IdTournoi = idTournoi;
	}

	public String getNomTournoi() {
		return nomTournoi;
	}

	public void setNomTournoi(String nomTournoi) {
		this.nomTournoi = nomTournoi;
	}

	public String getCodeTournoi() {
		return codeTournoi;
	}

	public void setCodeTournoi(String codeTournoi) {
		this.codeTournoi = codeTournoi;
	}
	
	
}
