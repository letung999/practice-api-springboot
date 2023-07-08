package com.example.practice_api.entities;

import lombok.Data;
import org.hibernate.id.GUIDGenerator;

import javax.persistence.*;

@Entity
@Table
@Data
public class CarDetail {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "weight", length = 255, nullable = true)
    private Float weight;

    @Column(name = "color", length = 255, nullable = true)
    private String color;

    @Column(name = "height", length = 255, nullable = true)
    private Float height;

    @Column(name = "maxSpeech", length = 255, nullable = true)
    private Float maxSpeech;
}
