/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;
import connect.Database;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import org.mindrot.jbcrypt.BCrypt;
/**
 *
 * @author PC
 */
public class Utilisateur {
    String id;
    String nom;
    String prenom;
    String pseudo;
    String email;
    String password;
    
    public Utilisateur() {
    }

    public Utilisateur(String id, String nom, String prenom, String pseudo, String email, String password) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.pseudo = pseudo;
        this.email = email;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getIdByPeseudo(String pseudo) throws Exception {
        Connection con = null;
        PreparedStatement prs = null;
        ResultSet res = null;
        String id = "";
        
        try {
            if(con == null){
                Database pg = new Database("postgresql");
                pg.openConnection("postgres", "admin", "cannabis");
                con = pg.getConnection();
                con.setAutoCommit(false);
            }
            //traitement
            String query = "select utilisateur_id from utilisateur where pseudo = ?";
            prs = con.prepareStatement(query);
            prs.setString(1, pseudo);
            res = prs.executeQuery();
            if (res.next()) {
                id = res.getString("utilisateur_id");
            }
            else{
                throw new Exception("Id non repertorier");
            }
            
            con.commit();
        } catch (Exception e) {
            if (con != null) {
                con.rollback();   
            }
            throw e;
        }
        finally{
            if (con != null) {
                con.close();
            }
            if (prs != null) {
                prs.close();
            }
            if (res != null){
                res.close();
            }
        }
        return id;
    }
    
    public void getById(String id) throws Exception{
            Connection con = null;
            PreparedStatement prs = null;
            ResultSet res = null;
        
        try {
            if(con == null){
                Database pg = new Database("postgresql");
                pg.openConnection("postgres", "admin", "cannabis");
                con = pg.getConnection();
                con.setAutoCommit(false);
            }
            //traitement
            String query = "SELECT * FROM utilisateur where utilisateur_id = ?";
            prs = con.prepareStatement(query);
            prs.setString(1, id);
            res = prs.executeQuery();
            
            if(res.next()){
                setId(res.getString("utilisateur_id"));
                setNom(res.getString("nom"));
                setPrenom(res.getString("prenom"));
                setPseudo(res.getString("pseudo"));
                setEmail(res.getString("email"));
                setPassword(res.getString("password"));
            }
            
            con.commit();
        } catch (Exception e) {
            if (con != null) {
                con.rollback();   
            }
            throw e;
        }
        finally{
            if (con != null) {
                con.close();
            }
            if (prs != null) {
                prs.close();
            }
            if (res != null){
                res.close();
            }
        }
    }
     
