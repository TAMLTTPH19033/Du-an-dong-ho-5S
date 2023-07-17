myApp.controller("cartCtrl", function ($scope,$rootScope, $http) {
    $scope.cart = [];
    $scope.total = 0;
    $scope.totalSp = 0;

    //load cart
    $scope.index = function () {
        if($rootScope.idkhachHang != null) {
            $http.get(`/api/giohang/${$rootScope.idkhachHang}`).then((resp) => {
                $scope.cart = resp.data;
                // console.log($scope.cart);
            }).catch(error => {
                console.log(error)

            });
        }else{
            Swal.fire({
                icon: "warning",
                title: "Bạn chưa đăng nhập !",
                text: "Hãy đăng nhập để tiếp tục shopping!",
                timer: 1600,
            });
        }
    };
    $scope.index();

    // toorng sp vaf toorng tieefn
    $scope.setTotals = function (item) {
        if (item) {
            $scope.total += item.giaBan * item.soLuongSanPham;
            $scope.totalSp +=  item.soLuongSanPham;
        }
    };

    // api update soLuongtronggiohang
    $scope.update = function (cart){
        if($rootScope.idkhachHang != null) {
            $http.put(`/api/giohang/update/${cart.idChiTietGioHang}`, cart)
                .then(resp => {
                    // $scope.cart = resp.data;
                    // alert("cap nhap thanh cong");
                })
                .catch(error => {
                    alert("Loi roi", error);
                })
        }else{
            Swal.fire({
                icon: "warning",
                title: "Bạn chưa đăng nhập !",
                text: "Hãy đăng nhập để tiếp tục shopping!",
                timer: 1600,
            });
        }
    }


// giam so luong
    $scope.giam = function (item) {
        if (item) {
            if (item.soLuongSanPham <= 1) {
                Swal.fire({
                    icon: "error",
                    title: "Số lượng > 0",
                    timer: 1600,
                });
                return;
            }
            item.soLuongSanPham = Number(item.soLuongSanPham) - 1;
            $scope.update(item);
            // console.log($scope.cart);
            $scope.total -= item.giaBan ;
            $scope.totalSp-- ;
        }
    };
    $scope.tang = function (item) {
        if (item) {
            if (item.soLuongSanPham < 1) {
               $scope.remove(item);
               return;
            }
            item.soLuongSanPham = Number(item.soLuongSanPham) + 1;
            $scope.update(item);
            $scope.total += item.giaBan ;
            $scope.totalSp++ ;
        }
    };

    //api xóa giohang
    $scope.delete = function (item){
        $http.delete(`/api/giohang/delete/${item.idChiTietGioHang}`)
            .then(resp =>{
                const index = $scope.cart.findIndex(p => p.idChiTietGioHang ==  item.idChiTietGioHang);
                $scope.cart.splice(index,1);
                // alert("xoa thanh cong");
            })
            .catch(error =>{
                alert("Loi roi");
                console.log("eror"+ error);
            })
    }

    //api xóa giohang
    $scope.deleteAll = function (){
        $http.delete(`/api/giohang/deleteAll`)
            .then(resp =>{
                $scope.cart.forEach(e => e.remove());
                // alert("xoa thanh cong");
            })
            .catch(error =>{
                alert("Loi roi");
                console.log("eror"+ error);
            })
    }

    //xóa sanpham trong giỏ hàng
    $scope.remove = function (item) {
        if (item) {
            $scope.delete(item);
            $scope.total -= item.giaBan * item.soLuongSanPham;
            $scope.totalSp -= item.soLuongSanPham;
            // console.log($scope.cart);
        }
    };

    $scope.removeAll = function () {
        if ($scope.cart) {
            $scope.deleteAll($scope.cart);
            $scope.total = 0;
            $scope.totalSp = 0;

            // console.log($scope.cart);
        }
    };


})