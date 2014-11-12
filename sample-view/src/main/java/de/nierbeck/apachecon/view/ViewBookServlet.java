package de.nierbeck.apachecon.view;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ops4j.pax.cdi.api.OsgiService;

import de.nierbeck.apachecon.persistence.api.CookBookService;
import de.nierbeck.apachecon.persistence.entity.Book;

@WebServlet(urlPatterns = "/book")
public class ViewBookServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	@OsgiService
	private CookBookService cookBookService;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		service(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		service(req, resp);
	}

	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		ViewModel viewModel = new ViewModel();
		for (Book book : cookBookService.getAllBooks())
			viewModel.addBook(book);

		RequestDispatcher disp;
		disp = getServletContext().getRequestDispatcher("/jsp/listbooks.jsp");
		request.setAttribute("app.viewModel", viewModel);
		disp.forward(request, response);
	}

	public void setCookBookService(CookBookService cookBookService) {
		this.cookBookService = cookBookService;
	}
}
