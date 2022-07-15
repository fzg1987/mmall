package com.fzg.service;

import com.fzg.entity.ProductCategory;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fzg.vo.ProductCategoryVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author admin
 * @since 2022-07-13
 */
public interface ProductCategoryService extends IService<ProductCategory> {
    public List<ProductCategoryVO> buildProductCategoryMenu();
}
