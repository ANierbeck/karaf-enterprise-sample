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

@WebServlet(urlPatterns = "/recipes")
public class ViewRecipesServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	@OsgiService
	CookBookService cookBookService;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		service(req, resp);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		service(req, resp);
	}

	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");

		if (id != null) {
			request.getSession().setAttribute("selectedBookID", id);
		} else {
			id = (String) request.getSession().getAttribute("selectedBookID");
		}

		ViewModel viewModel = new ViewModel();
		RequestDispatcher disp;
		if (id != null) {
			Book book = cookBookService.getBookById(Long.parseLong(id));

			viewModel.setRecipes(book.getRecipes());
			disp = getServletContext().getRequestDispatcher(
					"/jsp/listrecipes.jsp");

		} else {
			disp = getServletContext().getRequestDispatcher(
					"/jsp/listbooks.jsp");

			for (Book book : cookBookService.getAllBooks())
				viewModel.addBook(book);

		}
		request.setAttribute("app.viewModel", viewModel);
		disp.forward(request, response);
	}
}
