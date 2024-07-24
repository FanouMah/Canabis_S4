/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import connect.Database;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author PC
 */
public class Recolte {
    String id;
    Date date;
    Plante plante = new Plante();
    int rendement;
    String qualite;

    public Recolte(String id, Date date, Plante plante, int rendement, String qualite) {
        this.id = id;
        this.date = date;
        this.plante = plante;
        this.rendement = rendement;
        this.qualite = qualite;
    }

    public Recolte() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Plante getPlante() {
        return plante;
    }

    public void setPlante(Plante plante) {
        this.plante = plante;
    }

    public int getRendement() {
        return rendement;
    }

    public void setRendement(int rendement) {
        this.rendement = rendement;
    }

    public String getQualite() {
        return qualite;
    }

    public void setQualite(String qualite) {
        this.qualite = qualite;
    }
    
    public int getNombrePlantesRecoltees() throws Exception {
        Connection con = null;
        PreparedStatement prs = null;
        ResultSet res = null;
        int count = 0;

        try {
            if(con == null){
                Database pg = new Database("postgresql");
                pg.openConnection("postgres", "admin", "cannabis");
                con = pg.getConnection();
                con.setAutoCommit(false);
            }

            String query = "SELECT COUNT(DISTINCT p.plante_id) AS nombre_plantes_recoltees " +
                           "FROM plante p " +
                           "JOIN recolte r ON p.plante_id = r.plante_id";
            prs = con.prepareStatement(query);
            res = prs.executeQuery();

            if (res.next()) {
                count = res.getInt("nombre_plantes_recoltees");
            }

            con.commit();
        } catch (SQLException e) {
            if (con != null) {
                con.rollback();
            }
            throw e;
        } finally {
            if (con != null) {
                con.close();
            }
            if (prs != null) {
                prs.close();
            }
            if (res != null) {
                res.close();
            }
        }

        return count;
    }

