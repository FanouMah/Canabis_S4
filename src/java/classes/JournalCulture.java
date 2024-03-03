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
public class JournalCulture {
    String id;
    Date date;
    Plante plante = new Plante();
    String etapeCroissance;
    String notes;

    public JournalCulture(String id, Date date, Plante plante, String etapeCroissance, String notes) {
        this.id = id;
        this.date = date;
        this.plante = plante;
        this.etapeCroissance = etapeCroissance;
        this.notes = notes;
    }

    public JournalCulture() {
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

    public String getEtapeCroissance() {
        return etapeCroissance;
    }

    public void setEtapeCroissance(String etapeCroissance) {
        this.etapeCroissance = etapeCroissance;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
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
            String query = "SELECT * FROM journalCulture where journalCulture_id = ?";
            prs = con.prepareStatement(query);
            prs.setString(1, id);
            res = prs.executeQuery();
            
            if(res.next()){
                setId(res.getString("journalCulture_id"));
                setDate(res.getDate("date"));
                this.plante.getById(res.getString("plante_id"));
                setEtapeCroissance(res.getString("etapecroissance"));
                setNotes(res.getString("notes"));
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
    
    public List<JournalCulture> getAll() throws Exception{
    Connection con = null;
        PreparedStatement prs = null;
        ResultSet res = null;
        List<JournalCulture> list = new ArrayList<>();
        
        try {
            if(con == null){
                Database pg = new Database("postgresql");
                pg.openConnection("postgres", "admin", "cannabis");
                con = pg.getConnection();
                con.setAutoCommit(false);
            }
            //traitement
            String query = "SELECT * FROM journalCulture order by CAST(substring(journalCulture_id from 3) AS INTEGER)";
            prs = con.prepareStatement(query);
            res = prs.executeQuery();
            while (res.next()) {
                //add
                Plante p = new Plante();
                p.getById(res.getString("plante_id"));
                list.add(new JournalCulture(res.getString("journalCulture_id"),res.getDate("date"),p,res.getString("etapecroissance"),res.getString("notes")));
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
    
    public void create(JournalCulture journalCulture) throws Exception{
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
            String query = "INSERT INTO journalCulture (plante_id,date,etapecroissance,notes) VALUES (?,?,?,?)";
            prs = con.prepareStatement(query);
            prs.setString(1,journalCulture.getPlante().getId());
            prs.setDate(2,journalCulture.getDate());
            prs.setString(3,journalCulture.getEtapeCroissance());
            prs.setString(4,journalCulture.getNotes());
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
    
    public void update(JournalCulture journalCulture) throws Exception{
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
            String query = "UPDATE journalCulture SET plante_id = ?, date = ?, etapecroissance = ?, notes = ? WHERE journalculture_id = ?";
            prs = con.prepareStatement(query);
            prs.setString(1, journalCulture.getPlante().getId());
            prs.setDate(2, journalCulture.getDate());
            prs.setString(3, journalCulture.getEtapeCroissance());
            prs.setString(4, journalCulture.getNotes());
            prs.setString(5, journalCulture.getId());
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
            String query = "DELETE FROM journalCulture WHERE journalculture_id = ?";
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
