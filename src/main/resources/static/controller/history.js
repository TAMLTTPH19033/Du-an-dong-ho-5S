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
                        console.log($scope.donHang, "dh")
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
        // $scope.choXacNhan = "active";

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

        // $scope.getAllDH = function () {
        //     if (currentUser != null) {
        //         $http.get(`/don-hang/findAll/${currentUser.idKhachHang}`)
        //             .then((resp) => {
        //                 $scope.donHang = resp.data;
        //                 console.log($scope.donHang, "dh")
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
        //

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
        if (currentUser != null) {
            $http.get(`/don-hang/findByStatus/${currentUser.idKhachHang}?status=3`)
                .then((resp) => {
                    $scope.donHang = resp.data;
                    console.log($scope.donHang, "dhby")
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