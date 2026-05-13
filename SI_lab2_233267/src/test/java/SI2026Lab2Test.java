import org.junit.Test;
import java.util.List;
import static org.junit.Assert.*;

public class SI2026Lab2Test {

    @Test
    public void testSearchBookByTitleFound() {
        Library library = new Library();
        library.addBook(new Book("Clean Code", "Robert C. Martin", "Programming"));
        assertNotNull(library.searchBookByTitle("Clean Code"));
    }

    @Test
    public void testSearchBookByTitleBorrowedReturnsNull() {
        Library library = new Library();
        library.addBook(new Book("Clean Code", "Robert C. Martin", "Programming"));
        library.borrowBook("Clean Code", "Robert C. Martin");
        assertNull(library.searchBookByTitle("Clean Code"));
    }

    @Test
    public void testSearchBookByTitleNotFound() {
        Library library = new Library();
        library.addBook(new Book("Clean Code", "Robert C. Martin", "Programming"));
        assertNull(library.searchBookByTitle("Unknown Title"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSearchBookByTitleEmpty() {
        Library library = new Library();
        library.searchBookByTitle("");
    }

    @Test
    public void testBorrowBook() {
        Library library = new Library();
        library.addBook(new Book("The Hobbit", "J.R.R. Tolkien", "Fantasy"));
        library.borrowBook("The Hobbit", "J.R.R. Tolkien");
        assertNull(library.searchBookByTitle("The Hobbit"));
    }

    @Test(expected = RuntimeException.class)
    public void testBorrowAlreadyBorrowedBook() {
        Library library = new Library();
        library.addBook(new Book("The Hobbit", "J.R.R. Tolkien", "Fantasy"));
        library.borrowBook("The Hobbit", "J.R.R. Tolkien");
        library.borrowBook("The Hobbit", "J.R.R. Tolkien");
    }

    @Test(expected = RuntimeException.class)
    public void testBorrowNonExistentBook() {
        Library library = new Library();
        library.borrowBook("Nonexistent", "Nobody");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBorrowBookEmptyTitle() {
        Library library = new Library();
        library.borrowBook("", "Author");
    }

    @Test
    public void testReturnBook() {
        Library library = new Library();
        library.addBook(new Book("1984", "George Orwell", "Dystopian"));
        library.borrowBook("1984", "George Orwell");
        library.returnBook("1984");
        assertNotNull(library.searchBookByTitle("1984"));
    }

    @Test(expected = RuntimeException.class)
    public void testReturnBookNotBorrowed() {
        Library library = new Library();
        library.addBook(new Book("1984", "George Orwell", "Dystopian"));
        library.returnBook("1984");
    }

    @Test(expected = RuntimeException.class)
    public void testReturnNonExistentBook() {
        Library library = new Library();
        library.returnBook("Ghost Book");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testReturnBookEmptyTitle() {
        Library library = new Library();
        library.returnBook("");
    }

    @Test
    public void testCountBooksByGenre() {
        Library library = new Library();
        library.addBook(new Book("Clean Code", "Robert C. Martin", "Programming"));
        library.addBook(new Book("Effective Java", "Joshua Bloch", "Programming"));
        library.addBook(new Book("The Hobbit", "J.R.R. Tolkien", "Fantasy"));
        assertEquals(2, library.countBooksByGenre("Programming"));
        assertEquals(1, library.countBooksByGenre("Fantasy"));
    }

    @Test
    public void testCountAvailableBooks() {
        Library library = new Library();
        library.addBook(new Book("Clean Code", "Robert C. Martin", "Programming"));
        library.addBook(new Book("The Hobbit", "J.R.R. Tolkien", "Fantasy"));
        library.borrowBook("The Hobbit", "J.R.R. Tolkien");
        assertEquals(1, library.countAvailableBooks());
    }

    @Test
    public void borrowBookEveryBranchTest() {
        Library library = new Library();
        library.addBook(new Book("The Hobbit", "J.R.R. Tolkien", "Fantasy"));

        try {
            library.borrowBook("", "J.R.R. Tolkien");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {}

        try {
            library.borrowBook("The Hobbit", "");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {}

        library.borrowBook("The Hobbit", "J.R.R. Tolkien");
        assertNull(library.searchBookByTitle("The Hobbit"));

        try {
            library.borrowBook("The Hobbit", "J.R.R. Tolkien");
            fail("Expected RuntimeException");
        } catch (RuntimeException e) {}

        try {
            library.borrowBook("Unknown", "Nobody");
            fail("Expected RuntimeException");
        } catch (RuntimeException e) {}
    }

    @Test
    public void searchBookEveryStatementTest() {
        Library library = new Library();
        library.addBook(new Book("Clean Code", "Robert C. Martin", "Programming"));

        // Test 1: empty title → IllegalArgumentException
        try {
            library.searchBookByTitle("");
            fail("Expected IllegalArgumentException for empty title");
        } catch (IllegalArgumentException e) {
            // expected
        }

        // Test 2: book found and not borrowed → returns list containing the book
        List<Book> result = library.searchBookByTitle("Clean Code");
        assertNotNull(result);
        assertEquals(1, result.size());

        // Test 3: book found but borrowed → returns null
        library.borrowBook("Clean Code", "Robert C. Martin");
        assertNull(library.searchBookByTitle("Clean Code"));

        // Test 4: book not found → returns null
        assertNull(library.searchBookByTitle("Unknown Title"));
    }

    @Test
    public void searchBookMultipleConditionTest() {
        Library library1 = new Library();
        library1.addBook(new Book("Clean Code", "Robert C. Martin", "Programming"));
        assertNotNull(library1.searchBookByTitle("Clean Code"));

        Library library2 = new Library();
        library2.addBook(new Book("Clean Code", "Robert C. Martin", "Programming"));
        library2.borrowBook("Clean Code", "Robert C. Martin");
        assertNull(library2.searchBookByTitle("Clean Code"));

        Library library3 = new Library();
        library3.addBook(new Book("Clean Code", "Robert C. Martin", "Programming"));
        assertNull(library3.searchBookByTitle("Unknown"));

        Library library4 = new Library();
        library4.addBook(new Book("Clean Code", "Robert C. Martin", "Programming"));
        library4.borrowBook("Clean Code", "Robert C. Martin");
        assertNull(library4.searchBookByTitle("Unknown"));
    }

    @Test
    public void borrowBookMultipleConditionTest() {
        Library library = new Library();
        library.addBook(new Book("The Hobbit", "J.R.R. Tolkien", "Fantasy"));

        try {
            library.borrowBook("", "");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {}

        try {
            library.borrowBook("", "J.R.R. Tolkien");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {}

        try {
            library.borrowBook("The Hobbit", "");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {}

        library.borrowBook("The Hobbit", "J.R.R. Tolkien");
        assertNull(library.searchBookByTitle("The Hobbit"));
    }
}
