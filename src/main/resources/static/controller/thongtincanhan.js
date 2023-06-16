const getThongTinCaNhanByIdAPI = "http://localhost:8080/khach-hang/thong-tin-ca-nhan/";

class ThongTinCaNhan {
    constructor(id,tenKhachHang,soDienThoai,email,gioiTinh,diaChi,ngaySinh){
        this.id = id;
        this.tenKhachHang = tenKhachHang;
        this.soDienThoai = soDienThoai;
        this.email = email;
        this.gioiTinh = gioiTinh;
        this.diaChi = diaChi;
        this.ngaySinh = ngaySinh;
    }
};
myApp.controller("ThongTinCaNhanController",
    function ($scope, $http) {
        $scope.thongtincanhan = new ThongTinCaNhan();

        $scope.getThongTinCaNhan = () => {
            let idThongTin = 10;
            $http
                .get(getThongTinCaNhanByIdAPI + idThongTin)
                .then((response) => {
                    $scope.thongtincanhan = response.data;
                })
                .catch((error) => {
                    console.log(error);
                });
        };

        $scope.suaThongTinCaNhan = (thongTin) => {
            window.location.href = "#suathongtincanhan";
        };
        $scope.huyCapNhat = () =>{
            window.location.href = "#info";
        };

        $scope.getThongTinCaNhan();
    });
