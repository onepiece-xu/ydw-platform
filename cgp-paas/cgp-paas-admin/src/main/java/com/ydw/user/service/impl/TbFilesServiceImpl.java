package com.ydw.user.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ydw.user.model.db.TbFiles;
import com.ydw.user.dao.TbFilesMapper;
import com.ydw.user.model.vo.FileVO;
import com.ydw.user.service.ITbFilesService;

import com.ydw.user.utils.ExcelUtil;
import com.ydw.user.utils.ResultInfo;
import com.ydw.user.utils.SequenceGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author heao
 * @since 2020-05-04
 */
@Service
public class TbFilesServiceImpl extends ServiceImpl<TbFilesMapper, TbFiles> implements ITbFilesService {

    @Autowired
    private TbFilesMapper tbFilesMapper;


    private Logger logger = LoggerFactory.getLogger(getClass());
    @Override
    public ResultInfo createFile(HttpServletRequest request, TbFiles file) {
        file.setId(SequenceGenerator.sequence());
        file.setCreateTime(new Date());
        file.setValid(true);//正常状态
        tbFilesMapper.insert(file);
        logger.info(file.getName()+"文件创建成功！");
        return ResultInfo.success();
    }

    @Override
    public ResultInfo deleteFile(HttpServletRequest request, List<String> ids) {
        TbFiles file = new TbFiles();
        for (String id : ids) {
            file.setId(id);
            //软删除
            file.setValid(false);
            file.setUpdateTime(new Date());
            logger.info(file.getName()+"文件删除成功！");
            tbFilesMapper.updateById(file);
        }

        return ResultInfo.success();
    }

    @Override
    public ResultInfo getFileList(HttpServletRequest request, String name, String size, String fileServerPath, String fileClientPath, String identification, Integer status, String  search,Page buildPage) {

//        if (null != pageNum && null != pageSize) {
//            PageHelper.startPage(pageNum, pageSize);
//        } else {
//            PageHelper.startPage(1, 10);
//        }
        Page<FileVO> list = tbFilesMapper.getFileList(name, size, fileServerPath, fileClientPath, identification, status,search,buildPage);
//        PageInfo<FileVO> pageInfo = new PageInfo<>(list);
        return ResultInfo.success(list);
    }

    @Override
    public Integer insertDb(TbFiles file) {
        return tbFilesMapper.insert(file);
    }

    @Override
    public ResultInfo ajaxUploadExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

        MultipartFile file = multipartRequest.getFile("file");
        if(file.isEmpty()){
            try {
                throw new Exception("文件不存在！");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        InputStream in =null;
        try {
            in = file.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<List<Object>> listob = null;
        try {
            listob = new ExcelUtil().getBankListByExcel(in,file.getOriginalFilename());
        } catch (Exception e) {
            e.printStackTrace();
        }

        //该处可调用service相应方法进行数据保存到数据库中，现只对数据输出
        for (int i = 0; i < listob.size(); i++) {
            List<Object> lo = listob.get(i);
            TbFiles vo = new TbFiles();
            /*这里是主键验证，根据实际需要添加，可要可不要，加上之后，可以对现有数据进行批量修改和导入
            User j = null;
			try {
				j = userMapper.selectByPrimaryKey(Integer.valueOf(String.valueOf(lo.get(0))));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				System.out.println("没有新增");
			}*/
            //vo.setUserId(Integer.valueOf(String.valueOf(lo.get(0))));  // 刚开始写了主键，由于主键是自增的，又去掉了，现在只有批量插入的功能，不能对现有数据进行修改了
            vo.setId(SequenceGenerator.sequence());
            vo.setName(String.valueOf(lo.get(0))); // 表格的第一列
            vo.setSize(Integer.valueOf(String.valueOf(lo.get(1))));// 表格的第2列
            vo.setFileServerPath(String.valueOf(lo.get(2)));
            vo.setFileClientPath(String.valueOf(lo.get(3)));
            vo.setIdentification(String.valueOf(lo.get(4)));
            vo.setValid(true);
            vo.setCreateTime(new Date());
//            vo.setUserTel(String.valueOf(lo.get(0)));     // 表格的第一列   注意数据格式需要对应实体类属性   String.valueOf(lo.get(1))
//            vo.setIntegral(Integer.valueOf(String.valueOf(lo.get(1))));   // 表格的第二列
            //vo.setRegTime(Date.valueOf(String.valueOf(lo.get(2))));
            //由于数据库中此字段是datetime，所以要将字符串时间格式：yyyy-MM-dd HH:mm:ss，转为Date类型
//            if (lo.get(2) != null && lo.get(2) != "") {
//                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
////                vo.setRegTime(sdf.parse(String.valueOf(lo.get(2))));
//            }else {
////                vo.setRegTime(new Date());
//            }
            System.out.println("从excel中读取的实体类对象："+ vo);
            tbFilesMapper.insert(vo);
			/*if(j == null)
			{
		            userMapper.insert(vo);
			}
			else
			{
		            userMapper.updateByPrimaryKey(vo);
			}*/
        }

        return  ResultInfo.success("文件导入成功！");

    }

    @Override
    public ResultInfo updateFile(HttpServletRequest request, TbFiles file) {

        tbFilesMapper.updateById(file);
        return ResultInfo.success("Update Success");
    }

}
