package com.Freestor.RestAPI;
public class  ApiResource {

    // Post API

    public static String postLogin(){
        return "auth/login";
    }
    public static String postLogout(){
        return "auth/logout";
    }
    public static String postAddClient(){
        return "";
    }
    public static String postAddUser(){
        return "rest/user";
    }

    // Get API
    public static String getenumAdapters(){return "physicalresource/physicaladapter";}
    public static String getaAdapter(int adapterID) {return "physicalresource/physicaladapter/"+adapterID;}
    public static String getClientInitiator (){return "physicalresource/physicaladapter/fcclientinitiators";}
    public static String getFCbasePort(){return "physicalresource/physicaladapter/fcwwpn" ;}
    public static String getEnumPhysicalDevices (){return "physicalresource/physicaldevice";}
    public static String getPhyiscalDevicePath (String guid){return  "physicalresource/physicaldevice/scsialias/"+guid;}
    public static String getFCTargetPort(){return "physicalresource/physicaladapter/fctgtwwpn";}
    public static String getEnumUnassignedPhysicalDevices(){return  "physicalresource/physicaldevice/unassigned";}
    public static String getEnumeleligibleTarget(){return "physicalresource/physicaldevice/eligibletargets";}
    public static String getPhysicalDevices(String guid){return "physicalresource/physicaldevice/"+ guid;}
    public static String getEnumStoragePool(){return "physicalresource/storagepool";}
    public static String getSanresource(){return "logicalresource/sanresource";}
    public static String getReplication(int id){return  "logicalresource/replication/" + id;}
    public static String getConfigurationRepo(){return "logicalresource/configurationrepository";}

    // Put API
    // Requires lots of Testing
    public static String putManagePhysicalAdapter(int adapterNumberID){return "physicalresource/physicaladapter/"+adapterNumberID;}
    public static String putRescanPhysicalDevices(){return "physicalresource/physicaldevice/rescan";}
    public static String putManagePhysicalDevice(int guid){return "physicalresource/physicaldevice/"+guid;}
    public static String putManagePhysicalResourceBatch(){return "batch/physicalresource/physicaldevice";}

    //Post API
    public static String postCreateBlockDevice(){return "physicalresource/physicaldevice";}
    public static String postCreateStoragePool(){return "physicalresource/storagepool";}
    public static String postConfigureRepo(){return "logicalresource/configurationrepository";}
    public static String postReConfigureRepo(){return "logicalresource/configurationrepository/reconfigure";}

    //Delete API
    public static String delConfigureRepo(){return "logicalresource/configurationrepository";}
}
