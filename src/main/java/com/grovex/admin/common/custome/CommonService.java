package com.grovex.admin.common.custome;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 通用 Service
 * @param <T>
 */
public class CommonService<M extends BaseMapper<T>,T> extends ServiceImpl<M,T>{

    /**
     * 重写分页方法，如果当前页没有数据，则返回第一页
     * @param page 分页参数
     * @return  分页对象
     * @param <E> 分页对象
     */
    @Override
    public <E extends IPage<T>> E page(E page) {
        E result = super.page(page);
        if (result.getRecords().isEmpty() && result.getCurrent() > 1) {
            page.setCurrent(1);
            result = super.page(page);
        }
        return result;
    }
}
