package csadrienn.library.model;

public class Member {

	private int id;
	private String name;
	private String position;
	private int maxNumOfBooks;
	private int currentNumOfBooks;
	
	public Member() {
		
	}
	public Member(String name, String position) {
		this.name = name;
		this.position = position;
		currentNumOfBooks = 0;
		if(position.equalsIgnoreCase("t")) maxNumOfBooks = 10;
		else maxNumOfBooks = 5;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}
	

	public int getCurrentNumOfBooks() {
		return currentNumOfBooks;
	}

	public void setCurrentNumOfBooks(int currentNumOfBooks) {
		this.currentNumOfBooks = currentNumOfBooks;
	}

	public int getMaxNumOfBooks() {
		return maxNumOfBooks;
	}

	@Override
	public String toString() {
		return "Member [id=" + id + ", name=" + name + ", position=" + position + ", currentNumOfBooks="
				+ currentNumOfBooks + ", maxNumOfBooks=" + maxNumOfBooks + "]";
	}
	
	
}
