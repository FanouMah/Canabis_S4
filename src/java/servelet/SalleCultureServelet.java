/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servelet;

import classes.SalleCulture;
import java.io.IOException;
import java.io.PrintWriter;
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
        if (!action.isEmpty() || action == null || action == "") {
            
            switch (action){
                case  "remove":
                    removeSalleCulture(request, response);
                    break;
            }
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
        if (!action.isEmpty() || action == null || action == "") {
            
            switch (action){
                case  "create":
                    createSalleCulture(request, response);
                    break;
                case  "update":

                    break;
            }
        }
    }
    
    protected void removeSalleCulture(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        
        SalleCulture salle = new SalleCulture();
        
        try {
            salle.delete(id);
            request.setAttribute("success", "Salle de culture supprimer");
            request.getRequestDispatcher("Acceuil.jsp?listSalleCulture").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("Acceuil.jsp?listSalleCulture").forward(request, response);
        }
    }   
    
    protected void createSalleCulture(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nom = request.getParameter("nom");
        int temperature = Integer.valueOf(request.getParameter("temperature"));
        int humidite = Integer.valueOf(request.getParameter("humidite"));
        
        SalleCulture salle = new SalleCulture(null, nom, temperature, humidite);
        
        try {
            salle.save(salle);
            request.setAttribute("success", "Salle de culture ajoutée");
            request.getRequestDispatcher("Acceuil.jsp?addSalleCulture").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("Acceuil.jsp?addSalleCulture").forward(request, response);
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
