/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ipn.mx.modelo.dao;

import com.ipn.mx.modelo.dto.UsuarioDTO;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
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
public class UsuarioDAO {

    private static final String SQL_INSERT = "call spInsertarUsuario(?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE = "call spActualizarUsuario(?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_DELETE = "call spEliminarUsuario(?)";
    private static final String SQL_READ = "select * from buscaUnUsuario(?)";
    private static final String SQL_READ_ALL = "select * from seleccionaUsuarios()";

    private Connection conexion;

    //metodo que nos permitirá conectarnos a la base de datos
    public Connection conectar() {
        /*InitialContext initContext;
        try {
            initContext = new InitialContext();
            DataSource ds = (DataSource) initContext.lookup("java:/comp/env/jdbc/DataSourceWAD");
            conexion = ds.getConnection();
        } catch (NamingException | SQLException ex) {
            Logger.getLogger(CategoriaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conexion;*/
        String user = "postgres";
        String pwd = "123";
        String url = "jdbc:postgresql://localhost:5432/Base3CM13?characterEncoding=utf-8";
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

    public void create(UsuarioDTO dto) throws SQLException {
        conectar();
        CallableStatement ps = null;
        try {
            ps = conexion.prepareCall(SQL_INSERT);
            ps.setString(1, dto.getEntidad().getNombre());
            ps.setString(2, dto.getEntidad().getPaterno());
            ps.setString(3, dto.getEntidad().getMaterno());
            ps.setString(4, dto.getEntidad().getEmail());
            ps.setString(5, dto.getEntidad().getNombreUsuario());
            ps.setString(6, dto.getEntidad().getClaveUsuario());
            ps.setString(7, dto.getEntidad().getTipoUsuario());
            ps.executeUpdate();

        } finally {
            if (ps != null) {
                ps.close();
            }
            if (conexion != null) {
                conexion.close();
            }
        }
    }

    public void update(UsuarioDTO dto) throws SQLException {
        conectar();
        CallableStatement ps = null;
        try {
            ps = conexion.prepareCall(SQL_UPDATE);
            ps.setString(1, dto.getEntidad().getNombre());
            ps.setString(2, dto.getEntidad().getPaterno());
            ps.setString(3, dto.getEntidad().getMaterno());
            ps.setString(4, dto.getEntidad().getEmail());
            ps.setString(5, dto.getEntidad().getNombreUsuario());
            ps.setString(6, dto.getEntidad().getClaveUsuario());
            ps.setString(7, dto.getEntidad().getTipoUsuario());
            ps.setInt(8, dto.getEntidad().getIdAlumno());
            ps.executeUpdate();

        } finally {
            if (ps != null) {
                ps.close();
            }
            if (conexion != null) {
                conexion.close();
            }
        }
    }

    public void delete(UsuarioDTO dto) throws SQLException {
        conectar();
        CallableStatement ps = null;
        try {
            ps = conexion.prepareCall(SQL_DELETE);
            ps.setInt(1, dto.getEntidad().getIdAlumno());
            ps.executeUpdate();

        } finally {
            if (ps != null) {
                ps.close();
            }
            if (conexion != null) {
                conexion.close();
            }
        }
    }

    public UsuarioDTO read(UsuarioDTO dto) throws SQLException {
        conectar();
        CallableStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conexion.prepareCall(SQL_READ);
            ps.setInt(1, dto.getEntidad().getIdAlumno());
            rs = ps.executeQuery();
            List resultados = obtenerResultados(rs);
            if (resultados.size() > 0) {
                return (UsuarioDTO) resultados.get(0);
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
            UsuarioDTO p = new UsuarioDTO();
            p.getEntidad().setIdAlumno(rs.getInt("idAlumno"));
            p.getEntidad().setNombre(rs.getString("nombre"));
            p.getEntidad().setPaterno(rs.getString("paterno"));
            p.getEntidad().setMaterno(rs.getString("materno"));
            p.getEntidad().setEmail(rs.getString("email"));
            p.getEntidad().setNombreUsuario(rs.getString("nombreUsuario"));
            p.getEntidad().setClaveUsuario(rs.getString("claveUsuario"));
            p.getEntidad().setTipoUsuario(rs.getString("tipoUsuario"));
            resultados.add(p);
        }
        return resultados;
    }

    public static void main(String[] args) {
        UsuarioDAO dao = new UsuarioDAO();
        UsuarioDTO dto = new UsuarioDTO();
        try {
            dto.getEntidad().setNombre("Jose Emanuel");
            dto.getEntidad().setPaterno("Perez");
            dto.getEntidad().setMaterno("Escamilla");
            dto.getEntidad().setEmail("max.55@live.com.mx");
            dto.getEntidad().setNombreUsuario("PRUEBAmod");
            dto.getEntidad().setClaveUsuario("123prueba");
            dto.getEntidad().setTipoUsuario("Administrativo");
            dto.getEntidad().setIdAlumno(3);
            //dao.update(dto);
            System.out.println(dao.readAll());

        } catch (SQLException ex) {
            Logger.getLogger(ProductoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
