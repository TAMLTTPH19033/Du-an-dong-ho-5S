myApp.controller(
    "historyCtrl",
    function (  $scope, $rootScope, $http,$routeParams,$location, $window
    ) {
        let currentUser = JSON.parse(localStorage.getItem("currentUser"));
        $scope.donHang = [];
        $scope.dh =[];
        $scope.pageSize = 1;
        $scope.currentPage = 1;
        $scope.maxPagesToShow = 1;
        $scope.totalPages;
        $scope.isFirstPage = true;
        $scope.isLastPage=false;

           if (currentUser != null) {
                $http.get(`/don-hang/findAll/${currentUser.idKhachHang}`)
                    .then((resp) => {
                        $scope.dh = resp.data;
                        $scope.totalPages = Math.ceil(
                            $scope.dh.length / $scope.pageSize
                        );
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

        $scope.$watchGroup(["dh"], function () {
            $scope.currentPage=1;
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
            console.log($scope.pages);
            $scope.donHang = $scope.dh.slice(startIndex, endIndex);

        });

        $scope.changePage = function (page) {
            $scope.pages = [];
            $scope.currentPage = page;
            var startPage = Math.max(1, $scope.currentPage - $scope.maxPagesToShow);
            var endPage = Math.min(
                $scope.totalPages,
                $scope.currentPage + $scope.maxPagesToShow
            );
            var startIndex = ($scope.currentPage - 1) * $scope.pageSize;
            var endIndex = startIndex + $scope.pageSize;
            for (var i = startPage; i <= endPage; i++) {
                $scope.pages.push(i);
            }
            console.log($scope.pages);
            $scope.donHang = $scope.dh.slice(startIndex, endIndex);
            $scope.checkFirstLastPage();
        };

        $scope.previousPage = function () {
            $scope.pages = [];
            if ($scope.currentPage > 1) {
                $scope.currentPage--;
                var startPage = Math.max(1, $scope.currentPage - $scope.maxPagesToShow);
                var endPage = Math.min(
                    $scope.totalPages,
                    $scope.currentPage + $scope.maxPagesToShow
                );
                var startIndex = ($scope.currentPage - 1) * $scope.pageSize;
                var endIndex = startIndex + $scope.pageSize;
                for (var i = startPage; i <= endPage; i++) {
                    $scope.pages.push(i);
                }
                console.log($scope.pages);
                $scope.donHang = $scope.dh.slice(startIndex, endIndex);
                $scope.checkFirstLastPage();
            }
        };

        $scope.nextPage = function () {
            $scope.pages = [];
            if ($scope.currentPage < $scope.totalPages) {
                $scope.currentPage++;
                var startPage = Math.max(1, $scope.currentPage - $scope.maxPagesToShow);
                var endPage = Math.min(
                    $scope.totalPages,
                    $scope.currentPage + $scope.maxPagesToShow
                );
                var startIndex = ($scope.currentPage - 1) * $scope.pageSize;
                var endIndex = startIndex + $scope.pageSize;
                for (var i = startPage; i <= endPage; i++) {
                    $scope.pages.push(i);
                }
                console.log($scope.pages);
                $scope.donHang = $scope.dh.slice(startIndex, endIndex);
                $scope.checkFirstLastPage();
            }
        };

        $scope.checkFirstLastPage = function (){
            console.log($scope.currentPage);
            if($scope.currentPage<=1){
                $scope.isFirstPage= true;
            }else{
                $scope.isFirstPage = false;
            }
            if($scope.currentPage >= $scope.totalPages){
                $scope.isLastPage = true;
            }else{
                $scope.isLastPage=false;
            }
        }
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
        $scope.items= [];
            $scope.loading = true;
        if (currentUser != null) {
            $http.get(`/don-hang/findByStatus/${currentUser.idKhachHang}?status=5`)
                .then((resp) => {
                    $scope.donHang = resp.data;
                    console.log($scope.donHang)
                    $scope.items.push($scope.donHang[0])
                    $scope.items.push($scope.donHang[1])

                    console.log($scope.items)
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
        var i =2;
        $scope.more = function (){
            if(i == $scope.donHang.length){
                $scope.loading = false;
            }

            for( i; i < $scope.donHang.length;i++ ){
                $scope.newItem = $scope.donHang[i++];
                i = i++;
                $scope.items.push( $scope.newItem );
                console.log($scope.items)
                break;
            }

        }
        $scope.more();
    })

myApp.directive("whenScrolled", function(){
    return{

        restrict: 'A',
        link: function($scope, elem, attrs){

            // we get a list of elements of size 1 and need the first element
           var raw = elem[0];

            // we load more elements when scrolled past a limit
            elem.bind("scroll", function(){
                if(raw.scrollTop+raw.offsetHeight+5 >= raw.scrollHeight){
                    $scope.loading = true;
                    console.log(raw.scrollTop+raw.offsetHeight+5 >= raw.scrollHeight,"raw")
                    // we can give any function which loads more elements into the list
                    $scope.$apply(attrs.whenScrolled);
                }else {
                    $scope.loading = false;
                }
            });
        }
    }
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
