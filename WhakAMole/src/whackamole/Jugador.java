/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whackamole;

import java.io.Serializable;

/**
 *
 * @author lomba
 */
public class Jugador implements Serializable{
    
    private String name;
    private int points;

    public Jugador(String name) {
        this.name = name;
        points = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPoints() {
        return points;
    }
    
    public void hit(){
        points++;
    }

    public void setPoints(int points) {
        this.points = points;
    }
    
}
