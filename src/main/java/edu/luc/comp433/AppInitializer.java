package edu.luc.comp433;


import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class AppInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext container) throws ServletException {
        AnnotationConfigWebApplicationContext context
                = new AnnotationConfigWebApplicationContext();
        context.setConfigLocations("edu.luc.comp433.config.MainConfig", "edu.luc.comp433.config.PersistenceJPAConfig");

        container.addListener(new ContextLoaderListener(context));

        ServletRegistration.Dynamic dispatcher = container
                .addServlet("dispatcher", new DispatcherServlet(context));
        dispatcher.setInitParameter("contextConfigLocation", "edu.luc.comp433.config.WebMvcConfig");

        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/app");

        ServletRegistration.Dynamic cxf = container
                .addServlet("cxf", new CXFServlet());

        cxf.setLoadOnStartup(1);
        cxf.addMapping("/cxf");
    }
}
