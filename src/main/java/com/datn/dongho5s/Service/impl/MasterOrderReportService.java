package com.datn.dongho5s.Service.impl;

import com.datn.dongho5s.Entity.ReportItem;
import com.datn.dongho5s.Entity.DonHang;
import com.datn.dongho5s.Repository.DonHangRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class MasterOrderReportService {
    @Autowired private DonHangRepository repo;
    private DateFormat dateFormater;
    public List<ReportItem> getReportDataLast7Days(){
        System.out.println("getReportDataLast7Days....");
        return getReportDataLastXDays(7);
    }

    public List<ReportItem> getReportDataLast28Days(){
        System.out.println("getReportDataLast7Days....");
        return getReportDataLastXDays(28);
    }

    private List<ReportItem> getReportDataLastXDays(int days){
        Date endTime = new Date();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -(days -1));
        Date startTime = cal.getTime();
        System.out.println("Start time: " + startTime);
        System.out.println("End time: " + endTime);
        dateFormater = new SimpleDateFormat("yyyy-MM-dd");
        return getReportDataByDateRange(startTime, endTime, "days");
    }

    public List<ReportItem> getReportDataByDateRange(Date startTime, Date endTime){
        dateFormater = new SimpleDateFormat("yyyy-MM-dd");

        return getReportDataByDateRange(startTime, endTime, "days");
    }
    private List<ReportItem> getReportDataByDateRange(Date startTime, Date endTime, String period){
        List<DonHang> listOrders = repo.findByOrderBetween(startTime, endTime);
        printRawData(listOrders);
        List<ReportItem> listReportItems = createReportData(startTime, endTime, period);
        System.out.println();
        calculateSalesForReportData(listOrders, listReportItems);
        printReportData(listReportItems);
        return listReportItems;
    }

    private void calculateSalesForReportData(List<DonHang> listOrders, List<ReportItem> listReportItems){
        for(DonHang order: listOrders){
            String orderDateString = dateFormater.format(order.getNgayTao());
            ReportItem reportItem = new ReportItem(orderDateString);
            int itemIndex = listReportItems.indexOf(reportItem);
            for (ReportItem item : listReportItems) {
                if (item.getGrossSales() == null || item.getNetSales() == null) {
                    item.setGrossSales(0.0);
                    item.setNetSales(0.0);
                }
            }
            if (itemIndex >=0){
                reportItem = listReportItems.get(itemIndex);
                reportItem.addGrossSales(order.getTongTien());
                reportItem.addNetSales(order.getTongTien() - order.getPhiVanChuyen());
                reportItem.increaseOrderCount();
            }

        }
    }
    private void printReportData(List<ReportItem> listReportItems) {
        listReportItems.forEach(item ->{
            System.out.printf("%s, %.3f, %.3f, %d \n", item.getIdentifier(),
                    item.getGrossSales(), item.getNetSales(), item.getOrdersCount());
        });
    }

    private List<ReportItem> createReportData(Date startTime, Date endTime, String period) {
        List<ReportItem> listReportItems = new ArrayList<>();
        Calendar startDate = Calendar.getInstance();
        startDate.setTime(startTime);
        Calendar endDate = Calendar.getInstance();
        endDate.setTime(endTime);
        Date currentDate = startDate.getTime();
        String dateString = dateFormater.format(currentDate);

        listReportItems.add(new ReportItem(dateString));

        do {
            if (period.equals("days")) {
                startDate.add(Calendar.DAY_OF_MONTH, 1);
            } else if (period.equals("months")){
                startDate.add(Calendar.MONTH, 1);
            }
            currentDate = startDate.getTime();
            dateString = dateFormater.format(currentDate);
            listReportItems.add(new ReportItem(dateString));
        } while (startDate.before(endDate));
        return listReportItems;
    }


    private void printRawData(List<DonHang> listOrders){
        listOrders.forEach(order ->{
            System.out.printf("%-3d | %s | %.3f | %.3f \n",
                    order.getIdDonHang(),
                    order.getNgayTao(),
                    order.getTongTien(),
                    order.getPhiVanChuyen());
        });
    }

    public List<ReportItem> getReportDataLast6Months() {
        return getReportDataLastXMonths(6);
    }

    public List<ReportItem> getReportDataLastYear() {
        return getReportDataLastXMonths(12);
    }
    private List<ReportItem> getReportDataLastXMonths(int months){
        Date endTime = new Date();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -(months -1));
        Date startTime = cal.getTime();
        System.out.println("Start time: " + startTime);
        System.out.println("End time: " + endTime);
        dateFormater = new SimpleDateFormat("yyyy-MM");
        return getReportDataByDateRange(startTime, endTime, "months");
    }
}
