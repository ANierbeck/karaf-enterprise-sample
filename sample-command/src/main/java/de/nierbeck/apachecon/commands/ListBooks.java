package de.nierbeck.apachecon.commands;

import java.util.List;

import org.apache.karaf.shell.commands.Command;
import org.apache.karaf.shell.commands.Option;
import org.apache.karaf.shell.console.OsgiCommandSupport;
import org.apache.karaf.shell.table.Col;
import org.apache.karaf.shell.table.ShellTable;

import de.nierbeck.apachecon.persistence.api.CookBookService;
import de.nierbeck.apachecon.persistence.entity.Book;

@Command(scope = "apachecon", name = "ListBooks", description = "List books available")
public class ListBooks extends OsgiCommandSupport {

	@Option(name = "--no-format", description = "Disable table rendered output", required = false, multiValued = false)
	boolean noFormat;

	private CookBookService bookService;

	public void setBookService(CookBookService bookService) {
		this.bookService = bookService;
	}

	@Override
	protected Object doExecute() throws Exception {
		List<Book> allBooks = bookService.getAllBooks();

		ShellTable table = new ShellTable();
		table.column(new Col("ID"));
		table.column(new Col("Name"));
		table.column(new Col("ISBN"));
		table.column(new Col("Recipes"));

		for (Book book : allBooks) {

			table.addRow().addContent(book.getId(), book.getName(),
					book.getIsbn(),
					book.getRecipes() != null ? book.getRecipes().size() : "0");

		}

		table.print(System.out, !noFormat);

		return null;
	}

}
