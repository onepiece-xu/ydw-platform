package com.ydw.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ydw.admin.model.db.PictureType;
import com.ydw.admin.dao.PictureTypeMapper;
import com.ydw.admin.model.vo.ResultInfo;
import com.ydw.admin.service.IPictureTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xulh
 * @since 2021-02-19
 */
@Service
public class PictureTypeServiceImpl extends ServiceImpl<PictureTypeMapper, PictureType> implements IPictureTypeService {
    @Autowired
    private PictureTypeMapper pictureTypeMapper;

    @Override
    public ResultInfo getList() {
        QueryWrapper<PictureType> wrapper = new QueryWrapper<>();
        List<PictureType> pictureTypes = pictureTypeMapper.selectList(wrapper);
        return ResultInfo.success(pictureTypes);
    }
}
