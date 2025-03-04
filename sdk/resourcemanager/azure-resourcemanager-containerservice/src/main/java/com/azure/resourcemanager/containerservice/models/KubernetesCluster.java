// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
package com.azure.resourcemanager.containerservice.models;

import com.azure.core.annotation.Fluent;
import com.azure.resourcemanager.containerservice.ContainerServiceManager;
import com.azure.resourcemanager.containerservice.fluent.models.ManagedClusterInner;
import com.azure.core.management.Region;
import com.azure.resourcemanager.resources.fluentcore.arm.models.GroupableResource;
import com.azure.resourcemanager.resources.fluentcore.arm.models.Resource;
import com.azure.resourcemanager.resources.fluentcore.collection.SupportsListingPrivateEndpointConnection;
import com.azure.resourcemanager.resources.fluentcore.collection.SupportsListingPrivateLinkResource;
import com.azure.resourcemanager.resources.fluentcore.model.Accepted;
import com.azure.resourcemanager.resources.fluentcore.model.Appliable;
import com.azure.resourcemanager.resources.fluentcore.model.Attachable;
import com.azure.resourcemanager.resources.fluentcore.model.Creatable;
import com.azure.resourcemanager.resources.fluentcore.model.Refreshable;
import com.azure.resourcemanager.resources.fluentcore.model.Updatable;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

