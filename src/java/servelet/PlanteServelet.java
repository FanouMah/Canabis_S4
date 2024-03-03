/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servelet;

import classes.Plante;
import classes.SalleCulture;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author PC
 */
public class PlanteServelet extends HttpServlet {

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
            out.println("<title>Servlet PlanteServelet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet PlanteServelet at " + request.getContextPath() + "</h1>");
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
                    removePlante(request, response);
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
                    createPlante(request, response);
                    break;
                case  "update":
                    updatePlante(request, response);
                    break;
            }
        }
    }

    protected void removePlante(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        
        Plante plante = new Plante();
        
        try {
            plante.delete(id);
            request.setAttribute("success", "plante "+id+" supprimer");
            request.getRequestDispatcher("preparedPlanteServelet?action=listPlante").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("preparedPlanteServelet?action=listPlante").forward(request, response);
        }
    }   
    
    protected void createPlante(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String espece = request.getParameter("espece");
        String variete = request.getParameter("variete");
        String idSalleCulture = request.getParameter("salleCulture");
        
        SalleCulture salle = new SalleCulture();
        try {
            salle.getById(idSalleCulture);
        } catch (Exception ex) {
            Logger.getLogger(PlanteServelet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Plante plante = new Plante(null,espece, variete, salle);
        
        try {
            plante.create(plante);
            request.setAttribute("success", "Plante ajoutée");
            request.getRequestDispatcher("preparedSalleCutlureServelet?action=addPlante").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("preparedSalleCutlureServelet?action=addPlante").forward(request, response);
        }
    }
    
    protected void updatePlante(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idPlante = request.getParameter("pid");
        String espece = request.getParameter("pespece");
        String variete = request.getParameter("pvariete");
        String idSalleCulture = request.getParameter("psc");
        
        SalleCulture salle = new SalleCulture();
        try {
            salle.getById(idSalleCulture);
        } catch (Exception ex) {
            Logger.getLogger(PlanteServelet.class.getName()).log(Level.SEVERE, null, ex);
        }
        Plante plante = new Plante(idPlante,espece, variete, salle);
        try {
            plante.update(plante);
            request.setAttribute("success", "Plante "+idPlante+" modifiée");
            request.getRequestDispatcher("preparedPlanteServelet?action=listPlante").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("preparedPlanteServelet?action=listPlante").forward(request, response);
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
