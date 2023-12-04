package com.doitmin.webapp.api.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Board {
    @Id
    long id;
    double latitude;
    double longitude;
    String address;
}
