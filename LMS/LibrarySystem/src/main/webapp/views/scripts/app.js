/**
 * 
 */
var app = angular.module("homeApp",['ngRoute','ngMessages']);

app.config(['$routeProvider','$locationProvider',function($routeProvider,$locationProvider){
	$locationProvider.html5Mode(true);
	$locationProvider.hashPrefix('');
	$routeProvider
	.when('/',{
		templateUrl : "views/issuereturn.html",
		controller : "issuereturn"
	})
	.when('/bookAddition',{
		templateUrl : "views/addBook.html",
		controller : "addbookCtrl"
	})
	.when('/bookEnquiry',{
		templateUrl : "views/enquiryBook.html",
		controller : "enquiryCtrl"
	})
	.when('/issuereturn',{
		templateUrl : "views/issuereturn.html",
		controller : "issuereturn"
	})
	.when('/issuereturn/:studentrollno/:bookisbn',{
		templateUrl : "views/issuereturn.html",
		controller : "issuereturn"
	})
	.otherwise({redirectTo : '/'});
	
}]);

app.service("BookService",['$http',function($http){
		this.saveBook = function saveBook(book){
			var bookdata = angular.toJson(book);
			
		    return $http.post(
				'/LibrarySystem/addBook',bookdata
			);
		};


		this.findBook = function findBook(isbn){
			
			return $http.get(
				'/LibrarySystem/getbook/'+isbn
			);
		};
		this.checkAvailability = function(isbn){
			return $http.get(
				'/LibrarySystem/check/'+isbn
			);
		};
		
		this.getBooks = function(){
			return $http.get(
				'/LibrarySystem/getAllBooks'
			);
		};
		
	}]);

app.service("IssueService",['$http',function($http){
	
	this.getIssueHistory = function(rollno){
		return $http.get(
			'/LibrarySystem/getissueshistory/'+rollno
		);
	};
	
	this.getIssue = function(rollno){
		return $http.get(
			'/LibrarySystem/getissues/'+rollno
		);
	};
	
	this.issueBook = function(issue){
		
		var issuedata = angular.toJson(issue);
		
		return $http.post(
			'/LibrarySystem/issueBook',issuedata
		);
	};
	
	this.returnBook = function returnBook(issue){
		var issuedata = angular.toJson(issue);
		
		return $http.post(
			'/LibrarySystem/returnbook',issuedata
		);
	};
	
	this.calcFine = function calcFine(issue){
		var issuedata = angular.toJson(issue);
		
		return $http.post(
			'/LibrarySystem/fine', issuedata
		);
	};
}]);
app.service("StudentService",['$http',function($http){
	this.getStudentDetails = function(rollno){
		return $http.get(
			'/LibrarySystem/getStudentDetails/'+rollno
		);
	};
}]);
app.controller("addbookCtrl",['$scope','BookService',function($scope,BookService){
		$scope.messege = "";
		$scope.saveBook = function(){
			
			if($scope.bookForm.$valid){
				$scope.book.rem_copies = $scope.book.total_copies;
				BookService.saveBook($scope.book).then(function success(response){
					$scope.messege = response.data.messege;
					$scope.getAllBooks();
					
				},function error(response){
					$scope.messege = "Error:" + response;
				});
			}
		};
		
		$scope.getAllBooks = function(){
			BookService.getBooks().then(
				function success(response){
					$scope.books = response.data;
				},
				function error(response){
					
				}
			);
		};
		
		$scope.getAllBooks();
	}]);

app.controller("enquiryCtrl",['$scope','BookService',function($scope,BookService){

	$scope.available=true;
	$scope.messege = "";
	$scope.HideRollnoForm = true;
	$scope.findBook = function findBook(){

		if($scope.searchForm.$valid){
			BookService.findBook($scope.isbn).then(function success(response){
				
				$scope.book = response.data;
				if($scope.isbn==$scope.book.isbn){
					$scope.available = false;
					$scope.messege = "Book Details Found";
				}else{
					$scope.messege = "Book Not Available";
					$scope.available = true;
				}
				
			},function error(response){
				$scope.messege = "Book Not Available";
				$scope.available = true;
			});
		}
	}


}]);

app.controller("issuereturn",['$scope','StudentService','IssueService','$routeParams',function($scope,StudentService,IssueService,$routeParams){
	
	$scope.studentprofile = true;
	$scope.issueFormShow = true;
	$scope.returnbtn = true;
	$scope.errmsg = "";
	$scope.curollno = $scope.rollno;
	
	$scope.issues = [];
	$scope.issuehistory = [];
	$scope.showform = function(){
		if($scope.issueFormShow == true){
			$scope.issueFormShow = false;
			
		}else{
			$scope.issueFormShow = true;
		}
		
	};
	
	$scope.findStudentProfile = function(){
		StudentService.getStudentDetails($scope.rollno).then(function success(response){
			$scope.student = response.data;
			if($scope.rollno == $scope.student.rollno){
				$scope.studentprofile = false;
				$scope.errmsg = "student details found";
				$scope.curollno = $scope.rollno;
				//load current issue
				IssueService.getIssue($scope.curollno).then(function success(response){
					$scope.issues = response.data;
				},function error(response){
					
				});
				
				//load history issues
				IssueService.getIssueHistory($scope.curollno).then(function success(response){
					$scope.issuehistory = response.data;
				},function error(response){
					
				});
				
				
			}else{
				$scope.errmsg = "Invalid student details";
				$scope.studentprofile = true;
			}
			
		},function(response){
			$scope.studentprofile = true;
		});
	};
	
	$scope.issueBook = function(){
		var issue = {};
		issue.rollno = $scope.curollno;
		issue.isbn = $scope.isbn;
		
		IssueService.issueBook(issue).then(function success(response){
			
			$scope.issues.push(response.data);
			
		},function error(response){
			
		});
		
	};
	
	$scope.returnBook = function(issue){
		IssueService.returnBook(issue).then(function success(response){
			var index = $scope.issues.indexOf(issue);
			$scope.issues.splice(index,1);
			
			$scope.issuehistory.push(response.data);
		},function error(response){
			
		});
	};
	
	$scope.calcFine = function(issue){
		IssueService.calcFine(issue).then(function success(response){
			var index = $scope.issues.indexOf(issue);
			$scope.issues[index].fine = response.data.fine;
			$scope.returnbtn = false;
		},function error(response){
			
		});
	};
	
	if($routeParams.studentrollno!=undefined && $routeParams.bookisbn!=undefined){
		$scope.rollno = $routeParams.studentrollno;
		$scope.curollno = $routeParams.studentrollno;
		$scope.isbn = $routeParams.bookisbn;
		
		$scope.findStudentProfile();
		$scope.issueBook();
	}	
	
}]);




