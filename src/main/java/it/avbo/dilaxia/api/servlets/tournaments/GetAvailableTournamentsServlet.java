/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package it.avbo.dilaxia.api.servlets.tournaments;

import com.google.gson.Gson;
import it.avbo.dilaxia.api.database.TournamentSource;
import it.avbo.dilaxia.api.entities.Tournament;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 *
 * @author Federico
 */
@WebServlet("/tournaments/available")
public class GetAvailableTournamentsServlet extends HttpServlet {

    private final Gson gson = new Gson();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!request.isRequestedSessionIdValid()) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        Optional<Tournament[]> result = TournamentSource.getAllTournaments();

        if (result.isPresent()) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().print(gson.toJson(result.get()));
            return;
        }

        response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
