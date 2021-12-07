/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ipn.mx.modelo.dao;

import com.ipn.mx.modelo.dto.ProductoDTO;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author JMTN
 */
public class ProductoDAO {

    private static final String SQL_INSERT = "call spInsertarProducto(?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE = "call spActualizarProducto (?, ?, ?, ?, ?, ?, ?);";
    private static final String SQL_DELETE = "call spEliminarProducto(?)";
    private static final String SQL_READ = "select * from buscaUnProducto(?)";
    private static final String SQL_READ_ALL = "select * from seleccionaProductos()";

    private Connection conexion;

   public Connection conectar() {
        /*InitialContext initContext;
        try {
            initContext = new InitialContext();
            DataSource ds = (DataSource) initContext.lookup("java:/comp/env/jdbc/DataSourceWAD");
            conexion = ds.getConnection();
        } catch (NamingException | SQLException ex) {
            Logger.getLogger(ProductoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conexion;*/
        
        String user = "postgres";
        String pwd = "123";
        String url = "jdbc:postgresql://localhost:5432/Base3CM13";
        String pgDriver = "org.postgresql.Driver";
        try {
            Class.forName(pgDriver);
            conexion = DriverManager.getConnection(url, user, pwd);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conexion;
    }
    
    public List readAll() throws SQLException {
        conectar();
        CallableStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conexion.prepareCall(SQL_READ_ALL);
            rs = ps.executeQuery();
            List resultados = obtenerResultados(rs);
            if (resultados.size() > 0) {
                return resultados;
            } else {
                return null;
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (conexion != null) {
                conexion.close();
            }
        }
    }

    public void create(ProductoDTO dto) throws SQLException {
        conectar();
        CallableStatement ps = null;
        try {
            ps = conexion.prepareCall(SQL_INSERT);
            ps.setString(1, dto.getEntidad().getNombreProducto());
            ps.setString(2, dto.getEntidad().getDescripcionProducto());
            ps.setFloat(3, dto.getEntidad().getPrecio());
            ps.setInt(4, dto.getEntidad().getExistencia());
            ps.setInt(5, dto.getEntidad().getStockMinimo());
            ps.setInt(6, dto.getEntidad().getClaveCategoria());
            ps.executeUpdate();
            
                    
        } finally {
            if (ps != null) {ps.close();
            }
            if (conexion != null) {
                conexion.close();
            }
        }
    }

    public void update(ProductoDTO dto) throws SQLException {
        conectar();
        CallableStatement ps = null;
        try {
            ps = conexion.prepareCall(SQL_UPDATE);
            ps.setString(1, dto.getEntidad().getNombreProducto());
            ps.setString(2, dto.getEntidad().getDescripcionProducto());
            ps.setFloat(3, dto.getEntidad().getPrecio());
            ps.setInt(4, dto.getEntidad().getExistencia());
            ps.setInt(5, dto.getEntidad().getStockMinimo());
            ps.setInt(6, dto.getEntidad().getClaveCategoria());
            ps.setInt(7, dto.getEntidad().getIdProducto());
            ps.executeUpdate();
            
                    
        } finally {
            if (ps != null) {ps.close();
            }
            if (conexion != null) {
                conexion.close();
            }
        }
    }
    public void delete(ProductoDTO dto) throws SQLException {
        conectar();
        CallableStatement ps = null;
        try {
            ps = conexion.prepareCall(SQL_DELETE);
            ps.setInt(1, dto.getEntidad().getIdProducto());
            ps.executeUpdate();
            
                    
        } finally {
            if (ps != null) {ps.close();
            }
            if (conexion != null) {
                conexion.close();
            }
        }
    }
    
    public ProductoDTO read(ProductoDTO dto) throws SQLException {
        conectar();
        CallableStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conexion.prepareCall(SQL_READ);
            ps.setInt(1, dto.getEntidad().getIdProducto());
            rs = ps.executeQuery();
            List resultados = obtenerResultados(rs);
            if (resultados.size() > 0) {
                return (ProductoDTO) resultados.get(0);
            } else {
                return null;
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (conexion != null) {
                conexion.close();
            }
        }
    }
    
    private List obtenerResultados(ResultSet rs) throws SQLException {
        List resultados = new ArrayList();
        while (rs.next()) {
            ProductoDTO p = new ProductoDTO();
            p.getEntidad().setIdProducto(rs.getInt("idProducto"));
            p.getEntidad().setNombreProducto(rs.getString("nombreProducto"));
            p.getEntidad().setDescripcionProducto(rs.getString("descripcionProducto"));
            p.getEntidad().setPrecio(rs.getFloat("precio"));
            p.getEntidad().setExistencia(rs.getInt("existencia"));
            p.getEntidad().setStockMinimo(rs.getInt("stockMinimo"));
            p.getEntidad().setClaveCategoria(rs.getInt("claveCategoria"));
            resultados.add(p);
        }
        return resultados;
    }

    public static void main(String[] args) {
        try {
            ProductoDAO dao = new ProductoDAO();
            ProductoDTO dto = new ProductoDTO();
            /*dto.getEntidad().setNombreProducto("PC");
            dto.getEntidad().setDescripcionProducto("PC para juegos");
            dto.getEntidad().setExistencia(100);
            dto.getEntidad().setPrecio(1000);
            dto.getEntidad().setStockMinimo(10);
            dto.getEntidad().setClaveCategoria(2);*/
            //dao.create(dto);
            System.out.println(dao.readAll());
        } catch (SQLException ex) {
            Logger.getLogger(ProductoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
/*
private static final String SQL_INSERT = "INSERT INTO producto (nombreProducto, descripcionProducto, precio, existencia, stockMinimo, claveCategoria) VALUES (?,?,?,?,?,?)";
    private static final String SQL_UPDATE = "UPDATE producto SET nombreProducto = ?, descripcionProducto = ?, precio = ?, existencia = ?, stockMinimo = ?, claveCategoria = ? WHERE idProducto=?";
    private static final String SQL_DELETE = "DELETE FROM producto WHERE idProducto = ?";
    private static final String SQL_READ = "SELECT idProducto, nombreProducto, descripcionProducto, precio, existencia, stockMinimo, claveCategoria FROM producto WHERE idProducto = ?";
    private static final String SQL_READ_ALL = "SELECT idProducto, nombreProducto, descripcionProducto, precio, existencia, stockMinimo, claveCategoria FROM producto";*/