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
public class Consumidor extends Thread{
    
    private Almacen almBat;
    private Almacen almPan;
    private Almacen almCab;
    
    private int tiempoCons;
    private int[] undsFinales;
    private int durDia;
    
    private boolean despedido;
    
    private Semaphore sBatP;
    private Semaphore sBatC;
    private Semaphore sBatEx;
    private Semaphore sPanP;
    private Semaphore sPanC;
    private Semaphore sPanEx;
    private Semaphore sCabP;
    private Semaphore sCabC;
    private Semaphore sCabEx;

    public Consumidor(Almacen almBat, Almacen almPan, Almacen almCab, int[] undsFinales, int durDia, boolean despedido, Semaphore sBatP, Semaphore sBatC, Semaphore sBatEx, Semaphore sPanP, Semaphore sPanC, Semaphore sPanEx, Semaphore sCabP, Semaphore sCabC, Semaphore sCabEx) {
        this.almBat = almBat;
        this.almPan = almPan;
        this.almCab = almCab;
        this.undsFinales = undsFinales;
        this.durDia = durDia;
        this.despedido = despedido;
        this.sBatP = sBatP;
        this.sBatC = sBatC;
        this.sBatEx = sBatEx;
        this.sPanP = sPanP;
        this.sPanC = sPanC;
        this.sPanEx = sPanEx;
        this.sCabP = sCabP;
        this.sCabC = sCabC;
        this.sCabEx = sCabEx;
    }
    
    
    
    public void consumirComponente(Almacen almCom){
        almCom.setVec(almCom.getApunC(), 0);
        almCom.setApunC((almCom.getApunC() + 1) % almCom.getTam());
    }
    
//    public void consumirBateria(int apunC){
//        almBat.setVec(apunC, 0);
//        almBat.setApunC((apunC+1)%almBat.getTam());
//    }
//    
//    public void consumirPantalla(int apunC){
//        almPan.setVec(apunC, 0);
//        almPan.setApunC((apunC+1)%almPan.getTam());
//    }
//    
//    public void consumirCable(int apunC){
//        almCab.setVec(apunC, 0);
//        almCab.setApunC((apunC+1)%almCab.getTam());
//    }
    
    public void mostrarAlmacenComponente(String componenteStr, Almacen almCom){
        System.out.println("CONSUMIENDO "+ componenteStr);
        for (int i = 0; i < almCom.getTam(); i++) {
            System.out.print("["+ almCom.vec[i] +"]");
        }
        System.out.println("");
    }
    
    public void despedir(){
        this.despedido = true;
    }
    
    @Override
    public void run(){
        while(!despedido){
            try {
                sBatC.acquire();
                sPanC.acquire();
                sCabC.acquire(2);
                
                sBatEx.acquire();
                consumirComponente(almBat);
                sBatEx.release();
                
                mostrarAlmacenComponente("BATERIA", almBat);
                
                sPanEx.acquire();
                consumirComponente(almPan);
                sPanEx.release();
                
                mostrarAlmacenComponente("PANTALLA", almPan);
                
                sCabEx.acquire();
                consumirComponente(almCab);
                consumirComponente(almCab);
                sCabEx.release();
                
                mostrarAlmacenComponente("CABLES", almCab);
                
                Thread.sleep(durDia * 2 * 1000);
                
                sBatP.release();
                sPanP.release();
                sCabP.release(2);
                
                undsFinales[0]++;
                System.out.println("FINALES CONSUMIDOR: " + undsFinales[0]);
                                
            } catch (InterruptedException ex) {
                Logger.getLogger(Consumidor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
