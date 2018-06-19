/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import database.Database;
import domain.Annos;
import domain.Ohje;
import domain.RaakaAine;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author omistaja
 */
public class OhjeDao {

    private Database database;

    public OhjeDao(Database database) {
        this.database = database;
    }

    public void saveOrUpdate(Integer annosId, Integer raakaAineId, Ohje object) throws SQLException {

        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO Ohje (jarjestys, maara, ohje, annos_id, raaka_aine_id) VALUES (?, ?, ?, ?, ?)");
            stmt.setInt(1, object.getJarjestys());
            stmt.setDouble(2, object.getMaara());
            stmt.setString(3, object.getOhje());
            stmt.setInt(4, annosId);
            stmt.setInt(5, raakaAineId);
            stmt.executeUpdate();
        }

    }

    public List<Ohje> findAnnoksenOhjeet(Integer annoksenId) throws SQLException {
        List<Ohje> ohjeet = new ArrayList<>();

        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT jarjestys, maara, ohje, raaka_aine_id FROM Ohje WHERE annos_id = ?");
            stmt.setInt(1, annoksenId);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ohjeet.add(new Ohje(rs.getInt("jarjestys"), rs.getDouble("maara"), rs.getString("ohje"), rs.getInt("raaka_aine_id")));
            }

        }

        ohjeet.sort(Comparator.comparingInt(Ohje::getJarjestys));

        return ohjeet;

    }

    public List<String> ohjeenTulostus(Integer key) throws SQLException {
        List<Ohje> tulostettava = this.findAnnoksenOhjeet(key);
        List<String> palautettava = new ArrayList<>();
        RaakaAineDao aineet = new RaakaAineDao(database);

        for (int i = 0; i < tulostettava.size(); i++) {
            String lisattavaRaakaAine = aineet.findByKey(tulostettava.get(i).getRaakaAineId()).getName();
            String lisattava = lisattavaRaakaAine + ", " + tulostettava.get(i).getMaara() + " dl" + ", valmistus: ";
            if(tulostettava.get(i).getOhje().isEmpty()){
                lisattava += "ei määritelty \n";
            } else {
                lisattava += tulostettava.get(i).getOhje() + "\n";
            }
            palautettava.add(lisattava);
        }

        return palautettava;
    }

    public void delete(Integer key) throws SQLException {
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM Ohje WHERE annos_id = ?");
            stmt.setInt(1, key);
            stmt.executeUpdate();
        }
    }
}
