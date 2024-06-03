package org.prime;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class Message implements Serializable {
    private String type;
    private List<Integer> data;
}