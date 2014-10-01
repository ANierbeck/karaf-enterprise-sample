package de.nierbeck.apachecon.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import de.nierbeck.apachecon.persistence.entity.Book;
import de.nierbeck.apachecon.persistence.entity.Recipe;

public class ViewModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<Book> books;

	private List<Recipe> recipes;

	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}

	public void addBook(Book book) {
		if (this.books == null)
			books = new ArrayList<Book>();
		books.add(book);
	}

	public void setRecipes(List<Recipe> recipes) {
		this.recipes = recipes;
	}

	public List<Recipe> getRecipes() {
		return recipes;
	}

}
