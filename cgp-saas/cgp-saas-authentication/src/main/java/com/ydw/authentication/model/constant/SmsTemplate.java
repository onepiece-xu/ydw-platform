package com.ydw.authentication.model.constant;

public class SmsTemplate {
    /**相关配置
     *
     */
    public final static String appkey = "YDW168";

    public final static String appcode = "1000";

    public final static String appsecret = "65Li6p";

    public final static String smsurl = "http://yx.sms.zuiniu.xin:9090/sms/batch/v1";

    public final static String SMS_LOGIN = "{1}为您的登录验证码，请于{2}分钟内填写，如非本人操作，请忽略本短信。";

    public final static String SMS_WARN = "当前云游戏平台，在线用户中，出现3个或3个以上相同IP，请注意查看，是否进行非法操作。";

    public final static String SMS_REGIST = "您的注册验证码：{1}，请于{2}分钟内填写。如非本人操作，请忽略本短信！";

    public final static String BIND_PAYMENT_VERIFICATION = "{1}为您的绑定支付验证码，请于{2}分钟内填写，如非本人操作，请忽略本短信。";

    public final static String UNBUND_PAYMENT_VERIFICATION = "{1}为您的解绑支付验证码，请于{2}分钟内填写，如非本人操作，请忽略本短信。";

    public final static String APPLICATION_WITHDRAWAL = "您于{1}提交的{2}元的提现申请目前正在系统审批中。如有问题请联系客服反馈。";

    public final static String WITHDRAWAL_APPROVAL_PASSED = "您于{1}提交的{2}元的提现申请目前已审批通过，目前进入转账流程。转账完成后会有短信提示，请注意查收。如在三个工作日内仍未收到转账请联系客服查询。 ";

    public final static String WITHDRAWAL_APPROVAL_FAILED = "您于{1}提交的{2}元的提现申请未能审批通过。请重新提交申请或联系客服咨询";

    public final static String WITHDRAWAL_TRANSFER_SUCCEEDED = "您于{1}提交的{2}元的提现申请目前已完成转账。请在您绑定的支付宝账户{3}中查询。如有问题请联系客服咨询。";

    public final static String WITHDRAWAL_TRANSFER_FAILED = "您于{1}提交的{2}元的提现申请转账未能成功，错误代码{3}，请咨询客服确认失败原因。";

    public final static String RESOURCE_APPLICATION_NOTICE = "亲爱的易点玩云游戏用户，排队已到您，即将进入{1}，请尽快进入本平台点击确认开始，如1分钟内未进入设备，将被取消此次资格。";

    public final static String RESET_PASSWORD = "您的动态验证码为：{1}，您正在进行密码重置操作，如非本人操作，请忽略本短信！";
}
