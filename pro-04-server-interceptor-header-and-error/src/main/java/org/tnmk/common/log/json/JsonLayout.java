package org.tnmk.common.log.json;

import ch.qos.logback.classic.spi.ILoggingEvent;

import java.util.LinkedHashMap;
import java.util.Map;

public class JsonLayout extends ch.qos.logback.contrib.json.classic.JsonLayout {
    private static final String ATTR_TIMESTAMP = "time";


    @Override
    protected Map toJsonMap(ILoggingEvent event) {

        Map<String, Object> map = new LinkedHashMap<String, Object>();

        addTimestamp(ATTR_TIMESTAMP, this.includeTimestamp, event.getTimeStamp(), map);

        //Other methods are copied from ch.qos.logback.contrib.json.classic.JsonLayout
        add(LEVEL_ATTR_NAME, this.includeLevel, String.valueOf(event.getLevel()), map);
        add(THREAD_ATTR_NAME, this.includeThreadName, event.getThreadName(), map);
        addMap(MDC_ATTR_NAME, this.includeMDC, event.getMDCPropertyMap(), map);
        add(LOGGER_ATTR_NAME, this.includeLoggerName, event.getLoggerName(), map);
        add(FORMATTED_MESSAGE_ATTR_NAME, this.includeFormattedMessage, event.getFormattedMessage(), map);
        add(MESSAGE_ATTR_NAME, this.includeMessage, event.getMessage(), map);
        add(CONTEXT_ATTR_NAME, this.includeContextName, event.getLoggerContextVO().getName(), map);
        addThrowableInfo(EXCEPTION_ATTR_NAME, this.includeException, event, map);
        addCustomDataToJsonMap(map, event);
        return map;
    }
}
