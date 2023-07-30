const themDonHangApi = "http://localhost:8080/don-hang/them-don-hang";
const getThongTinCaNhanAPI = "http://localhost:8080/khach-hang/thong-tin/";
const getFeeAPI = "http://localhost:8080/don-hang/tinh-phi-van-chuyen";
const thanhToanVNPayAPI = "http://localhost:8080/don-hang/thanh-toan-vnpay";
const getTinhThanhAPI = "http://localhost:8080/dia-chi/get-tinh-thanh";
const getQuanHuyenAPI = "http://localhost:8080/dia-chi/get-quan-huyen/";
const getPhuongXaAPI = "http://localhost:8080/dia-chi/get-phuong-xa/";

myApp.controller(
  "ThanhToanCtrl",
  function ($scope, $rootScope, $http, checkOutDataService, $window) {
    // if (new URLSearchParams(window.location.search).get("message") != null) {
    //   var mess = new URLSearchParams(window.location.search).get("message");
    //   alert("Lỗi" + mess);
    // }

    $scope.listHoaDonChiTietRequest = [];
    $scope.soLuongSanPham;
    $scope.listHoaDonChiTietRequest = checkOutDataService.getData();
     console.log($scope.listHoaDonChiTietRequest);

    if ($scope.listHoaDonChiTietRequest == undefined) {
      alert("Please select item");
      $window.location.href = '#index';
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
        // .get(getThongTinCaNhanAPI + currentUser.idKhachHang)
          .get(getThongTinCaNhanAPI + 1)
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

    $scope.thanhToan = (isVNPAY) => {
      $scope.checkOutRequest = {
        // khachHangId: 1,
        khachHangId: $scope.thongtincanhan.id,
        listHoaDonChiTietRequest:
          $scope.listHoaDonChiTietRequest,
        idQuanHuyen: $scope.diaChiGiaoHang.idQuanHuyen,
        idPhuongXa: $scope.diaChiGiaoHang.idPhuongXa,
        diaChi: $scope.diaChiGiaoHang.diaChi,
        ghiChu: $scope.ghiChu,
        soLuongSanPham: $scope.soLuongSanPham,
        phiVanChuyen: $scope.fee,
      };
      if (isVNPAY==true) {
        $http
          .post(thanhToanVNPayAPI, $scope.checkOutRequest)
          .then(function (response) {
            var url = response.data.url;
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

    $scope.getListThanhPho = () =>{
        $http
            .get(getTinhThanhAPI)
            .then(function (response) {
                $scope.listThanhPho = new Map();
                for (const key in response.data) {
                    $scope.listThanhPho.set(key, response.data.key);
                }
                console.log($scope.listThanhPho.get(201))
            })
            .catch(function (error){
                console.log(error);
            })

    }
      $scope.getListThanhPho();
  }
);
