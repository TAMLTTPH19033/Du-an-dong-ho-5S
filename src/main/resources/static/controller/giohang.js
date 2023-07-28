myApp.controller("cartCtrl", function ($scope,$rootScope, $http,$window,checkOutDataService,$location) {
    $scope.cart = [];
    $scope.total = 0;
    $scope.totalSp = 0;

    let currentUser = JSON.parse(localStorage.getItem("currentUser"));
    //load cart
    $scope.index = function () {
        if(currentUser != null) {
            $http.get(`/api/giohang/${currentUser.idKhachHang}`).then((resp) => {
                $scope.cart = resp.data;
                // console.log($scope.cart);
            }).catch(error => {
                if(error.status == 403) {
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
            });
        }else{
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
    $scope.update = function (cart, soLuong){
            $http.put(`/api/giohang/update/${cart.idChiTietGioHang}?soLuong=${soLuong}`)
                .then(resp => {
                    // $scope.cart = resp.data;
                    // alert("cap nhap thanh cong");
                    console.log(resp)
                })
                .catch(error => {
                    alert("Loi roi", error);
                })
        // }else{
        //     Swal.fire({
        //         icon: "warning",
        //         title: "Bạn chưa đăng nhập !",
        //         text: "Hãy đăng nhập để tiếp tục shopping!",
        //         timer: 1600,
        //     });
        // }
    }


// giam so luong
    $scope.giam = function (item, soLuong) {
        if (item) {
                if (item.soLuongSanPham <= 1) {
                    $scope.removeSP(item);
                    return;
                }
            }
            item.soLuongSanPham = Number(item.soLuongSanPham) - 1;
            soLuong = item.soLuongSanPham;
            $scope.update(item,soLuong);
            $scope.total -= item.giaBan ;
            $scope.totalSp-- ;
    };
    $scope.tang = function (item,soLuong) {
        if (item) {
            if(item.soLuongSanPham >= item.chiTietSanPham.soLuong){
                Swal.fire({
                            icon: "warning",
                            title: "Thông báo!",
                            text: "Số lượng quá",
                            timer: 1600,
                        });
                return;
            }
            item.soLuongSanPham = Number(item.soLuongSanPham) + 1;
            soLuong = item.soLuongSanPham;
            $scope.update(item,soLuong);
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
                $scope.cart= [];
                // alert("xoa thanh cong");
            })
            .catch(error =>{
                alert("Loi roi");
                console.log("eror"+ error);
            })
    }

    //xóa sanpham trong giỏ hàng
    $scope.removeSP= function (item) {
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

    $scope.change = function (item) {
        if (item) {
            if (item.soLuongSanPham < 1) {
                $scope.removeSP(item);
                return;
            }
            $scope.update(item);
            $scope.setTotals(item)
        }
    };

    $scope.buy = () => {
        $scope.chiTietSanPham=[];
        $scope.cart.forEach(item=>{
            $scope.chiTietSanPham.push({
                idChiTietSanPham: item.chiTietSanPham.idChiTietSanPham,
                giaBan: item.giaBan,
                soLuong: item.soLuongSanPham
            })
        })
        console.log($scope.chiTietSanPham,"CTSP")
        checkOutDataService.setData($scope.chiTietSanPham);
        $location.path("/thanhtoan");
      };

})