///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
package filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Jamie
 */
public class AuthenticationFilter implements Filter {

    private ServletContext context;

    public void init(FilterConfig fConfig) throws ServletException {
        this.context = fConfig.getServletContext();
        this.context.log("AuthenticationFilter initialized");
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String uri = req.getRequestURI();
        this.context.log("Requested Resource::" + uri);

        // !! NOTES: when the project is started, there's no session, but a session is auto. 
        // created when accessing a .jsp with no attribute, so all the pages 
        // before user login or signup should be html
        HttpSession session = req.getSession(false);
        System.out.println("------------------------------------------");

        
        // preload stylesheets and images
        if (uri.endsWith(".css") || uri.endsWith(".ico") || uri.endsWith(".png")) {
            chain.doFilter(request, response);
        } 
        // logout
        else if (session != null && uri.equals("/Logout"))
            chain.doFilter(request, response); 
        // if no session and the uri is not for login or signup processes, redirect
        else if (session == null && !(
                     uri.equals("/Login.html") || uri.equals("/Login")
                    || uri.equals("/SignUpClient.html") || uri.equals("/SignUpClient")
                    || uri.equals("/SignUp.html") || uri.equals("/SignUp"))) {
            this.context.log("Unauthorized access request");
            res.sendRedirect("/Login.html");
        } 
        // if user is loggedin, but the uri does not belong to their dashboard, redirect to Home page
        else if(session != null && session.getAttribute("isLoggedIn")!=null) {
            String role = (String) session.getAttribute("role");
            if ((role.equals("admin") && !adminAccess(uri))             // admin access
                    || (role.equals("client") && !clientAccess(uri))    // client access
                    || (role.equals("doctor") && !doctorAccess(uri))    // doctor access
                    || (role.equals("nurse") && !nurseAccess(uri)) )    // nurse access
                res.sendRedirect("/viewer/Home.jsp");            
            else
                chain.doFilter(request, response);            
        }
        else {
            // pass the request along the filter chain
            chain.doFilter(request, response);
        }
    }

    public void destroy() {
        //close any resources h
    }

    public boolean adminAccess(String uri) {
        return (uri.endsWith("/Home.jsp")
                || uri.equals("/viewer/admin/AddEmployees.jsp") || uri.equals("/AddEmployees")
                || uri.equals("/viewer/admin/CancelSurgery.jsp") || uri.equals("/CancelSurgery")
                || uri.equals("/viewer/admin/ChangePrices.jsp") || uri.equals("/ChangePrices")
                || uri.equals("/viewer/admin/ProduceDocuments.jsp") || uri.equals("/ProduceDocuments"));

    }
    public boolean clientAccess(String uri) {
        // implement this like the adminAccess method above
        // include both the .jsp page and the servlet paths
        return true;

    }
    public boolean doctorAccess(String uri) {
        // implement this like the adminAccess method above
        // include both the .jsp page and the servlet paths
        return true;

    }
    public boolean nurseAccess(String uri) {
        // implement this like the adminAccess method above
        // include both the .jsp page and the servlet paths
        return true;

    }

}
