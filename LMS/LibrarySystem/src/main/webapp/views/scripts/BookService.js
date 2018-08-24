/**
 * 
 */
angular.module("homeApp",[]).service("BookService",['$http',function($http){
	this.saveBook = function saveBook(book){
		var bookdata = angular.toJson(book);
		var config = {
	        headers : {
	           	'Accept':'application/json',
	      	    'Content-Type': 'application/json'
	   	    }
	    }
	    return $http.post(
			'/LibrarySystem/addBook',data,config
		);
	};
}]);