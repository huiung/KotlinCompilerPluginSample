package com.huiung.processor

import com.google.auto.service.AutoService
import org.jetbrains.kotlin.compiler.plugin.AbstractCliOption
import org.jetbrains.kotlin.compiler.plugin.CliOption
import org.jetbrains.kotlin.compiler.plugin.CommandLineProcessor
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.config.CompilerConfigurationKey

const val PluginId = "my.plugin"

val KEY_ENABLED_TAG = CompilerConfigurationKey<Boolean>("whether plugin is enable")
val OPTION_ENABLED = CliOption("enabled", "<true|false>", KEY_ENABLED_TAG.toString())

@OptIn(ExperimentalCompilerApi::class)
@AutoService(CommandLineProcessor::class)
class MyCommandLineProcessor: CommandLineProcessor {

    override val pluginId = PluginId

    override val pluginOptions: Collection<AbstractCliOption> = listOf(OPTION_ENABLED)

    override fun processOption(option: AbstractCliOption, value: String, configuration: CompilerConfiguration) {
        when (val optionName = option.optionName) {
            OPTION_ENABLED.optionName -> configuration.put(KEY_ENABLED_TAG, value.toBoolean())
            else -> error("Unknown plugin option: $optionName")
        }
    }

}