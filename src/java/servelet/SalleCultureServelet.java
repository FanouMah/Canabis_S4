/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servelet;

import classes.SalleCulture;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author PC
 */
public class SalleCultureServelet extends HttpServlet {

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
            out.println("<title>Servlet SalleCultureServelet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SalleCultureServelet at " + request.getContextPath() + "</h1>");
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
        String action = request.getParameter("action");
        if (action != null && !action.isEmpty()) {
            switch (action) {
                case "remove":
                    removeSalleCulture(request, response);
                    break;
            }
        } else {
            processRequest(request, response);
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
        String action = request.getParameter("action");
        if (action != null && !action.isEmpty()) {
            switch (action) {
                case "create":
                    createSalleCulture(request, response);
                    break;
                case "update":
                    updateSalleCulture(request, response);
                    break;
                case "search":
                    searchSalleCulture(request, response);
                    break;
            }
        } else {
            processRequest(request, response);
        }
    }
    
    protected void removeSalleCulture(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        
        SalleCulture salle = new SalleCulture();
        
        try {
            salle.delete(id);
            request.setAttribute("success", "Salle de culture " + id + " supprimée");
            request.getRequestDispatcher("preparedSalleCutlureServelet?action=listSalleCulture").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("preparedSalleCutlureServelet?action=listSalleCulture").forward(request, response);
        }
    }   
    
    protected void createSalleCulture(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nom = request.getParameter("nom");
        int temperature = Integer.parseInt(request.getParameter("temperature"));
        int humidite = Integer.parseInt(request.getParameter("humidite"));
        
        SalleCulture salle = new SalleCulture(null, nom, temperature, humidite);
        
        try {
            salle.create(salle);
            request.setAttribute("success", "Salle de culture ajoutée");
            request.getRequestDispatcher("Acceuil.jsp?addSalleCulture").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("Acceuil.jsp?addSalleCulture").forward(request, response);
        }
    }
    
    protected void updateSalleCulture(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("slid");
        String nom = request.getParameter("slnom");
        int temperature = Integer.parseInt(request.getParameter("sltemperature"));
        int humidite = Integer.parseInt(request.getParameter("slhumidite"));
        
        SalleCulture salle = new SalleCulture(id, nom, temperature, humidite);
        
        try {
            salle.update(salle);
            request.setAttribute("success", "Salle de culture " + id + " modifiée");
            request.getRequestDispatcher("preparedSalleCutlureServelet?action=listSalleCulture").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("preparedSalleCutlureServelet?action=listSalleCulture").forward(request, response);
        }
    }
    
    protected void searchSalleCulture(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
           String nom = request.getParameter("nom");
            Integer tempMin = request.getParameter("tempMin").isEmpty() ? null : Integer.parseInt(request.getParameter("tempMin"));
            Integer tempMax = request.getParameter("tempMax").isEmpty() ? null : Integer.parseInt(request.getParameter("tempMax"));
            Integer humMin = request.getParameter("humMin").isEmpty() ? null : Integer.parseInt(request.getParameter("humMin"));
            Integer humMax = request.getParameter("humMax").isEmpty() ? null : Integer.parseInt(request.getParameter("humMax"));

            SalleCulture salle = new SalleCulture();

            try {
                List<SalleCulture> results = salle.search(nom, tempMin, tempMax, humMin, humMax);

                request.setAttribute("listSalleCulture", results);
                RequestDispatcher dispat =  request.getRequestDispatcher("Acceuil.jsp?listSalleCulture");
                dispat.forward(request, response);
            } catch (Exception e) {
                request.setAttribute("error", e.getMessage());
                    request.getRequestDispatcher("Acceuil.jsp").forward(request, response);
            }
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
