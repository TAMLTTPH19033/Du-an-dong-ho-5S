package com.datn.dongho5s.Controller.DonHang;

import com.datn.dongho5s.Entity.DonHang;
import com.datn.dongho5s.Service.DonHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@RequestMapping("/don-hang")
@Controller
public class DonHangController {

    @Autowired
    private DonHangService donHangService;

    @GetMapping
    public String getForm(Model model){
        return findAll(1,model);
    }

    @GetMapping("/page/{pageNum}")
    public String findAll(@PathVariable("pageNum") int pageNum, Model model){
        model.addAttribute("list",donHangService.getAll(pageNum));

        return "donhang/donhang";
//        return ResponseEntity.status(HttpStatus.OK).body(donHangService.getAll(pageNum));
    }

    @GetMapping("/get/{id}")
    public void getById(
            @PathVariable("id") int id,
            HttpSession session
    ){
        session.setAttribute("donHang",donHangService.findById(id));
    }

    @PutMapping("/update")
    public void updateStatusDonHang(HttpSession session){
        DonHang donHang = (DonHang) session.getAttribute("donHang");
        donHangService.updateTrangThaiDonHang(donHang);
    }
}
