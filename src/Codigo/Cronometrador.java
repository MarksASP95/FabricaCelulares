/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Codigo;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marco
 */
public class Cronometrador extends Thread{
    
    Semaphore sContar;
    int[] contador;
    int dias;
    int durDia;
    int diasDespacho;
    double restoDia;
    double tiempoReducir;

    public Cronometrador(Semaphore sContar, int contador[], int durDia, int diasDespacho) {
        this.sContar = sContar;
        this.contador = contador;
        this.durDia = durDia;
        this.diasDespacho = diasDespacho;
        this.dias = 0;
        tiempoReducir = (1.5*durDia)*1000 / 24;
        restoDia = (durDia*1000) - tiempoReducir;
    }
    
    @Override
    public void run(){
        
        while(true){
            try {
                sContar.acquire();
                System.out.println("DISMINUYENDO CONTADOR");
                Thread.sleep((int) tiempoReducir);
                contador[0]--;
                
                if(contador[0] < 0){
                    contador[0] = diasDespacho;
                }
                sContar.release();
                System.out.println("DURMIENDO");
                Thread.sleep((int) restoDia);
                dias++;
            
            } 
            catch (InterruptedException ex) {
                Logger.getLogger(Cronometrador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        
    }
    
    
}
