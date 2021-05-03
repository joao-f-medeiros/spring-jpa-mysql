package com.javaee.springjpamysql.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javaee.springjpamysql.repositories.GarageRepository;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Persistable;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.persistence.*;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class AuditListener {

    private ObjectMapper objectMapper;

    @PostPersist
    @SneakyThrows
    private void afterPersist(Object object) {
        objectMapper = SpringContext.getBean(ObjectMapper.class);
        JsonNode json = convertEntityToJsonNode(object);
        GarageRepository garageRepository = SpringContext.getBean(GarageRepository.class);
        garageRepository.findById(json.get("id").asLong());
        log.info("Saving Entity: {}", json);
        log.info("Sending to RabbitMQ retrieve data before sending to Audit Service");
    }

    @SneakyThrows
    private JsonNode convertEntityToJsonNode(Object entity) {
        String currentTransactionName = TransactionSynchronizationManager.getCurrentTransactionName();
        Map<String, Object> entityMap = new HashMap<>();
        for (Field attribute : entity.getClass().getDeclaredFields()) {
            String key = attribute.getName();
            attribute.setAccessible(true);
            Object value = attribute.get(entity);
            entityMap.put(key, value);
            if (!isPersistable(attribute)) {
                entityMap.remove(key);
            } else if (value != null && isEntity(attribute)) {
                entityMap.put(key, IdentifierEntity.of(((Persistable<Serializable>) value).getId()));
            } else if(value != null && isCollection(attribute)) {
                if (isOneToMany(attribute)) {
                    entityMap.remove(key);
                } else if (isManyToMany(attribute)) {
                    Collection<Persistable<Serializable>> collection = (Collection<Persistable<Serializable>>) value;
                    entityMap.put(key, collection.stream().map(Persistable::getId).map(IdentifierEntity::of).collect(Collectors.toList()));
                }
            }
        }

        return createJsonNode(entityMap);
    }

    @SneakyThrows
    private JsonNode createJsonNode(Map<String, Object> object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
//        Hibernate5Module hibernate5Module = new Hibernate5Module();
//        hibernate5Module.enable(Hibernate5Module.Feature.SERIALIZE_IDENTIFIER_FOR_LAZY_NOT_LOADED_OBJECTS);
//        objectMapper.registerModule(hibernate5Module);
        return objectMapper.readTree(objectMapper.writeValueAsString(object));
    }

    private boolean isOneToMany(Field attribute) {
        return attribute.getAnnotation(OneToMany.class) != null;
    }

    private boolean isManyToMany(Field attribute) {
        return attribute.getAnnotation(ManyToMany.class) != null;
    }

    private boolean isEntity(Field attribute) {
        return attribute.getType().getAnnotation(Entity.class) != null;
    }

    private boolean isCollection(Field attribute) {
        return Collection.class.isAssignableFrom(attribute.getType());
    }

    private boolean isPersistable(Field attribute) {
        return attribute.getAnnotation(Transient.class) == null;
    }

    @PostUpdate
    private void afterUpdate(Object object) {
        JsonNode json = convertEntityToJsonNode(object);
        GarageRepository garageRepository = SpringContext.getBean(GarageRepository.class);
        garageRepository.findById(json.get("id").asLong());
        log.info("Updating Entity: {}", json);
        log.info("Sending to RabbitMQ retrieve data before sending to Audit Service");
    }

    @PostRemove
    private void afterDelete(Object object) {
        log.info("Deleting Entity: {}", ((Garage)object).getId());
        log.info("Sending to RabbitMQ");
    }
}
