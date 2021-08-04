package com.ydw.user.controller;


import com.ydw.user.model.db.TbClusters;
import com.ydw.user.model.vo.CreateClusterVO;
import com.ydw.user.service.ISignalServerService;
import com.ydw.user.service.ITbClustersService;
import com.ydw.user.service.ITurnServerService;
import com.ydw.user.utils.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author heao
 * @since 2020-05-13
 */
@Controller
@RequestMapping("/v1/clusters")
public class TbClustersController extends BaseController{
    @Autowired
    private ITbClustersService iTbClustersService;

    @Autowired
    private ITurnServerService iTurnServerService;

    @Autowired
    private ISignalServerService iSignalServerService;

    /**
     * 获取服务相关企业集群列表
     */
    @GetMapping(value = "/getServiceClusters")
    @ResponseBody
    public ResultInfo getServiceClusters(){
        return  iTbClustersService.getServiceClusters();
    }

    @RequestMapping(value = "/createCluster", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo createCluster(HttpServletRequest request, @RequestBody CreateClusterVO cluster) {

        return iTbClustersService.createCluster(request, cluster);
    }

    @PostMapping(value = "/updateCluster")
    @ResponseBody
    public ResultInfo updateCluster(HttpServletRequest request, @RequestBody CreateClusterVO cluster) {

        return iTbClustersService.updateCluster(request, cluster);
    }

    @PostMapping(value = "/deleteClusters")
    @ResponseBody
    public ResultInfo deleteClusters(HttpServletRequest request, @RequestBody List<String> ids) {

        return iTbClustersService.deleteClusters(request, ids);
    }


    @GetMapping(value = "/getClusters")
    @ResponseBody
    public ResultInfo getClusters(HttpServletRequest request, @RequestParam(required = false) String name,
                               @RequestParam(required = false) Integer type,
                               @RequestParam(required = false) Integer isLocal,
                               @RequestParam(required = false) String description,
                               @RequestParam(required = false) String apiUrl,
                               @RequestParam(required = false) String accessIp,
                               @RequestParam(required = false) Integer schStatus,
                               @RequestParam(required = false) String search,
                               @RequestParam(required = false) Integer pageNum,
                               @RequestParam(required = false) Integer pageSize){

        return  iTbClustersService.getClusters(request,name,type,isLocal,description,apiUrl,accessIp,schStatus,search,buildPage());
    }


    @GetMapping(value = "/getClusterById/{id}")
    @ResponseBody
    public ResultInfo getClusterById(HttpServletRequest request,  @PathVariable   String id){

        return  iTbClustersService.getClusterById(request,id);
    }

    /**
     * 集群 绑定webrtcconfig
     */
    @PostMapping(value = "/bindConfig")
    @ResponseBody
    public ResultInfo bindConfig(@RequestBody String body){

        return  iTbClustersService.bindConfig(body);
    }


    /**
     * 获取集群绑定的TURN服务列表
     */
    @GetMapping(value = "/getTurn/{id}")
    @ResponseBody
    public ResultInfo getTurn(HttpServletRequest request,  @PathVariable   String id){

        return  iTbClustersService.getTurn(request,id);
    }
    /**
     * 获取集群绑定的信令服务列表
     */
    @GetMapping(value = "/getSignal/{id}")
    @ResponseBody
    public ResultInfo getSignal(HttpServletRequest request,  @PathVariable  String id){

        return  iTbClustersService.getSignal(request,id);
    }

    /**
     * 获取集群空闲机器数和总启用机器数
     */
    @GetMapping(value = "/getClusterStatus")
    @ResponseBody
    public ResultInfo getClusterStatus(HttpServletRequest request,  @RequestParam  String id){

        return  iTbClustersService.getClusterStatus(request,id);
    }
}

