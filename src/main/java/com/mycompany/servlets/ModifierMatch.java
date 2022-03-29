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
 * Servlet implementation class ModifierMatch
 */
@WebServlet("/ModifierMatch")
public class ModifierMatch extends HttpServlet {
	private static final long serialVersionUID = 1L;
	JoueurDaoImpl jdi;
	TournoiDaoImpl tdi;
	MatchDaoImpl mdi;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModifierMatch() {
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
			Long idMatch = Long.parseLong(request.getParameter("id"));
			request.setAttribute("match", mdi.lecture(idMatch));
			request.setAttribute("listeTournois", tdi.lister());
			request.setAttribute("listeJoueurs", jdi.lister());
			this.getServletContext().getRequestDispatcher("/WEB-INF/modifiermatch.jsp").forward(request, response);
		} else {
			response.sendRedirect("/Appli_tennis/login");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Match m = new Match();
		Tournoi t = new Tournoi();
		Joueur v = new Joueur();
		Joueur f = new Joueur();
		t = tdi.lecture(Long.parseLong(request.getParameter("tournoi")));
		v = jdi.lecture(Long.parseLong(request.getParameter("vainqueur")));
		f = jdi.lecture(Long.parseLong(request.getParameter("finaliste")));
		m.setIdMatch(Long.parseLong(request.getParameter("idMatch")));
		m.setTournoi(t);
		m.setAnnee(Integer.parseInt(request.getParameter("annee")));
		m.setTypeEpreuve(request.getParameter("typeEpreuve"));
		m.setVainqueur(v);
		m.setFinaliste(f);
		System.out.println(m.getIdMatch());
		mdi.updateMatch(m.getIdMatch(), m.getTournoi(), m.getAnnee(), m.getTypeEpreuve(), m.getVainqueur(), m.getFinaliste());
		response.sendRedirect("/Appli_tennis/ListMatchs");
	}

}
