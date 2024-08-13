package clases;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class EventoTeclado extends KeyAdapter { // Esta clase nos permite trabajar con eventos de teclado y detecta si se presionó una tecla
    
    static boolean w, s, up, down;
    
    
    @Override
    public void keyPressed(KeyEvent e){ // Esta tecla detecta cuando la tecla está siendo presionada, para mover 
        
        int id = e.getKeyCode();
        
        if(id == KeyEvent.VK_W){
            w = true;
        }
        
        if(id == KeyEvent.VK_S){
            s = true;
        }
        
        if(id == KeyEvent.VK_UP){
            up = true;
        }
        
        if(id == KeyEvent.VK_DOWN){
            down = true;
        }
    }
    
    @Override
    public void keyReleased(KeyEvent e){ // Esto detecta cuando la tecla ya no es presionada, para parar de mover
        
        int id = e.getKeyCode();
        
        if(id == KeyEvent.VK_W){
            w = false;
        }
        
        if(id == KeyEvent.VK_S){
            s = false;
        }
        
        if(id == KeyEvent.VK_UP){
            up = false;
        }
        
        if(id == KeyEvent.VK_DOWN){
            down = false;
        }
    }
    
}