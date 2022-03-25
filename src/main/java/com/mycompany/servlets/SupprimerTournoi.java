package com.mycompany.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mycompany.dao.DaoFactory;
import com.mycompany.dao.TournoiDaoImpl;

/**
 * Servlet implementation class SupprimerTournoi
 */
@WebServlet("/SupprimerTournoi")
public class SupprimerTournoi extends HttpServlet {
	private static final long serialVersionUID = 1L;
	TournoiDaoImpl tdi;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SupprimerTournoi() {
        super();
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
		tdi.deleteTournoi(Long.parseLong(request.getParameter("id")));
		response.sendRedirect("/Appli_tennis/ListTournois");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("/Appli_tennis/ListTournois");	
	}

}
