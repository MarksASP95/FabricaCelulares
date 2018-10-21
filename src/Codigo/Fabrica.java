/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Codigo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

/**
 *
 * @author Marco
 */
public class Fabrica {
    
    Almacen almBat;
    Almacen almPan;
    Almacen almCab;
    Almacen almTerminados;
    
    ArrayList<Productor> prodsBat;
    ArrayList<Productor> prodsPan;
    ArrayList<Productor> prodsCab;
    ArrayList<Consumidor> ensambladores;
    
    private Semaphore sBatP;
    private Semaphore sBatC;
    private Semaphore sBatEx;
    private Semaphore sPanP;
    private Semaphore sPanC;
    private Semaphore sPanEx;
    private Semaphore sCabP;
    private Semaphore sCabC;
    private Semaphore sCabEx;
    private Semaphore sContador;
    
    private int dia;
    private int diasEntreDespacho;
    
    private int almBatMax;  // capacidad maxima del almacen de componente
    private int almPanMax;
    private int almCabMax;
    
    private int prodBatIni;  // cantidad inicial de productores
    private int prodPanIni;
    private int prodCabIni;
    
    private int prodBatMax;  // cantidad maxima de productores
    private int prodPanMax;
    private int prodCabMax;
    
    private int ensIni; // cantidad inicial de ensambladores
    private int ensMax; //cantidad maxima de ensambladores
    private int[] undsFinales;
    private int [] contadorDiasDespacho;
    
    private int durBat;
    private int durPan;
    private int durCab;
    
    Gerente gerente;
    
    Cronometrador cronometrador;
    
    ArrayList<Integer> datos = new ArrayList<Integer>();
    Scanner sc;
    
