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
public class RaakaAine {
    private Integer id;
    private String name;
    
    public RaakaAine(Integer id, String nimi){
        this.id = id;
        this.name = nimi;
    }
    
    public Integer getId(){
        return this.id;
    }
    
    public String getName(){
        return this.name;
    }
}
