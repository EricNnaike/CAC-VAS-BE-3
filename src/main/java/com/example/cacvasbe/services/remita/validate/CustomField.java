package com.example.cacvasbe.services.remita.validate;

import java.io.Serializable;
import java.util.List;

public class CustomField implements Serializable {
    private String id;

    private List<Value> values = null;


    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }


    public List<Value> getValues() {
        return values;
    }


    public void setValues(List<Value> values) {
        this.values = values;
    }

    @Override
    public String toString() {
        return "CustomField{" +
                "id='" + id + '\'' +
                ", values=" + values +
                '}';
    }
}
