/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

/**
 *
 * @author omistaja
 */
public class Ohje {
    private Integer jarjestys;
    private double maara;
    private String ohje;
    private Integer raakaAineId;
    
    public Ohje(Integer jarjestys, double maara, String ohje, Integer raakaAineId){
        this.jarjestys = jarjestys;
        this.maara = maara;
        this.ohje = ohje;
        this.raakaAineId = raakaAineId;
    }
    
    public Integer getJarjestys(){
        return this.jarjestys;
    }
    
    public double getMaara(){
        return this.maara;
    }
    
    public String getOhje(){
        return this.ohje;
    }
    
    public int getRaakaAineId(){
        return this.raakaAineId;
    }
    
}
