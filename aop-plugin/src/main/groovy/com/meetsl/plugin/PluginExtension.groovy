package com.meetsl.plugin

class PluginExtension{
    def enabled = true

    def setEnabled(boolean enabled) {
        this.enabled = enabled
    }

    def getEnabled() {
        return enabled
    }
}