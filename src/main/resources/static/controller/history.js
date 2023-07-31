myApp.controller(
    "historyCtrl",
    function (  $scope, $rootScope, $http,$routeParams,$location, $window
    ) {
        let currentUser = JSON.parse(localStorage.getItem("currentUser"));
        $scope.donHang = [];
        // $scope.all = "active";
        // $scope.changeTab = function (tab) {
        //     $scope.all = "";
        //     $scope.choXacNhan = "";
        //     $scope.DangGiao = "";
        //     $scope.HoanThanh = "";
        //     $scope.DaHuy = "";
        //     $scope.HoanTien = "";
        //     if (tab == 0) {
        //         $scope.all = "active";
        //         $scope.getAllDH();
        //     } else if (tab == 1) {
        //         $scope.choXacNhan = "active";
        //         $scope.getDHByStatus(1);
        //     } else if (tab == 2) {
        //         $scope.DangGiao = "active";
        //         $scope.getDHByStatus(2);
        //
        //     } else if (tab == 3) {
        //         $scope.HoanThanh = "active";
        //         $scope.getDHByStatus(3);
        //
        //     } else if (tab == 4) {
        //         $scope.DaHuy = "active";
        //         $scope.getDHByStatus(4);
        //
        //     } else if (tab == 5) {
        //         $scope.HoanTien = "active";
        //         $scope.getDHByStatus(5);
        //
        //     }
        // }

           if (currentUser != null) {
                $http.get(`/don-hang/findAll/${currentUser.idKhachHang}`)
                    .then((resp) => {
                        $scope.donHang = resp.data;

                    }).catch(error => {
                    if (error.status == 403) {
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
                });
            } else {
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


        // $scope.getDHByStatus = function (status) {
        //     if (currentUser != null) {
        //         $http.get(`/don-hang/findByStatus/${currentUser.idKhachHang}?status=${status}`)
        //             .then((resp) => {
        //                 $scope.donHang = resp.data;
        //                 console.log($scope.donHang, "dhby")
        //             }).catch(error => {
        //             if (error.status == 403) {
        //                 Swal.fire({
        //                     icon: "warning",
        //                     title: "Bạn chưa đăng nhập !",
        //                     text: "Hãy đăng nhập để tiếp tục shopping!",
        //                     showConfirmButton: true,
        //                     closeOnClickOutside: true,
        //                     timer: 5600,
        //                 });
        //                 $window.location.href = '#login';
        //             }
        //         });
        //     } else {
        //         Swal.fire({
        //             icon: "warning",
        //             title: "Bạn chưa đăng nhập !",
        //             text: "Hãy đăng nhập để tiếp tục shopping!",
        //             showConfirmButton: true,
        //             closeOnClickOutside: true,
        //             timer: 5600,
        //         });
        //         $window.location.href = '#login';
        //     }
        // }
    })

myApp.controller(
    "historyChoCtrl",
    function (  $scope, $rootScope, $http,$routeParams,$location, $window
    ) {
        let currentUser = JSON.parse(localStorage.getItem("currentUser"));
        $scope.donHang = [];
            if (currentUser != null) {
                $http.get(`/don-hang/findByStatus/${currentUser.idKhachHang}?status=0`)
                    .then((resp) => {
                        $scope.donHang = resp.data;
                    }).catch(error => {
                    if (error.status == 403) {
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
                });
            } else {
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

        var hoaDonChiTiets = [];
            $scope.huy =function (item){
               if($scope.lyDo == null && $scope.lyDoKhac == null){
                   $scope.error ="Lý do không được để trống "
               }else {
                   var donHangRequest = {
                       idDonHang: item.idDonHang,
                       lyDo: $scope.lyDo == null ? $scope.lyDoKhac : $scope.lyDo,
                       trangThaiDonHang: 4
                   }
                   console.log(donHangRequest)
                   $http.put(`/don-hang/update`,donHangRequest)
                       .then((resp) => {
                           $scope.donHang = resp.data;

                           // hoaDonChiTiets = $scope.donHang.hoaDonChiTiets;
                           // hoaDonChiTiets.forEach(item =>{
                           //     $scope.updateCTSP(item.)
                           // })
                           $('#myModal').modal('hide');
                          $window.location.href='/index#/history/4';
                           $window.location.reload();
                       }).catch(error => {
                       console.log(error)
                   });

               }
            }

            $scope.updateCTSP = function (chiTietSanPham){
                $http.put(`/chi-tiet-san-pham/update`,chiTietSanPham)
                    .then((resp) => {

                    }).catch(error => {
                    console.log(error)
                });
            }
    })

myApp.controller(
    "historyWaitCtrl",
    function (  $scope, $rootScope, $http,$routeParams,$location, $window
    ) {
        let currentUser = JSON.parse(localStorage.getItem("currentUser"));
        $scope.donHang = [];
        if (currentUser != null) {
            $http.get(`/don-hang/findByStatus/${currentUser.idKhachHang}?status=1`)
                .then((resp) => {
                    $scope.donHang = resp.data;
                }).catch(error => {
                if (error.status == 403) {
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
            });
        } else {
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
    })

myApp.controller(
    "historyShippingCtrl",
    function (  $scope, $rootScope, $http,$routeParams,$location, $window
    ) {
        let currentUser = JSON.parse(localStorage.getItem("currentUser"));
        $scope.donHang = [];
        if (currentUser != null) {
            $http.get(`/don-hang/findByStatus/${currentUser.idKhachHang}?status=2`)
                .then((resp) => {
                    $scope.donHang = resp.data;
                }).catch(error => {
                if (error.status == 403) {
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
            });
        } else {
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
    })

myApp.controller(
    "historyDoneCtrl",
    function (  $scope, $rootScope, $http,$routeParams,$location, $window
    ) {
        let currentUser = JSON.parse(localStorage.getItem("currentUser"));
        $scope.donHang = [];
        $scope.isPhanHoi;
        $scope.checkPhanHoi = new Map();
        if (currentUser != null) {
            $http.get(`/don-hang/findByStatus/${currentUser.idKhachHang}?status=3`)
                .then((resp) => {
                    $scope.donHang = resp.data;
                    $scope.donHang.forEach(item => {
                            item.hoaDonChiTiets.forEach(h => {
                                $scope.checkPhanHoiAPI(h.chiTietSanPham.idChiTietSanPham)
                            })
                        }
                    )
                }).catch(error => {
                if (error.status == 403) {
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
            });
        } else {
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

        $scope.hoanTra = function (item){
                if($scope.lyDo == null && $scope.lyDoKhac == null){
                    $scope.error ="Lý do không được để trống "
                }else {
                    var donHangRequest = {
                        idDonHang: item.idDonHang,
                        lyDo: $scope.lyDo == null ? $scope.lyDoKhac : $scope.lyDo,
                        trangThaiDonHang: 5
                    }
                    console.log(donHangRequest)
                    $http.put(`/don-hang/update`,donHangRequest)
                        .then((resp) => {
                            $scope.donHang = resp.data;
                            $('#myModal').modal('hide');
                            $window.location.href='/index#/history/5';
                            $window.location.reload();
                        }).catch(error => {
                        console.log(error)
                    });

                }
        }

        $scope.rating = 0;
        $scope.ratings = {
            current: -1,
            max: 5,
        };



        $scope.checkPhanHoiAPI = function (idChiTietSanPham) {
            if(currentUser) {
                $http
                    .get(
                        `phan-hoi/checkPhanHoi?idKhachHang=${currentUser.idKhachHang}&idSanPham=${idChiTietSanPham}`
                    )
                    .then(function (response) {
                        $scope.isPhanHoi= response.data;
                        $scope.checkPhanHoi.set(idChiTietSanPham,$scope.isPhanHoi);
                    })
                    .catch(function (error) {
                        console.log(error);
                    });

            }else{
                return true;
            }
        };
        $scope.sendRate = function (h) {
            if(currentUser) {
                $scope.phanhoirequest = {
                    danhGia: $scope.ratings.current,
                    noiDungPhanHoi: angular.element("#noiDungPhanHoi").val(),
                    idChiTietSanPham: h.chiTietSanPham.idChiTietSanPham,
                    idKhachHang: currentUser.idKhachHang,
                };
                console.log($scope.phanhoirequest);
                $http
                    .post(`/api/phan-hoi/add`, $scope.phanhoirequest)
                    .then((resp) => {
                        console.log(resp);
                        $scope.isPhanHoi = true;
                        alert("Them thanh cong");
                        $window.location.reload();

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
    })


myApp.controller(
    "historyCancelCtrl",
    function (  $scope, $rootScope, $http,$routeParams,$location, $window
    ) {
        let currentUser = JSON.parse(localStorage.getItem("currentUser"));
        $scope.donHang = [];
        if (currentUser != null) {
            $http.get(`/don-hang/findByStatus/${currentUser.idKhachHang}?status=4`)
                .then((resp) => {
                    $scope.donHang = resp.data;
                }).catch(error => {
                if (error.status == 403) {
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
            });
        } else {
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
    })

myApp.controller(
    "historyReturnCtrl",
    function (  $scope, $rootScope, $http,$routeParams,$location, $window
    ) {
        let currentUser = JSON.parse(localStorage.getItem("currentUser"));
        $scope.donHang = [];
        if (currentUser != null) {
            $http.get(`/don-hang/findByStatus/${currentUser.idKhachHang}?status=5`)
                .then((resp) => {
                    $scope.donHang = resp.data;
                }).catch(error => {
                if (error.status == 403) {
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
            });
        } else {
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
    })

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
