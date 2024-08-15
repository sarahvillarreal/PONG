package clases;

import java.applet.AudioClip;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import javax.swing.Timer;

import obtenerRecursos.Audio;


public class Pelota {
   private int x;
   private int y;
   private final int ANCHO=15, ALTO=15;
   private int dx=1, dy=1;
   private float velocidad;
   private final float velocidadInicial = 1; // Velocidad inicial
   
   
   private Integer puntaje1=0, puntaje2=0;
   public static boolean finJuego = false;
   private Timer velocidadTimer;
   private Timer pausaTimer;
   private boolean enPausa = false;
   
   Audio audio = new Audio();
   AudioClip rebote_1=audio.getAudio("/recursos/rebote_pelota1.wav");
   AudioClip rebote_2=audio.getAudio("/recursos/rebote_pelota2.wav");
   AudioClip falta=audio.getAudio("/recursos/falta.wav");
   
   
   public Pelota(int x, int y) {
       this.x = x;
       this.y = y;
       this.velocidad = velocidadInicial;

       // Configurar el Timer para incrementar la velocidad cada 10 segundos
       velocidadTimer = new Timer(10000, new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               incrementarVelocidad();
           }
       });
       velocidadTimer.start();
   }

   public Ellipse2D getPelota() {
       return new Ellipse2D.Double(x, y, ANCHO, ALTO);
   }
    
    public void mover(Rectangle limites, boolean colisionR1, boolean colisionR2){
    	
    	if (enPausa==true) {
    		if(Temporizador.getTiempoRestante() < (Temporizador.getTiempoDelPartido()/1000)/2){
    			
    			terminarPausa();
    			resetearPosicion(limites);
    		}
    		
    		else{
    			
    			return;
    		}
        }
    	
        x+=dx;
        y+=dy;
        
        if (colisionR1){ // si se detecta una colisión con la raqueta 1, entonces la pelota cambia de dirección
          dx =-dx;  
          x=25; // Esto corrige el error de que no rebota automáticamente
        }
        
        if (colisionR2){ // lo mismo para la raqueta 2
          dx =-dx;  
          x=740; // Esto corrige el error de que no rebota automáticamente 
        }
        
        
        
        
        // Esto hace que si supera el limite max en x, cambie de direccion a la contraria
        if (x < limites.getMinX()) {
        	
        	if(Temporizador.getTiempoRestante() > (Temporizador.getTiempoDelPartido()/1000)/2){
        		
        		puntaje2++; //el puntaje del jugador2 aumenta en uno
        	}

        	if(Temporizador.getTiempoRestante() < (Temporizador.getTiempoDelPartido()/1000)/2){
        		
        		puntaje1++; //el puntaje del jugador2 aumenta en uno
        	}
           
        	resetearPosicion(limites);
            falta.play();
        }

        if (x + ANCHO >= limites.getMaxX()) {
            puntaje1++; //el puntaje del jugador1 aumenta en uno
            
            resetearPosicion(limites);
            audio.getAudio("/recursos/falta.wav").play();
        }

        if (y < limites.getMinY()) {

            y = (int) limites.getMinY();

            dy = -dy;
            rebote_2.play();
        }

        if (y + ALTO >= limites.getMaxY()) {

            y = (int) (limites.getMaxY() - ALTO);

            dy = -dy;
            rebote_2.play();
        }
        
        if(Temporizador.getTiempoRestante() == (Temporizador.getTiempoDelPartido()/1000)/2) {
        	
        	iniciarPausa();
        }
      
    }
    
    private void iniciarPausa() {
        enPausa = true; // Activar el estado de pausa
        
    }

    private void terminarPausa() {
        enPausa = false; // Desactivar el estado de pausa
    }
    
    private void resetearPosicion(Rectangle limites) {
        x = (int) limites.getCenterX() - (ANCHO / 2);
        y = (int) limites.getCenterY() - (ALTO / 2);
        dx = -dx; // Revertir la direccion horizontal

        // Reiniciar la velocidad a su valor inicial
        velocidad = velocidadInicial;
        
        reiniciarTimer();
    }

    private void incrementarVelocidad() {
        velocidad += 0.5; // Incrementar la velocidad en 0.5
        System.out.println("Nueva velocidad: " + velocidad); // Para comprobar. Borrar
    }
    
    private void reiniciarTimer() {
        velocidadTimer.stop(); // Detener el Timer
        velocidadTimer.setDelay(10000); // Volver a configurar el intervalo (10 segundos)
        velocidadTimer.start(); // Reiniciar el Timer
    }
    
    public int getScore1(){
        return puntaje1;
    }
    
     public int getScore2(){
        return puntaje2;
    }

}