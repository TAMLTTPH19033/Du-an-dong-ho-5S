myApp.controller("loginCtrl", function ($scope,$rootScope ,$http,$location, $window) {
    // let currentUser = localStorage.getItem("currentUser");
    // if(currentUser){
    //     $location.path("/");
    // }
    $scope.errorMessages="";

    $scope.currentUser ={
        idKhachHang:"",
        username :"",
        token :""
    };
    $scope.logins = function() {
        $scope.loginRequest = {
            username : $scope.username,
            password : $scope.password
        }


        $http.post('/api/login', $scope.loginRequest ).then((resp) => {
            if ( resp.data.status == 200) {
                $scope.currentUser.username = resp.data.username
                $scope.currentUser.token =resp.data.token
                $scope.currentUser.idKhachHang =resp.data.idKhachHang
                $rootScope.currentUser =$scope.currentUser;
                $window.localStorage.setItem('currentUser', JSON.stringify($scope.currentUser));
                $http.defaults.headers.common.Authorization = "Bearer " + resp.data.token;

                Swal.fire({
                    icon: "success",
                    title:  resp.data.message,
                    text: "Quay lại trang chủ!",
                    timer: 1600,
                });

                window.location.href = '#index';
                // window.location.reload();
                $scope.error = false;
            }
        }).catch(error =>{
            $scope.errorMessages = error.data.message;
            $rootScope.authenticated = false;
            $location.path("/login");
            $scope.error = true;

        });

    };




})


