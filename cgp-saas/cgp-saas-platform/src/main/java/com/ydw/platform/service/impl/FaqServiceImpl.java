package com.ydw.platform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ydw.platform.model.db.Faq;
import com.ydw.platform.dao.FaqMapper;
import com.ydw.platform.model.vo.ResultInfo;
import com.ydw.platform.service.IFaqService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public ResultInfo getFaqList(Page buildPage) {
        QueryWrapper<Faq> faqQueryWrapper = new QueryWrapper<>();
        faqQueryWrapper.eq("valid",1);
        List<Faq> faqs = faqMapper.selectList(faqQueryWrapper);
        return ResultInfo.success(faqs);
    }

    @Override
    public ResultInfo addFaq(Faq faq) {
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
}
