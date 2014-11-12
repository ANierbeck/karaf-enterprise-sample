package de.nierbeck.apachecon.view.internal;

import java.util.Dictionary;
import java.util.Hashtable;

import javax.servlet.ServletException;

import org.ops4j.pax.web.samples.helloworld.hs.internal.HelloWorldServlet;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.http.HttpContext;
import org.osgi.service.http.HttpService;
import org.osgi.service.http.NamespaceException;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

import de.nierbeck.apachecon.persistence.api.CookBookService;
import de.nierbeck.apachecon.view.ViewBookServlet;

public class NotUsedHttpServiceActivator implements BundleActivator,
		ServiceTrackerCustomizer<HttpService, HttpService> {

	private BundleContext bundleContext;

	private ServiceTracker<HttpService, HttpService> tracker;

	/**
	 * Called when the OSGi framework starts our bundle
	 */
	public void start(BundleContext bc) throws Exception {
		bundleContext = bc;
		tracker = new ServiceTracker<HttpService, HttpService>(bc,
				HttpService.class, this);
		tracker.open();
	}

	/**
	 * Called when the OSGi framework stops our bundle
	 */
	public void stop(BundleContext bc) throws Exception {
		tracker.close();
	}
	
	@Override
	public HttpService addingService(ServiceReference<HttpService> reference) {
		final HttpService httpService = (HttpService) bundleContext.getService(reference);
		if (httpService != null) {
			// create a default context to share between registrations
			final HttpContext httpContext = httpService
					.createDefaultHttpContext();
			// register the BookServlet

			// first wait for the DAO-Service
			CookBookService cookBookService;
			ServiceReference<CookBookService> serviceReference = bundleContext
					.getServiceReference(CookBookService.class);
			cookBookService = bundleContext.getService(serviceReference);

			//now create the servlet
			ViewBookServlet viewBookServlet = new ViewBookServlet();
			
			//set the DAO to the Servlet
			viewBookServlet.setCookBookService(cookBookService);

			final Dictionary<String, Object> initParams = new Hashtable<String, Object>();
			try {
				httpService.registerServlet("/book", // alias
						viewBookServlet,
						initParams, httpContext);
			} catch (ServletException e) {
				e.printStackTrace();
			} catch (NamespaceException e) {
				e.printStackTrace();
			}
		}
		return httpService;
	}

	@Override
	public void modifiedService(ServiceReference<HttpService> reference,
			HttpService service) {
		// ignore
	}

	@Override
	public void removedService(ServiceReference<HttpService> reference,
			HttpService service) {
		try {
			service.unregister("/book");
		} catch (Exception e) { 

		}
	}

}
