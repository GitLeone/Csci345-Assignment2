import java.awt.*;
import javax.swing.*;
import javax.swing.ImageIcon;
import javax.imageio.ImageIO;
import java.awt.event.*;
import javax.swing.JOptionPane;

class boardMouseListener implements MouseListener{
    // Code for the different button clicks
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == bAct){
        playerlabel.setVisible(true);
        System.out.println("Acting is Selected\n");
        }
        else if (e.getSource()== bRehearse){
            System.out.println("Rehearse is Selected\n");
        }
        else if (e.getSource()== bMove){
            System.out.println("Move is Selected\n");
        }
    }

    public void mousePressed(MouseEvent e) {
        
    }
    public void mouseReleased(MouseEvent e) {
    
    }
    public void mouseEntered(MouseEvent e) {
    
    }
    public void mouseExited(MouseEvent e) {
    
    }
}