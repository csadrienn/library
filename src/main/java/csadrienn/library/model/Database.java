package csadrienn.library.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class Database {
	// JDBC driver name and database URL
	public static final String DB_NAME = "library";
	public static final String CONN_STRING = "jdbc:mysql://localhost/" + DB_NAME + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	//  Database credentials
	public static final String USER = "root";
	public static final String PASSWORD = "RTpass";
	
	//members constants
	public static final String TABLE_MEMBERS = "members";
	public static final String MEMBER_ID = "member_id";
	public static final String MEMBER_NAME = "name";
	public static final String MEMBER_POSITION = "position";
	public static final String MEMBER_NUM_OF_BOOKS = "num_of_books";
	
	//members column indexes
	public static final int INDEX_MEMBER_ID = 1;
	public static final int INDEX_MEMBER_NAME = 2;
	public static final int INDEX_MEMBER_POSITION = 3;
	public static final int INDEX_MEMBER_NUM_OF_BOOKS = 4;
	
	//books constants
	public static final String TABLE_BOOKS = "books";
	public static final String BOOK_ID = "book_id";
	public static final String BOOK_TITLE = "title";
	public static final String BOOK_AUTHOR = "author";
	public static final String BOOK_AVAILABLE = "available";
	public static final String BOOK_ALL = "all_editions";
	
	//books column indexes
	public static final int INDEX_BOOK_ID = 1;
	public static final int INDEX_BOOK_TITLE = 2;
	public static final int INDEX_BOOK_AUTHOR = 3;
	public static final int INDEX_BOOK_AVAILABLE = 4;
	public static final int INDEX_BOOK_ALL = 5;
	
	//editions constants
	public static final String TABLE_EDITIONS = "editions";
	public static final String EDITION_ID = "edition_id";
	public static final String EDITION_ISBN = "isbn";
	public static final String EDITION_YEAR = "year";
	public static final String EDITION_BOOK = "book";
	public static final String EDITION_AVAILABLE = "available";
	public static final String EDITION_MEMBER = "member";
	public static final String EDITION_DUE = "due";
//	public static final String EDITION_LATE = "late";
	
	//editions column indexes
	public static final int INDEX_EDITION_ID = 1;
	public static final int INDEX_EDITION_ISBN = 2;
	public static final int INDEX_EDITION_YEAR = 3;
	public static final int INDEX_EDITION_BOOK = 4;
	public static final int INDEX_EDITION_AVAILABLE = 5;
	public static final int INDEX_EDITION_MEMBER = 6;
	public static final int INDEX_EDITION_DUE = 7;
//	public static final int INDEX_EDITION_LATE = 8;
	
	//sql string constants
	public static final String INCREASE = " + ";
	public static final String DECREASE = " - ";
	public static final String YES = "Y";
	public static final String NO = "N";
	public static final int DUE = 2;
	
	public static final String INSERT_BOOKS = "INSERT INTO " + TABLE_BOOKS 
			+ " (" + BOOK_TITLE + ", " + BOOK_AUTHOR + ", " + BOOK_AVAILABLE + ", " +  BOOK_ALL + ") VALUES (?, ?, ?, ?)";
	
	public static final String INSERT_EDITION = "INSERT INTO " + TABLE_EDITIONS 
			+ " (" + EDITION_ISBN + ", " + EDITION_YEAR + ", " + EDITION_BOOK + ", " + EDITION_AVAILABLE + ", " + EDITION_MEMBER + ") VALUES (?, ?, ?, ?, ?)";
	
	public static final String QUERY_BOOKS_ID = "SELECT " + BOOK_ID + " FROM " + TABLE_BOOKS 
						+ " WHERE " + " (" + BOOK_TITLE + " = ? AND " +  BOOK_AUTHOR + " = ?)";
	public static final String QUERY_BOOKS_EDITION_NUM = "SELECT " + BOOK_ALL + " FROM " + TABLE_BOOKS 
						+ " WHERE " + BOOK_ID + " = ?";
	
	public static final String UPDATE_NUM_OF_EDITIONS_PART1 = "UPDATE " + TABLE_BOOKS + " SET " + BOOK_AVAILABLE + " = " + BOOK_AVAILABLE;
	public static final String UPDATE_NUM_OF_EDITIONS_PART2 = "1, " + BOOK_ALL + " = " + BOOK_ALL;
	public static final String UPDATE_NUM_OF_EDITIONS_PART3 = "1  WHERE " +  BOOK_ID + " = ?";
	
	public static final String DELETE_EDITION = "DELETE FROM " + TABLE_EDITIONS + " WHERE " + EDITION_ID + " = ?";
	
	
	public static final String UPDATE_MEMBER_BOOKNUM_PART1_ = "UPDATE " + TABLE_MEMBERS + " SET " + MEMBER_NUM_OF_BOOKS + " = " + MEMBER_NUM_OF_BOOKS;
	public static final String UPDATE_MEMBER_BOOKNUM_PART2 = "1  WHERE " +  MEMBER_ID + " = ?";
	
	public static final String UPDATE_EDITION = "UPDATE " + TABLE_EDITIONS + " SET " 
						+ EDITION_AVAILABLE + " = ?, "  +  EDITION_MEMBER + " = ?, " + EDITION_DUE + " = ? "
						+ "WHERE " +  EDITION_ID + " = ?";
	

	private Connection conn = null;
	private Statement statement = null;
	PreparedStatement updateMember = null;
	
	PreparedStatement updateEdition = null;
	private PreparedStatement insertIntoBooks;
	private PreparedStatement insertIntoEditions;
	private PreparedStatement queryBooksID;
	private PreparedStatement queryBooksEditionNum;
	private PreparedStatement updateBooksEditionNum;
	private PreparedStatement deleteEditions;
	
	
	public boolean open() {
		try {
			conn = DriverManager.getConnection(CONN_STRING, USER, PASSWORD);
			insertIntoBooks = conn.prepareStatement(INSERT_BOOKS, Statement.RETURN_GENERATED_KEYS);
			insertIntoEditions = conn.prepareStatement(INSERT_EDITION);
			queryBooksID = conn.prepareStatement(QUERY_BOOKS_ID);
			queryBooksEditionNum = conn.prepareStatement(QUERY_BOOKS_EDITION_NUM);
			deleteEditions = conn.prepareStatement(DELETE_EDITION);
			return true;
		}catch(SQLException e) {
			System.out.println("Couldn't connect to the database " + e.getMessage());
			return false;
		}
	}
	
	public void close() {
		try {
			
			if(insertIntoBooks != null) {
				insertIntoBooks.close();
			}
			
			if(insertIntoEditions != null) {
				insertIntoEditions.close();
			}
			
			if(queryBooksID != null) {
				queryBooksID.close();
			}
			
			if(updateBooksEditionNum != null) {
				updateBooksEditionNum.close();
			}
			if(queryBooksEditionNum != null) {
				queryBooksEditionNum.close();
			}
			
			if(deleteEditions != null) {
				deleteEditions.close();
			}
			
			if(conn != null) {
				conn.close();
			}
		}catch(SQLException e) {
			System.out.println("Error in closing  " + e.getMessage());
		}
	}
	
	public boolean createTables() {
		try {
			statement = conn.createStatement();
			statement.execute("CREATE TABLE IF NOT EXISTS " + TABLE_MEMBERS + " ("
					+ MEMBER_ID  + " INT AUTO_INCREMENT, "
					+ MEMBER_NAME + " VARCHAR(60) NOT NULL, "
					+ MEMBER_POSITION + " VARCHAR(1) NOT NULL, "
					+ MEMBER_NUM_OF_BOOKS + " TINYINT, "
					+ "PRIMARY KEY (" + MEMBER_ID + "))" );
		
			statement.execute("CREATE TABLE IF NOT EXISTS " + TABLE_BOOKS + " ("
					+ BOOK_ID  + " INT AUTO_INCREMENT, "
					+ BOOK_TITLE + " VARCHAR(100) NOT NULL, "
					+ BOOK_AUTHOR + " VARCHAR(60) NOT NULL, "
					+ BOOK_AVAILABLE + " TINYINT, "
				    + BOOK_ALL + " TINYINT, "
					+ "PRIMARY KEY (" + BOOK_ID + "))" );
								
			statement.execute("CREATE TABLE IF NOT EXISTS " + TABLE_EDITIONS + " ("
					+ EDITION_ID  + " INT AUTO_INCREMENT, "
					+ EDITION_ISBN + " VARCHAR(17) NOT NULL, "
					+ EDITION_YEAR + " VARCHAR(4) NOT NULL, "
					+ EDITION_BOOK + " INT, "
					+ EDITION_AVAILABLE + " VARCHAR(1) , "
					+ EDITION_MEMBER + " INT, "
					+ EDITION_DUE + " DATE, "
					+ "PRIMARY KEY (" + EDITION_ID + "), "
					+ "FOREIGN KEY (" + EDITION_BOOK + ") "
							+ "REFERENCES " + TABLE_BOOKS + " (" +  BOOK_ID + ") "
							+ "ON DELETE CASCADE )");
						
			return true;
		}catch(SQLException e) {
			System.out.println("Couldn't create the tables " + e.getMessage());
			return false;
		}finally {
			if(statement != null) {
				try{
					statement.close();
				}catch(SQLException e) {
					System.out.println("Couldn't close the statement " + e.getMessage());
				}
			}
		}
	}

	
	public HashMap<Integer, Member> getMembers(){
		String sql = "SELECT * FROM " + TABLE_MEMBERS;
		Statement statement = null;
		ResultSet rs = null;
			
		try {
			statement = conn.createStatement();
			rs = statement.executeQuery(sql);
			
			HashMap<Integer, Member> members = new HashMap<>();
			while(rs.next()) {
				int id = rs.getInt(INDEX_MEMBER_ID);
				String name = rs.getString(INDEX_MEMBER_NAME);
				String position = rs.getString(INDEX_MEMBER_POSITION);
				int currentNumOfBooks = rs.getInt(INDEX_MEMBER_NUM_OF_BOOKS);
				Member member = new Member(name, position);
				member.setId(id);
				member.setCurrentNumOfBooks(currentNumOfBooks);
				members.put(member.getId(),member);
			}
			return members;
			
		}catch(SQLException e) {
			System.out.println("Query failed " + e.getMessage());
			return null;
		}finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					System.out.println("Error closing the ResultSet");
					e.printStackTrace();
				}
			}
			if(statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					System.out.println("Error closing the Statement");
					e.printStackTrace();
				}
			}
		}
		
	}
		
	public HashMap<Integer, Book> getBooks(){
		String sql = "SELECT * FROM " + TABLE_BOOKS;
		Statement statement = null;
		ResultSet rs = null;
			
		try {
			statement = conn.createStatement();
			rs = statement.executeQuery(sql);
			
			HashMap<Integer, Book> books = new HashMap<>();
			while(rs.next()) {
				Book book = new Book();
				book.setBook_id(rs.getInt(INDEX_BOOK_ID));
				book.setTitle(rs.getString(INDEX_BOOK_TITLE));
				book.setAuthor(rs.getString(INDEX_BOOK_AUTHOR));
				book.setAvailableEditions(INDEX_BOOK_AVAILABLE);
				book.setNumOfEditions(rs.getInt(INDEX_BOOK_ALL));
				
				books.put(book.getBook_id(), book);
			}
			return books;
			
		}catch(SQLException e) {
			System.out.println("Query failed " + e.getMessage());
			return null;
		}finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					System.out.println("Error closing the ResultSet");
					e.printStackTrace();
				}
			}
			if(statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					System.out.println("Error closing the Statement");
					e.printStackTrace();
				}
			}
		}
	}
	
	public HashMap<Integer, Edition> getEditions(){
		String sql = "SELECT * FROM " + TABLE_EDITIONS;
		Statement statement = null;
		ResultSet rs = null;
			
		try {
			statement = conn.createStatement();
			rs = statement.executeQuery(sql);
			
			HashMap<Integer, Edition> editions = new HashMap<>();
			while(rs.next()) {
				Edition edition = new Edition();
				edition.setEdition_id(rs.getInt(INDEX_EDITION_ID));
				edition.setIsbn(rs.getString(INDEX_EDITION_ISBN));
				edition.setYear(rs.getString(INDEX_EDITION_YEAR));
				edition.setBook(rs.getInt(INDEX_EDITION_BOOK));
				edition.setAvailable(rs.getString(INDEX_EDITION_AVAILABLE));
				edition.setMember(rs.getInt(INDEX_EDITION_MEMBER));
				edition.setDue(rs.getDate(INDEX_EDITION_DUE));
				
				editions.put(edition.getEdition_id(), edition);
			}
			return editions;
			
		}catch(SQLException e) {
			System.out.println("Query failed " + e.getMessage());
			return null;
		}finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					System.out.println("Error closing the ResultSet");
					e.printStackTrace();
				}
			}
			if(statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					System.out.println("Error closing the Statement");
					e.printStackTrace();
				}
			}
		}
	}
	
	public void insertMember(Member member) {
		String sql = "INSERT INTO " + TABLE_MEMBERS + " (" + MEMBER_NAME + ", " + MEMBER_POSITION + ", " + MEMBER_NUM_OF_BOOKS + ")" + " VALUES(?,?,?)";
		PreparedStatement statement = null;
		
		try {
			statement = conn.prepareStatement(sql);
			statement.setString(1, member.getName());
			statement.setString(2, member.getPosition());
			statement.setInt(3, 0);
			statement.execute();

		}catch(SQLException e) {
			System.out.println("Query failed " + e.getMessage());
			
		}finally {
			if(statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					System.out.println("Error closing the Statement");
					e.printStackTrace();
				}
			}
		}
	}

	//add or sub one to available and all editions in books
	private int updateNumOfEditions(int id, String direction) {
		String sql = UPDATE_NUM_OF_EDITIONS_PART1 + direction + UPDATE_NUM_OF_EDITIONS_PART2 + direction + UPDATE_NUM_OF_EDITIONS_PART3;
		try {
			updateBooksEditionNum = conn.prepareStatement(sql);
			updateBooksEditionNum.setInt(1, id);
			updateBooksEditionNum.execute();
			
			queryBooksEditionNum.setInt(1, id);
			ResultSet result = queryBooksEditionNum.executeQuery();
			if(result.next()) {
				return result.getInt(1);
			}else {
				throw new SQLException("Couldn't get the num of editions");
			}	
			 
		}catch(SQLException e) {
			System.out.println("Error updating num of editions " + e.getMessage());
			return -1;
		}
		
	}
	
	//add or sub one to available in books
	private boolean updateAvailableEditions(int id, String direction) {
		String sql = UPDATE_NUM_OF_EDITIONS_PART1 + direction + UPDATE_NUM_OF_EDITIONS_PART3;
		try {
			updateBooksEditionNum = conn.prepareStatement(sql);
			updateBooksEditionNum.setInt(1, id);
			updateBooksEditionNum.execute();
			
			queryBooksEditionNum.setInt(1, id);
			ResultSet result = queryBooksEditionNum.executeQuery();
			if(result.next()) {
				return true;			
			}else {
				throw new SQLException("Couldn't get the num of editions");
			}			
		}catch(SQLException e) {
			System.out.println("Error updating num of editions " + e.getMessage());
			return false;
		}
	}
	
	//get the id and if it not exist insert
	private int insertBook(String title, String author) throws SQLException{
		
		queryBooksID.setString(1, title);
		queryBooksID.setString(2, author);
		
		ResultSet result = queryBooksID.executeQuery();
		if(result.next()) {
			int id = result.getInt(1);
			updateNumOfEditions(id, INCREASE);
			return id;
		}else {
			//insert the book
			insertIntoBooks.setString(1, title);
			insertIntoBooks.setString(2, author);
			insertIntoBooks.setInt(3, 1);
			insertIntoBooks.setInt(4, 1);
			int affectedRows = insertIntoBooks.executeUpdate();
			if(affectedRows != 1) {
				throw new SQLException("Couldn't insert book");
			}
			
			ResultSet generatedKey = insertIntoBooks.getGeneratedKeys();
			if(generatedKey.next()) {
				return generatedKey.getInt(1);
			}else {
				throw new SQLException("Couldn't get id from books");
			}
		}
	}
	
	public void insertEdition(Edition edition, Book book) {
		try {
			conn.setAutoCommit(false);
			int bookId = insertBook(book.getTitle(), book.getAuthor());
			
			insertIntoEditions.setString(1, edition.getIsbn());
			insertIntoEditions.setString(2, edition.getYear());
			insertIntoEditions.setInt(3, bookId);
			insertIntoEditions.setString(4, edition.getAvailable());
			insertIntoEditions.setInt(5, -1);
			
			int affectedRows = insertIntoEditions.executeUpdate();
			if(affectedRows == 1) {
				conn.commit();
			}else {
				throw new SQLException("Edition insert failed");
			}
			
		}catch(SQLException e) {
			System.out.println("Error inserting edition " + e.getMessage());
			try{ 
				System.out.println("Performing rollback");
				conn.rollback();
			}catch(SQLException e2) {
				System.out.println("Rollback is unsuccessful" + e2.getMessage());
			}
		}finally {
			try {
				System.out.println("Resetting commit behavior");
				conn.setAutoCommit(true);
			}catch(SQLException e){
				System.out.println("Couldn't reset auto-commit" + e.getMessage());
			}
			
		}
	}

	public void deleteMember(Member member){
		String sql = "DELETE FROM " + TABLE_MEMBERS + " WHERE " + MEMBER_ID + " = ?";
		PreparedStatement statement = null;
		
		try {
			statement = conn.prepareStatement(sql);
			statement.setInt(1, member.getId());
			statement.execute();
			
		}catch(SQLException e) {
			System.out.println("Error deleting member " + e.getMessage());
		}finally {
			if(statement != null) {
				try {
					statement.close();
				}catch(SQLException e) {
					System.out.println("Error closing statement " + e.getMessage());
				}
			}
		}
	}
	
	//delete a book with its all editions
	public void deleteBooks(int book_id) {
		String sql = "DELETE FROM " + TABLE_BOOKS + " WHERE " + BOOK_ID + " = ?";
		PreparedStatement statement = null;
		
		try {
			statement = conn.prepareStatement(sql);
			statement.setInt(1, book_id);
			statement.execute();
			
		}catch(SQLException e) {
			System.out.println("Error deleting member " + e.getMessage());
		}finally {
			if(statement != null) {
				try {
					statement.close();
				}catch(SQLException e) {
					System.out.println("Error closing statement " + e.getMessage());
				}
			}
		}
	}
	//deleting an edition, if it was the last of a book than deleting the book as well
	public void deleteEdition(Edition edition) {
		
		try {
			conn.setAutoCommit(false);
			int book_id = edition.getBook();
			if(updateNumOfEditions(book_id, DECREASE) == 0) {
				deleteBooks(book_id);
			}
			deleteEditions.setInt(1, edition.getEdition_id());
			int affectedRows = deleteEditions.executeUpdate();
			if(affectedRows == 1) {
				conn.commit();
			}else {
				throw new SQLException("Edition delete failed");
			}
			
		}catch(SQLException e) {
			System.out.println("Error deleting edition " + e.getMessage());
			try{ 
				System.out.println("Performing rollback");
				conn.rollback();
			}catch(SQLException e2) {
				System.out.println("Rollback is unsuccessful" + e2.getMessage());
			}
			
		}finally {
			try {
				System.out.println("Resetting commit behavior");
				conn.setAutoCommit(true);
			}catch(SQLException e){
				System.out.println("Couldn't reset auto-commit" + e.getMessage());
			}
		}
	}
	
	
	//update members column: num of books
	private boolean updateMemberBookNum(Member member, String direction){
		try {
			updateMember = conn.prepareStatement(UPDATE_MEMBER_BOOKNUM_PART1_ + direction + UPDATE_MEMBER_BOOKNUM_PART2);
			updateMember.setInt(1, member.getId());
			updateMember.execute();
			return true;
		}catch(SQLException e) {
			System.out.println("Couldn't update members num of books " + e.getMessage());
			return false;		
		}finally {
			if(updateMember != null) {
				try{
					updateMember.close();
				}catch(SQLException e2) {
					System.out.println("Error closing statement " + e2.getMessage());
				}
			}
		}
	}
	
	//update editions columns: member, available, due
	private boolean updateEditionDataBorrow(Member member, Edition edition){

		// create a java calendar instance and add 2 weeks
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.WEEK_OF_YEAR, DUE);
		java.util.Date currentDate = calendar.getTime();
		
		// create a java.sql.Date from the java.util.Date
		java.sql.Date date = new java.sql.Date(currentDate.getTime());
		
		try {
			updateEdition = conn.prepareStatement(UPDATE_EDITION);
			updateEdition.setString(1, NO);
			updateEdition.setInt(2, member.getId());
			updateEdition.setDate(3, date);
			updateEdition.setInt(4, edition.getEdition_id());
			updateEdition.execute();
			return true;
			
		}catch(SQLException e) {
			System.out.println("Couldn't update editions' columns " + e.getMessage());
			return false;	
			
		}finally {
			if(updateEdition != null) {
				try{
					updateEdition.close();
				}catch(SQLException e2) {
					System.out.println("Error closing statement " + e2.getMessage());
				}
			}
		}	
	}
	
	//update editions columns: member, available, due
	private boolean updateEditionDataBack(Member member, Edition edition) {
		try {
			updateEdition = conn.prepareStatement(UPDATE_EDITION);
			updateEdition.setString(1, YES);
			updateEdition.setInt(2, -1);
			updateEdition.setDate(3, null);
			updateEdition.setInt(4, edition.getEdition_id());
			updateEdition.execute();
			return true;
			
		}catch(SQLException e) {
			System.out.println("Couldn't update editions' columns " + e.getMessage());
			return false;	
			
		}finally {
			if(updateEdition != null) {
				try{
					updateEdition.close();
				}catch(SQLException e2) {
					System.out.println("Error closing statement " + e2.getMessage());
				}
			}
		}	
	}
	
	//member numOfBook increases by one, edition available set to N, member set to members.member_id, due set to current time + 2 weeks 
	public void borrowBook(Member member, Edition edition) {
		try {
			conn.setAutoCommit(false);
			if(member.getCurrentNumOfBooks() < member.getMaxNumOfBooks()) {
				if(updateMemberBookNum(member, INCREASE) && updateEditionDataBorrow(member, edition) && updateAvailableEditions(edition.getBook(), DECREASE)) {
					conn.commit();
				}else {
					throw new SQLException("Edition insert failed");
				}
			}else {
				System.out.println("Can't borrow: reached maximum number of books");
			}
			
		}catch(SQLException e) {
			System.out.println("Error updating members and editions " + e.getMessage());
			try{ 
				System.out.println("Performing rollback");
				conn.rollback();
			}catch(SQLException e2) {
				System.out.println("Rollback is unsuccessful" + e2.getMessage());
			}
		}finally {
			try {
				System.out.println("Resetting commit behavior");
				conn.setAutoCommit(true);
			}catch(SQLException e){
				System.out.println("Couldn't reset auto-commit" + e.getMessage());
			}
			
		}
	}

	//member numOfBook decreases by one, edition available set to Y, member set to -1, due set to null
	public void giveBackBook(Member member, Edition edition) {
		try {
			conn.setAutoCommit(false);
			if(updateMemberBookNum(member, DECREASE) && updateEditionDataBack(member, edition) && updateAvailableEditions(edition.getBook(), INCREASE)) {
				conn.commit();
			}else {
				throw new SQLException("Edition insert failed");
			}
		}catch(SQLException e) {
			System.out.println("Error updating members and editions " + e.getMessage());
			try{ 
				System.out.println("Performing rollback");
				conn.rollback();
			}catch(SQLException e2) {
				System.out.println("Rollback is unsuccessful" + e2.getMessage());
			}
		}finally {
			try {
				System.out.println("Resetting commit behavior");
				conn.setAutoCommit(true);
			}catch(SQLException e){
				System.out.println("Couldn't reset auto-commit" + e.getMessage());
			}
			
		}
	}


	public void updateMember(String oldDataField, String newData, Member oldMember) {
		String sql1 = "UPDATE " + TABLE_MEMBERS + " SET ";
		String sql2 = " = ?  WHERE " + MEMBER_ID + " = ?";
		PreparedStatement statement = null;
		try {
			statement = conn.prepareStatement(sql1 + oldDataField + sql2);
			statement.setString(1, newData);
			statement.setInt(2, oldMember.getId());
			statement.execute();
			
		}catch(SQLException e){
			System.out.println("Error updating " + e.getMessage());
		}finally {
			if(statement !=null) {
				try {
					statement.close();
				}catch(SQLException e2) {
					System.out.println("Error closing statement " + e2.getMessage());
				}
			}
		}
		
	}
	
	public void updateEdition(String oldDataField, String newData, Edition oldEdition) {
		String sql1 = "UPDATE " + TABLE_EDITIONS + " SET ";
		String sql2 = " = ?  WHERE " + EDITION_ID + " = ?";
		PreparedStatement statement = null;
		try {
			statement = conn.prepareStatement(sql1 + oldDataField + sql2);
			statement.setString(1, newData);
			statement.setInt(2, oldEdition.getEdition_id());
			statement.execute();
			
		}catch(SQLException e){
			System.out.println("Error updating " + e.getMessage());
		}finally {
			if(statement !=null) {
				try {
					statement.close();
				}catch(SQLException e2) {
					System.out.println("Error closing statement " + e2.getMessage());
				}
			}
		}
		
	}
	
	public void updateBook(String oldDataField, String newData, Book oldBook) {
		String sql1 = "UPDATE " + TABLE_BOOKS + " SET ";
		String sql2 = " = ?  WHERE " + BOOK_ID + " = ?";
		PreparedStatement statement = null;
		try {
			statement = conn.prepareStatement(sql1 + oldDataField + sql2);
			statement.setString(1, newData);
			statement.setInt(2, oldBook.getBook_id());
			statement.execute();
			
		}catch(SQLException e){
			System.out.println("Error updating " + e.getMessage());
		}finally {
			if(statement !=null) {
				try {
					statement.close();
				}catch(SQLException e2) {
					System.out.println("Error closing statement " + e2.getMessage());
				}
			}
		}
		
	}
}

