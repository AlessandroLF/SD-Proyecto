/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whackamole;

/**
 *
 * @author lomba
 */
public class Starter {
    
    public static void main (String[] args){
        java.awt.EventQueue.invokeLater(() -> {
            Game tablero = new Game();
            tablero.setVisible(true);
        });
        new Server().main();
    }
    
}
