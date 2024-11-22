package com.example.controller;


import com.example.entity.Back;
import com.example.entity.Book;
import com.example.entity.Borrow;
import com.example.entity.User;
import com.example.service.IBackService;
import com.example.service.IBookService;
import com.example.service.IBorrowService;
import com.example.vo.BackVO;
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
@RequestMapping("/back")
public class BackController {

    @Autowired
    private IBorrowService borrowService;
    @Autowired
    private IBackService backService;
    @Autowired
    private IBookService bookService;

    @GetMapping("/list")
    public String list(HttpSession session, Model model){
        User user = (User) session.getAttribute("user");
        List<BorrowVO> backList = this.borrowService.backList(user.getId());
        model.addAttribute("list",backList);
        return "/user/back";
    }

    @GetMapping("/add")
    public String add(Integer id){
        Back back=new Back();
        back.setBrid(id);
        this.backService.save(back);
        Borrow borrow = this.borrowService.getById(id);
        borrow.setStatus(3);
        this.borrowService.updateById(borrow);
        return "redirect:/back/list";
    }

    @GetMapping("/adminList")
    public String adminList(Model model){
        List<BackVO> backVOS = this.backService.adminList();
        model.addAttribute("list",backVOS);
        return "admin/back";
    }

    @GetMapping("/allow")
    public String backAllow(Integer id){
        Back back = this.backService.getById(id);
        back.setStatus(1);
        Borrow borrow = this.borrowService.getById(back.getBrid());
        Book book = this.bookService.getById(borrow.getBid());
        book.setNumber(book.getNumber()+1);
        bookService.updateById(book);
        backService.updateById(back);
        return "redirect:/back/adminList";
    }
}
