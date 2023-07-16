myApp.controller("loginCtrl", function ($scope,$rootScope, $http,$location) {


    $scope.errorMessages="";

    $scope.login = function() {
        $scope.loginRequest = {
            username : $scope.username,
            password : $scope.password
        }
        $http.post('/api/login', $scope.loginRequest ).then((resp) => {
            console.log(resp.data.idKhachHang,"aa")
            if ( resp.data.status == 200) {
                $rootScope.idkhachHang = resp.data.idKhachHang;
                $http.defaults.headers.common.Authorization = "Bearer " + resp.data.token;
                Swal.fire({
                    icon: "success",
                    title:  resp.data.message,
                    text: "Quay lại trang chủ!",
                    timer: 1600,
                });
                window.location.href = '#index';
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