package com.bookapp.model.service;

import java.util.List;

import com.bookapp.model.dao.book.Book;

public interface BookService {
	public List<Book> getAllBooks();
	public Book addBook(Book book);
	public Book delBook(int id);
	public Book updateBook(int id, double price);
	public Book getBookById(int id);
}
