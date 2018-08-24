/**
 * 
 */
var app = angular.module("homeApp",['ngRoute','ngMessages']);

app.config(['$routeProvider','$locationProvider',function($routeProvider,$locationProvider){
	$locationProvider.html5Mode(true);
	$locationProvider.hashPrefix('');
	$routeProvider
	.when('/',{
		templateUrl : "views/enquiryBook.html",
		controller : "enquiryCtrl"
	})
	.when('/bookAddition',{
		templateUrl : "views/addBook.html",
		controller : "addbookCtrl"
	})
	.when('/bookEnquiry',{
		templateUrl : "views/enquiryBook.html",
		controller : "enquiryCtrl"
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
		}
	}]);


app.controller("addbookCtrl",['$scope','BookService',function($scope,BookService){
		$scope.messege = "";
		$scope.saveBook = function(){
			
			if($scope.bookForm.$valid){
				$scope.book.rem_copies = $scope.book.total_copies;
				BookService.saveBook($scope.book).then(function success(response){
					$scope.messege = response.data.messege;
					
				},function error(response){
					$scope.messege = "Error:" + response;
				});
			}
		};
	}]);

app.controller("enquiryCtrl",['$scope','BookService',function($scope,BookService){

	$scope.available=true;
	$scope.messege = "";
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

	$scope.issueBook = function issueBook(){
		
	}
}]);

