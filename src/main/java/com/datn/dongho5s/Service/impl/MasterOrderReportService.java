package com.datn.dongho5s.Service.impl;
import com.datn.dongho5s.Entity.ReportItem;
import com.datn.dongho5s.Entity.DonHang;
import com.datn.dongho5s.Entity.ReportType;
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
public class MasterOrderReportService extends AbstractReportService {
    @Autowired private DonHangRepository repo;

    protected List<ReportItem> getReportDataByDateRangeInternal(Date startTime, Date endTime, ReportType reportType) {
        List<DonHang> listOrders = repo.findByOrderBetween(startTime, endTime);
        printRawData(listOrders);
        List<ReportItem> listReportItems = createReportData(startTime, endTime, reportType);

        System.out.println();

        calculateSalesForReportData(listOrders, listReportItems);

        printReportData(listReportItems);

        return listReportItems;
    }


//    public List<ReportItem> getReportDataByDateRange(Date startTime, Date endTime){
//        return getReportDataByDateRangeR(startTime, endTime, "days");
//    }
//    public List<ReportItem> getReportDataByMonthRange(Date startTime, Date endTime){
//        return getReportDataByDateRangeR(startTime, endTime, "months");
//    }
//    public List<ReportItem> getReportDataByYearRange(Date startTime, Date endTime){
//        return getReportDataByDateRangeR(startTime, endTime, "years");
//    }


    private void calculateSalesForReportData(List<DonHang> listOrders, List<ReportItem> listReportItems){
        for(DonHang order: listOrders){
            String orderDateString = dateFormatter.format(order.getNgayTao());
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

    private List<ReportItem> createReportData(Date startTime, Date endTime, ReportType reportType) {
        List<ReportItem> listReportItems = new ArrayList<>();
        Calendar startDate = Calendar.getInstance();
        startDate.setTime(startTime);
        Calendar endDate = Calendar.getInstance();
        endDate.setTime(endTime);

        Date currentDate = startDate.getTime();
        String dateString = dateFormatter.format(currentDate);

        do {
            if (reportType.equals(ReportType.DAY)) {
                startDate.add(Calendar.DAY_OF_MONTH, 1);
            } else if (reportType.equals(ReportType.MONTH)) {
                startDate.add(Calendar.MONTH, 1);
            }
            currentDate = startDate.getTime();
            dateString = dateFormatter.format(currentDate);
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


//    public List<ReportItem> getReportDataLastXYears(int years){
//        Date endTime = new Date();
//        Calendar cal = Calendar.getInstance();
//        cal.add(Calendar.YEAR, -(years -1));
//        Date startTime = cal.getTime();
//        System.out.println("Start time: " + startTime);
//        System.out.println("End time: " + endTime);
//        dateFormater = new SimpleDateFormat("yyyy");
//        return getReportDataByDateRangeR(startTime, endTime, "years");
//    }
}
