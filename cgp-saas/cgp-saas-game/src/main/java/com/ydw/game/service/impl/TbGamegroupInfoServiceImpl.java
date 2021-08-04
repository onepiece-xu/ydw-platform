package com.ydw.game.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.ydw.game.dao.TbGameGroupMapper;
import com.ydw.game.dao.TbGamegroupInfoMapper;
import com.ydw.game.model.db.TbGameGroup;
import com.ydw.game.model.db.TbGamegroupInfo;
import com.ydw.game.model.vo.ResultInfo;
import com.ydw.game.service.ITbGamegroupInfoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author heao
 * @since 2020-07-16
 */
@Service
public class TbGamegroupInfoServiceImpl extends ServiceImpl<TbGamegroupInfoMapper, TbGamegroupInfo> implements ITbGamegroupInfoService {

    @Autowired
    private TbGamegroupInfoMapper tbGamegroupInfoMapper;
    @Autowired
    private TbGameGroupMapper tbGameGroupMapper;


    @Override
    public ResultInfo getGameGroupList(HttpServletRequest request, Page b) {
//        if (null != pageNum && null != pageSize) {
//            PageHelper.startPage(pageNum, pageSize);
//        } else {
//            PageHelper.startPage(1, 10);
//        }
        Page<TbGameGroup> list = tbGamegroupInfoMapper.getGameGroupList(b);
//        PageInfo<TbGameGroup> pageInfo = new PageInfo<>(list);
        return ResultInfo.success(list);
    }

    @Override
    public ResultInfo addGameGroup(HttpServletRequest request, TbGamegroupInfo tbGamegroupInfo) {
        tbGamegroupInfo.setValid(true);
        tbGamegroupInfoMapper.insert(tbGamegroupInfo);
          return ResultInfo.success("添加成功！");
    }

    @Override
    public ResultInfo updateGameGroup(HttpServletRequest request, TbGamegroupInfo tbGamegroupInfo) {
        tbGamegroupInfoMapper.updateById(tbGamegroupInfo);
        return ResultInfo.success("编辑成功！");
    }

    @Override
    public ResultInfo deleteGameGroup(HttpServletRequest request, List<Integer> ids) {
        TbGamegroupInfo info = new TbGamegroupInfo();
        String msg="";
        for (Integer i:ids) {
            List<TbGameGroup> infos = tbGameGroupMapper.getByGameGroupId(i);
                if(infos.size()>0){
                    info.setGamegroupId(i);
                    TbGamegroupInfo byId = tbGamegroupInfoMapper.selectById(info);
                    msg+=byId.getGamegroupName()+"游戏组下包含应用,删除失败,";
                }else{
                    info.setGamegroupId(i);
                    TbGamegroupInfo byId = tbGamegroupInfoMapper.selectById(info);
                    byId.setValid(false);
                    tbGamegroupInfoMapper.updateById(byId);
                }

        }
        if(!msg.equals("")){
            return  ResultInfo.fail(msg.substring(0, msg.length() -1));
        }

        return ResultInfo.success();
    }
}
