package com.ydw.user.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ydw.user.model.db.TbFiles;

import com.ydw.user.utils.ResultInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author heao
 * @since 2020-05-04
 */
public interface ITbFilesService extends IService<TbFiles> {

    ResultInfo createFile(HttpServletRequest request, TbFiles file);

    ResultInfo deleteFile(HttpServletRequest request, List<String> ids);

    ResultInfo getFileList(HttpServletRequest request, String name, String size, String fileServerPath, String fileClientPath, String identification, Integer status
                           , String search , Page buildPage);

    Integer  insertDb(TbFiles file);

    ResultInfo ajaxUploadExcel(HttpServletRequest request, HttpServletResponse response) throws Exception;

    ResultInfo updateFile(HttpServletRequest request, TbFiles file);
}
