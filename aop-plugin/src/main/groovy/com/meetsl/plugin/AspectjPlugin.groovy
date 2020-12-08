package com.meetsl.plugin

import com.android.build.gradle.AppExtension
import com.android.build.gradle.LibraryExtension
import org.aspectj.tools.ajc.Main
import org.aspectj.bridge.IMessage
import org.aspectj.bridge.MessageHandler
import org.gradle.api.Plugin
import org.gradle.api.Project

class AspectjPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        final def log = project.logger
        def hasApp = project.plugins.hasPlugin('com.android.application')
        def hasLib = project.plugins.hasPlugin('com.android.library')
        if (!hasApp && !hasLib) {
            throw new IllegalStateException("'android' or 'android-library' plugin required.")
        }
        def android
        final def variants
        if (hasApp) {
            android = project.extensions.getByType(AppExtension)
            variants = android.applicationVariants
        } else {
            android = project.extensions.getByType(LibraryExtension)
            variants = android.libraryVariants
        }
        project.dependencies {
            if (project.gradle.gradleVersion > "4.0") {
                project.logger.debug("gradlew version > 4.0")
                implementation 'org.aspectj:aspectjrt:1.9.5'
            } else {
                project.logger.debug("gradlew version < 4.0")
                compile 'org.aspectj:aspectjrt:1.9.5'
            }
        }

        project.extensions.create('aspectj', PluginExtension)
        variants.all { variant ->
            if (!project.aspectj.enabled) {
                log.debug("aspectj is not disabled.")
                return
            }
            def javaCompile = variant.getJavaCompileProvider().get()
            javaCompile.doLast {
                String[] args = [
                        "-showWeaveInfo",
                        "-1.5",
                        "-inpath", javaCompile.destinationDir.toString(),
                        "-aspectpath", javaCompile.classpath.asPath,
                        "-d", javaCompile.destinationDir.toString(),
                        "-classpath", javaCompile.classpath.asPath,
                        "-bootclasspath", android.bootClasspath.join(File.pathSeparator)
                ]
                log.debug "ajc args: " + Arrays.toString(args)

                MessageHandler handler = new MessageHandler(true)
                new Main().run(args, handler)
                for (IMessage message : handler.getMessages(null, true)) {
                    switch (message.getKind()) {
                        case IMessage.ABORT:
                        case IMessage.ERROR:
                        case IMessage.FAIL:
                            log.error message.message, message.thrown
                            break
                        case IMessage.WARNING:
                            log.warn message.message, message.thrown
                            break
                        case IMessage.INFO:
                            log.info message.message, message.thrown
                            break
                        case IMessage.DEBUG:
                            log.debug message.message, message.thrown
                            break
                    }
                }
            }
        }
    }
}