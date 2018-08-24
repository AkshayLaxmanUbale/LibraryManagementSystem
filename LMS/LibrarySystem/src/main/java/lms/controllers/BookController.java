package lms.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lms.beans.Book;
import lms.services.BookService;

@RestController
public class BookController {

	@Autowired
	BookService bookService;
	
	@Autowired
	HttpServletRequest request;
	
	@RequestMapping(value="/getbook/{isbn}", method = RequestMethod.GET)
	public Book getBookData(@PathVariable int isbn) {
		Book book = new Book();
		book.setISBN(isbn);
		
		Book book1 = bookService.findBookBYISBN(book);
		try {
		System.out.println(book1.getISBN());
		}catch(NullPointerException e) {
			book1 = new Book();
			book1.setISBN(-123);
		}
		return book1;
		
	}
	
}
