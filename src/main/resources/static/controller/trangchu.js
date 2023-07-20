myApp.controller("homeCtrl", function ($scope, $http) {
    $scope.newSanPhams = [];
    $scope.hotSanPhams = [];
    $scope.itemWithGiaNN = new Map();
    $scope.itemWithGiaLN = new Map();
    $scope.idSanPham ="" ;
    $scope.run =0;
    $scope.init = function () {
        //load product
        $http.get(`/api/index`).then((resp) => {
            $scope.hotSanPhams = resp.data.listSPbanChay;
            console.log(resp.data.listSPbanChay);
            $scope.newSanPhams = resp.data.listSPmoiNhat;

            $scope.hotSanPhams.forEach(item=>{
                $scope.getGiaNN(item.idSanPham)
                $scope.getGiaLN(item.idSanPham)
            })
        });
    };
    $scope.init();

    $scope.spkm ;
    $scope.getGiaNN = function (idSanPham){
        $http
            .get(`/api/index/getSPKM?idCTSP=${idSanPham}`)
            .then((resp) => {
            $scope.spkm = resp.data;
            console.log(resp.data,"data")
            $scope.spkm.sort(function(a, b){return a.giaSanPham - b.giaSanPham});
            $scope.itemWithGiaNN.set(idSanPham,$scope.spkm[0].giaSanPham);
            console.log($scope.itemWithGiaNN.get(idSanPham));
            })
            .catch((e)=>{
            console.log(e);
            });
    }

    $scope.getGiaLN = function (idSanPham){
        $http
            .get(`/api/index/getSPKM?idCTSP=${idSanPham}`)
            .then((resp) => {
                $scope.spkm = resp.data;
                console.log(resp.data,"data")
                $scope.spkm.sort(function(a, b){return a.giaSanPham - b.giaSanPham});
                $scope.itemWithGiaLN.set(idSanPham,$scope.spkm[$scope.spkm.length-1].giaSanPham);
                console.log($scope.itemWithGiaLN.get(idSanPham));
            })
            .catch((e)=>{
                console.log(e);
            });
    }


    $scope.getGiaNNOld = function (listCT){
        listCT.sort(function(a, b){return a.giaSanPham - b.giaSanPham})
        return listCT[0].giaSanPham;
    }
    $scope.getGiaLNOld = function (listCT){
        listCT.sort(function(a, b){return a.giaSanPham - b.giaSanPham})
        return listCT[listCT.length-1].giaSanPham;
    }

})