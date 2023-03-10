package com.example.cacvasbe.services.remita.serviceTypes;

import java.io.Serializable;

public class GetServiceResponseData implements Serializable {
    private String id;
    private String name;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "GetServiceResponseData{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
