package de.nierbeck.apachecon.view.internal;

import javax.servlet.Servlet;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

import de.nierbeck.apachecon.persistence.api.CookBookService;
import de.nierbeck.apachecon.view.ViewBookServlet;

public class NotUsedWhiteboardActivator implements BundleActivator {

	private BundleContext bundleContext;
	private ServiceRegistration<Servlet> registerServiced;

	/**
	 * Called when the OSGi framework starts our bundle
	 */
	public void start(BundleContext bc) throws Exception {
		bundleContext = bc;

		// first wait for the DAO-Service
		CookBookService cookBookService;
		ServiceReference<CookBookService> serviceReference = bundleContext
				.getServiceReference(CookBookService.class);
		cookBookService = bundleContext.getService(serviceReference);

		// create new Servlet
		ViewBookServlet cookBookViewServlet = new ViewBookServlet();
		cookBookViewServlet.setCookBookService(cookBookService);

		registerServiced = bundleContext.registerService(Servlet.class,
				cookBookViewServlet, null);
	}

	/**
	 * Called when the OSGi framework stops our bundle
	 */
	public void stop(BundleContext bc) throws Exception {
		registerServiced.unregister();
	}
}
