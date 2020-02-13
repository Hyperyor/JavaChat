/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author alumno
 */
public class Nombres implements Serializable{
    
    ArrayList<String> nombres;
    
    public Nombres(ArrayList<String> n)
    {
        nombres = n;
    }

    public ArrayList<String> getNombres() {
        return nombres;
    }

    public void setNombres(ArrayList<String> nombres) {
        this.nombres = nombres;
    }
    
    
    
}
