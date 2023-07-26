const themDonHangApi = "http://localhost:8080/don-hang/them-don-hang";
const getThongTinCaNhanAPI = "http://localhost:8080/khach-hang/thong-tin/";
const getFeeAPI = "http://localhost:8080/don-hang/tinh-phi-van-chuyen";
const thanhToanVNPayAPI = "http://localhost:8080/don-hang/thanh-toan-vnpay";

myApp.controller(
  "ThanhToanCtrl",
  function ($scope, $rootScope, $http, checkOutDataService, $window) {
    if (new URLSearchParams(window.location.search).get("message") != null) {
      var mess = new URLSearchParams(window.location.search).get("message");
      alert("Lỗi" + mess);
    }

    $scope.listHoaDonChiTietRequest = [];
    $scope.soLuongSanPham;
    $scope.listHoaDonChiTietRequest = checkOutDataService.getData();
    if ($scope.listHoaDonChiTietRequest.length == 0) {
      alert("Please select item");
      return;
    } else {
      console.log("");
      $scope.listHoaDonChiTietRequest.forEach((item) => {
        $scope.soLuongSanPham = +item.soLuong;
      });
    }
    $scope.ghiChu;
    $scope.isVNPAY = false;
    $scope.fee;
    $scope.tongTien = 0;
      let currentUser = JSON.parse(localStorage.getItem("currentUser"));
    $scope.getThongTinCaNhan = () => {
      $http
        .get(getThongTinCaNhanAPI + currentUser.idKhachHang)
        .then((response) => {
          $scope.thongtincanhan = response.data;
          $scope.diaChiGiaoHangs = $scope.thongtincanhan.listDiaChi;
          $scope.diaChiGiaoHang = $scope.diaChiGiaoHangs.filter(function (
            item
          ) {
            return item.trangThaiMacDinh == 1;
          })[0];
          $scope.idDiaChi=$scope.diaChiGiaoHang.idDiaChi;
          $scope.getFeeRequest = {
            idQuanHuyen: $scope.diaChiGiaoHang.idQuanHuyen,
            idPhuongXa: $scope.diaChiGiaoHang.idPhuongXa,
            soLuongSanPham: $scope.soLuongSanPham,
          };
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
      $scope.checkOutRequest = {
        // khachHangId: $rootScope.idKhachHang,
        khachHangId: currentUser.idKhachHang,
        listHoaDonChiTietRequest:
          $scope.listHoaDonChiTietRequest,
        idQuanHuyen: $scope.diaChiGiaoHang.idQuanHuyen,
        idPhuongXa: $scope.diaChiGiaoHang.idPhuongXa,
        diaChi: $scope.diaChiGiaoHang.diaChi,
        ghiChu: $scope.ghiChu,
        soLuongSanPham: $scope.soLuongSanPham,
        phiVanChuyen: $scope.fee,
      };
      if ($scope.isVNPAY) {
        $http
          .post(thanhToanVNPayAPI, $scope.checkOutRequest)
          .then(function (response) {
            var url = response.data.url;
            console.log(url);
            $window.open(url, "_self");
          })
          .catch((error) => {
            console.log(error);
          });
      } else {
        $http
          .post(themDonHangApi, $scope.checkOutRequest)
          .then((response) => {
            console.log(response.data);
          })
          .catch((error) => {
            console.log(error);
          });
      }
    };
    $scope.listHoaDonChiTietRequest.forEach((item) => {
      $scope.tongTien = +Number(item.giaBan) * Number(item.soLuong);
    });

    $scope.$watch("diaChiGiaoHang", (newValue, oldValue) => {
      $scope.getFeeRequest = {
        idQuanHuyen: $scope.diaChiGiaoHang.idQuanHuyen,
        idPhuongXa: $scope.diaChiGiaoHang.idPhuongXa,
        soLuongSanPham: $scope.soLuongSanPham,
      };
      $scope.getFee();
    });
  }
);
