package com.javaee.springjpamysql.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GasStation {

    private Long id;

    private Garage garage;
    
    private String name;
}
