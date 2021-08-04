package com.ydw.user.controller;


import com.ydw.user.model.db.TbFiles;
import com.ydw.user.service.ITbFilesService;
import com.ydw.user.utils.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.net.URLDecoder;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author heao
 * @since 2020-05-04
 */
@Controller
@RequestMapping("/v1/files")
public class TbFilesController extends BaseController{
    @Autowired
    private ITbFilesService iTbFilesService;

    @RequestMapping(value = "/createFile", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo createFile(HttpServletRequest request, @RequestBody TbFiles file) {
        return iTbFilesService.createFile(request, file);
    }

    @RequestMapping(value = "/deleteFile", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo deleteFile(HttpServletRequest request, @RequestBody List<String> ids) {
        return iTbFilesService.deleteFile(request, ids);
    }

    @RequestMapping(value = "/updateFile", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo updateFile(HttpServletRequest request, @RequestBody TbFiles file) {
        return iTbFilesService.updateFile(request, file);
    }

    @RequestMapping(value = "/getFileList", method = RequestMethod.GET)
    @ResponseBody
    public ResultInfo getFileList(HttpServletRequest request,
                                  @RequestParam(required = false) String name,
                                  @RequestParam(required = false) String size,
                                  @RequestParam(required = false) String fileServerPath,
                                  @RequestParam(required = false) String fileClientPath,
                                  @RequestParam(required = false) String identification,
                                  @RequestParam(required = false) Integer status,
                                  @RequestParam(required = false) String search,
                                  @RequestParam(required = false) Integer pageNum,
                                  @RequestParam(required = false) Integer pageSize) {
        return iTbFilesService.getFileList(request, name, size, fileServerPath, fileClientPath, identification, status,search,buildPage());
    }

    @ResponseBody
    @RequestMapping(value = "/fileUpload", produces = "application/json; charset=utf-8")
    public ResultInfo UploadExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {

        return iTbFilesService.ajaxUploadExcel(request, response);
    }

    @RequestMapping(value = "/downloadExcel")
    @ResponseBody
    public void downloadExcel(HttpServletResponse res, HttpServletRequest req, String name) throws Exception {
        String fileName = "设备导入模板" + ".xlsx";
        ServletOutputStream out;
        res.setContentType("multipart/form-data");
        res.setCharacterEncoding("UTF-8");
        res.setContentType("text/html");
        String filePath = getClass().getResource("/templates/" + fileName).getPath();
        String userAgent = req.getHeader("User-Agent");
        if (userAgent.contains("MSIE") || userAgent.contains("Trident")) {
            fileName = java.net.URLEncoder.encode(fileName, "UTF-8");
        } else {
            // 非IE浏览器的处理：
            fileName = new String((fileName).getBytes("UTF-8"), "ISO-8859-1");
        }
        filePath = URLDecoder.decode(filePath, "UTF-8");
        res.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
        FileInputStream inputStream = new FileInputStream(filePath);
        out = res.getOutputStream();
        int b = 0;
        byte[] buffer = new byte[1024];
        while ((b = inputStream.read(buffer)) != -1) {
// 4.写到输出流(out)中
            out.write(buffer, 0, b);
        }
        inputStream.close();

        if (out != null) {
            out.flush();
            out.close();
        }

    }
}

