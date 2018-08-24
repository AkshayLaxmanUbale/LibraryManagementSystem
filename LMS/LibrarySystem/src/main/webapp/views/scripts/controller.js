/**
 * 
 */

angular.module("homeApp",[]).controller("homeCtrl",['$scope',function($scope){
	//home page controller part
	$scope.template = "addBook.html";
	
	//addBook controller part
	$scope.messege = "";
	$scope.book={};
	$scope.saveBook = function(){
		$scope.book.rem_copies = $scope.book.total_copies;
		if($scope.bookForm.$valid){
			BookService.saveBook($scope.book).then(function success(response){
				$scope.messege = "Book Added Successfully";
			},function error(response){
				$scope.messege = "Error:" +response.data.messege;
			});
		}
	};
}]);