package com.ydw.resource.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.ydw.resource.model.db.Devices;
import com.ydw.resource.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ydw.resource.dao.ClustersMapper;
import com.ydw.resource.model.db.Clusters;
import com.ydw.resource.model.vo.ClusterVO;
import com.ydw.resource.service.IClustersService;
import com.ydw.resource.utils.ResultInfo;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xulh
 * @since 2020-08-24
 */
@Service
public class ClustersServiceImpl extends ServiceImpl<ClustersMapper, Clusters> implements IClustersService {
	
	@Autowired
	private ClustersMapper clustersMapper;

	@Override
	public ResultInfo getClusters(String identification,String appId) {
        List<ClusterVO> list = null;
	    if (StringUtil.isBlank(appId)){
            list = clustersMapper.getClusters(identification);
        }else{
            list = clustersMapper.getClustersByApp(identification, appId);
        }
		return ResultInfo.success(list);
	}

	@Override
	public ResultInfo updateExternalIp(String oldIp, String newIp) {
	    int result = 0;
		List<Clusters> list = this.list(new QueryWrapper<>());
		for(Clusters c : list){
            String apiUrl = c.getApiUrl();
            if(apiUrl.contains(oldIp)){
                apiUrl = apiUrl.replace(oldIp,newIp);
                c.setApiUrl(apiUrl);
                updateById(c);
                result++;
            }
            String accessIp = c.getAccessIp();
            if(accessIp.equals(oldIp)){
                c.setAccessIp(newIp);
                updateById(c);
                result++;
            }
        }
        if (result > 0){
            return ResultInfo.success();
        }else{
            return ResultInfo.fail();
        }
	}

}
