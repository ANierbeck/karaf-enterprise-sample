package de.nierbeck.apachecon.persistence.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import de.nierbeck.apachecon.persistence.api.CookBookService;
import de.nierbeck.apachecon.persistence.entity.Book;
import de.nierbeck.apachecon.persistence.entity.Recipe;

public class CookBookServiceImpl implements CookBookService {

	EntityManager em;

	void close() {
		em.close();
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}

	public Book getBookByName(String name) {
		TypedQuery<Book> createQuery = em.createQuery(
				"SELECT book FROM Book book where book.name=:name", Book.class);
		createQuery.setParameter("name", name);

		return createQuery.getSingleResult();
	}

	public Book getBookByIsbn(String isbn) {
		TypedQuery<Book> createQuery = em.createQuery(
				"SELECT book FROM Book book WHERE book.isbn=:isbn", Book.class);
		createQuery.setParameter("isbn", isbn);

		return createQuery.getSingleResult();
	}

	@Override
	public Book getBookById(Long id) {
		TypedQuery<Book> query = em.createQuery(
				"SELECT book FROM Book book WHERE book.id = :id", Book.class);
		query.setParameter("id", id);

		Book book = null;
		try {
			book = query.getSingleResult();
		} catch (NoResultException nre) {
			// NOOP
		}

		return book;
	}

	public void addRecipeToCookBook(Book book, Recipe recipe) {

		Book bookEntity = em.find(Book.class, book.getId());
		bookEntity.getRecipes().add(recipe);

	}

	public void createBook(Book book) {
		em.persist(book);
	}

	public List<Book> getAllBooks() {
		TypedQuery<Book> createQuery = em.createQuery(
				"SELECT book FROM Book book ORDER BY book.name", Book.class);
		return createQuery.getResultList();
	}

	public List<Book> getAllBooksDetached() {
		List<Book> allBooks = getAllBooks();
		for (Book book : allBooks) {
			em.detach(book);
		}
		return allBooks;
	}

}
