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
public class Estresador {
    
    private static final int N = 20;
    
    public static void main (String[] args){
        for (int i = 0; i < N; i++){
//            new EstresaRegistros(i).start();
//            new EstresaJuego(i).start();
            new EstresaEntradas(i).start();
        }
        new Server().main();
    }
    
}

class EstresaRegistros extends Thread{
    
    int ind;
    int N;
    Game g;
    
    public EstresaRegistros(int i){
        g = new Game();
        ind = i;
    }
    
    @Override
    public void run(){
        long[] times = new long[100];
        for (int i = 0; i < 100; i++){
            //System.out.println("rr");
            long startTime = System.currentTimeMillis();
            g.manualRegisry(ind, i);
            long finTime = System.currentTimeMillis();
            times[i] = finTime - startTime;
//            try {
//                Thread.sleep(500);
//            } catch (InterruptedException ex) {
//                Logger.getLogger(EstresaRegistros.class.getName()).log(Level.SEVERE, null, ex);
//            }
        }
        System.out.println(Algebraic.prom(times) + "," + Algebraic.stdDev(times));
    }
}

class EstresaJuego extends Thread{

    int ind;
    Game g;
    
    public EstresaJuego(int i){
        g = new Game();
        //g.getOut();
        ind = i;
    }
    
    @Override
    public void run(){
        long[] times = new long[100];
        for (int i = 0; i < 100; i++){
            long startTime = System.currentTimeMillis();
            g.manualHit(ind);
            long finTime = System.currentTimeMillis();
            times[i] = finTime - startTime;
        }
        System.out.println(Algebraic.prom(times) + "," + Algebraic.stdDev(times));
    }
}

class EstresaEntradas extends Thread{

    int ind;
    Game g;
    
    public EstresaEntradas(int i){
        g = new Game();
        ind = i;
    }
    
    @Override
    public void run(){
        long[] times = new long[100];
        for (int i = 0; i < 100; i++){
            long startTime = System.currentTimeMillis();
            g.manualRegisry(ind);
            long finTime = System.currentTimeMillis();
            times[i] = finTime - startTime;
        }
        System.out.println(Algebraic.prom(times) + "," + Algebraic.stdDev(times));
    }
}

class Algebraic{
    
    public static double stdDev(long[] list){
        double sum = 0.0;
        double num = 0.0;

        for (int i=0; i < list.length; i++)
            sum+=list[i];

        double mean = sum / list.length;
        for (int i=0; i <list.length; i++)
            num+=Math.pow((list[i] - mean),2);

        return Math.sqrt(num/list.length);
    }
    
    public static double prom(long[] list){
        double p = 0;
        for( int i = 0; i < list.length; i++){
            p += list[i];
        }
        p = (double)p / list.length;
        return p;
    }
    
}
