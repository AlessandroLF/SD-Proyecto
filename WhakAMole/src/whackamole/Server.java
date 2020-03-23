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
        jugadores = new ArrayList<>();
        N = 10;
    }
    
    public  Server(int n){
        jugadores = new ArrayList<>();
        N = n;
    }
    
    public void main(){
        new Multicast().start();
        new Aux(this).start();
        try {
            int serverPort = 6792;
            ServerSocket listenSocket = new ServerSocket(serverPort);
            while(true){
                System.out.println("Hits");
                Socket clientSocket = listenSocket.accept();
                Connection c = new Connection(clientSocket);
                c.start();
            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    class Aux extends Thread{
        
        private Server s;
        
        public Aux(Server ss){
            s = ss;
        }
        
        @Override
        public void run(){
            try{
                int serverRegistryPort = 6790;
                ServerSocket listenRSocket = new ServerSocket(serverRegistryPort);
                while(true){
                    System.out.println("Registros");
                    Socket clientRSocket = listenRSocket.accept();
                    Registro r = new Registro(clientRSocket, s);
                    r.start();
                }
            }catch(IOException e) {
                System.out.println("Listen :"+ e.getMessage());

            }
        }
    }
    
    class Registro extends Thread{
        
        ObjectInputStream in;
	Socket clientSocket;
        Server s;
        
	public Registro (Socket aClientSocket, Server server) {
	    try {
                s = server;
		clientSocket = aClientSocket;
		in = new ObjectInputStream(clientSocket.getInputStream());
	     } catch(IOException e)  {
                 System.out.println("Registro: "+ e.getMessage());
             }
	}
        
        @Override
        public void run(){
            try {
                Jugador j = (Jugador) in.readObject();
                ArrayList<Jugador>js = s.jugadores;
                boolean sesion = false;
                for(Jugador ji : js){
                    if(j.getName().equals(ji.getName()))
                        sesion = true;
                    if(sesion)
                        break;
                }
                if(!sesion){
                    js.add(j);
                    s.jugadores = js;
                }
                Socket resSocket = new Socket("localhost", 6791);
                ObjectOutputStream out = new ObjectOutputStream(resSocket.getOutputStream());
                RgistroRespuesta rr = new RgistroRespuesta(js, 6789, 6792, resSocket.getLocalAddress().toString());
                out.writeObject(rr);
                resSocket.close();
            } catch (ClassNotFoundException | IOException ex) {
                System.out.println("No leyo bien");
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
                    for(Jugador j : jugadores){
                        if(j.getName().equals(msg)){
                            j.hit();
                            MulticastSocket s = null;
                            try {
                                InetAddress group = InetAddress.getByName("228.5.6.7"); // destination multicast group 
                                s = new MulticastSocket(6789);
                                s.joinGroup(group);
                                String myMessage;
                                if(j.getPoints() >= 10)
                                    myMessage = (-1 * (jugadores.indexOf(j) + 1)) + "";
                                else
                                    myMessage = "0" + jugadores.indexOf(j);
                                byte [] m = myMessage.getBytes();
                                DatagramPacket messageOut = new DatagramPacket(m, m.length, group, 6789);
                                s.send(messageOut);
                                s.leaveGroup(group);		
                            }
                            catch (SocketException e){
                                System.out.println("Socket: " + e.getMessage());
                            }
                                break;
                            }
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
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
                    //System.out.println(myMessage);
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
