const themDonHangApi = "http://localhost:8080/san-pham/tim-kiem/don-hang/them-don-hang"
const getThongTinCaNhanByIdAPI ="http://localhost:8080/khach-hang/thong-tin/"

myApp.controller("ThanhToanCtrl", function ($scope,$rootScope, $http) {
    $scope.listChiTietSanPhamRequest = [];
    $scope.diaChiGiaoHang ;
    $scope.ghiChu;
    $scope.isVNPAY = false;
    $scope.checkOutRequest ={
        khachHangId : $rootScope.khachHangId,
        ngayTao : null,
        listChiTietSanPhamRequest : $scope.listChiTietSanPhamRequest,
        diaChiGiaoHang : $scope.diaChiGiaoHang,
        ghiChu : $scope.ghiChu
    }
    $scope.getThongTinCaNhan = () => {
        $http
            .get(getThongTinCaNhanByIdAPI + $rootScope.khachHangId)
            .then((response) => {
                $scope.thongtincanhan = response.data;
                $scope.diaChiGiaoHangs = $scope.thongtincanhan.diaChi;
                $scope.diaChiGiaoHang = $scope.diaChiGiaoHangs
                .filter(function (item) {
                    return item.trangThaiMacDinh == 1;
                })[0];
            })
            .catch((error) => {
                console.log(error);
            });
    };
    
    $scope.getThongTinCaNhan();
    $scope.thanhToan = () =>{
        if(isVNPAY){
            alert("Chức năng đang bảo trì")
        }else{
            $http
                .post(themDonHangApi, $scope.checkOutRequest)
                .then((response)=>{
                    alert("Hóa đơn của bạn đã được tạo thành công" + response.ngayTao);
                })
                .catch((error)=>{
                    console.log(error)
                })
        }
    }
})