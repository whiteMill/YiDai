package com.sdot.yidai.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/11/29.
 */

public class OrderRzzlVo implements Serializable{
            /**
             * id : 39
             * label : null
             * createdAt : 2017-11-28T07:38:58.000+0000
             * lastModifiedAt : 2017-11-28T09:45:50.000+0000
             * act : null
             * notice : null
             * currentUserCanActList : [{"id":1,"label":"创建","actCode":"create","iconClass":null,"btnClass":null,"sort":10,"importance":false,"actGroup":"UPDATE","messageType":null,"subcategory":null,"topcategory":null},{"id":2,"label":"读取","actCode":"read","iconClass":null,"btnClass":null,"sort":20,"importance":false,"actGroup":"READ","messageType":null,"subcategory":null,"topcategory":null},{"id":3,"label":"编辑/上传图片","actCode":"update","iconClass":null,"btnClass":null,"sort":30,"importance":false,"actGroup":"UPDATE","messageType":null,"subcategory":null,"topcategory":null},{"id":5,"label":"自己的列表","actCode":"listOwn","iconClass":null,"btnClass":null,"sort":50,"importance":false,"actGroup":"READ","messageType":null,"subcategory":null,"topcategory":null},{"id":12,"label":"确定完善资料","actCode":"confirm","iconClass":"fa fa-gg","btnClass":"pull-left  btn btn-success btn-sm  m-r-sm m-b-sm btn-addon","sort":120,"importance":null,"actGroup":"OPERATE","messageType":null,"subcategory":null,"topcategory":null},{"id":22,"label":"营业执照","actCode":"uploadDuplicateofBusinessLicense","iconClass":null,"btnClass":null,"sort":200,"importance":null,"actGroup":"FILE_OPERATE","messageType":null,"subcategory":null,"topcategory":null},{"id":23,"label":"快递业务经营许可证","actCode":"uploadExpressBusinessLicense","iconClass":null,"btnClass":null,"sort":210,"importance":null,"actGroup":"FILE_OPERATE","messageType":null,"subcategory":null,"topcategory":null},{"id":24,"label":"开户许可证","actCode":"uploadPermitForOpeningBankAccount","iconClass":null,"btnClass":null,"sort":220,"importance":null,"actGroup":"FILE_OPERATE","messageType":null,"subcategory":null,"topcategory":null},{"id":25,"label":"公司章程","actCode":"uploadArticlesOfCompanyAssociation","iconClass":null,"btnClass":null,"sort":230,"importance":null,"actGroup":"FILE_OPERATE","messageType":null,"subcategory":null,"topcategory":null},{"id":26,"label":"网点加盟协议","actCode":"uploadTheJoinedCooperationAgreementOfBraches","iconClass":null,"btnClass":null,"sort":240,"importance":null,"actGroup":"FILE_OPERATE","messageType":null,"subcategory":null,"topcategory":null},{"id":29,"label":"租房合同","actCode":"uploadLeaseContract","iconClass":null,"btnClass":null,"sort":250,"importance":null,"actGroup":"FILE_OPERATE","messageType":null,"subcategory":null,"topcategory":null},{"id":30,"label":"夫妻双方身份证正反面照片","actCode":"uploadDoubleSidedPhotosOfIDCard","iconClass":null,"btnClass":null,"sort":270,"importance":null,"actGroup":"FILE_OPERATE","messageType":null,"subcategory":null,"topcategory":null},{"id":31,"label":"结婚证照片","actCode":"uploadmarriageCertificatePhoto","iconClass":null,"btnClass":null,"sort":280,"importance":null,"actGroup":"FILE_OPERATE","messageType":null,"subcategory":null,"topcategory":null},{"id":51,"label":"征信报告","actCode":"uploadCreditReport","iconClass":null,"btnClass":null,"sort":290,"importance":null,"actGroup":"FILE_OPERATE","messageType":null,"subcategory":null,"topcategory":null},{"id":41,"label":"近3个月水电费清单","actCode":"uploadListOfWaterAndElectricityFeeForLast3Months","iconClass":null,"btnClass":null,"sort":300,"importance":null,"actGroup":"FILE_OPERATE","messageType":null,"subcategory":null,"topcategory":null},{"id":50,"label":"房产证","actCode":"uploadHousingOwnershipCertificate","iconClass":null,"btnClass":null,"sort":310,"importance":null,"actGroup":"FILE_OPERATE","messageType":null,"subcategory":null,"topcategory":null},{"id":34,"label":"车辆登记证书","actCode":"uploadMotorVehicleRegister","iconClass":null,"btnClass":null,"sort":320,"importance":null,"actGroup":"FILE_OPERATE","messageType":null,"subcategory":null,"topcategory":null},{"id":28,"label":"近2年快递系统业务数据","actCode":"uploadCheckingAndAnalysisSystemOfBusinessDataForRecent2Years","iconClass":null,"btnClass":null,"sort":330,"importance":null,"actGroup":"FILE_OPERATE","messageType":null,"subcategory":null,"topcategory":null},{"id":44,"label":"公司固定资产清单","actCode":"uploadDetailedListOfFixedAssets","iconClass":null,"btnClass":null,"sort":340,"importance":null,"actGroup":"FILE_OPERATE","messageType":null,"subcategory":null,"topcategory":null},{"id":52,"label":"近4个月的财务报表和科目明细表和纳税申报表","actCode":"uploadFinancialStatements\n","iconClass":null,"btnClass":null,"sort":350,"importance":null,"actGroup":"FILE_OPERATE","messageType":null,"subcategory":null,"topcategory":null},{"id":53,"label":"对公及个人主要账户流水","actCode":"uploadAccountFlow","iconClass":null,"btnClass":null,"sort":360,"importance":null,"actGroup":"FILE_OPERATE","messageType":null,"subcategory":null,"topcategory":null},{"id":54,"label":"跟淘宝电商签订《服务协议》","actCode":"uploadServiceAgreement","iconClass":null,"btnClass":null,"sort":370,"importance":null,"actGroup":"FILE_OPERATE","messageType":null,"subcategory":null,"topcategory":null},{"id":55,"label":"设备采购合同","actCode":"uploadEquipmentPurchaseContract","iconClass":null,"btnClass":null,"sort":380,"importance":null,"actGroup":"FILE_OPERATE","messageType":null,"subcategory":null,"topcategory":null},{"id":15,"label":"上传其他图片","actCode":"uploadOther","iconClass":null,"btnClass":null,"sort":900,"importance":null,"actGroup":"FILE_OPERATE","messageType":null,"subcategory":null,"topcategory":null},{"id":17,"label":"关闭","actCode":"disable","iconClass":"fa fa-close","btnClass":"pull-left m-r-sm m-b-sm btn btn-default btn-sm btn-addon","sort":910,"importance":null,"actGroup":"OPERATE","messageType":null,"subcategory":null,"topcategory":null},{"id":18,"label":"身份验证","actCode":"authentication","iconClass":"fa fa-vcard","btnClass":"pull-left  btn btn-info btn-sm  m-r-sm m-b-sm btn-addon","sort":930,"importance":null,"actGroup":"OPERATE","messageType":null,"subcategory":null,"topcategory":null},{"id":19,"label":"下载所有图片","actCode":"downloadFile","iconClass":"fa fa-file-zip-o","btnClass":"pull-left  btn btn-info btn-sm  m-r-sm m-b-sm btn-addon","sort":940,"importance":null,"actGroup":"FILE_PACKAGE","messageType":null,"subcategory":null,"topcategory":null},{"id":20,"label":"修改额度","actCode":"modifyApplyAmount","iconClass":"fa fa-pencil","btnClass":"pull-left  btn btn-primary btn-sm  m-r-sm m-b-sm btn-addon","sort":950,"importance":null,"actGroup":"OPERATE","messageType":null,"subcategory":null,"topcategory":null},{"id":21,"label":"修改身份证","actCode":"changeID","iconClass":"fa fa-pencil","btnClass":"pull-left  btn btn-primary btn-sm  m-r-sm m-b-sm btn-addon","sort":960,"importance":null,"actGroup":"UPDATE","messageType":null,"subcategory":null,"topcategory":null}]
             * fileObject : null
             * wechatFiles : []
             * stateActList : {}
             * fileCategoryTree : null
             * filePackage : null
             * topcategory : -
             * subcategory : -
             * application : null
             * applyAmount : 2000000
             * comfirmAmount : null
             * comfirmComprehensiveLiabilities : null
             * comfirmMortgageLiabilities : null
             * applyPeriodNumber : 36
             * applyPeriodCode : null
             * realName : 伽耶
             * applyMobile : 15356795858
             * applyIdentityNo : 72947926299273
             * applyEnterpriseName : 衣蛾，我
             * applyEnterpriseReigionName : null
             * applyEnterpriseAddress : null
             * applyReferrerRealName : 是
             * applyDayPickExpress : 2465
             * applyDayPatchExpress : 885
             * applyWayBillFee : null
             * comment : null
             * applyEnterpriseProvince : 上海市
             * applyEnterpriseCity : 上海市
             * applyEnterpriseDistrict : 金山区
             * applyEnterpriseTown : null
             * serviceName : 中通快递
             * serviceId : null
             * distribution : null
             * stateEnable : false
             * stateAdopt : false
             * _links : {"self":{"href":"http://test.edairisk.com/rest/orderrzzls/39"},"orderrzzl":{"href":"http://test.edairisk.com/rest/orderrzzls/39"},"debtorReceiveAccount":{"href":"http://test.edairisk.com/rest/orderrzzls/39/debtorReceiveAccount"},"user":{"href":"http://test.edairisk.com/rest/orderrzzls/39/user"},"product":{"href":"http://test.edairisk.com/rest/orderrzzls/39/product"},"debtorAccount":{"href":"http://test.edairisk.com/rest/orderrzzls/39/debtorAccount"},"logs":{"href":"http://test.edairisk.com/rest/orderrzzls/39/logs"},"createdByDepartment":{"href":"http://test.edairisk.com/rest/orderrzzls/39/createdByDepartment"},"enterprise":{"href":"http://test.edairisk.com/rest/orderrzzls/39/enterprise"},"serviceUser":{"href":"http://test.edairisk.com/rest/orderrzzls/39/serviceUser"},"workflow":{"href":"http://test.edairisk.com/rest/orderrzzls/39/workflow"},"person":{"href":"http://test.edairisk.com/rest/orderrzzls/39/person"},"lastModifiedBy":{"href":"http://test.edairisk.com/rest/orderrzzls/39/lastModifiedBy"},"createdBy":{"href":"http://test.edairisk.com/rest/orderrzzls/39/createdBy"},"files":{"href":"http://test.edairisk.com/rest/orderrzzls/39/files"},"state":{"href":"http://test.edairisk.com/rest/orderrzzls/39/state"},"lastAct":{"href":"http://test.edairisk.com/rest/orderrzzls/39/lastAct"}}
             */

