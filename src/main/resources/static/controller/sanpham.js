const settingAPI = "http://localhost:8080/san-pham/get-setting";
const searchAPI = "http://localhost:8080/san-pham/tim-kiem";

myApp.controller("SanPhamController", function ($scope, $rootScope, $http) {
  $scope.listSetting = {};
  $scope.listSanPham = [];
  $scope.thuongHieuId = "";
  $scope.danhMucId = "";
  $scope.sizeId = "";
  $scope.mauSacId = "";
  $scope.vatLieuId = "";
  $scope.dayDeoId = "";
  $scope.giaSanPham = "";
  $scope.tenSanPham;
  $scope.pageSize = 1;
  $scope.currentPage = 1;
  $scope.maxPagesToShow = 2;
  $scope.totalPages;

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
      thuongHieuId: $scope.thuongHieuId,
      danhMucId: $scope.danhMucId,
      sizeId: $scope.sizeId,
      mauSacId: $scope.mauSacId,
      vatLieuId: $scope.vatLieuId,
      dayDeoId: $scope.dayDeoId,
      giaSanPham: $scope.giaSanPham,
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
