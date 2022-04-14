package com.mycompany.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mycompany.dao.DaoException;
import com.mycompany.dao.DaoFactory;
import com.mycompany.dao.JoueurDaoImpl;

/**
 * Servlet implementation class ListJoueur
 */
@WebServlet("/ListJoueur")
public class ListJoueur extends HttpServlet {
	private static final long serialVersionUID = 1L;
	JoueurDaoImpl jdi;
	String typePage = "pageJoueur";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListJoueur() {
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
		String choixSexe = request.getParameter("inputChoixSexe");
		request.setAttribute("choixSexe", choixSexe);
		if (request.getSession().getAttribute("utilisateur") != null) {
			try {
		request.setAttribute("liste", jdi.lister());
			}
			catch (DaoException e) {
				request.setAttribute("erreur", e.getMessage());
			}
			request.getSession().setAttribute("page", typePage);
		
			this.getServletContext().getRequestDispatcher("/WEB-INF/listjoueur.jsp").forward(request, response);
	} else {
		response.sendRedirect("/Appli_tennis/login");
	}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		String choixSexe = request.getParameter("inputChoixSexe");
//		request.setAttribute("choixSexe", choixSexe);
//		request.setAttribute("liste", jdi.rechercher(request.getParameter("txtsearch")));
//		this.getServletContext().getRequestDispatcher("/WEB-INF/listjoueur.jsp").forward(request, response);
		doGet(request, response);
	}

}
