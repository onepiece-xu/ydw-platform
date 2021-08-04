package com.ydw.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mysql.cj.util.StringUtils;
import com.ydw.admin.dao.FaqMapper;
import com.ydw.admin.model.db.Faq;
import com.ydw.admin.model.vo.FaqUploadVO;
import com.ydw.admin.model.vo.ResultInfo;
import com.ydw.admin.service.IFaqService;

import com.ydw.admin.util.FTPUtil;
import com.ydw.admin.util.SequenceGenerator;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xulh
 * @since 2020-10-06
 */
@Service
public class FaqServiceImpl extends ServiceImpl<FaqMapper, Faq> implements IFaqService {

    @Autowired
    private FaqMapper faqMapper;

    @Value("${ftp.address}")
    private String address;

    @Value("${ftp.port}")
    private int port;

    @Value("${ftp.userName}")
    private String userName;

    @Value("${ftp.passWord}")
    private String passWord;

    @Value("${config.faqPic}")
    private String faqPic;
    @Value("${url.pics}")
    private String pics;

    @Override
    public ResultInfo getFaqList(String search,Page buildPage) {
        QueryWrapper<Faq> faqQueryWrapper = new QueryWrapper<>();
        faqQueryWrapper.eq("valid",1);
        faqQueryWrapper.orderByDesc("time");
        if(!StringUtils.isNullOrEmpty(search)){
            faqQueryWrapper.like("title",search);
        }
        faqQueryWrapper.orderByDesc("time");
        Page<Faq> faqs = faqMapper.selectPage(buildPage,faqQueryWrapper);
        return ResultInfo.success(faqs);
    }

    @Override
    public ResultInfo addFaq(Faq faq) {
        faq.setTime(new Date());
        faq.setValid(true);
        faqMapper.insert(faq);
        return ResultInfo.success("创建成功！");
    }

    @Override
    public ResultInfo deleteFaqs(List<Integer> ids) {
        for (Integer id:ids) {
            Faq faq = faqMapper.selectById(id);
            faq.setValid(false);
            faqMapper.updateById(faq);
        }
        return ResultInfo.success("删除成功！");
    }

    @Override
    public ResultInfo updateFaq(Faq faq) {
        faqMapper.updateById(faq);
        return ResultInfo.success("编辑成功！");
    }

    @Override
    public ResultInfo getFaq(Integer id) {
        Faq faq = faqMapper.selectById(id);
        return ResultInfo.success(faq);
    }

    @Override
    public FaqUploadVO upload(HttpServletRequest request) {
        String sequence = SequenceGenerator.sequence();
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("file");
        long size = file.getSize();

        FaqUploadVO uploadVO = new FaqUploadVO();
        if(size>512000){
             uploadVO.setMsg("图片过大,只允许添加500K以下图片");
             uploadVO.setCode(500);
            return uploadVO;
        }
        if(null!=file){

            FTPClient ftpClient = FTPUtil.connectFtpServer(address, port, userName, passWord);
//            ftpClient.enterLocalPassiveMode();
            try {
                FTPUtil.uploadFiles(ftpClient, faqPic  +sequence + ".jpg", file.getInputStream());
                uploadVO.setErrno(0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String url =pics+"faq/"+sequence+ ".jpg";
        List<String> list = new ArrayList<>();
        list.add(url);
        uploadVO.setData(list);
        uploadVO.setErrno(0);
        return uploadVO;
    }
}
