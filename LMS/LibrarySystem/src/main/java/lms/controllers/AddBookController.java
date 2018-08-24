package lms.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.util.JSONPObject;

import lms.beans.Book;
import lms.services.BookService;

@RestController
public class AddBookController {
	
	@Autowired
	HttpServletRequest request;
	
	@Autowired
	BookService bookService;
	
	@RequestMapping(value="/addBook", method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> addBook(@RequestBody Book book) 	{
		String message="";
		if(bookService.findBookBYISBN(book)==null) {
			bookService.addBook(book);
			message="{\"messege\":\"Book Added Successfully!!\"}";
		}else {
			message="{\"messege\":\"Book Already Exists for ISBN ="+book.getISBN()+"\"}";
		}
		
		return new ResponseEntity<String>(message,HttpStatus.OK);
	}
	
	
	
	
}
