
myApp.controller("registerCtrl", function ($scope,$rootScope, $http,$location) {
    let currentUser = localStorage.getItem("currentUser");
    if(currentUser){
        $location.path("/");
    }
    $scope.tpMap =[];
    $scope.qhMap =[];
    $scope.pxMap=[];
    $scope.isFirstRunQH = 0;
    $scope.isFirstRunPX = 0;
    // $scope.idTinhThanh ;
    $scope.getTP = ()=>{
        $scope.qhMap =[];
        $scope.pxMap=[];
        $http.get('/api/getTP')
            .then(resp =>{
                // $scope.tpMap.map
                $scope.tpMap = resp.data;
            }).catch(error =>{
            alert("Loi roi")
        })
    }
    $scope.getTP();

    $scope.$watchGroup(
        ["user.idTinhThanh"],
        function (newValues, oldValues) {
            $scope.pxMap =[];
            $scope.isFirstRunQH++;
            if ($scope.isFirstRunQH <= 1) {
                return;
            }
            $http.get("http://localhost:8080/api/getQH/"+ newValues)
                .then(resp =>{
                    $scope.qhMap = resp.data;
                }).catch(error =>{
            })
        }
    );


    $scope.$watchGroup(
        ["user.idQuanHuyen"],
        function (newValues, oldValues) {
            $scope.isFirstRunPX++;
            if ($scope.isFirstRunPX <= 1) {
                return;
            }
                $http.get("http://localhost:8080/api/getPX/" + newValues)
                    .then(resp => {
                        $scope.pxMap = resp.data;
                        console.log(resp)
                    }).catch(error => {
                })
        }
    );

    $scope.register = function(){
        let user = angular.copy($scope.user);
        console.log(user)
        $http.post('/api/register',user)
            .then(resp =>{
                // resp.data.createDate= new Date(resp.data.createDate);
                // $scope.items.push(resp.data);
                // $scope.reset();
                alert("Them thanh cong")
            }).catch(error =>{
            alert("Loi roi")
        })
    }

})