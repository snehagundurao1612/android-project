package com.project.work.projectviewer;

/**
 * Created by work on 4/25/2017.
 */

public class ProjectInformation {


    public String appname;
    public String member;
    public String description;
    public String status;
    public String downloadlink;
    public String url;


    public ProjectInformation(){

    }

    public ProjectInformation(String appname, String member, String description, String status, String downloadlink, String url) {
        this.appname = appname;
        this.member = member;
        this.description = description;
        this.status = status;
        this.downloadlink = downloadlink;
        this.url = url;
    }
    public String getAppname(){
        return appname;
    }
    public String getmember(){
        return member;
    }
    public String getdescription(){
        return description;
    }
    public String getstatus(){
        return status;
    }
    public String getdownloadlink(){
        return downloadlink;
    }
}
