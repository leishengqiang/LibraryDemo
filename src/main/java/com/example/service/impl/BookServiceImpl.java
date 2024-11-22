package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.entity.Book;
import com.example.entity.Sort;
import com.example.mapper.BookMapper;
import com.example.mapper.SortMapper;
import com.example.service.IBookService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.vo.BookVO;
import com.example.vo.PageVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lei
 * @since 2024-11-09
 */
@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements IBookService {

    @Autowired
    private BookMapper bookMapper;
    @Autowired
    private SortMapper sortMapper;

    /*•eq：等于 (=)
        •ne：不等于 (<>)
        •lt：小于 (<)
        •le：小于等于 (<=)
        •ge：大于等于 (>=)
        •like：模糊匹配 (LIKE)
        •notLike：不模糊匹配 (NOT LIKE)
        •in：在某个集合中 (IN)
        •notIn：不在某个集合中 (NOT IN)
        •isNull：为空 (IS NULL)
        •isNotNull：不为空 (IS NOT NULL)*/
    @Override
    public PageVO pageList(Integer currentPage) {
        //查询全部
        //创建的分叶查询的QueryWrapper对象
        QueryWrapper<Book> bookQueryWrapper = new QueryWrapper<>();
        bookQueryWrapper.gt("number", 0);//.gt  大于
        //创建分页对象
        Page<Book> page = new Page<>(currentPage, 5);
        Page<Book> resultPage = this.bookMapper.selectPage(page, bookQueryWrapper);
        PageVO pageVO = new PageVO();
        pageVO.setCurrentPage(resultPage.getCurrent());
        pageVO.setTotalPage(resultPage.getPages());

        //分类查询
        //创建集合存储查结果
        List<BookVO> result = new ArrayList<>();
        for (Book book : resultPage.getRecords()) {
            BookVO bookVO = new BookVO();
            //使用 BeanUtils.copyProperties 将 Book 对象的属性复制到 BookVO 对象
            BeanUtils.copyProperties(book, bookVO);
            //创建的分类查询的QueryWrapper对象
            QueryWrapper<Sort> sortQueryWrapper = new QueryWrapper<>();
            //根据分类的id查询
            sortQueryWrapper.eq("id", book.getSid());
            Sort sort = this.sortMapper.selectOne(sortQueryWrapper);
            bookVO.setSname(sort.getName());
            result.add(bookVO);
        }
        pageVO.setData(result);
        return pageVO;
    }

    @Override
    public PageVO searchByKeyWord(String keyWord,Integer currentPage) {
        QueryWrapper<Book> bookQueryWrapper = new QueryWrapper<>();
        bookQueryWrapper.gt("number", 0);
        bookQueryWrapper.like(StringUtils.isNotBlank(keyWord), "name", keyWord)
                .or()
                .like(StringUtils.isNotBlank(keyWord), "author", keyWord)
                .or()
                .like(StringUtils.isNotBlank(keyWord), "publish", keyWord);
        Page<Book> page = new Page<>(currentPage, 5);
        Page<Book> resultPage = this.bookMapper.selectPage(page, bookQueryWrapper);
        PageVO pageVO = new PageVO();
        pageVO.setCurrentPage(resultPage.getCurrent());
        pageVO.setTotalPage(resultPage.getPages());
        List<BookVO> result = new ArrayList<>();
        for (Book book : resultPage.getRecords()) {
            BookVO bookVO = new BookVO();
            BeanUtils.copyProperties(book, bookVO);
            QueryWrapper<Sort> sortQueryWrapper = new QueryWrapper<>();
            sortQueryWrapper.eq("id", book.getSid());
            Sort sort = this.sortMapper.selectOne(sortQueryWrapper);
            bookVO.setSname(sort.getName());
            result.add(bookVO);
        }
        pageVO.setData(result);
        return pageVO;
    }

    @Override
    public PageVO searchBySort(Integer sid, Integer currentPage) {
        QueryWrapper<Book> bookQueryWrapper = new QueryWrapper<>();
        bookQueryWrapper.gt("number", 0);
        bookQueryWrapper.eq("sid",sid);
        Page<Book> page = new Page<>(currentPage, 5);
        Page<Book> resultPage = this.bookMapper.selectPage(page, bookQueryWrapper);
        PageVO pageVO = new PageVO();
        pageVO.setCurrentPage(resultPage.getCurrent());
        pageVO.setTotalPage(resultPage.getPages());
        List<BookVO> result = new ArrayList<>();
        for (Book book : resultPage.getRecords()) {
            BookVO bookVO = new BookVO();
            BeanUtils.copyProperties(book, bookVO);
            QueryWrapper<Sort> sortQueryWrapper = new QueryWrapper<>();
            sortQueryWrapper.eq("id", book.getSid());
            Sort sort = this.sortMapper.selectOne(sortQueryWrapper);
            bookVO.setSname(sort.getName());
            result.add(bookVO);
        }
        pageVO.setData(result);
        return pageVO;
    }
}
