package com.ydw.open.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ydw.open.dao.TbTagMapper;
import com.ydw.open.model.db.TbTag;
import com.ydw.open.model.db.constants.Constant;
import com.ydw.open.service.ITbTagService;
import com.ydw.open.utils.HttpUtil;
import com.ydw.open.utils.RedisUtil;
import com.ydw.open.utils.ResultInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author heao
 * @since 2020-06-29
 */
@Service
public class TbTagServiceImpl extends ServiceImpl<TbTagMapper, TbTag> implements ITbTagService {

//    @Value("${User.url}")
    private String userUrl;
    @Autowired
    private RedisUtil redisUtil;

//    @Value("${mocklogin.loginUrl}")
    private String loginUrl;

//    @Value("${mocklogin.loginName}")
    private String loginName;

//    @Value("${mocklogin.password}")
    private String password;

    private Logger logger = LoggerFactory.getLogger(getClass());
    /**
     * 模拟登录
     * @return
     */
    public String mockLogin(){
        String token = null;
        if((token = (String) redisUtil.get(Constant.STR_MOCK_LOGIN)) == null){
            try {
                Map<String,Object> params = new HashMap<>();
                params.put("loginName", loginName);
                params.put("password", password);
                String doJsonPost = HttpUtil.doJsonPost(loginUrl, null, params);
                JSONObject resultInfo = JSON.parseObject(doJsonPost);
                JSONObject uservo = resultInfo.getJSONObject("data");
                token = uservo.getString("token");
                redisUtil.set(Constant.STR_MOCK_LOGIN, token, 360 * 10);
            } catch (Exception e) {
                logger.error("模拟登录失败！loginName:{},password:{}" ,loginName , password);
            }

        }
        return token;
    }

    @Override
    public ResultInfo getTagList(HttpServletRequest request) {
        String getTags= userUrl+"/tag/getTagsByType";
        Map<String,String> headers = new HashMap<>();
        headers.put("Authorization", this.mockLogin());
        String doGet = HttpUtil.doGet(getTags, headers, null);
        ResultInfo info = JSON.parseObject(JSON.parse(doGet).toString(), ResultInfo.class);
        Object data = info.getData();
//        JSONObject json = JSONObject.parseObject(data.toString());
//        JSONArray list = json.getJSONArray("list");
//        TagVO vo = new TagVO();
//        List arrayList = new ArrayList();
//        List<Object> objects = new ArrayList<>();
//        Map map = new HashMap<>();
//        for (Object tagVO:list ) {
//            Integer tagType = JSONObject.parseObject(tagVO.toString()).getInteger("tagType");
//            String tagTypeName = JSONObject.parseObject(tagVO.toString()).getString("tagTypeName");
//            arrayList.
//            if(!arrayList.contains(tagTypeName)){
//                objects.add(tagVO);
//
//            }
//            map.put(JSONObject.parseObject(tagVO.toString()).getString("tagTypeName"),tagVO);
//        }

        return ResultInfo.success(data);
    }

    @Override
    public ResultInfo getAppListByTag(HttpServletRequest request, String body) {
        String getTags= userUrl+"/v1/userApps/getAppListByTag";
        Map<String,String> headers = new HashMap<>();
        headers.put("Authorization", this.mockLogin());
        Map<String,Object> params = new HashMap<>();
        JSONObject object = JSONObject.parseObject(body);
        JSONArray tagNames = object.getJSONArray("tagNames");
        Integer pageNum = object.getIntValue("pageNum");
        Integer pageSize = object.getIntValue("pageSize");
        String search = object.getString("search");
        params.put("pageNum", pageNum);
        params.put("pageSize", pageSize);

        params.put("tagNames",tagNames);
        params.put("search",search);
        String doGet = HttpUtil.doJsonPost(getTags, headers, params);

        ResultInfo info = JSON.parseObject(JSON.parse(doGet).toString(), ResultInfo.class);
        return ResultInfo.success(info.getMsg(),info.getData());
    }



}
