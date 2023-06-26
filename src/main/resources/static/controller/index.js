var myApp = angular.module("myApp", ["ngRoute"]);
myApp.config(function ($routeProvider, $locationProvider) {
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
    })
    .when("/cart", {
      templateUrl: "page/giohang.html",
    })
    .when("/signup", {
      templateUrl: "page/signup.html",
    })
    .when("/login", {
      templateUrl: "page/login.html",
    })

    .when("/changepass", {
      templateUrl: "page/doimatkhau.html",
    })
    .when("/thanhtoan", {
      templateUrl: "page/thanhtoan.html",
    })
    .when("/chitietsanpham/:idSp", {
      templateUrl: "page/chitietsanpham.html",
      controller: "TrangChiTietSanPhamController"
    })
    .otherwise({
      redirectTo: "/",
    });
});
