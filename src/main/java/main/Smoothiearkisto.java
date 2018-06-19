/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import dao.AnnosDao;
import dao.OhjeDao;
import dao.RaakaAineDao;
import dao.TilastoDao;
import database.Database;
import domain.Annos;
import domain.Ohje;
import domain.RaakaAine;
import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import spark.ModelAndView;
import spark.Spark;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

/**
 *
 * @author omistaja
 */
public class Smoothiearkisto {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Throwable {
        // TODO code application logic here
        File tiedosto = new File("db", "arkisto.db");
        Database database = new Database("jdbc:sqlite:" + tiedosto.getAbsolutePath());
        RaakaAineDao raakaAineet = new RaakaAineDao(database);
        AnnosDao annokset = new AnnosDao(database);
        OhjeDao ohjeet = new OhjeDao(database);
        TilastoDao tilastot = new TilastoDao(database);


        Spark.get("/index", (req, res) -> {
            HashMap map = new HashMap<>();

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());

        Spark.get("/reseptit", (req, res) -> {
            HashMap map = new HashMap<>();

            map.put("annokset", annokset.findAll());

            return new ModelAndView(map, "reseptit");
        }, new ThymeleafTemplateEngine());

        Spark.get("/reseptit/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            Integer reseptinId = Integer.parseInt(req.params(":id"));
            
            map.put("reseptinnimi", annokset.findByKey(reseptinId).getName());
            map.put("ohjeet", ohjeet.ohjeenTulostus(reseptinId));

            return new ModelAndView(map, "resepti");
        }, new ThymeleafTemplateEngine());

        Spark.post("/reseptit/:id/delete", (req, res) -> {
            
            ohjeet.delete(Integer.parseInt(req.params(":id")));
            annokset.delete(Integer.parseInt(req.params(":id")));
            
            res.redirect("/reseptit");

            return "";
        });

        Spark.get("/lisaaminen", (req, res) -> {
            HashMap map = new HashMap<>();

            map.put("raakaAineet", raakaAineet.findAll());
            map.put("annokset", annokset.findAll());

            return new ModelAndView(map, "lisaaminen");
        }, new ThymeleafTemplateEngine());

        Spark.post("/lisaaminen", (req, res) -> {
            RaakaAine raakaAine = new RaakaAine(-1, req.queryParams("nimi"));
            raakaAineet.saveOrUpdate(raakaAine);

            res.redirect("/lisaaminen");
            return "";
        });

        Spark.post("/lisaaminen/:id/delete", (req, res) -> {

            int aineId = Integer.parseInt(req.params(":id"));
            raakaAineet.delete(aineId);

            res.redirect("/lisaaminen");
            return req.params(":id");
        });

        Spark.post("/lisaaminen/reseptillenimi", (req, res) -> {
            Annos lisattava = new Annos(-1, req.queryParams("nimi"));
            annokset.saveOrUpdate(lisattava);
            res.redirect("/lisaaminen");
            return "";
        });

        Spark.post("/lisaaminen/reseptilleraakaaine", (req, res) -> {

            int lisattavanAnnoksenId = Integer.parseInt(req.queryParams("annos"));
            int lisattavanRaakaAineenId = Integer.parseInt(req.queryParams("raakaAine"));
            Ohje lisattavaOhje = new Ohje(Integer.parseInt(req.queryParams("monesko")), Double.parseDouble(req.queryParams("paljonko")), req.queryParams("kasittely"), lisattavanRaakaAineenId);
            ohjeet.saveOrUpdate(lisattavanAnnoksenId, lisattavanRaakaAineenId, lisattavaOhje);

            res.redirect("/lisaaminen");
            return "";
        });

        Spark.get("/tilastot", (req, res) -> {
            HashMap map = new HashMap<>();
            
            map.put("raakaAineidenLkm", tilastot.haeRaakaAineidenLukumaara());
            map.put("annostenLkm", tilastot.haeAnnostenLukumaara());
            map.put("aineidenKaytto", tilastot.haeRaakaAineidenKayttomaarat());
            
            System.out.println(tilastot.haeRaakaAineidenKayttomaarat().get(1));

            return new ModelAndView(map, "tilastot");
        }, new ThymeleafTemplateEngine());
    }

}
