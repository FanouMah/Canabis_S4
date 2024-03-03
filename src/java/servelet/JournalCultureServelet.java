/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servelet;

import classes.JournalCulture;
import classes.Plante;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utils.Function;

/**
 *
 * @author PC
 */
public class JournalCultureServelet extends HttpServlet {

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
            out.println("<title>Servlet JournalCultureServelet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet JournalCultureServelet at " + request.getContextPath() + "</h1>");
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
                    removeJournalCulture(request, response);
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
                    createJournalCulture(request, response);
                    break;
                case  "update":
                    updateJournalCulture(request, response);
                    break;
            }
        }
    }
    
    protected void removeJournalCulture(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        
        JournalCulture journal = new JournalCulture();
        
        try {
            journal.delete(id);
            request.setAttribute("success", "Journal de culture "+id+" supprimer");
            request.getRequestDispatcher("preparedJournalCutlureServelet?action=listJournalCulture").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("preparedJournalCutlureServelet?action=listJournalCulture").forward(request, response);
        }
    }   

    protected void createJournalCulture(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String strDate = request.getParameter("date");
        Date date = Function.stringToDate(strDate);
        String idPlante = request.getParameter("plante");
        String etapeCroissance = request.getParameter("etapeCroissance");
        String notes = request.getParameter("notes");
        
        Plante plante = new Plante();
        try {
            plante.getById(idPlante);
        } catch (Exception ex) {
            Logger.getLogger(JournalCultureServelet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        JournalCulture journalCulture = new JournalCulture(null, date, plante, etapeCroissance, notes);
        
        try {
            journalCulture.create(journalCulture);
            request.setAttribute("success", "Journal de culture ajoutée");
            request.getRequestDispatcher("preparedPlanteServelet?action=addJournalCulture").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("preparedPlanteServelet?action=addJournalCulture").forward(request, response);
        }
    }
    
    protected void updateJournalCulture(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idJournalCulture = request.getParameter("jcid");
        String strDate = request.getParameter("jcdate");
        String idPlante = request.getParameter("jcplante");
        String etapeCroissance = request.getParameter("jcetapeCroissance");
        String notes = request.getParameter("jcnotes");
        
        Date date = Function.stringToDate(strDate);
        
        Plante plante = new Plante();
        try {
            plante.getById(idPlante);
        } catch (Exception ex) {
            Logger.getLogger(JournalCultureServelet.class.getName()).log(Level.SEVERE, null, ex);
        }
        JournalCulture journal = new JournalCulture(idJournalCulture, date, plante, etapeCroissance, notes);
        try {
            journal.update(journal);
            request.setAttribute("success", "journal de culture "+idJournalCulture+" modifiée");
            request.getRequestDispatcher("preparedJournalCutlureServelet?action=listJournalCulture").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("preparedJournalCutlureServelet?action=listJournalCulture").forward(request, response);
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
