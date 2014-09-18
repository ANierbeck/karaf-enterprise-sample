package de.nierbeck.apachecon.commands;

import org.apache.karaf.shell.commands.Argument;
import org.apache.karaf.shell.commands.Command;
import org.apache.karaf.shell.commands.Option;

import de.nierbeck.apachecon.persistence.api.CookBookService;
import de.nierbeck.apachecon.persistence.entity.Book;

@Command(scope = "apachecon", name = "CreateBook", description = "Create book")
public class CreateBook {

	@Option(name = "-i", aliases = { "--isbn" }, description = "The ISBN of the book", required = false, multiValued = false)
	private String isbn;

	@Argument(name = "name", description = "Name of CookBook", required = true, multiValued = false)
	private String name;

	private CookBookService bookService;

	public CookBookService getBookService() {
		return bookService;
	}

	public void setBookService(CookBookService bookService) {
		this.bookService = bookService;
	}

	protected Object doExecute() throws Exception {

		Book book = new Book();
		book.setName(name);
		book.setIsbn(isbn);

		boolean bookCreated = bookService.createBook(book);

		if (bookCreated)
			System.out.println("Book created");

		return null;
	}

}
