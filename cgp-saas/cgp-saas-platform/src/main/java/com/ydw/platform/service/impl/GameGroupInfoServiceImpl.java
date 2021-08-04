package com.ydw.platform.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ydw.platform.dao.GameGroupInfoMapper;
import com.ydw.platform.dao.GameGroupMapper;
import com.ydw.platform.model.db.GameGroup;
import com.ydw.platform.model.db.GameGroupInfo;
import com.ydw.platform.model.vo.ResultInfo;
import com.ydw.platform.service.IGameGroupInfoService;

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
public class GameGroupInfoServiceImpl extends ServiceImpl<GameGroupInfoMapper, GameGroupInfo> implements IGameGroupInfoService {

    @Autowired
    private GameGroupInfoMapper gamegroupInfoMapper;
    @Autowired
    private GameGroupMapper tbGameGroupMapper;


    @Override
    public ResultInfo getGameGroupList(HttpServletRequest request, Page b) {
//        if (null != pageNum && null != pageSize) {
//            PageHelper.startPage(pageNum, pageSize);
//        } else {
//            PageHelper.startPage(1, 10);
//        }
        Page<GameGroup> list = gamegroupInfoMapper.getGameGroupList(b);
//        PageInfo<TbGameGroup> pageInfo = new PageInfo<>(list);
        return ResultInfo.success(list);
    }

    @Override
    public ResultInfo addGameGroup(HttpServletRequest request, GameGroupInfo tbGamegroupInfo) {
        tbGamegroupInfo.setValid(true);
        gamegroupInfoMapper.insert(tbGamegroupInfo);
          return ResultInfo.success("添加成功！");
    }

    @Override
    public ResultInfo updateGameGroup(HttpServletRequest request, GameGroupInfo tbGamegroupInfo) {
        gamegroupInfoMapper.updateById(tbGamegroupInfo);
        return ResultInfo.success("编辑成功！");
    }

    @Override
    public ResultInfo deleteGameGroup(HttpServletRequest request, List<Integer> ids) {
        GameGroupInfo info = new GameGroupInfo();
        String msg="";
        for (Integer i:ids) {
            List<GameGroup> infos = tbGameGroupMapper.getByGameGroupId(i);
                if(infos.size()>0){
                    info.setGamegroupId(i);
                    GameGroupInfo byId = gamegroupInfoMapper.selectById(info);
                    msg+=byId.getGamegroupName()+"游戏组下包含应用,删除失败,";
                }else{
                    info.setGamegroupId(i);
                    GameGroupInfo byId = gamegroupInfoMapper.selectById(info);
                    byId.setValid(false);
                    gamegroupInfoMapper.updateById(byId);
                }

        }
        if(!msg.equals("")){
            return  ResultInfo.fail(msg.substring(0, msg.length() -1));
        }

        return ResultInfo.success();
    }
}
