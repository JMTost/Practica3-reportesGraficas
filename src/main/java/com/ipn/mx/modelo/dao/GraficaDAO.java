/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ipn.mx.modelo.dao;

import com.ipn.mx.modelo.dto.GraficaDTO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author JMTN
 */
public class GraficaDAO {
    private Connection conexion;
    private static final String SQL_GRAFICAR = "select nombreCategoria, count(*) as cantidad from categoria inner join Producto on categoria.idcategoria = producto.clavecategoria group by categoria.nombrecategoria;";
    private static final String SQL_GRAFICAUSER = "select tipousuario, count(*) as cantidad from usuario group by tipousuario;";
     private void conectar() {
        String user = "postgres";
        String pwd = "123";
        /*Properties props = new Properties();
        props.setProperty("user", "postgres");
        props.setProperty("password", "123");*/

        String url = "jdbc:postgresql://localhost:5432/Base3CM13";
        String pgDriver = "org.postgresql.Driver";
        try {
            //registra el driver de java para el manejador de base de datos
            Class.forName(pgDriver);
            conexion = DriverManager.getConnection(url, user, pwd);
            //debería de ser dos excepciones, class not found exception y sql exception, por no colocar de forma correcta los datos para la conexion a la base 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
     public List graficarProductosPorCategoria() throws SQLException{
         conectar();
         PreparedStatement ps = null;
         ResultSet rs = null;
         List lista = new ArrayList();
         try{
             ps = conexion.prepareStatement(SQL_GRAFICAR);
             rs = ps.executeQuery();
             while(rs.next()){
                 GraficaDTO dto = new GraficaDTO();
                 dto.setNombreCategoria(rs.getString("nombreCategoria"));
                 dto.setCantidad(rs.getInt("cantidad"));
                 lista.add(dto);
             }
         }finally{
             if(rs != null){
                 rs.close();
             }
             if(ps != null){
                 ps.close();
             }
             if(conexion != null){
                 conexion.close();
             }
         }
         return lista;
     }
     public List graficarUserPorTipoUsuario() throws SQLException{
         conectar();
         PreparedStatement ps = null;
         ResultSet rs = null;
         List lista = new ArrayList();
         try{
             ps = conexion.prepareStatement(SQL_GRAFICAUSER);
             rs = ps.executeQuery();
             while(rs.next()){
                 GraficaDTO dto = new GraficaDTO();
                 dto.setNombreCategoria(rs.getString("tipousuario"));
                 dto.setCantidad(rs.getInt("cantidad"));
                 lista.add(dto);
             }
         } catch (SQLException ex) {
            Logger.getLogger(GraficaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
             if(rs != null){
                 rs.close();
             }
             if(ps != null){
                 ps.close();
             }
             if(conexion != null){
                 conexion.close();
             }
         }
         return lista;
     }
     public static void main(String[] args) {
        GraficaDAO dao = new GraficaDAO();
        try {
            //System.out.println(dao.graficarUserPorTipoUsuario());
            System.out.println(dao.graficarProductosPorCategoria());
        } catch (SQLException ex) {
            Logger.getLogger(GraficaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     /*
     OBTENER EL NUMERO DE PRODUCTOS POR CATEGORIA
     select nombreCategoria count(*) from categorias inner join Producto on categoria.idcategoria = producto.clavecategoria group by categoira.nombrecategoria;
     */
     
}
