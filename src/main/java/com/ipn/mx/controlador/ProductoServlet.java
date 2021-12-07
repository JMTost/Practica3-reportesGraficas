/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ipn.mx.controlador;

import com.ipn.mx.modelo.dao.GraficaDAO;
import com.ipn.mx.modelo.dao.ProductoDAO;
import com.ipn.mx.modelo.dto.GraficaDTO;
import com.ipn.mx.modelo.dto.ProductoDTO;
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
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.Dataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

/**
 *
 * @author JMTN
 */
@WebServlet(name = "ProductoServlet", urlPatterns = {"/ProductoServlet"})
public class ProductoServlet extends HttpServlet {

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
        response.setCharacterEncoding("UTF-8");
        String accion = request.getParameter("accion");
        if (accion.equals("listaDeProductos")) {
            listaDeProductos(request, response);
        } else {
            if (accion.equals("nuevo")) {
                agregarProducto(request, response);
            } else {
                if (accion.equals("eliminar")) {
                    eliminarProducto(request, response);
                } else {
                    if (accion.equals("actualizar")) {
                        actualizarProducto(request, response);
                    } else {
                        if (accion.equals("guardar")) {
                            almacenarProducto(request, response);
                        } else {
                            if (accion.equals("ver")) {
                                mostrarProducto(request, response);
                            } else {
                                if (accion.equals("verReporte")) {
                                    verReporte(request, response);
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

    private void listaDeProductos(HttpServletRequest request, HttpServletResponse response) {
        ProductoDAO dao = new ProductoDAO();
        try {
            Collection lista = dao.readAll();
            //System.out.println("lista");
            request.setAttribute("listaDeProductos", lista);
            RequestDispatcher rd = request.getRequestDispatcher("/productos/listaProductos.jsp");
            rd.forward(request, response);
        } catch (SQLException | ServletException | IOException ex) {
            Logger.getLogger(ProductoServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void agregarProducto(HttpServletRequest request, HttpServletResponse response) {
        RequestDispatcher vista = request.getRequestDispatcher("/productos/productosForm.jsp");
        try {
            request.setCharacterEncoding("UTF-8");
            request.setAttribute("modificar", 0);
            vista.forward(request, response);
        } catch (ServletException ex) {
            Logger.getLogger(ProductoServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ProductoServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void eliminarProducto(HttpServletRequest request, HttpServletResponse response) {
        ProductoDAO dao = new ProductoDAO();
        ProductoDTO dto = new ProductoDTO();
        dto.getEntidad().setIdProducto(Integer.parseInt(request.getParameter("id")));
        try {
            dao.delete(dto);
            listaDeProductos(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ProductoServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void actualizarProducto(HttpServletRequest request, HttpServletResponse response) {
        ProductoDAO dao = new ProductoDAO();
        ProductoDTO dto = new ProductoDTO();
        dto.getEntidad().setIdProducto(Integer.parseInt(request.getParameter("id")));
        RequestDispatcher vista = request.getRequestDispatcher("/productos/productosForm.jsp");
        try {
            request.setCharacterEncoding("UTF-8");
            dto = dao.read(dto);
            request.setAttribute("producto", dto);
            request.setAttribute("modificar", 1);
            vista.forward(request, response);
        } catch (SQLException | ServletException | IOException ex) {
            Logger.getLogger(ProductoServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void almacenarProducto(HttpServletRequest request, HttpServletResponse response) {
        ProductoDAO dao = new ProductoDAO();
        ProductoDTO dto = new ProductoDTO();

        if (Integer.parseInt(request.getParameter("modificar")) == 0) {
            dto.getEntidad().setNombreProducto(request.getParameter("txtNombreProducto"));
            dto.getEntidad().setDescripcionProducto(request.getParameter("txtDescripcionProducto"));
            dto.getEntidad().setPrecio(Float.parseFloat(request.getParameter("txtPrecioProducto")));
            dto.getEntidad().setExistencia(Integer.parseInt(request.getParameter("txtExistenciaProducto")));
            dto.getEntidad().setStockMinimo(Integer.parseInt(request.getParameter("txtStockProducto")));
            dto.getEntidad().setClaveCategoria(Integer.parseInt(request.getParameter("txtClaveCategoria")));
            try {
                request.setCharacterEncoding("UTF-8");
                //response.setCharacterEncoding("UTF-8");
                dao.create(dto);
                request.setAttribute("mensaje", "El producto fue agregado con exito");
                listaDeProductos(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(ProductoServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(ProductoServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            dto.getEntidad().setNombreProducto(request.getParameter("txtNombreProducto"));
            dto.getEntidad().setDescripcionProducto(request.getParameter("txtDescripcionProducto"));
            dto.getEntidad().setPrecio(Float.parseFloat(request.getParameter("txtPrecioProducto")));
            dto.getEntidad().setExistencia(Integer.parseInt(request.getParameter("txtExistenciaProducto")));
            dto.getEntidad().setStockMinimo(Integer.parseInt(request.getParameter("txtStockProducto")));
            dto.getEntidad().setClaveCategoria(Integer.parseInt(request.getParameter("txtClaveCategoria")));
            dto.getEntidad().setIdProducto(Integer.parseInt(request.getParameter("txtIdProducto")));
            try {
                request.setCharacterEncoding("UTF-8");
                dao.update(dto);
                request.setAttribute("mensaje", "Producto actualizado con exito");
                listaDeProductos(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(ProductoServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(ProductoServlet.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    private void mostrarProducto(HttpServletRequest request, HttpServletResponse response) {
        ProductoDAO dao = new ProductoDAO();
        ProductoDTO dto = new ProductoDTO();
        dto.getEntidad().setIdProducto(Integer.parseInt(request.getParameter("id")));
        RequestDispatcher vista = request.getRequestDispatcher("/productos/datosProducto.jsp");
        try {
            dto = dao.read(dto);
            request.setAttribute("producto", dto);
            vista.forward(request, response);
        } catch (SQLException | ServletException | IOException ex) {
            Logger.getLogger(ProductoServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void verReporte(HttpServletRequest request, HttpServletResponse response) {
        ProductoDAO dao = new ProductoDAO();
        if (Integer.parseInt(request.getParameter("individual")) == 0) {
            try {
                ServletOutputStream sos = response.getOutputStream();
                File reporte = new File(getServletConfig().getServletContext().getRealPath("/reportes/reportesProductos.jasper"));
                byte[] b = JasperRunManager.runReportToPdf(reporte.getPath(), null, dao.conectar());
                response.setContentType("application/pdf");
                response.setContentLength(b.length);

                sos.write(b, 0, b.length);
                sos.flush();
                sos.close();

            } catch (IOException | JRException ex) {
                Logger.getLogger(ProductoServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                ServletOutputStream sos = response.getOutputStream();
                File reporte = new File(getServletConfig().getServletContext().getRealPath("/reportes/ReporteProductosxID.jasper"));
                HashMap parametros = new HashMap();
                parametros.put("idBuscarProd", Integer.parseInt(request.getParameter("id")));
                byte[] b = JasperRunManager.runReportToPdf(reporte.getPath(), parametros, dao.conectar());
                response.setContentType("application/pdf");
                response.setContentLength(b.length);

                sos.write(b, 0, b.length);
                sos.flush();
                sos.close();

            } catch (IOException | JRException ex) {
                Logger.getLogger(ProductoServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

     private void mostrarGrafica(HttpServletRequest request, HttpServletResponse response) {
        JFreeChart grafica = ChartFactory.createBarChart("Existencia de los productos", "Producto", "Existencia", obtenerGraficaTipouser(), PlotOrientation.VERTICAL, true, true, false);
        //Declaramos la ruta en donde se guardara el archivo
        String archivo = getServletConfig().getServletContext().getRealPath("/grafica_Prod.png");
        
//getServletConfig().getServletContext().getRealPath("/grafica.png");
        try {
            request.setAttribute("grafica", 1);
            ChartUtils.saveChartAsPNG(new File(archivo), grafica, 500, 500);
            RequestDispatcher vista = request.getRequestDispatcher("grafica.jsp");
            vista.forward(request, response);
        } catch (IOException | ServletException ex) {
            Logger.getLogger(CategoriaServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private CategoryDataset obtenerGraficaTipouser(){
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        GraficaDAO dao = new GraficaDAO();
        try {
            List datos = dao.graficarProductosPorCategoria();
            for (int i = 0; i < datos.size(); i++) {
                GraficaDTO dto = (GraficaDTO) datos.get(i);
                dataset.addValue(dto.getCantidad(), dto.getNombreCategoria(), "");
            }
        } catch (SQLException ex) {
            Logger.getLogger(CategoriaServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dataset;
    }

}
