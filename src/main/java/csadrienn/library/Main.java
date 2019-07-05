package csadrienn.library;

import java.util.HashMap;
import java.util.Scanner;

import csadrienn.library.model.Book;
import csadrienn.library.model.Database;
import csadrienn.library.model.Edition;
import csadrienn.library.model.Member;

public class Main {

	private static HashMap<Integer, Member> members;
	private static HashMap<Integer, Book> books;
	private static HashMap<Integer, Edition> editions;
	private static Database db = new Database();
	private static Scanner scanner;
	private static final String MENU = "Menu: \n"
			+ "\tPress 1: add new data\n"
			+ "\tPress 2: delete data\n"
			+ "\tPress 3: update the database, because book was borrowed\n"
			+ "\tPress 4: update the database, because book was given back\n"
			+ "\tPress 5: check a member's data\n"
			+ "\tPress 6: check a book's data\n"
			+ "\tPress 7: update data\n"
			+ "\tPress 0: quit\n";
	
	public static void main(String[] args){
		
		if(!db.open()) {
			System.out.println("Can't open the database");
			return;
		}
		refreshLists();
		
//		if(!db.createTables()) {
//			System.out.println("Can't create the tables");
//			return;
//		}
		
//		Member student1 = new Member("ADAM SMITH", "S");
//		Member student2 = new Member("PETER JOHNSON", "S");
//		Member student3 = new Member("SUSAN HALL", "S");
//		Member teacher1 = new Member("THOMAS MILLER", "T");
//		Member teacher2 = new Member("VIVIAN GREEN", "T");
//		Member teacher3 = new Member("EVA WOOD", "T");
//		
//		Book prideAndPrejudice = new Book("PRIDE AND PREJUDICE", "JANE AUSTEN");
//		Book treasureIsland = new Book("TREASURE ISLAND", "ROBERT LOUIS STEVENSON");
//		Book _1984 = new Book("1984", "GEORGE ORWELL");
//		
//		
//		Edition prideAndPrejudice1 = new Edition("978-0141439518", "2002");
//		Edition prideAndPrejudice2 = new Edition("978-1909621657", "2016");
//		Edition prideAndPrejudice3 = new Edition("978-0174324997", "1992");
//		
//		Edition treasureIsland1 = new Edition("978-1603400268", "2008");
//		Edition treasureIsland2 = new Edition("978-0763644451", "2009");
//		
//		Edition _1984_1 = new Edition("978-0451524935", "1961");
//		Edition _1984_2 = new Edition("978-0679417392", "1992");
//		Edition _1984_3 = new Edition("978-9389053739", "2019");
//		
//		
//		db.insertMember(student1);
//		db.insertMember(student2);
//		db.insertMember(student3);
//		db.insertMember(teacher1);
//		db.insertMember(teacher2);
//		db.insertMember(teacher3);
//		
//		db.insertEdition(prideAndPrejudice1, prideAndPrejudice);
//		db.insertEdition(prideAndPrejudice2, prideAndPrejudice);
//		db.insertEdition(prideAndPrejudice3, prideAndPrejudice);
//		db.insertEdition(treasureIsland1, treasureIsland);
//		db.insertEdition(treasureIsland2, treasureIsland);
//		db.insertEdition(_1984_1, _1984);
//		db.insertEdition(_1984_2, _1984);
//		db.insertEdition(_1984_3, _1984);

//		refreshLists();	
		
		
		scanner = new Scanner(System.in);
		boolean quit = false;
		
		System.out.println(MENU);
		
		while(!quit) {
				String input = scanner.nextLine();
				if(input.matches("^\\d$")){
					switch(Integer.parseInt(input)) {
						case 1:
							addData();
							break;
						case 2:
							deleteFromDatabase();
							break;
						case 3:
							updateForBorrow();
							break;
						case 4:
							updateForBookBack();
							break;
						case 5:
							showMemberInfo();
							System.out.println("_________________________");
							System.out.println(MENU);
							break;
						case 6:
							showBookinfo();
							System.out.println("_________________________");
							System.out.println(MENU);
							break;
						case 7:
							updateData();
							break;
						case 0:
							System.out.println("Quit");
							quit = true;
							break;
							
						default:
							System.out.println("Invalid input, try again!");	
					}
				}else {
					System.out.println("Invalid input, try again!");
					
				}	
		}
		

		
		db.close();
		
	}	
	
