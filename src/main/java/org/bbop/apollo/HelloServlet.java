package org.bbop.apollo;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;

//@WebServlet(value="/jbrowse/", name="helloServlet")
@WebServlet(value="/jbrowse/asdkfjasdlfj", name="JBrowseData")
public class HelloServlet extends GenericServlet {
    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        res.getWriter().println("JBrowse Data rerouting");
    }
}