// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) TypeSpec Code Generator.
package com.azure.ai.translation.text.models;

import com.azure.core.annotation.Generated;
import com.azure.core.annotation.Immutable;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Properties of the target dictionary language.
 */
@Immutable
public final class TargetDictionaryLanguage {

    /*
     * Display name of the language in the locale requested via Accept-Language header.
     */
    @Generated
    @JsonProperty(value = "name")
    private String name;

    /*
     * Display name of the language in the locale native for this language.
     */
    @Generated
    @JsonProperty(value = "nativeName")
    private String nativeName;

    /*
     * Directionality, which is rtl for right-to-left languages or ltr for left-to-right languages.
     */
    @Generated
    @JsonProperty(value = "dir")
    private String dir;

    /*
     * Language code identifying the target language.
     */
    @Generated
    @JsonProperty(value = "code")
    private String code;

    /**
     * Creates an instance of TargetDictionaryLanguage class.
     *
     * @param name the name value to set.
     * @param nativeName the nativeName value to set.
     * @param dir the dir value to set.
     * @param code the code value to set.
     */
    @Generated
    @JsonCreator
    private TargetDictionaryLanguage(@JsonProperty(value = "name") String name,
        @JsonProperty(value = "nativeName") String nativeName, @JsonProperty(value = "dir") String dir,
        @JsonProperty(value = "code") String code) {
        this.name = name;
        this.nativeName = nativeName;
        this.dir = dir;
        this.code = code;
    }

    /**
     * Get the name property: Display name of the language in the locale requested via Accept-Language header.
     *
     * @return the name value.
     */
    @Generated
    public String getName() {
        return this.name;
    }

    /**
     * Get the nativeName property: Display name of the language in the locale native for this language.
     *
     * @return the nativeName value.
     */
    @Generated
    public String getNativeName() {
        return this.nativeName;
    }

    /**
     * Get the dir property: Directionality, which is rtl for right-to-left languages or ltr for left-to-right
     * languages.
     *
     * @return the dir value.
     */
    @Generated
    public String getDir() {
        return this.dir;
    }

    /**
     * Get the code property: Language code identifying the target language.
     *
     * @return the code value.
     */
    @Generated
    public String getCode() {
        return this.code;
    }
}