    public int getNombrePlantesNonRecoltees() throws Exception {
        Connection con = null;
        PreparedStatement prs = null;
        ResultSet res = null;
        int count = 0;

        try {
           if(con == null){
                Database pg = new Database("postgresql");
                pg.openConnection("postgres", "admin", "cannabis");
                con = pg.getConnection();
                con.setAutoCommit(false);
            }

            String query = "SELECT COUNT(p.plante_id) AS nombre_plantes_non_recoltees " +
                           "FROM plante p " +
                           "LEFT JOIN recolte r ON p.plante_id = r.plante_id " +
                           "WHERE r.plante_id IS NULL";
            prs = con.prepareStatement(query);
            res = prs.executeQuery();

            if (res.next()) {
                count = res.getInt("nombre_plantes_non_recoltees");
            }

            con.commit();
        } catch (SQLException e) {
            if (con != null) {
                con.rollback();
            }
            throw e;
        } finally {
            if (con != null) {
                con.close();
            }
            if (prs != null) {
                prs.close();
            }
            if (res != null) {
                res.close();
            }
        }

        return count;
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
            String query = "SELECT * FROM recolte where recolte_id = ?";
            prs = con.prepareStatement(query);
            prs.setString(1, id);
            res = prs.executeQuery();
            
            if(res.next()){
                setId(res.getString("journalCulture_id"));
                setDate(res.getDate("date"));
                this.plante.getById(res.getString("plante_id"));
                setRendement(res.getInt("rendement"));
                setQualite(res.getString("qualite"));
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
    
    public List<Recolte> search(Date dateDebut, Date dateFin, String planteId, Integer rendMin, Integer rendMax, String qualite) throws Exception {
        Connection con = null;
        PreparedStatement prs = null;
        ResultSet res = null;
        List<Recolte> list = new ArrayList<>();

        try {
            if (con == null) {
                Database pg = new Database("postgresql");
                pg.openConnection("postgres", "admin", "cannabis");
                con = pg.getConnection();
                con.setAutoCommit(false);
            }

            // Construire la requête SQL
            String query = "SELECT * FROM recolte WHERE 1=1";

            if (dateDebut != null) {
                query += " AND date >= ?";
            }

            if (dateFin != null) {
                query += " AND date <= ?";
            }
            
             if (planteId != null && !planteId.isEmpty()) {
                query += " AND plante_id = ?";
            }
             
 
            if (rendMin != null) {
                query += " AND rendement >= ?";
            }

            if (rendMax != null) {
                query += " AND rendement <= ?";
            }
            
            if (qualite != null && !qualite.isEmpty()) {
                query += " AND LOWER(qualite) LIKE ?";
            }

            query += " ORDER BY CAST(substring(recolte_id from 3) AS INTEGER)";

            prs = con.prepareStatement(query);

            int paramIndex = 1;
            
            if (dateDebut != null) {
                prs.setDate(paramIndex++, dateDebut);
            }

            if (dateFin != null) {
                prs.setDate(paramIndex++, dateFin);
            }
  
            if (planteId != null && !planteId.isEmpty()) {
                prs.setString(paramIndex++, planteId);
            }
                      
            if (rendMin != null) {
                prs.setInt(paramIndex++, rendMin);
            }

            if (rendMax != null) {
                prs.setInt(paramIndex++, rendMax);
            }
            
            if (qualite != null && !qualite.isEmpty()) {
                prs.setString(paramIndex++, "%" + qualite.toLowerCase() + "%");
            }
            
            System.out.println(prs);
            res = prs.executeQuery();
            while (res.next()) {
                Plante p = new Plante();
                p.getById(res.getString("plante_id"));
                list.add(new Recolte(res.getString("recolte_id"),res.getDate("date"),p,res.getInt("rendement"),res.getString("qualite")));
            }

            con.commit();
        } catch (Exception e) {
            if (con != null) {
                con.rollback();   
            }
            throw e;
        } finally {
            if (con != null) {
                con.close();
            }
            if (prs != null) {
                prs.close();
            }
            if (res != null) {
                res.close();
            }
        }
        return list;
    }
    
    public List<Recolte> getAll() throws Exception{
    Connection con = null;
        PreparedStatement prs = null;
        ResultSet res = null;
        List<Recolte> list = new ArrayList<>();
        
        try {
            if(con == null){
                Database pg = new Database("postgresql");
                pg.openConnection("postgres", "admin", "cannabis");
                con = pg.getConnection();
                con.setAutoCommit(false);
            }
            //traitement
            String query = "SELECT * FROM recolte order by CAST(substring(recolte_id from 3) AS INTEGER)";
            prs = con.prepareStatement(query);
            res = prs.executeQuery();
            while (res.next()) {
                //add
                Plante p = new Plante();
                p.getById(res.getString("plante_id"));
                list.add(new Recolte(res.getString("recolte_id"),res.getDate("date"),p,res.getInt("rendement"),res.getString("qualite")));
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
    
    public void create(Recolte recolte) throws Exception{
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
            String query = "INSERT INTO recolte (plante_id,date,rendement,qualite) VALUES (?,?,?,?)";
            prs = con.prepareStatement(query);
            prs.setString(1,recolte.getPlante().getId());
            prs.setDate(2,recolte.getDate());
            prs.setInt(3,recolte.getRendement());
            prs.setString(4,recolte.getQualite());
            prs.executeUpdate();
            
            con.commit();
        } catch (SQLException e) {
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
    
    public void update(Recolte recolte) throws Exception{
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
            String query = "UPDATE recolte SET plante_id = ?, date = ?, rendement = ?, qualite = ? WHERE recolte_id = ?";
            prs = con.prepareStatement(query);
            prs.setString(1, recolte.getPlante().getId());
            prs.setDate(2, recolte.getDate());
            prs.setInt(3, recolte.getRendement());
            prs.setString(4, recolte.getQualite());
            prs.setString(5, recolte.getId());
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
            String query = "DELETE FROM recolte WHERE recolte_id = ?";
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
}
