package clases;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Temporizador {

    private Timer timer;
    private static int tiempoRestante;
    private TemporizadorListener listener;
    private boolean enPausa = false;
    private int tiempoPausa = 6000; // 6 segundos en milisegundos
    private int tiempoPausado = 0;
    private static int tiempoTranscurrido = 0;
    static int tiempoDelPartido = 120000; // Tiempo total en milisegundos

    public interface TemporizadorListener {
        void onTiempoActualizado(int tiempoRestante);
        void onTiempoFinalizado();
    }

    public Temporizador(TemporizadorListener listener) {
        
        
        this.tiempoRestante = tiempoDelPartido / 1000; // Tiempo en segundos
        this.listener = listener;

        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (enPausa == false) {
                    tiempoTranscurrido++;
                    tiempoRestante--;

                    // Si pasó la mitad del tiempo del partido (PT), iniciar la pausa
                    if (tiempoTranscurrido == (tiempoDelPartido/1000)/2) {
                        enPausa = true;
                        tiempoPausado = 0; // Reiniciar el contador de pausa
                        timer.setDelay(tiempoPausa); // Establecer el tiempo de pausa
                    } 

                    // Actualizar al oyente si existe
                    if (listener != null) {
                        listener.onTiempoActualizado(tiempoRestante);
                    }

                    // Si el tiempo restante es 0 o menor, detener el temporizador
                    if (tiempoRestante <= 0) {
                        timer.stop();
                        if (listener != null) {
                            listener.onTiempoFinalizado();
                        }
                    }
                } else {
                    // Aumentar el contador de tiempo pausado
                    tiempoPausado += timer.getDelay();
                    if(tiempoPausado==tiempoPausa)
                    {
                    	enPausa = false;
                    	timer.setDelay(1000);
                    }
                }
            }
        });
    }

    public void start() {
        timer.start();
    }

    public void detener() {
        timer.stop();
    }

    public static int getTiempoRestante() {
        return tiempoRestante;
    }
    
    public static int getTiempoTranscurrido() {
        return tiempoTranscurrido;
    }
    
    public static int getTiempoDelPartido() {
        return tiempoDelPartido;
    }
}
