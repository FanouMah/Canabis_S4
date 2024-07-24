/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import connect.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author PC
 */
public class Plante {
    String id;
    String espece;
    String variete;
    SalleCulture salleCulture = new SalleCulture();

    @Override
    public String toString() {
        return id+" - "+espece+" - "+variete+" ("+salleCulture.getNom()+")";
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEspece() {
        return espece;
    }

    public void setEspece(String espece) {
        this.espece = espece;
    }

    public String getVariete() {
        return variete;
    }

    public void setVariete(String variete) {
        this.variete = variete;
    }

    public SalleCulture getSalleCulture() {
        return salleCulture;
    }

    public void setSalleCulture(SalleCulture salleCulture) {
        this.salleCulture = salleCulture;
    }

    public Plante() {
    }

    public Plante(String id, String espece, String variete, SalleCulture salleCulture) {
        this.id = id;
        this.espece = espece;
        this.variete = variete;
        this.salleCulture = salleCulture;
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
            String query = "SELECT * FROM plante where plante_id = ?";
            prs = con.prepareStatement(query);
            prs.setString(1, id);
            res = prs.executeQuery();
            
            if(res.next()){
                setId(res.getString("plante_id"));
                setEspece(res.getString("espece"));
                setVariete(res.getString("variete"));
                this.salleCulture.getById(res.getString("salleCulture_id"));
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

    public void create(Plante plante) throws Exception{
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
            String query = "INSERT INTO plante (espece,variete,salleCulture_id) VALUES (?,?,?)";
            prs = con.prepareStatement(query);
            prs.setString(1, plante.getEspece());
            prs.setString(2, plante.getVariete());
            prs.setString(3, plante.getSalleCulture().getId());
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
    
    public void update(Plante plante) throws Exception{
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
            String query = "UPDATE plante SET espece = ?, variete = ?, salleCulture_id = ? WHERE plante_id = ?";
            prs = con.prepareStatement(query);
            prs.setString(1, plante.getEspece());
            prs.setString(2, plante.getVariete());
            prs.setString(3, plante.getSalleCulture().getId());
            prs.setString(4, plante.getId());
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
            String query = "DELETE FROM plante WHERE plante_id = ?";
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
    
    public List<Plante> getAll() throws Exception{
    Connection con = null;
        PreparedStatement prs = null;
        ResultSet res = null;
        List<Plante> list = new ArrayList<>();
        
        try {
            if(con == null){
                Database pg = new Database("postgresql");
                pg.openConnection("postgres", "admin", "cannabis");
                con = pg.getConnection();
                con.setAutoCommit(false);
            }
            //traitement
            String query = "SELECT * FROM plante order by CAST(substring(plante_id from 3) AS INTEGER)";
            prs = con.prepareStatement(query);
            res = prs.executeQuery();
            while (res.next()) {
                //add
                SalleCulture s = new SalleCulture();
                s.getById(res.getString("salleCulture_id"));
                list.add(new Plante(res.getString("plante_id"),res.getString("espece"),res.getString("variete"),s));
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
    
    public List<Plante> search(String espece, String variete, String salleCultureId) throws Exception {
    Connection con = null;
    PreparedStatement prs = null;
    ResultSet res = null;
    List<Plante> list = new ArrayList<>();
    
    try {
        if (con == null) {
            Database pg = new Database("postgresql");
            pg.openConnection("postgres", "admin", "cannabis");
            con = pg.getConnection();
            con.setAutoCommit(false);
        }
        
        // Construire la requête SQL dynamique
        StringBuilder query = new StringBuilder("SELECT * FROM plante WHERE 1=1");
        if (espece != null && !espece.isEmpty()) {
            query.append(" AND espece LIKE ?");
        }
        if (variete != null && !variete.isEmpty()) {
            query.append(" AND variete LIKE ?");
        }
        if (salleCultureId != null && !salleCultureId.isEmpty()) {
            query.append(" AND salleCulture_id = ?");
        }
        query.append(" ORDER BY CAST(substring(plante_id from 3) AS INTEGER)");
        
        prs = con.prepareStatement(query.toString());
        
        // Définir les paramètres
        int paramIndex = 1;
        if (espece != null && !espece.isEmpty()) {
            prs.setString(paramIndex++, "%" + espece + "%");
        }
        if (variete != null && !variete.isEmpty()) {
            prs.setString(paramIndex++, "%" + variete + "%");
        }
        if (salleCultureId != null && !salleCultureId.isEmpty()) {
            prs.setString(paramIndex++, salleCultureId);
        }
        
        res = prs.executeQuery();
        while (res.next()) {
            SalleCulture s = new SalleCulture();
            s.getById(res.getString("salleCulture_id"));
            list.add(new Plante(res.getString("plante_id"), res.getString("espece"), res.getString("variete"), s));
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

}
