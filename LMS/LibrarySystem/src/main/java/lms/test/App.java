package lms.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import lms.beans.Book;
import lms.config.ApplicationConfig;
import lms.services.BookService;

public class App {
	public static void main(String[] args) {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
		
		BookService bookService = (BookService) context.getBean("bookService");
		
		bookService.deleteAllBooks();
		
		context.close();
		
	}
}
