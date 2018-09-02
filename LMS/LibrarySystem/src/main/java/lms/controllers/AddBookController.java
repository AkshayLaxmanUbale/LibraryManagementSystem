package lms.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lms.beans.Book;
import lms.services.BookService;

@RestController
public class AddBookController {
	
	final static Logger logger = Logger.getLogger(AddBookController.class);
	
	@Autowired
	HttpServletRequest request;
	
	@Autowired
	BookService bookService;
	
	@RequestMapping(value="/addBook", method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> addBook(@RequestBody Book book) 	{
		
		logger.debug("Received '/addBook' request with data = " + book.toString());
		
		String message="";
		if(bookService.findBookBYISBN(book)==null) {
			logger.debug("book not found for isbn = "+book.getISBN());
			bookService.addBook(book);
			
			message="{\"messege\":\"Book Added Successfully!!\"}";
		}else {
			
			logger.debug("book already exists with given isbn number i.e. "+book.getISBN());
			message="{\"messege\":\"Book Already Exists for ISBN ="+book.getISBN()+"\"}";
		}
		
		logger.debug("successfully processed the request");
		
		return new ResponseEntity<String>(message,HttpStatus.OK);
	}
	
	@RequestMapping(value="/getAllBooks", method = RequestMethod.GET)
	public List getBooks() {
		return bookService.findAllBooks();
	}
	
	
}
