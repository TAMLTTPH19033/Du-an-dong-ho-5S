const themDonHangApi = "http://localhost:8080/don-hang/them-don-hang";
const getThongTinCaNhanAPI = "http://localhost:8080/khach-hang/thong-tin/";
const getFeeAPI = "http://localhost:8080/don-hang/tinh-phi-van-chuyen";

myApp.controller(
  "ThanhToanCtrl",
  function ($scope, $rootScope, $http, checkOutDataService) {
    $scope.listHoaDonChiTietRequest = [];
    $scope.soLuongSanPham;
    $scope.listHoaDonChiTietRequest = checkOutDataService.getData();
    if ($scope.listHoaDonChiTietRequest.length == 0) {
      alert("Please select item");
      return;
    }else{
      $scope.listHoaDonChiTietRequest.forEach(item=>{
        $scope.soLuongSanPham =+ item.soLuong;
      })
    }
    $scope.diaChiGiaoHang;
    $scope.ghiChu;
    $scope.isVNPAY = false;
    $scope.tongTien = 0;

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
          $scope.checkOutRequest = {
            // khachHangId: $rootScope.idKhachHang,
            khachHangId: 1,
            listHoaDonChiTietRequest: $scope.listHoaDonChiTietRequest,
            idQuanHuyen: $scope.diaChiGiaoHang.idQuanHuyen,
            idPhuongXa: $scope.diaChiGiaoHang.idPhuongXa,
            diaChi: $scope.diaChiGiaoHang.diaChi,
            ghiChu: $scope.ghiChu,
            soLuongSanPham: $scope.soLuongSanPham,
            phiVanChuyen: $scope.fee,
          };
          $scope.getFeeRequest = {
            idQuanHuyen: $scope.diaChiGiaoHang.idQuanHuyen,
            idPhuongXa: $scope.diaChiGiaoHang.idPhuongXa,
            soLuongSanPham: $scope.soLuongSanPham,
          };
          console.log($scope.checkOutRequest);
          console.log($scope.getFeeRequest);
          $scope.getFee();
        })
        .catch((error) => {
          console.log(error);
        });
    };
    $scope.init = () => {
      $scope.getThongTinCaNhan();
    };
    $scope.init();
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
            alert("Hóa đơn của bạn đã được tạo thành công ngày tạo: " + response.expectedDeliveryTime);
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
