package com.example.vo;

import lombok.Data;

import java.util.List;

@Data
public class PageVO {
    private Long currentPage;//当前页面
    private Long totalPage;//总
    private List data;
}
