// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.resourcemanager.migrationdiscoverysap.fluent.models;

import com.azure.core.annotation.Fluent;
import com.azure.core.management.Resource;
import com.azure.core.management.SystemData;
import com.azure.resourcemanager.migrationdiscoverysap.models.ProvisioningState;
import com.azure.resourcemanager.migrationdiscoverysap.models.SapInstanceEnvironment;
import com.azure.resourcemanager.migrationdiscoverysap.models.SapMigrateError;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

/**
 * Define the SAP Instance resource.
 */
@Fluent
public final class SapInstanceInner extends Resource {
    /*
     * The resource-specific properties for this resource.
     */
    @JsonProperty(value = "properties")
    private SapInstanceProperties innerProperties;

    /*
     * Azure Resource Manager metadata containing createdBy and modifiedBy information.
     */
    @JsonProperty(value = "systemData", access = JsonProperty.Access.WRITE_ONLY)
    private SystemData systemData;

    /**
     * Creates an instance of SapInstanceInner class.
     */
    public SapInstanceInner() {
    }

    /**
     * Get the innerProperties property: The resource-specific properties for this resource.
     * 
     * @return the innerProperties value.
     */
    private SapInstanceProperties innerProperties() {
        return this.innerProperties;
    }

    /**
     * Get the systemData property: Azure Resource Manager metadata containing createdBy and modifiedBy information.
     * 
     * @return the systemData value.
     */
    public SystemData systemData() {
        return this.systemData;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SapInstanceInner withLocation(String location) {
        super.withLocation(location);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SapInstanceInner withTags(Map<String, String> tags) {
        super.withTags(tags);
        return this;
    }

    /**
     * Get the systemSid property: This is the SID of SAP System. Keeping this not equal to ID as different landscapes
     * can have repeated System SID IDs.
     * 
     * @return the systemSid value.
     */
    public String systemSid() {
        return this.innerProperties() == null ? null : this.innerProperties().systemSid();
    }

    /**
     * Get the environment property: The Environment; PRD, QA, DEV, etc to which SAP system belongs to. Select from the
     * list of available dropdown values.
     * 
     * @return the environment value.
     */
    public SapInstanceEnvironment environment() {
        return this.innerProperties() == null ? null : this.innerProperties().environment();
    }

    /**
     * Get the landscapeSid property: This is the SID of the production system in a landscape. An SAP system could
     * itself be a production SID or a part of a landscape with a different Production SID. This field can be used to
     * relate non-prod SIDs, other components, SID (WEBDISP) to the prod SID. Enter the value of Production SID.
     * 
     * @return the landscapeSid value.
     */
    public String landscapeSid() {
        return this.innerProperties() == null ? null : this.innerProperties().landscapeSid();
    }

    /**
     * Get the application property: Enter a business function/department identifier to group multiple SIDs.
     * 
     * @return the application value.
     */
    public String application() {
        return this.innerProperties() == null ? null : this.innerProperties().application();
    }

    /**
     * Get the provisioningState property: Defines the provisioning states.
     * 
     * @return the provisioningState value.
     */
    public ProvisioningState provisioningState() {
        return this.innerProperties() == null ? null : this.innerProperties().provisioningState();
    }

    /**
     * Get the errors property: Defines the errors related to SAP Instance resource.
     * 
     * @return the errors value.
     */
    public SapMigrateError errors() {
        return this.innerProperties() == null ? null : this.innerProperties().errors();
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
