// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
package com.azure.ai.openai;

import com.azure.ai.openai.functions.MyFunctionCallArguments;
import com.azure.ai.openai.models.AudioTaskLabel;
import com.azure.ai.openai.models.AudioTranscription;
import com.azure.ai.openai.models.AudioTranscriptionFormat;
import com.azure.ai.openai.models.AudioTranslation;
import com.azure.ai.openai.models.AudioTranslationFormat;
import com.azure.ai.openai.models.AzureSearchChatExtensionConfiguration;
import com.azure.ai.openai.models.AzureSearchChatExtensionParameters;
import com.azure.ai.openai.models.ChatChoice;
import com.azure.ai.openai.models.ChatCompletions;
import com.azure.ai.openai.models.ChatCompletionsFunctionToolCall;
import com.azure.ai.openai.models.ChatCompletionsOptions;
import com.azure.ai.openai.models.ChatCompletionsToolCall;
import com.azure.ai.openai.models.ChatResponseMessage;
import com.azure.ai.openai.models.ChatRole;
import com.azure.ai.openai.models.Choice;
import com.azure.ai.openai.models.Completions;
import com.azure.ai.openai.models.CompletionsFinishReason;
import com.azure.ai.openai.models.CompletionsOptions;
import com.azure.ai.openai.models.CompletionsUsage;
import com.azure.ai.openai.models.Embeddings;
import com.azure.ai.openai.models.FunctionCall;
import com.azure.ai.openai.models.FunctionCallConfig;
import com.azure.ai.openai.models.OnYourDataApiKeyAuthenticationOptions;
import com.azure.core.exception.HttpResponseException;
import com.azure.core.exception.ResourceNotFoundException;
import com.azure.core.http.HttpClient;
import com.azure.core.http.rest.RequestOptions;
import com.azure.core.http.rest.Response;
import com.azure.core.test.annotation.RecordWithoutRequestBody;
import com.azure.core.util.BinaryData;
import com.azure.core.util.CoreUtils;
import com.azure.core.util.IterableStream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static com.azure.ai.openai.TestUtils.DISPLAY_NAME_WITH_ARGUMENTS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OpenAISyncClientTest extends OpenAIClientTestBase {
    private OpenAIClient client;

    private OpenAIClient getOpenAIClient(HttpClient httpClient, OpenAIServiceVersion serviceVersion) {
        return getOpenAIClientBuilder(
            interceptorManager.isPlaybackMode() ? interceptorManager.getPlaybackClient() : httpClient, serviceVersion)
            .buildClient();
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    public void testGetCompletions(HttpClient httpClient, OpenAIServiceVersion serviceVersion) {
        client = getOpenAIClient(httpClient, serviceVersion);
        getCompletionsRunner((deploymentId, prompt) -> {
            Completions resultCompletions = client.getCompletions(deploymentId, new CompletionsOptions(prompt));
            assertCompletions(1, resultCompletions);
        });
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    public void testGetCompletionsStream(HttpClient httpClient, OpenAIServiceVersion serviceVersion) {
        client = getOpenAIClient(httpClient, serviceVersion);
        getCompletionsRunner((deploymentId, prompt) -> {
            IterableStream<Completions> resultCompletions = client.getCompletionsStream(deploymentId, new CompletionsOptions(prompt));
            assertTrue(resultCompletions.stream().toArray().length > 1);
            resultCompletions.forEach(OpenAIClientTestBase::assertCompletionsStream);
        });
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    public void testGetCompletionsFromPrompt(HttpClient httpClient, OpenAIServiceVersion serviceVersion) {
        client = getOpenAIClient(httpClient, serviceVersion);
        getCompletionsFromSinglePromptRunner((deploymentId, prompts) -> {
            Completions completions = client.getCompletions(deploymentId, prompts);
            assertCompletions(1, completions);
        });
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    public void testGetCompletionsWithResponse(HttpClient httpClient, OpenAIServiceVersion serviceVersion) {
        client = getOpenAIClient(httpClient, serviceVersion);
        getCompletionsRunner((deploymentId, prompt) -> {
            Response<BinaryData> response = client.getCompletionsWithResponse(deploymentId,
                BinaryData.fromObject(new CompletionsOptions(prompt)), new RequestOptions());
            Completions resultCompletions = assertAndGetValueFromResponse(response, Completions.class, 200);
            assertCompletions(1, resultCompletions);
        });
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    public void testGetCompletionsWithResponseBadDeployment(HttpClient httpClient, OpenAIServiceVersion serviceVersion) {
        client = getOpenAIClient(httpClient, serviceVersion);
        getCompletionsRunner((_deploymentId, prompt) -> {
            String deploymentId = "BAD_DEPLOYMENT_ID";
            ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> client.getCompletionsWithResponse(deploymentId,
                    BinaryData.fromObject(new CompletionsOptions(prompt)), new RequestOptions()));
            assertEquals(404, exception.getResponse().getStatusCode());
        });
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    public void testGetCompletionsUsageField(HttpClient httpClient, OpenAIServiceVersion serviceVersion) {
        client = getOpenAIClient(httpClient, serviceVersion);
        getCompletionsRunner((modelId, prompt) -> {
            CompletionsOptions completionsOptions = new CompletionsOptions(prompt);
            completionsOptions.setMaxTokens(1024);
            completionsOptions.setN(3);
            completionsOptions.setLogprobs(1);

            Completions resultCompletions = client.getCompletions(modelId, completionsOptions);

            CompletionsUsage usage = resultCompletions.getUsage();
            assertCompletions(completionsOptions.getN() * completionsOptions.getPrompt().size(), resultCompletions);
            assertNotNull(usage);
            assertTrue(usage.getTotalTokens() > 0);
            assertEquals(usage.getCompletionTokens() + usage.getPromptTokens(), usage.getTotalTokens());
        });
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    public void testGetCompletionsTokenCutoff(HttpClient httpClient, OpenAIServiceVersion serviceVersion) {
        client = getOpenAIClient(httpClient, serviceVersion);
        getCompletionsRunner((modelId, prompt) -> {
            CompletionsOptions completionsOptions = new CompletionsOptions(prompt);
            completionsOptions.setMaxTokens(3);
            Completions resultCompletions = client.getCompletions(modelId, completionsOptions);
            assertCompletions(1, resultCompletions);
        });
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    public void testGetChatCompletions(HttpClient httpClient, OpenAIServiceVersion serviceVersion) {
        client = getOpenAIClient(httpClient, serviceVersion);
        getChatCompletionsRunner((deploymentId, chatMessages) -> {
            ChatCompletions resultChatCompletions = client.getChatCompletions(deploymentId, new ChatCompletionsOptions(chatMessages));
            assertChatCompletions(1, resultChatCompletions);
        });
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    public void testGetChatCompletionsStream(HttpClient httpClient, OpenAIServiceVersion serviceVersion) {
        client = getOpenAIClient(httpClient, serviceVersion);
        getChatCompletionsRunner((deploymentId, chatMessages) -> {
            IterableStream<ChatCompletions> resultChatCompletions = client.getChatCompletionsStream(deploymentId, new ChatCompletionsOptions(chatMessages));
            assertTrue(resultChatCompletions.stream().toArray().length > 1);
            resultChatCompletions.forEach(OpenAIClientTestBase::assertChatCompletionsStream);
        });
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    public void testGetChatCompletionsStreamWithResponse(HttpClient httpClient, OpenAIServiceVersion serviceVersion) {
        client = getOpenAIClient(httpClient, serviceVersion);
        getChatCompletionsWithResponseRunner(deploymentId -> chatMessages -> requestOptions -> {
            Response<IterableStream<ChatCompletions>> response = client.getChatCompletionsStreamWithResponse(
                    deploymentId, new ChatCompletionsOptions(chatMessages), requestOptions);
            assertResponseRequestHeader(response.getRequest());
            IterableStream<ChatCompletions> value = response.getValue();
            assertTrue(value.stream().toArray().length > 1);
            value.forEach(OpenAIClientTestBase::assertChatCompletionsStream);
        });
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    public void testGetChatCompletionsWithResponse(HttpClient httpClient, OpenAIServiceVersion serviceVersion) {
        client = getOpenAIClient(httpClient, serviceVersion);
        getChatCompletionsRunner((deploymentId, chatMessages) -> {
            Response<BinaryData> response = client.getChatCompletionsWithResponse(deploymentId,
                BinaryData.fromObject(new ChatCompletionsOptions(chatMessages)), new RequestOptions());
            ChatCompletions resultChatCompletions = assertAndGetValueFromResponse(response, ChatCompletions.class, 200);
            assertChatCompletions(1, resultChatCompletions);
        });
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    public void testGetEmbeddings(HttpClient httpClient, OpenAIServiceVersion serviceVersion) {
        client = getOpenAIClient(httpClient, serviceVersion);
        getEmbeddingRunner((deploymentId, embeddingsOptions) -> {
            Embeddings resultEmbeddings = client.getEmbeddings(deploymentId, embeddingsOptions);
            assertEmbeddings(resultEmbeddings);
        });
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    public void testGetEmbeddingsWithResponse(HttpClient httpClient, OpenAIServiceVersion serviceVersion) {
        client = getOpenAIClient(httpClient, serviceVersion);
        getEmbeddingRunner((deploymentId, embeddingsOptions) -> {
            Response<BinaryData> response = client.getEmbeddingsWithResponse(deploymentId,
                BinaryData.fromObject(embeddingsOptions), new RequestOptions());
            Embeddings resultEmbeddings = assertAndGetValueFromResponse(response, Embeddings.class, 200);
            assertEmbeddings(resultEmbeddings);
        });
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    public void testGenerateImage(HttpClient httpClient, OpenAIServiceVersion serviceVersion) {
        client = getOpenAIClient(httpClient, serviceVersion);
        getImageGenerationRunner((deploymentId, options) ->
                assertImageGenerationsForAzure(client.getImageGenerations(deploymentId, options)));
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    public void testContentFilterInputExceptionInImageGeneration(HttpClient httpClient, OpenAIServiceVersion serviceVersion) {
        client = getOpenAIClient(httpClient, serviceVersion);
        contentFilterInputExceptionRunner((deploymentId, options) -> {
            HttpResponseException httpResponseException = assertThrows(HttpResponseException.class,
                    () -> client.getImageGenerations(deploymentId, options));
            validateImageGenerationContentFilteringException(httpResponseException);
        });
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    public void testChatFunctionAutoPreset(HttpClient httpClient, OpenAIServiceVersion serviceVersion) {
        client = getOpenAIClient(httpClient, serviceVersion);
        getChatFunctionForRunner((modelId, chatCompletionsOptions) -> {
            chatCompletionsOptions.setFunctionCall(FunctionCallConfig.AUTO);
            ChatCompletions chatCompletions = client.getChatCompletions(modelId, chatCompletionsOptions);

            assertEquals(1, chatCompletions.getChoices().size());
            ChatChoice chatChoice = chatCompletions.getChoices().get(0);
            MyFunctionCallArguments arguments = assertFunctionCall(
                chatChoice,
                "MyFunction",
                MyFunctionCallArguments.class);
            assertEquals(arguments.getLocation(), "San Francisco, CA");
            assertEquals(arguments.getUnit(), "CELSIUS");
        });
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    public void testChatFunctionNonePreset(HttpClient httpClient, OpenAIServiceVersion serviceVersion) {
        client = getOpenAIClient(httpClient, serviceVersion);
        getChatFunctionForRunner((modelId, chatCompletionsOptions) -> {
            chatCompletionsOptions.setFunctionCall(FunctionCallConfig.NONE);
            ChatCompletions chatCompletions = client.getChatCompletions(modelId, chatCompletionsOptions);

            assertChatCompletions(1, "stop", ChatRole.ASSISTANT, chatCompletions);
        });
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    public void testChatFunctionNotSuppliedByNamePreset(HttpClient httpClient, OpenAIServiceVersion serviceVersion) {
        client = getOpenAIClient(httpClient, serviceVersion);
        getChatFunctionForRunner((modelId, chatCompletionsOptions) -> {
            chatCompletionsOptions.setFunctionCall(new FunctionCallConfig("NotMyFunction"));
            HttpResponseException exception = assertThrows(HttpResponseException.class,
                () -> client.getChatCompletions(modelId, chatCompletionsOptions));
            assertEquals(400, exception.getResponse().getStatusCode());

            assertInstanceOf(HttpResponseException.class, exception);
            assertTrue(exception.getMessage().contains("Invalid value for 'function_call'"));
        });
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    public void testChatCompletionContentFiltering(HttpClient httpClient, OpenAIServiceVersion serviceVersion) {
        client = getOpenAIClient(httpClient, serviceVersion);
        getChatCompletionsContentFilterRunner((modelId, chatMessages) -> {
            ChatCompletions chatCompletions = client.getChatCompletions(modelId, new ChatCompletionsOptions(chatMessages));
            assertSafePromptContentFilterResults(chatCompletions.getPromptFilterResults().get(0));
            assertEquals(1, chatCompletions.getChoices().size());
            assertSafeChoiceContentFilterResults(chatCompletions.getChoices().get(0).getContentFilterResults());

        });
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    public void testChatCompletionStreamContentFiltering(HttpClient httpClient, OpenAIServiceVersion serviceVersion) {
        client = getOpenAIClient(httpClient, serviceVersion);
        getChatCompletionsContentFilterRunner((modelId, chatMessages) -> {
            IterableStream<ChatCompletions> messageList = client.getChatCompletionsStream(modelId, new ChatCompletionsOptions(chatMessages));

            int i = 0;
            int totalMessages = messageList.stream().toArray().length;
            for (Iterator<ChatCompletions> it = messageList.iterator(); it.hasNext();) {
                ChatCompletions chatCompletions = it.next();
                assertChatCompletionsStream(chatCompletions);
                if (i == 0) {
                    // The first stream message has the prompt filter result
                    assertEquals(1, chatCompletions.getPromptFilterResults().size());
                    assertSafePromptContentFilterResults(chatCompletions.getPromptFilterResults().get(0));
                } else if (i == 1) {
                    // The second message no longer has the prompt filter result, but contains a ChatChoice
                    // filter result with all the filter set to null. The roll is also ASSISTANT
                    assertEquals(ChatRole.ASSISTANT, chatCompletions.getChoices().get(0).getDelta().getRole());
                    assertNull(chatCompletions.getPromptFilterResults());
                    // TODO (team): change in behaviour, this used to be uncommented
//                    assertSafeChoiceContentFilterResults(chatCompletions.getChoices().get(0).getContentFilterResults());
                } else if (i == totalMessages - 1) {
                    // The last stream message is empty with all the filters set to null
                    assertEquals(1, chatCompletions.getChoices().size());
                    ChatChoice chatChoice = chatCompletions.getChoices().get(0);

                    assertEquals(CompletionsFinishReason.fromString("stop"), chatChoice.getFinishReason());
                    assertNotNull(chatChoice.getDelta());
                    assertNull(chatChoice.getDelta().getContent());
                    // TODO (team): change in behaviour, this used to be uncommented
//                    assertSafeChoiceContentFilterResults(chatChoice.getContentFilterResults());
                } else {
                    // The rest of the intermediary messages have the text generation content filter set
                    assertNull(chatCompletions.getPromptFilterResults());
                    assertNotNull(chatCompletions.getChoices().get(0).getDelta());
                    assertSafeChoiceContentFilterResults(chatCompletions.getChoices().get(0).getContentFilterResults());
                }
                i++;
            }

        });
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    public void testCompletionContentFiltering(HttpClient httpClient, OpenAIServiceVersion serviceVersion) {
        client = getOpenAIClient(httpClient, serviceVersion);
        getCompletionsContentFilterRunner((modelId, prompt) -> {
            CompletionsOptions completionsOptions = new CompletionsOptions(Arrays.asList(prompt));
            // work around for this model, there seem to be some issues with Completions in gpt-turbo models
            completionsOptions.setMaxTokens(2000);
            Completions completions = client.getCompletions(modelId, completionsOptions);
            assertCompletions(1, completions);
            assertSafePromptContentFilterResults(completions.getPromptFilterResults().get(0));
            assertSafeChoiceContentFilterResults(completions.getChoices().get(0).getContentFilterResults());
        });
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    public void testCompletionStreamContentFiltering(HttpClient httpClient, OpenAIServiceVersion serviceVersion) {
        client = getOpenAIClient(httpClient, serviceVersion);
        getCompletionsContentFilterRunner((deploymentId, prompt) -> {
            IterableStream<Completions> resultCompletions = client.getCompletionsStream(deploymentId,
                new CompletionsOptions(Arrays.asList(prompt)).setMaxTokens(2000));
            assertTrue(resultCompletions.stream().toArray().length > 1);
            int i = 0;
            int totalCompletions = resultCompletions.stream().toArray().length;
            for (Iterator<Completions> it = resultCompletions.iterator(); it.hasNext();) {
                Completions completions = it.next();
                assertCompletionsStream(completions);
                if (i == 0) {
                    // The first stream message has the prompt filter result
                    assertEquals(1, completions.getPromptFilterResults().size());
                    assertSafePromptContentFilterResults(completions.getPromptFilterResults().get(0));
                } else if (i == totalCompletions - 1) {
                    // The last stream message is empty with all the filters set to null
                    assertEquals(1, completions.getChoices().size());
                    Choice choice = completions.getChoices().get(0);
                    assertEquals(CompletionsFinishReason.fromString("stop"), choice.getFinishReason());
                    assertNotNull(choice.getText());
                    // TODO (team): change in behaviour, this used to be uncommented
//                    assertSafeChoiceContentFilterResults(choice.getContentFilterResults());
                } else {
                    // The rest of the intermediary messages have the text generation content filter set
                    assertNull(completions.getPromptFilterResults());
                    assertNotNull(completions.getChoices().get(0));
                    assertSafeChoiceContentFilterResults(completions.getChoices().get(0).getContentFilterResults());
                }
                i++;
            }
        });
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    public void testChatCompletionsBasicSearchExtension(HttpClient httpClient, OpenAIServiceVersion serviceVersion) {
        client = getOpenAIClient(httpClient, serviceVersion);

        getChatCompletionsAzureChatSearchRunner((deploymentName, chatCompletionsOptions) -> {
            AzureSearchChatExtensionParameters searchParameters = new AzureSearchChatExtensionParameters(
                    "https://openaisdktestsearch.search.windows.net",
                    "openai-test-index-carbon-wiki"
            );
            searchParameters.setAuthentication(new OnYourDataApiKeyAuthenticationOptions(getAzureCognitiveSearchKey()));
            AzureSearchChatExtensionConfiguration cognitiveSearchConfiguration =
                new AzureSearchChatExtensionConfiguration(searchParameters);

            chatCompletionsOptions.setDataSources(Arrays.asList(cognitiveSearchConfiguration));
            ChatCompletions chatCompletions = client.getChatCompletions(deploymentName, chatCompletionsOptions);

            assertChatCompletionsCognitiveSearch(chatCompletions);
        });
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    public void testChatCompletionsStreamingBasicSearchExtension(HttpClient httpClient, OpenAIServiceVersion serviceVersion) {
        client = getOpenAIClient(httpClient, serviceVersion);

        getChatCompletionsAzureChatSearchRunner((deploymentName, chatCompletionsOptions) -> {
            AzureSearchChatExtensionParameters searchParameters = new AzureSearchChatExtensionParameters(
                    "https://openaisdktestsearch.search.windows.net",
                    "openai-test-index-carbon-wiki"
            );
            searchParameters.setAuthentication(new OnYourDataApiKeyAuthenticationOptions(getAzureCognitiveSearchKey()));
            AzureSearchChatExtensionConfiguration cognitiveSearchConfiguration =
                    new AzureSearchChatExtensionConfiguration(searchParameters);

            chatCompletionsOptions.setDataSources(Arrays.asList(cognitiveSearchConfiguration));
            IterableStream<ChatCompletions> resultChatCompletions = client.getChatCompletionsStream(deploymentName, chatCompletionsOptions);

            assertChatCompletionsStreamingCognitiveSearch(resultChatCompletions.stream());
        });
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    @RecordWithoutRequestBody
    public void testGetAudioTranscriptionJson(HttpClient httpClient, OpenAIServiceVersion serviceVersion) {
        client = getOpenAIClient(httpClient, serviceVersion);

        getAudioTranscriptionRunner((deploymentName, transcriptionOptions) -> {
            transcriptionOptions.setResponseFormat(AudioTranscriptionFormat.JSON);

            AudioTranscription transcription = client.getAudioTranscription(deploymentName, transcriptionOptions.getFilename(), transcriptionOptions);
            assertAudioTranscriptionSimpleJson(transcription, BATMAN_TRANSCRIPTION);
        });
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    @RecordWithoutRequestBody
    public void testGetAudioTranscriptionVerboseJson(HttpClient httpClient, OpenAIServiceVersion serviceVersion) {
        client = getOpenAIClient(httpClient, serviceVersion);

        getAudioTranscriptionRunner((deploymentName, transcriptionOptions) -> {
            transcriptionOptions.setResponseFormat(AudioTranscriptionFormat.VERBOSE_JSON);

            AudioTranscription transcription = client.getAudioTranscription(deploymentName, transcriptionOptions.getFilename(), transcriptionOptions);
            assertAudioTranscriptionVerboseJson(transcription, BATMAN_TRANSCRIPTION, AudioTaskLabel.TRANSCRIBE);
        });
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    @RecordWithoutRequestBody
    public void testGetAudioTranscriptionTextPlain(HttpClient httpClient, OpenAIServiceVersion serviceVersion) {
        client = getOpenAIClient(httpClient, serviceVersion);

        getAudioTranscriptionRunner((deploymentName, transcriptionOptions) -> {
            transcriptionOptions.setResponseFormat(AudioTranscriptionFormat.TEXT);

            String transcription = client.getAudioTranscriptionText(deploymentName, transcriptionOptions.getFilename(), transcriptionOptions);
            // A plain/text request adds a line break as an artifact. Also observed for translations
            assertEquals(BATMAN_TRANSCRIPTION + "\n", transcription);
        });
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    @RecordWithoutRequestBody
    public void testGetAudioTranscriptionSrt(HttpClient httpClient, OpenAIServiceVersion serviceVersion) {
        client = getOpenAIClient(httpClient, serviceVersion);

        getAudioTranscriptionRunner((deploymentName, transcriptionOptions) -> {
            transcriptionOptions.setResponseFormat(AudioTranscriptionFormat.SRT);

            String transcription = client.getAudioTranscriptionText(deploymentName, transcriptionOptions.getFilename(), transcriptionOptions);
            // Contains at least one sequence
            assertTrue(transcription.contains("1\n"));
            // First sequence starts at timestamp 0
            assertTrue(transcription.contains("00:00:00,000 --> "));
            // Contains at least one expected word
            assertTrue(transcription.contains("Batman"));
        });
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    @RecordWithoutRequestBody
    public void testGetAudioTranscriptionVtt(HttpClient httpClient, OpenAIServiceVersion serviceVersion) {
        client = getOpenAIClient(httpClient, serviceVersion);

        getAudioTranscriptionRunner((deploymentName, transcriptionOptions) -> {
            transcriptionOptions.setResponseFormat(AudioTranscriptionFormat.VTT);

            String transcription = client.getAudioTranscriptionText(deploymentName, transcriptionOptions.getFilename(), transcriptionOptions);
            // Start value according to spec
            assertTrue(transcription.startsWith("WEBVTT\n"));
            // First sequence starts at timestamp 0. Note: unlike SRT, the millisecond separator is a "."
            assertTrue(transcription.contains("00:00:00.000 --> "));
            // Contains at least one expected word in the transcription
            assertTrue(transcription.contains("Batman"));
        });
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    public void testGetAudioTranscriptionTextWrongFormats(HttpClient httpClient, OpenAIServiceVersion serviceVersion) {
        client = getOpenAIClient(httpClient, serviceVersion);
        List<AudioTranscriptionFormat> wrongFormats = Arrays.asList(
                AudioTranscriptionFormat.JSON,
                AudioTranscriptionFormat.VERBOSE_JSON
        );

        getAudioTranscriptionRunner((deploymentName, transcriptionOptions) -> {
            for (AudioTranscriptionFormat format: wrongFormats) {
                transcriptionOptions.setResponseFormat(format);
                assertThrows(IllegalArgumentException.class, () ->
                        client.getAudioTranscriptionText(deploymentName, transcriptionOptions.getFilename(), transcriptionOptions));
            }
        });
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    public void testGetAudioTranscriptionJsonWrongFormats(HttpClient httpClient, OpenAIServiceVersion serviceVersion) {
        client = getOpenAIClient(httpClient, serviceVersion);
        List<AudioTranscriptionFormat> wrongFormats = Arrays.asList(
                AudioTranscriptionFormat.TEXT,
                AudioTranscriptionFormat.SRT,
                AudioTranscriptionFormat.VTT
        );

        getAudioTranscriptionRunner((deploymentName, transcriptionOptions) -> {
            for (AudioTranscriptionFormat format: wrongFormats) {
                transcriptionOptions.setResponseFormat(format);
                assertThrows(IllegalArgumentException.class, () ->
                        client.getAudioTranscription(deploymentName, transcriptionOptions.getFilename(), transcriptionOptions));
            }
        });
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    @RecordWithoutRequestBody
    public void testGetAudioTranslationJson(HttpClient httpClient, OpenAIServiceVersion serviceVersion) {
        client = getOpenAIClient(httpClient, serviceVersion);

        getAudioTranslationRunner((deploymentName, translationOptions) -> {
            translationOptions.setResponseFormat(AudioTranslationFormat.JSON);

            AudioTranslation translation = client.getAudioTranslation(deploymentName, translationOptions.getFilename(), translationOptions);
            assertAudioTranslationSimpleJson(translation, "It's raining today.");
        });
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    @RecordWithoutRequestBody
    public void testGetAudioTranslationVerboseJson(HttpClient httpClient, OpenAIServiceVersion serviceVersion) {
        client = getOpenAIClient(httpClient, serviceVersion);

        getAudioTranslationRunner((deploymentName, translationOptions) -> {
            translationOptions.setResponseFormat(AudioTranslationFormat.VERBOSE_JSON);

            AudioTranslation translation = client.getAudioTranslation(deploymentName, translationOptions.getFilename(), translationOptions);
            assertAudioTranslationVerboseJson(translation, "It's raining today.", AudioTaskLabel.TRANSLATE);
        });
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    @RecordWithoutRequestBody
    public void testGetAudioTranslationTextPlain(HttpClient httpClient, OpenAIServiceVersion serviceVersion) {
        client = getOpenAIClient(httpClient, serviceVersion);

        getAudioTranslationRunner((deploymentName, translationOptions) -> {
            translationOptions.setResponseFormat(AudioTranslationFormat.TEXT);

            String transcription = client.getAudioTranslationText(deploymentName, translationOptions.getFilename(), translationOptions);
            assertEquals("It's raining today.\n", transcription);
        });
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    @RecordWithoutRequestBody
    public void testGetAudioTranslationSrt(HttpClient httpClient, OpenAIServiceVersion serviceVersion) {
        client = getOpenAIClient(httpClient, serviceVersion);

        getAudioTranslationRunner((deploymentName, translationOptions) -> {
            translationOptions.setResponseFormat(AudioTranslationFormat.SRT);

            String transcription = client.getAudioTranslationText(deploymentName, translationOptions.getFilename(), translationOptions);
            // Sequence number
            assertTrue(transcription.contains("1\n"));
            // First sequence starts at timestamp 0
            assertTrue(transcription.contains("00:00:00,000 --> "));
            // Actual translation value
            assertTrue(transcription.contains("It's raining today."));
        });
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    @RecordWithoutRequestBody
    public void testGetAudioTranslationVtt(HttpClient httpClient, OpenAIServiceVersion serviceVersion) {
        client = getOpenAIClient(httpClient, serviceVersion);

        getAudioTranslationRunner((deploymentName, translationOptions) -> {
            translationOptions.setResponseFormat(AudioTranslationFormat.VTT);

            String transcription = client.getAudioTranslationText(deploymentName, translationOptions.getFilename(), translationOptions);
            // Start value according to spec
            assertTrue(transcription.startsWith("WEBVTT\n"));
            // First sequence starts at timestamp 0. Note: unlike SRT, the millisecond separator is a "."
            assertTrue(transcription.contains("00:00:00.000 --> "));
            // Actual translation value
            assertTrue(transcription.contains("It's raining today."));
        });
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    public void testGetAudioTranslationTextWrongFormats(HttpClient httpClient, OpenAIServiceVersion serviceVersion) {
        client = getOpenAIClient(httpClient, serviceVersion);
        List<AudioTranslationFormat> wrongFormats = Arrays.asList(
            AudioTranslationFormat.JSON,
            AudioTranslationFormat.VERBOSE_JSON
        );

        getAudioTranslationRunner((deploymentName, translationOptions) -> {
            for (AudioTranslationFormat format: wrongFormats) {
                translationOptions.setResponseFormat(format);
                assertThrows(IllegalArgumentException.class, () -> {
                    client.getAudioTranslationText(deploymentName, translationOptions.getFilename(), translationOptions);
                });
            }
        });
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    public void testGetAudioTranslationJsonWrongFormats(HttpClient httpClient, OpenAIServiceVersion serviceVersion) {
        client = getOpenAIClient(httpClient, serviceVersion);
        List<AudioTranslationFormat> wrongFormats = Arrays.asList(
            AudioTranslationFormat.TEXT,
            AudioTranslationFormat.SRT,
            AudioTranslationFormat.VTT
        );

        getAudioTranslationRunner((deploymentName, translationOptions) -> {
            for (AudioTranslationFormat format: wrongFormats) {
                translationOptions.setResponseFormat(format);
                assertThrows(IllegalArgumentException.class, () -> {
                    client.getAudioTranslation(deploymentName, translationOptions.getFilename(), translationOptions);
                });
            }
        });
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    public void testGetChatCompletionsToolCall(HttpClient httpClient, OpenAIServiceVersion serviceVersion) {
        client = getOpenAIClient(httpClient, serviceVersion);
        getChatWithToolCallRunner(((modelId, chatCompletionsOptions) -> {
            Response<ChatCompletions> response = client.getChatCompletionsWithResponse(modelId, chatCompletionsOptions, new RequestOptions());

            // first round trip
            assertNotNull(response);
            assertTrue(response.getStatusCode() >= 200 && response.getStatusCode() < 300);
            ChatCompletions chatCompletions = response.getValue();
            assertNotNull(chatCompletions);

            assertTrue(chatCompletions.getChoices() != null && !chatCompletions.getChoices().isEmpty());
            ChatChoice chatChoice = chatCompletions.getChoices().get(0);
            assertEquals(chatChoice.getFinishReason(), CompletionsFinishReason.TOOL_CALLS);

            ChatResponseMessage responseMessage = chatChoice.getMessage();
            assertNotNull(responseMessage);
            assertTrue(responseMessage.getContent() == null || responseMessage.getContent().isEmpty());
            assertFalse(responseMessage.getToolCalls() == null || responseMessage.getToolCalls().isEmpty());
            assertEquals(1, responseMessage.getToolCalls().size());

            ChatCompletionsFunctionToolCall functionToolCall = (ChatCompletionsFunctionToolCall) responseMessage.getToolCalls().get(0);
            assertNotNull(functionToolCall);
            assertEquals(functionToolCall.getFunction().getName(), "FutureTemperature"); // see base class
            assertFalse(functionToolCall.getFunction().getArguments() == null
                    || functionToolCall.getFunction().getArguments().isEmpty());

            // we should be passing responseMessage.getContent()) instead of ""; but it's null and Azure does not accept that
            ChatCompletionsOptions followUpChatCompletionsOptions = getChatCompletionsOptionWithToolCallFollowUp(
                    functionToolCall, "");

            ChatCompletions followUpChatCompletions = client.getChatCompletions(modelId, followUpChatCompletionsOptions);

            assertNotNull(followUpChatCompletions);
            assertNotNull(followUpChatCompletions.getChoices());
            ChatChoice followUpChatChoice = followUpChatCompletions.getChoices().get(0);
            assertNotNull(followUpChatChoice);
            assertNotNull(followUpChatChoice.getMessage());
            String content = followUpChatChoice.getMessage().getContent();
            assertFalse(content == null || content.isEmpty());
            assertEquals(followUpChatChoice.getMessage().getRole(), ChatRole.ASSISTANT);
            assertEquals(followUpChatChoice.getFinishReason(), CompletionsFinishReason.STOPPED);
        }));
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    public void testGetChatCompletionsToolCallStreaming(HttpClient httpClient, OpenAIServiceVersion serviceVersion) {
        client = getOpenAIClient(httpClient, serviceVersion);
        getChatWithToolCallRunner(((modelId, chatCompletionsOptions) -> {
            IterableStream<ChatCompletions> chatCompletionsStream = client.getChatCompletionsStream(modelId, chatCompletionsOptions);

            StringBuilder argumentsBuilder = new StringBuilder();
            long totalStreamMessages = chatCompletionsStream.stream().count();
            String functionName = null;
            String toolCallId = null;
            String content = null;
            assertTrue(totalStreamMessages > 0);

            int i = 0;
            for (ChatCompletions chatCompletions : chatCompletionsStream) {
                List<ChatChoice> chatChoices = chatCompletions.getChoices();
                if (!chatChoices.isEmpty() && chatChoices.get(0) != null) {
                    assertEquals(1, chatChoices.size());
                    ChatChoice chatChoice = chatChoices.get(0);
                    List<ChatCompletionsToolCall> toolCalls = chatChoice.getDelta().getToolCalls();
                    if (toolCalls != null && !toolCalls.isEmpty()) {
                        assertEquals(1, toolCalls.size());
                        ChatCompletionsFunctionToolCall toolCall = (ChatCompletionsFunctionToolCall) toolCalls.get(0);
                        FunctionCall functionCall = toolCall.getFunction();

                        // this data is only available in the second stream message, if at all
                        // The first contains filter results mostly
                        if (i == 1) {
                            content = chatChoice.getDelta().getContent();
                            functionName = functionCall.getName();
                            toolCallId = toolCall.getId();
                        }
                        argumentsBuilder.append(functionCall.getArguments());
                    }
                    if (i < totalStreamMessages - 1) {
                        assertNull(chatChoice.getFinishReason());
                    } else {
                        assertEquals(CompletionsFinishReason.TOOL_CALLS, chatChoice.getFinishReason());
                    }
                }
                i++;
            }
            assertFunctionToolCallArgs(argumentsBuilder.toString());
            FunctionCall functionCall = new FunctionCall(functionName, argumentsBuilder.toString());
            ChatCompletionsFunctionToolCall functionToolCall = new ChatCompletionsFunctionToolCall(toolCallId, functionCall);

            // we should be passing responseMessage.getContent()) instead of ""; but it's null and Azure does not accept that
            ChatCompletionsOptions followUpChatCompletionsOptions = getChatCompletionsOptionWithToolCallFollowUp(
                    functionToolCall, "");

            IterableStream<ChatCompletions> followupChatCompletionsStream = client.getChatCompletionsStream(modelId, followUpChatCompletionsOptions);
            assertNotNull(followupChatCompletionsStream);

            StringBuilder contentBuilder = new StringBuilder();
            long totalStreamFollowUpMessages = followupChatCompletionsStream.stream().count();
            int j = 0;

            for (ChatCompletions chatCompletions: followupChatCompletionsStream) {
                List<ChatChoice> chatChoices = chatCompletions.getChoices();
                if (!chatChoices.isEmpty() && chatChoices.get(0) != null) {
                    assertEquals(1, chatChoices.size());
                    ChatChoice chatChoice = chatChoices.get(0);
                    contentBuilder.append(chatChoice.getDelta().getContent());
                    if (j < totalStreamFollowUpMessages - 1) {
                        assertNull(chatChoice.getFinishReason());
                    } else {
                        assertEquals(CompletionsFinishReason.STOPPED, chatChoice.getFinishReason());
                    }
                }
                j++;
            }
            assertFalse(CoreUtils.isNullOrEmpty(contentBuilder.toString()));
        }));
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    public void testTextToSpeech(HttpClient httpClient, OpenAIServiceVersion serviceVersion) {
        client = getOpenAIClient(httpClient, serviceVersion);
        textToSpeechRunner(((modelId, speechGenerationOptions) -> {
            BinaryData binaryData = client.generateSpeechFromText(modelId, speechGenerationOptions);
            assertNotNull(binaryData);
        }));
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    public void testTextToSpeechWithResponse(HttpClient httpClient, OpenAIServiceVersion serviceVersion) {
        client = getOpenAIClient(httpClient, serviceVersion);
        textToSpeechRunner(((modelId, speechGenerationOptions) -> {
            Response<BinaryData> response = client.generateSpeechFromTextWithResponse(modelId,
                    BinaryData.fromObject(speechGenerationOptions), new RequestOptions());
            assertTrue(response.getStatusCode() > 0);
            assertNotNull(response.getHeaders());
            BinaryData speech = response.getValue();
            assertNotNull(speech);
            assertNotNull(speech.toBytes());
        }));
    }
}
