// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) TypeSpec Code Generator.

package com.azure.analytics.purview.datamap.models;

import com.azure.core.annotation.Fluent;
import com.azure.core.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The basic information for term categorization.
 */
@Fluent
public final class AtlasTermCategorizationHeader {
    /*
     * The GUID of the category.
     */
    @Generated
    @JsonProperty(value = "categoryGuid")
    private String categoryGuid;

    /*
     * The description of the record.
     */
    @Generated
    @JsonProperty(value = "description")
    private String description;

    /*
     * The display text.
     */
    @Generated
    @JsonProperty(value = "displayText")
    private String displayText;

    /*
     * The GUID of the relationship.
     */
    @Generated
    @JsonProperty(value = "relationGuid")
    private String relationGuid;

    /*
     * The status of term relationship.
     */
    @Generated
    @JsonProperty(value = "status")
    private AtlasTermRelationshipStatus status;

    /**
     * Creates an instance of AtlasTermCategorizationHeader class.
     */
    @Generated
    public AtlasTermCategorizationHeader() {
    }

    /**
     * Get the categoryGuid property: The GUID of the category.
     * 
     * @return the categoryGuid value.
     */
    @Generated
    public String getCategoryGuid() {
        return this.categoryGuid;
    }

    /**
     * Set the categoryGuid property: The GUID of the category.
     * 
     * @param categoryGuid the categoryGuid value to set.
     * @return the AtlasTermCategorizationHeader object itself.
     */
    @Generated
    public AtlasTermCategorizationHeader setCategoryGuid(String categoryGuid) {
        this.categoryGuid = categoryGuid;
        return this;
    }

    /**
     * Get the description property: The description of the record.
     * 
     * @return the description value.
     */
    @Generated
    public String getDescription() {
        return this.description;
    }

    /**
     * Set the description property: The description of the record.
     * 
     * @param description the description value to set.
     * @return the AtlasTermCategorizationHeader object itself.
     */
    @Generated
    public AtlasTermCategorizationHeader setDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * Get the displayText property: The display text.
     * 
     * @return the displayText value.
     */
    @Generated
    public String getDisplayText() {
        return this.displayText;
    }

    /**
     * Set the displayText property: The display text.
     * 
     * @param displayText the displayText value to set.
     * @return the AtlasTermCategorizationHeader object itself.
     */
    @Generated
    public AtlasTermCategorizationHeader setDisplayText(String displayText) {
        this.displayText = displayText;
        return this;
    }

    /**
     * Get the relationGuid property: The GUID of the relationship.
     * 
     * @return the relationGuid value.
     */
    @Generated
    public String getRelationGuid() {
        return this.relationGuid;
    }

    /**
     * Set the relationGuid property: The GUID of the relationship.
     * 
     * @param relationGuid the relationGuid value to set.
     * @return the AtlasTermCategorizationHeader object itself.
     */
    @Generated
    public AtlasTermCategorizationHeader setRelationGuid(String relationGuid) {
        this.relationGuid = relationGuid;
        return this;
    }

    /**
     * Get the status property: The status of term relationship.
     * 
     * @return the status value.
     */
    @Generated
    public AtlasTermRelationshipStatus getStatus() {
        return this.status;
    }

    /**
     * Set the status property: The status of term relationship.
     * 
     * @param status the status value to set.
     * @return the AtlasTermCategorizationHeader object itself.
     */
    @Generated
    public AtlasTermCategorizationHeader setStatus(AtlasTermRelationshipStatus status) {
        this.status = status;
        return this;
    }
}
