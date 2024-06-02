package org.microroutenet.core;

import org.springframework.context.ApplicationListener;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.stereotype.Component;

@Component
public class AdditionalRefreshEventListener implements ApplicationListener<EnvironmentChangeEvent> {

    private final InitializeListeners initializeListeners;

    public AdditionalRefreshEventListener(InitializeListeners initializeListeners) {
        this.initializeListeners = initializeListeners;
    }

    @Override
    public void onApplicationEvent(EnvironmentChangeEvent event) {
        System.out.println("Environment has been refreshed! Performing stop listeners...");
        performAdditionalTask();
    }

    private void performAdditionalTask() {
        initializeListeners.stopAllPlugins();
    }
}