	private static void refreshLists() {
		members = db.getMembers();
		books = db.getBooks();
		editions = db.getEditions();
	}
	
	
	private static void showMemberInfo() {
		Member member = chooseGivingMemberIDOrData();
		System.out.println(member);
	}
	
	private static void showBookinfo() {
		Book book = chooseGivingBookIDOrData();
		System.out.println(book);
		for(Edition edition: editions.values()) {
			if(edition.getBook() == book.getBook_id()) {
				System.out.println("\t" + edition);
			}
		}
	}
		
	
	private static void addData() {
		
		boolean back = false;
		String additionMenu = "Data addition menu:\n"
				+ "\tPress 1: add new member\n"
				+ "\tPress 2: add new edition\n"
				+ "\tPress 0: back to the main menu\n";
		System.out.println(additionMenu);
		
		while(!back) {
			String input = scanner.nextLine();
			if(input.matches("^\\d$")){
				switch(Integer.parseInt(input)) {
					case 1:
						addMember();
						System.out.println(additionMenu);
						break;
					case 2:
						addEdition();
						System.out.println(additionMenu);
						break;
					case 0:
						System.out.println("Go back\n");
						System.out.println(MENU);
						back = true;
						break;
					default:
						System.out.println("Invalid input, try again!");	
				}
			}
		}
	}
	
	private static void addMember() {
		String[] memberData = validateMemberData();
		db.insertMember(new Member(memberData[0], memberData[1]));
		refreshLists();	
		System.out.println("Added");
	}
	
	private static void addEdition() {
		
		String[] bookData =validateBookData();
		String[] editionData = validateEditionData(); 
		db.insertEdition(new Edition(editionData[0], editionData[1]), new Book(bookData[0], bookData[1]));;
		refreshLists();	
		System.out.println("Added");
	}

	

	private static void deleteFromDatabase() {
		boolean back = false;
		String deleteMenu = "Data delete menu:\n"
				+ "\tPress 1: delete member\n"
				+ "\tPress 2: delete edition\n"
				+ "\tPress 3: delete book with all its editions\n"
				+ "\tPress 0: back to the main menu\n";
		System.out.println(deleteMenu);
		
		while(!back) {
			String input = scanner.nextLine();
			if(input.matches("^\\d$")){
				switch(Integer.parseInt(input)) {
					case 1:
						deleteMember();
						System.out.println(deleteMenu);
						break;
					case 2:
						deleteEdition();
						System.out.println(deleteMenu);
						break;
					case 3:
						deleteBook();
						System.out.println(deleteMenu);
						break;
					case 0:
						System.out.println("Go back\n");
						System.out.println(MENU);
						back = true;
						break;
					default:
						System.out.println("Invalid input, try again!");	
				}
			}
		}
	}
	
	
	private static void deleteMember() {
		Member member = chooseGivingMemberIDOrData();
		if(member !=null) {
			System.out.println("Member with ID: " + member.getId() + " is deleted");
			db.deleteMember(member);
			refreshLists();	
		}else {
			System.out.println("Deleting unsuccessful. The member ID didn't exist in the database");
		}
		
	}
	
	private static Member askMemberID() {
		System.out.println("Give the member ID:");
		String id = scanner.nextLine();
		while(!id.matches("^\\d+$")) {
			System.out.println("Give a valid number!");
			id = scanner.nextLine();
			}
		return members.get(Integer.parseInt(id));
	}
	
