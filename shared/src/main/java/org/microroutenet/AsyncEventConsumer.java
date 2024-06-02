package org.microroutenet;

public interface AsyncEventConsumer {
    void accept(AsyncEvent event);
}
