const settingAPI = "http://localhost:8080/san-pham/get-setting";
const searchAPI = "http://localhost:8080/san-pham/tim-kiem";

myApp.controller("SanPhamController", function ($scope, $rootScope, $http,$filter) {
  $scope.listSetting = {};
  $scope.listSanPham = [];
  $scope.danhMucIds = [];
  $scope.thuongHieuIds = [];
  $scope.dayDeoIds = [];
  $scope.vatLieuIds = [];
  $scope.kichCoIds = [];
  $scope.mauSacIds = [];
  $scope.tenSanPham;
  $scope.pageSize = 8;
  $scope.currentPage = 1;
  $scope.maxPagesToShow = 2;
  $scope.totalPages;

  $scope.changeDanhMucs = function () {
    $scope.selectedDanhMucs = $filter('filter')($scope.listSetting.listDanhMuc, {checked: true});
  }
  $scope.changeThuongHieucs = function () {
    $scope.selectedThuongHieus = $filter('filter')($scope.listSetting.listThuongHieu, {checked: true});
  }
  $scope.changeDayDeos = function () {
    $scope.selectedDayDeos = $filter('filter')($scope.listSetting.listDayDeo, {checked: true});
  }
  $scope.changeVatLieus = function () {
    $scope.selectedVatLieus = $filter('filter')($scope.listSetting.listVatLieu, {checked: true});
  }
  $scope.changeKichCos = function () {
    $scope.selectedKichCos = $filter('filter')($scope.listSetting.listKichCo, {checked: true});
  }
  $scope.changeMauSacs = function () {
    $scope.selectedMauSacs = $filter('filter')($scope.listSetting.listMauSac, {checked: true});
  }

  $scope.$watchGroup(["selectedDanhMucs","selectedThuongHieus","selectedDayDeos","selectedVatLieus","selectedKichCos","selectedMauSacs",], function (newValues, oldValues) {
    if (newValues[0] !== oldValues[0]) {
      console.log("Thay DanhMuc");
      $scope.selectedDanhMucs.forEach(element => {
        if(!$scope.danhMucIds.includes(element.id)){
          $scope.danhMucIds.push(element.id)
        }
      });
    }
    if (newValues[1] !== oldValues[1]) {
      console.log("Thay th");
      $scope.selectedThuongHieus.forEach(element => {
        if(!$scope.thuongHieuIds.includes(element.idThuongHieu)){
          $scope.thuongHieuIds.push(element.idThuongHieu)
        }
      });
    }
    if (newValues[2] !== oldValues[2]) {
      console.log("Thay dd");
      $scope.selectedDayDeos.forEach(element => {
       if(!$scope.dayDeoIds.includes(element.idDayDeo)){
          $scope.dayDeoIds.push(element.idDayDeo)
        }
      });
    }
    if (newValues[3] !== oldValues[3]) {
      console.log("Thay vl");
      $scope.selectedVatLieus.forEach(element => {
        if(!$scope.vatLieuIds.includes(element.idVatLieu)){
          $scope.vatLieuIds.push(element.idVatLieu)
        }
      });
    }
    if (newValues[4] !== oldValues[4]) {
      console.log("Thay kc");
      $scope.selectedKichCos.forEach(element => {
        if(!$scope.kichCoIds.includes(element.idKichCo)){
          $scope.kichCoIds.push(element.idKichCo)
        }
      });
    }
    if (newValues[5] !== oldValues[5]) {
      console.log("Thay ms");
      $scope.selectedMauSacs.forEach(element => {
        if(!$scope.mauSacIds.includes(element.idMauSac)){
          $scope.mauSacIds.push(element.idMauSac)
        }
      });
    }
  });
  $scope.getSettings = function () {
    $http
      .get(settingAPI)
      .then(function (response) {
        $scope.listSetting = response.data;
      })
      .catch(function (error) {
        console.log(error);
      });
  };
  $scope.getSettings();
  $scope.searchList = function () {
    $scope.conditionRequest = {
      thuongHieuId: $scope.thuongHieuIds,
      danhMucId: $scope.danhMucIds,
      sizeId: $scope.kichCoIds,
      mauSacId: $scope.mauSacIds,
      vatLieuId: $scope.vatLieuIds,
      dayDeoId: $scope.dayDeoIds,
      tenSanPham: $scope.tenSanPham,
    };
    $http
      .post(searchAPI, $scope.conditionRequest)
      .then(function (response) {
        $scope.listSanPham = response.data;
        $scope.totalPages = Math.ceil(
          $scope.listSanPham.length / $scope.pageSize
        );
        console.log(response.data);
      })
      .catch(function (error) {
        console.log(error);
      });
  };
  $scope.searchList();
  $scope.$watchGroup(["listSanPham"], function () {
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
    $scope.displayedItems = $scope.listSanPham.slice(startIndex, endIndex);
  });

  $scope.changePage = function (page) {
    $scope.currentPage = page;
    var startIndex = ($scope.currentPage - 1) * $scope.pageSize;
    var endIndex = startIndex + $scope.pageSize;
    $scope.displayedItems = $scope.listSanPham.slice(startIndex, endIndex);
  };

  $scope.previousPage = function () {
    if ($scope.currentPage > 1) {
      $scope.currentPage--;
      var startIndex = ($scope.currentPage - 1) * $scope.pageSize;
      var endIndex = startIndex + $scope.pageSize;
      $scope.displayedItems = $scope.listSanPham.slice(startIndex, endIndex);
    }
  };

  $scope.nextPage = function () {
    if ($scope.currentPage < $scope.totalPages) {
      $scope.currentPage++;
      var startIndex = ($scope.currentPage - 1) * $scope.pageSize;
      var endIndex = startIndex + $scope.pageSize;
      $scope.displayedItems = $scope.listSanPham.slice(startIndex, endIndex);
    }
  };
});
