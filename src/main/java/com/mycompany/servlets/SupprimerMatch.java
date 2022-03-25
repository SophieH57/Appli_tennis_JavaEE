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
 * Servlet implementation class SupprimerMatch
 */
@WebServlet("/SupprimerMatch")
public class SupprimerMatch extends HttpServlet {
	private static final long serialVersionUID = 1L;
	MatchDaoImpl mdi;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SupprimerMatch() {
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
		mdi.deleteMatch(Long.parseLong(request.getParameter("id")));
		response.sendRedirect("/Appli_tennis/ListMatchs");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
