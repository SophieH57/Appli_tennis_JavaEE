package com.mycompany.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mycompany.beans.BeanException;
import com.mycompany.beans.Joueur;
import com.mycompany.dao.DaoException;
import com.mycompany.dao.DaoFactory;
import com.mycompany.dao.JoueurDaoImpl;

/**
 * Servlet implementation class AjouterJoueur
 */
@WebServlet("/AjouterJoueur")
public class AjouterJoueur extends HttpServlet {
	private static final long serialVersionUID = 1L;
	JoueurDaoImpl jdi;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AjouterJoueur() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    @Override
    public void init() throws ServletException {
    	// TODO Auto-generated method stub
    	super.init();
        DaoFactory daoF = DaoFactory.getInstance(); //création instance DaoFactory  
        jdi = new JoueurDaoImpl(daoF);
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getSession().getAttribute("utilisateur") != null) {
			this.getServletContext().getRequestDispatcher("/WEB-INF/ajouterJoueur.jsp").forward(request, response);
		} else {
			response.sendRedirect("/Appli_tennis/login");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Joueur nouveauJ = new Joueur();
		try {
			nouveauJ.setNom(request.getParameter("inputNom"));
			nouveauJ.setPrenom(request.getParameter("inputPrenom"));
			nouveauJ.setSexe(request.getParameter("inputSexe"));
			jdi.ajouterJoueur(nouveauJ);
			response.sendRedirect("/Appli_tennis/ListJoueur");
		} catch (BeanException | DaoException e) {
			request.setAttribute("erreur", e.getMessage());
			this.getServletContext().getRequestDispatcher("/WEB-INF/ajouterJoueur.jsp").forward(request, response);
		}
	}
}
