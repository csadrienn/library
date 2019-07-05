package csadrienn.library.model;

public class Book {

	private int book_id;
	private String title;
	private String author;
	private int availableEditions;
	private int numOfEditions;

	public Book() {
		
	}
	public Book(String title, String author) {
		this.title = title;
		this.author = author;
	}

	public int getBook_id() {
		return book_id;
	}

	public void setBook_id(int book_id) {
		this.book_id = book_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
	public int getNumOfEditions() {
		return numOfEditions;
	}
	public void setNumOfEditions(int numOfEditions) {
		this.numOfEditions = numOfEditions;
	}
	
	public int getAvailableEditions() {
		return availableEditions;
	}
	public void setAvailableEditions(int availableEditions) {
		this.availableEditions = availableEditions;
	}
	@Override
	public String toString() {
		return "Book [book_id=" + book_id + ", title=" + title + ", author=" + author + ", numOfEditions="
				+ numOfEditions + "]";
	}
	
	
}
