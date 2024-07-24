/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import classes.*;
import java.util.List;
import utils.Function;

/**
 *
 * @author ASUS
 */
public class Main {
    
    public static void main(String[] args) throws Exception{
        SalleCulture salle = new SalleCulture();
        Plante plante = new Plante();
        JournalCulture j = new JournalCulture();
        Recolte r = new Recolte();
        try {
//            List<SalleCulture> list = salle.search(null, 21, null, null, null);
//            List<Plante> plantes = plante.search(null, null, null);
//            List<JournalCulture> journaux = j.search(Function.stringToDate("2024-07-07"), Function.stringToDate("2024-07-08"), "PL7", null, null);
            List<Recolte> list = r.search(Function.stringToDate("2024-07-07"), Function.stringToDate("2024-07-07"), "PL7", 500, 550, "tests");
        } catch (Exception e) {
            throw e;
        }
    }
}
