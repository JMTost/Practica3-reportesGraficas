/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ipn.mx.modelo.dto;

import com.ipn.mx.modelo.entidades.Producto;
import java.io.Serializable;
import lombok.Data;

/**
 *
 * @author JMTN
 */
@Data
public class ProductoDTO implements Serializable{
    private Producto entidad;
    public ProductoDTO(){
        entidad = new Producto();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Clave: ").append(getEntidad().getIdProducto()).append("\n");
        sb.append("Nombre: ").append(getEntidad().getNombreProducto()).append("\n");
        sb.append("Descripcion: ").append(getEntidad().getDescripcionProducto()).append("\n");
        sb.append("Existencia: ").append(getEntidad().getExistencia()).append("\n");
        sb.append("Clave categoria: ").append(getEntidad().getClaveCategoria()).append("\n");
        return sb.toString();
    }
    
}
