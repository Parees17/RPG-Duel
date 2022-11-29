package com.example.uuuu;

public class Special {

    HelloController user;
    String name;
    int level;
    double strength;
    int energyRequirement;
    double purchasePrice;

    public Special(HelloController user, String name, int level, double strength, int energyRequirement, double purchasePrice) {
        this.user = user;
        this.name = name;
        this.level = level;

        // multiplicated with level
        this.strength = strength;
        // multilicated with level
        this.energyRequirement = energyRequirement;
        this.purchasePrice = purchasePrice;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return (int)(strength * level);
    }

    public int getLevel() {
        return level;
    }

    public int getEnergyRequirement() {
        return energyRequirement * level;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public boolean checkMoney(double value) {
        if(value <= purchasePrice) {
            return true;
        } else {
            return false;
        }
    }

}
