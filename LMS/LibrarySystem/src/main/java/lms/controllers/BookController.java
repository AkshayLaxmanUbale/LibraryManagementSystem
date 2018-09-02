package lms.controllers;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lms.beans.Book;
import lms.services.BookService;

@RestController
public class BookController {
	
	final static Logger logger = Logger.getLogger(BookController.class);

	@Autowired
	BookService bookService;
	
	@Autowired
	HttpServletRequest request;
	
	@RequestMapping(value="/getbook/{isbn}", method = RequestMethod.GET)
	public Book getBookData(@PathVariable int isbn) {
		
		logger.debug("recived request for details about book isbn = " +isbn);
		
		Book book = new Book();
		book.setISBN(isbn);
		
		Book book1 = bookService.findBookBYISBN(book);
		try {
			System.out.println(book1.getISBN());
			logger.debug("book details found");
		}catch(NullPointerException e) {
			
			logger.debug("book not found for requested isbn = "+isbn);
			
			book1 = new Book();
			book1.setISBN(-123);
		}
		
		logger.debug("response is sent successfully to client");
		
		return book1;
		
	}
	
}
