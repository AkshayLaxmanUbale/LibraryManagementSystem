package lms.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lms.beans.Issue;
import lms.services.IssueService;

@RestController
public class IssueBookController {

	@Autowired
	HttpServletRequest request;
	
	@Autowired
	IssueService issueService;
	
	@RequestMapping(value="/issueBook", method = RequestMethod.POST)
	public Issue issueBook(@RequestBody Issue issue) {
		
		issue = issueService.addIssueEntry(issue);
		
		return issue;
	}
	
	
	@RequestMapping(value="/getissues/{rollno}",method = RequestMethod.GET)
	public List getCurrentIssues(@PathVariable int rollno) {
		
		return issueService.getIssues(rollno);
	}
	
	@RequestMapping(value="/getissueshistory/{rollno}",method = RequestMethod.GET)
	public List getHistoryIssues(@PathVariable int rollno) {
		
		return issueService.getIssuesHistory(rollno);
	}
	
}
