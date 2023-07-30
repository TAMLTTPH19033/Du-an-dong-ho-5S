const detailSanPhamAPI =
  "http://localhost:8080/san-pham/san-pham-detail/id-san-pham=";
const getPhanHoiAPI = "http://localhost:8080/phan-hoi/get/idSanPham=";
const getSanPhamCungThuongHieuAPI = "http://localhost:8080/san-pham/cung-thuong-hieu=";
myApp.controller(
  "TrangChiTietSanPhamController",
  function (
    $scope,
    $rootScope,
    $http,
    $routeParams,
    checkOutDataService,
    $location,
    $window
  ) {

    $scope.idSp = $routeParams.idSp;
    $scope.chiTietSanPham;
    $scope.sanPhamDetail;
    $scope.isFirstRun = 0;
    $scope.soLuong = 1;
    $scope.moreInfo="active";
    $scope.dataSheet="";
    $scope.review="";

    $scope.phanHoi = [];
    $scope.pageSize = 1;
    $scope.currentPage = 1;
    $scope.maxPagesToShow = 2;
    $scope.totalPages;
    $scope.check;
    $scope.Items =[];
    $scope.spCungThuongHieu = [];

    var setDayDeo = new Set();
    var setVatLieu = new Set();
    var setMauSac = new Set();
    var setKichCo = new Set();

    $scope.setAvaiableVatLieu = new Set();
    $scope.setAvaiableMauSac = new Set();
    $scope.setAvaiableKichCo = new Set();

    $scope.selectedDD;
    $scope.selectedMS;
    $scope.selectedVL;
    $scope.selectedKC;

    $scope.getDetailSanPham = function (idSp) {
      $http
        .get(detailSanPhamAPI + idSp)
        .then(function (response) {
          $scope.sanPhamDetail = response.data;
          $scope.chiTietSanPham = $scope.sanPhamDetail.listChiTietSanPham[0];
          $scope.selectedDD = $scope.chiTietSanPham.dayDeo.tenDayDeo;
          $scope.selectedMS = $scope.chiTietSanPham.mauSac.tenMauSac;
          $scope.selectedVL = $scope.chiTietSanPham.vatLieu.tenVatLieu;
          $scope.selectedKC = $scope.chiTietSanPham.kichCo.tenKichCo;
          getSettingAttributeSp($scope.sanPhamDetail.listChiTietSanPham);
          $scope.PhanHoiAPI();
          $scope.checkPhanHoiAPI();
          $rootScope.currentDate = new Date().toISOString();

          getAvailabelAttribute(
            $scope.selectedDD,
            $scope.selectedMS,
            $scope.selectedVL,
            $scope.selectedKC
          );
        })
        .catch(function (error) {
          console.log(error);
        });
    };


    $scope.tang = function () {
      $scope.soLuong = $scope.soLuong + 1;
    };
    $scope.giam = function () {
      if ($scope.soLuong == 1) {
        alert("Đã giảm tối thiểu");
        return;
      }
      $scope.soLuong = $scope.soLuong - 1;
    };

    $scope.getCungThuongHieu = function (idThuongHieu){
      $http
          .get(getSanPhamCungThuongHieuAPI + idThuongHieu)
          .then(function (response) {
            $scope.spCungThuongHieu = response.data;
          })
          .catch(function (error) {
            console.log(error);
          });
    }

    $scope.getDetailSanPham($scope.idSp);
    var getSettingAttributeSp = function (listChiTietSanPham) {
      listChiTietSanPham.forEach((ctsp) => {
        setDayDeo.add(ctsp.dayDeo.tenDayDeo);
        setVatLieu.add(ctsp.vatLieu.tenVatLieu);
        setMauSac.add(ctsp.mauSac.tenMauSac);
        setKichCo.add(ctsp.kichCo.tenKichCo);
      });
      $scope.listDayDeo = Array.from(setDayDeo);
      $scope.listVatLieu = Array.from(setVatLieu);
      $scope.listMauSac = Array.from(setMauSac);
      $scope.listKichCo = Array.from(setKichCo);
    };
    $scope.$watchGroup(
      ["selectedDD", "selectedVL", "selectedMS", "selectedKC"],
      function (newValues, oldValues) {
        $scope.soLuong = 1;
        $scope.isFirstRun++;
        if ($scope.isFirstRun <= 1) {
          return;
        }
        // $scope.selectedDD = newValues[0];
        // $scope.selectedVL = newValues[1];
        // $scope.selectedMS = newValues[2];
        // $scope.selectedKC = newValues[3];
        //DayDeo
        if (newValues[0] !== oldValues[0]) {
          console.log("Thay day deo");
          $scope.chiTietSanPham =
            $scope.sanPhamDetail.listChiTietSanPham.filter(function (item) {
              return item.dayDeo.tenDayDeo == $scope.selectedDD;
            })[0];
            resetSelectedValue();
        }
        //VatLieu
        if (newValues[1] !== oldValues[1]) {
          console.log("Thay vl");
          $scope.chiTietSanPham =
            $scope.sanPhamDetail.listChiTietSanPham.filter(function (item) {
              return (
                item.dayDeo.tenDayDeo == $scope.selectedDD &&
                item.vatLieu.tenVatLieu == $scope.selectedVL
              );
            })[0];
            resetSelectedValue();
        }
        //MauSac
        if (newValues[2] !== oldValues[2]) {
          console.log("Thay mms");
          $scope.chiTietSanPham =
            $scope.sanPhamDetail.listChiTietSanPham.filter(function (item) {
              return (
                item.dayDeo.tenDayDeo == $scope.selectedDD &&
                item.vatLieu.tenVatLieu == $scope.selectedVL &&
                item.mauSac.tenMauSac == $scope.selectedMS
              );
            })[0];
            resetSelectedValue();
        }
        //KichCo
        if (newValues[3] !== oldValues[3]) {
          console.log("Thay kc");
          $scope.chiTietSanPham =
            $scope.sanPhamDetail.listChiTietSanPham.filter(function (item) {
              return (
                item.dayDeo.tenDayDeo == $scope.selectedDD &&
                item.vatLieu.tenVatLieu == $scope.selectedVL &&
                item.kichCo.tenKichCo == $scope.selectedKC &&
                item.mauSac.tenMauSac == $scope.selectedMS
              );
            })[0];
            resetSelectedValue();
        }
        //
        getAvailabelAttribute();
        $rootScope.currentDate = new Date().toISOString();
        $scope.PhanHoiAPI();
        $scope.checkPhanHoiAPI();



      }
    );

    var resetSelectedValue = function () {
      $scope.selectedDD = $scope.chiTietSanPham.dayDeo.tenDayDeo;
      $scope.selectedMS = $scope.chiTietSanPham.mauSac.tenMauSac;
      $scope.selectedVL = $scope.chiTietSanPham.vatLieu.tenVatLieu;
      $scope.selectedKC = $scope.chiTietSanPham.kichCo.tenKichCo;
    };

    var getAvailabelAttribute = function () {
      $scope.setAvaiableVatLieu = new Set();
      $scope.setAvaiableMauSac = new Set();
      $scope.setAvaiableKichCo = new Set();

      //Filter Set vat lieu
      $scope.sanPhamDetail.listChiTietSanPham
        .filter(function (item) {
          return item.dayDeo.tenDayDeo == $scope.selectedDD;
        })
        .forEach(function (item) {
          $scope.setAvaiableVatLieu.add(item.vatLieu.tenVatLieu);
        });
      // Filter Set Mau Sac
      $scope.sanPhamDetail.listChiTietSanPham
        .filter(function (item) {
          return (
            item.dayDeo.tenDayDeo == $scope.selectedDD &&
            item.vatLieu.tenVatLieu == $scope.selectedVL
          );
        })
        .forEach(function (item) {
          $scope.setAvaiableMauSac.add(item.mauSac.tenMauSac);
        });
      //Filter Set Kich Co
      $scope.sanPhamDetail.listChiTietSanPham
        .filter(function (item) {
          return (
            item.dayDeo.tenDayDeo == $scope.selectedDD &&
            item.vatLieu.tenVatLieu == $scope.selectedVL &&
            item.mauSac.tenMauSac == $scope.selectedMS
          );
        })
        .forEach(function (item) {
          $scope.setAvaiableKichCo.add(item.kichCo.tenKichCo);
        });
    };
    let currentUser = JSON.parse(localStorage.getItem("currentUser"));
    var giaSanPham= 0;
    $scope.getGia = function (){
      if($scope.chiTietSanPham.khuyenMai == null) {
        return giaSanPham = $scope.chiTietSanPham.giaSanPham;
      }else{
         if($scope.chiTietSanPham.khuyenMai.enabled == false ){
               return giaSanPham = $scope.chiTietSanPham.giaSanPham ;
           }else{
           if($rootScope.currentDate < $scope.chiTietSanPham.khuyenMai.ngayKetThuc.toString()){
              if($rootScope.currentDate < $scope.chiTietSanPham.khuyenMai.ngayBatDau.toString()){
                return giaSanPham = $scope.chiTietSanPham.giaSanPham ;
              }else {
                return giaSanPham =$scope.chiTietSanPham.giaSanPham - $scope.chiTietSanPham.giaSanPham * $scope.chiTietSanPham.khuyenMai.chietKhau/100;
              }
           }else{
             return giaSanPham = $scope.chiTietSanPham.giaSanPham ;


           }
         }
      }
    }


    $scope.changeTab = function (tab){

          $scope.moreInfo="";
      $scope.dataSheet="";
      $scope.review="";
      if(tab==1){
        $scope.moreInfo="active";
      }else if(tab==2){
        $scope.dataSheet="active";
      }else{
        $scope.review="active";
        $scope.PhanHoiAPI();
        $scope.$watchGroup(["phanHoi"], function () {
          $scope.pages = [];
          var startPage = Math.max(1, $scope.currentPage - $scope.maxPagesToShow);
          var endPage = Math.max(
              $scope.totalPages,
              $scope.currentPage + $scope.maxPagesToShow
          );
          for (var i = startPage; i <= endPage; i++) {
            $scope.pages.push(i);
          }

          var startIndex = ($scope.currentPage - 1) * $scope.pageSize;
          var endIndex = startIndex + $scope.pageSize;
          $scope.Items = $scope.phanHoi.slice(startIndex, endIndex);
          console.log($scope.phanHoi, "phanhoi");
          console.log( $scope.pages);

          // $scope.checkPhanHoiAPI();
        });

        $scope.checkPhanHoiAPI();

      }
    }
    $scope.addToCart = function () {
      if(currentUser) {
        var item = {
          idChiTietSanPham: $scope.chiTietSanPham.idChiTietSanPham,
          soLuong: $scope.soLuong,
          idKhachHang: currentUser.idKhachHang,
          giaSanPham: $scope.getGia()
        };

        if ($scope.chiTietSanPham) {
          //api add gio hang
          console.log(item);
          $http
              .post(`/api/giohang/addToCart`, item)
              .then((resp) => {
                if (resp == null) {
                  Swal.fire({
                    icon: "warning",
                    title: "Thông báo !",
                    text: "Quá số lượng sản phẩm!",
                    timer: 1600,
                  });
                } else {
                  console.log(resp);
                  Swal.fire({
                    icon: "success",
                    title: "Thành công",
                    text: "Đã thêm vào giỏ hàng!",
                    timer: 1600,
                  });
                }
              })
              .catch((error) => {
                alert("Loi roi", error);
              });
        } else {
          alert("Khong có sp");
          return;
        }
      }else{
        Swal.fire({
          icon: "warning",
          title: "Chưa đăng nhập",
          text: "Bạn hãy đăng nhập !",
          timer: 1600,
        });
        $window.location.href = '#login';
      }
    };
    $scope.buyNow = () => {
      checkOutDataService.setData([
        {
          // idChiTietSanPham: $scope.chiTietSanPham.idChiTietSanPham,
          giaBan: $scope.getGia(),
          idChiTietSanPham: $scope.chiTietSanPham.idChiTietSanPham,
          soLuong: $scope.soLuong,
          chiTietSanPham: $scope.chiTietSanPham
        },
      ]);
      $location.path("/checkout");
    };

    $scope.PhanHoiAPI = function () {
      var idChiTietSanPham = $scope.chiTietSanPham.idChiTietSanPham;
      console.log(idChiTietSanPham,"id")

      // const idSP = $scope.idSP;
      $http
        .get(`phan-hoi/get/${idChiTietSanPham}`)
        .then(function (response) {
          $scope.phanHoi = response.data;
          $scope.totalPages = Math.ceil(
            $scope.phanHoi.length / $scope.pageSize
          );
          console.log( $scope.totalPages);
          $scope.checkPhanHoiAPI();
        })

        .catch(function (error) {
          console.log(error);
        });
    };


    $scope.$watchGroup(["phanHoi"], function () {
      $scope.pages = [];
      var startPage = Math.max(1, $scope.currentPage - $scope.maxPagesToShow);
      var endPage = Math.min(
        $scope.totalPages,
        $scope.currentPage + $scope.maxPagesToShow
      );
      for (var i = startPage; i <= endPage; i++) {
        $scope.pages.push(i);
      }

      var startIndex = ($scope.currentPage - 1) * $scope.pageSize;
      var endIndex = startIndex + $scope.pageSize;
      $scope.Items = $scope.phanHoi.slice(startIndex, endIndex);
      console.log($scope.phanHoi, "phanhoi");
      console.log($scope.Items);

      // $scope.checkPhanHoiAPI();
    });

    $scope.changePage = function (page) {
      $scope.currentPage = page;
      var startIndex = ($scope.currentPage - 1) * $scope.pageSize;
      var endIndex = startIndex + $scope.pageSize;
      $scope.Items = $scope.phanHoi.slice(startIndex, endIndex);
    };

    $scope.previousPage = function () {
      if ($scope.currentPage > 1) {
        $scope.currentPage--;
        var startIndex = ($scope.currentPage - 1) * $scope.pageSize;
        var endIndex = startIndex + $scope.pageSize;
        $scope.Items = $scope.phanHoi.slice(startIndex, endIndex);
      }
    };

    $scope.nextPage = function () {
      if ($scope.currentPage < $scope.totalPages) {
        $scope.currentPage++;
        var startIndex = ($scope.currentPage - 1) * $scope.pageSize;
        var endIndex = startIndex + $scope.pageSize;
        $scope.Items = $scope.phanHoi.slice(startIndex, endIndex);
      }
    };

    $scope.checkPhanHoiAPI = function () {

      var idChiTietSanPham = $scope.chiTietSanPham.idChiTietSanPham;
      if(currentUser) {
        $http
            .get(
                `phan-hoi/checkPhanHoi?idKhachHang=${currentUser.idKhachHang}&idSanPham=${idChiTietSanPham}`
            )
            .then(function (response) {
              $scope.check = response.data;
              console.log(response.data);
            })
            .catch(function (error) {
              console.log(error);
            });
      }else{
        $scope.check = true;
      }
    };
    // $scope.checkPhanHoiAPI();
    //binhluan
    $scope.rating = 0;
    $scope.ratings = {
      current: -1,
      max: 5,
    };
    $scope.sendRate = function () {
      if(currentUser) {
        $scope.phanhoirequest = {
          danhGia: $scope.ratings.current,
          noiDungPhanHoi: angular.element("#noiDungPhanHoi").val(),
          idChiTietSanPham: $scope.chiTietSanPham.idChiTietSanPham,
          idKhachHang: currentUser.idKhachHang,
        };
        console.log($scope.phanhoirequest);
        $http
            .post(`/api/phan-hoi/add`, $scope.phanhoirequest)
            .then((resp) => {
              console.log(resp);
              alert("Them thanh cong");
              $scope.PhanHoiAPI();
              // $scope.checkPhanHoiAPI();

              // $window.location.reload();
            })
            .catch((error) => {
              alert("Loi roi", error);
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
  }
);

myApp.directive("starRating", function () {
  return {
    template:
      '<ul class="rating" >' +
      '<li ng-repeat="star in stars" ng-class="star">' +
      "\u2605" +
      "</li>" +
      "</ul>",
    scope: {
      ratingValue: "=",
      max: "=",
      onRatingSelected: "&",
    },
    link: function (scope, elem, attrs) {
      var updateStars = function () {
        scope.stars = [];
        for (var i = 0; i < scope.max; i++) {
          scope.stars.push({
            filled: i < scope.ratingValue,
          });
        }
      };
      scope.$watch("ratingValue", function (newVal) {
        if (newVal) {
          updateStars();
        }
      });
    },
  };
});

myApp.directive("starRatings", function () {
  return {
    restrict: "A",
    template:
      '<ul class="rating">' +
      '<li ng-repeat="star in stars" ng-class="star"   ng-click="toggle($index)">' +
      "\u2605" +
      "</li>" +
      "</ul>",
    scope: {
      ratingValue: "=",
      max: "=",
      onRatingSelected: "&",
    },
    link: function (scope, elem, attrs) {
      var updateStars = function () {
        scope.stars = [];
        for (var i = 0; i < scope.max; i++) {
          scope.stars.push({
            filled: i < scope.ratingValue,
          });
          // elem[i].css("color","yellow")
        }
      };

      scope.toggle = function (index) {
        scope.ratingValue = index + 1;
        scope.onRatingSelected({
          rating: index + 1,
        });
      };

      scope.$watch("ratingValue", function (oldVal, newVal) {
        if (newVal) {
          updateStars();
        }
      });
    },
  };
});
