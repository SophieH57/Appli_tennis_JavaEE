package com.mycompany.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mycompany.dao.DaoFactory;
import com.mycompany.dao.JoueurDaoImpl;
import com.mycompany.dao.MatchDaoImpl;
import com.mycompany.dao.TournoiDaoImpl;

/**
 * Servlet implementation class RechercherJoueur
 */
@WebServlet("/Rechercher")
public class Rechercher extends HttpServlet {
	private static final long serialVersionUID = 1L;
	JoueurDaoImpl jdi;
	MatchDaoImpl mdi;
	TournoiDaoImpl tdi;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Rechercher() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    @Override
    public void init() throws ServletException {
    	// TODO Auto-generated method stub
    	super.init();
    	DaoFactory daoF1 = DaoFactory.getInstance();
    	jdi = new JoueurDaoImpl(daoF1);
    	DaoFactory daoF2 = DaoFactory.getInstance();
    	mdi = new MatchDaoImpl(daoF2);
    	DaoFactory daoF3 = DaoFactory.getInstance();
    	tdi = new TournoiDaoImpl(daoF3);    	
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String typePage = (String) request.getSession().getAttribute("page");
		
		switch (typePage) {
		case "pageJoueur" : 
			request.setAttribute("liste", jdi.rechercher(request.getParameter("txtsearch")));
			this.getServletContext().getRequestDispatcher("/WEB-INF/listjoueur.jsp").forward(request, response);
			break;
		case "pageMatch" :
			request.setAttribute("listeMatchs", mdi.rechercher(request.getParameter("txtsearch")));
			this.getServletContext().getRequestDispatcher("/WEB-INF/listmatchs.jsp").forward(request, response);
			break;
		case "pageTournoi":
			request.setAttribute("listeTournois", tdi.rechercher(request.getParameter("txtsearch")));
			this.getServletContext().getRequestDispatcher("/WEB-INF/listtournois.jsp").forward(request, response);
			break;
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
