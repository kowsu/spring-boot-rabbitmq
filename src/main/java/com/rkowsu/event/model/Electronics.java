package com.rkowsu.event.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Electronics extends Product{
    private String origin, sizeOfRAM, processor;
}
