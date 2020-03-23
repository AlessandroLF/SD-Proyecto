/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whackamole;

import java.io.*;
import java.net.DatagramPacket;
import java.net.*;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lomba
 */
public class Server{
    
    ArrayList<Jugador> jugadores;
    int N;
    
    
    public  Server(){
        jugadores = new ArrayList<>(500);
        N = 10;
    }
    
    public  Server(int n){
        jugadores = new ArrayList<>();
        N = n;
    }
    
    public void main(){
        new Multicast().start();
        new Aux(this).start();
        new Aux2().start();
    }
    
    class Aux2 extends Thread{
        
        
        @Override
        public void run(){
            try{
                int serverRegistryPort = 6792;
                ServerSocket listenRSocket = new ServerSocket(serverRegistryPort);
                while(true){
                    Socket clientRSocket = listenRSocket.accept();
                    Connection c = new Connection(clientRSocket);
                    c.start();
                }
            }catch(IOException e) {
                System.out.println("Conection :"+ e.getMessage());

            }
        }
    }
    
    class Aux extends Thread{
        
        private Server s;
        
        public Aux(Server ss){
            s = ss;
        }
        
        @Override
        public void run(){
            //try{
                //int serverRegistryPort = 6790;
                //ServerSocket listenRSocket = new ServerSocket(serverRegistryPort);
                //while(true){
                    //Socket clientRSocket = listenRSocket.accept();
                    Registro r = new Registro(s);
                    r.start();
                //}
            //}catch(IOException e) {
                //System.out.println("Listen :"+ e.getMessage());

            //}
        }
    }
    
    class Registro extends Thread{
        
        DataInputStream in;
	DatagramSocket clientSocket;
        Server s;
        
	public Registro (Server server) {
	    try {
                s = server;
		clientSocket = new DatagramSocket(6790);
	     } catch(IOException e)  {
                 System.out.println("Registro: "+ e.getMessage());
             }
	}
        
        @Override
        public void run(){
            try {
                byte[] b = new byte[1000];
                while(true){
                    DatagramPacket request = new DatagramPacket(b, b.length);
                    clientSocket.receive(request);
                    String j = new String(request.getData()).trim();
                    ArrayList<Jugador>js = s.jugadores;
                    boolean sesion = false;
                    for(Jugador ji : js){
                        if(j.equals(ji.getName()))
                            sesion = true;
                        if(sesion)
                            break;
                    }
                    if(!sesion){
                        s.jugadores.add(new Jugador(j));
                    }
                    Socket resSocket = new Socket("localhost", 6791);
                    ObjectOutputStream out = new ObjectOutputStream(resSocket.getOutputStream());
                    RgistroRespuesta rr = new RgistroRespuesta(js, 6789, 6792, resSocket.getLocalAddress().toString());
                    out.writeObject(rr);
                    resSocket.close();
                }
            } catch (IOException ex) {
                //Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
            finally {
                try{
                    clientSocket.close();
                }catch(Exception e){
                    
                }
            }
        }
        
    }
    
    class Connection extends Thread {
	DataInputStream in;
	Socket clientSocket;
	public Connection (Socket aClientSocket) {
	    try {
		clientSocket = aClientSocket;
		in = new DataInputStream(clientSocket.getInputStream());
	     } catch(IOException e)  {
                 System.out.println("Connection:"+e.getMessage());
             }
	}
        
        @Override
	public void run(){
            try {
                while(true){
                    String msg = in.readUTF();
                    Jugador js = null;
                    for(Jugador j : jugadores){
                        if(j.getName().equals(msg)){
                            js = j;
                            break;
                        }
                    }
                    if(js == null){
                        js = new Jugador(msg);
                        jugadores.add(js);
                    }
                    js.hit();
                    MulticastSocket s = null;
                    try {
                        InetAddress group = InetAddress.getByName("228.5.6.7"); // destination multicast group 
                        s = new MulticastSocket(6789);
                        s.joinGroup(group);
                        String myMessage;
                        if(js.getPoints() >= 10)
                            myMessage = (-1 * (jugadores.indexOf(js) + 1)) + "";
                        else
                            myMessage = "0," +js.getName() + "," + jugadores.indexOf(js);
                        byte [] m = myMessage.getBytes();
                        DatagramPacket messageOut = new DatagramPacket(m, m.length, group, 6789);
                        s.send(messageOut);
                        s.leaveGroup(group);		
                    }
                    catch (SocketException e){
                        System.out.println("Socket: " + e.getMessage());
                    }
                }
            } catch (Exception ex) {
                //Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
            finally {
                try {
                    clientSocket.close();
                } catch (IOException e){
                    System.out.println(e);
                }
            }
        }
    }
    
    class Multicast extends Thread{
        
        @Override
        public void run(){
            MulticastSocket s = null;
            try {
                InetAddress group = InetAddress.getByName("228.5.6.7"); // destination multicast group 
                s = new MulticastSocket(6789);
                s.joinGroup(group);
                while(true){
                    Random random = new Random();
                    String myMessage = (random.nextInt(6) + 1) + "";
                    byte [] m = myMessage.getBytes();
                    DatagramPacket messageOut = new DatagramPacket(m, m.length, group, 6789);
                    s.send(messageOut);
                    Thread.sleep(3000);
                }
                //s.leaveGroup(group);		
            }
            catch (SocketException e){
                System.out.println("Socket: " + e.getMessage());
            }
            catch (IOException e){
                System.out.println("IO: " + e.getMessage());
            } catch (InterruptedException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
            finally {
               if(s != null) s.close();
            }
        }
    }
    
}
