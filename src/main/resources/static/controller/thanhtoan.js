const themDonHangApi =
  "http://localhost:8080/don-hang/them-don-hang";
const getThongTinCaNhanAPI = "http://localhost:8080/khach-hang/thong-tin/";
const getFeeAPI = "http://localhost:8080/don-hang/tinh-phi-van-chuyen";

myApp.controller(
  "ThanhToanCtrl",
  function ($scope, $rootScope, $http, checkOutDataService) {
    $scope.listHoaDonChiTietRequest = [];
    $scope.listHoaDonChiTietRequest = checkOutDataService.getData();
    if ($scope.listHoaDonChiTietRequest.lenght == 0) {
      alert("Please select item");
      return;
    }
    $scope.diaChiGiaoHang;
    $scope.ghiChu;
    $scope.isVNPAY = false;
    console.log($scope.isVNPAY);
    $scope.soLuongSanPham;
    $scope.tongTien = 0;
    $scope.getFeeRequest = {
      idQuanHuyen: $scope.diaChiGiaoHang.idQuanHuyen,
      idPhuongXa: $scope.diaChiGiaoHang.idPhuongXa,
      soLuongSanPham: $scope.soLuongSanPham,
    };
    $scope.checkOutRequest = {
      khachHangId: $rootScope.idKhachHang,
      listHoaDonChiTietRequest: $scope.listHoaDonChiTietRequest,
      idQuanHuyen: $scope.diaChiGiaoHang.idQuanHuyen,
      idPhuongXa: $scope.diaChiGiaoHang.idPhuongXa,
      diaChi: $scope.diaChiGiaoHang.diaChi,
      ghiChu: $scope.ghiChu,
      soLuongSanPham: $scope.soLuongSanPham,
    };

    $scope.getThongTinCaNhan = () => {
      $http
        .get(getThongTinCaNhanAPI + 1)
        .then((response) => {
          $scope.thongtincanhan = response.data;
          $scope.diaChiGiaoHangs = $scope.thongtincanhan.listDiaChi;
          console.log($scope.thongtincanhan);
          $scope.diaChiGiaoHang = $scope.diaChiGiaoHangs.filter(function (
            item
          ) {
            return item.trangThaiMacDinh == 1;
          })[0];
          $scope.getFee();
        })
        .catch((error) => {
          console.log(error);
        });
    };
    $scope.getThongTinCaNhan();
    $scope.getFee = () => {
      $http
        .post(getFeeAPI, $scope.getFeeRequest)
        .then((response) => {
          $scope.fee = response.data;
        })
        .catch((error) => {
          console.log(error);
        });
    };

    $scope.thanhToan = () => {
      if ($scope.isVNPAY) {
        alert("Chức năng đang bảo trì");
      } else {
        $http
          .post(themDonHangApi, $scope.checkOutRequest)
          .then((response) => {
            alert("Hóa đơn của bạn đã được tạo thành công" + response.ngayTao);
          })
          .catch((error) => {
            console.log(error);
          });
      }
    };
    $scope.listHoaDonChiTietRequest.forEach((item) => {
      $scope.tongTien = +Number(item.giaBan) * Number(item.soLuong);
    });
  }
);
