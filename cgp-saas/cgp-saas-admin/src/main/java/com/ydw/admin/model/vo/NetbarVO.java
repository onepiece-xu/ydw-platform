package com.ydw.admin.model.vo;

import java.util.List;

public class NetbarVO {
    private static final long serialVersionUID=1L;


    private String id;

    /**
     * 网吧名称
     */
    private String name;

    private String description;

    /**
     * 地址
     */
    private String location;

    private String logoPic;

    /**
     * 省
     */
    private String province;

    /**
     * 市
     */
    private String city;

    /**
     * 区
     */
    private String district;

    /**
     * 特别服务
     */
    private String specialService;

    /**
     * 基础设施
     */
    private String baseStation;

    /**
     * 营业时间
     */
    private String businessHours;

    /**
     * 配套设施
     */
    private String matchStation;

    private Boolean valid;

    private String identification;

    private String list;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLogoPic() {
        return logoPic;
    }

    public void setLogoPic(String logoPic) {
        this.logoPic = logoPic;
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

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getSpecialService() {
        return specialService;
    }

    public void setSpecialService(String specialService) {
        this.specialService = specialService;
    }

    public String getBaseStation() {
        return baseStation;
    }

    public void setBaseStation(String baseStation) {
        this.baseStation = baseStation;
    }

    public String getBusinessHours() {
        return businessHours;
    }

    public void setBusinessHours(String businessHours) {
        this.businessHours = businessHours;
    }

    public String getMatchStation() {
        return matchStation;
    }

    public void setMatchStation(String matchStation) {
        this.matchStation = matchStation;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public String getList() {
        return list;
    }

    public void setList(String list) {
        this.list = list;
    }
}