            private String id;
            private Object label;
            private String createdAt;
            private String lastModifiedAt;
            private Object act;
            private Object notice;
            private Object fileObject;
            private Object stateActList;
            private Object fileCategoryTree;
            private Object filePackage;
            private String topcategory;
            private String subcategory;
            private Object application;
            private String applyAmount;
            private Object comfirmAmount;
            private Object comfirmComprehensiveLiabilities;
            private Object comfirmMortgageLiabilities;
            private String applyPeriodNumber;
            private Object applyPeriodCode;
            private String realName;
            private String applyMobile;
            private String applyIdentityNo;
            private String applyEnterpriseName;
            private Object applyEnterpriseReigionName;
            private Object applyEnterpriseAddress;
            private String applyReferrerRealName;
            private String applyDayPickExpress;
            private String applyDayPatchExpress;
            private Object applyWayBillFee;
            private Object comment;
            private String agentBrand;
            private String applyEnterpriseProvince;
            private String applyEnterpriseCity;
            private String applyEnterpriseDistrict;
            private Object applyEnterpriseTown;
            private String serviceName;
            private Object serviceId;
            private Object distribution;
            private boolean stateEnable;
            private boolean stateAdopt;
            private Object _links;
            private Object currentUserCanActList;
            private Object wechatFiles;

