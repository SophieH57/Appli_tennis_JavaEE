package com.mycompany.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mycompany.beans.User;

public class UserDaoImpl {
	private DaoFactory daoFactory;

	public UserDaoImpl(DaoFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}
	
	public UserDaoImpl() {
	}

	public User isValidLogin(String login, String password) {
		Connection connexion = null;
		PreparedStatement statement = null;
		User utilisateur = new User();
		try {
			connexion = daoFactory.getConnection();
			statement = connexion.prepareStatement("select * from tennis.connexion where connexion.login = ? and connexion.password = ?");
			statement.setString(1, login);
			statement.setString(2, password);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				utilisateur.setId(rs.getInt("connexion.id"));
				utilisateur.setLogin(rs.getString("connexion.login"));
				utilisateur.setPassword(rs.getString("connexion.password"));
				utilisateur.setProfil(rs.getInt("connexion.profil"));
				}
			else {
				return null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return utilisateur;
}
	
	public User logOut() {
		return null;
	}
}

