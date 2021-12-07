/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ipn.mx.modelo.dto;

import com.ipn.mx.modelo.entidades.Usuario;
import lombok.Data;

/**
 *
 * @author JMTN
 */
@Data
public class UsuarioDTO {
    private Usuario entidad;
    public UsuarioDTO(){
        entidad = new Usuario();
    }
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Id alumno: ").append(getEntidad().getIdAlumno()).append("\n");
        sb.append("Nombre: ").append(getEntidad().getNombre()).append("\n");
        sb.append("Apellido paterno: ").append(getEntidad().getPaterno()).append("\n");
        sb.append("Apellido materno: ").append(getEntidad().getMaterno()).append("\n");
        sb.append("Email: ").append(getEntidad().getEmail()).append("\n");
        sb.append("Nombre de usuario: ").append(getEntidad().getNombreUsuario()).append("\n");
        sb.append("Clave de usuario: ").append(getEntidad().getClaveUsuario()).append("\n");
        sb.append("Tipo de usuario: ").append(getEntidad().getTipoUsuario()).append("\n");
        return sb.toString();
    }
}
