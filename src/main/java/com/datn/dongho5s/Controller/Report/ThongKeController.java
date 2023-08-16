package com.datn.dongho5s.Controller.Report;

import com.datn.dongho5s.Entity.DonHang;
import com.datn.dongho5s.Entity.StatusValue;
import com.datn.dongho5s.Service.DonHangService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Controller
public class ThongKeController {
    private DonHangService service;
    @GetMapping("/admin/statisticals")
    public String viewSalesReportHome(Model model){
//        List<StatusValue> statusValues = Arrays.asList(
//                new StatusValue(1, "Chờ giao hàng"),
//                new StatusValue(2, "Đang giao hàng"),
//                new StatusValue(3, "Đã hoàn tất"),
//                new StatusValue(4, "Đã hủy"),
//                new StatusValue(5, "Muốn hoàn trả"),
//                new StatusValue(6, "Đã hoàn trả")
//
//        );
//
//        model.addAttribute("statusValues", statusValues);
        return "admin/thongke/statisticals";

    }


}
