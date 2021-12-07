/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ipn.mx.modelo.entidades;

import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 *
 * @author JMTN
 */
@Data
@NoArgsConstructor
public class Producto implements Serializable{
    private int idProducto;
    private String nombreProducto;
    private String descripcionProducto;
    private float precio;
    private int existencia;
    private int stockMinimo;
    private int claveCategoria;
   
    
        
}
