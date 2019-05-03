package com.example.espaceetudiant;

public class DraftProcessItem {
    public String app_uid;
    public String tas_uid;
    public String pro_uid;
    public String app_pro_title;
    public String app_tas_title;
    public String app_create_date;
    public String app_update_date;
    public String usr_firstname;
    public String usr_lastname;
    public String usr_username;
    public String del_task_due_date;
    public DraftProcessItem(String app_uid, String tas_uid, String pro_uid, String app_pro_title, String app_tas_title, String app_create_date, String app_update_date, String usr_firstname, String usr_lastname, String usr_username, String del_task_due_date) {
        this.app_uid = app_uid;
        this.tas_uid = tas_uid;
        this.pro_uid = pro_uid;
        this.app_pro_title = app_pro_title;
        this.app_tas_title = app_tas_title;
        this.app_create_date = app_create_date;
        this.app_update_date = app_update_date;
        this.usr_firstname = usr_firstname;
        this.usr_lastname = usr_lastname;
        this.usr_username = usr_username;
        this.del_task_due_date = del_task_due_date;
    }

    public String getTas_uid() {
        return tas_uid;
    }

    public void setTas_uid(String tas_uid) {
        this.tas_uid = tas_uid;
    }

    public String getPro_uid() {
        return pro_uid;
    }

    public void setPro_uid(String pro_uid) {
        this.pro_uid = pro_uid;
    }

    public String getApp_pro_title() {
        return app_pro_title;
    }

    public void setApp_pro_title(String app_pro_title) {
        this.app_pro_title = app_pro_title;
    }

    public String getApp_tas_title() {
        return app_tas_title;
    }

    public void setApp_tas_title(String app_tas_title) {
        this.app_tas_title = app_tas_title;
    }

    public String getApp_create_date() {
        return app_create_date;
    }

    public void setApp_create_date(String app_create_date) {
        this.app_create_date = app_create_date;
    }

    public String getApp_update_date() {
        return app_update_date;
    }

    public void setApp_update_date(String app_update_date) {
        this.app_update_date = app_update_date;
    }

    public String getUsr_firstname() {
        return usr_firstname;
    }

    public void setUsr_firstname(String usr_firstname) {
        this.usr_firstname = usr_firstname;
    }

    public String getUsr_lastname() {
        return usr_lastname;
    }

    public void setUsr_lastname(String usr_lastname) {
        this.usr_lastname = usr_lastname;
    }

    public String getUsr_username() {
        return usr_username;
    }

    public void setUsr_username(String usr_username) {
        this.usr_username = usr_username;
    }

    public String getDel_task_due_date() {
        return del_task_due_date;
    }

    public void setDel_task_due_date(String del_task_due_date) {
        this.del_task_due_date = del_task_due_date;
    }
}
