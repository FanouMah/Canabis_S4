/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servelet;

import classes.JournalCulture;
import classes.Plante;
import classes.Recolte;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utils.Function;

/**
 *
 * @author PC
 */
public class RecolteServelet extends HttpServlet {

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
            out.println("<title>Servlet RecolteServelet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet RecolteServelet at " + request.getContextPath() + "</h1>");
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
                    removeRecolte(request, response);
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
                    createRecolte(request, response);
                    break;
                case  "update":
                    updateRecolte(request, response);
                    break;
                case "search":
                    searchRecolte(request, response);
                    break;
            }
        }
    }
    
    protected void searchRecolte(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            Date dateDebut = request.getParameter("dateDebut").isEmpty() ? null : Function.stringToDate(request.getParameter("dateDebut"));
            Date dateFin = request.getParameter("dateFin").isEmpty() ? null : Function.stringToDate(request.getParameter("dateFin"));
            String idPlante = request.getParameter("plante");
            Integer rendMin = request.getParameter("rendMin").isEmpty() ? null : Integer.parseInt(request.getParameter("rendMin"));
            Integer rendMax = request.getParameter("rendMax").isEmpty() ? null : Integer.parseInt(request.getParameter("rendMax"));
            String qualite = request.getParameter("qualite");
           
            Recolte recolte = new Recolte();
            Plante plante = new Plante();

            try {
                
                List<Plante> listPlante  = plante.getAll();
                List<Recolte> results = recolte.search(dateDebut, dateFin, idPlante, rendMin, rendMax, qualite);
                
                request.setAttribute("listPlante", listPlante);
                request.setAttribute("listRecolte", results);
                RequestDispatcher dispat =  request.getRequestDispatcher("Acceuil.jsp?listRecolte");
                dispat.forward(request, response);
            } catch (Exception e) {
                request.setAttribute("error", e.getMessage());
                    request.getRequestDispatcher("Acceuil.jsp").forward(request, response);
            }
        }
    
    protected void removeRecolte(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        
        Recolte recolte = new Recolte();
        
        try {
            recolte.delete(id);
            request.setAttribute("success", "Recolte "+id+" supprimer");
            request.getRequestDispatcher("preparedRecolteServelet?action=listRecolte").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("preparedRecolteServelet?action=listRecolte").forward(request, response);
        }
    }  
    
    protected void createRecolte(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String strDate = request.getParameter("date");
        Date date = Function.stringToDate(strDate);
        String idPlante = request.getParameter("plante");
        int rendement = Integer.valueOf(request.getParameter("rendement"));
        String qualite = request.getParameter("qualite");
        
        Plante plante = new Plante();
        
        try {
            plante.getById(idPlante);
        } catch (Exception ex) {
            Logger.getLogger(RecolteServelet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Recolte recolte = new Recolte(null, date, plante, rendement, qualite);
        
        try {
            recolte.create(recolte);
            request.setAttribute("success", "Récolte ajoutée");
            request.getRequestDispatcher("preparedPlanteServelet?action=addRecolte").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("preparedPlanteServelet?action=addRecolte").forward(request, response);
        }
    }

    protected void updateRecolte(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idRecolte = request.getParameter("rid");
        String strDate = request.getParameter("rdate");
        String idPlante = request.getParameter("rplante");
        int rendement = Integer.valueOf(request.getParameter("rrendement"));
        String qualite = request.getParameter("rqualite");
        
        Date date = Function.stringToDate(strDate);
        
        Plante plante = new Plante();
        try {
            plante.getById(idPlante);
        } catch (Exception ex) {
            Logger.getLogger(RecolteServelet.class.getName()).log(Level.SEVERE, null, ex);
        }
        Recolte recolte = new Recolte(idRecolte, date, plante, rendement, qualite);
        try {
            recolte.update(recolte);
            request.setAttribute("success", "Récolte "+idRecolte+" modifiée");
            request.getRequestDispatcher("preparedRecolteServelet?action=listRecolte").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("preparedRecolteServelet?action=listRecolte").forward(request, response);
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
