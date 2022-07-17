package com.fzg.vo;

import com.fzg.entity.OrderDetail;
import lombok.Data;

import java.util.List;

@Data
public class OrderDetailVO {
    private Integer id;
    private Integer quantity;
    private String name;
    private Float price;
    private String fileName;
    private Float cost;
}
