package com.datn.dongho5s.Controller.Report;

import com.datn.dongho5s.Entity.ReportItem;
import com.datn.dongho5s.Service.impl.MasterOrderReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
public class ReportRestController {
    @Autowired private MasterOrderReportService masterOrderReportService;

    @GetMapping("/statisticals/sales_report_by_date/{period}")
    public List<ReportItem> getReportDataByDatePeriod(@PathVariable("period") String period){
        System.out.println("Report peroid: " + period);

        switch (period){
            case "last_7_days":
                return masterOrderReportService.getReportDataLast7Days();
            case "last_28_days":
                return masterOrderReportService.getReportDataLast28Days();
            case "last_6_months":
                return masterOrderReportService.getReportDataLast6Months();
            case "last_year":
                return masterOrderReportService.getReportDataLastYear();
            default:
                return masterOrderReportService.getReportDataLast7Days();
        }
    }

    @GetMapping("/statisticals/sales_report_by_date/{startDate}/{endDate}")
    public List<ReportItem> getReportDataByDatePeriod(@PathVariable("startDate") String startDate,
                                                      @PathVariable("endDate") String endDate) throws ParseException {
        DateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");
        Date startTime = dateFormater.parse(startDate);
        Date endTime = dateFormater.parse(endDate);
        return masterOrderReportService.getReportDataByDateRange(startTime, endTime);
    }
}