/** A client-side representation for a managed Kubernetes cluster. */
@Fluent
public interface KubernetesCluster
    extends GroupableResource<ContainerServiceManager, ManagedClusterInner>,
    Refreshable<KubernetesCluster>,
    Updatable<KubernetesCluster.Update>,
    SupportsListingPrivateLinkResource,
    SupportsListingPrivateEndpointConnection {

    /** @return the provisioning state of the Kubernetes cluster */
    String provisioningState();

    /** @return the DNS prefix which was specified at creation time */
    String dnsPrefix();

    /** @return the FQDN for the master pool */
    String fqdn();

    /** @return the Kubernetes version */
    String version();

    /** @return the Kubernetes configuration file content with administrative privileges to the cluster */
    byte[] adminKubeConfigContent();

    /** @return the Kubernetes configuration file content with user-level privileges to the cluster */
    byte[] userKubeConfigContent();

    /**
     * @param format Only apply to AAD clusters, specifies the format of returned kubeconfig. Format 'azure' will return azure auth-provider kubeconfig; format 'exec' will return exec format kubeconfig, which requires kubelogin binary in the path.
     * @return the Kubernetes configuration file content with user-level privileges to the cluster
     */
    byte[] userKubeConfigContent(Format format);

    /** @return the Kubernetes credentials with administrative privileges to the cluster */
    List<CredentialResult> adminKubeConfigs();

    /** @return the Kubernetes credentials with user-level privileges to the cluster */
    List<CredentialResult> userKubeConfigs();

    /**
     * @param format Only apply to AAD clusters, specifies the format of returned kubeconfig. Format 'azure' will return azure auth-provider kubeconfig; format 'exec' will return exec format kubeconfig, which requires kubelogin binary in the path.
     * @return the Kubernetes credentials with user-level privileges to the cluster
     */
    List<CredentialResult> userKubeConfigs(Format format);

    /** @return the service principal client ID */
    String servicePrincipalClientId();

    /** @return the service principal secret */
    String servicePrincipalSecret();

    /** @return the Linux root username */
    String linuxRootUsername();

    /** @return the Linux SSH key */
    String sshKey();

    /** @return the agent pools in the Kubernetes cluster */
    Map<String, KubernetesClusterAgentPool> agentPools();

    /** @return the network profile settings for the cluster */
    ContainerServiceNetworkProfile networkProfile();

    /** @return the cluster's add-on's profiles */
    Map<String, ManagedClusterAddonProfile> addonProfiles();

    /** @return the name of the resource group containing agent pool nodes */
    String nodeResourceGroup();

    /** @return true if Kubernetes Role-Based Access Control is enabled */
    boolean enableRBAC();

    /** @return the power state */
    PowerState powerState();

    /** @return the SKU of a Managed Cluster */
    ManagedClusterSku sku();

    /**
     * @return the System Assigned Managed Service Identity specific Active Directory service principal ID
     *     assigned to the Kubernetes cluster.
     */
    String systemAssignedManagedServiceIdentityPrincipalId();

    /** @return the IDs (object IDs) of the Azure AD groups as the admin group of the cluster. */
    List<String> azureActiveDirectoryGroupIds();

    /** @return whether local accounts is enabled. */
    boolean isLocalAccountsEnabled();

    /** @return whether Azure Role-Based Access Control for Kubernetes authorization is enabled. */
    boolean isAzureRbacEnabled();

    /** @return resource ID of the disk encryption set. */
    String diskEncryptionSetId();

    /**
     * Gets the resource group containing agent pool nodes.
     *
     * @return The resource group containing agent pool nodes.
     */
    String agentPoolResourceGroup();

    // Actions

    /**
     * Starts a stopped Kubernetes cluster.
     */
    void start();

    /**
     * Starts a stopped Kubernetes cluster.
     *
     * @return the completion.
     */
    Mono<Void> startAsync();

    /**
     * Stops a running Kubernetes cluster.
     */
    void stop();

    /**
     * Stops a running Kubernetes cluster.
     *
     * @return the completion.
     */
    Mono<Void> stopAsync();

    /**
     * Begins creating the agent pool resource.
     *
     * @param agentPoolName the name of the agent pool.
     * @param agentPool the agent pool.
     * @return the accepted create operation
     */
    Accepted<AgentPool> beginCreateAgentPool(String agentPoolName, AgentPoolData agentPool);

    // Fluent interfaces

    /** Interface for all the definitions related to a Kubernetes cluster. */
    interface Definition
        extends KubernetesCluster.DefinitionStages.Blank,
            KubernetesCluster.DefinitionStages.WithGroup,
            KubernetesCluster.DefinitionStages.WithVersion,
            DefinitionStages.WithLinuxRootUsername,
            DefinitionStages.WithLinuxSshKey,
            DefinitionStages.WithServicePrincipalClientId,
            DefinitionStages.WithServicePrincipalProfile,
            DefinitionStages.WithDnsPrefix,
            DefinitionStages.WithAgentPool,
            DefinitionStages.WithNetworkProfile,
            DefinitionStages.WithAddOnProfiles,
            DefinitionStages.WithManagedClusterSku,
            KubernetesCluster.DefinitionStages.WithCreate {
    }

    /** Grouping of Kubernetes cluster definition stages. */
    interface DefinitionStages {
        /**
         * The first stage of a container service definition.
         */
        interface Blank extends DefinitionWithRegion<WithGroup> {
        }

        /**
         * The stage of the Kubernetes cluster definition allowing to specify the resource group.
         */
        interface WithGroup extends GroupableResource.DefinitionStages.WithGroup<WithVersion> {
        }

        /**
         * The stage of the Kubernetes cluster definition allowing to specify the managed cluster SKU.
         * LongTermSupport and Premium tier should be enabled/disabled together.
         * For more information, please see https://learn.microsoft.com/azure/aks/long-term-support
         */
        interface WithManagedClusterSku {

            /**
             * Specifies the managed cluster SKU is free.
             *
             * @return the next stage of the definition
             */
            WithCreate withFreeSku();

            /**
             * Specifies the managed cluster SKU is standard.
             *
             * @return the next stage of the definition
             */
            WithCreate withStandardSku();

            /**
             * Specifies the managed cluster SKU is premium.
             *
             * @return the next stage of the definition
             */
            WithCreate withPremiumSku();
        }

        /**
         * The stage of the Kubernetes cluster definition allowing to specify orchestration type.
         */
        interface WithVersion {
            /**
             * Specifies the version for the Kubernetes cluster.
             * Could retrieve from {@link KubernetesClusters#listOrchestrators(Region, ContainerServiceResourceTypes)}
             *
             * @param kubernetesVersion the kubernetes version
             * @return the next stage of the definition
             */
            WithLinuxRootUsername withVersion(String kubernetesVersion);

            /**
             * Uses the default version for the Kubernetes cluster.
             *
             * @return the next stage of the definition
             */
            WithLinuxRootUsername withDefaultVersion();
        }

        /**
         * The stage of the Kubernetes cluster definition allowing to specific the Linux root username.
         */
        interface WithLinuxRootUsername extends WithServicePrincipalClientId {
            /**
             * Begins the definition to specify Linux root username.
             *
             * @param rootUserName the root username
             * @return the next stage of the definition
             */
            WithLinuxSshKey withRootUsername(String rootUserName);
        }

        /**
         * The stage of the Kubernetes cluster definition allowing to specific the Linux SSH key.
         */
        interface WithLinuxSshKey {
            /**
             * Begins the definition to specify Linux ssh key.
             *
             * @param sshKeyData the SSH key data
             * @return the next stage of the definition
             */
            WithServicePrincipalClientId withSshKey(String sshKeyData);
        }

        /**
         * The stage of the Kubernetes cluster definition allowing to specify the service principal client ID.
         */
        interface WithServicePrincipalClientId extends WithManagedServiceIdentity {
            /**
             * Properties for Kubernetes cluster service principal.
             *
             * @param clientId the ID for the service principal
             * @return the next stage
             */
            WithServicePrincipalProfile withServicePrincipalClientId(String clientId);
        }

        /**
         * The stage of the Kubernetes cluster definition allowing to specify the service principal secret.
         */
        interface WithServicePrincipalProfile {
            /**
             * Properties for service principal.
             *
             * @param secret the secret password associated with the service principal
             * @return the next stage
             */
            WithAgentPool withServicePrincipalSecret(String secret);
        }

        /**
         * The stage of the Kubernetes cluster definition allowing to specify an agent pool profile.
         */
        interface WithAgentPool {
            /**
             * Begins the definition of an agent pool profile to be attached to the Kubernetes cluster.
             *
             * @param name the name for the agent pool profile
             * @return the stage representing configuration for the agent pool profile
             */
            KubernetesClusterAgentPool.DefinitionStages.Blank<? extends WithCreate> defineAgentPool(String name);
        }

        /**
         * The stage of the Kubernetes cluster definition allowing to specify a network profile.
         */
        interface WithNetworkProfile {
            /**
             * Begins the definition of a network profile to be attached to the Kubernetes cluster.
             *
             * @return the stage representing configuration for the network profile
             */
            NetworkProfileDefinitionStages.Blank<KubernetesCluster.DefinitionStages.WithCreate> defineNetworkProfile();
        }

        /** The Kubernetes cluster definition allowing to specify a network profile. */
        interface NetworkProfileDefinitionStages {
            /**
             * The first stage of a network profile definition.
             *
             * @param <ParentT> the stage of the Kubernetes cluster network profile definition to return to after
             *     attaching this definition
             */
            interface Blank<ParentT> extends WithAttach<ParentT> {
                /**
                 * Specifies the network plugin type to be used for building the Kubernetes network.
                 *
                 * @param networkPlugin the network plugin type to be used for building the Kubernetes network
                 * @return the next stage of the definition
                 */
                WithAttach<ParentT> withNetworkPlugin(NetworkPlugin networkPlugin);
            }

            /**
             * The stage of a network profile definition allowing to specify the network policy.
             *
             * @param <ParentT> the stage of the network profile definition to return to after attaching this definition
             */
            interface WithNetworkPolicy<ParentT> {
                /**
                 * Specifies the network policy to be used for building the Kubernetes network.
                 *
                 * @param networkPolicy the network policy to be used for building the Kubernetes network
                 * @return the next stage of the definition
                 */
                WithAttach<ParentT> withNetworkPolicy(NetworkPolicy networkPolicy);
            }

            /**
             * The stage of a network profile definition allowing to specify a CIDR notation IP range from which to
             * assign pod IPs when kubenet is used.
             *
             * @param <ParentT> the stage of the network profile definition to return to after attaching this definition
             */
            interface WithPodCidr<ParentT> {
                /**
                 * Specifies a CIDR notation IP range from which to assign pod IPs when kubenet is used.
                 *
                 * @param podCidr the CIDR notation IP range from which to assign pod IPs when kubenet is used
                 * @return the next stage of the definition
                 */
                WithAttach<ParentT> withPodCidr(String podCidr);
            }

            /**
             * The stage of a network profile definition allowing to specify a CIDR notation IP range from which to
             * assign service cluster IPs.
             *
             * @param <ParentT> the stage of the network profile definition to return to after attaching this definition
             */
            interface WithServiceCidr<ParentT> {
                /**
                 * Specifies a CIDR notation IP range from which to assign service cluster IPs; must not overlap with
                 * any subnet IP ranges.
                 *
                 * @param serviceCidr the CIDR notation IP range from which to assign service cluster IPs; it must not
                 *     overlap with any Subnet IP ranges
                 * @return the next stage of the definition
                 */
                WithAttach<ParentT> withServiceCidr(String serviceCidr);
            }

            /**
             * The stage of a network profile definition allowing to specify an IP address assigned to the Kubernetes
             * DNS service.
             *
             * @param <ParentT> the stage of the network profile definition to return to after attaching this definition
             */
            interface WithDnsServiceIP<ParentT> {
                /**
                 * Specifies an IP address assigned to the Kubernetes DNS service; it must be within the Kubernetes
                 * service address range specified in the service CIDR.
                 *
                 * @param dnsServiceIP the IP address assigned to the Kubernetes DNS service; it must be within the
                 *     Kubernetes service address range specified in the service CIDR
                 * @return the next stage of the definition
                 */
                WithAttach<ParentT> withDnsServiceIP(String dnsServiceIP);
            }

            /**
             * The stage of a network profile definition allowing to specify a CIDR notation IP range assigned to the
             * Docker bridge network.
             *
             * @param <ParentT> the stage of the network profile definition to return to after attaching this definition
             */
            interface WithDockerBridgeCidr<ParentT> {
                /**
                 * Specifies a CIDR notation IP range assigned to the Docker bridge network; it must not overlap with
                 * any subnet IP ranges or the Kubernetes service address range.
                 *
                 * @param dockerBridgeCidr the CIDR notation IP range assigned to the Docker bridge network; it must not
                 *     overlap with any subnet IP ranges or the Kubernetes service address range
                 * @return the next stage of the definition
                 * @deprecated The property has no effect since 2019
                 */
                @Deprecated
                WithAttach<ParentT> withDockerBridgeCidr(String dockerBridgeCidr);
            }

            /**
             * The stage of a network profile definition allowing to specify load balancer profile.
             *
             * @param <ParentT> the stage of the network profile definition to return to after attaching this definition
             */
            interface WithLoadBalancerProfile<ParentT> {
                /**
                 * Specifies the load balancer SKU.
                 *
                 * @param loadBalancerSku the load balancer SKU.
                 * @return the next stage of the definition
                 */
                WithAttach<ParentT> withLoadBalancerSku(LoadBalancerSku loadBalancerSku);
            }

            /**
             * The final stage of a network profile definition. At this stage, any remaining optional settings can be
             * specified, or the container service agent pool can be attached to the parent container service
             * definition.
             *
             * @param <ParentT> the stage of the container service definition to return to after attaching this
             *     definition
             */
            interface WithAttach<ParentT>
                extends NetworkProfileDefinitionStages.WithNetworkPolicy<ParentT>,
                    NetworkProfileDefinitionStages.WithPodCidr<ParentT>,
                    NetworkProfileDefinitionStages.WithServiceCidr<ParentT>,
                    NetworkProfileDefinitionStages.WithDnsServiceIP<ParentT>,
                    NetworkProfileDefinitionStages.WithDockerBridgeCidr<ParentT>,
                    NetworkProfileDefinitionStages.WithLoadBalancerProfile<ParentT>,
                    Attachable.InDefinition<ParentT> {
            }
        }

        /**
         * The Kubernetes cluster network profile definition. The entirety of a Kubernetes cluster network profile
         * definition as a part of a parent definition.
         *
         * @param <ParentT> the stage of the container service definition to return to after attaching this definition
         */
        interface NetworkProfileDefinition<ParentT>
            extends NetworkProfileDefinitionStages.Blank<ParentT>,
                NetworkProfileDefinitionStages.WithNetworkPolicy<ParentT>,
                NetworkProfileDefinitionStages.WithPodCidr<ParentT>,
                NetworkProfileDefinitionStages.WithServiceCidr<ParentT>,
                NetworkProfileDefinitionStages.WithDnsServiceIP<ParentT>,
                NetworkProfileDefinitionStages.WithDockerBridgeCidr<ParentT>,
                NetworkProfileDefinitionStages.WithAttach<ParentT> {
        }

        /** The stage of the Kubernetes cluster definition allowing to specify the DNS prefix label. */
        interface WithDnsPrefix {
            /**
             * Specifies the DNS prefix to be used to create the FQDN for the master pool.
             *
             * @param dnsPrefix the DNS prefix to be used to create the FQDN for the master pool
             * @return the next stage of the definition
             */
            KubernetesCluster.DefinitionStages.WithCreate withDnsPrefix(String dnsPrefix);
        }

        /** The stage of the Kubernetes cluster definition allowing to specify the cluster's add-on's profiles. */
        interface WithAddOnProfiles {
            /**
             * Specifies the cluster's add-on's profiles.
             *
             * @param addOnProfileMap the cluster's add-on's profiles
             * @return the next stage of the definition
             */
            WithCreate withAddOnProfiles(Map<String, ManagedClusterAddonProfile> addOnProfileMap);
        }

        /** The stage of the Kubernetes cluster definition allowing to specify the cluster's access profiles. */
        interface WithAccessProfiles {
            /**
             * Enables private cluster.
             *
             * @return the next stage of the definition
             */
            WithCreate enablePrivateCluster();
        }

        /** The stage of the Kubernetes cluster definition allowing to specify the auto-scale profile. */
        interface WithAutoScalerProfile {
            /**
             * Specifies the auto-scale profile.
             *
             * @param autoScalerProfile the auto-scale profile
             * @return the next stage
             */
            WithCreate withAutoScalerProfile(ManagedClusterPropertiesAutoScalerProfile autoScalerProfile);
        }

        /** The stage of the Kubernetes cluster definition allowing to specify the auto-scale profile. */
        interface WithManagedServiceIdentity {
            /**
             * Specifies that System Assigned Managed Service Identity needs to be enabled in the cluster.
             *
             * @return the next stage
             */
            WithCreate withSystemAssignedManagedServiceIdentity();
        }

        /** The stage of the Kubernetes cluster definition allowing to specify Kubernetes Role-Based Access Control. */
        interface WithRBAC {
            /**
             * Disables Kubernetes Role-Based Access Control.
             *
             * @return the next stage
             */
            WithCreate disableKubernetesRbac();
        }

        /** The stage of the Kubernetes cluster definition allowing to specify Azure AD integration. */
        interface WithAAD {
            /**
             * Specified Azure AD group as the admin group of the cluster.
             * <p>
             * Azure AD integration cannot be disabled. It will enable Kubernetes Role-Based Access Control.
             *
             * @param activeDirectoryGroupObjectId the object ID of the Azure AD group.
             * <p>
             * See {@code com.azure.resourcemanager.authorization.models.ActiveDirectoryGroup} in
             * azure-resourcemanager-authorization package.
             * @return the next stage
             */
            WithCreate withAzureActiveDirectoryGroup(String activeDirectoryGroupObjectId);

            /**
             * Enables Azure Role-Based Access Control for Kubernetes authorization.
             * <p>
             * Azure AD integration cannot be disabled. It will enable Kubernetes Role-Based Access Control.
             *
             * @return the next stage
             */
            WithCreate enableAzureRbac();
        }

        /** The stage of the Kubernetes cluster definition allowing to specify local accounts. */
        interface WithLocalAccounts {
            /**
             * Disables authentication from local accounts.
             *
             * @return the next stage
             */
            WithCreate disableLocalAccounts();
        }

        /** The stage of the Kubernetes cluster definition allowing to specify disk encryption. */
        interface WithDiskEncryption {
            /**
             * Specifies the disk encryption set for the disk in cluster.
             *
             * @param diskEncryptionSetId the ID of disk encryption set.
             * @return the next stage of the definition
             */
            WithCreate withDiskEncryptionSet(String diskEncryptionSetId);;
        }

        /**
         * The stage of the Kubernetes cluster definition allowing to specify the resource group for agent pool nodes.
         */
        interface WithAgentPoolResourceGroup {
            /**
             * Specifies the resource group for agent pool nodes.
             *
             * @param resourceGroupName the resource group for agent pool nodes
             * @return the next stage of the definition
             */
            WithCreate withAgentPoolResourceGroup(String resourceGroupName);
        }

        /**
         * The stage of the definition which contains all the minimum required inputs for the resource to be created,
         * but also allows for any other optional settings to be specified.
         */
        interface WithCreate
            extends Creatable<KubernetesCluster>,
                WithAgentPool,
                WithNetworkProfile,
                WithDnsPrefix,
                WithAddOnProfiles,
                WithAccessProfiles,
                WithAutoScalerProfile,
                WithManagedServiceIdentity,
                WithRBAC,
                WithAAD,
                WithLocalAccounts,
                WithDiskEncryption,
                WithAgentPoolResourceGroup,
                WithManagedClusterSku,
                Resource.DefinitionWithTags<WithCreate> {
        }
    }

    /** The template for an update operation, containing all the settings that can be modified. */
    interface Update
        extends UpdateStages.WithAgentPool,
            UpdateStages.WithAddOnProfiles,
            UpdateStages.WithNetworkProfile,
            UpdateStages.WithRBAC,
            UpdateStages.WithAutoScalerProfile,
            UpdateStages.WithAAD,
            UpdateStages.WithLocalAccounts,
            UpdateStages.WithVersion,
            UpdateStages.WithManagedClusterSku,
            Resource.UpdateWithTags<KubernetesCluster.Update>,
            Appliable<KubernetesCluster> {
    }

    /** Grouping of the Kubernetes cluster update stages. */
    interface UpdateStages {
        /**
         * The stage of the Kubernetes cluster update definition allowing to specify the agent poll in the cluster.
         */
        interface WithAgentPool {
            /**
             * Begins the definition of an agent pool profile to be attached to the Kubernetes cluster.
             *
             * @param name the name for the agent pool profile
             * @return the stage representing configuration for the agent pool profile
             */
            KubernetesClusterAgentPool.DefinitionStages.Blank<? extends Update> defineAgentPool(String name);

            /**
             * Begins the definition of an agent pool profile to be attached to the Kubernetes cluster.
             *
             * @param name the name for the agent pool profile
             * @return the stage representing configuration for the agent pool profile
             */
            KubernetesClusterAgentPool.Update<? extends Update> updateAgentPool(String name);

            /**
             * Removes an agent pool profile from the Kubernetes cluster.
             *
             * @param name the name for the agent pool profile
             * @return the next stage of the update
             */
            Update withoutAgentPool(String name);
        }

        /**
         * The stage of the Kubernetes cluster update definition allowing to specify the cluster's add-on's profiles.
         */
        interface WithAddOnProfiles {
            /**
             * Updates the cluster's add-on's profiles.
             *
             * @param addOnProfileMap the cluster's add-on's profiles
             * @return the next stage of the update
             */
            Update withAddOnProfiles(Map<String, ManagedClusterAddonProfile> addOnProfileMap);
        }

        /** The stage of the Kubernetes cluster update definition allowing to specify the cluster's network profile. */
        interface WithNetworkProfile {
            /**
             * Updates the cluster's network profile.
             *
             * @param networkProfile the cluster's networkProfile
             * @return the next stage of the update
             */
            Update withNetworkProfile(ContainerServiceNetworkProfile networkProfile);
        }

        /**
         * The stage of the Kubernetes cluster update definition allowing to specify if Kubernetes Role-Based Access
         * Control is enabled or disabled.
         */
        interface WithRBAC {
            /**
             * Updates the cluster to specify the Kubernetes Role-Based Access Control is enabled.
             *
             * @return the next stage of the update
             * @deprecated Change Kubernetes RBAC is not allowed.
             */
            @Deprecated
            Update withRBACEnabled();

            /**
             * Updates the cluster to specify the Kubernetes Role-Based Access Control is disabled.
             *
             * @return the next stage of the update
             * @deprecated Change Kubernetes RBAC is not allowed.
             */
            @Deprecated
            Update withRBACDisabled();
        }

        /** The stage of the Kubernetes cluster update allowing to specify the auto-scale profile. */
        interface WithAutoScalerProfile {
            /**
             * Specifies the auto-scale profile.
             *
             * @param autoScalerProfile the auto-scale profile
             * @return the next stage
             */
            Update withAutoScalerProfile(ManagedClusterPropertiesAutoScalerProfile autoScalerProfile);
        }

        /** The stage of the Kubernetes cluster update allowing to specify Azure AD integration. */
        interface WithAAD {
            /**
             * Specified Azure AD group as the admin group of the cluster.
             *
             * Azure AD integration cannot be disabled. It will enable Kubernetes Role-Based Access Control.
             *
             * @param activeDirectoryGroupObjectId the object ID of the Azure AD group.
             * <p>
             * See {@code com.azure.resourcemanager.authorization.models.ActiveDirectoryGroup} in
             * azure-resourcemanager-authorization package.
             * @return the next stage
             */
            Update withAzureActiveDirectoryGroup(String activeDirectoryGroupObjectId);

            /**
             * Enables Azure Role-Based Access Control for Kubernetes authorization.
             *
             * @return the next stage
             */
            Update enableAzureRbac();
        }

        /** The stage of the Kubernetes cluster update allowing to specify local accounts. */
        interface WithLocalAccounts {
            /**
             * Enables authentication from local accounts.
             *
             * @return the next stage
             */
            Update enableLocalAccounts();

            /**
             * Disables authentication from local accounts.
             *
             * @return the next stage
             */
            Update disableLocalAccounts();
        }

        /**
         * The stage of the Kubernetes cluster definition allowing to specify the managed cluster SKU.
         * LongTermSupport and Premium tier should be enabled/disabled together.
         * For more information, please see https://learn.microsoft.com/azure/aks/long-term-support
         */
        interface WithManagedClusterSku {

            /**
             * Specifies the managed cluster SKU is free.
             *
             * @return the next stage
             */
            Update withFreeSku();

            /**
             * Specifies the managed cluster SKU is standard.
             *
             * @return the next stage
             */
            Update withStandardSku();

            /**
             * Specifies the managed cluster SKU is premium.
             *
             * @return the next stage
             */
            Update withPremiumSku();
        }

        /**
         * The stage of the Kubernetes cluster update allowing to specify orchestration type.
         */
        interface WithVersion {
            /**
             * Specifies the version for the Kubernetes cluster.
             * Could retrieve from {@link KubernetesClusters#listOrchestrators(Region, ContainerServiceResourceTypes)}
             * To prevent version conflicts, specify a higher version than the initial version.
             *
             * @param kubernetesVersion the kubernetes version
             * @return the next stage
             */
            Update withVersion(String kubernetesVersion);
        }
    }
}
