package com.sdot.yidai.utils;

/**
 * Created by windmill on 2017/10/30.
 */

public class URL {

    //测试服务器  http://test.edairisk.com/
    //正式服务器  http://test.edairisk.com/
    //获取验证码
    public static final String GET_YZM = "http://test.edairisk.com/smsCode";
    //登录
    public static final String USER_LOGIN = "http://test.edairisk.com/weblogin";
    //注册
    public static final String USER_REGISTER = "http://test.edairisk.com/register";
    //修改手机号码
    public static final String UPDATE_PHONE = "http://test.edairisk.com/smsCode";
    //修改密码
    public static final String USER_PASS = "http://test.edairisk.com/smsCode";
    //借款申请
    public static final String  APPLY_MONEY= "http://test.edairisk.com/rest/orderwdsjshes";
    //获取随借随还
    public static final String  HAVE_PRODUCT = "http://test.edairisk.com/rest/orderwdsjshes/search/listOwn";
    //获取网点信用贷
    public static final String  HAVE_WDXY = "http://test.edairisk.com/rest/orderwdxyds/search/listOwn";
    //获取融资租赁
    public static final String  HAVE_RZZL = "http://test.edairisk.com/rest/orderrzzls/search/listOwn";
    //上传图片
    public  static final String UPDATE_IMAGE = "http://test.edairisk.com/fileUpload";
    //随借随还图片绑定
    public static final String BIND_IMAGE = "http://test.edairisk.com/rest/orderwdsjshes/";
    //网点信用图片绑定
    public static final String BIND_WANGD_IMAGE = "http://test.edairisk.com/rest/orderwdxyds/";
    //融资租赁图片绑定
    public static final String BIND_RONGZ_IMAGE = "http://test.edairisk.com/rest/orderrzzls/";
    //获取PERSION
    public static final String GET_EDU_PERSION_ID = "http://test.edairisk.com/rest/users/";
    //获取额度
    public static final String GET_EDU_AMOUNT = "http://test.edairisk.com/rest/creditcards/";
    //获取借款记录/rest/creditcards/8/loans
    public static final String BORROW_RECORDER ="http://test.edairisk.com/rest/creditcards/";
    //获取还款记录/rest/creditcards/8/creditrepayments
    public static final String BACK_RECORDER ="http://test.edairisk.com/rest/creditcards/";
    //用户注册协议
    public static final String USER_XIEYI ="http://test.edairisk.com/wechat/wlqz/html/loan/userAgreement.html";
    //密码修改
    public static final String UPDATE_PASS = "http://test.edairisk.com/rest/users/";
    //忘记密码
    public static final String FORGET_PASS="http://test.edairisk.com/resetPassword";
    //得到随借随还图片
    public static final String GET_IMAGE = "http://test.edairisk.com/rest/orderwdsjshes/";
    //申请网点信用贷
    public static final String APPLY_WANGDIAN = "http://test.edairisk.com/rest/orderwdxyds";
    //申请融资租赁
    public static final String APPLY_RONGZZL = "http://test.edairisk.com/rest/orderrzzls";
    //获取消息
    public static final String GET_MESSAGE = "http://test.edairisk.com/rest/appMessages/search/findByToUser";
    //开通入口
    public static final String BEND_MONEY = "http://118.178.91.113:8080/bdjf/html5/basicInformation.html?";
    //借钱入口
    public static final String BEND_BORROW_MONEY = "http://118.178.91.113:8080/bdjf/html5/borrowByFollow.html?";
    //贝兜还款
    public static final String BEND_BACK_MONEY = "http://118.178.91.113:8080/bdjf/html5/repayment.html?";
    //获取各产品状态
    public static final String GET_SORT_PRODUCT = "http://test.edairisk.com/getuserproductinfo";
    //获取是否有消息未读
    public static final String GET_RED_DOT = "http://test.edairisk.com/rest/appMessages/search/findByUserAndReading";
    //修改消息状态
    public static final String CHANGE_NEWS_STATE = "http://test.edairisk.com/rest/appMessages/";
    //提交企业信息
    public static final String APPLY_COMPANY = "http://test.edairisk.com/company/addcompany";
    //提交面单白条
    public static final String APPLY_MDBT = "http://test.edairisk.com/rest/ordermdbts";
    //获取企业信息
    public static final String QUERY_COMPANY = "http://test.edairisk.com/rest/companies/search/findByCreateBy";
    //获取面单白条信息
    public static final String HAVE_MDBT = "http://test.edairisk.com/rest/ordermdbts/search/listOwn";
    //获取面单白条资料
    public static final String BIND_MDBT_IMAGE = "http://test.edairisk.com/rest/ordermdbts/";
    //获取公司信息
    public static final String COMPANY_INFO = "http://test.edairisk.com/company/getcompanyinformation";
    //添加员工
    public static final String ADD_STAFF = "http://test.edairisk.com/company/addemployee";
    //移除员工
    public static final String DELETE_STAFF = "http://test.edairisk.com/company/removeemployee";
    //编辑公司信息
    public static final String EDIT_COMPANY = "http://test.edairisk.com/company/changecompanyinfo";
    //申请调整额度
    public static final String CHANGE_EEDU = "http://test.edairisk.com/quota/quotaApplication";
    //查询某一客户某一自然月是否已经申请过提额
    public static final String CHANGE_EEDU_STATE = "http://test.edairisk.com/quota/queryQuotaRecord";
    //当月已申请额度test
    public static final String MANTH_TOTAL_MONEY = "http://test.edairisk.com/rest/loans/search/findByCreditcardAndApplyDateMonth";
    //面单白条利率查询
    public static final String QUERY_LILV  = "http://test.edairisk.com/rest/products/4";
    //申请借款
    public static final String APPLY_MD_SINGLE = "http://test.edairisk.com/rest/loans";
    //查询面单白条的借款记录
    public static final String QUERY_MD_BORROW_RECORD = "http://test.edairisk.com/rest/loans/search/findByCreatedBy";
    //查询面单白条某借款下的还款计划
    public static final String QUERY_MD_BACK_PLAN = "http://test.edairisk.com/rest/creditrepayplans/search/getCreditRepayplanByLoan";
    //面单白条 未还清  已审核
    public static final String WEI_MD_BORROW_RECORD = "http://test.edairisk.com/rest/loans/search/findByCreatedBy";
    //面单白条 未还清 未审核
    public static final String WEI_MD_BORROW_RECORD_NO = "http://test.edairisk.com/rest/loans/search/findByStateAndCreatedBy";
    //面单白条 已还清
    public static final String YI_MD_BORROW_RECORD = "http://test.edairisk.com/rest/loans/search/findByStateCode";
    //面单白条还款
    public static final String MD_BACK_FEES = "http://test.edairisk.com/rest/creditrepayments";
    //查询是否提交过还款凭证
    public static final String  QUERY_BAKC_IMAGE = "http://test.edairisk.com/rest/creditrepayments/search/findByCreditrepayplanAndState";
    //绑定还款凭证
    public static final String BIND_BACK_IMAGE = "http://test.edairisk.com/rest/creditrepayments/";
    //查询最新的还款记录
    public static final String QUERT_NEW_RECORD = "http://test.edairisk.com/rest/creditrepayments/search/getCreditRepaymentByCreditrepayplan";
    //查询是老板还是员工
    public static final String QUERU_IS_BOSS = "http://test.edairisk.com/user/getCompanyRole";
    //查看公司的借款记录
    public static final String QUERY_COMPANY_BORR_RECORD = "http://test.edairisk.com/rest/loans/search/findByCompanyId";
    //查询异常用户
    public static final String EXCEPTION_USER = "http://test.edairisk.com/person/getAccountStatus";
    //查询CREATED和ADOPT两种状态的数据
    public static final String TWO_STATE_USER = "http://test.edairisk.com/rest/approveLoans/search/findByTwoStateCode";
    //根据id查询异常用户申请借款记录接口
    public static final String FIND_BY_PERSION_STATE_CODE = "http://test.edairisk.com/rest/approveLoans/search/findByPersonAndStateCode";
    //提交异常用户借款记录
    public static final String UPDATE_EXCEPTION_RECORD = "http://test.edairisk.com/approveLoan/addApproveLoan";
    //逾期查询
    public static final String QUERY_YUQI = "http://test.edairisk.com/rest/loanoverdues/search/findEnable";
    //查询Person
    public static final String INIT_PERSON = "http://test.edairisk.com/person/getPersonid";
    //查询是够有逾期记录
    public static final String QUERY_HAVE_YUQI = "http://test.edairisk.com/rest/loans/search/findByCreatedByAndOverdue";
}
