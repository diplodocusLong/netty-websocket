package com.ygyg.data.entity;


// infomodel
public class Infomodel {
    private Long modelId;
    private String name;
    private String shortName;
    private String code;
    private Long nodeType;
    private Long relationModelId;
    private String substanceSystem;
    private String substanceDeviceGroup;
    private String substanceDevice;

    public Long getModelId() {
        return modelId;
    }

    public void setModelId(Long modelId) {
        this.modelId = modelId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getNodeType() {
        return nodeType;
    }

    public void setNodeType(Long nodeType) {
        this.nodeType = nodeType;
    }

    public Long getRelationModelId() {
        return relationModelId;
    }

    public void setRelationModelId(Long relationModelId) {
        this.relationModelId = relationModelId;
    }

    public String getSubstanceSystem() {
        return substanceSystem;
    }

    public void setSubstanceSystem(String substanceSystem) {
        this.substanceSystem = substanceSystem;
    }

    public String getSubstanceDeviceGroup() {
        return substanceDeviceGroup;
    }

    public void setSubstanceDeviceGroup(String substanceDeviceGroup) {
        this.substanceDeviceGroup = substanceDeviceGroup;
    }

    public String getSubstanceDevice() {
        return substanceDevice;
    }

    public void setSubstanceDevice(String substanceDevice) {
        this.substanceDevice = substanceDevice;
    }
}
