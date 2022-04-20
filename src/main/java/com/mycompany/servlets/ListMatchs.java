package com.mycompany.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mycompany.dao.DaoFactory;
import com.mycompany.dao.MatchDaoImpl;

/**
 * Servlet implementation class ListMatchs
 */
@WebServlet("/ListMatchs")
public class ListMatchs extends HttpServlet {
	private static final long serialVersionUID = 1L;
	MatchDaoImpl mdi;
	String typePage = "pageMatch";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListMatchs() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    @Override
    public void init() throws ServletException {
    	// TODO Auto-generated method stub
    	super.init();
    	DaoFactory daoF = DaoFactory.getInstance();
    	mdi = new MatchDaoImpl(daoF);
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String choixTypeJoueur = request.getParameter("inputChoix");
		String anneeChoisie = request.getParameter("choixAnnee");
		String epreuveChoisie = request.getParameter("choixEpreuve");
		if (choixTypeJoueur == null) choixTypeJoueur = "Tous";
		if (anneeChoisie == null) anneeChoisie = "Toutes";
		if (epreuveChoisie == null) epreuveChoisie = "Toutes";
		if (request.getSession().getAttribute("utilisateur") != null) {
			request.setAttribute("listeMatchs", mdi.lister());
			request.setAttribute("listeAnnees", mdi.listingAnnees());
			request.getSession().setAttribute("page", typePage);
			request.setAttribute("choixTypeJoueur", choixTypeJoueur);
			request.setAttribute("anneeChoisie", anneeChoisie);
			request.setAttribute("epreuveChoisie", epreuveChoisie);
			this.getServletContext().getRequestDispatcher("/WEB-INF/listmatchs.jsp").forward(request, response);
		} else {
			response.sendRedirect("/Appli_tennis/login");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
