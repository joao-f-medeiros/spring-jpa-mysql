package com.javaee.springjpamysql.domain;

import lombok.Getter;

import java.io.Serializable;

@Getter
public final class IdentifierEntity {

    private IdentifierEntity(Serializable id) {
        this.id = id;
    }

    private final Serializable id;

    public static IdentifierEntity of(Serializable id) {
        return new IdentifierEntity(id);
    }

}
