# Release History

## 1.0.0-beta.8 (Unreleased)

### Features Added

- Added a new overload `getChatCompletionsStreamWithResponse` that takes `RequestOptions` to provide the flexibility to
  modify the HTTP request.

### Breaking Changes

### Bugs Fixed

### Other Changes

## 1.0.0-beta.7 (2024-03-04)

### Features Added

- Text-to-speech using OpenAI TTS models is now supported. See [OpenAI's API reference](https://platform.openai.com/docs/api-reference/audio/createSpeech) 
  or the [Azure OpenAI quickstart](https://learn.microsoft.com/azure/ai-services/openai/text-to-speech-quickstart)
  for detailed overview and background information. The new method `generateSpeechFromText` exposes this capability on 
  `OpenAIClient` and `OpenAIAsyncClient`. Text-to-speech converts text into lifelike spoken audio in a chosen voice, together with other optional
  configurations. This method works for both Azure OpenAI and non-Azure `api.openai.com` client configurations.
- Added two new authentication options, `OnYourDataEncodedApiKeyAuthenticationOptions` and `OnYourDataAccessTokenAuthenticationOptions`
  to support the new authentication mechanism for "On Your Data" feature.

### Breaking Changes

- Introduced a new type `AzureChatExtensionDataSourceResponseCitation` for a more structured representation of citation data.
- Correspondingly, updated `AzureChatExtensionsMessageContext`:
  - Replaced `messages` with `citations` of type `AzureChatExtensionDataSourceResponseCitation`.
  - Added `intent` as a string type.
- Renamed "AzureCognitiveSearch" to "AzureSearch":
  - `AzureCognitiveSearchChatExtensionConfiguration` is now `AzureSearchChatExtensionConfiguration`.
  - `AzureCognitiveSearchIndexFieldMappingOptions` is now `AzureSearchIndexFieldMappingOptions`.
  - `AzureCognitiveSearchQueryType` is now `AzureSearchQueryType`.
- Replaced `String` property `name` by `ChatCompletionsFunctionToolSelection` property `function` in `ChatCompletionsNamedFunctionToolSelection`
- Made `embeddingDependency` as a required parameter in `AzureCosmosDBChatExtensionParameters` and `PineconeChatExtensionParameters` class, and removed setter method.
- Removed `vectorFields` and `imageVectorFields` from `PineconeFieldMappingOptions` class, and made `contentField` as required parameter.
- Removed `getAudioTranscriptionAsPlainTextWithResponse` and `getAudioTranslationAsPlainTextWithResponse` methods from `OpenAIClient` and `OpenAIAsyncClient` classes.
- Made `ImageGeneration` constructor as private.
- Made `ImageGenerationData` constructor as private and removed setter methods.

### Bugs Fixed

- Fixed `ChatRequestUserMessage` deserialization issue. [#38183](https://github.com/Azure/azure-sdk-for-java/issues/38183)

### Other Changes

- Dropped service API version support for `2023-08-01-preview`, `2023-09-01-preview` and `2023-12-01-preview`.
- Made the `getContent` a public method in `ChatRequestUserMessage` class. ([#38805](https://github.com/Azure/azure-sdk-for-java/pull/38805))
- Added a new property `logprobs` in `ChatChoice` class to support log probabilities for this chat choice.
- Added new properties `logprobs` and `topLogprobs` in `ChatCompletionsOptions` class to support log probabilities for chat completions.
- Added a new property `inputType` in `EmbeddingsOptions` class to support embeddings for different input types 
  when using Azure OpenAI, specifies the input type to use for embedding search.
- Added more properties to `AzureCosmosDBFieldMappingOptions` class to support more field mapping options, including
  `titleField`, `urlField`, `filepathField`, `contentFields`, and `contentFieldsSeparator`. Made `contentField` as required parameter.
- Added new properties `ImageGenerationContentFilterResults contentFilterResults` and `ImageGenerationPromptFilterResults promptFilterResults`
  in `ImageGenerationData` class to support filtering results.
- Added new property `suffix` in `CompletionsOptions` class to support suffix for completions.

## 1.0.0-beta.6 (2023-12-11)

### Features Added

- `-1106` model feature support for `gpt-35-turbo` and `gpt-4-turbo`, including use of `seed`, `system_fingerprint`,
  parallel function calling via tools, "JSON mode" for guaranteed function outputs, and more
- `dall-e-3` image generation capabilities via `getImageGenerations`, featuring higher model quality, automatic prompt
  revisions by `gpt-4`, and customizable quality/style settings
- Greatly expanded "On Your Data" capabilities in Azure OpenAI, including many new data source options and authentication
  mechanisms
- Early support for `gpt-4-vision-preview`, which allows the hybrid use of text and images as input to enable scenarios
  like "describe this image for me"
- Support for Azure enhancements to `gpt-4-vision-preview` results that include grounding and OCR features

### Breaking Changes

- Removed methods `getAudioTranscriptionAsResponseObject` and `getAudioTranscriptionAsResponseObjectWithResponse` from `OpenAIClient` and `OpenAIAsyncClient` classes.
Use `getAudioTranscription` or `getAudioTranscriptionWithResponse` convenience methods from respective classes.
- Removed methods `getAudioTranslationAsResponseObject` and `getAudioTranslationAsResponseObjectWithResponse` from `OpenAIClient` and `OpenAIAsyncClient` classes.
Use `getAudioTranslation` or `getAudioTranslationWithResponse` convenience methods from respective classes.

**`ChatMessage` changes:**

- The singular `ChatMessage` type has been replaced by `ChatRequestMessage` and `ChatResponseMessage`, the former of
  which is an abstract, polymorphic type with concrete derivations like `ChatRequestSystemMessage` and
  `ChatRequestUserMessage`. This requires conversion from old `ChatMessage` into the new types. While this is
  usually a straightforward string replacement, converting a response message into a request message (e.g. when
  propagating an assistant response to continue the conversation) will require creating a new instance of the
  appropriate request message with the response message's data. See the examples for details.

**Dall-e-3:**

- Azure OpenAI now uses `dall-e-3` model deployments for its image generation API and such a valid deployment must
  be provided into the options for the `getImageGenerations()` method to receive results.

### Other Changes

- Audio transcription and translation (via `getAudioTranscription()` and `getAudioTranslation()` now allow specification  
  of an optional `fileName` in addition to the binary audio data. This is used purely as an identifier and does not 
  functionally alter the transcription/translation behavior in any way.

## 1.0.0-beta.5 (2023-09-22)

### Features Added

- Added support for `Whisper` endpoints.
- Translation and Transcription of audio files is available.
- The above features are available both in Azure and non-Azure OpenAI.
- Added more convenience methods, which are wrappers around the existing`get{ChatCompletions|Completions|Embeddings}WithResponse` 
  methods with concrete data types instead of using `BinaryData` as the return data type. For example, a new method 
  introduced is
  - Async: `Mono<Response<ChatCompletions>> getChatCompletionsWithResponse(String deploymentOrModelName, ChatCompletionsOptions chatCompletionsOptions, RequestOptions requestOptions)`
  - Sync: `Response<ChatCompletions> getChatCompletionsWithResponse(String deploymentOrModelName, ChatCompletionsOptions chatCompletionsOptions, RequestOptions requestOptions)`
  
  Same methods are added for `Completions` and `Embeddings` endpoints as well.

### Breaking Changes

- Replaced usage of class `AzureKeyCredential` by `KeyCredential`.

### Other Changes

#### Dependency Updates

- Upgraded `azure-core` from `1.42.0` to version `1.43.0`.
- Upgraded `azure-core-http-netty` from `1.13.6` to version `1.13.7`.

## 1.0.0-beta.4 (2023-08-28)

### Features Added

- Support for BYOD (Bring Your Own Data) was added. [Related link](https://learn.microsoft.com/azure/ai-services/openai/use-your-data-quickstart)

### Breaking Changes

- Replaced usage of class `NonAzureOpenAIKeyCredential` by Azure Core class `KeyCredential`.

### Other Changes

#### Dependency Updates

- Upgraded `azure-core` from `1.41.0` to version `1.42.0`.
- Upgraded `azure-core-http-netty` from `1.13.5` to version `1.13.6`.

## 1.0.0-beta.3 (2023-07-19)

### Features Added

- Added methods and models to support DALL-E
- Added methods and models to support Functions
- Added models supporting ResponsibleAI annotations

### Bugs Fixed

- Fixed garbled characters issue in the returned data of `getChatCompletionsStream`.

### Other Changes

#### Dependency Updates
- Updated `azure-core` to `1.41.0`.
- Updated `azure-core-http-netty` to `1.13.5`.

## 1.0.0-beta.2 (2023-06-13)

### Breaking Changes

- Removed class `ChatMessageDelta` and replaced usage of it by `ChatMessage`. 

### Bugs Fixed
- [PR#35336](https://github.com/Azure/azure-sdk-for-java/commit/bf4fdac9cea3c18362029df4589bc78b834a4348) fixed
  `com.fasterxml.jackson.databind.exc.MismatchedInputException: Missing required creator property 'usage' (index 3)`.

### Other Changes

#### Dependency Updates
- Updated `azure-core` to `1.40.0`.
- Updated `azure-core-http-netty` to `1.13.4`.

## 1.0.0-beta.1 (2023-05-22)

- Azure OpenAI client library for Java. This package contains Microsoft Azure OpenAI client library. Initial generation from [spec](https://github.com/Azure/azure-rest-api-specs/tree/main/specification/cognitiveservices/OpenAI.Inference)
- Support for Non-Azure OpenAI endpoints was introduced, by passing `NonAzureOpenAIKeyCredential` to the `OpenAIClientBuilder`
- Added Streaming support to `Completions` and `ChatCompletions` endpoints
