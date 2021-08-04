/**
* @Title: StringUtil.java
* @Package com.awcloud.common.util
* @Description: TODO
* @author 段志伟
* @date 2016年5月7日 上午11:03:36
* @version V1.0
* Copyright @ 2015 北京海云捷迅科技有限公司
*/
package com.ydw.authentication.utils;

import org.apache.commons.lang3.StringUtils;


/**
* @ClassName: StringUtil
* @Description: 字符串相关操作
* @author
* @date
*
*/
public class StringUtil extends StringUtils{

    /**
     *
    * @Title: nullOrEmpty
    * @Description: 判断字符串是否为null或者""或者"null"
    * @param @param value
    * @param @return    参数
    * @return boolean    返回类型
    * @throws
     */
    public static boolean nullOrEmpty(String value) {
        if (value == null || "".equals(value) || "null".equals(value)) {
            return true;
        }
        return false;
    }

    /**
    * @Description: TODO(字符串补全)
    * @param @param source 源字符串
    * @param @param fillLength 补全后的长度
    * @param @param fillChar 补全字符
    * @param @param isLeftFill 是否左补全， true：左补全，false：右补全
    * @param @return    设定文件
    * @return String    返回类型
    * @throws
    */
    public static String stringFill(String source, int fillLength, char fillChar,
            boolean isLeftFill) {
        if (source == null || source.length() >= fillLength)
            return source;

        StringBuilder result = new StringBuilder(fillLength);
        int len = fillLength - source.length();
        if (isLeftFill) {
            for (; len > 0; len--) {
                result.append(fillChar);
            }
            result.append(source);
        }
        else {
            result.append(source);
            for (; len > 0; len--) {
                result.append(fillChar);
            }
        }
        return result.toString();
    }

    /**
     *
    * @Title: isIP
    * @Description: 判断输入字符船是否符合IP规则
    * @param @param ipStr
    * @param @return    参数
    * @return Boolean    返回类型
    * @throws
     */
    public static Boolean isIP(String ipStr) {
        Boolean b = false;

        ipStr = ipStr.trim();

        if (!ipStr.matches(
                "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}")) {
            return b;
        }

        b = true;
        return b;
    }
}
