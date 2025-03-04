// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) TypeSpec Code Generator.

package com.azure.analytics.purview.datamap.models;

import com.azure.core.annotation.Fluent;
import com.azure.core.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;

/**
 * class that captures details of a struct-type.
 */
@Fluent
public final class AtlasBusinessMetadataDef {
    /*
     * The enum of type category.
     */
    @Generated
    @JsonProperty(value = "category")
    private TypeCategory category;

    /*
     * The created time of the record.
     */
    @Generated
    @JsonProperty(value = "createTime")
    private Long createTime;

    /*
     * The user who created the record.
     */
    @Generated
    @JsonProperty(value = "createdBy")
    private String createdBy;

    /*
     * The date format.
     */
    @Generated
    @JsonProperty(value = "dateFormatter")
    private DateFormat dateFormatter;

    /*
     * The description of the type definition.
     */
    @Generated
    @JsonProperty(value = "description")
    private String description;

    /*
     * The GUID of the type definition.
     */
    @Generated
    @JsonProperty(value = "guid")
    private String guid;

    /*
     * The name of the type definition.
     */
    @Generated
    @JsonProperty(value = "name")
    private String name;

    /*
     * The options for the type definition.
     */
    @Generated
    @JsonProperty(value = "options")
    private Map<String, String> options;

    /*
     * The service type.
     */
    @Generated
    @JsonProperty(value = "serviceType")
    private String serviceType;

    /*
     * The version of the type.
     */
    @Generated
    @JsonProperty(value = "typeVersion")
    private String typeVersion;

    /*
     * The update time of the record.
     */
    @Generated
    @JsonProperty(value = "updateTime")
    private Long updateTime;

    /*
     * The user who updated the record.
     */
    @Generated
    @JsonProperty(value = "updatedBy")
    private String updatedBy;

    /*
     * The version of the record.
     */
    @Generated
    @JsonProperty(value = "version")
    private Long version;

    /*
     * ETag for concurrency control.
     */
    @Generated
    @JsonProperty(value = "lastModifiedTS")
    private String lastModifiedTS;

    /*
     * An array of attribute definitions.
     */
    @Generated
    @JsonProperty(value = "attributeDefs")
    private List<AtlasAttributeDef> attributeDefs;

    /**
     * Creates an instance of AtlasBusinessMetadataDef class.
     */
    @Generated
    public AtlasBusinessMetadataDef() {
    }

    /**
     * Get the category property: The enum of type category.
     * 
     * @return the category value.
     */
    @Generated
    public TypeCategory getCategory() {
        return this.category;
    }

    /**
     * Set the category property: The enum of type category.
     * 
     * @param category the category value to set.
     * @return the AtlasBusinessMetadataDef object itself.
     */
    @Generated
    public AtlasBusinessMetadataDef setCategory(TypeCategory category) {
        this.category = category;
        return this;
    }

    /**
     * Get the createTime property: The created time of the record.
     * 
     * @return the createTime value.
     */
    @Generated
    public Long getCreateTime() {
        return this.createTime;
    }

    /**
     * Set the createTime property: The created time of the record.
     * 
     * @param createTime the createTime value to set.
     * @return the AtlasBusinessMetadataDef object itself.
     */
    @Generated
    public AtlasBusinessMetadataDef setCreateTime(Long createTime) {
        this.createTime = createTime;
        return this;
    }

    /**
     * Get the createdBy property: The user who created the record.
     * 
     * @return the createdBy value.
     */
    @Generated
    public String getCreatedBy() {
        return this.createdBy;
    }

    /**
     * Set the createdBy property: The user who created the record.
     * 
     * @param createdBy the createdBy value to set.
     * @return the AtlasBusinessMetadataDef object itself.
     */
    @Generated
    public AtlasBusinessMetadataDef setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    /**
     * Get the dateFormatter property: The date format.
     * 
     * @return the dateFormatter value.
     */
    @Generated
    public DateFormat getDateFormatter() {
        return this.dateFormatter;
    }

    /**
     * Set the dateFormatter property: The date format.
     * 
     * @param dateFormatter the dateFormatter value to set.
     * @return the AtlasBusinessMetadataDef object itself.
     */
    @Generated
    public AtlasBusinessMetadataDef setDateFormatter(DateFormat dateFormatter) {
        this.dateFormatter = dateFormatter;
        return this;
    }

    /**
     * Get the description property: The description of the type definition.
     * 
     * @return the description value.
     */
    @Generated
    public String getDescription() {
        return this.description;
    }

    /**
     * Set the description property: The description of the type definition.
     * 
     * @param description the description value to set.
     * @return the AtlasBusinessMetadataDef object itself.
     */
    @Generated
    public AtlasBusinessMetadataDef setDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * Get the guid property: The GUID of the type definition.
     * 
     * @return the guid value.
     */
    @Generated
    public String getGuid() {
        return this.guid;
    }

