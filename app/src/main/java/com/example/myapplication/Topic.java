package com.example.myapplication;

import java.util.List;

class Topic {
    public Topic(String topic, List<Resource> resources) {
        this.topic = topic;
        this.resources = resources;
    }

    public String topic;
public List<Resource> resources;
}