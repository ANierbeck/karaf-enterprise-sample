package de.nierbeck.apachecon.commands;

import org.apache.karaf.shell.commands.Argument;
import org.apache.karaf.shell.commands.Command;
import org.apache.karaf.shell.console.OsgiCommandSupport;

import de.nierbeck.apachecon.persistence.api.CookBookService;
import de.nierbeck.apachecon.persistence.entity.Book;
import de.nierbeck.apachecon.persistence.entity.Recipe;

@Command(scope = "apachecon", name = "AddRecipe", description = "Add recipe to book")
public class AddRecipe extends OsgiCommandSupport {

	@Argument(index = 0, name = "bookId", description = "Id of CookBook", required = true, multiValued = false)
	private Long id;

	@Argument(index = 1, name = "recipeName", description = "Name of the Recipe to add to book", required = true, multiValued = false)
	private String recipeName;

	@Argument(index = 2, name = "ingredients", description = "Ingredients of the Recipe", required = true, multiValued = true)
	private String ingredients;

	private CookBookService bookService;

	public void setBookService(CookBookService bookService) {
		this.bookService = bookService;
	}

	@Override
	protected Object doExecute() throws Exception {

		Book book = bookService.getBookById(id);

		Recipe recipe = new Recipe();
		recipe.setName(recipeName);
		recipe.setIngredients(ingredients);

		bookService.addRecipeToCookBook(book, recipe);
		
		System.out.println("added Recipe to book: " + book.getName());

		return null;
	}

}
