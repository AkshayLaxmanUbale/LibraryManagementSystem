package lms.beans;

import java.util.Arrays;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="book")
public class Book {
	
	@Id
	int ISBN;
	String title;
	String author;
	int total_copies;
	int rem_copies;
	
	public Book() {
		super();
	}

	public Book(int iSBN, String title, String authors, int total_copies, int rem_copies) {
		super();
		ISBN = iSBN;
		this.title = title;
		author = authors;
		this.total_copies = total_copies;
		this.rem_copies = rem_copies;
	}

	public int getISBN() {
		return ISBN;
	}

	public void setISBN(int iSBN) {
		ISBN = iSBN;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthors(String authors) {
		author = authors;
	}

	public int getTotal_copies() {
		return total_copies;
	}

	public void setTotal_copies(int total_copies) {
		this.total_copies = total_copies;
	}

	public int getRem_copies() {
		return rem_copies;
	}

	public void setRem_copies(int rem_copies) {
		this.rem_copies = rem_copies;
	}

	@Override
	public String toString() {
		return "Book [ISBN=" + ISBN + ", title=" + title + ", Authors=" + author + ", total_copies="
				+ total_copies + ", rem_copies=" + rem_copies + "]";
	}
	
	
}
