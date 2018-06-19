/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author omistaja
 */
public class TaulujenLuonti {

    private Database database;

    public TaulujenLuonti(Database database) {
        this.database = database;
    }

    public void luoTauluAnnos() throws SQLException {
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(
                    "CREATE TABLE Annos(\n"
                    + "id SERIAL PRIMARY KEY,\n"
                    + "nimi varchar(200)\n"
                    + ")");

            stmt.executeQuery();

        }
    }

    public void luoTauluRaakaAine() throws SQLException {
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(
                    "CREATE TABLE RaakaAine (\n"
                    + "id SERIAL PRIMARY KEY,\n"
                    + "nimi varchar(200)\n"
                    + ")");

            stmt.executeQuery();

        }
    }

    public void luoTauluOhje() throws SQLException {
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(
                    "CREATE TABLE Ohje (\n"
                    + "jarjestys integer,\n"
                    + "maara float,\n"
                    + "ohje varchar(1000),\n"
                    + "annos_id integer,\n"
                    + "raaka_aine_id integer,\n"
                    + "FOREIGN KEY(annos_id) REFERENCES Annos(id),\n"
                    + "FOREIGN KEY(raaka_aine_id) REFERENCES RaakaAine(id),\n"
                    + "PRIMARY KEY (annos_id, raaka_aine_id)\n"
                    + ")");

            stmt.executeQuery();

        }
    }
}
