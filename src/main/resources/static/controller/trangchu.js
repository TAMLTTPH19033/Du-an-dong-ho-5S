myApp.controller("homeCtrl", function ($scope, $http) {
    $scope.newSanPhams = [];
    $scope.hotSanPhams = [];
    $scope.init = function () {
        //load product
        $http.get(`/api/index`).then((resp) => {
            $scope.hotSanPhams = resp.data.listSPbanChay;
            console.log($scope.newSanPhams)
            $scope.newSanPhams = resp.data.listSPmoiNhat;
        });
    };
    $scope.init();
})