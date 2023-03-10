package com.example.cacvasbe.services.remita.billers;

import java.io.Serializable;

public class GetBillerResponseData implements Serializable {
    private String id;

    private String description;

    private String label;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "GetBillerResponseData{" +
                "id='" + id + '\'' +
                ", description='" + description + '\'' +
                ", label='" + label + '\'' +
                '}';
    }
}
