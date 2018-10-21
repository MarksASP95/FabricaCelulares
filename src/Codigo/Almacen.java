/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Codigo;

/**
 *
 * @author Marco
 */
public class Almacen {
    
    int vec[];
    int tam;
    int apunP;
    int apunC;
    int cantidadPiezas;
    
    public Almacen(int tam){
        this.tam = tam;
        this.vec = new int[this.tam];
    }
        
    public int getTam(){
        return tam;
    }
    
    public int[] getVec(){
        return vec;
    }
    
    public void setVec(int i, int val){
        vec[i] = val;
    }
    
    public int getApunP(){
        return apunP;
    }
    
    public int getApunC(){
        return apunC;
    }
    
    public void setApunP(int x){
        apunP = x;
    }
    
    public void setApunC(int x){
        apunC = x;
    }
    
}
