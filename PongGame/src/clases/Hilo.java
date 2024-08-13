package clases;

import java.util.logging.Level;
import java.util.logging.Logger;


public class Hilo extends Thread { // Esta clase es la responsable de actualizar constantemente el movimiento de los elementos 
// (la clase Thread se usa para aplicaciones multitarea)
    
    private final TableroJuego lamina;
   
    public Hilo (TableroJuego lamina){
        
        this.lamina = lamina;
    }
    
    
    @Override
    public void run (){
        
        // Condición de que cuando en Pelota.finJuego == false, entonces se termine
        while(Pelota.finJuego!=true ){
            lamina.repaint();
            try {
                Thread.sleep(3);  // Esto controla la velocidad de la pelota, a mayor nro, más lento
            } catch (InterruptedException ex) {
                System.out.println("error in graphics engine: " + ex.getMessage());
            }
            
        }
        
        
    }
    
    
}