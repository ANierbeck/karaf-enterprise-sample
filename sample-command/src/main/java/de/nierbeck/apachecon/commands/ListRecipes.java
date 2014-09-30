package de.nierbeck.apachecon.commands;

import java.util.List;

import org.apache.karaf.shell.commands.Argument;
import org.apache.karaf.shell.commands.Command;
import org.apache.karaf.shell.commands.Option;
import org.apache.karaf.shell.console.OsgiCommandSupport;
import org.apache.karaf.shell.table.Col;
import org.apache.karaf.shell.table.ShellTable;

import de.nierbeck.apachecon.persistence.api.CookBookService;
import de.nierbeck.apachecon.persistence.entity.Recipe;

@Command(scope = "apachecon", name = "ListRecipes", description = "List recipes of the given book (by name or id)")
public class ListRecipes extends OsgiCommandSupport {

	@Option(name = "--no-format", description = "Disable table rendered output", required = false, multiValued = false)
	boolean noFormat;

	@Argument(index = 0, name = "name", description = "Name of CookBook", required = false, multiValued = false)
	private String name;

	@Argument(index = 1, name = "isbn", description = "ISBN of CookBook", required = false, multiValued = false)
	private String isbn;


	private CookBookService bookService;

	public void setBookService(CookBookService bookService) {
		this.bookService = bookService;
	}

	@Override
	protected Object doExecute() throws Exception {

		if (name == null && isbn == null) {
			System.out.println("Either name or isbn must be set");
			return null;
		}

		List<Recipe> allRecipes = null;

		if (name != null) {
			allRecipes = bookService.getBookByName(name).getRecipes();
		} else {
			allRecipes = bookService.getBookByIsbn(isbn).getRecipes();
		}

		ShellTable table = new ShellTable();
		table.column(new Col("ID"));
		table.column(new Col("Name"));
		table.column(new Col("Ingredients"));

		for (Recipe recipe : allRecipes) {

			table.addRow().addContent(recipe.getId(), recipe.getName(),
					recipe.getIngredients());

		}

		table.print(System.out, !noFormat);

		return null;
	}

}
