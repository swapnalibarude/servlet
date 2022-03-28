package com.bookapp.model.dao.book;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.bookapp.model.dao.factories.JdbcConnectionFactory;
import com.bookapp.model.exceptions.BookNotFoundException;
import com.bookapp.model.exceptions.DataAccessException;

public class BookDaoImpl implements BookDao{

	private Connection connection;
	
	public BookDaoImpl() {
		connection=JdbcConnectionFactory.getConnection();
	}
	@Override
	public List<Book> getAllBooks() {

		List<Book> books = new ArrayList<>();
		Book tempBook;
		try {
			String all_books_query = "select * from books";
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(all_books_query);
			while (rs.next()) {
				tempBook = new Book(rs.getInt("id"),
						rs.getString("title"),
						rs.getString("author"), 
						rs.getDouble("price"));

				books.add(tempBook);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new DataAccessException(ex.getMessage());
		}
		return books;
	}

	@Override
	public Book addBook(Book book) {
		
		try {
			String add_book_query=
			"insert into books(title, author,price) values(?,?,?)";
			PreparedStatement pstmt=connection.prepareStatement(add_book_query, 
					Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, book.getTitle());
			pstmt.setString(2, book.getAuthor());
			pstmt.setDouble(3, book.getPrice());
			
			int noOfRowsEffected=pstmt.executeUpdate();
			
			if(noOfRowsEffected>0) {
				ResultSet rs=pstmt.getGeneratedKeys();
				rs.next();
				book.setId(rs.getInt(1));
			}	
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new DataAccessException(ex.getMessage());
		}
		
		return book;
	}

	@Override
	public Book delBook(int id) {
		
		Book bookToBeDeleted = getBookById(id);
		try {
			String delete_book_by_id="delete from books where id=?";
			PreparedStatement pstmt= connection.prepareStatement(delete_book_by_id);
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bookToBeDeleted;
	}

	@Override
	public Book updateBook(int id, double price) {
		Book bookToBeUpdated = getBookById(id);// if book is not found it throws the ex: BookNotFoundEx
		
		try {
			String update_book_query=
			"update books set price=? where id=?";
			PreparedStatement pstmt=connection.prepareStatement(update_book_query);
			pstmt.setDouble(1, price);
			pstmt.setInt(2, id);
			int noOfRowsEffected=pstmt.executeUpdate();
			
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new DataAccessException(ex.getMessage());
		}
		bookToBeUpdated.setPrice(price);
		
		return bookToBeUpdated;
	}

	@Override
	public Book getBookById(int id) {
		Book book=null;
		try {
			String get_book_by_id="select * from books where id=?";
			PreparedStatement pstmt= connection.prepareStatement(get_book_by_id);
			pstmt.setInt(1, id);
			ResultSet rs=pstmt.executeQuery();
			if(rs.next()) {
				book=new Book(rs.getInt("id"), rs.getString("title"),
						rs.getString("author"),rs.getDouble("price"));
			}else {
				throw new BookNotFoundException("book with id "+ id +" is not found");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return book;
	}

	@Override
	public Book getBookByIsbn(String isbn) {
		Book book=null;
		try {
			String get_book_by_isbn="select * from books where isbn=?";
			PreparedStatement pstmt= connection.prepareStatement(get_book_by_isbn);
			pstmt.setString(1, isbn);
			
			ResultSet rs=pstmt.executeQuery();
			if(rs.next()) {
				book=new Book(rs.getInt("id"), rs.getString("title"),
						rs.getString("author"), rs.getDouble("price"));
			}else {
				throw new BookNotFoundException("book with isbn "+ isbn +" is not found");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return book;
	}


}
