package me.aroliveira.jakartaee8.servlet;

import javax.servlet.*;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static me.aroliveira.jakartaee8.servlet.me.aroliveira.jakartaee8.utils.Langz.defaultIfNull;
import static me.aroliveira.jakartaee8.servlet.me.aroliveira.jakartaee8.utils.Langz.unsafeSleep;

/*
The container register servlets declared on the web.xml or annotated as below.

Servlet lifecycle:
1. init(), called once
2. service(), called for each request
3. destroy(), called once
See details below.
 */
@WebServlet(
    urlPatterns = "/one/*",
    initParams = {
        @WebInitParam(name = "hello", value = "Hello from MyFirstServlet!"),
        @WebInitParam(name = "bye", value = "Run away and never return!"),
        @WebInitParam(name = "defaultz", value = "I'm alive!"),
    })
public class MyFirstServlet implements Servlet {

    private ServletConfig config;
    private String hello;
    private String bye;
    private String defaultz;

    /*
        Only one instance of a servlet exists.
         */
    public MyFirstServlet() {
        System.out.println("Executing servlet constructor");
    }

    /*
    Init() is called only once, after the constructor.
    May be when the first request arrives or (if loadOnStartup is set) when the container starts.
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        System.out.println("Executing servlet init()");
        this.config = config;
        this.hello = config.getInitParameter("hello");
        this.bye = config.getInitParameter("bye");
        this.defaultz = config.getInitParameter("defaultz");
    }

    /*
    Service() is called for each request.
     */
    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {

        // send many requests to see other threads being created
        unsafeSleep(1000);

        String path = null;
        if (req instanceof HttpServletRequest) {
            path = ((HttpServletRequest) req).getPathInfo();
        }
        System.out.printf("Executing servlet service() on thread %d for path %s %n",
                Thread.currentThread().getId(), path);
        String responseText;
        switch (defaultIfNull(path, "")) {
            case "/hello":
                responseText = hello;
                break;
            case "/bye":
                responseText = bye;
                break;
            default:
                responseText = defaultz;
        }
        res.setContentType("text/plain");
        res.getWriter().print(responseText);
    }

    /*
    Destroy() is called once, when the container is shutting down.
     */
    @Override
    public void destroy() {
        System.out.println("Executing servlet destroy()");
    }

    @Override
    public ServletConfig getServletConfig() {
        return config;
    }

    @Override
    public String getServletInfo() {
        return "whatever";
    }

}
