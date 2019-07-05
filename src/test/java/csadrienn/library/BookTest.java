package csadrienn.library;

import csadrienn.library.model.Book;
import org.junit.Assert;
import org.junit.Test;

public class BookTest {

    @Test
    public void shouldMatchTitleAndAuthor() {

        Book book = new Book("hobbit", "tolkien");

        String title = book.getTitle();
        String author = book.getAuthor();

        Assert.assertTrue("hobbit".equalsIgnoreCase(title));
        Assert.assertTrue("tolkien".equalsIgnoreCase(author));

    }
}
