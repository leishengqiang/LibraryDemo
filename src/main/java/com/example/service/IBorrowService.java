package com.example.service;

import com.example.entity.Borrow;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.vo.AdminBorrowVO;
import com.example.vo.BorrowVO;
import com.example.vo.PageVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lei
 * @since 2024-11-09
 */
public interface IBorrowService extends IService<Borrow> {
    /**
     * 用户新增借阅图书
     * @param uid
     * @param bid
     */
    public void add(Integer uid,Integer bid);

    /**
     * 查询用户借阅信息
     * @param uid
     * @return
     */
    public List<BorrowVO> borrowList(Integer uid);

    /**
     *查询用户借阅信息及归还
     * @param uid
     * @return
     */
    public List<BorrowVO> backList(Integer uid);

    /**
     * 管理员查询借书记录
     * @return
     */
    public List<AdminBorrowVO> adminborrowList();

}
