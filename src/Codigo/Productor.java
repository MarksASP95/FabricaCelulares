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
public class Productor extends Thread{
    
    private Almacen alm;
    private int tiempoProd;
    private boolean despedido;
    private Semaphore sP;
    private Semaphore sC;
    private Semaphore sE;
    
    private String nombreComponente;

    public Productor(Almacen alm, int tiempoProd, boolean despedido, Semaphore sP, Semaphore sC, Semaphore sE, String nombreComponente) {
        this.alm = alm;
        this.tiempoProd = tiempoProd;
        this.despedido = despedido;
        this.sP = sP;
        this.sC = sC;
        this.sE = sE;
        this.nombreComponente = nombreComponente;
        
        System.out.println(tiempoProd);
    }
    
    public void despedir(){
        this.despedido = true;
    }

    public void MostrarAlmacen(){

        System.out.println("PRODUCCION DE "+ nombreComponente);
        for (int i = 0; i < alm.getTam(); i++) {
            System.out.print("["+alm.getVec()[i]+"]");

        }
        System.out.println("");
        System.out.println("");
        
        
    }
      
    @Override
    public void run(){
        
        while(!despedido){
            try {
                sP.acquire();
                sE.acquire();
                alm.setVec(alm.apunP, 1);
                alm.apunP=(alm.apunP+1)%alm.getTam();
                sC.release();
                
                sE.release();
                Thread.sleep(tiempoProd);
                alm.cantidadPiezas++;
            } 
            catch (InterruptedException ex) {
                Logger.getLogger(Productor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
}
