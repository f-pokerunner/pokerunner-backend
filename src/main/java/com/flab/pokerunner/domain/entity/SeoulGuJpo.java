package com.flab.pokerunner.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "seoul_gu")
@Getter
public class SeoulGuJpo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String guName;

    @CreatedDate
    private LocalDateTime createdDt;
}
