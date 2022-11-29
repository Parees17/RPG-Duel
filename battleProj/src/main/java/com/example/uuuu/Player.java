package com.example.uuuu;

public class Player extends Battle{

    double critChance;
    public Player(String name, int lifeMax, int startEnergy, int powerMax,double critChance) {
        super(name, lifeMax, startEnergy, powerMax);
        this.critChance = critChance;
    }

    ///adds statistic boosts and resets stats for next battle
    public void increaseStats() {
        moneyBalance += 25.0;
        lifeMax += 5;
        startEnergy += 2;
        startStrength += 1;
        critChance += 0.1;

        lifeActual = lifeMax;
        currentEnergy = startEnergy;
        currentStrength = startStrength;

        stunned = false;

    }


    public double getCritChance() {
        return critChance;
    }

}