	private static Member askMemberData() {
		
		String[] memberData = validateMemberData();
		
		int idCounter = 0;
		System.out.println("The possible ID(s):\n");
		for(Integer currentMemberID: members.keySet()) {
			if(members.get(currentMemberID).getName().equals(memberData[0]) && members.get(currentMemberID).getPosition().equals(memberData[1])){
				System.out.println("\t" + currentMemberID);
				idCounter++;
				}
		}
		if(idCounter == 0) {
			System.out.println("Deleting unsuccessful. The member ID didn't exist in the database");
			return null;
		}else {
			return askMemberID();
		}
	}
	

	private static void deleteEdition() {
		Edition edition = chooseGivingEditionIDOrData();
		if(edition !=null) {
			System.out.println("Edition with ID: " + edition.getEdition_id() + " is deleted");
			
			db.deleteEdition(edition);
			refreshLists();	
		}else {
			System.out.println("Deleting unsuccessful. The edition ID didn't exist in the database");
		}
		
	}
	
	private static Edition askEditionID() {
		System.out.println("Give the edition ID:\n");
		String id = scanner.nextLine();
		while(!id.matches("^\\d+$")) {
			System.out.println("Give a valid number!");
			id = scanner.nextLine();
		}
		return editions.get(Integer.parseInt(id));
	}
	
	private static Edition askEditionData() {
		
		String[] editionData = validateEditionData(); 
		
		int idCounter = 0;
		System.out.println("The possible ID(s):\n");
		for(Integer currentEditionID: editions.keySet()) {
			if(editions.get(currentEditionID).getIsbn().equals(editionData[0]) && editions.get(currentEditionID).getYear().equals(editionData[1])){
				System.out.println("\t" + currentEditionID);
				idCounter++;
				}
		}
		if(idCounter == 0) {
			System.out.println("Deleting unsuccessful. The edition ID didn't exist in the database");
			return null;
		}else {
			return askEditionID();
		}
	}
	
	
	private static void deleteBook() {
		Book book = chooseGivingBookIDOrData();
		
		if(book !=null) {
			System.out.println("Book with ID: " + book.getBook_id() + " is deleted");
						
			db.deleteBooks(book.getBook_id());
			refreshLists();	
		}else {
			System.out.println("Deleting unsuccessful. The book ID didn't exist in the database");
		}
		
	}
	
	private static Book askBookID() {
		System.out.println("Give the book ID:\n");
		String id = scanner.nextLine();
		while(!id.matches("^\\d+$")) {
			System.out.println("Give a valid number!");
			id = scanner.nextLine();
		}
		return books.get(Integer.parseInt(id));
	}
	
	private static Book askBookData() {
		
		String[] bookData = validateBookData(); 
		
		int idCounter = 0;
		System.out.println("The possible ID(s):\n");
		for(Integer currentBookID: editions.keySet()) {
			if(editions.get(currentBookID).getIsbn().equals(bookData[0]) && editions.get(currentBookID).getYear().equals(bookData[1])){
				System.out.println("\t" + currentBookID);
				idCounter++;
				}
		}
		if(idCounter == 0) {
			System.out.println("Deleting unsuccessful. The book ID didn't exist in the database");
			return null;
		}else {
			return askBookID();
		}
	}
	
	
	
	private static void updateForBorrow() {
		System.out.println("Give the member who borrows!");
		Member member = chooseGivingMemberIDOrData();
		
		if(member != null) {
			if(member.getCurrentNumOfBooks()<member.getMaxNumOfBooks()) {
				System.out.println("Give the edition that is borrowed!");
				Edition edition = chooseGivingEditionIDOrData();
				
				if(edition !=null) {
					if(edition.getAvailable().equalsIgnoreCase("Y")) {
						db.borrowBook(member, edition);
						refreshLists();
						System.out.println("Updated");
						System.out.println(MENU);
					}else {
						System.out.println("Process unsuccessful. This edition is not available at the moment\"");
					}
					
				}else {
					System.out.println("Process unsuccessful. The edition ID didn't exist in the database\"");
					
				}
				
			}else {
				System.out.println("Can't borrow. Member " + member.getId() + "-" + member.getName() + " reached the maximum number of books to borrow.");
			}
		}else {
			System.out.println("Process unsuccessful. The member ID didn't exist in the database");
		}
	}
	
