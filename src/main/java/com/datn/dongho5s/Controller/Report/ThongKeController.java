package com.datn.dongho5s.Controller.Report;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Controller
public class ThongKeController {
    @GetMapping("/statisticals")
    public String viewSalesReportHomes(){
        return "admin/thongke/statisticals";
    }
}
