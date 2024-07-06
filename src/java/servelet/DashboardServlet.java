/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servelet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.stream.Collectors;
import classes.*;

/**
 *
 * @author ASUS
 */
public class DashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet DashboardServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DashboardServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
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
        try {
            SalleCulture salleCulture = new SalleCulture();
            Plante plante = new Plante();
            JournalCulture journalCulture = new JournalCulture();
            Recolte recolte = new Recolte();
            
            List<SalleCulture> salles = salleCulture.getAll();
            List<Plante> plantes = plante.getAll();
            List<JournalCulture> journaux = journalCulture.getAll();
            List<Recolte> recoltes = recolte.getAll();
            
            request.setAttribute("salles", salles);
            request.setAttribute("plantes", plantes);
            request.setAttribute("journaux", journaux);
            request.setAttribute("recoltes", recoltes);
            
            // Convert data to JSON format for Chart.js
            String salleNoms = salles.stream().map(SalleCulture::getNom).collect(Collectors.joining("\",\"", "[\"", "\"]"));
            String salleTemp = salles.stream().map(s -> String.valueOf(s.getTemperature())).collect(Collectors.joining(",", "[", "]"));
            String salleHumid = salles.stream().map(s -> String.valueOf(s.getHumidite())).collect(Collectors.joining(",", "[", "]"));

            String recolteDates = recoltes.stream().map(r -> r.getDate().toString()).collect(Collectors.joining("\",\"", "[\"", "\"]"));
            String recolteRendements = recoltes.stream().map(r -> String.valueOf(r.getRendement())).collect(Collectors.joining(",", "[", "]"));

            request.setAttribute("salleNoms", salleNoms);
            request.setAttribute("salleTemp", salleTemp);
            request.setAttribute("salleHumid", salleHumid);
            request.setAttribute("recolteDates", recolteDates);
            request.setAttribute("recolteRendements", recolteRendements);

            request.getRequestDispatcher("Acceuil.jsp?dashboard").forward(request, response);

        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("Acceuil.jsp").forward(request, response);
        }
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
