package org.generation.italy.codeSchool.model.entities;

public enum RemotePlatform {
    ZOOM(1000.0),MICROSOFT_TEAMS(1200.0),WEBEX(1500.0);
    private double annualCost;
    private RemotePlatform(double annualCost){
        this.annualCost = annualCost;
    }

    public double getAnnualCost(){
        return annualCost;
    }
}
