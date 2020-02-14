/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor;

import java.util.ArrayList;
import java.util.Observable;

/**
 *
 * @author alumno
 */
public class ListadoClientes extends Observable {

    //arraylist donde guardaremos
    //los nombres de los usuarios
    //conectados
    private ArrayList<String> listaNombres;

    public ListadoClientes() {
        listaNombres = new ArrayList<String>();
    }

    //metodo que llama un hilo cuando se conecta por primera
    //vez al servidor
    public synchronized void anadirUsusario(String nombre) {
        //añadimos el nombre a la lista
        listaNombres.add(nombre);

        this.setChanged();
        //le enviamos la lista a todos los observadores (todos los usuarios
        //conectados)
        notifyObservers(listaNombres);

    }

    //metodo similar al anterior pero esta vez eliminando uno de la lista,
    //es decir, lo llamamos cuando se desconecta un cliente
    public synchronized void borrarUsuario(String nombre) {
        listaNombres.remove(nombre);

        this.setChanged();

        notifyObservers(listaNombres);

    }

}
