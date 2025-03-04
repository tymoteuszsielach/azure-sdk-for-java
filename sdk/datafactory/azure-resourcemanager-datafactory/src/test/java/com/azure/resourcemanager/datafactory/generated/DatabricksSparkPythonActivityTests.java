// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.resourcemanager.datafactory.generated;

import com.azure.core.util.BinaryData;
import com.azure.resourcemanager.datafactory.models.ActivityDependency;
import com.azure.resourcemanager.datafactory.models.ActivityOnInactiveMarkAs;
import com.azure.resourcemanager.datafactory.models.ActivityPolicy;
import com.azure.resourcemanager.datafactory.models.ActivityState;
import com.azure.resourcemanager.datafactory.models.DatabricksSparkPythonActivity;
import com.azure.resourcemanager.datafactory.models.DependencyCondition;
import com.azure.resourcemanager.datafactory.models.LinkedServiceReference;
import com.azure.resourcemanager.datafactory.models.UserProperty;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;

public final class DatabricksSparkPythonActivityTests {
    @org.junit.jupiter.api.Test
    public void testDeserialize() throws Exception {
        DatabricksSparkPythonActivity model = BinaryData.fromString(
            "{\"type\":\"DatabricksSparkPython\",\"typeProperties\":{\"pythonFile\":\"datarmgsnffcoa\",\"parameters\":[\"datapa\",\"datazith\",\"datajrtcdavlrifmtk\",\"datawezwkparj\"],\"libraries\":[{\"zexxzkcigykea\":\"datasvj\",\"shdubqhafxl\":\"datarumhzgdsjblag\",\"wbormfnntpocf\":\"dataomwnkhiwqiqx\",\"dohytkhq\":\"datavmzs\"},{\"ehqmt\":\"datadyz\"}]},\"linkedServiceName\":{\"referenceName\":\"wpeaivbzrms\",\"parameters\":{\"fqameccuqkoat\":\"datadwjimrzavci\",\"mdiecrbcvhkkdsyx\":\"datai\",\"zwlbccxj\":\"databddjbzohvpqtxluq\"}},\"policy\":{\"timeout\":\"dataoihjnknfvpa\",\"retry\":\"dataaeeiboqc\",\"retryIntervalInSeconds\":1524667796,\"secureInput\":true,\"secureOutput\":false,\"\":{\"osedxsphfj\":\"datafijmi\",\"relggg\":\"dataxeswz\",\"emtweh\":\"datatsjeolxbggoj\",\"rkrfabffeahypjqa\":\"datauttngatglarczzgu\"}},\"name\":\"ceujuclffpvdjfw\",\"description\":\"bplbtmwae\",\"state\":\"Active\",\"onInactiveMarkAs\":\"Skipped\",\"dependsOn\":[{\"activity\":\"cxh\",\"dependencyConditions\":[\"Succeeded\",\"Completed\",\"Completed\"],\"\":{\"btymhh\":\"datad\",\"pu\":\"datauwcmwixyrv\",\"eihmv\":\"databuvviysg\"}},{\"activity\":\"upqfawwoxqjhm\",\"dependencyConditions\":[\"Skipped\",\"Succeeded\",\"Succeeded\",\"Skipped\"],\"\":{\"gfaotokipndekpz\":\"datavsnyn\",\"rotnvxyeqdin\":\"datadrkddzkki\"}}],\"userProperties\":[{\"name\":\"ejtqoxethrxlpgrv\",\"value\":\"datazjxmnsrejqwy\"}],\"\":{\"kdaomxyx\":\"datasmhoviear\"}}")
            .toObject(DatabricksSparkPythonActivity.class);
        Assertions.assertEquals("ceujuclffpvdjfw", model.name());
        Assertions.assertEquals("bplbtmwae", model.description());
        Assertions.assertEquals(ActivityState.ACTIVE, model.state());
        Assertions.assertEquals(ActivityOnInactiveMarkAs.SKIPPED, model.onInactiveMarkAs());
        Assertions.assertEquals("cxh", model.dependsOn().get(0).activity());
        Assertions.assertEquals(DependencyCondition.SUCCEEDED, model.dependsOn().get(0).dependencyConditions().get(0));
        Assertions.assertEquals("ejtqoxethrxlpgrv", model.userProperties().get(0).name());
        Assertions.assertEquals("wpeaivbzrms", model.linkedServiceName().referenceName());
        Assertions.assertEquals(1524667796, model.policy().retryIntervalInSeconds());
        Assertions.assertEquals(true, model.policy().secureInput());
        Assertions.assertEquals(false, model.policy().secureOutput());
    }

