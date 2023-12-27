package com.grovex.admin.modules.csj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.grovex.admin.modules.csj.mapper.CsjDataMapper;
import com.grovex.admin.modules.csj.service.CsjDataService;
import com.grovex.admin.modules.csj.entity.CsjData;
import org.springframework.stereotype.Service;

@Service
public class CsjDataServiceImpl extends ServiceImpl<CsjDataMapper, CsjData> implements CsjDataService {
}
