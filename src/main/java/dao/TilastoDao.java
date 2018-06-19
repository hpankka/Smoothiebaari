/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import database.Database;
import domain.Ohje;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author omistaja
 */
public class TilastoDao {

    private Database database;

    public TilastoDao(Database database) {
        this.database = database;
    }

    public int haeRaakaAineidenLukumaara() throws SQLException {

        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT COUNT(*) FROM RaakaAine");

            ResultSet rs = stmt.executeQuery();

            int i = rs.getInt(1);

            return i;
        }
    }

    public int haeAnnostenLukumaara() throws SQLException {

        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT COUNT(*) FROM Annos");

            ResultSet rs = stmt.executeQuery();

            int i = rs.getInt(1);

            return i;
        }
    }

    public List<String> haeRaakaAineidenKayttomaarat() throws SQLException {
        List<String> palautettava = new ArrayList<>();

        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT RaakaAine.nimi AS raakaAine, COUNT(*) AS maara FROM RaakaAine, Ohje, Annos WHERE Annos.id = Ohje.annos_id AND Ohje.raaka_aine_id = RaakaAine.id GROUP BY RaakaAine.nimi;");

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String lisattavaRivi = "Raaka-ainetta '" + rs.getString("raakaAine") + "' on käytetty " + rs.getInt("maara") + " kertaa.";
                palautettava.add(lisattavaRivi);
            }

        }

        return palautettava;
    }

}
