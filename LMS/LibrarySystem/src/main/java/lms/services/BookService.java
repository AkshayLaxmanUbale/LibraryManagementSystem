package lms.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import lms.beans.Book;

@Service("bookService")
public class BookService {
	
	@Autowired
	MongoTemplate mongoTemplate;
	
	final String COLLECTION = "book";
	
	public void addBook(Book book) {
		mongoTemplate.insert(book,COLLECTION);
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
		Query query = new Query(Criteria.where("_id").is(book.getISBN()));
		return mongoTemplate.findOne(query, Book.class, COLLECTION);
	}
	
	public List<Book> findAllBooks(){
		return (List<Book>) mongoTemplate.findAll(Book.class);
	}
}