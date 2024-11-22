package com.example.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AdminBorrowVO {
    private Integer id;
    private String userName;
    private String bookName;
    private String sortName;
    private String author;
    private String publish;
    private String edition;
    private LocalDateTime startTime;
    private Integer status = 0;
}
