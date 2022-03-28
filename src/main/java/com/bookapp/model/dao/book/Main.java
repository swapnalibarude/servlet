package com.bookapp.model.dao.book;
import java.util.List;

import com.bookapp.model.service.BookService;
import com.bookapp.model.service.BookServiceImpl;
public class Main {

	public static void main(String[] args) {
		
//		UserService service=new UserServiceImpl();
//		User user=new User("indu", "indu123", "mgr");
//		service.addUser(user);
//		System.out.println(user);
//	
//		
		BookService dao=new BookServiceImpl();
		
		System.out.println(dao.getBookById(2));
		List<Book>books=dao.getAllBooks();
		books.forEach(b-> System.out.println(b));
//		//pl check all the method before next day session!
		
	}
}
