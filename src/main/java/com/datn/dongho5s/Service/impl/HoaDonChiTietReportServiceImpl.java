package com.datn.dongho5s.Service.impl;

import com.datn.dongho5s.Entity.DonHang;
import com.datn.dongho5s.Entity.HoaDonChiTiet;
import com.datn.dongho5s.Entity.ReportItem;
import com.datn.dongho5s.Entity.ReportType;
import com.datn.dongho5s.Repository.HoaDonChiTietRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class HoaDonChiTietReportServiceImpl extends AbstractReportService {

	@Autowired private HoaDonChiTietRepository repo;
	
	@Override
	protected List<ReportItem> getReportDataByDateRangeInternal(
			Date startDate, Date endDate, ReportType reportType) {
		List<HoaDonChiTiet> listHoaDonChiTiets = null;
		
		if (reportType.equals(ReportType.CATEGORY)) {
			listHoaDonChiTiets = repo.findWithCategoryAndTimeBetween(startDate, endDate);
		} else if (reportType.equals(ReportType.PRODUCT)) {
			listHoaDonChiTiets = repo.findWithProductAndTimeBetween(startDate, endDate);
		}

		//printRawData(listHoaDonChiTiets);

		Set<String> identifiers = new HashSet<>();
		List<ReportItem> listReportItems = new ArrayList<>();
		for (HoaDonChiTiet detail : listHoaDonChiTiets) {
			String identifier = "";

			if (reportType.equals(ReportType.CATEGORY)) {
				identifier = detail.getChiTietSanPham().getSanPham().getDanhMuc().getTen();
			} else if (reportType.equals(ReportType.PRODUCT)) {
				identifier = detail.getChiTietSanPham().getSanPham().getTenSanPham();
			}

			if (!identifiers.contains(identifier)) {
				// Add a new ReportItem for each unique identifier
				listReportItems.add(new ReportItem(identifier));
				identifiers.add(identifier);
			}
		}

		// Now perform the data processing
		for (HoaDonChiTiet detail : listHoaDonChiTiets) {
			String identifier = "";

			if (reportType.equals(ReportType.CATEGORY)) {
				identifier = detail.getChiTietSanPham().getSanPham().getDanhMuc().getTen();
			} else if (reportType.equals(ReportType.PRODUCT)) {
				identifier = detail.getChiTietSanPham().getSanPham().getTenSanPham();
			}

			ReportItem reportItem = new ReportItem(identifier);
			double netSales = detail.getGiaBan() * detail.getSoLuong() ;
			double grossSales = netSales + (detail.getDonHang().getPhiVanChuyen());


			int itemIndex = listReportItems.indexOf(reportItem);

			if (itemIndex >= 0) {
				reportItem = listReportItems.get(itemIndex);
				reportItem.addGrossSales(grossSales);
				reportItem.addNetSales(netSales);
				reportItem.increaseProductsCount(detail.getSoLuong());
			} else {
				listReportItems.add(new ReportItem(identifier, grossSales, netSales, detail.getSoLuong()));
			}
		}

		// Other print statements or code
		//printRawData();
		//printReportData();
		//printReportData(listReportItems);

		return listReportItems;
	}

	private void printReportData(List<ReportItem> listReportItems) {
		for (ReportItem item : listReportItems) {
			System.out.printf("%-20s, %.3f, %.3f, %d \n",
					item.getIdentifier(), item.getGrossSales(), item.getNetSales(), item.getOrdersCount());
		}
	}

	private void printRawData(List<HoaDonChiTiet> listHoaDonChiTiets) {
		for (HoaDonChiTiet detail : listHoaDonChiTiets) {
			System.out.printf("%d, %-20s, %.3f, %.3f \n",
					detail.getSoLuong(), detail.getChiTietSanPham().getSanPham().getTenSanPham().substring(0, 20),
					(detail.getGiaBan()*detail.getSoLuong()), detail.getDonHang().getPhiVanChuyen());
		}
	}

}
