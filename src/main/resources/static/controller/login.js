myApp.controller("loginCtrl", function ($scope,$rootScope, $http,$location) {

    var loginRequest = {
        username :$scope.username,
        password : $scope.password
    }
    $scope.error="";
    var authenticate = function() {
        console.log(loginRequest)
        $http.post('/api/login',loginRequest).then((resp) => {
            if ( resp.data.status) {
                $rootScope.idkhachHang = data.idkhachHang;
                $rootScope.authenticated = true;
            } else {
                $scope.error = resp.data.message;
                $rootScope.authenticated = false;
            }
        }).catch(error =>{
            $rootScope.authenticated = false;
        });


    }


    $scope.login = function() {
        authenticate();
            if ($rootScope.authenticated) {
                window.location.href = '#'
                $scope.error = false;
            } else {
                $location.path("/login");

            }
    };

})