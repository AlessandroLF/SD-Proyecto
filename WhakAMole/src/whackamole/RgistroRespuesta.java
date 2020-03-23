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
public class RgistroRespuesta implements Serializable{
    
    private ArrayList<Jugador> jugadores;
    int gameReportPort;
    int hittPort;
    String IP;

    public RgistroRespuesta(ArrayList<Jugador> jugadores, int gameReportPort, int hittPort, String IP) {
        this.jugadores = jugadores;
        this.gameReportPort = gameReportPort;
        this.hittPort = hittPort;
        this.IP = IP;
    }

    public ArrayList<Jugador> getJugadores() {
        return jugadores;
    }

    public int getGameReportPort() {
        return gameReportPort;
    }

    public int getHittPort() {
        return hittPort;
    }
    
    public String getIP(){
        return IP;
    }
    
}
