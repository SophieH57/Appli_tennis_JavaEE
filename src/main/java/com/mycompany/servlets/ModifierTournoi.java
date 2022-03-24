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
 * Servlet implementation class ModifierTournoi
 */
@WebServlet("/ModifierTournoi")
public class ModifierTournoi extends HttpServlet {
	private static final long serialVersionUID = 1L;
	TournoiDaoImpl tdi;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModifierTournoi() {
        super();
        // TODO Auto-generated constructor stub
        DaoFactory daoF = DaoFactory.getInstance();
        tdi = new TournoiDaoImpl(daoF);
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Long idTournoiModif = Long.parseLong(request.getParameter("id"));
		request.setAttribute("tournoi", tdi.lecture(idTournoiModif));
		this.getServletContext().getRequestDispatcher("/WEB-INF/modifiertournoi.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nomT = request.getParameter("modifNomTournoi");
		String codeT = request.getParameter("modifCodeTournoi");
		Long id = Long.parseLong(request.getParameter("modifIdTournoi"));
		tdi.updateTournoi(id, nomT, codeT);
		response.sendRedirect("/Appli_tennis/ListTournois");
	}

}
