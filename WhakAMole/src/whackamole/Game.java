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
    JButton[] bts;
    Boolean[] mole;
    
    private void init() throws IOException, InterruptedException{
        cursores();
        initListeners();
        setLocationRelativeTo(this);
        addMouseMotionListener(this);
        addMouseListener(this);
        jButton1.setIcon(null);
        jButton2.setIcon(null);
        jButton3.setIcon(null);
        jButton4.setIcon(null);
        jButton5.setIcon(null);
        jButton6.setIcon(null);
        bts = new JButton[6];
        bts[0] = jButton1;
        bts[1] = jButton2;
        bts[2] = jButton3;
        bts[3] = jButton4;
        bts[4] = jButton5;
        bts[5] = jButton6;
        mole = new Boolean[6];
        for(Boolean b : mole)
            b = false;
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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Whack-a-Mole");
        setBackground(new java.awt.Color(255, 255, 255));
        setBounds(new java.awt.Rectangle(400, 500, 400, 400));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setName("frame"); // NOI18N
        setResizable(false);

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
                .addComponent(statusLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 399, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton7)
                .addGap(0, 16, Short.MAX_VALUE))
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

        lsLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lsLabel.setIconTextGap(0);
        lsLabel.setMaximumSize(new java.awt.Dimension(200, 200));
        lsLabel.setMinimumSize(new java.awt.Dimension(100, 100));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/battle-droidT.png"))); // NOI18N
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

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/battle-droidTR.png"))); // NOI18N
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

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/battle-droidT.png"))); // NOI18N
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

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/battle-droidTR.png"))); // NOI18N
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

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/battle-droidT.png"))); // NOI18N
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

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/battle-droidTR.png"))); // NOI18N
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

        jLayeredPane1.setLayer(lsLabel, javax.swing.JLayeredPane.DRAG_LAYER);
        jLayeredPane1.setLayer(jButton1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jButton2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jButton3, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jButton4, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jButton5, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jButton6, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(17, 17, 17))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)))
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(lsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(107, 107, 107)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 44, Short.MAX_VALUE))
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addGap(160, 160, 160)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(77, 77, 77))
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addGap(261, 261, 261)
                .addComponent(lsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane1Layout.createSequentialGroup()
                .addContainerGap(87, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(60, 60, 60)
                .addComponent(jButton5)
                .addGap(88, 88, 88))
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(jButton2))
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addGap(91, 91, 91)
                        .addComponent(jButton3)))
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jButton6)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton4)
                        .addGap(55, 55, 55))))
        );

        lsLabel.getAccessibleContext().setAccessibleParent(jLayeredPane1);

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
                .addComponent(jLayeredPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
            Socket registrySocket = new Socket("localhost", 6790);
            ObjectOutputStream sol = new ObjectOutputStream(registrySocket.getOutputStream());
            Jugador j = new Jugador(jTextField1.getText());
            sol.writeObject(j);
            sol.close();
            registrySocket.close();
            ServerSocket listenRSocket = new ServerSocket(6791);
            ObjectInputStream in = new ObjectInputStream(listenRSocket.accept().getInputStream());
            RgistroRespuesta response = (RgistroRespuesta) in.readObject();
            name = jTextField1.getText();
            jugadores = response.getJugadores();
            gamePort = response.getGameReportPort();
            hitPort = response.getHittPort();
            IP = response.getIP();
            listenRSocket.close();
            updateScores();
            jLayeredPane1.setVisible(true);
            Socket hitSocket = new Socket("localhost", hitPort);
            out = new DataOutputStream(hitSocket.getOutputStream());
            new MulticastListener().start();
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.FINE, null, ex);
        }
    }//GEN-LAST:event_jButton7ActionPerformed
    
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
            ind = i-1;
        }
        
        @Override
        public void run(){
            try {
                Thread.sleep(1500);
            bts[ind].setIcon(null);
            mole[ind] = false;
            } catch (InterruptedException ex) {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void molePop(int where){
        System.out.println(where-1);
        mole[where-1] = true;
        bts[where-1].setIcon(new ImageIcon(getClass().getResource("/resources/battle-droidT.png")));
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
                    char[] res = response.toCharArray();
                    int ind = Integer.parseInt(res[0]+"");
                    if(ind == 0){
                        System.out.println(response.split(",")[1]);
                        String nname = response.split(",")[1];
                        int ind2 = Integer.parseInt(response.split(",")[2].charAt(0)+"");
                        System.out.println(ind2);
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
                        updateScores();
                    }
                    if(ind < 0){
                        statusLabel.setText("Ganó " + jugadores.get((ind * -1) - 1) + "!");
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