package com.sdot.yidai.model;

/**
 * Created by Administrator on 2017/11/8.
 */

public class UserVo {


    /**
     * country : null
     * id : 295
     * state : {"id":3,"label":"有效"}
     * role : {"id":3,"label":"普通用户"}
     * province : null
     * city : null
     * sig : null
     * username : 18758031162
     * workflow : null
     * person : {"id":68,"label":null}
     * headimgurl : null
     * sex : null
     * nickname : null
     * department : null
     */

    private String country;
    private String id;
    private StateVo state;
    private RoleVo role;
    private String province;
    private String city;
    private String sig;
    private String username;
    private Object workflow;
    private PersonVo person;
    private String headimgurl;
    private String companyRole;
    private String sex;
    private String nickname;
    private String department;

    public String getCompanyRole() {
        return companyRole;
    }

    public void setCompanyRole(String companyRole) {
        this.companyRole = companyRole;
    }

    /*
    public String getCompanyRole() {
        return companyRole;
    }

    public void setCompanyRole(String companyRole) {
        this.companyRole = companyRole;
    }*/

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public StateVo getState() {
        return state;
    }

    public void setState(StateVo state) {
        this.state = state;
    }

    public RoleVo getRole() {
        return role;
    }

    public void setRole(RoleVo role) {
        this.role = role;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSig() {
        return sig;
    }

    public void setSig(String sig) {
        this.sig = sig;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Object getWorkflow() {
        return workflow;
    }

    public void setWorkflow(Object workflow) {
        this.workflow = workflow;
    }

    public PersonVo getPerson() {
        return person;
    }

    public void setPerson(PersonVo person) {
        this.person = person;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public static class StateVo {
        /**
         * id : 3
         * label : 有效
         */

        private int id;
        private String label;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }
    }

    public static class RoleVo {
        /**
         * id : 3
         * label : 普通用户
         */

        private int id;
        private String label;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }
    }

    public static class PersonVo {

        public PersonVo(int id) {
            this.id = id;
        }

        /**
         * id : 68
         * label : null
         */



        private int id;
        private Object label;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Object getLabel() {
            return label;
        }

        public void setLabel(Object label) {
            this.label = label;
        }
    }

}
