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
      templateUrl: "page/about-us.html",
    })
    .when("/contact", {
      templateUrl: "page/contact.html",
    })
    .when("/blog", {
      templateUrl: "page/blog.html",
    })
    .when("/sanpham", {
      templateUrl: "page/shop-gird.html",
      controller: "SanPhamController"
    })
    .when("/account", {
      templateUrl: "page/my-account.html",
      controller: "ThongTinCaNhanController"
    })
    .when("/cart", {
      templateUrl: "page/cart.html",
      controller :"cartCtrl"
    })
    .when("/signup", {
      templateUrl: "page/checkout-signin.html",
      controller: "registerCtrl"
    })
    .when("/login", {
      templateUrl: "page/signin.html",
      controller:"loginCtrl"

    }).when("/address", {
    templateUrl: "page/checkout-address.html",

  }).when("/shipping", {
    templateUrl: "page/checkout-shipping.html",

  })

    .when("/checkout", {
      templateUrl: "page/checkout.html",
      controller: "ThanhToanCtrl"
    })
    .when("/chitietsanpham/:idSp", {
      templateUrl: "page/single-product.html",
      controller: "TrangChiTietSanPhamController"
    })
    .when("/suathongtincanhan", {
      templateUrl: "page/my-.html",
      controller: "ThongTinCaNhanController"
    })
    .otherwise({
      redirectTo: "/",
    });
});

    // .run(function ($rootScope, $http, $location) {
    //   // Keep user logged in after page refresh
    //   let user = localStorage.getItem("currentUser");
    //   console.log(user)
    //  let currentUser = user ? JSON.parse(user) : {};
    //   if (currentUser) {
    //     $rootScope.currentUser = currentUser;
    //     $http.defaults.headers.common.Authorization = "Bearer " + currentUser.token;
    //   }
    //   $rootScope.$on("$locationChangeStart", function(event, next, current) {
    //     var publicPages = ["#"];
    //     var restrictPage = publicPages.indexOf($location.path()) === -1;
    //     if(restrictPage && !currentUser) {
    //       $location.path("/login");
    //     }
    //   });
    // });



myApp.factory('responseObserver', function responseObserver($q, $window) {
  return {
    'responseError': function(errorResponse) {
      switch (errorResponse.status) {
        case 403:
          $window.localStorage.removeItem('currentUser');
          // $http.defaults.headers.common.Authorization = "";
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


myApp.controller("indexCtrl", function ($rootScope,$scope, $http,$window, $location){
    let currentUser = localStorage.getItem("currentUser");
    if(currentUser){
  console.log(currentUser)
    $scope.currentUser = currentUser ? JSON.parse(currentUser) : {};
  $rootScope.currentUser =$scope.currentUser;
   $window.localStorage.setItem('currentUser', JSON.stringify($scope.currentUser));
      $http.defaults.headers.common.Authorization = "Bearer " + $scope.currentUser.token;
    }
else{
      $http.defaults.headers.common.Authorization = "";
    }

  $rootScope.logout = function (){
    $window.localStorage.removeItem('currentUser');
    $http.defaults.headers.common.Authorization = "";
    Swal.fire({
      icon: "warning",
      title: "Đã đăng xuất!",
      text: "Quay lại trang chủ!",
      showConfirmButton: false,
      closeOnClickOutside: false,
      allowOutsideClick: false,
      timer: 1600,
    });
    // $location.path("/")
    window.location.reload();
  }
})