var data;
var chartOptions;
var totalGrossSales;
var totalNetSales;
var totalOrders;
var startDateField;
var endDateField;
var Milliseconds_per_day = 24 * 60 * 60 * 1000;
$(document).ready(function(){
    divCustomDateRange = $("#divCustomDateRange");
    startDateField = document.getElementById('startDate');
    endDateField = document.getElementById('endDate');

    $(".button-sales-by-date").on("click", function (){
       $(".button-sales-by-date").each(function(e){
           $(this).removeClass('btn-primary').addClass('btn-light');
       })
       $(this).removeClass('btn-light').addClass('btn-primary');
       period = $(this).attr("period");
        if (period){
            loadSalesReportByDates(period);
            divCustomDateRange.addClass("d-none");
        } else{
            divCustomDateRange.removeClass("d-none");
        }
   });
   initCustomDateRange();
   $("#buttonViewReportByDateRange").on("click", function (e){
      validateDateRange();
   });
});

function validateDateRange(){
    days = caculateDays();
    if (days >= 7 && days <= 30){
        loadSalesReportByDates("customizedDate");
    } else {
        startDateField.setCustomValidity("Date must be in the range of 7..30 days");
        startDateField.reportValidity();
    }
}
function caculateDays(){
    startDate = startDateField.valueAsDate;
    endDate = endDateField.valueAsDate;

    differencesInMilliseconds = endDate - startDate;
    return differencesInMilliseconds / Milliseconds_per_day;
}
function initCustomDateRange(){
    toDate = new Date();
    endDateField.valueAsDate = toDate;

    fromDate = new Date();
    fromDate.setDate(toDate.getDate() - 30);
    startDateField.valueAsDate = fromDate;
}
function loadSalesReportByDates(period) {
    if (period = "customizedDate"){
        startDate = $("#startDate").val();
        endDate = $("#endDate").val();
        requestURL = contextPath + "statisticals/sales_report_by_date/" + startDate + "/" + endDate;
    } else{
        requestURL = contextPath + "statisticals/sales_report_by_date/" + period;
    }
    $.get(requestURL, function (responseJSON) {
        prepareChartData(responseJSON);
        customizeChart(period);
        drawChart(period);
    });
    // fetch(requestURL)
    //     .then(response=>{
    //         console.log(response);
    //         prepareChartData(response);
    //         customizeChart();
    //         drawChart();
    //     })
    //     .catch(error=>{
    //         console.log(error)
    //     })
}

function prepareChartData(responseJSON) {
    data = new google.visualization.DataTable();
    data.addColumn('string', 'Ngày');
    data.addColumn('number', 'Tổng Doanh Thu');
    data.addColumn('number', 'Doanh Thu Thuần');
    data.addColumn('number', 'Số Đơn Hàng');

     totalGrossSales = 0.0;
     totalNetSales = 0.0;
     totalOrders = 0;

    $.each(responseJSON, function (index, reportItem) {
        data.addRows([[reportItem.identifier, reportItem.grossSales, reportItem.netSales, reportItem.ordersCount]]);
        totalGrossSales += parseFloat(reportItem.grossSales);
        totalNetSales += parseFloat(reportItem.netSales);
        totalOrders += parseInt(reportItem.ordersCount);

    });
}

function customizeChart(period) {
    chartOptions = {
        title: getChartTitle(period),
        'height': 360,
        legend: {position: 'top'},
        series: {
            0: {targetAxisIndex: 0},
            1: {targetAxisIndex: 0},
            2: {targetAxisIndex: 1},
        },
        vAxes: {
            0: {title: 'Doanh thu', format: 'VND '},
            1: {title: 'Số đơn hàng'}
        }
    };
    var formatter = new google.visualization.NumberFormat({
        suffix: ' VND'
    });

    formatter.format(data, 1);
    formatter.format(data, 2);
}

function drawChart(period) {
    var salesChart = new google.visualization.ColumnChart(document.getElementById('chart_sales_by_date'));
    salesChart.draw(data, chartOptions);
    $("#textTotalGrossSales").text($.number(totalGrossSales, 1) + " VND");
    $("#textTotalNetSales").text($.number(totalNetSales, 1) + " VND");

    denomiter = getDenomiter(period);
    $("#textAvgGrossSales").text($.number(totalGrossSales/ denomiter, 1) + " VND");
    $("#textAvgNetSales").text($.number(totalNetSales/ denomiter, 1) + " VND");
    $("#textTotalOrder").text(totalOrders + " đơn");
}

function getChartTitle(){
    if (period == "last_7_days") return "Tình hình kinh doanh trong 7 ngày";
    if (period == "last_28_days") return "Tình hình kinh doanh trong 28 ngày";
    if (period == "last_6_months") return "Tình hình kinh doanh trong 6 tháng";
    if (period == "last_year") return "Tình hình kinh doanh trong 1 năm";

    if (period == "customizedDate") return "Tùy chỉnh số ngày báo cáo";

    return "";
}

function getDenomiter(){
    if (period == "last_7_days") return 7;
    if (period == "last_28_days") return 28;
    if (period == "last_6_months") return 6;
    if (period == "last_year") return 12;
    if (period == "customizedDate") return caculateDays();
}