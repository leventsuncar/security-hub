package com.lsuncar.securityhub.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "system_config")
@Getter
@Setter
public class SystemConfigEntity {

    @Id
    @Column(name = "key")
    private String key;

    @Column(name = "value")
    private String value;

}
