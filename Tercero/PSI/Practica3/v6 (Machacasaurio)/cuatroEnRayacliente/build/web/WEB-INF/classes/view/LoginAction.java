/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import con.connectWS;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.WebServiceRef;
//import testers.testCreateGame;
import webservice.WebServiceServer;
import webservice.WebServiceServer_Service;
import webservice.Exception_Exception;
import webservice.ReturnError;

/**
 *
 * @author roberto
 */
@WebServlet(name = "loginAction", urlPatterns = {"/loginAction"})
public class LoginAction extends HttpServlet {
    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8080/CuatroEnRayaServer/WebServiceServer.wsdl")
    private webservice.WebServiceServer_Service service;
    private HttpSession session = null;

    //@EJB
    connectWS connect4;

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
        /*connect4.initSession();
        WebServiceServer port = connect4.getPort();
        //save port in session
        HttpSession session = request.getSession();
        session.setAttribute("port", port);*/
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            this.session = request.getSession();
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>loginAction</title>");
            out.println("</head>");
            out.println("<body>");
            String userName = request.getParameter("userName");
            String passwd = request.getParameter("passwd");
            ReturnError err = login(userName, passwd);
            if (err.getErrorCode() != 0)
                out.println("Error login: " + userName + " (try another username)");
            else {
                out.println("Logged user " + userName + "with userId: " + err.getValue() );
                this.session.setAttribute("user", userName);
            }
            //out.println(" <a href=\"/connectFourClient/createGame.jsp\">create Game</a> ");
            out.println("</body>");
            out.println("</html>");
        } catch (Exception e) {
            System.out.println(e.getMessage());
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

    private webservice.WebServiceServer getPort() {
        webservice.WebServiceServer port;
        
        if ((session.getAttribute("port")==null)) {
            port = service.getWebServiceServerPort();
            Map requestContext = ((BindingProvider) port).getRequestContext();
            requestContext.put(BindingProvider.SESSION_MAINTAIN_PROPERTY,
                    Boolean.TRUE);
            session.setAttribute("port", port);
        }
        else
            port = (webservice.WebServiceServer) session.getAttribute("port");  
        return port;
    }

    private ReturnError login(java.lang.String userName, java.lang.String password) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        webservice.WebServiceServer port = getPort();
        return port.login(userName, password);
    }
    
    
}
