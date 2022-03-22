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
 * Servlet implementation class SupprimerJoueur
 */
@WebServlet("/SupprimerJoueur")
public class SupprimerJoueur extends HttpServlet {
	private static final long serialVersionUID = 1L;
	JoueurDaoImpl jdi;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SupprimerJoueur() {
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
		Long idJoueurASupprimer = Long.parseLong(request.getParameter("id"));
		request.setAttribute("joueur", jdi.lecture(idJoueurASupprimer));
		this.getServletContext().getRequestDispatcher("/WEB-INF/supprimerjoueur.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		jdi.deleteJoueur(Long.parseLong(request.getParameter("deleteId")));
		response.sendRedirect("/Appli_tennis/ListJoueur");		
	}

}
