package com.ydw.open.controller;
import com.ydw.open.model.db.TbClusters;
import com.ydw.open.service.ITbClustersService;
import com.ydw.open.utils.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/clusters")
public class TbClustersController extends BaseController {
    @Autowired
    private ITbClustersService iTbClustersService;



    @RequestMapping(value = "/createCluster", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo createCluster(HttpServletRequest request, @RequestBody TbClusters cluster) {

        return iTbClustersService.createCluster(request, cluster);
    }

    @PostMapping(value = "/updateCluster")
    @ResponseBody
    public ResultInfo updateCluster(HttpServletRequest request, @RequestBody TbClusters cluster) {

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
    public ResultInfo getSignal(HttpServletRequest request,  @PathVariable   String id){

        return  iTbClustersService.getSignal(request,id);
    }

    /**
     * 获取Open 管理员界面 网吧绑定获取集群
     */
    @GetMapping(value = "/getClusterList")
    @ResponseBody
    public ResultInfo getClusterList(HttpServletRequest request){

        return  iTbClustersService.getClusterList(request,buildPage());
    }
}

