/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ipn.mx.controlador;

import com.ipn.mx.modelo.dao.GraficaDAO;
import com.ipn.mx.modelo.dao.UsuarioDAO;
import com.ipn.mx.modelo.dto.GraficaDTO;
import com.ipn.mx.modelo.dto.UsuarioDTO;
import com.ipn.mx.utilerias.EnviarMail;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

/**
 *
 * @author JMTN
 */
@WebServlet(name = "UsuarioServlet", urlPatterns = {"/UsuarioServlet"})
public class UsuarioServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String accion = request.getParameter("accion");
        if (accion.equals("listaDeUsuarios")) {
            listaDeUsuarios(request, response);
        } else {
            if (accion.equals("nuevo")) {
                agregarUsuario(request, response);
            } else {
                if (accion.equals("eliminar")) {
                    eliminarUsuario(request, response);
                } else {
                    if (accion.equals("actualizar")) {
                        actualizarUsuario(request, response);
                    } else {
                        if (accion.equals("guardar")) {
                            almacenarUsuario(request, response);
                        } else {
                            if (accion.equals("ver")) {
                                mostrarUsuario(request, response);
                            } else {
                                if (accion.equals("verReporte")) {
                                    mostrarReporte(request, response);
                                } else {
                                    if (accion.equals("graficar")) {
                                        mostrarGrafica(request, response);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void listaDeUsuarios(HttpServletRequest request, HttpServletResponse response) {
        UsuarioDAO dao = new UsuarioDAO();
        try {
            Collection lista = dao.readAll();
            request.setAttribute("listaDeUsuarios", lista);
            RequestDispatcher rd = request.getRequestDispatcher("/usuario/listaUsuarios.jsp");
            rd.forward(request, response);

        } catch (SQLException | ServletException | IOException ex) {
            Logger.getLogger(UsuarioServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void eliminarUsuario(HttpServletRequest request, HttpServletResponse response) {
        UsuarioDAO dao = new UsuarioDAO();
        UsuarioDTO dto = new UsuarioDTO();
        dto.getEntidad().setIdAlumno(Integer.parseInt(request.getParameter("id")));
        try {
            dao.delete(dto);
            listaDeUsuarios(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void agregarUsuario(HttpServletRequest request, HttpServletResponse response) {
        RequestDispatcher vista = request.getRequestDispatcher("usuario/usuariosForm.jsp");
        try {
            //request.setCharacterEncoding("UTF-8");
            request.setAttribute("modificar", 0);
            vista.forward(request, response);
        } catch (ServletException | IOException ex) {
            Logger.getLogger(UsuarioServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void actualizarUsuario(HttpServletRequest request, HttpServletResponse response) {
        UsuarioDAO dao = new UsuarioDAO();
        UsuarioDTO dto = new UsuarioDTO();
        dto.getEntidad().setIdAlumno(Integer.parseInt(request.getParameter("id")));
        RequestDispatcher vista = request.getRequestDispatcher("/usuario/usuariosForm.jsp");
        try {
            request.setCharacterEncoding("UTF-8");
            dto = dao.read(dto);
            request.setAttribute("usuario", dto);
            request.setAttribute("modificar", 1);
            vista.forward(request, response);
        } catch (SQLException | ServletException | IOException ex) {
            Logger.getLogger(UsuarioServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void almacenarUsuario(HttpServletRequest request, HttpServletResponse response) {
        UsuarioDAO dao = new UsuarioDAO();
        UsuarioDTO dto = new UsuarioDTO();
        if (Integer.parseInt(request.getParameter("modificar")) == 0) {
            dto.getEntidad().setNombre(request.getParameter("txtNombre"));
            dto.getEntidad().setPaterno(request.getParameter("txtApPaterno"));
            dto.getEntidad().setMaterno(request.getParameter("txtApMaterno"));
            dto.getEntidad().setEmail(request.getParameter("txtEmailUser"));
            dto.getEntidad().setNombreUsuario(request.getParameter("txtUsername"));
            dto.getEntidad().setClaveUsuario(request.getParameter("txtClaveUser"));
            dto.getEntidad().setTipoUsuario(request.getParameter("txtTypeUser"));
            try {
                //request.setCharacterEncoding("UTF-8");
                dao.create(dto);
                request.setAttribute("mensaje", "Usuario creado");
                EnviarMail mail = new EnviarMail();
                mail.enviarCorreo(request.getParameter("txtEmailUser"), "Registro satisfactorio", "Nuevo usuario creado con exito");
                listaDeUsuarios(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(UsuarioServlet.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            dto.getEntidad().setNombre(request.getParameter("txtNombre"));
            dto.getEntidad().setPaterno(request.getParameter("txtApPaterno"));
            dto.getEntidad().setMaterno(request.getParameter("txtApMaterno"));
            dto.getEntidad().setEmail(request.getParameter("txtEmailUser"));
            dto.getEntidad().setNombreUsuario(request.getParameter("txtUsername"));
            dto.getEntidad().setClaveUsuario(request.getParameter("txtClaveUser"));
            dto.getEntidad().setTipoUsuario(request.getParameter("txtTypeUser"));
            dto.getEntidad().setIdAlumno(Integer.parseInt(request.getParameter("txtIdUsuario")));
            try {
                //request.setCharacterEncoding("UTF-8");
                
                //System.out.println(dto);
                dao.update(dto);
                
                request.setAttribute("mensaje", "Usuario modificado con exito");
                listaDeUsuarios(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(UsuarioServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void mostrarUsuario(HttpServletRequest request, HttpServletResponse response) {
        UsuarioDAO dao = new UsuarioDAO();
        UsuarioDTO dto = new UsuarioDTO();
        dto.getEntidad().setIdAlumno(Integer.parseInt(request.getParameter("id")));
        RequestDispatcher vista = request.getRequestDispatcher("/usuario/datosUsuario.jsp");
        try {
            dto = dao.read(dto);
            request.setAttribute("usuario", dto);
            vista.forward(request, response);
        } catch (SQLException | ServletException | IOException ex) {
            Logger.getLogger(UsuarioServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void mostrarReporte(HttpServletRequest request, HttpServletResponse response) {
        UsuarioDAO dao = new UsuarioDAO();
        if(Integer.parseInt(request.getParameter("individual"))==0){
            try {
                ServletOutputStream sos = response.getOutputStream();
                File reporte = new File(getServletConfig().getServletContext().getRealPath("/reportes/ReporteGeneralUsers.jasper"));
                byte[] b = JasperRunManager.runReportToPdf(reporte.getPath(), null, dao.conectar());
                response.setContentType("application/pdf");
                response.setContentLength(b.length);
                
                sos.write(b, 0, b.length);
                sos.flush();
                sos.close();
            } catch (IOException | JRException ex) {
                Logger.getLogger(UsuarioServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            //quiere ver el reporte de un id
            try {
                ServletOutputStream sos = response.getOutputStream();
                File reporte = new File(getServletConfig().getServletContext().getRealPath("/reportes/ReporteUsuariosxID.jasper"));
                HashMap parametros = new HashMap();
                parametros.put("idBuscarUsuarios", Integer.parseInt(request.getParameter("id")));
                byte[] b = JasperRunManager.runReportToPdf(reporte.getPath(), parametros, dao.conectar());
                response.setContentType("application/pdf");
                response.setContentLength(b.length);

                sos.write(b, 0, b.length);
                sos.flush();//flush, aztualización, 
                sos.close();

            } catch (IOException | JRException ex) {
                Logger.getLogger(CategoriaServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void mostrarGrafica(HttpServletRequest request, HttpServletResponse response) {
        JFreeChart grafica = ChartFactory.createPieChart("Usuarios por tipo de usuario", obtenerGraficaDeUsuariosPorTipo(), true, true, Locale.getDefault());
        //Declaración de donde se guardara el archivo
        String archivo = getServletConfig().getServletContext().getRealPath("/grafica_User.png");
        try{
            request.setAttribute("grafica", 2);
            ChartUtils.saveChartAsPNG(new File(archivo), grafica, 500, 500);
            RequestDispatcher vistas = request.getRequestDispatcher("grafica.jsp");
            vistas.forward(request, response);
        } catch (IOException | ServletException ex) {
            Logger.getLogger(UsuarioServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private PieDataset obtenerGraficaDeUsuariosPorTipo() {
        DefaultPieDataset dsPie = new DefaultPieDataset();
        GraficaDAO dao = new GraficaDAO();
        try {
            List datos = dao.graficarUserPorTipoUsuario();
            for (int i = 0; i < datos.size(); i++) {
                GraficaDTO dto = (GraficaDTO) datos.get(i);
                dsPie.setValue(dto.getNombreCategoria(), dto.getCantidad());
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dsPie;
    }
}