package Spring.WebMVC;
import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.servlets.DefaultServlet;
import org.springframework.web.servlet.DispatcherServlet;
import org.apache.catalina.Wrapper;

import java.io.File;

public class AppConfig {
	public static void main(String[] args) throws LifecycleException {
		Tomcat tomcat = new Tomcat();
		tomcat.setPort(8080);

		String webAppDir = new File("src/main/webapp/").getAbsolutePath();
		Context context = tomcat.addWebapp("", webAppDir);

		// Add DispatcherServlet
		Wrapper dispatcherServlet = Tomcat.addServlet(context, "dispatcher", new DispatcherServlet());
		dispatcherServlet.addInitParameter("contextConfigLocation", "quickstart.WebConfig");
		dispatcherServlet.setLoadOnStartup(1);
		context.addServletMappingDecoded("/", "dispatcher");

		tomcat.start();
		tomcat.getServer().await();
	}
}
