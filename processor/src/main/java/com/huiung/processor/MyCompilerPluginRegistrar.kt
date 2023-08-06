package com.huiung.processor

import com.google.auto.service.AutoService
import org.jetbrains.kotlin.cli.common.CLIConfigurationKeys
import org.jetbrains.kotlin.cli.common.messages.MessageCollector
import org.jetbrains.kotlin.codegen.extensions.ClassBuilderInterceptorExtension
import org.jetbrains.kotlin.compiler.plugin.CompilerPluginRegistrar
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import org.jetbrains.kotlin.config.CompilerConfiguration


@OptIn(ExperimentalCompilerApi::class)
@AutoService(CompilerPluginRegistrar::class)
class MyCompilerPluginRegistrar : CompilerPluginRegistrar() {
    override val supportsK2 = false

    override fun ExtensionStorage.registerExtensions(configuration: CompilerConfiguration) {
//        if (configuration[KEY_ENABLED_TAG] == false) {
//            return
//        }


        val logger = configuration.get(CLIConfigurationKeys.MESSAGE_COLLECTOR_KEY, MessageCollector.NONE)

        ClassBuilderInterceptorExtension.registerExtension(
            MyClassGenerationInterceptor(logger)
        )
    }
}