	private static void updateForBookBack() {
		System.out.println("Give the member who brings back a book!");
		Member member = chooseGivingMemberIDOrData();
		
		if(member != null) {
			if(member.getCurrentNumOfBooks() !=0) {
				
				Edition edition = chooseGivingEditionIDOrData();
				
				if(edition != null) {
					if(edition.getMember() == member.getId()) {
						db.giveBackBook(member, edition);
						refreshLists();
						System.out.println("Updated");
						System.out.println(MENU);
						
					}else {
						System.out.println("Process unsuccessful. The member does not have this book");
					}
					
				}else {
					System.out.println("Process unsuccessful. The edition ID didn't exist in the database");
				}
				
			}else {
				System.out.println("Process unsuccessful. This member does not have books");
			}
		}else {
			System.out.println("Process unsuccessful. The member ID didn't exist in the database");
		}
		
	}
	
	
	private static void updateData() {
		boolean back = false;
		String updateMenu = "Data update menu:\n"
				+ "\tPress 1: update member\n"
				+ "\tPress 2: update edition\n"
				+ "\tPress 3: update book with all its editions\n"
				+ "\tPress 0: back to the main menu\n";
		System.out.println(updateMenu);
		
		while(!back) {
			String input = scanner.nextLine();
			if(input.matches("^\\d$")){
				switch(Integer.parseInt(input)) {
					case 1:
						updateMember();
						System.out.println(updateMenu);
						break;
					case 2:
						updateEdition();
						System.out.println(updateMenu);
						break;
					case 3:
						updateBook();
						System.out.println(updateMenu);
						break;
					case 0:
						System.out.println("Go back\n");
						System.out.println(MENU);
						back = true;
						break;
					default:
						System.out.println("Invalid input, try again!");	
				}
			}
		}
	}
	
	private static void updateMember() {
		Member member = chooseGivingMemberIDOrData();
		System.out.println("Press\n"
				+ "\t1 to update name"
				+ "\t2 to update position"
				+ "\t3 to both"
				+ "\t0 back to to main");
		
		String input = scanner.nextLine();
		while(true) {
			if(input.matches("^\\d$")){
				
				switch(Integer.parseInt(input)) {
					case 1:
						System.out.println("Current state:\n\t" + member);
						System.out.println("____________________________");
						System.out.println("Give the new name:");
						String name = scanner.nextLine().toUpperCase();
						db.updateMember(db.MEMBER_NAME, name, member);
						refreshLists();
						System.out.println("Updated");
						return;
					case 2:
						System.out.println("Current state:\n\t" + member);
						System.out.println("____________________________");
						System.out.println("Give the new position (T for teacher / S for student):");
						String position = scanner.nextLine().toUpperCase();
						
						while(!position.matches("(T|S)")) {
							System.out.println("Please press T or S letter!");
							position = scanner.nextLine().toUpperCase();
						}
						db.updateMember(db.MEMBER_POSITION, position, member);
						refreshLists();
						System.out.println("Updated");
						return;
						
					case 3:
						System.out.println("Current state:\n\t" + member);
						System.out.println("____________________________");
						System.out.println("Asking for new data:");
						String[] newData = validateMemberData();
						db.updateMember(db.MEMBER_NAME, newData[2], member);
						refreshLists();
						db.updateMember(db.MEMBER_POSITION, newData[1], member);
						refreshLists();
						System.out.println("Updated");
						return;
						
					case 0:
						System.out.println(MENU);
						return;
						
					default:
						System.out.println("Invalid input, try again!");	
						
				}
			}
		}
	}
	
