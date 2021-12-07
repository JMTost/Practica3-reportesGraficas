/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ipn.mx.modelo.dto;

import java.io.Serializable;
import com.ipn.mx.modelo.entidades.Categoria;
import lombok.Data;
/**
 *
 * @author JMTN
 */
@Data
        //Data Transfer Object 
public class CategoriaDTO implements Serializable{
    private Categoria entidad;
    public CategoriaDTO(){
        entidad = new Categoria();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("clave: ").append(getEntidad().getIdCategoria()).append("\n");
        sb.append("nombre: ").append(getEntidad().getNombreCategoria()).append("\n");
        sb.append("descripcion: ").append(getEntidad().getDescripcionCategoria()).append("\n");
        return sb.toString();
    }
    
}
