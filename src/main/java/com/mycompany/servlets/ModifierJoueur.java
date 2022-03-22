package com.mycompany.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mycompany.dao.DaoFactory;
import com.mycompany.dao.JoueurDaoImpl;

/**
 * Servlet implementation class ModifierJoueur
 */
@WebServlet("/ModifierJoueur")
public class ModifierJoueur extends HttpServlet {
	private static final long serialVersionUID = 1L;
	JoueurDaoImpl jdi;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModifierJoueur() {
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
		Long idJoueurAModifier = Long.parseLong(request.getParameter("id"));
		request.setAttribute("joueur", jdi.lecture(idJoueurAModifier));
		this.getServletContext().getRequestDispatcher("/WEB-INF/modifierjoueur.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nom = request.getParameter("modifNom");
		String prenom = request.getParameter("modifPrenom");
		String sexe = request.getParameter("modifSexe");
		Long id = Long.parseLong(request.getParameter("modifId"));
		jdi.updateJoueur(id, nom, prenom, sexe);
		response.sendRedirect("/Appli_tennis/ListJoueur");
	}

}
