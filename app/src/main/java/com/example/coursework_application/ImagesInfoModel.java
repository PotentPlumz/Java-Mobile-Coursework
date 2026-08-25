package com.example.coursework_application;

public class ImagesInfoModel {

    private String building_name;
    private String building_services;
    private String building_desc;
    private Integer building_image;
    private Integer building_image_bigger;

    public ImagesInfoModel() {}

    public ImagesInfoModel(String title, String services, String desc,  Integer image, Integer big_image)
    {
        this.building_name = title;
        this.building_services = services;
        this.building_desc = desc;
        this.building_image = image;
        this.building_image_bigger = big_image;
    }

    public String get_Building_name()
    {
        return building_name;
    }

    public String get_Building_Services()
    {
        return building_services;
    }

    public String get_Building_Desc(){return building_desc; }

    public Integer getBuilding_image() {return building_image; }

    public Integer getBuilding_image_bigger() {return building_image_bigger; }

    public void change_class_to_undiscovered(){
        this.building_name = "???";
        this.building_services = "???";
        this.building_desc = "???";
    }
}





