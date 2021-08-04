package com.ydw.user.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ydw.user.dao.TbDeviceBaseMapper;
import com.ydw.user.dao.TbDevicesMapper;
import com.ydw.user.model.db.TbDeviceBase;
import com.ydw.user.model.db.TbDevices;
import com.ydw.user.service.ITbDeviceBaseService;
import com.ydw.user.utils.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author heao
 * @since 2020-07-24
 */
@Service
public class TbDeviceBaseServiceImpl extends ServiceImpl<TbDeviceBaseMapper, TbDeviceBase> implements ITbDeviceBaseService {

    @Autowired
    private TbDeviceBaseMapper tbDeviceBaseMapper;
    @Autowired
    private TbDevicesMapper tbDevicesMapper;

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Override
    public ResultInfo getBaseList(HttpServletRequest request, String search , Page buildPage) {
//        QueryWrapper queryWrapper = new QueryWrapper();
//        queryWrapper.();

        Page<TbDeviceBase> baseList = tbDeviceBaseMapper.getBaseList(search, buildPage);
        return ResultInfo.success(baseList);
    }

    @Override
    public ResultInfo addDeviceBase(HttpServletRequest request, TbDeviceBase tbDeviceBase) {
        tbDeviceBase.setValid(true);
        tbDeviceBaseMapper.insert(tbDeviceBase);
        logger.info(tbDeviceBase.getName()+"，设备规格添加成功！");
        return ResultInfo.success("添加成功！");
    }

    @Override
    public ResultInfo updateDeviceBase(HttpServletRequest request, TbDeviceBase tbDeviceBase) {
        tbDeviceBaseMapper.updateById(tbDeviceBase);
        logger.info(tbDeviceBase.getName()+"，设备修改成功！");
        return ResultInfo.success("修改成功！");
    }

    @Override
    public ResultInfo deleteDeviceBase(HttpServletRequest request, List<Integer> ids) {
        String msg="";
        for (Integer id:ids ) {
            List<TbDevices> devicesByBaseId = tbDeviceBaseMapper.getDevicesByBaseId(id);
            TbDeviceBase baseById = tbDeviceBaseMapper.getBaseById(id);

            if(devicesByBaseId.size()>0){
              msg+= baseById.getName()+",";
            }else {
                if(baseById!=null) {
                    baseById.setValid(false);//软删除
                    tbDeviceBaseMapper.updateById(baseById);
                }
            }
        }
        if(msg!=""){
            msg=msg.substring(0,msg.length()-1)+"删除失败，规格已被设备使用！";
            logger.error(msg);
            return  ResultInfo.fail(msg);
        }
        return ResultInfo.success("删除成功！");
    }


    @Override
    public ResultInfo getBaseById(HttpServletRequest request, Integer id) {
        TbDeviceBase baseById = tbDeviceBaseMapper.getBaseById(id);
        return ResultInfo.success(baseById);
    }

}
