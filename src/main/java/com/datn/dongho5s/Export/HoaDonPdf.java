package com.datn.dongho5s.Export;

import com.datn.dongho5s.Cache.DiaChiCache;
import com.datn.dongho5s.Entity.DonHang;
import com.datn.dongho5s.Entity.HoaDonChiTiet;
import com.datn.dongho5s.GiaoHangNhanhService.DiaChiAPI;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class HoaDonPdf {

    public void exportToPDF(
            HttpServletResponse response,
            List<HoaDonChiTiet> lst,
            DonHang donHang
    ) throws Exception {

        DiaChiAPI diaChiAPI = new DiaChiAPI();
        DiaChiCache diaChiCache = new DiaChiCache();

        // Tạo tài liệu PDF mới
        Document document = new Document();

        // Thiết lập tên file khi xuất ra
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=\"HD" + donHang.getIdDonHang() + ".pdf\"");

        // Tạo đối tượng PdfWriter để ghi dữ liệu vào tài liệu PDF
        PdfWriter.getInstance(document, response.getOutputStream());

        // Mở tài liệu PDF để bắt đầu viết
        document.open();

        // Thiết kế tài liệu PDF giống như đoạn mã HTML
        Paragraph header1 = new Paragraph("Dong ho 5S");
        header1.setAlignment(Element.ALIGN_CENTER);
        header1.setFont(FontFactory.getFont(FontFactory.TIMES_BOLD, 16, Font.BOLD));

        String diaChi = donHang.getDiaChi() + ", " +
                diaChiAPI.callGetPhuongXaAPI(donHang.getIdQuanHuyen()).get(donHang.getIdPhuongXa()) + ", " +
                diaChiAPI.callGetQuanHuyenAPI(donHang.getIdTinhThanh()).get(donHang.getIdQuanHuyen()) + ", " +
                diaChiCache.hashMapTinhThanh.get(donHang.getIdTinhThanh());

        Paragraph header2 = new Paragraph(diaChi);
        header2.setAlignment(Element.ALIGN_CENTER);
        header2.setFont(FontFactory.getFont(FontFactory.TIMES_ITALIC, 12));

        Paragraph header3 = new Paragraph("Hoa don thanh toan");
        header3.setAlignment(Element.ALIGN_CENTER);
        header3.setFont(FontFactory.getFont(FontFactory.TIMES_BOLD, 14, Font.BOLD));

        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);

        PdfPCell cell1 = new PdfPCell(new Phrase("Ten san pham"));
        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);

        PdfPCell cell2 = new PdfPCell(new Phrase("So Luong"));
        cell2.setHorizontalAlignment(Element.ALIGN_CENTER);

        PdfPCell cell3 = new PdfPCell(new Phrase("Don gia"));
        cell3.setHorizontalAlignment(Element.ALIGN_CENTER);

        PdfPCell cell4 = new PdfPCell(new Phrase("Thanh tien"));
        cell4.setHorizontalAlignment(Element.ALIGN_CENTER);

        table.addCell(cell1);
        table.addCell(cell2);
        table.addCell(cell3);
        table.addCell(cell4);

        // Lấy danh sách hóa đơn chi tiết từ model
        //List<HoaDonChiTiet> lstHDCT = (List<HoaDonChiTiet>) model.getAttribute("lstHDCT");

        // Thêm dữ liệu vào bảng
        for (HoaDonChiTiet item : lst) {
            PdfPCell cell5 = new PdfPCell(new Phrase(item.getChiTietSanPham().getSanPham().getTenSanPham()));
            PdfPCell cell6 = new PdfPCell(new Phrase("x" + item.getSoLuong()));
            PdfPCell cell7 = new PdfPCell(new Phrase(item.getGiaBan() + "d"));
            PdfPCell cell8 = new PdfPCell(new Phrase((item.getGiaBan() * item.getSoLuong()) + "d"));

            table.addCell(cell5);
            table.addCell(cell6);
            table.addCell(cell7);
            table.addCell(cell8);
        }

        // Tạo footer
        Paragraph footer = new Paragraph("Chuc quy khach vui ve! Hen gap lai!");
        footer.setAlignment(Element.ALIGN_CENTER);
        footer.setFont(FontFactory.getFont(FontFactory.TIMES_ITALIC, 12));


        // Đơn giá các sản phẩm
        Paragraph donGia = new Paragraph("Don gia cac san pham " + (donHang.getTongTien()) + "d");
        donGia.setAlignment(Element.ALIGN_RIGHT);
        donGia.setFont(FontFactory.getFont(FontFactory.TIMES_BOLD, 12, Font.BOLD));

        // phí vận chuyển
        Paragraph pvc = new Paragraph("Phi van chuyem " + (donHang.getPhiVanChuyen()) + "d");
        pvc.setAlignment(Element.ALIGN_RIGHT);
        pvc.setFont(FontFactory.getFont(FontFactory.TIMES_BOLD, 12, Font.BOLD));

        // Tổng cộng tiền
        Paragraph tongCong = new Paragraph("Tong tien: " + (donHang.getTongTien() + donHang.getPhiVanChuyen()) + "d");
        tongCong.setAlignment(Element.ALIGN_RIGHT);
        tongCong.setFont(FontFactory.getFont(FontFactory.TIMES_BOLD, 12, Font.BOLD));

        // Thêm các phần tử vào tài liệu PDF
        document.add(header1);
        document.add(header2);
        document.add(header3);
        document.add(table);
        document.add(donGia);
        document.add(pvc);
        document.add(tongCong);
        document.add(footer);

        // Đóng tài liệu PDF
        document.close();
    }
}
