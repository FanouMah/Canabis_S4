/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servelet;

import classes.Utilisateur;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author PC
 */
public class UtilisateurServelet extends HttpServlet {

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
            out.println("<title>Servlet UtilisateurServelet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UtilisateurServelet at " + request.getContextPath() + "</h1>");
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
                case  "logout":
                    HttpSession session = request.getSession();
                    session.invalidate();
                    response.sendRedirect("index.jsp");
                    break;
                case  "remove":
                    removeUser(request, response);
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
                    createUser(request, response);
                    break;
                case  "update":
                    updateUser(request, response);
                    break;
                case "updatePwd":
                    updatePwd(request, response);
                    break;
                case  "login":
                    String pseudo = request.getParameter("pseudo");
                    String password = request.getParameter("password");
                    
                    Utilisateur user = new Utilisateur();
                    try {
                        if (user.autentification(pseudo, password)) {
                            HttpSession session = request.getSession();
                            session.setAttribute("idUser", user.getIdByPeseudo(pseudo));
                            
                            response.sendRedirect("Acceuil.jsp");
                        }
                        
                    } catch (Exception e) {
                        request.setAttribute("error", e.getMessage());
                        request.getRequestDispatcher("login.jsp").forward(request, response);
                    }
                    
                    break;
            }
        
        }
    }
    
    protected void createUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String password = request.getParameter("password");
        String passwordConf = request.getParameter("passwordConf");
                
        if(password.equals(passwordConf)){
            String nom = request.getParameter("nom");
            String prenom = request.getParameter("prenom");
            String pseudo = request.getParameter("pseudo");
            String email = request.getParameter("email");
                        
            Utilisateur user = new Utilisateur(null, nom, prenom, pseudo, email, password);
            try {
                user.create(user);
                request.setAttribute("success", "Compte créer avec success");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            } catch (Exception e) {
                request.setAttribute("error", e.getMessage());
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        }
        else{
            request.setAttribute("error", "Les mot de passe saisis son differents");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
    protected void removeUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        Utilisateur user = new Utilisateur();
        try {
            user.delete(id);
            HttpSession session = request.getSession();
            session.invalidate();
            request.setAttribute("error", "Compte("+id+") supprimer");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("Acceuil.jsp?setting").forward(request, response);
        }   
    }
    
    protected void updatePwd(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String password = request.getParameter("password");
        String newPassword = request.getParameter("newPassword");
        String passwordConf = request.getParameter("passwordConf");
        String id = request.getParameter("id");

        Utilisateur user = new Utilisateur();
        try {
            user.getById(id);

            if (!BCrypt.checkpw(password, user.getPassword())) {
                request.setAttribute("errorPassword", "Mot de passe incorrect.");
                request.getRequestDispatcher("Acceuil.jsp?setting").forward(request, response);
                return;
            }

            if (!newPassword.equals(passwordConf)) {
                request.setAttribute("errorPasswordConf", "Les nouveaux mots de passe ne correspondent pas.");
                request.getRequestDispatcher("Acceuil.jsp?setting").forward(request, response);
                return;
            }

            user.changePassword(newPassword);
            request.setAttribute("success", "Mot de passe changé avec succès.");
            request.getRequestDispatcher("Acceuil.jsp?setting").forward(request, response);

        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("Acceuil.jsp?setting").forward(request, response);
        }
    }
    
    protected void updateUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String pseudo = request.getParameter("pseudo");
        String email = request.getParameter("email");
        String id = request.getParameter("id");
        
        Utilisateur user = new Utilisateur(id, nom, prenom, pseudo, email, null);
        try {
            user.update(user);
            request.setAttribute("success", "Modifaction éfectuée");
            request.getRequestDispatcher("Acceuil.jsp?setting").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("Acceuil.jsp?setting").forward(request, response);
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
