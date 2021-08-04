package com.ydw.admin.model.vo;

public class NetbarInfoVO {

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

    private String firstPic;
    private String secondPic;
    private String thirdPic;
    private String fourthPic;
    private String fifthPic;

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

    private Integer totalCount;

    private Integer availableCount;
    private Integer idleCount;
    private  String clusterId;
    private  String identification;

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public String getClusterId() {
        return clusterId;
    }

    public void setClusterId(String clusterId) {
        this.clusterId = clusterId;
    }

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

    public String getFirstPic() {
        return firstPic;
    }

    public void setFirstPic(String firstPic) {
        this.firstPic = firstPic;
    }

    public String getSecondPic() {
        return secondPic;
    }

    public void setSecondPic(String secondPic) {
        this.secondPic = secondPic;
    }

    public String getThirdPic() {
        return thirdPic;
    }

    public void setThirdPic(String thirdPic) {
        this.thirdPic = thirdPic;
    }

    public String getFourthPic() {
        return fourthPic;
    }

    public void setFourthPic(String fourthPic) {
        this.fourthPic = fourthPic;
    }

    public String getFifthPic() {
        return fifthPic;
    }

    public void setFifthPic(String fifthPic) {
        this.fifthPic = fifthPic;
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

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getAvailableCount() {
        return availableCount;
    }

    public void setAvailableCount(Integer availableCount) {
        this.availableCount = availableCount;
    }

    public Integer getIdleCount() {
        return idleCount;
    }

    public void setIdleCount(Integer idleCount) {
        this.idleCount = idleCount;
    }
}
