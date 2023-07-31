myApp.controller("homeCtrl", function ($scope, $http,$window,$rootScope) {

    $scope.newSanPhams = [];
    $scope.hotSanPhams = [];
    $scope.featureSanPhams = [];
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
            $scope.featureSanPhams = resp.data.listSPNoiBat;

            $scope.hotSanPhams.forEach(item=>{
                $scope.getGiaNN(item.idSanPham)
                $scope.getGiaLN(item.idSanPham)
            })
            $scope.newSanPhams.forEach(item=>{
                $scope.getGiaNN(item.idSanPham)
                $scope.getGiaLN(item.idSanPham)
            })
            $scope.featureSanPhams.forEach(item=>{
                $scope.getGiaNN(item.idSanPham)
                $scope.getGiaLN(item.idSanPham)
            })
        });
        $rootScope.currentDate = new Date().toISOString();
    };
    $scope.init();

    //cart
    $scope.cart = [];
    $scope.total = 0;
    $scope.totalSp = 0;
    let currentUser = JSON.parse(localStorage.getItem("currentUser"));
    $rootScope.index = function () {
        if(currentUser != null) {
            $http.get(`/api/giohang/${currentUser.idKhachHang}`).then((resp) => {
                $scope.cart = resp.data;
                // console.log($scope.cart);
            }).catch(error => {
                if(error.status == 403) {
                return null ;
                    // Swal.fire({
                    //     icon: "warning",
                    //     title: "Bạn chưa đăng nhập !",
                    //     text: "Hãy đăng nhập để tiếp tục shopping!",
                    //     showConfirmButton: true,
                    //     closeOnClickOutside: true,
                    //     timer: 5600,
                    // });
                    // $window.location.href = '#login';
                }
            });
        }else{
            return null ;
            $window.location.reload();
            // Swal.fire({
            //     icon: "warning",
            //     title: "Bạn chưa đăng nhập !",
            //     text: "Hãy đăng nhập để tiếp tục shopping!",
            //     showConfirmButton: true,
            //     closeOnClickOutside: true,
            //     timer: 5600,
            // });
            // $window.location.href = '#login';
        }
    };
    $rootScope.index();
    $scope.setTotals = function (item) {

        if (item) {
            $scope.total += item.giaBan * item.soLuongSanPham;
            $scope.totalSp +=  item.soLuongSanPham;
        }
    };
    $scope.removeSP= function (item) {
        if (item) {
            $scope.delete(item);
            $scope.total -= item.giaBan * item.soLuongSanPham;
            $scope.totalSp -= item.soLuongSanPham;
            // console.log($scope.cart);
        }
    };

    // kt cart



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