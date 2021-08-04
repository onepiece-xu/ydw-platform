package com.ydw.user.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ydw.user.model.db.TbFiles;

import com.ydw.user.model.vo.FileVO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author heao
 * @since 2020-05-04
 */
public interface TbFilesMapper extends BaseMapper<TbFiles> {

    Page<FileVO> getFileList(@Param("name")String name, @Param("size")String size, @Param("fileServerPath")String fileServerPath
            , @Param("fileClientPath") String fileClientPath, @Param("identification")String identification, @Param("status") Integer status, @Param("search")String search,Page buildPage);
}
