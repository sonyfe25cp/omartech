package com.omartech.review_quality.chinese.model;

/**
 * Created by OmarTech on 15-3-21.
 */
public class KReview {

    private int id;
    private int company_id;
    private String name;
    private String pros;
    private String cons;
    private String advice;
    private int rating;
    private int useful_num;
    private int weight;
    private int employee_status;
    private int recommend_friend;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCompany_id() {
        return company_id;
    }

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPros() {
        return pros;
    }

    public void setPros(String pros) {
        this.pros = pros;
    }

    public String getCons() {
        return cons;
    }

    public void setCons(String cons) {
        this.cons = cons;
    }

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getUseful_num() {
        return useful_num;
    }

    public void setUseful_num(int useful_num) {
        this.useful_num = useful_num;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getEmployee_status() {
        return employee_status;
    }

    public void setEmployee_status(int employee_status) {
        this.employee_status = employee_status;
    }

    public int getRecommend_friend() {
        return recommend_friend;
    }

    public void setRecommend_friend(int recommend_friend) {
        this.recommend_friend = recommend_friend;
    }
}
