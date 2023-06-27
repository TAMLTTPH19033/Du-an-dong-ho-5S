myApp.controller("homeCtrl", function ($scope, $http) {
    $scope.newSanPhams = [];
    $scope.hotSanPhams = [];
    $scope.init = function () {
        //load product
        $http.get(`/api/index`).then((resp) => {
            $scope.hotSanPhams = resp.data.listSPbanChay;
            console.log(resp.data.listSPbanChay);
            $scope.newSanPhams = resp.data.listSPmoiNhat;
        });
    };
    $scope.init();
    $scope.getGiaNN = function (listCT){
        console.log(listCT,"listCT")
        listCT.sort(function(a, b){return a.giaSanPham - b.giaSanPham})
        return listCT[0].giaSanPham;
    }
    $scope.getGiaLN = function (listCT){
        listCT.sort(function(a, b){return a.giaSanPham - b.giaSanPham})
        return listCT[listCT.length-1].giaSanPham;
    }

    // $scope.getGiaLN();
})