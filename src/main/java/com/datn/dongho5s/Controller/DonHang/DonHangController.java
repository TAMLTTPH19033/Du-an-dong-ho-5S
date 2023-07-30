package com.datn.dongho5s.Controller.DonHang;

import com.datn.dongho5s.Cache.DiaChiCache;
import com.datn.dongho5s.Entity.DonHang;
import com.datn.dongho5s.GiaoHangNhanhService.DiaChiAPI;
import com.datn.dongho5s.Service.DonHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RequestMapping("/don-hang")
@Controller
public class DonHangController {

    @Autowired
    private DonHangService donHangService;

    @GetMapping
    public String getForm(Model model,
                          HttpSession httpSession) {
        return findAll(1, model,httpSession);
    }

    @GetMapping("/page/{pageNum}")
    public String findAll(
            @PathVariable("pageNum") int pageNum,
            Model model,
            HttpSession httpSession
    ) {
        Page<DonHang> donHangs = donHangService.getAll(pageNum);

        model.addAttribute("list", donHangs.getContent());

        model.addAttribute("diaChiCache", new DiaChiCache());
        model.addAttribute("diaChiAPI", new DiaChiAPI());
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", donHangs.getTotalPages());

//        httpSession.setAttribute("listDonHang",donHangs.getContent());

        return "admin/donhang/donhang";
    }

    @GetMapping("/search/date")
    public void searchByDateStartanDateEnd(
        HttpSession httpSession,
        Model model,
        HttpServletRequest httpServletRequest
    ) {
        String dateStart = httpServletRequest.getParameter("dateStart");
        String dateEnd = httpServletRequest.getParameter("dateEnd");

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date dateStartParse = null;
        Date dateEndParse = null;

        try {
            dateStartParse = format.parse(dateStart);
            dateEndParse = format.parse(dateEnd);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        List<DonHang> lst = donHangService.findByNgayTao(dateStartParse,dateEndParse);

        model.addAttribute("list",lst);
//        return "admin/donhang/donhang";
    }

    @GetMapping("/get/{id}")
    public void getById(
            @PathVariable("id") int id,
            HttpSession session
    ) {
        System.out.println(id);
        session.setAttribute("donHang", donHangService.findById(id));
    }

    @PutMapping("/update/{trangThai}")
    public String updateStatusDonHang(
            HttpSession session,
            @PathVariable("trangThai") int trangThai
    ) {
        DonHang donHang = (DonHang) session.getAttribute("donHang");
        donHang.setTrangThaiDonHang(trangThai);
        donHangService.updateTrangThaiDonHang(donHang);
        return "donhang/donhang";
    }
}
