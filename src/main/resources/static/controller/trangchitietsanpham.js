const detailSanPhamAPI =

    "http://localhost:8080/san-pham/san-pham-detail/id-san-pham=";
const getPhanHoiAPI =
    "http://localhost:8080/phan-hoi/get/idSanPham=";

myApp.controller(
    "TrangChiTietSanPhamController",
    function ($scope, $rootScope, $http, $routeParams, checkOutDataService, $location) {
        $scope.idSp = $routeParams.idSp;
        $scope.chiTietSanPham;
        $scope.sanPhamDetail;
        $scope.isFirstRun = 0;

        $rootScope.idKhachhang = 1;
        $scope.phanHoi = [];
        $scope.pageSize = 1;
        $scope.currentPage = 1;
        $scope.maxPagesToShow = 2;
        $scope.totalPages;
        $scope.check;


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
                $scope.soLuong = 1;
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

$scope.addToCart = function () {
    $rootScope.idKhachhang = 1;
    var item = {
        chiTietSanPham: $scope.chiTietSanPham,
        soLuong: $scope.soLuong,
        idKhachHang: $rootScope.idKhachhang,
    };
    console.log(item);
    if ($scope.chiTietSanPham) {
        //api add gio hang
        $http
            .post(`/api/giohang/addToCart`, item)
            .then((resp) => {
                console.log(resp);
                alert("Them thanh cong");
            })
            .catch((error) => {
                alert("Loi roi", error);
            });
    } else {
        alert("Khong có sp");
        return;
    }
};
$scope.buyNow = () => {
    checkOutDataService.setData([
        {
            idChiTietSanPham: $scope.chiTietSanPham.idChiTietSanPham,
            giaBan: $scope.chiTietSanPham.giaSanPham,
            soLuong: $scope.soLuong,
        },
    ]);
    $location.path("/thanhtoan");
};

$scope.PhanHoiAPI = function () {
    // const idSP = $scope.idSP;
    $http
        .get(`phan-hoi/get/${$scope.idSp}`)
        .then(function (response) {
            $scope.phanHoi = response.data;
            $scope.totalPages = Math.ceil(
                $scope.phanHoi.length / $scope.pageSize
            );
            console.log(response.data);
        })
        .catch(function (error) {
            console.log(error);
        });
}

$scope.PhanHoiAPI();

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
    console.log($scope.phanHoi, "phanhoi")
    console.log($scope.Items)
})

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
    $http
        .get(`phan-hoi/checkPhanHoi?idKhachHang=${$rootScope.idKhachhang}&idSanPham=${$scope.idSp}`)
        .then(function (response) {
            $scope.check = response.data;
            console.log(response.data);
        })
        .catch(function (error) {
            console.log(error);
        });
}
$scope.checkPhanHoiAPI();
//binhluan
$scope.rating = 0;
$scope.ratings = {
    current: -1,
    max: 5,
}
$scope.sendRate = function () {
    $scope.phanhoirequest = {
        danhGia: $scope.ratings.current,
        noiDungPhanHoi: angular.element('#noiDungPhanHoi').val(),
        chiTietSanPham: $scope.chiTietSanPham,
        idKhachHang: $rootScope.idKhachhang
    }
    console.log($scope.noiDungPhanHoi)
    $http.post(`/phan-hoi/add`, $scope.phanhoirequest)
        .then(resp => {
            console.log(resp)
            alert("Them thanh cong");
            $scope.PhanHoiAPI();
        })
        .catch(error => {
            alert("Loi roi", error);
        })
};
    }
);

myApp.directive("starRating", function () {
    return {
        template:
            '<ul class="rating">' +
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
            '<li ng-repeat="star in stars" ng-class="star" ng-click="toggle($index)">' +
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
