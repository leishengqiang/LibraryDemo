package com.example.service;

import com.example.entity.Book;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.vo.PageVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lei
 * @since 2024-11-09
 */
public interface IBookService extends IService<Book> {

    /**
     * 查询所有图书及分页
     * @param currentPage
     * @return
     */
    public PageVO pageList(Integer currentPage);

    /**
     * 根据关键词检索及分页
     * @param keyWord
     * @param currentPage
     * @return
     */
    public PageVO searchByKeyWord(String keyWord,Integer currentPage);

    /**
     * 分类检索
     * @param sid
     * @param currentPage
     * @return
     */
    public PageVO searchBySort(Integer sid,Integer currentPage);
}