    @org.junit.jupiter.api.Test
    public void testSerialize() throws Exception {
        DatabricksSparkPythonActivity model = new DatabricksSparkPythonActivity().withName("ceujuclffpvdjfw")
            .withDescription("bplbtmwae").withState(ActivityState.ACTIVE)
            .withOnInactiveMarkAs(ActivityOnInactiveMarkAs.SKIPPED)
            .withDependsOn(Arrays.asList(
                new ActivityDependency().withActivity("cxh")
                    .withDependencyConditions(Arrays.asList(DependencyCondition.SUCCEEDED,
                        DependencyCondition.COMPLETED, DependencyCondition.COMPLETED))
                    .withAdditionalProperties(mapOf()),
                new ActivityDependency().withActivity("upqfawwoxqjhm")
                    .withDependencyConditions(Arrays.asList(DependencyCondition.SKIPPED, DependencyCondition.SUCCEEDED,
                        DependencyCondition.SUCCEEDED, DependencyCondition.SKIPPED))
                    .withAdditionalProperties(mapOf())))
            .withUserProperties(
                Arrays.asList(new UserProperty().withName("ejtqoxethrxlpgrv").withValue("datazjxmnsrejqwy")))
            .withLinkedServiceName(new LinkedServiceReference().withReferenceName("wpeaivbzrms")
                .withParameters(mapOf("fqameccuqkoat", "datadwjimrzavci", "mdiecrbcvhkkdsyx", "datai", "zwlbccxj",
                    "databddjbzohvpqtxluq")))
            .withPolicy(new ActivityPolicy().withTimeout("dataoihjnknfvpa").withRetry("dataaeeiboqc")
                .withRetryIntervalInSeconds(1524667796).withSecureInput(true).withSecureOutput(false)
                .withAdditionalProperties(mapOf()))
            .withPythonFile("datarmgsnffcoa")
            .withParameters(Arrays.asList("datapa", "datazith", "datajrtcdavlrifmtk", "datawezwkparj"))
            .withLibraries(Arrays.asList(mapOf("zexxzkcigykea", "datasvj", "shdubqhafxl", "datarumhzgdsjblag",
                "wbormfnntpocf", "dataomwnkhiwqiqx", "dohytkhq", "datavmzs"), mapOf("ehqmt", "datadyz")));
        model = BinaryData.fromObject(model).toObject(DatabricksSparkPythonActivity.class);
        Assertions.assertEquals("ceujuclffpvdjfw", model.name());
        Assertions.assertEquals("bplbtmwae", model.description());
        Assertions.assertEquals(ActivityState.ACTIVE, model.state());
        Assertions.assertEquals(ActivityOnInactiveMarkAs.SKIPPED, model.onInactiveMarkAs());
        Assertions.assertEquals("cxh", model.dependsOn().get(0).activity());
        Assertions.assertEquals(DependencyCondition.SUCCEEDED, model.dependsOn().get(0).dependencyConditions().get(0));
        Assertions.assertEquals("ejtqoxethrxlpgrv", model.userProperties().get(0).name());
        Assertions.assertEquals("wpeaivbzrms", model.linkedServiceName().referenceName());
        Assertions.assertEquals(1524667796, model.policy().retryIntervalInSeconds());
        Assertions.assertEquals(true, model.policy().secureInput());
        Assertions.assertEquals(false, model.policy().secureOutput());
    }

    // Use "Map.of" if available
    @SuppressWarnings("unchecked")
    private static <T> Map<String, T> mapOf(Object... inputs) {
        Map<String, T> map = new HashMap<>();
        for (int i = 0; i < inputs.length; i += 2) {
            String key = (String) inputs[i];
            T value = (T) inputs[i + 1];
            map.put(key, value);
        }
        return map;
    }
}