	private static void updateEdition() {
		Edition edition = chooseGivingEditionIDOrData();
		System.out.println("Press\n"
				+ "\t1 to update isbn"
				+ "\t2 to update year"
				+ "\t3 to both"
				+ "\t0 back to to main");
		
		String input = scanner.nextLine();
		while(true) {
			if(input.matches("^\\d$")){
				
				switch(Integer.parseInt(input)) {
					case 1:
						System.out.println("Current state:\n\t" + edition);
						System.out.println("____________________________");
						System.out.println("Give the new isbn:");
						String isbn = scanner.nextLine();
						
						while(!isbn.matches("^\\d{3}-\\d{10}$")) {
							System.out.println("Please give a valid isbn! (e.g.:000-0000000000)");
							isbn = scanner.nextLine();
						}
						db.updateEdition(db.EDITION_ISBN, isbn, edition);
						refreshLists();
						System.out.println("Updated");
						return;
						
					case 2:
						System.out.println("Current state:\n\t" + edition);
						System.out.println("____________________________");
						
						System.out.println("Give the new year:");
						String year = scanner.nextLine();
						
						while(!year.matches("^\\d{4}$")) {
							System.out.println("Please give a valid year!");
							year = scanner.nextLine();
						}
						db.updateEdition(db.EDITION_YEAR, year, edition);
						refreshLists();
						System.out.println("Updated");
						return;
						
					case 3:
						System.out.println("Current state:\n\t" + edition);
						System.out.println("____________________________");
						System.out.println("Asking for new data:");
						String[] newData = validateEditionData();
						db.updateEdition(db.EDITION_ISBN, newData[2], edition);
						refreshLists();
						db.updateEdition(db.EDITION_YEAR, newData[1], edition);
						refreshLists();
						System.out.println("Updated");
						return;
						
					case 0:
						System.out.println(MENU);
						return;	
						
					default:
						System.out.println("Invalid input, try again!");	
						
				}
			}
		}
	}
	
