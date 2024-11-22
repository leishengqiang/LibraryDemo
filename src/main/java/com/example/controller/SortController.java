package com.example.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.entity.Back;
import com.example.entity.Book;
import com.example.entity.Borrow;
import com.example.entity.Sort;
import com.example.service.IBackService;
import com.example.service.IBookService;
import com.example.service.IBorrowService;
import com.example.service.ISortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/sort")
public class SortController {@Autowired
private ISortService sortService;
    @Autowired
    private IBookService bookService;
    @Autowired
    private IBorrowService borrowService;
    @Autowired
    private IBackService backService;

    @GetMapping("/list")
    public String list(Model model){
        model.addAttribute("list", this.sortService.list());
        return "sysadmin/addBook";
    }

    @PostMapping("/search")
    public String search(String name,Model model){
        QueryWrapper<Sort> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(name), "name", name);
        List<Sort> list = this.sortService.list(queryWrapper);
        model.addAttribute("list", list);
        return "/sysadmin/sort";
    }

    @PostMapping("/add")
    public String add(Sort sort){
        this.sortService.save(sort);
        return "redirect:/sysadmin/sortList";
    }

    @GetMapping("/findById/{id}")
    public String findById(@PathVariable("id") Integer id, Model model){
        Sort sort = this.sortService.getById(id);
        model.addAttribute("sort", sort);
        return "/sysadmin/updateSort";
    }

    @PostMapping("/update")
    public String update(Sort sort){
        this.sortService.updateById(sort);
        return "redirect:/sysadmin/sortList";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id){
        QueryWrapper<Book> bookQueryWrapper = new QueryWrapper<>();
        bookQueryWrapper.eq("sid", id);
        List<Book> bookList = this.bookService.list(bookQueryWrapper);
        for (Book book : bookList) {
            QueryWrapper<Borrow> borrowQueryWrapper = new QueryWrapper<>();
            borrowQueryWrapper.eq("bid", book.getId());
            List<Borrow> borrowList = this.borrowService.list(borrowQueryWrapper);
            for (Borrow borrow : borrowList) {
                QueryWrapper<Back> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("brid", borrow.getId());
                this.backService.remove(queryWrapper);
                this.borrowService.removeById(borrow.getId());
            }
            this.bookService.removeById(book.getId());
        }
        this.sortService.removeById(id);
        return "redirect:/sysadmin/sortList";
    }
}
