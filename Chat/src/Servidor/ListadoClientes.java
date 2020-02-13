/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor;

import Modelo.Nombres;
import java.util.ArrayList;
import java.util.Observable;

/**
 *
 * @author alumno
 */
public class ListadoClientes extends Observable{
    
    private ArrayList<String> listaNombres ;
    
    private Nombres n;
    
    public ListadoClientes()
    {
        listaNombres = new ArrayList<String>();
    }
    
    public synchronized void anadirUsusario(String nombre)
    {
        listaNombres.add(nombre);
        
        //n = new Nombres(listaNombres);
        
        this.setChanged();
        
        notifyObservers(listaNombres);
    }
    
    public synchronized void borrarUsuario(String nombre)
    {
        listaNombres.remove(nombre);
        
        //n = new Nombres(listaNombres);
        
        this.setChanged();
        
        notifyObservers(listaNombres);
    }
    
}
