package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.entity.Back;
import com.example.entity.Book;
import com.example.entity.Borrow;
import com.example.entity.User;
import com.example.mapper.BackMapper;
import com.example.mapper.BookMapper;
import com.example.mapper.BorrowMapper;
import com.example.mapper.UserMapper;
import com.example.service.IBackService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.vo.BackVO;
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
public class BackServiceImpl extends ServiceImpl<BackMapper, Back> implements IBackService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private BookMapper bookMapper;
    @Autowired
    private BorrowMapper borrowMapper;
    @Autowired
    private BackMapper backMapper;

    @Override
    public List<BackVO> adminList() {
        QueryWrapper<Back> backQueryWrapper=new QueryWrapper<>();
        backQueryWrapper.eq("status",0);
        List<Back> backs = this.backMapper.selectList(backQueryWrapper);
        List<BackVO> backVOS=new ArrayList<>();
        for (Back back : backs) {
            BackVO backVO=new BackVO();
            Borrow borrow = this.borrowMapper.selectById(back.getBrid());
            Book book = this.bookMapper.selectById(borrow.getBid());
            BeanUtils.copyProperties(book,backVO);
            User user = this.userMapper.selectById(borrow.getUid());
            backVO.setUserName(user.getUsername());
            backVO.setBookName(book.getName());
            BeanUtils.copyProperties(back,backVO);
            backVOS.add(backVO);
        }
        return backVOS;
    }
}
