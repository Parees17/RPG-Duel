package com.example.uuuu;

public class Battle {

    // basic values
    private String name;

    int moneyBalance;
    int lifeMax;
    int startEnergy;
    int startStrength;


    int lifeActual;
    int currentEnergy;
    int currentStrength;


    // state values
    protected boolean alive;
    protected static boolean stunned;


    public Battle(String name, int lifeMax, int startEnergy, int startStrength) {
        this.name = name;
        this.lifeMax = lifeMax;
        this.startEnergy = startEnergy;
        this.startStrength = startStrength;

        this.lifeActual = lifeMax;
        this.currentEnergy = startEnergy;
        this.currentStrength = startStrength;


        // state values
        alive = true;
        // when stunned, user or enemy gets an extra turn
        stunned = false;
        // how many rounds, how big is damage

    }



    public String getName() {
        return name;
    }

    public int getLife() {
        return lifeActual;
    }

    public int getLifeMax() {
        return lifeMax;
    }

    public int getEnergy() {
        return currentEnergy;
    }

    public int getStartEnergy() {
        return startEnergy;
    }

    public int getMoneyBalance() {
        return moneyBalance;
    }



    public boolean isStunned() {
        if(stunned) {

            stunned = false;
            return true;
        } else {
            return false;
        }
    }


    public boolean isAlive() {
        return alive;
    }

    public void checkDamage(int value) {
        if(value > 0) {

            lifeActual -= value;
        }

        if (lifeActual <= 0) {
            alive = false;
        }
    }

    public boolean checkEnergy(int value) {
        if(value <= currentEnergy) {
            currentEnergy -= value;
            return true;
        } else {

            return false;
        }
    }


    public void addHealth(int value) {
        int difference = lifeMax - lifeActual;
        if(value >= difference) {
            lifeActual = lifeMax;

        } else {
            lifeActual += value;

        }
    }

    public void addEnergy(int value) {
        int difference = startEnergy - currentEnergy;
        if(value >= difference) {
            currentEnergy = startEnergy;

        } else {
            currentEnergy += value;

        }
    }

}

