package com.example.uuuu;


import java.util.Random;

public class Villain extends Battle {


    double critChance;

    public Villain(String name, int lifeMax, int startEnergy, int powerMax,double critChance) {
        super(name, lifeMax, startEnergy, powerMax);
        this.critChance = critChance;
    }




    public void increaseStats(double factor) {
        lifeMax *= factor;
        startEnergy *= factor;
        startEnergy += 3;
        startStrength += factor;

        lifeActual = lifeMax;
        currentEnergy = startEnergy;
        currentStrength = startStrength;

        stunned = false;
    }

    public double getCritChance() {
        return critChance;
    }
}



