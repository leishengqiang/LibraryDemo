package com.example.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.entity.Book;
import com.example.entity.Borrow;
import com.example.entity.Sort;
import com.example.service.IBookService;
import com.example.service.IBorrowService;
import com.example.service.ISortService;
import com.example.vo.BookVO;
import com.example.vo.PageVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
@RequestMapping("/book")
public class BookController {
    @Autowired
    private IBookService bookService;
    @Autowired
    private ISortService sortService;
    @Autowired
    private IBorrowService borrowService;

    @GetMapping("/list/{current}")
    public String list(@PathVariable("current") Integer current, Model model){
        //自定义pagelist方法，查询所有图书并封装进PageVo
        PageVO pageVO = this.bookService.pageList(current);
        //展示所有图书
        model.addAttribute("page", pageVO);
        //进行分类再展示
        model.addAttribute("sortList", this.sortService.list());
        return "/user/list";
    }

    @PostMapping("/search")
    public String search(String keyWord,Integer current,Integer sid,Model model){
        PageVO pageVO = null;
        //类别检索
        if(!sid.equals(0)){
            pageVO = this.bookService.searchBySort(sid, current);
        } else {
            //关键字检索带分页
            pageVO = this.bookService.searchByKeyWord(keyWord, current);
        }
        model.addAttribute("page", pageVO);
        model.addAttribute("sortList", this.sortService.list());
        return "/user/list";
    }

    @PostMapping("/findByKey")
    public String findByKey(String key,Model model){
        QueryWrapper<Book> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(key), "name", key)
                .or()
                .like(StringUtils.isNotBlank(key), "author", key)
                .or()
                .like(StringUtils.isNotBlank(key), "publish", key);
        List<Book> list = this.bookService.list(queryWrapper);
        List<BookVO> bookVOList = new ArrayList<>();
        for (Book book : list) {
            BookVO bookVO = new BookVO();
            BeanUtils.copyProperties(book, bookVO);
            Sort sort = this.sortService.getById(book.getSid());
            bookVO.setSname(sort.getName());
            bookVOList.add(bookVO);
        }
        model.addAttribute("list", bookVOList);
        return "/sysadmin/book";
    }

    @PostMapping("/add")
    public String add(Book book){
        this.bookService.save(book);
        return "redirect:/sysadmin/bookList";
    }

    @GetMapping("/findById/{id}")
    public String findById(@PathVariable("id") Integer id,Model model){
        Book book = this.bookService.getById(id);
        model.addAttribute("book", book);
        model.addAttribute("list", this.sortService.list());
        return "/sysadmin/updateBook";
    }

    @PostMapping("/update")
    public String update(Book book){
        this.bookService.updateById(book);
        return "redirect:/sysadmin/bookList";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id){
        QueryWrapper<Borrow> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("bid", id);
        this.borrowService.remove(queryWrapper);
        this.bookService.removeById(id);
        return "redirect:/sysadmin/bookList";
    }
}
