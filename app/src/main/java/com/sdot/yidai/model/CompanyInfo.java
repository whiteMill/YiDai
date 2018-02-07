package com.sdot.yidai.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/12/15.
 */

public class CompanyInfo implements Serializable{

    /**
     * id : 68
     * companyName : 耒阳测试
     * companyNumber : 6879451349
     * companyAddress : null
     * companyPersonName : 挺轻松
     * state : CREATED
     */

    private String id;
    private String companyName;
    private String companyNumber;
    private String companyProvince;
    private String companyCity;
    private String companyDistrict;
    private String companyAddress;

    private String companyPersonName;
    private String state;

    public String getCompanyProvince() {
        return companyProvince;
    }

    public void setCompanyProvince(String companyProvince) {
        this.companyProvince = companyProvince;
    }

    public String getCompanyCity() {
        return companyCity;
    }

    public void setCompanyCity(String companyCity) {
        this.companyCity = companyCity;
    }

    public String getCompanyDistrict() {
        return companyDistrict;
    }

    public void setCompanyDistrict(String companyDistrict) {
        this.companyDistrict = companyDistrict;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyNumber() {
        return companyNumber;
    }

    public void setCompanyNumber(String companyNumber) {
        this.companyNumber = companyNumber;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getCompanyPersonName() {
        return companyPersonName;
    }

    public void setCompanyPersonName(String companyPersonName) {
        this.companyPersonName = companyPersonName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
