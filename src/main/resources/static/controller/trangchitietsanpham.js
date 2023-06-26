const detailSanPhamAPI =
  "http://localhost:8080/san-pham/san-pham-detail/id-san-pham=";

myApp.controller(
  "TrangChiTietSanPhamController",
  function ($scope, $rootScope, $http, $routeParams) {
    $scope.idSp = $routeParams.idSp;
    $scope.chiTietSanPham;
    $scope.sanPhamDetail;
    $scope.isFirstRun = 0;

    var setDayDeo = new Set();
    var setVatLieu = new Set();
    var setMauSac = new Set();
    var setKichCo = new Set();

    $scope.setAvaiableDayDeo = new Set();
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
        $scope.isFirstRun++;
        if ($scope.isFirstRun <= 1) {
          return;
        }
        $scope.selectedDD = newValues[0];
        $scope.selectedVL = newValues[1];
        $scope.selectedMS = newValues[2];
        $scope.selectedKC = newValues[3];
        $scope.chiTietSanPham = $scope.sanPhamDetail.listChiTietSanPham.filter(
          function (item) {
            return (
              item.dayDeo.tenDayDeo == $scope.selectedDD &&
              item.vatLieu.tenVatLieu == $scope.selectedVL &&
              item.kichCo.tenKichCo == $scope.selectedKC &&
              item.mauSac.tenMauSac == $scope.selectedMS
            );
          }
        )[0];
        //
        getAvailabelAttribute();
      }
    );

    var getAvailabelAttribute = function () {
      $scope.setAvaiableDayDeo = new Set();
      $scope.setAvaiableVatLieu = new Set();
      //Filter Set day deo
      $scope.sanPhamDetail.listChiTietSanPham
        .filter(function (item) {
          return item.vatLieu.tenVatLieu == $scope.selectedVL;
        })
        .forEach(function (item) {
          $scope.setAvaiableDayDeo.add(item.dayDeo.tenDayDeo);
        });
      //Filter Set vat lieu
      $scope.sanPhamDetail.listChiTietSanPham
        .filter(function (item) {
          return item.dayDeo.tenDayDeo == $scope.selectedDD;
        })
        .forEach(function (item) {
          $scope.setAvaiableVatLieu.add(item.vatLieu.tenVatLieu);
        });
      console.log($scope.setAvaiableDayDeo);
      console.log($scope.setAvaiableVatLieu);
      console.log($scope.setAvaiableMauSac);
      console.log($scope.setAvaiableKichCo);
    };
  }
);
