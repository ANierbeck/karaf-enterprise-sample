package de.nierbeck.apachecon.commands;

import org.apache.karaf.shell.commands.Argument;
import org.apache.karaf.shell.commands.Command;
import org.apache.karaf.shell.commands.Option;
import org.apache.karaf.shell.console.OsgiCommandSupport;

import de.nierbeck.apachecon.persistence.api.CookBookService;
import de.nierbeck.apachecon.persistence.entity.Book;

@Command(scope = "apachecon", name = "CreateBook", description = "Create book")
public class CreateBook extends OsgiCommandSupport {

	@Option(name = "-i", aliases = { "--isbn" }, description = "The ISBN of the book", required = false, multiValued = false)
	private String isbn;

	@Argument(name = "name", description = "Name of CookBook", required = true, multiValued = false)
	private String name;

	private CookBookService bookService;

	public void setBookService(CookBookService bookService) {
		this.bookService = bookService;
	}

	protected Object doExecute() throws Exception {

		Book book = new Book();
		book.setName(name);
		book.setIsbn(isbn);

		bookService.createBook(book);

		System.out.println("Book created");

		return null;
	}

}
