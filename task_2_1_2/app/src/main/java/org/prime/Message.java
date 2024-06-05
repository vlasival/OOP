package org.prime;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * Class represents a message wandering on the network.
 */
@Data
public class Message implements Serializable {
    private String type;
    private List<Integer> data;
}