    /**
     * Set the guid property: The GUID of the type definition.
     * 
     * @param guid the guid value to set.
     * @return the AtlasBusinessMetadataDef object itself.
     */
    @Generated
    public AtlasBusinessMetadataDef setGuid(String guid) {
        this.guid = guid;
        return this;
    }

    /**
     * Get the name property: The name of the type definition.
     * 
     * @return the name value.
     */
    @Generated
    public String getName() {
        return this.name;
    }

    /**
     * Set the name property: The name of the type definition.
     * 
     * @param name the name value to set.
     * @return the AtlasBusinessMetadataDef object itself.
     */
    @Generated
    public AtlasBusinessMetadataDef setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Get the options property: The options for the type definition.
     * 
     * @return the options value.
     */
    @Generated
    public Map<String, String> getOptions() {
        return this.options;
    }

    /**
     * Set the options property: The options for the type definition.
     * 
     * @param options the options value to set.
     * @return the AtlasBusinessMetadataDef object itself.
     */
    @Generated
    public AtlasBusinessMetadataDef setOptions(Map<String, String> options) {
        this.options = options;
        return this;
    }

    /**
     * Get the serviceType property: The service type.
     * 
     * @return the serviceType value.
     */
    @Generated
    public String getServiceType() {
        return this.serviceType;
    }

    /**
     * Set the serviceType property: The service type.
     * 
     * @param serviceType the serviceType value to set.
     * @return the AtlasBusinessMetadataDef object itself.
     */
    @Generated
    public AtlasBusinessMetadataDef setServiceType(String serviceType) {
        this.serviceType = serviceType;
        return this;
    }

    /**
     * Get the typeVersion property: The version of the type.
     * 
     * @return the typeVersion value.
     */
    @Generated
    public String getTypeVersion() {
        return this.typeVersion;
    }

    /**
     * Set the typeVersion property: The version of the type.
     * 
     * @param typeVersion the typeVersion value to set.
     * @return the AtlasBusinessMetadataDef object itself.
     */
    @Generated
    public AtlasBusinessMetadataDef setTypeVersion(String typeVersion) {
        this.typeVersion = typeVersion;
        return this;
    }

    /**
     * Get the updateTime property: The update time of the record.
     * 
     * @return the updateTime value.
     */
    @Generated
    public Long getUpdateTime() {
        return this.updateTime;
    }

    /**
     * Set the updateTime property: The update time of the record.
     * 
     * @param updateTime the updateTime value to set.
     * @return the AtlasBusinessMetadataDef object itself.
     */
    @Generated
    public AtlasBusinessMetadataDef setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    /**
     * Get the updatedBy property: The user who updated the record.
     * 
     * @return the updatedBy value.
     */
    @Generated
    public String getUpdatedBy() {
        return this.updatedBy;
    }

    /**
     * Set the updatedBy property: The user who updated the record.
     * 
     * @param updatedBy the updatedBy value to set.
     * @return the AtlasBusinessMetadataDef object itself.
     */
    @Generated
    public AtlasBusinessMetadataDef setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    /**
     * Get the version property: The version of the record.
     * 
     * @return the version value.
     */
    @Generated
    public Long getVersion() {
        return this.version;
    }

    /**
     * Set the version property: The version of the record.
     * 
     * @param version the version value to set.
     * @return the AtlasBusinessMetadataDef object itself.
     */
    @Generated
    public AtlasBusinessMetadataDef setVersion(Long version) {
        this.version = version;
        return this;
    }

    /**
     * Get the lastModifiedTS property: ETag for concurrency control.
     * 
     * @return the lastModifiedTS value.
     */
    @Generated
    public String getLastModifiedTS() {
        return this.lastModifiedTS;
    }

    /**
     * Set the lastModifiedTS property: ETag for concurrency control.
     * 
     * @param lastModifiedTS the lastModifiedTS value to set.
     * @return the AtlasBusinessMetadataDef object itself.
     */
    @Generated
    public AtlasBusinessMetadataDef setLastModifiedTS(String lastModifiedTS) {
        this.lastModifiedTS = lastModifiedTS;
        return this;
    }

    /**
     * Get the attributeDefs property: An array of attribute definitions.
     * 
     * @return the attributeDefs value.
     */
    @Generated
    public List<AtlasAttributeDef> getAttributeDefs() {
        return this.attributeDefs;
    }

    /**
     * Set the attributeDefs property: An array of attribute definitions.
     * 
     * @param attributeDefs the attributeDefs value to set.
     * @return the AtlasBusinessMetadataDef object itself.
     */
    @Generated
    public AtlasBusinessMetadataDef setAttributeDefs(List<AtlasAttributeDef> attributeDefs) {
        this.attributeDefs = attributeDefs;
        return this;
    }
}
