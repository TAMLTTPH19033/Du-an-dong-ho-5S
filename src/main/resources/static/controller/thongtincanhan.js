const getThongTinCaNhanByIdAPI = "http://localhost:8080/khach-hang/thong-tin-ca-nhan/";
const postThemDiaChiAPI = "http://localhost:8080/dia-chi/them-dia-chi/";
const getAllDiaChiAPI = "http://localhost:8080/dia-chi/find-all";
const putUpdateThongTinCaNhanAPI = "http://localhost:8080/khach-hang/thong-tin-ca-nhan/";
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

class DiaChi {
    constructor(idDiaChi,diaChi,maBuuChinh,soDienThoai,ghiChu,trangThaiMacDinh){
        this.idDiaChi = idDiaChi;
        this.diaChi = diaChi;
        this.maBuuChinh = maBuuChinh;
        this.soDienThoai = soDienThoai;
        this.ghiChu = ghiChu;
        this.trangThaiMacDinh = trangThaiMacDinh;
    }
};
myApp.controller("ThongTinCaNhanController",
    function ($scope, $http) {
        $scope.thongtincanhan = new ThongTinCaNhan();
        $scope.diachi = new DiaChi();
        $scope.lstdiachi = undefined;

        $scope.getThongTinCaNhan = () => {
            let idThongTin = 1;
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

        $scope.postThemDiaChi = () => {
            $scope.diachi.trangThaiMacDinh = 0;
            $http
                .post(postThemDiaChiAPI,$scope.diachi)
                .then((request) => {
                    $scope.diachi = request.data;
                })
                .catch((error) => {
                    console.log(error);
                });
        };

        $scope.getAllDiaChi = () =>{
            $http
                .get(getAllDiaChiAPI)
                .then((request) => {
                    $scope.lstdiachi = request.data;
                })
                .catch((error) => {
                    console.log(error);
                });
        };
        $scope.getAllDiaChi();

        $scope.updateThongTinCaNhan = () => {

            $http
                .put(putUpdateThongTinCaNhanAPI,$scope.thongtincanhan)
                .then((request) => {
                    $scope.thongtincanhan.gioiTinh = $scope.thongtincanhan.gioiTinh = 'Nam' ? 1 : 0;

                    $scope.thongtincanhan = request.data;

                    console.log($scope.thongtincanhan.tenKhachHang);
                    console.log($scope.thongtincanhan.gioiTinh);
                    console.log($scope.thongtincanhan.diaChi);
                    console.log($scope.thongtincanhan.ngaySinh);
                })
                .catch((error) => {
                    console.log(error);
                });
        };

    });
