package csadrienn.library.model;

import java.util.Date;

public class Edition {
	
	private int edition_id;
	private String isbn;
	private String year;
	private int member;
	private String available;
	private Date due;
//	private int late;
	private int book;


	public Edition() {
		
	}
	
	public Edition(String isbn, String year) {
		this.isbn = isbn;
		this.year = year;
		this.available = "Y";
	}

	public int getEdition_id() {
		return edition_id;
	}

	public void setEdition_id(int edition_id) {
		this.edition_id = edition_id;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public int getMember() {
		return member;
	}

	public void setMember(int member) {
		this.member = member;
	}

	public String getAvailable() {
		return available;
	}

	public void setAvailable(String available) {
		this.available = available;
	}

	public Date getDue() {
		return due;
	}

	public void setDue(Date due) {
		this.due = due;
	}

//	public int getLate() {
//		return late;
//	}
//
//	public void setLate(int late) {
//		this.late = late;
//	}
	
	public int getBook() {
		return book;
	}

	public void setBook(int book) {
		this.book = book;
	}

	@Override
	public String toString() {
		return "Edition [edition_id=" + edition_id + ", isbn=" + isbn + ", year=" + year + ", member=" + member
				+ ", available=" + available + ", due=" + due + ", book=" + book + "]";
	}

	
}
