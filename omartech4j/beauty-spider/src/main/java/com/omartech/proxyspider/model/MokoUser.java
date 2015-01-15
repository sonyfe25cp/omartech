package com.omartech.proxyspider.model;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by omar on 15-1-8.
 */
public class MokoUser {

    public int id;
    public String name;
    public int sex;// 1：男，2：女
    public Set<String> tags = new HashSet<>();


    public void addTag(String tag){
        tags.add(tag);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "MokoUser{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sex=" + sex +
                ", tags=" + tags +
                '}';
    }
}
