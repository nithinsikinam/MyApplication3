package com.example.myapplication;

import java.util.List;

class Course{
public String chapter;
public List<Topic> topics;

    public Course(String chapter, List<Topic> topics) {
        this.chapter = chapter;
        this.topics = topics;
    }
}