    public String getAgentBrand() {
        return agentBrand;
    }

    public void setAgentBrand(String agentBrand) {
        this.agentBrand = agentBrand;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object getLabel() {
        return label;
    }

    public void setLabel(Object label) {
        this.label = label;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getLastModifiedAt() {
        return lastModifiedAt;
    }

    public void setLastModifiedAt(String lastModifiedAt) {
        this.lastModifiedAt = lastModifiedAt;
    }

    public Object getAct() {
        return act;
    }

    public void setAct(Object act) {
        this.act = act;
    }

    public Object getNotice() {
        return notice;
    }

    public void setNotice(Object notice) {
        this.notice = notice;
    }

    public Object getFileObject() {
        return fileObject;
    }

    public void setFileObject(Object fileObject) {
        this.fileObject = fileObject;
    }

    public Object getStateActList() {
        return stateActList;
    }

    public void setStateActList(Object stateActList) {
        this.stateActList = stateActList;
    }

    public Object getFileCategoryTree() {
        return fileCategoryTree;
    }

    public void setFileCategoryTree(Object fileCategoryTree) {
        this.fileCategoryTree = fileCategoryTree;
    }

    public Object getFilePackage() {
        return filePackage;
    }

    public void setFilePackage(Object filePackage) {
        this.filePackage = filePackage;
    }

    public String getTopcategory() {
        return topcategory;
    }

    public void setTopcategory(String topcategory) {
        this.topcategory = topcategory;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }

    public Object getApplication() {
        return application;
    }

    public void setApplication(Object application) {
        this.application = application;
    }

    public String getApplyAmount() {
        return applyAmount;
    }

    public void setApplyAmount(String applyAmount) {
        this.applyAmount = applyAmount;
    }

    public Object getComfirmAmount() {
        return comfirmAmount;
    }

    public void setComfirmAmount(Object comfirmAmount) {
        this.comfirmAmount = comfirmAmount;
    }

    public Object getComfirmComprehensiveLiabilities() {
        return comfirmComprehensiveLiabilities;
    }

    public void setComfirmComprehensiveLiabilities(Object comfirmComprehensiveLiabilities) {
        this.comfirmComprehensiveLiabilities = comfirmComprehensiveLiabilities;
    }

    public Object getComfirmMortgageLiabilities() {
        return comfirmMortgageLiabilities;
    }

    public void setComfirmMortgageLiabilities(Object comfirmMortgageLiabilities) {
        this.comfirmMortgageLiabilities = comfirmMortgageLiabilities;
    }

    public String getApplyPeriodNumber() {
        return applyPeriodNumber;
    }

    public void setApplyPeriodNumber(String applyPeriodNumber) {
        this.applyPeriodNumber = applyPeriodNumber;
    }

    public Object getApplyPeriodCode() {
        return applyPeriodCode;
    }

    public void setApplyPeriodCode(Object applyPeriodCode) {
        this.applyPeriodCode = applyPeriodCode;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getApplyMobile() {
        return applyMobile;
    }

    public void setApplyMobile(String applyMobile) {
        this.applyMobile = applyMobile;
    }

    public String getApplyIdentityNo() {
        return applyIdentityNo;
    }

    public void setApplyIdentityNo(String applyIdentityNo) {
        this.applyIdentityNo = applyIdentityNo;
    }

    public String getApplyEnterpriseName() {
        return applyEnterpriseName;
    }

    public void setApplyEnterpriseName(String applyEnterpriseName) {
        this.applyEnterpriseName = applyEnterpriseName;
    }

    public Object getApplyEnterpriseReigionName() {
        return applyEnterpriseReigionName;
    }

    public void setApplyEnterpriseReigionName(Object applyEnterpriseReigionName) {
        this.applyEnterpriseReigionName = applyEnterpriseReigionName;
    }

    public Object getApplyEnterpriseAddress() {
        return applyEnterpriseAddress;
    }

    public void setApplyEnterpriseAddress(Object applyEnterpriseAddress) {
        this.applyEnterpriseAddress = applyEnterpriseAddress;
    }

    public String getApplyReferrerRealName() {
        return applyReferrerRealName;
    }

    public void setApplyReferrerRealName(String applyReferrerRealName) {
        this.applyReferrerRealName = applyReferrerRealName;
    }

    public String getApplyDayPickExpress() {
        return applyDayPickExpress;
    }

    public void setApplyDayPickExpress(String applyDayPickExpress) {
        this.applyDayPickExpress = applyDayPickExpress;
    }

    public String getApplyDayPatchExpress() {
        return applyDayPatchExpress;
    }

    public void setApplyDayPatchExpress(String applyDayPatchExpress) {
        this.applyDayPatchExpress = applyDayPatchExpress;
    }

    public Object getApplyWayBillFee() {
        return applyWayBillFee;
    }

    public void setApplyWayBillFee(Object applyWayBillFee) {
        this.applyWayBillFee = applyWayBillFee;
    }

    public Object getComment() {
        return comment;
    }

    public void setComment(Object comment) {
        this.comment = comment;
    }

    public String getApplyEnterpriseProvince() {
        return applyEnterpriseProvince;
    }

    public void setApplyEnterpriseProvince(String applyEnterpriseProvince) {
        this.applyEnterpriseProvince = applyEnterpriseProvince;
    }

    public String getApplyEnterpriseCity() {
        return applyEnterpriseCity;
    }

    public void setApplyEnterpriseCity(String applyEnterpriseCity) {
        this.applyEnterpriseCity = applyEnterpriseCity;
    }

    public String getApplyEnterpriseDistrict() {
        return applyEnterpriseDistrict;
    }

    public void setApplyEnterpriseDistrict(String applyEnterpriseDistrict) {
        this.applyEnterpriseDistrict = applyEnterpriseDistrict;
    }

    public Object getApplyEnterpriseTown() {
        return applyEnterpriseTown;
    }

    public void setApplyEnterpriseTown(Object applyEnterpriseTown) {
        this.applyEnterpriseTown = applyEnterpriseTown;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Object getServiceId() {
        return serviceId;
    }

    public void setServiceId(Object serviceId) {
        this.serviceId = serviceId;
    }

    public Object getDistribution() {
        return distribution;
    }

    public void setDistribution(Object distribution) {
        this.distribution = distribution;
    }

    public boolean isStateEnable() {
        return stateEnable;
    }

    public void setStateEnable(boolean stateEnable) {
        this.stateEnable = stateEnable;
    }

    public boolean isStateAdopt() {
        return stateAdopt;
    }

    public void setStateAdopt(boolean stateAdopt) {
        this.stateAdopt = stateAdopt;
    }

    public Object get_links() {
        return _links;
    }

    public void set_links(Object _links) {
        this._links = _links;
    }

    public Object getCurrentUserCanActList() {
        return currentUserCanActList;
    }

    public void setCurrentUserCanActList(Object currentUserCanActList) {
        this.currentUserCanActList = currentUserCanActList;
    }

    public Object getWechatFiles() {
        return wechatFiles;
    }

    public void setWechatFiles(Object wechatFiles) {
        this.wechatFiles = wechatFiles;
    }
}
