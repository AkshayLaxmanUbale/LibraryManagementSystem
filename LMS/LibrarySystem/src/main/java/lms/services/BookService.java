package lms.services;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import lms.beans.Book;

@Service("bookService")
public class BookService {
	
	final static Logger logger = Logger.getLogger(BookService.class);
	
	@Autowired
	MongoTemplate mongoTemplate;
	
	final String COLLECTION = "book";
	
	public void addBook(Book book) {
		logger.debug("Adding book to database");
		mongoTemplate.insert(book,COLLECTION);
		logger.debug("book added successfully to database");
	}
	
	public void updateBook(Book book) {
		mongoTemplate.save(book,COLLECTION);
	}
	
	public void deleteBook(Book book) {
		mongoTemplate.remove(book, COLLECTION);
	}
	
	public void deleteAllBooks() {
		mongoTemplate.remove(new Query(),COLLECTION);
	}
	
	public Book findBookBYISBN(Book book) {
		
		logger.debug("finding book with isbn = " + book.getISBN());
		
		Query query = new Query(Criteria.where("_id").is(book.getISBN()));
		return mongoTemplate.findOne(query, Book.class, COLLECTION);
	}
	
	public List<Book> findAllBooks(){
		return (List<Book>) mongoTemplate.findAll(Book.class);
	}
	
	public boolean issuebook(int isbn) {
		
		Book book = new Book();
		book.setISBN(isbn);
		book = findBookBYISBN(book);
		if(book.getRem_copies()>0) {
			book.setRem_copies(book.getRem_copies()-1);
			updateBook(book);
			
			return true;
		}else {
			return false;
		}
		
	}
	
	public boolean returnbook(int isbn) {
		Book book = new Book();
		book.setISBN(isbn);
		book = findBookBYISBN(book);
		book.setRem_copies(book.getRem_copies()+1);
		updateBook(book);
		return true;
	}
}