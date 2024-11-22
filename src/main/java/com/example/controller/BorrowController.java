package com.example.controller;


import com.example.entity.User;
import com.example.service.IBorrowService;
import com.example.vo.BorrowVO;
import com.example.vo.PageVO;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lei
 * @since 2024-11-09
 */
@Controller
@RequestMapping("/borrow")
public class BorrowController {
    @Autowired
    private IBorrowService borrowService;

    @GetMapping("/add")
    public String add(Integer bookId, HttpSession session){
        User user = (User) session.getAttribute("user");
        this.borrowService.add(user.getId(),bookId);
        return "redirect:/borrow/list";
    }

    @GetMapping("/list")
    public String list(HttpSession session, Model model){
        User user = (User) session.getAttribute("user");
        List<BorrowVO> borrowVOList = this.borrowService.borrowList(user.getId());
        model.addAttribute("list",borrowVOList);
        return "/user/borrow";
    }

}