	private static void updateBook() {
		Book book = chooseGivingBookIDOrData();
		System.out.println("Press\n"
				+ "\t1 to update title"
				+ "\t2 to update author"
				+ "\t3 to both"
				+ "\t0 back to to main");
		
		String input = scanner.nextLine();
		while(true) {
			if(input.matches("^\\d$")){
				
				switch(Integer.parseInt(input)) {
					case 1:
						System.out.println("Current state:\n\t" + book);
						System.out.println("____________________________");
						System.out.println("Give the new title:");
						String title = scanner.nextLine();
						
						db.updateBook(db.BOOK_TITLE, title, book);
						refreshLists();
						System.out.println("Updated");
						return;
						
					case 2:
						System.out.println("Current state:\n\t" + book);
						System.out.println("____________________________");
						
						System.out.println("Give the new author:");
						String author = scanner.nextLine();
						db.updateBook(db.BOOK_AUTHOR, author, book);
						System.out.println("Updated");
						refreshLists();
						return;
						
					case 3:
						System.out.println("Current state:\n\t" + book);
						System.out.println("____________________________");
						System.out.println("Asking for new data:");
						String[] newData = validateBookData();
						db.updateBook(db.BOOK_TITLE, newData[2], book);
						refreshLists();
						db.updateBook(db.BOOK_AUTHOR, newData[1], book);
						refreshLists();
						System.out.println("Updated");
						return;
						
					case 0:
						System.out.println(MENU);
						return;	
						
					default:
						System.out.println("Invalid input, try again!");	
						
				}
			}
		}
	}
	
	
	//give the option whether the user gives id or data (in this case show the ids to choose)
	private static Member chooseGivingMemberIDOrData() {
		
		System.out.println("Press\n\t1 to give member ID or\n\t2 to give name and position(T/S) or\n\tpress 0 to go back to the main menu");
		String input = scanner.nextLine();
		boolean back = false;
		Member member = null;		
		while(!back) {
			if(input.matches("^\\d$")){
				
				switch(Integer.parseInt(input)) {
					case 1:
						member = askMemberID();
						return member;
					case 2:
						member = askMemberData();
						return member;
						
					case 0:
						System.out.println("Go back\n");
						System.out.println(MENU);
						back = true;
						break;
					default:
						System.out.println("Invalid input, try again!");	
						
				}
			}
		} return member;
	}
	//give the option whether the user gives id or data (in this case show the ids to choose)
	private static Edition chooseGivingEditionIDOrData() {
		System.out.println("Press\n\t1 to give edition ID or\n\t2 to give isbn(dd-dddddddddd) and year or\n\t0 to go back");
		String input = scanner.nextLine();
		boolean back = false;
		Edition edition = null;
		while(!back) {
			if(input.matches("^\\d$")){
				
				switch(Integer.parseInt(input)) {
					case 1:
						edition = askEditionID();
						return edition;	
						
					case 2:
						edition = askEditionData();
						return edition;	
					case 0:
						System.out.println("Go back\n");
						System.out.println(MENU);
						back = true;
						break;
					default:
						System.out.println("Invalid input, try again!");	
				}
			}	
		}return edition;	
	}
	//give the option whether the user gives id or data (in this case show the ids to choose)
	private static Book chooseGivingBookIDOrData() {
		System.out.println("Press\n\t1 to give book ID or\n\t2 to give title and author or\n\t0 to go back");
		String input = scanner.nextLine();
		boolean back = false;
		Book book = null;
		while(!back) {
			if(input.matches("^\\d$")){
				
				switch(Integer.parseInt(input)) {
					case 1:
						book = askBookID();
						return book;	
						
					case 2:
						book = askBookData();
						return book;	
					case 0:
						System.out.println("Go back\n");
						System.out.println(MENU);
						back = true;
						break;
					default:
						System.out.println("Invalid input, try again!");	
				}
			}	
		}return book;
		
	}
	
	//check if the given data is valid for the database values
	private static String[] validateMemberData() {

		String[] memberData = new String[2];
		
		System.out.println("Give member's name:");
		String name = scanner.nextLine().toUpperCase();
		System.out.println("Give the member's position (T for teacher / S for student):");
		String position = scanner.nextLine().toUpperCase();
		
		while(!position.matches("(T|S)")) {
			System.out.println("Please press T or S letter!");
			position = scanner.nextLine().toUpperCase();
		}
		memberData[0] = name;
		memberData[1] = position;
		return memberData;
		
	}
	//check if the given data is valid for the database values
	private static String[] validateEditionData() {
		String[] memberData = new String[2]; 
		
		System.out.println("Give isbn:");
		String isbn = scanner.nextLine();
		
		while(!isbn.matches("^\\d{3}-\\d{10}$")) {
			System.out.println("Please give a valid isbn! (e.g.:000-0000000000)");
			isbn = scanner.nextLine();
		}
		System.out.println("Give the edition's year:");
		String year = scanner.nextLine();
		
		while(!year.matches("^\\d{4}$")) {
			System.out.println("Please give a valid year!");
			year = scanner.nextLine();
		}
		memberData[0]=isbn;
		memberData[1]=year;
		return memberData;
		
	}
	//check if the given data is valid for the database values
	private static String[] validateBookData() {
		String[] bookData = new String[2]; 
		System.out.println("Give the new edition's title:");
		String title = scanner.nextLine().toUpperCase();
		
		System.out.println("Give the new edition's author:");
		String author = scanner.nextLine().toUpperCase();
		bookData[0] = title;
		bookData[1] = author;
		return bookData;
	}

	
	
}