    public void create(Utilisateur user) throws Exception{
            Connection con = null;
            PreparedStatement prs = null;
            ResultSet res = null;
        
        try {
            if(con == null){
                Database pg = new Database("postgresql");
                pg.openConnection("postgres", "admin", "cannabis");
                con = pg.getConnection();
                con.setAutoCommit(false);
            }
            //traitement
            String query = "INSERT INTO utilisateur (nom,prenom,pseudo,email,password) VALUES (?,?,?,?,?)";
            prs = con.prepareStatement(query);
            prs.setString(1, user.getNom());
            prs.setString(2, user.getPrenom());
            prs.setString(3, user.getPseudo());
            prs.setString(4, user.getEmail());
            prs.setString(5, BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
            prs.executeUpdate();
            
            con.commit();
        } catch (SQLException e) {
            if(e.getSQLState().equals("23505")){
                e = new SQLException("Pseudo deja prise");
            }
            if (con != null) {
                con.rollback();   
            }
            throw e;
        }
        finally{
            if (con != null) {
                con.close();
            }
            if (prs != null) {
                prs.close();
            }
            if (res != null){
                res.close();
            }
        }
    }

    public void update(Utilisateur user) throws Exception{
        Connection con = null;
        PreparedStatement prs = null;
        ResultSet res = null;
        
        try {
            if(con == null){
                Database pg = new Database("postgresql");
                pg.openConnection("postgres", "admin", "cannabis");
                con = pg.getConnection();
                con.setAutoCommit(false);
            }
            //traitement
            String query = "UPDATE utilisateur SET nom = ?, prenom = ?, pseudo = ?, email = ? WHERE utilisateur_id = ?";
            prs = con.prepareStatement(query);
            prs.setString(1, user.getNom());
            prs.setString(2, user.getPrenom());
            prs.setString(3, user.getPseudo());
            prs.setString(4, user.getEmail());
            prs.setString(5, user.getId());
            prs.executeUpdate();
            
            con.commit();
        } catch (Exception e) {
            if (con != null) {
                con.rollback();   
            }
            throw e;
        }
        finally{
            if (con != null) {
                con.close();
            }
            if (prs != null) {
                prs.close();
            }
            if (res != null){
                res.close();
            }
        }
    }
    public void changePassword(String newPassword) throws Exception{
        Connection con = null;
        PreparedStatement prs = null;
        ResultSet res = null;
        
        try {
            if(con == null){
                Database pg = new Database("postgresql");
                pg.openConnection("postgres", "admin", "cannabis");
                con = pg.getConnection();
                con.setAutoCommit(false);
            }
            //traitement
            String query = "UPDATE utilisateur SET password = ? WHERE utilisateur_id = ?";
            prs = con.prepareStatement(query);
            prs.setString(1, BCrypt.hashpw(newPassword, BCrypt.gensalt()));
            prs.setString(2, this.id);
            prs.executeUpdate();
            
            con.commit();
        } catch (Exception e) {
            if (con != null) {
                con.rollback();   
            }
            throw e;
        }
        finally{
            if (con != null) {
                con.close();
            }
            if (prs != null) {
                prs.close();
            }
            if (res != null){
                res.close();
            }
        }
    }

    public void delete(String id) throws Exception{
        Connection con = null;
        PreparedStatement prs = null;
        ResultSet res = null;
        
        try {
            if(con == null){
                Database pg = new Database("postgresql");
                pg.openConnection("postgres", "admin", "cannabis");
                con = pg.getConnection();
                con.setAutoCommit(false);
            }
            //traitement
            String query = "DELETE FROM utilisateur WHERE utilisateur_id = ?";
            prs = con.prepareStatement(query);
            prs.setString(1, id);
            prs.executeUpdate();
            
            con.commit();
        } catch (Exception e) {
            if (con != null) {
                con.rollback();   
            }
            throw e;
        }
        finally{
            if (con != null) {
                con.close();
            }
            if (prs != null) {
                prs.close();
            }
            if (res != null){
                res.close();
            }
        }
    }
    
    public List<Utilisateur> getAll() throws Exception{
        Connection con = null;
        PreparedStatement prs = null;
        ResultSet res = null;
        List<Utilisateur> list = new ArrayList<>();
        
        try {
            if(con == null){
                Database pg = new Database("postgresql");
                pg.openConnection("postgres", "admin", "cannabis");
                con = pg.getConnection();
                con.setAutoCommit(false);
            }
            //traitement
            String query = "SELECT * FROM utilisateur";
            prs = con.prepareStatement(query);
            res = prs.executeQuery();
            while (res.next()) {
                list.add(new Utilisateur(res.getString("utilisateur_id"),res.getString("nom"),res.getString("prenom"),res.getString("pseudo"),res.getString("email"),res.getString("password")));
            }
            
            con.commit();
        } catch (Exception e) {
            if (con != null) {
                con.rollback();   
            }
            throw e;
        }
        finally{
            if (con != null) {
                con.close();
            }
            if (prs != null) {
                prs.close();
            }
            if (res != null){
                res.close();
            }
        }
        return list;
    }
    
    public boolean autentification(String pseudo,String password) throws Exception {
        boolean response = false;
        Connection con = null;
        PreparedStatement prs = null;
        ResultSet res = null;
        
        try {
            if(con == null){
                Database pg = new Database("postgresql");
                pg.openConnection("postgres", "admin", "cannabis");
                con = pg.getConnection();
                con.setAutoCommit(false);
            }
            //traitement
            String query = "select pseudo,password from utilisateur where pseudo = ?";
            prs = con.prepareStatement(query);
            prs.setString(1, pseudo);
            res = prs.executeQuery();
            
            if(res.next()){
                if(BCrypt.checkpw(password, res.getString("password"))){
                    response = true;
                }else {
                    throw new Exception("auth_2");
                }
            } else {
                throw new Exception("auth_1");
            }
            
            con.commit();
        } catch (Exception e) {
            if (con != null) {
                con.rollback();   
            }
            throw e;
        }
        finally{
            if (con != null) {
                con.close();
            }
            if (prs != null) {
                prs.close();
            }
            if (res != null){
                res.close();
            }
        }
        return response;
    }
}
