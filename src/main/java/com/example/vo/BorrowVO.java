package com.example.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BorrowVO {
    private Integer id;
    private String bookName;
    private String sortName;
    private String author;
    private String publish;
    private String edition;
    private LocalDateTime startTime;
    private Integer status = 0;
}
