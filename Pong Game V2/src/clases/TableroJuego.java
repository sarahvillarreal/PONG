package clases;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;



public class TableroJuego extends JPanel { // Dibujo de las raquetas y la pelota
    
    Pelota pelota = new Pelota(392, 240);
    Raqueta r1 = new Raqueta(10,200);
    Raqueta r2 = new Raqueta(786-10-Raqueta.ANCHO,200);
    
    
    public TableroJuego(){
        
        setBackground(Color.black);       
    }
    
    
    @Override
    public void paintComponent(Graphics g){ // este método es el que nos va a dibujar las dos raquetas y la pelota
      
        super.paintComponent(g); // esto ejecuta el método de la clase padre, como si fuera un activador o un "ON"
        Graphics2D g2 = (Graphics2D)g; // Graphics2D permite usar más funciones de aspecto que Graphics solo
        g2.setColor(Color.white);
       

        dibujarPuntaje(g2);       
        dibujarTiempoRestante(g2);
          
        dibujar(g2);
        actualizar();
        
    }
    
   
    public void dibujar(Graphics2D g){
       
        //Dibuja la linea del medio
        Line2D.Double linea = new Line2D.Double(getBounds().getCenterX(), 0, getBounds().getCenterX(), getBounds().getMaxY());
        g.draw(linea); //imprime la linea del medio
        
        
        
        g.fill(pelota.getPelota()); // get Pelota nos retorna un objeto en 2D con las dimensiones que les dimos
        g.fill (r1.getRaqueta());
        g.fill (r2.getRaqueta());
        
       // System.out.println("x"+getBounds().getMaxX()); // esto nos sirve para ver las dimensiones reales del panel para establecer los limites
       // System.out.println("x"+getBounds().getMaxY());
    } 
    
    public void actualizar(){
        
        
        pelota.mover(getBounds(), colision(r1.getRaqueta()),colision(r2.getRaqueta()) );
        r1.moverR1(getBounds());
        r2.moverR2(getBounds());
    }
    
    public void iterarJuego(){
        while(true){
            repaint();
           
            try {
                Thread.sleep(6);
            } catch (InterruptedException ex) {
                Logger.getLogger(TableroJuego.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
    private boolean colision (Rectangle2D r){// método para poder detectar la colisión
        return pelota.getPelota().intersects(r); // intersects detecta si la pelota se cruza con la raqueta
        // este metodo intersect detecta si dos objetos de tipo rectagulo se estan cruzando o no, si lo detecta, devuelve un true
    }
    
    
    private void dibujarTiempoRestante(Graphics2D g) {
        Font tiempoFont = new Font("Arial", Font.BOLD, 20);
        g.setFont(tiempoFont);
        g.drawString("Tiempo restante: " + Temporizador.getTiempoRestante() + " segundos", 10, getHeight() - 10);
    }
    
    
    private void dibujarPuntaje (Graphics2D g){
        
        Graphics2D g1=g, g2=g;
        
        Font puntaje = new Font("Arial", Font.BOLD, 30);
        g.setFont(puntaje);
        
        g1.drawString(Integer.toString(pelota.getScore1()), (float) getBounds().getCenterX() - 45, 30);
        g2.drawString(Integer.toString(pelota.getScore2()), (float) getBounds().getCenterX() + 25, 30);
        if ((pelota.getScore1() >= 7 && (pelota.getScore1() - pelota.getScore2()) >= 2) || (Temporizador.getTiempoRestante() <= 0 && pelota.getScore1() > pelota.getScore2())) {
        	String texto = "GANÓ EL JUGADOR 1";
        	dibujarTextoCentrado(g, texto);
            Pelota.finJuego = true;
        }
        if ((pelota.getScore2() >= 7 && (pelota.getScore2() - pelota.getScore1()) >= 2) || (Temporizador.getTiempoRestante() <= 0 && pelota.getScore1() < pelota.getScore2())) {
            String texto = "GANÓ EL JUGADOR 2";
        	dibujarTextoCentrado(g, texto);
            Pelota.finJuego = true;
        }
        if (Temporizador.getTiempoRestante() <= 0 && pelota.getScore1() == pelota.getScore2()) {
        	String texto = "EMPATE";
        	dibujarTextoCentrado(g, texto);
            Pelota.finJuego = true;
        }
        
    }

    
    
    
    private void dibujarTextoCentrado(Graphics2D g, String texto) {
    	// Obtener el FontMetrics para medir el ancho del texto
        FontMetrics metrics = g.getFontMetrics(g.getFont());
        
        // Calcular la posición x para centrar el texto
        int x = (int) (getBounds().getCenterX() - metrics.stringWidth(texto) / 2);
        
        // Calcular la posición y para centrar verticalmente
        int y = (int) (getBounds().getCenterY() - metrics.getHeight() / 2);
        g.drawString(texto, x, y);
    }

    
}