var myApp = angular.module("myApp", ["ngRoute"]);
myApp.config(function ($routeProvider, $locationProvider,$httpProvider) {
  $httpProvider.interceptors.push('responseObserver');
  $locationProvider.hashPrefix("");
  $routeProvider
    // .when("/", {
    //   templateUrl: "page/trangchu.html",
    // })
    .when("/", {
      templateUrl: "page/trangchu.html",
      controller :"homeCtrl"
    })
    .when("/about", {
      templateUrl: "page/about.html",
    })
    .when("/contact", {
      templateUrl: "page/contact.html",
    })
    .when("/blog", {
      templateUrl: "page/blog.html",
    })
    .when("/sanpham", {
      templateUrl: "page/sanpham.html",
      controller: "SanPhamController"
    })
    .when("/info", {
      templateUrl: "page/thongTinCaNhan.html",
      controller: "ThongTinCaNhanController"
    })
    .when("/cart", {
      templateUrl: "page/giohang.html",
      controller :"cartCtrl"
    })
    .when("/signup", {
      templateUrl: "page/signup.html",
    })
    .when("/login", {
      templateUrl: "page/login.html",
      controller:"loginCtrl"

    })

    .when("/changepass", {
      templateUrl: "/page/doimatkhau.html",
    })
    .when("/thanhtoan", {
      templateUrl: "page/thanhtoan.html",
      controller: "ThanhToanCtrl"
    })
    .when("/chitietsanpham/:idSp", {
      templateUrl: "page/chitietsanpham.html",
      controller: "TrangChiTietSanPhamController"
    })
    .when("/suathongtincanhan", {
      templateUrl: "page/suathongtincanhan.html",
      controller: "ThongTinCaNhanController"
    })
    .otherwise({
      redirectTo: "/",
    });
});

myApp.factory('responseObserver', function responseObserver($q, $window) {
  return {
    'responseError': function(errorResponse) {
      switch (errorResponse.status) {
        case 403:
          $window.location.href = '#login';
          break;
        // case 500:
        //   $window.location.href = '/login';
        //   break;
      }
      return $q.reject(errorResponse);
    }
  };
});
