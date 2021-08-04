package com.ydw.platform.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ydw.platform.model.db.Faq;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ydw.platform.model.vo.ResultInfo;

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

    ResultInfo getFaqList(Page buildPage);

    ResultInfo addFaq(Faq faq);

    ResultInfo deleteFaqs(List<Integer> ids);

    ResultInfo updateFaq(Faq faq);

    ResultInfo getFaq(Integer id);
}
