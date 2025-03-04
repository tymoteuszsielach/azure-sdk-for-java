// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.resourcemanager.migrationdiscoverysap.models;

import com.azure.core.annotation.Immutable;
import com.azure.resourcemanager.migrationdiscoverysap.fluent.models.ErrorDefinition;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * An error response from the SAP migrate resources.
 */
@Immutable
public final class SapMigrateError {
    /*
     * The SAP Discovery site resource error body.
     */
    @JsonProperty(value = "properties")
    private ErrorDefinition innerProperties;

    /**
     * Creates an instance of SapMigrateError class.
     */
    public SapMigrateError() {
    }

    /**
     * Get the innerProperties property: The SAP Discovery site resource error body.
     * 
     * @return the innerProperties value.
     */
    private ErrorDefinition innerProperties() {
        return this.innerProperties;
    }

    /**
     * Get the code property: Service specific error code which serves as the substatus for the HTTP error code.
     * 
     * @return the code value.
     */
    public String code() {
        return this.innerProperties() == null ? null : this.innerProperties().code();
    }

    /**
     * Get the message property: Description of the error.
     * 
     * @return the message value.
     */
    public String message() {
        return this.innerProperties() == null ? null : this.innerProperties().message();
    }

    /**
     * Get the recommendation property: Description of the recommendation.
     * 
     * @return the recommendation value.
     */
    public String recommendation() {
        return this.innerProperties() == null ? null : this.innerProperties().recommendation();
    }

    /**
     * Get the details property: Internal error details.
     * 
     * @return the details value.
     */
    public List<ErrorDefinition> details() {
        return this.innerProperties() == null ? null : this.innerProperties().details();
    }

    /**
     * Validates the instance.
     * 
     * @throws IllegalArgumentException thrown if the instance is not valid.
     */
    public void validate() {
        if (innerProperties() != null) {
            innerProperties().validate();
        }
    }
}
