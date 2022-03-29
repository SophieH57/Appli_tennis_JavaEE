package com.mycompany.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mycompany.beans.Joueur;
import com.mycompany.beans.Match;
import com.mycompany.beans.Tournoi;
import com.mycompany.dao.DaoFactory;
import com.mycompany.dao.JoueurDaoImpl;
import com.mycompany.dao.MatchDaoImpl;
import com.mycompany.dao.TournoiDaoImpl;

/**
 * Servlet implementation class AjouterMatch
 */
@WebServlet("/AjouterMatch")
public class AjouterMatch extends HttpServlet {
	private static final long serialVersionUID = 1L;
	TournoiDaoImpl tdi;
	JoueurDaoImpl jdi;
	MatchDaoImpl mdi;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AjouterMatch() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    @Override
    public void init() throws ServletException {
    	// TODO Auto-generated method stub
    	super.init();
    	DaoFactory daof1 = DaoFactory.getInstance();
    	tdi = new TournoiDaoImpl(daof1);
    	DaoFactory daof2 = DaoFactory.getInstance();
    	jdi = new JoueurDaoImpl(daof2);
    	DaoFactory daof3 = DaoFactory.getInstance();
    	mdi = new MatchDaoImpl(daof3);
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getSession().getAttribute("utilisateur") != null) {
			request.setAttribute("listeTournois", tdi.lister());
			request.setAttribute("listeJoueurs", jdi.lister());
			this.getServletContext().getRequestDispatcher("/WEB-INF/ajoutermatch.jsp").forward(request, response);
		} else {
			response.sendRedirect("/Appli_tennis/login");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Match nouveauMatch = new Match();
		Tournoi t = new Tournoi();
		Joueur v = new Joueur();
		Joueur f = new Joueur();
		t = tdi.lecture(Long.parseLong(request.getParameter("tournoi")));
		v = jdi.lecture(Long.parseLong(request.getParameter("vainqueur")));
		f = jdi.lecture(Long.parseLong(request.getParameter("finaliste")));
		nouveauMatch.setTournoi(t);
		nouveauMatch.setAnnee(Integer.parseInt(request.getParameter("annee")));
		nouveauMatch.setTypeEpreuve(request.getParameter("typeEpreuve"));
		nouveauMatch.setVainqueur(v);
		nouveauMatch.setFinaliste(f);
		mdi.ajouterMatch(nouveauMatch);
		response.sendRedirect("/Appli_tennis/ListMatchs");
	}

}
