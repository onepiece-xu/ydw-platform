package com.ydw.user.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.ydw.user.dao.TbUserAppsMapper;
import com.ydw.user.model.db.TbAppStrategy;
import com.ydw.user.dao.TbAppStrategyMapper;
import com.ydw.user.model.db.TbUserApps;
import com.ydw.user.model.vo.AppVO;
import com.ydw.user.service.ITbAppStrategyService;

import com.ydw.user.utils.ResultInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import com.github.pagehelper.Page;
//import com.github.pagehelper.PageHelper;
//import com.github.pagehelper.PageInfo;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author heao
 * @since 2020-04-29
 */
@Service
public class TbAppStrategyServiceImpl extends ServiceImpl<TbAppStrategyMapper, TbAppStrategy> implements ITbAppStrategyService {

    @Autowired
    private TbAppStrategyMapper tbAppStrategyMapper;
    @Autowired
    private TbUserAppsMapper tbUserAppsMapper;

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Override
    public ResultInfo createStrategy(HttpServletRequest request, TbAppStrategy strategy) {
        strategy.setCreateTime(new Date());
        strategy.setValid(true);//状态
        strategy.setType(1);
        Integer output = strategy.getOutput();
        strategy.setVideo(output);
        tbAppStrategyMapper.insert(strategy);
        logger.info(strategy.getName()+"添加成功！-----");
        return ResultInfo.success();
    }

    @Override
    public ResultInfo updateStrategy(HttpServletRequest request, TbAppStrategy strategy) {
        strategy.setUpdateTime(new Date());
        tbAppStrategyMapper.updateById(strategy);
        logger.info(strategy.getName()+"修改成功！-----");
        return ResultInfo.success();
    }

    @Override
    public ResultInfo getStrategyList(HttpServletRequest request, String name, Integer fps, Integer speed, Integer output, Integer video, String encode,String search, Page buildPage) {
//        if (null != pageNum && null != pageSize) {
//            PageHelper.startPage(pageNum, pageSize);
//        } else {
//            PageHelper.startPage(1, 10);
//        }
        Page<TbAppStrategy> list =tbAppStrategyMapper.getStrategyList(name,fps,speed,output,video,encode,search,buildPage);
//        PageInfo<TbAppStrategy> pageInfo = new PageInfo<>(list);
        return ResultInfo.success(list);

    }

    @Override
    public ResultInfo bindStrategy(HttpServletRequest request, Integer strategyId, List<String> ids) {
        TbUserApps userApps = new TbUserApps();
      for (String id: ids ) {
            userApps.setId(id);
            userApps.setStrategyId(strategyId);
            tbUserAppsMapper.updateById(userApps);
        }

        return ResultInfo.success();
    }

    @Override
    public ResultInfo getBindApps(HttpServletRequest request, Integer strategyId,String appName,String enterpriseName,
                                  Integer type, Page buildPage) {
//        if (null != pageNum && null != pageSize) {
//            PageHelper.startPage(pageNum, pageSize);
//        } else {
//            PageHelper.startPage(1, 10);
//        }
        Page<AppVO> bindApps = tbAppStrategyMapper.getBindApps(strategyId, appName, enterpriseName, type,buildPage);
//        PageInfo<AppVO> pageInfo = new PageInfo<>(bindApps);
        return ResultInfo.success(bindApps);
    }

    @Override
    public ResultInfo getUnBindApps(HttpServletRequest request,Integer strategyId,String appName,String userName,Integer type, Page buildPage) {
//        if (null != pageNum && null != pageSize) {
//            PageHelper.startPage(pageNum, pageSize);
//        } else {
//            PageHelper.startPage(1, 10);
//        }
        Page<AppVO> unBindApps = tbAppStrategyMapper.getUnBindApps(strategyId, appName, userName, type,buildPage);
//        PageInfo<AppVO> pageInfo = new PageInfo<>(unBindApps);
        return ResultInfo.success(unBindApps);
    }

    @Override
    public ResultInfo UnbindStrategy(HttpServletRequest request, Integer strategyId, List<String> ids) {
         TbUserApps userApps = new TbUserApps();
        TbAppStrategy ts = tbAppStrategyMapper.getDefaultStrategy();
        for (String id: ids ) {
            userApps.setId(id);
            userApps.setStrategyId(ts.getId());
            tbUserAppsMapper.updateById(userApps);
        }
        return ResultInfo.success();
    }

    @Override
    public ResultInfo deleteStrategy(HttpServletRequest request, List<Integer> ids) {
        TbAppStrategy tbAppStrategy = new TbAppStrategy();
        String  msg= "";


        for (Integer id: ids ) {
        //删除前查询流策略是否被关联，关联则不能被删除
            List<TbUserApps> byStrategyId = tbUserAppsMapper.getByStrategyId(id);
            if(byStrategyId.size()>0){
                TbAppStrategy byId = tbAppStrategyMapper.selectById(id);
                msg+=byId.getName()+",";
            }else{
                tbAppStrategy.setId(id);
                tbAppStrategy.setValid(false);
                tbAppStrategyMapper.updateById(tbAppStrategy);
            }
        }
        if(!msg.equals("")){
            msg="【"+msg.substring(0,msg.length()-1)+"】刪除失敗，策略已被綁定！";
            logger.error(msg);
            return ResultInfo.fail(msg);

        }
            return ResultInfo.success("刪除成功！");
        }


}
