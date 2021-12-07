/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ipn.mx.controlador;

import com.ipn.mx.modelo.dao.CategoriaDAO;
import com.ipn.mx.modelo.dao.GraficaDAO;
import com.ipn.mx.modelo.dto.CategoriaDTO;
import com.ipn.mx.modelo.dto.GraficaDTO;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "CategoriaServlet", urlPatterns = {"/CategoriaServlet"})
public class CategoriaServlet extends HttpServlet {

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
        if (accion.equals("listaDeCategorias")) {
            listaDeCategorias(request, response);
        } else {
            if (accion.equals("nuevo")) {
                agregarCategoria(request, response);
            } else {
                if (accion.equals("eliminar")) {
                    eliminarCategoria(request, response);
                } else {
                    if (accion.equals("actualizar")) {
                        actualizarCategoria(request, response);
                    } else {
                        if (accion.equals("guardar")) {
                            almacenarCategoria(request, response);
                        } else {
                            if (accion.equals("ver")) {
                                mostrarCategoria(request, response);
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
        /*try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet CategoriaServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CategoriaServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }*/
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

    private void listaDeCategorias(HttpServletRequest request, HttpServletResponse response) {
        CategoriaDAO dao = new CategoriaDAO();
        try {
            Collection lista = dao.readAll();
            request.setAttribute("listaDeCategorias", lista);
            RequestDispatcher rd = request.getRequestDispatcher("/categorias/listaCategorias.jsp");
            rd.forward(request, response);
        } catch (SQLException | ServletException | IOException ex) {
            Logger.getLogger(CategoriaServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void agregarCategoria(HttpServletRequest request, HttpServletResponse response) {
        RequestDispatcher vista = request.getRequestDispatcher("/categorias/categoriasForm.jsp");
        try {
            request.setAttribute("modificar", 0);
            vista.forward(request, response);
        } catch (ServletException | IOException ex) {
            Logger.getLogger(CategoriaServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void eliminarCategoria(HttpServletRequest request, HttpServletResponse response) {
        CategoriaDAO dao = new CategoriaDAO();
        CategoriaDTO dto = new CategoriaDTO();
        dto.getEntidad().setIdCategoria(Integer.parseInt(request.getParameter("id")));
        try {
            dao.delete(dto);
            listaDeCategorias(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(CategoriaServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void actualizarCategoria(HttpServletRequest request, HttpServletResponse response) {
        CategoriaDAO dao = new CategoriaDAO();
        CategoriaDTO dto = new CategoriaDTO();
        dto.getEntidad().setIdCategoria(Integer.parseInt(request.getParameter("id")));
        RequestDispatcher vista = request.getRequestDispatcher("/categorias/categoriasForm.jsp");
        try {
            dto = dao.read(dto);
            request.setAttribute("categoria", dto);
            request.setAttribute("modificar", 1);
            vista.forward(request, response);
        } catch (SQLException | ServletException | IOException ex) {
            Logger.getLogger(CategoriaServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void almacenarCategoria(HttpServletRequest request, HttpServletResponse response) {
        CategoriaDAO dao = new CategoriaDAO();
        CategoriaDTO dto = new CategoriaDTO();
        if (Integer.parseInt(request.getParameter("modificar")) == 0) {
            dto.getEntidad().setNombreCategoria(request.getParameter("txtNombreCategoria"));
            dto.getEntidad().setDescripcionCategoria(request.getParameter("txtDescripcionCategoria"));
            try {
                dao.create(dto);
                request.setAttribute("mensaje", "Categoría agregada con exito");
                listaDeCategorias(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(CategoriaServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            dto.getEntidad().setNombreCategoria(request.getParameter("txtNombreCategoria"));
            dto.getEntidad().setDescripcionCategoria(request.getParameter("txtDescripcionCategoria"));
            dto.getEntidad().setIdCategoria(Integer.parseInt(request.getParameter("txtIDcategoria")));
            try {
                dao.update(dto);
                request.setAttribute("mensaje", "Categoría actualizada con exito");
                listaDeCategorias(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(CategoriaServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    private void mostrarCategoria(HttpServletRequest request, HttpServletResponse response) {
        CategoriaDAO dao = new CategoriaDAO();
        CategoriaDTO dto = new CategoriaDTO();
        dto.getEntidad().setIdCategoria(Integer.parseInt(request.getParameter("id")));
        RequestDispatcher vista = request.getRequestDispatcher("/categorias/datosCategoria.jsp");
        try {
            dto = dao.read(dto);
            request.setAttribute("categoria", dto);
            vista.forward(request, response);
        } catch (SQLException | ServletException | IOException ex) {
            Logger.getLogger(CategoriaServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void mostrarReporte(HttpServletRequest request, HttpServletResponse response) {
        CategoriaDAO dao = new CategoriaDAO();
        if (Integer.parseInt(request.getParameter("individual")) == 0) {
            //quiere ver el reporte general
            try {
                ServletOutputStream sos = response.getOutputStream();
                File reporte = new File(getServletConfig().getServletContext().getRealPath("/reportes/ReporteGeneral.jasper"));
                byte[] b = JasperRunManager.runReportToPdf(reporte.getPath(), null, dao.conectar());
                response.setContentType("application/pdf");
                response.setContentLength(b.length);

                sos.write(b, 0, b.length);
                sos.flush();//flush, aztualización, 
                sos.close();

            } catch (IOException | JRException ex) {
                Logger.getLogger(CategoriaServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            //quiere ver el reporte de un id
            try {
                ServletOutputStream sos = response.getOutputStream();
                File reporte = new File(getServletConfig().getServletContext().getRealPath("/reportes/ReporteCategoriasxID.jasper"));
                HashMap parametros = new HashMap();
                parametros.put("idBuscar", Integer.parseInt(request.getParameter("id")));
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
        JFreeChart grafica = ChartFactory.createPieChart("Productos por categoria", obtenerGraficaProductosPorCategoria(), true, true, Locale.getDefault());
        //Declaramos la ruta en donde se guardara el archivo
        String archivo = getServletConfig().getServletContext().getRealPath("/grafica.png");
        
//getServletConfig().getServletContext().getRealPath("/grafica.png");
        try {
            request.setAttribute("grafica", 0);
            ChartUtils.saveChartAsPNG(new File(archivo), grafica, 500, 500);
            RequestDispatcher vista = request.getRequestDispatcher("grafica.jsp");
            vista.forward(request, response);
        } catch (IOException | ServletException ex) {
            Logger.getLogger(CategoriaServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private PieDataset obtenerGraficaProductosPorCategoria() {
        DefaultPieDataset dsPie = new DefaultPieDataset();
        GraficaDAO dao = new GraficaDAO();
        try {
            List datos = dao.graficarProductosPorCategoria();
            for (int i = 0; i < datos.size(); i++) {
                GraficaDTO dto = (GraficaDTO) datos.get(i);
                dsPie.setValue(dto.getNombreCategoria(), dto.getCantidad());
            }
        } catch (SQLException ex) {
            Logger.getLogger(CategoriaServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dsPie;
    }
}


/*

create or replace function seleccionaTodoCategoria()
de VC WEBEX ESCOM17 a Todos:    8:16 AM
returns Table(idCategoria int, nombreCategoria varchar, descripcionCategoria varchar)
de VC WEBEX ESCOM17 a Todos:    8:16 AM
as $$ select * from Categoria $$ language sql;

create or replace function seleccionaTodoCategoria()
returns Table(idCategoria int, nombreCategoria varchar, descripcionCategoria varchar)
as $$ select * from Categoria $$ language sql;


CREATE OR REPLACE FUNCTION seleccionaTodoCategoria() RETURNS Table(
    idCategoria int,
    nombreCategoria varchar,
    descripcionCategoria varchar
) AS $$
    SELECT * FROM Categoria;
$$ LANGUAGE sql
 select seleccionaTodoCategoria();


ACTUALIZAR
create or replace procedure spActualizarCategoria(
    in nombre varchar,
    in descripcion varchar,
    in id int
)
language sql 
as $$
    update  Categoria set nombreCategoria = nombre, descripcionCategoria = descripcion where idCategoria = id;
$$
call spActualizarCategoria('Categoria vhgjgj', 'jgjkgjkg 1', 16);


INSERTAR
create or replace procedure spInsertarCategoria(
    in nombre varchar,
    in descripcion varchar
)
language sql 
as $$
    insert into Categoria (nombreCategoria, descripcionCategoria) values (nombre, descripcion);
$$
call spInsertarCategoria('Categoria 1', 'Categporia 1');


ELIMINAR Y SELECCIONAR UNO
create or replace procedure spBuscaUno(in id int) RETURNS TABLE(
    idCategoria int,
    nombreCategoria varchar,
    descripcionCategoria varchar
)AS $$
    SELECT * FROM Categoría WHERE idCategoria = id;
$$ language sql

select spBuscaUno();
select * from spBuscaUno()

 */
 /*

CREATE OR REPLACE FUNCTION seleccionaTodoCategoria() RETURNS Table(
    idCategoria int,
    nombreCategoria varchar,
    descripcionCategoria varchar
) AS $$
    SELECT * FROM Categoria;
$$ LANGUAGE sql

 select seleccionaTodoCategoria();
 
 
 create or replace procedure spActualizarCategoria(
    in nombre varchar,
    in descripcion varchar,
    in id int
)
language sql 
as $$
    update  Categoria set nombreCategoria = nombre, descripcionCategoria = descripcion where idCategoria = id;
$$

call spActualizarCategoria('Categoria aouihdaiso 32', 'laakljkldj a 3', 32);


create or replace procedure spInsertarCategoria(
    in nombre varchar,
    in descripcion varchar
)
language sql 
as $$
    insert into Categoria (nombreCategoria, descripcionCategoria) values (nombre, descripcion);
$$

call spInsertarCategoria('Categoria DE SP', 'Categporia del store procedures');

create or replace procedure spBuscaUno (
    in id int
)
language sql as $$
    SELECT * FROM categoria WHERE idCategoria = id;
$$ 

select spBuscaUno('32');







CREATE OR REPLACE procedure spEliminarCategoria(in id int)
language sql
as $$
    delete from categoria where idCategoria = id;
$$

BUSCAR UNO

CREATE OR REPLACE FUNCTION seleccionarCategoria(in id int)
 RETURNS TABLE(idcategoria integer, nombrecategoria character varying, descripcioncategoria character varying)
 LANGUAGE sql
AS $function$
    SELECT * FROM Categoria where idCategoria = id;
$function$

CREATE OR REPLACE PROCEDURE public.spinsertarcategoria(nombre character varying, descripcion character varying)
 LANGUAGE sql
AS $procedure$
    insert into Categoria (nombreCategoria, descripcionCategoria) values (nombre, descripcion);
$procedure$
;

CREATE OR REPLACE PROCEDURE public.spactualizarcategoria(nombre character varying, descripcion character varying, id integer)
 LANGUAGE sql
AS $procedure$
    update  Categoria set nombreCategoria = nombre, descripcionCategoria = descripcion where idCategoria = id;
$procedure$
;


 */
 /*
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
de VC WEBEX ESCOM17 a Todos:    7:57 AM
private PieDataset obtenerGraficaProductosPorCtegoria(){
        DefaultPieDataset dsPie = new DefaultPieDataset();
        GraficaDAO dao = new GraficaDAO();
        try {
            List datos = dao.graficarProductosPorCategoria();
            for (int i = 0; i < datos.size(); i++) {
                GraficaDTO dto = (GraficaDTO) datos.get(i);
                dsPie.setValue(dto.getNombreCategoria(), dto.getCantidad());
            }
        } catch (SQLException ex) {
            Logger.getLogger(CategoriaServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dsPie;
    }
 */
