/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Codigo;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marco
 */
public class Gerente extends Thread{
    
    Semaphore sContador;
    int tiempoOcio;
    int durDia;
    int[] undsFinales;
    int[] contador;
    
    public Gerente(Semaphore sContador, int[] contador, int durDia, int[] undsFinales){
        
        this.contador = contador;
        this.undsFinales = undsFinales;
        this.durDia = durDia;
        this.sContador = sContador;
        tiempoOcio = (new Random().nextInt(8 * durDia * 1000) + 8) / 24;   // (((16 - 8) * durDia * 1000) + 8)  / 24
        
        
    }
    
    @Override
    public void run(){
        while(true){
            
            try {
                sContador.acquire();
                System.out.println("FINALEA GERENTE: " + undsFinales[0]);
                if(contador[0] == 0){
                    undsFinales[0] = 0;
                    sContador.release();
                    System.out.println("-------------DESPACHANDO-------------");
                    Thread.sleep(durDia * 1000);
                }
                else{
                    sContador.release();
                    Thread.sleep(tiempoOcio);
                }
            } 
            catch (InterruptedException ex) {
                Logger.getLogger(Gerente.class.getName()).log(Level.SEVERE, null, ex);
            }  
        }    
    }
}
