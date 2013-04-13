package mdettlaff.cloudreader.web;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import mdettlaff.cloudreader.persistence.PersistenceConfig;
import mdettlaff.cloudreader.service.ServiceConfig;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class CloudReaderWebAppInitializer implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext container) throws ServletException {
		AnnotationConfigWebApplicationContext root = new AnnotationConfigWebApplicationContext();
		root.scan(PersistenceConfig.class.getPackage().getName(),
				ServiceConfig.class.getPackage().getName(),
				WebConfig.class.getPackage().getName());
		container.addListener(new ContextLoaderListener(root));
		ServletRegistration.Dynamic dispatcher = container.addServlet(
				"dispatcher", new DispatcherServlet(root));
		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping("/");
	}
}
