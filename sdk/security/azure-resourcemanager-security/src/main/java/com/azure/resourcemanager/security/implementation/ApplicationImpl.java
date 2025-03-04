// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.resourcemanager.security.implementation;

import com.azure.core.util.Context;
import com.azure.resourcemanager.security.fluent.models.ApplicationInner;
import com.azure.resourcemanager.security.models.Application;
import com.azure.resourcemanager.security.models.ApplicationSourceResourceType;
import java.util.Collections;
import java.util.List;

public final class ApplicationImpl implements Application, Application.Definition, Application.Update {
    private ApplicationInner innerObject;

    private final com.azure.resourcemanager.security.SecurityManager serviceManager;

    public String id() {
        return this.innerModel().id();
    }

    public String name() {
        return this.innerModel().name();
    }

    public String type() {
        return this.innerModel().type();
    }

    public String displayName() {
        return this.innerModel().displayName();
    }

    public String description() {
        return this.innerModel().description();
    }

    public ApplicationSourceResourceType sourceResourceType() {
        return this.innerModel().sourceResourceType();
    }

    public List<Object> conditionSets() {
        List<Object> inner = this.innerModel().conditionSets();
        if (inner != null) {
            return Collections.unmodifiableList(inner);
        } else {
            return Collections.emptyList();
        }
    }

    public ApplicationInner innerModel() {
        return this.innerObject;
    }

    private com.azure.resourcemanager.security.SecurityManager manager() {
        return this.serviceManager;
    }

    private String applicationId;

    public Application create() {
        this.innerObject = serviceManager.serviceClient().getApplicationOperations()
            .createOrUpdateWithResponse(applicationId, this.innerModel(), Context.NONE).getValue();
        return this;
    }

    public Application create(Context context) {
        this.innerObject = serviceManager.serviceClient().getApplicationOperations()
            .createOrUpdateWithResponse(applicationId, this.innerModel(), context).getValue();
        return this;
    }

    ApplicationImpl(String name, com.azure.resourcemanager.security.SecurityManager serviceManager) {
        this.innerObject = new ApplicationInner();
        this.serviceManager = serviceManager;
        this.applicationId = name;
    }

    public ApplicationImpl update() {
        return this;
    }

    public Application apply() {
        this.innerObject = serviceManager.serviceClient().getApplicationOperations()
            .createOrUpdateWithResponse(applicationId, this.innerModel(), Context.NONE).getValue();
        return this;
    }

    public Application apply(Context context) {
        this.innerObject = serviceManager.serviceClient().getApplicationOperations()
            .createOrUpdateWithResponse(applicationId, this.innerModel(), context).getValue();
        return this;
    }

    ApplicationImpl(ApplicationInner innerObject, com.azure.resourcemanager.security.SecurityManager serviceManager) {
        this.innerObject = innerObject;
        this.serviceManager = serviceManager;
        this.applicationId = ResourceManagerUtils.getValueFromIdByName(innerObject.id(), "applications");
    }

    public Application refresh() {
        this.innerObject = serviceManager.serviceClient().getApplicationOperations()
            .getWithResponse(applicationId, Context.NONE).getValue();
        return this;
    }

    public Application refresh(Context context) {
        this.innerObject = serviceManager.serviceClient().getApplicationOperations()
            .getWithResponse(applicationId, context).getValue();
        return this;
    }

    public ApplicationImpl withDisplayName(String displayName) {
        this.innerModel().withDisplayName(displayName);
        return this;
    }

    public ApplicationImpl withDescription(String description) {
        this.innerModel().withDescription(description);
        return this;
    }

    public ApplicationImpl withSourceResourceType(ApplicationSourceResourceType sourceResourceType) {
        this.innerModel().withSourceResourceType(sourceResourceType);
        return this;
    }

    public ApplicationImpl withConditionSets(List<Object> conditionSets) {
        this.innerModel().withConditionSets(conditionSets);
        return this;
    }
}
