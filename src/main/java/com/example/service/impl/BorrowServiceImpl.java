package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.entity.Book;
import com.example.entity.Borrow;
import com.example.entity.Sort;
import com.example.entity.User;
import com.example.mapper.BookMapper;
import com.example.mapper.BorrowMapper;
import com.example.mapper.SortMapper;
import com.example.mapper.UserMapper;
import com.example.service.IBorrowService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.vo.AdminBorrowVO;
import com.example.vo.BorrowVO;
import com.example.vo.PageVO;
import lombok.val;
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
public class BorrowServiceImpl extends ServiceImpl<BorrowMapper, Borrow> implements IBorrowService {

    @Autowired
    private BorrowMapper borrowMapper;
    @Autowired
    private BookMapper bookMapper;
    @Autowired
    private SortMapper sortMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public void add(Integer uid, Integer bid) {
        Borrow borrow = new Borrow();
        borrow.setBid(bid);
        borrow.setUid(uid);
        this.borrowMapper.insert(borrow);
        Book book = bookMapper.selectById(bid);
        book.setNumber(book.getNumber() - 1);
        this.bookMapper.updateById(book);
    }

    @Override
    public List<BorrowVO> borrowList(Integer uid) {
        QueryWrapper<Borrow> borrowQueryWrapper = new QueryWrapper<>();
        borrowQueryWrapper.eq("uid", uid);
        List<Borrow> borrowList = this.borrowMapper.selectList(borrowQueryWrapper);
        List<BorrowVO> borrowVOList = new ArrayList<>();
        for (Borrow borrow : borrowList) {
            BorrowVO borrowVO = new BorrowVO();
            BeanUtils.copyProperties(borrow, borrowVO);
            Book book = this.bookMapper.selectById(borrow.getBid());
            BeanUtils.copyProperties(book, borrowVO);
            borrowVO.setBookName(book.getName());
            Sort sort = this.sortMapper.selectById(book.getSid());
            borrowVO.setSortName(sort.getName());
            borrowVOList.add(borrowVO);
        }
        return borrowVOList;
    }

    @Override
    public List<BorrowVO> backList(Integer uid) {
        QueryWrapper<Borrow> borrowQueryWrapper = new QueryWrapper<>();
        borrowQueryWrapper.eq("uid", uid);
        borrowQueryWrapper.eq("status", 1);
        List<Borrow> borrowList = this.borrowMapper.selectList(borrowQueryWrapper);
        List<BorrowVO> borrowVOList = new ArrayList<>();
        for (Borrow borrow : borrowList) {
            BorrowVO borrowVO = new BorrowVO();
            BeanUtils.copyProperties(borrow, borrowVO);
            Book book = this.bookMapper.selectById(borrow.getBid());
            BeanUtils.copyProperties(book, borrowVO);
            borrowVO.setId(borrow.getId());
            borrowVO.setBookName(book.getName());
            borrowVOList.add(borrowVO);
        }
        return borrowVOList;
    }

    @Override
    public List<AdminBorrowVO> adminborrowList() {
        QueryWrapper<Borrow> borrowQueryWrapper = new QueryWrapper<>();
        borrowQueryWrapper.eq("status", 0);
        List<Borrow> borrows = this.borrowMapper.selectList(borrowQueryWrapper);
        List<AdminBorrowVO> adminBorrowVOS = new ArrayList<>();
        for (Borrow borrow : borrows) {
            AdminBorrowVO adminBorrowVO = new AdminBorrowVO();
            BeanUtils.copyProperties(borrow, adminBorrowVO);
            User user = this.userMapper.selectById(borrow.getUid());
            adminBorrowVO.setUserName(user.getUsername());
            Book book = this.bookMapper.selectById(borrow.getBid());
            BeanUtils.copyProperties(book, adminBorrowVO);
            Sort sort = this.sortMapper.selectById(book.getSid());
            adminBorrowVO.setSortName(sort.getName());
            adminBorrowVO.setId(borrow.getId());
            adminBorrowVO.setBookName(book.getName());
            adminBorrowVOS.add(adminBorrowVO);
        }
        return adminBorrowVOS;
    }

}