    public Fabrica() throws FileNotFoundException, IOException{
        
        try (BufferedReader br = new BufferedReader(new FileReader("archivo.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                sc = new Scanner(line);
                while(sc.hasNextInt() == false){
                    sc.next();
                }
                datos.add(sc.nextInt());
            }
        }
        
        dia = datos.get(0);
        diasEntreDespacho = datos.get(1);
        almBatMax = datos.get(2);
        almPanMax = datos.get(3);
        almCabMax = datos.get(4);
        prodBatIni = datos.get(5);
        prodPanIni = datos.get(6);
        prodCabIni = datos.get(7);
        prodBatMax = datos.get(8);
        prodPanMax = datos.get(9);
        prodCabMax = datos.get(10);
        ensIni = datos.get(11);
        ensMax = datos.get(12);
               
        almBat = new Almacen(almBatMax);
        almPan = new Almacen(almPanMax);
        almCab = new Almacen(almCabMax);
        
        prodsBat = new ArrayList<Productor>();
        prodsPan = new ArrayList<Productor>();
        prodsCab = new ArrayList<Productor>();

        ensambladores = new ArrayList<Consumidor>();
        
        sBatP = new Semaphore(almBatMax);
        sBatC = new Semaphore(0);
        sBatEx = new Semaphore(1);

        sPanP = new Semaphore(almPanMax);
        sPanC = new Semaphore(0);
        sPanEx = new Semaphore(1);
        
        sCabP = new Semaphore(almCabMax);
        sCabC = new Semaphore(0);
        sCabEx = new Semaphore(1);
        
        sContador = new Semaphore(1);
        
        contadorDiasDespacho = new int[1];
        contadorDiasDespacho[0] = diasEntreDespacho;
       
        undsFinales = new int[1];
        undsFinales[0] = 0;
        
        durBat = dia * 1000;
        durPan = dia * 2 * 1000;
        durCab = dia * 1000;
       
//        cantProdEx=cantInicialEx;
//        cantProdCa=cantInicialCa;
//        cantProdCu=cantInicialCu;
//        cantEns=cantInicialEns;

        Iniciar();
                
    }
    
    public void admProductorBat(Boolean contratar){
        if(!contratar){
            if(prodsBat.size() > 0){
                prodsBat.get(prodsBat.size() - 1).despedir();
                prodsBat.remove(prodsBat.size() - 1);
            }
            else{
                System.out.println("NO SE PUEDE DESPEDIR MAS PRODUCTORES DE BATERIAS");
            }
        }
        else if(prodsBat.size() < prodBatMax){
            Productor p = new Productor(almBat, durBat, false, sBatP, sBatC, sBatEx, "BATERIA");
            prodsBat.add(p);
            p.start();
            System.out.println("CONTRATADO: PRODUCTOR DE BATERIAS");
        }
        else{
            System.out.println("NO SE PUEDE CONTRATAR MAS PRODUCTORES DE BATERIAS");
        }
    }
    
    public void admProductorPan(Boolean contratar){
        if(!contratar){
            if(prodsPan.size() > 0){
                prodsPan.get(prodsPan.size() - 1).despedir();
                prodsPan.remove(prodsPan.size() - 1);
            }
            else{
                System.out.println("NO SE PUEDE DESPEDIR MAS PRODUCTORES DE PANTALLAS");
            }
        }
        else if(prodsPan.size() < prodPanMax){
            Productor p = new Productor(almPan, durPan, false, sPanP, sPanC, sPanEx, "PANTALLA");
            prodsBat.add(p);
            p.start();
            System.out.println("CONTRATADO: PRODUCTOR DE PANTALLAS");
        }
        else{
            System.out.println("NO SE PUEDE CONTRATAR MAS PRODUCTORES DE PANTALLAS");
        }
    }
        
    public void admProductorCab(Boolean contratar){
        if(!contratar){
            if(prodsCab.size() > 0){
                prodsCab.get(prodsCab.size() - 1).despedir();
                prodsCab.remove(prodsCab.size() - 1);
            }
            System.out.println("NO SE PUEDE DESPEDIR MAS PRODUCTORES DE CABLES");
        }
        else if(prodsCab.size() < prodCabMax){
            Productor p = new Productor(almCab, durCab, false, sCabP, sCabC, sCabEx, "CABLE");
            prodsCab.add(p);
            p.start();
            System.out.println("CONTRATADO: PRODUCTOR DE CABLES");
        }
        else{
            System.out.println("NO SE PUEDE CONTRATAR MAS PRODUCTORES DE CABLES");
        }
    }
    
    public void admEnsamblador(Boolean contratar){
        if(!contratar){
            if(ensambladores.size() > 0){
                ensambladores.get(ensambladores.size() - 1).despedir();
                ensambladores.remove(ensambladores.size() - 1);
            }
            else{
                System.out.println("NO SE PUEDE DESPEDIR MAS ENSAMBLADORES");
            }
        }
        else if(ensambladores.size() < ensMax){
            Consumidor e = new Consumidor(almBat, almPan, almCab, undsFinales, dia, false, sBatP, sBatC, sBatEx, sPanP, sPanC, sPanEx, sCabP, sCabC, sCabEx);
            ensambladores.add(e);
            e.start();
            System.out.println("CONTRATADO: ENSAMBLADOR");
        }
        else{
            System.out.println("NO SE PUEDE CONTRATAR MAS ENSAMBLADORES");
        }
    }
    
    public void Iniciar(){

        for(int i=0;i<prodBatIni;i++){
            Productor p = new Productor(almBat, durBat, false, sBatP, sBatC, sBatEx, "BATERIA");
            prodsBat.add(p);
            p.start();           
        }

        for(int i=0;i<prodPanIni;i++){
            Productor p = new Productor(almPan, durPan, false, sPanP, sPanC, sPanEx, "PANTALLA");
            prodsPan.add(p);
            p.start();           
        }


        for(int i=0;i<prodCabIni;i++){
            Productor p = new Productor(almCab, durCab, false, sCabP, sCabC, sCabEx, "CABLE");
            prodsCab.add(p);
            p.start();           
        }


        for(int i=0;i<ensIni;i++){
            Consumidor e = new Consumidor(almBat, almPan, almCab, undsFinales, dia, false, sBatP, sBatC, sBatEx, sPanP, sPanC, sPanEx, sCabP, sCabC, sCabEx);
            ensambladores.add(e);
            e.start();           
        }

        Gerente g = new Gerente(sContador, contadorDiasDespacho, dia, undsFinales);
        Cronometrador c = new Cronometrador(sContador, contadorDiasDespacho, dia, diasEntreDespacho);
        g.start();
        c.start();

    }
    
}
