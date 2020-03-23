/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whackamole;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author lomba
 */
public class Status implements Serializable{
    
    private ArrayList<Jugador> js;
    private int flag;

    public ArrayList<Jugador> getJs() {
        return js;
    }

    public int getFlag() {
        return flag;
    }

    public Status(ArrayList<Jugador> js, int flag) {
        this.js = js;
        this.flag = flag;
    }
    
}
