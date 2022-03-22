package com.mycompany.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mycompany.beans.Tournoi;
import com.mycompany.dao.DaoFactory;
import com.mycompany.dao.TournoiDaoImpl;

/**
 * Servlet implementation class AjouterTournoi
 */
@WebServlet("/AjouterTournoi")
public class AjouterTournoi extends HttpServlet {
	private static final long serialVersionUID = 1L;
	TournoiDaoImpl tdi;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AjouterTournoi() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    @Override
    public void init() throws ServletException {
    	// TODO Auto-generated method stub
    	super.init();
    	DaoFactory daoF = DaoFactory.getInstance();
    	tdi = new TournoiDaoImpl(daoF);
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher("/WEB-INF/ajouterTournoi.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Tournoi nouveauT = new Tournoi(request.getParameter("inputNomTournoi"), request.getParameter("inputCodeTournoi"));
		tdi.ajouterTournoi(nouveauT);
		response.sendRedirect("/Appli_tennis/ListTournois");
	}

}
