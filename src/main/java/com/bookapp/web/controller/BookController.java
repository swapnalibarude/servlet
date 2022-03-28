package com.bookapp.web.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bookapp.model.dao.book.Book;
import com.bookapp.model.service.BookService;
import com.bookapp.model.service.BookServiceImpl;

@WebServlet("/BookController.do")
public class BookController extends HttpServlet {

	private Logger logger = LoggerFactory.getLogger(BookController.class);

	private static final long serialVersionUID = 1L;

	private BookService bookService = new BookServiceImpl();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");

		if ("showall".equals(action)) {
			List<Book> books = bookService.getAllBooks();
			request.setAttribute("books", books);
			RequestDispatcher rd = request.getRequestDispatcher("show.jsp");
			rd.forward(request, response);
		}

		else if ("addbook".equals(action)) {
			request.getRequestDispatcher("addbook.jsp").forward(request, response);
		}

		else if ("delBook".equals(action)) {
			int id = Integer.parseInt(request.getParameter("id").trim());
			bookService.delBook(id);
			logger.info("book is deleted ");
			response.sendRedirect("BookController.do?action=showall");
		} else if ("updateBook".equals(action)) {
			int id = Integer.parseInt(request.getParameter("id").trim());
			Book book = bookService.getBookById(id);
			// hey use i know u want to update this book, we want to populate the data into
			// the form
			request.setAttribute("book", book);
			request.getRequestDispatcher("updatebook.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id").trim());

		String title = request.getParameter("title");
		String author = request.getParameter("author");

		String priceStr = request.getParameter("price");
		Double price = Double.parseDouble(priceStr);

		Book book = new Book(title, author, price);

		if (id == 0) {
			bookService.addBook(book);
			logger.info("book is added ");
		} else {
			bookService.updateBook(id, price);
			logger.info("book is updated ");
		}

		// PRG pattern
		response.sendRedirect("BookController.do?action=showall");
	}

}
