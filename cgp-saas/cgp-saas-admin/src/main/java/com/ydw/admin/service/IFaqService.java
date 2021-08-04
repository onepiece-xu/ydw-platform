package com.ydw.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ydw.admin.model.db.Faq;
import com.ydw.admin.model.vo.FaqUploadVO;
import com.ydw.admin.model.vo.ResultInfo;


import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xulh
 * @since 2020-10-06
 */
public interface IFaqService extends IService<Faq> {

    ResultInfo getFaqList(String search,Page buildPage);

    ResultInfo addFaq(Faq faq);

    ResultInfo deleteFaqs(List<Integer> ids);

    ResultInfo updateFaq(Faq faq);

    ResultInfo getFaq(Integer id);

    FaqUploadVO upload(HttpServletRequest request);
}
