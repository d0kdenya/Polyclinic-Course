package com.springrest.course.polyclinic.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springrest.course.polyclinic.dto.EventType;
import com.springrest.course.polyclinic.dto.ObjectType;
import com.springrest.course.polyclinic.dto.WsEventDto;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.function.BiConsumer;

@Component
public class WsSender {
    private final SimpMessagingTemplate template;
    private final ObjectMapper mapper;

    public WsSender(SimpMessagingTemplate template, ObjectMapper mapper) {
        this.template = template;
        this.mapper = mapper;
    }

    public <T> BiConsumer<EventType, T> getSender(ObjectType objectType) {
        ObjectMapper writer = mapper
                .setConfig(mapper.getSerializationConfig());

        return (EventType eventType, T payload) -> {
            String value;

            try {
                value = writer.writeValueAsString(payload);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

            template.convertAndSend(
                    "/topic/activity",
                    new WsEventDto(objectType, eventType, value)
            );
        };
    }
}
