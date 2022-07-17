package com.fzg.vo;

import com.fzg.entity.OrderDetail;
import lombok.Data;

import java.util.List;

@Data
public class OrdersVO {
    private Integer id;
    private String loginName;
    private String userAddress;
    private Float cost;
    private String serialnumber;
    private List<OrderDetailVO> orderDetailList;
}