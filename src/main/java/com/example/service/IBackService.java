package com.example.service;

import com.example.entity.Back;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.vo.BackVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lei
 * @since 2024-11-09
 */
public interface IBackService extends IService<Back> {

    List<BackVO> adminList();
}
