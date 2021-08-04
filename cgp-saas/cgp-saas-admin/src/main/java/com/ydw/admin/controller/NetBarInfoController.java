package com.ydw.admin.controller;


import com.ydw.admin.model.db.NetBarInfo;
import com.ydw.admin.model.vo.ResultInfo;
import com.ydw.admin.service.INetBarInfoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author heao
 * @since 2020-12-09
 */
@Controller
@RequestMapping("/netBarInfo")
public class NetBarInfoController extends BaseController {

    @Autowired
    private INetBarInfoService iNetBarInfoService;

    @PostMapping("/addNetBar")
    @ResponseBody
    public ResultInfo addNetBarInfo(HttpServletRequest request, NetBarInfo info, @RequestParam(value = "file1", required = false) MultipartFile file1,
                                    @RequestParam(value = "file2", required = false) MultipartFile file2,
                                    @RequestParam(value = "file3", required = false) MultipartFile file3,
                                    @RequestParam(value = "file4", required = false) MultipartFile file4,
                                    @RequestParam(value = "file5", required = false) MultipartFile file5,
                                    @RequestParam(value = "logo", required = false) MultipartFile logo) {
        return iNetBarInfoService.addNetBarInfo(request, info, file1, file2, file3, file4, file5, logo);
    }

    @GetMapping("/getNetBarList")
    @ResponseBody
    public ResultInfo getNetBarList(HttpServletRequest request, @RequestParam(value = "name", required = false) String name,
                                    @RequestParam(value = "province", required = false) String province,
                                    @RequestParam(value = "city", required = false) String city,
                                    @RequestParam(value = "distinct", required = false) String district,
                                    @RequestParam(value = "identification", required = false) String identification) {
        return iNetBarInfoService.getNetBarList(request, name, province, city, district,identification, buildPage());
    }

    @GetMapping("/getNetBarInfo/{id}")
    @ResponseBody
    public ResultInfo getNetBarInfo(HttpServletRequest request,@PathVariable String id) {
        return iNetBarInfoService.getNetBarInfo(request,id);
    }

    @PostMapping("/updateNetBar")
    @ResponseBody
    public ResultInfo updateNetBar(HttpServletRequest request, NetBarInfo info, @RequestParam(value = "file1", required = false) MultipartFile file1,
                                   @RequestParam(value = "file2", required = false) MultipartFile file2,
                                   @RequestParam(value = "file3", required = false) MultipartFile file3,
                                   @RequestParam(value = "file4", required = false) MultipartFile file4,
                                   @RequestParam(value = "file5", required = false) MultipartFile file5,
                                   @RequestParam(value = "logo", required = false) MultipartFile logo) {
        return iNetBarInfoService.updateNetBar(request, info, file1, file2, file3, file4, file5, logo);
    }


    @PostMapping("/deleteNetbar")
    @ResponseBody
    public ResultInfo deleteNetbar(HttpServletRequest request,@RequestBody List<String> ids) {
        return iNetBarInfoService.deleteNetbar(request,ids);
    }


    @GetMapping("/getAllNetBarList")
    @ResponseBody
    public ResultInfo getAllNetBarList(HttpServletRequest request) {
        return iNetBarInfoService.getAllNetBarList(request, buildPage());
    }

    @GetMapping("/getBaseStation")
    @ResponseBody
    public ResultInfo getBaseStation(HttpServletRequest request) {
        return iNetBarInfoService.getBaseStation(request, buildPage());
    }

    @GetMapping("/getMatchStation")
    @ResponseBody
    public ResultInfo getMatchStation(HttpServletRequest request) {
        return iNetBarInfoService.getMatchStation(request, buildPage());
    }

    /**
     * 获取企业用户id
     */
    @GetMapping("/getEnterpriseIdentification")
    @ResponseBody
    public ResultInfo getEnterpriseIdentification(HttpServletRequest request) {
        return iNetBarInfoService.getEnterpriseIdentification(request, buildPage());
    }

    /**
     * 根据集群id获取网吧
     */
    @PostMapping("/getNetbarbyClusterId")
    @ResponseBody
    public ResultInfo getNetbarbyClusterId(HttpServletRequest request,@RequestBody List<String> clusterIds) {
        return iNetBarInfoService.getNetbarbyClusterId(request, clusterIds);
    }
    /**
     *根据网吧获取游戏列表
     */
    @GetMapping("/getNetbarApps")
    @ResponseBody
    public ResultInfo getNetbarApps(@RequestParam String id) {
        return iNetBarInfoService.getNetbarApps(id);
    }

}

