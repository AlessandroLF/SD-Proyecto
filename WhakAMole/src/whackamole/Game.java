/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whackamole;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.*;
import java.net.*;
import javax.swing.ImageIcon;
import javax.swing.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.util.ArrayList;

/**
 *
 * @author lomba
 */
public class Game extends JFrame implements MouseMotionListener, MouseListener{

    /**
     * Creates new form Game
     */
    public Game() {
        initComponents();
        try {
            init();
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private DataOutputStream out;
    public ArrayList<Jugador> jugadores;
    public String name;
    public int color;
    public int gamePort;
    public int hitPort;
    public String IP;
    Boolean[] mole;
    Boolean[] falses;
    private ImageIcon spot1;
    private ImageIcon spot2;
    private ImageIcon spot3;
    private ImageIcon spot4;
    private ImageIcon spot5;
    private ImageIcon spot6;
    private ImageIcon spoted1;
    private ImageIcon spoted2;
    private ImageIcon spoted3;
    private ImageIcon spoted4;
    private ImageIcon spoted5;
    private ImageIcon spoted6;
    Socket hitSocket;
    
    private void init() throws IOException, InterruptedException{
        cursores();
        initListeners();
        setLocationRelativeTo(this);
        addMouseMotionListener(this);
        addMouseListener(this);
        icons();
        setSpots();
        falses = new Boolean[6];
        for(Boolean b : falses)
            b = false;
        mole = falses.clone();
        jLabel2.setIcon(new ImageIcon(getClass().getResource("/resources/battlefield.png")));
    }
    
    public void icons(){
            spot1 = new ImageIcon(getClass().getResource("/resources/1spot.png"));
            spot2 = new ImageIcon(getClass().getResource("/resources/2spot.png"));
            spot3 = new ImageIcon(getClass().getResource("/resources/3spot.png"));
            spot4 = new ImageIcon(getClass().getResource("/resources/6spot.png"));
            spot5 = new ImageIcon(getClass().getResource("/resources/4spot.png"));
            spot6 = new ImageIcon(getClass().getResource("/resources/5spot.png"));
            spoted1 = new ImageIcon(getClass().getResource("/resources/1spoted.png"));
            spoted2 = new ImageIcon(getClass().getResource("/resources/2spoted.png"));
            spoted3 = new ImageIcon(getClass().getResource("/resources/3spoted.png"));
            spoted4 = new ImageIcon(getClass().getResource("/resources/6spoted.png"));
            spoted5 = new ImageIcon(getClass().getResource("/resources/4spoted.png"));
            spoted6 = new ImageIcon(getClass().getResource("/resources/5spoted.png"));
            
    }
    
    public void setSpots(){
        ImageIcon i = new ImageIcon(getClass().getResource("/resources/noDroid.png"));
        jButton1.setIcon(spot1);
        jButton2.setIcon(spot2);
        jButton3.setIcon(spot3);
        jButton4.setIcon(spot4);
        jButton5.setIcon(spot5);
        jButton6.setIcon(spot6);
    }
    
    private void initListeners(){
        jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                mouseEnteredPanel(evt);
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                mouseExitedPanel(evt);
            }
        });
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jButton1MouseReleased(evt);
            }
        });
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jButton1MouseReleased(evt);
            }
        });
        jButton4.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jButton1MouseReleased(evt);
            }
        });
        jButton5.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jButton1MouseReleased(evt);
            }
        });
        jButton6.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jButton1MouseReleased(evt);
            }
        });
    }
    
    private void cursores(){
        ImageIcon i = new ImageIcon(getClass().getResource("/resources/null_pointer.png"));
        Cursor c = Toolkit.getDefaultToolkit().createCustomCursor(i.getImage(), new Point(1,1), "Null-Pointer");
        jPanel1.setCursor(c);
        jLayeredPane1.setCursor(createTransparentCursor(30, 0, Color.BLUE));
    }
    
    public void updateScores(){
            player1.setText(jugadores.get(0).getName() + ":" + jugadores.get(0).getPoints());
        if(jugadores.size()>1)
            player2.setText(jugadores.get(1).getName() + ":" + jugadores.get(1).getPoints());
        if(jugadores.size()>2)
            player3.setText(jugadores.get(2).getName() + ":" + jugadores.get(2).getPoints());
        if(jugadores.size()>3)
            player4.setText(jugadores.get(3).getName() + ":" + jugadores.get(3).getPoints());
    }
    
    public static synchronized Cursor createTransparentCursor( int size, int frameThickness, Color frameColor ) {
        final int cursourSize = size + (2 * frameThickness);

        final BufferedImage bufferedImage = new BufferedImage( 32 + 2, 32 + 2, BufferedImage.TYPE_INT_ARGB );
        final Graphics graphic = bufferedImage.getGraphics();
        final Color colTrans = new Color( 0, 0, 0, 0 );
        for( int i = 0 ; i < cursourSize ; i++ ){
                for( int j = 0 ; j < cursourSize ; j++ ){
                        if( i < frameThickness || i >= cursourSize - frameThickness || j < frameThickness || j > cursourSize - frameThickness - 1 ){
                                graphic.setColor( frameColor );
                        }
                        else{
                                graphic.setColor( colTrans );
                        }
                        graphic.fillRect( i, j, 1, 1 );
                }
        }
        final Point hotSpot = new Point(15, 15);
        return Toolkit.getDefaultToolkit().createCustomCursor( bufferedImage, hotSpot, "Trans" );
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        statusLabel = new javax.swing.JLabel();
        player1 = new javax.swing.JLabel();
        player3 = new javax.swing.JLabel();
        player4 = new javax.swing.JLabel();
        player2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton7 = new javax.swing.JButton();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        lsLabel = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Whack-a-Mole");
        setBackground(new java.awt.Color(255, 255, 255));
        setBounds(new java.awt.Rectangle(400, 500, 400, 400));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setName("frame"); // NOI18N
        setPreferredSize(new java.awt.Dimension(784, 704));
        setResizable(false);
        setSize(new java.awt.Dimension(784, 637));

        jLabel1.setText("Nombre:");

        jTextField1.setText("jugador");

        jButton7.setText("Registrar");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(67, 67, 67)
                .addComponent(player1, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(121, 121, 121)
                .addComponent(player2, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(player3, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(69, 69, 69)
                .addComponent(player4, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(66, 66, 66))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(statusLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(75, 75, 75)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton7)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(statusLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton7)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(player1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(player3, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
                    .addComponent(player4, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
                    .addComponent(player2, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE))
                .addContainerGap())
        );

        jLayeredPane1.setMinimumSize(new java.awt.Dimension(784, 542));
        jLayeredPane1.setPreferredSize(new java.awt.Dimension(784, 610));
        jLayeredPane1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lsLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lsLabel.setIconTextGap(0);
        lsLabel.setMaximumSize(new java.awt.Dimension(200, 200));
        lsLabel.setMinimumSize(new java.awt.Dimension(100, 100));
        jLayeredPane1.setLayer(lsLabel, javax.swing.JLayeredPane.DRAG_LAYER);
        jLayeredPane1.add(lsLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 90, 171, 174));
        lsLabel.getAccessibleContext().setAccessibleParent(jLayeredPane1);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/1spot.png"))); // NOI18N
        jButton1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jButton1MouseDragged(evt);
            }
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jButton1MouseMoved(evt);
            }
        });
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jButton1MouseReleased(evt);
            }
        });
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jLayeredPane1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(195, 108, 89, 180));

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/2spot.png"))); // NOI18N
        jButton2.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jButton2MouseDragged(evt);
            }
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jButton2MouseMoved(evt);
            }
        });
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jLayeredPane1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(361, 213, 89, 180));

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/3spot.png"))); // NOI18N
        jButton3.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jButton3MouseDragged(evt);
            }
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jButton3MouseMoved(evt);
            }
        });
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jLayeredPane1.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 154, 89, 180));

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/6spot.png"))); // NOI18N
        jButton4.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jButton4MouseDragged(evt);
            }
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jButton4MouseMoved(evt);
            }
        });
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jLayeredPane1.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(532, 336, 89, 180));

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/4spot.png"))); // NOI18N
        jButton5.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jButton5MouseDragged(evt);
            }
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jButton5MouseMoved(evt);
            }
        });
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jLayeredPane1.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(48, 313, 89, 180));

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/5spot.png"))); // NOI18N
        jButton6.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jButton6MouseDragged(evt);
            }
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jButton6MouseMoved(evt);
            }
        });
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jLayeredPane1.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(251, 355, 89, 180));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/battlefield.png"))); // NOI18N
        jLayeredPane1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 540));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLayeredPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLayeredPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 542, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        //statusLabel.setText("Not Killed 1");
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton1MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseMoved
        ImageIcon i = new ImageIcon(getClass().getResource("/resources/lightsaber.png"));
        lsLabel.setIcon(i);
        lsLabel.setLocation(evt.getX()+jButton1.getX()-60, evt.getY()+jButton1.getY()-90);
    }//GEN-LAST:event_jButton1MouseMoved

    private void jButton2MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseMoved
        ImageIcon i = new ImageIcon(getClass().getResource("/resources/lightsaber.png"));
        lsLabel.setIcon(i);
        lsLabel.setLocation(evt.getX()+jButton2.getX()-60, evt.getY()+jButton2.getY()-90);
    }//GEN-LAST:event_jButton2MouseMoved

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        //statusLabel.setText("Not Killed 2");
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseMoved
        ImageIcon i = new ImageIcon(getClass().getResource("/resources/lightsaber.png"));
        lsLabel.setIcon(i);
        lsLabel.setLocation(evt.getX()+jButton3.getX()-60, evt.getY()+jButton3.getY()-90);
    }//GEN-LAST:event_jButton3MouseMoved

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        //statusLabel.setText("Not Killed 3");
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton4MouseMoved
        ImageIcon i = new ImageIcon(getClass().getResource("/resources/lightsaber.png"));
        lsLabel.setIcon(i);
        lsLabel.setLocation(evt.getX()+jButton4.getX()-60, evt.getY()+jButton4.getY()-90);
    }//GEN-LAST:event_jButton4MouseMoved

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        //statusLabel.setText("Not Killed 4");
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton5MouseMoved
        ImageIcon i = new ImageIcon(getClass().getResource("/resources/lightsaber.png"));
        lsLabel.setIcon(i);
        lsLabel.setLocation(evt.getX()+jButton5.getX()-60, evt.getY()+jButton5.getY()-90);
    }//GEN-LAST:event_jButton5MouseMoved

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        //statusLabel.setText("Not Killed 5");
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton6MouseMoved
        ImageIcon i = new ImageIcon(getClass().getResource("/resources/lightsaber.png"));
        lsLabel.setIcon(i);
        lsLabel.setLocation(evt.getX()+jButton6.getX()-60, evt.getY()+jButton6.getY()-90);
    }//GEN-LAST:event_jButton6MouseMoved

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        //statusLabel.setText("Not Killed 6");
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton1MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseDragged
        lsLabel.setLocation(evt.getX(), evt.getY());
        timer ++;
        if(timer == 1){
            ImageIcon i = new ImageIcon(getClass().getResource("/resources/lightsaberSwing.png"));
            lsLabel.setIcon(i);
        }
        lsLabel.setLocation(evt.getX()+jButton1.getX()-60, evt.getY()+jButton1.getY()-90);
        if(timer == 11){
            ImageIcon i = new ImageIcon(getClass().getResource("/resources/lightsaberSwinged.png"));
            lsLabel.setIcon(i);
            if(mole[0]){
                try {
                    out.writeUTF(name);
                } catch (IOException ex) {
                    statusLabel.setText("Connection Error");
                }
            }else{
                jButton1.setIcon(spot1);
            }
        }
    }//GEN-LAST:event_jButton1MouseDragged

    private void jButton2MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseDragged
       timer ++;
        if(timer == 1){
            ImageIcon i = new ImageIcon(getClass().getResource("/resources/lightsaberSwing.png"));
            lsLabel.setIcon(i);
        }
        lsLabel.setLocation(evt.getX()+jButton2.getX()-60, evt.getY()+jButton2.getY()-90);
        if(timer == 11){
            ImageIcon i = new ImageIcon(getClass().getResource("/resources/lightsaberSwinged.png"));
            lsLabel.setIcon(i);
            if(mole[1]){
                try {
                    out.writeUTF(name);
                } catch (IOException ex) {
                    statusLabel.setText("Connection Error");
                }
            }else{
                jButton2.setIcon(spot1);
            }
        }
    }//GEN-LAST:event_jButton2MouseDragged

    private void jButton3MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseDragged
        timer ++;
        if(timer == 1){
            ImageIcon i = new ImageIcon(getClass().getResource("/resources/lightsaberSwing.png"));
            lsLabel.setIcon(i);
        }
        lsLabel.setLocation(evt.getX()+jButton3.getX()-60, evt.getY()+jButton3.getY()-90);
        if(timer == 11){
            ImageIcon i = new ImageIcon(getClass().getResource("/resources/lightsaberSwinged.png"));
            lsLabel.setIcon(i);
            if(mole[2]){
                try {
                    out.writeUTF(name);
                } catch (IOException ex) {
                    statusLabel.setText("Connection Error");
                }
            }else{
                jButton3.setIcon(spot1);
            }
        }
    }//GEN-LAST:event_jButton3MouseDragged

    private void jButton5MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton5MouseDragged
        timer ++;
        if(timer == 1){
            ImageIcon i = new ImageIcon(getClass().getResource("/resources/lightsaberSwing.png"));
            lsLabel.setIcon(i);
        }
        lsLabel.setLocation(evt.getX()+jButton5.getX()-60, evt.getY()+jButton5.getY()-90);
        if(timer == 11){
            ImageIcon i = new ImageIcon(getClass().getResource("/resources/lightsaberSwinged.png"));
            lsLabel.setIcon(i);
            if(mole[4]){
                try {
                    out.writeUTF(name);
                } catch (IOException ex) {
                    statusLabel.setText("Connection Error");
                }
            }else{
                jButton5.setIcon(spot1);
            }
        }
    }//GEN-LAST:event_jButton5MouseDragged

    private void jButton6MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton6MouseDragged
        timer ++;
        if(timer == 1){
            ImageIcon i = new ImageIcon(getClass().getResource("/resources/lightsaberSwing.png"));
            lsLabel.setIcon(i);
        }
        lsLabel.setLocation(evt.getX()+jButton6.getX()-60, evt.getY()+jButton6.getY()-90);
        if(timer == 11){
            ImageIcon i = new ImageIcon(getClass().getResource("/resources/lightsaberSwinged.png"));
            lsLabel.setIcon(i);
            if(mole[5]){
                try {
                    out.writeUTF(name);
                } catch (IOException ex) {
                    statusLabel.setText("Connection Error");
                }
            }else{
                jButton6.setIcon(spot1);
            }
        }
    }//GEN-LAST:event_jButton6MouseDragged

    private void jButton4MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton4MouseDragged
        timer ++;
        if(timer == 1){
            ImageIcon i = new ImageIcon(getClass().getResource("/resources/lightsaberSwing.png"));
            lsLabel.setIcon(i);
        }
        lsLabel.setLocation(evt.getX()+jButton4.getX()-60, evt.getY()+jButton4.getY()-90);
        if(timer == 11){
            ImageIcon i = new ImageIcon(getClass().getResource("/resources/lightsaberSwinged.png"));
            lsLabel.setIcon(i);
            if(mole[3]){
                try {
                    out.writeUTF(name);
                } catch (IOException ex) {
                    statusLabel.setText("Connection Error");
                }
            }else{
                jButton1.setIcon(spot4);
            }
        }
    }//GEN-LAST:event_jButton4MouseDragged

    private void jButton1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseReleased
        ImageIcon i = new ImageIcon(getClass().getResource("/resources/lightsaber.png"));
        lsLabel.setIcon(i);
        timer = 0;
    }//GEN-LAST:event_jButton1MouseReleased

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        try {
            jButton7.setEnabled(false);
            DatagramSocket registrySocket = new DatagramSocket();
            name = jTextField1.getText();
            byte[] m = name.getBytes();
            DatagramPacket request = new DatagramPacket(m, m.length, InetAddress.getByName("localhost"), 6790);
            registrySocket.send(request);
            registrySocket.close();
            ServerSocket listenRSocket = new ServerSocket(6791);
            ObjectInputStream in = new ObjectInputStream(listenRSocket.accept().getInputStream());
            RgistroRespuesta response = (RgistroRespuesta) in.readObject();
            jugadores = response.getJugadores();
            gamePort = response.getGameReportPort();
            hitPort = response.getHittPort();
            IP = response.getIP();
            listenRSocket.close();
            updateScores();
            jLayeredPane1.setVisible(true);
            if(hitSocket != null)
                hitSocket.close();
            hitSocket = new Socket("localhost", hitPort);
            out = new DataOutputStream(hitSocket.getOutputStream());
            new MulticastListener().start();
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.FINE, null, ex);
        }
    }//GEN-LAST:event_jButton7ActionPerformed
    
    public void manualRegisry(int i, int k){
        try {
            DatagramSocket registrySocket = new DatagramSocket();
            name = "StressClient" + i + "" + k;
            byte[] m = name.getBytes();
            DatagramPacket request = new DatagramPacket(m, m.length, InetAddress.getByName("localhost"), 6790);
            registrySocket.send(request);
            registrySocket.close();
            ServerSocket listenRSocket = new ServerSocket(6791);
            ObjectInputStream in = new ObjectInputStream(listenRSocket.accept().getInputStream());
            RgistroRespuesta response = (RgistroRespuesta) in.readObject();
            jugadores = response.getJugadores();
            gamePort = response.getGameReportPort();
            hitPort = response.getHittPort();
            IP = response.getIP();
            listenRSocket.close();
            updateScores();
            Socket hitSocket = new Socket("localhost", hitPort);
            out = new DataOutputStream(hitSocket.getOutputStream());
            new MulticastListener().start();
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.FINE, null, ex);
        }
    }
    
    public void manualRegisry(int i){
        try {
            DatagramSocket registrySocket = new DatagramSocket();
            name = "StressClient" + i;
            byte[] m = name.getBytes();
            DatagramPacket request = new DatagramPacket(m, m.length, InetAddress.getByName("localhost"), 6790);
            registrySocket.send(request);
            registrySocket.close();
            ServerSocket listenRSocket = new ServerSocket(6791);
            ObjectInputStream in = new ObjectInputStream(listenRSocket.accept().getInputStream());
            RgistroRespuesta response = (RgistroRespuesta) in.readObject();
            jugadores = response.getJugadores();
            gamePort = response.getGameReportPort();
            hitPort = response.getHittPort();
            IP = response.getIP();
            listenRSocket.close();
            updateScores();
            Socket hitSocket = new Socket("localhost", hitPort);
            out = new DataOutputStream(hitSocket.getOutputStream());
            new MulticastListener().start();
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.FINE, null, ex);
        }
    }
    
    public void getOut(){
        try {
            if(hitSocket != null)
                hitSocket.close();
            hitSocket = new Socket("localhost", 6792);
            out = new DataOutputStream(hitSocket.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void manualHit(int iname){
        try {
            if(hitSocket == null){
                hitSocket = new Socket("localhost", 6792);
                out = new DataOutputStream(hitSocket.getOutputStream());
            }
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(!mole.equals(falses)){
            try {
                out.writeUTF("Estresador" + iname);
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            System.out.println("No hay topo");
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Game.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Game.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Game.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Game.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            Game tablero = new Game();
            tablero.setVisible(true);
            tablero.count(5);
        });
    }
    
    
    public class Erease extends Thread{
        int ind;
        
        public Erease(int i){
            ind = i;
        }
        
        
        @Override
        public void run(){
            try {
                Thread.sleep(1500);
                if(ind == 1)
                    jButton1.setIcon(spot1);
                if(ind == 2)
                    jButton2.setIcon(spot2);
                if(ind == 3)
                    jButton3.setIcon(spot3);
                if(ind == 4)
                    jButton4.setIcon(spot4);
                if(ind == 5)
                    jButton5.setIcon(spot5);
                if(ind == 6)
                    jButton6.setIcon(spot6);
                mole[ind -1] = false;
            } catch (InterruptedException ex) {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void molePop(int where){
        mole[where-1] = true;
        if(where == 1)
            jButton1.setIcon(spoted1);
        if(where == 2)
            jButton2.setIcon(spoted2);
        if(where == 3)
            jButton3.setIcon(spoted3);
        if(where == 4)
            jButton4.setIcon(spoted4);
        if(where == 5)
            jButton5.setIcon(spoted5);
        if(where == 6)
            jButton6.setIcon(spoted6);
        
        new Erease(where).start();
    }
    
    private class MulticastListener extends Thread{
        
        @Override
        public void run(){
            MulticastSocket s = null;
            try {
                InetAddress group = InetAddress.getByName("228.5.6.7"); // destination multicast group 
                s = new MulticastSocket(gamePort);
                s.joinGroup(group); 
                while(true) {
                    byte[] buffer = new byte[1000];
                    DatagramPacket messageIn = new DatagramPacket(buffer, buffer.length);
                    s.receive(messageIn);
                    String response = new String(messageIn.getData());
                    String[] res = response.split(",");
                    int ind;
                    if(res[0].charAt(0) == '-'){
                        ind = -1* Integer.parseInt(res[0].charAt(1) + "");
                    }else{
                        ind = Integer.parseInt(res[0].charAt(0)+"");
                    }
                    if(ind == 0){
                        String nname = response.split(",")[1];
                        int ind2 = Integer.parseInt(response.split(",")[2].charAt(0)+"");
                        boolean esta = false;
                        for(Jugador ji : jugadores){
                            if(ji.getName().equals(nname)){
                                esta = true;
                            }
                        }
                        if(!esta){
                            Jugador j = new Jugador(nname);
                            jugadores.add(j);
                        }
                        jugadores.get(ind2).hit();
                        mole = falses.clone();
                        setSpots();
                        updateScores();
                    }
                    if(ind < 0){
                        statusLabel.setText("Ganó " + jugadores.get((ind * -1) - 1).getName() + "!");
                    }
                    if(ind > 0){
                        molePop(ind);
                    }
                }		
            }
            catch (SocketException e){
                System.out.println("Socket: " + e.getMessage());
            }
            catch (IOException e){
                System.out.println("IO: " + e.getMessage());
            }
            finally {
                if(s != null) s.close();
            }
        }	
    }
    
    public synchronized void count(int s){
        new Count(s).start();
    }
    
    private class Count extends Thread {
        
        int delay;
        
        public Count (int s){
            delay = s;
        }
        
        @Override
        public void run(){
            try {
                Thread.sleep(delay * 1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    @Override
    public void mouseMoved(MouseEvent e){
        
        ImageIcon i = new ImageIcon(getClass().getResource("/resources/lightsaber.png"));
        lsLabel.setIcon(i);
        lsLabel.setLocation(e.getX()-60, e.getY()-200);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel lsLabel;
    private javax.swing.JLabel player1;
    private javax.swing.JLabel player2;
    private javax.swing.JLabel player3;
    private javax.swing.JLabel player4;
    private javax.swing.JLabel statusLabel;
    // End of variables declaration//GEN-END:variables

    static int timer = 0;
    
    @Override
    public void mouseDragged(MouseEvent e) {
        timer ++;
        if(timer == 1){
            ImageIcon i = new ImageIcon(getClass().getResource("/resources/lightsaberSwing.png"));
            lsLabel.setIcon(i);
        }
        lsLabel.setLocation(e.getX()-60, e.getY()-180);
        if(timer == 11){
            ImageIcon i = new ImageIcon(getClass().getResource("/resources/lightsaberSwinged.png"));
            lsLabel.setIcon(i);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        statusLabel.setText("Miss");
        ImageIcon i = new ImageIcon(getClass().getResource("/resources/lightsaberSwinged.png"));
        lsLabel.setIcon(i);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        ImageIcon i = new ImageIcon(getClass().getResource("/resources/lightsaber.png"));
        lsLabel.setIcon(i);
        timer = 0;
    }

    @Override
    public void mouseEntered(MouseEvent e) {}
    
    public void mouseEnteredPanel(MouseEvent e) {
        lsLabel.setIcon(null);
    }
    
    public void mouseExitedPanel(MouseEvent e) {
        lsLabel.setIcon(new ImageIcon(getClass().getResource("/resources/lightsaber.png")));
    }

    @Override
    public void mouseExited(MouseEvent e) {}
}