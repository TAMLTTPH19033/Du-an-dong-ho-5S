myApp.controller("loginCtrl", function ($scope,$rootScope ,$http,$location, $window) {

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

                $window.location.href = '#index';
                $window.location.reload();
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

myApp.controller("ChangePassCtrl", function ($scope,$rootScope ,$http,$location, $window){
    let currentUser = JSON.parse(localStorage.getItem("currentUser"));
    if(currentUser == null) {
    Swal.fire({
        icon: "warning",
        title: "Bạn chưa đăng nhập !",
        text: "Hãy đăng nhập để tiếp tục shopping!",
        showConfirmButton: true,
        closeOnClickOutside: true,
        timer: 5600,
    });
    $window.location.href = '#login';
}
    $scope.changePass = function (){

            let changeRequest = angular.copy($scope.changeRequest);
            console.log(changeRequest)
            $http.post('/api/changePass', changeRequest).then((resp) => {
                if (resp.status == 200) {
                    Swal.fire({
                        icon: "success",
                        title: "Thành công!",
                        text: "Bạn hãy đăng nhập lại để tiếp tục sử dụng dịch vụ nhé!",
                        timer: 5600,
                    });
                    $window.localStorage.removeItem('currentUser');
                    $http.defaults.headers.common.Authorization = "";
                    $window.location.href = '#login';
                }
            }).catch(error => {
                $scope.errorMessages = error.data.message;
            });

    }


})
