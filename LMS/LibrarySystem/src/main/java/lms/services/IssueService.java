package lms.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import lms.beans.Book;
import lms.beans.Issue;

@Service("issueService")
public class IssueService {
	
	@Autowired
	BookService bookService;
	
	@Autowired
	StudentService studentService;
	
	@Autowired
	MongoTemplate mongoTemplate;
	
	SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");
	
	final String COLLECTION = "issuecollection";
	
	public Issue addIssueEntry(Issue issue) {
		
		if(bookService.issuebook(issue.getISBN())) {
		
			Book book = new Book();
			book.setISBN(issue.getISBN());
			
			book = bookService.findBookBYISBN(book);
			
			Issue issuef = new Issue();
			issuef.setFine(0);
			issuef.setRollno(issue.getRollno());
			issuef.setISBN(issue.getISBN());
			issuef.setTitle(book.getTitle());
			issuef.setStatus(1);
			issuef.setFinestatus(0);
			Date date = new Date();
			
			issuef.setIssuedate(simple.format(date));
			
			Calendar calender = Calendar.getInstance();
			
			calender.setTime(date);
			
			calender.add(Calendar.DATE, 7);
			
			date = calender.getTime();
			
			issuef.setReturndate(simple.format(date));
			
			mongoTemplate.insert(issuef, COLLECTION);
			
			
			return issuef;
		}else {
			return null;
		}
		
	}
	
	public List getIssues(int rollno) {
		Query query = new Query();
		
		query.addCriteria(Criteria.where("rollno").is(rollno));
		query.addCriteria(Criteria.where("status").is(1));
		
		List issues = mongoTemplate.find(query,Issue.class,COLLECTION);
		
		return issues;
	}
	
	public List getIssuesHistory(int rollno) {
		Query query = new Query();
		
		query.addCriteria(Criteria.where("rollno").is(rollno));
		query.addCriteria(Criteria.where("status").is(0));
		
		List issues = mongoTemplate.find(query,Issue.class,COLLECTION);
		
		return issues;
	}
	
	public Issue updateReturnStatus(Issue issue) {
		Query query = new Query();
		query.addCriteria(Criteria.where("rollno").is(issue.getRollno()));
		query.addCriteria(Criteria.where("ISBN").is(issue.getISBN()));
		query.addCriteria(Criteria.where("status").is(1));
		
		Update update = new Update();
		update.set("status", 0);
		update.set("returndate", simple.format(new Date()));
		update.set("finestatus", 1);
		update.set("fine", issue.getFine());
		mongoTemplate.updateFirst(query, update, Issue.class,COLLECTION);
		
		bookService.returnbook(issue.getISBN());
		
		Query query1 = new Query();
		query1.addCriteria(Criteria.where("rollno").is(issue.getRollno()));
		query1.addCriteria(Criteria.where("ISBN").is(issue.getISBN()));
		query1.addCriteria(Criteria.where("returndate").is(simple.format(new Date())));
		
		return mongoTemplate.findOne(query1, Issue.class, COLLECTION);
	}
	
	public Issue getFine(Issue issue) {
		try {
			Date date = simple.parse(issue.getIssuedate());
			Date datef = simple.parse(issue.getReturndate());
			int days = (int) ((datef.getTime() - date.getTime()) / (1000 * 60 * 60 * 24));
			days = days - 7;
			int fine=0;
			if(days>0) {
				fine = days*10;
			}
			issue.setFine(fine);
			return issue;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
}
