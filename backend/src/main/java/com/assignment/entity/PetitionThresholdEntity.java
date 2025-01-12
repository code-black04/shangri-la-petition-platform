package com.assignment.entity;

import javax.persistence.*;

@Entity
@Table(name = "petition_threshold")
public class PetitionThresholdEntity {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "threshold_value")
    private Integer thresholdValue;

    public PetitionThresholdEntity() {
    }

    public PetitionThresholdEntity(Integer id, Integer thresholdValue) {
        this.id = id;
        this.thresholdValue = thresholdValue;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getThresholdValue() {
        return thresholdValue;
    }

    public void setThresholdValue(Integer thresholdValue) {
        this.thresholdValue = thresholdValue;
    }

    @Override
    public String toString() {
        return "PetitionThresholdEntity{" +
                "id=" + id +
                ", thresholdValue=" + thresholdValue +
                '}';
    }
}
