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
public class SalleCulture {
    String id;
    String nom;
    int temperature;
    int humidite;

    public SalleCulture() {
    }

    public SalleCulture(String id, String nom, int temperature, int humidite) {
        this.id = id;
        this.nom = nom;
        this.temperature = temperature;
        this.humidite = humidite;
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

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getHumidite() {
        return humidite;
    }

    public void setHumidite(int humidite) {
        this.humidite = humidite;
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
            String query = "SELECT * FROM salleCulture where salleCulture_id = ?";
            prs = con.prepareStatement(query);
            prs.setString(1, id);
            res = prs.executeQuery();
            
            if(res.next()){
                setId(res.getString("salleCulture_id"));
                setNom(res.getString("nom_salle"));
                setTemperature(res.getInt("temperature"));
                setHumidite(res.getInt("humidite"));
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
    
    public void create(SalleCulture salle) throws Exception{
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
            String query = "INSERT INTO salleCulture (nom_salle,temperature,humidite) VALUES (?,?,?)";
            prs = con.prepareStatement(query);
            prs.setString(1, salle.getNom());
            prs.setInt(2, salle.getTemperature());
            prs.setInt(3, salle.getHumidite());
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
        
    public void update(SalleCulture salle) throws Exception{
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
            String query = "UPDATE salleCulture SET nom_salle = ?, temperature = ?, humidite = ? WHERE salleCulture_id = ?";
            prs = con.prepareStatement(query);
            prs.setString(1, salle.getNom());
            prs.setInt(2, salle.getTemperature());
            prs.setInt(3, salle.getHumidite());
            prs.setString(4, salle.getId());
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
            String query = "DELETE FROM salleCulture WHERE salleCulture_id = ?";
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
    
    public List<SalleCulture> getAll() throws Exception{
    Connection con = null;
        PreparedStatement prs = null;
        ResultSet res = null;
        List<SalleCulture> list = new ArrayList<>();
        
        try {
            if(con == null){
                Database pg = new Database("postgresql");
                pg.openConnection("postgres", "admin", "cannabis");
                con = pg.getConnection();
                con.setAutoCommit(false);
            }
            //traitement
            String query = "SELECT * FROM salleCulture order by CAST(substring(salleCulture_id from 3) AS INTEGER)";
            prs = con.prepareStatement(query);
            res = prs.executeQuery();
            while (res.next()) {
                list.add(new SalleCulture(res.getString("salleCulture_id"), res.getString("nom_salle"), res.getInt("temperature"), res.getInt("humidite")));
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
    
    public List<SalleCulture> search(String nom,int tempMin,int tempMax,int humMin,int humMax) throws Exception{
    Connection con = null;
        PreparedStatement prs = null;
        ResultSet res = null;
        List<SalleCulture> list = new ArrayList<>();
        
        try {
            if(con == null){
                Database pg = new Database("postgresql");
                pg.openConnection("postgres", "admin", "cannabis");
                con = pg.getConnection();
                con.setAutoCommit(false);
            }
            //traitement
            String query = "SELECT * FROM salleCulture where ";
            
            if (!nom.isEmpty()) {
                query += "and LOWER(nom_salle) like '%?%' ";
            }
            
            query += "order by CAST(substring(salleCulture_id from 3) AS INTEGER)";
            prs = con.prepareStatement(query);
            res = prs.executeQuery();
            while (res.next()) {
                list.add(new SalleCulture(res.getString("salleCulture_id"), res.getString("nom_salle"), res.getInt("temperature"), res.getInt("humidite")));
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
}
