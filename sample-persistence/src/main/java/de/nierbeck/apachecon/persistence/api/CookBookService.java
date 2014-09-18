package de.nierbeck.apachecon.persistence.api;

import de.nierbeck.apachecon.persistence.entity.Book;
import de.nierbeck.apachecon.persistence.entity.Recipe;

public interface CookBookService {

	Book getBookByName(String name);

	Book getBookByIsbn(String isbn);

	void addRecipeToCookBook(Book book, Recipe recipe);

	boolean createBook(Book book);

}
