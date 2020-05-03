package com.ygyg.data.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ygyg.data.dao.InfoModelDao;
import com.ygyg.data.dao.InfomodelPropertyDao;
import com.ygyg.data.entity.Infomodel;
import com.ygyg.data.entity.InfomodelProperty;
import com.ygyg.data.service.InfoModelService;
import com.ygyg.data.service.InfomodelPropertyService;
import org.springframework.stereotype.Service;

@Service
public class InfomodelPropertyServiceImpl extends ServiceImpl<InfomodelPropertyDao, InfomodelProperty> implements InfomodelPropertyService {

}