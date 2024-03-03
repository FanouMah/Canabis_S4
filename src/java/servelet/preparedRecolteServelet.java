/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servelet;

import classes.Plante;
import classes.Recolte;
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
public class preparedRecolteServelet extends HttpServlet {

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
            out.println("<title>Servlet preparedRecolteServelet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet preparedRecolteServelet at " + request.getContextPath() + "</h1>");
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
            Plante plante = new Plante();
            Recolte recolte = new Recolte();

            try {
                List<Plante> listPlante = plante.getAll();
                List<Recolte> listRecolte  = recolte.getAll();
                
                request.setAttribute("listPlante", listPlante);
                request.setAttribute("listRecolte", listRecolte);
                
                RequestDispatcher dispat =  request.getRequestDispatcher("Acceuil.jsp?"+action);
                dispat.forward(request, response);

            } catch (Exception e) {
                request.setAttribute("error", e.getMessage());
                request.getRequestDispatcher("Acceuil.jsp").forward(request, response);
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
            Plante plante = new Plante();
            Recolte recolte = new Recolte();

            try {
                List<Plante> listPlante = plante.getAll();
                List<Recolte> listRecolte  = recolte.getAll();
                
                request.setAttribute("listPlante", listPlante);
                request.setAttribute("listRecolte", listRecolte);
                
                RequestDispatcher dispat =  request.getRequestDispatcher("Acceuil.jsp?"+action);
                dispat.forward(request, response);

            } catch (Exception e) {
                request.setAttribute("error", e.getMessage());
                request.getRequestDispatcher("Acceuil.jsp").forward(request, response);
            }
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
