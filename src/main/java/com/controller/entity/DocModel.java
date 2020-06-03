package com.controller.entity;

import java.io.Serializable;
import java.util.Map;

public class DocModel implements Serializable {

    private Map<String, Object> model;

    public DocModel() {
    }

    public DocModel(Map<String, Object> model) {
        this.model = model;
    }

    public Map<String, Object> getModel() {
        return model;
    }

    public void setModel(Map<String, Object> model) {
        this.model = model;
    }
}
