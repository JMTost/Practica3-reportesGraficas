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
public class Categoria implements Serializable{
    private int idCategoria;
    private String nombreCategoria;
    private String descripcionCategoria;

    

//    @Override
//    public String toString() {
//        StringBuilder sb = new StringBuilder();
//        sb.append("idCategoria= ").append(getIdCategoria()).append("\n");
//        sb.append("nombreCategoria= ").append(getNombreCategoria()).append("\n");
//        sb.append("descripcionCategoria= ").append(getDescripcionCategoria()).append("\n");
//        return sb.toString();
//    }
    
    
}
