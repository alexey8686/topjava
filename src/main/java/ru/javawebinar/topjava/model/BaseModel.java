package ru.javawebinar.topjava.model;

public abstract class BaseModel {
    private Integer id;

    public BaseModel() {
    }

    public BaseModel(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isNew(Integer id) {
        return this.id == null;
    }
}
