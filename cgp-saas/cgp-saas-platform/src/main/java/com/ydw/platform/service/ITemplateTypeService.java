package com.ydw.platform.service;

import com.ydw.platform.model.db.TemplateType;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ydw.platform.model.vo.ResultInfo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hea
 * @since 2020-11-04
 */
public interface ITemplateTypeService extends IService<TemplateType> {

    ResultInfo getTemplateId(String  term);

}
