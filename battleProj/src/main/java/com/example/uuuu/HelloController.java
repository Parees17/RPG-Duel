package com.example.uuuu;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class HelloController {
    @FXML
    private Label lblPlayerStats,lblRounds,lblStart,lblEnemyStats,lblScoreboard,lblPlayerScore,
            lblInGameAction,lblPurchaseInfo, shopIntroduction,
            lblMoneyBalance,lblCongratulations,lblDamageMessage,lblCritical,lblVictory,lblEnergy,lblFinalStats,lblSave,lblBoost;

    @FXML
    private Button startButton,buttonAttack1,buttonAttack2,buttonAttack3,
            buttonAttack4,buttonAttack5, buttonEnemy,buttonShop, buttonContinueGame,btnIceScythe, btnDreamInvader
            ,btnDarkMagic,btnHarmPotion,btnBeserkPotion,btnIceScytheAttack,
            btnDarkMagicAttack, btnDreamInvaderAttack,btnHarmPotionAttack,btnBeserkPotionAttack,btnLoad,btnSave;

    @FXML
    private ImageView imgBattleBackground,imgPotion, imgSpecialAttack;

    private Special firestorm, stunner, flowerHealing, lightningStrike, waterDragon,iceScythe,dreamInvader, darkMagic, harmPotion,  beserkPotion;

    private Player player;
    private Villain opponent;
    int powerActual;
    private int score;
    int rounds;
    int level = 1;
    private ArrayList <Special> attacks = new ArrayList<Special>();
    Villain ghoul = new Villain("Ghoul" , 60, 20,5,0.25);
    Villain demon = new Villain("Demon" , 55, 20,5,0.5);
    Villain goblin = new Villain("Goblin" , 70, 20,5,0.4);
    Villain dragon = new Villain("Dragon" , 35, 20,5,0.45);
    Villain asassain = new Villain("Assassain" , 45, 20,5,0.6);

    Villain[] listVillians = new Villain[]{ ghoul,demon, goblin,dragon, asassain};


    public HelloController(){
        player = new Player("Player", 50, 30, 5,0.1);
        opponent = new Villain("Enemy", 5, 30, 5,0.15);

        // skills
        firestorm = new Special(this, "Firestorm", 1, 3, 1,0);
        stunner = new Special(this, "Stunner", 1, 3, 2,0);
        flowerHealing = new Special(this, "Healing", 1, 2, 3,0);
        lightningStrike = new Special(this, "Lightning Strike", 1, 4, 2,0);
        waterDragon =  new Special(this, "Water Dragon", 1, 5, 3,0);
        iceScythe = new Special(this, "Ice Scythe", 1, 4, 3,10);
        dreamInvader =  new Special(this, "Dream Invader", 1, 2, 2, 5);
        darkMagic = new Special(this, "Dark Magic ", 1, 4, 4, 15);
        harmPotion = new Special(this, " Harming Potion ", 1, 5, 4, 12);
        beserkPotion = new Special(this, " Beserk Potion ", 1, 4, 5, 17);

        attacks.add(firestorm);
        attacks.add(stunner);
        attacks.add(flowerHealing);
        attacks.add(waterDragon);
        attacks.add(lightningStrike);



    }

    public void loadImages(){
        try {
            FileInputStream input = new FileInputStream("C:\\Users\\pradh\\IdeaProjects\\battleProj\\src\\main\\resources\\pics\\BattleBackground.jpg");
            imgBattleBackground.setImage(new Image(input));
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }

     


    }


    
    public int determinePlayerCrit(Special attack){
        double random = Math.random();
        System.out.println(random);
        int incomingAttack = attack.getValue();
        lblCritical.setText(" ");
        if (random <= opponent.getCritChance()) {
            incomingAttack = attack.getValue() * 2;
            lblCritical.setText(player.getName() + " has landed a critical hit!\n Their attack damage is doubled onto the player!!");

        }

        int hitPoints = incomingAttack;
        System.out.println(hitPoints);
        return hitPoints;

    }

    public int determineEnemyCrit(Special attack){
        double random = Math.random();
        System.out.println(random);
        int incomingAttack = attack.getValue();
        lblCritical.setText(" ");
        if (random <= player.getCritChance()) {
            incomingAttack = attack.getValue() * 2;
            lblCritical.setText(opponent.getName() + " has landed a critical hit!\n Their attack damage is doubled onto the player!!");
        }

        int hitPoints = incomingAttack;
        System.out.println(hitPoints);
        return hitPoints;

    }

    @FXML
    public void enforceFireStorm(Battle enemy) {
        lblInGameAction.setText("Current Attack: " + player.getName()+ " casts " + firestorm.getName() + " on " + enemy.getName() + ".");

        if(player.checkEnergy(firestorm.getEnergyRequirement())) {

            enemy.checkDamage(determinePlayerCrit(firestorm));
            lblDamageMessage.setText(enemy.getName() + " is damaged by " + determinePlayerCrit(firestorm) + " points\n");
            lblEnergy.setText(player.getName() + " has used " + firestorm.getEnergyRequirement() + " energy point.");

        }
    }

    @FXML
    public void healUser() {

        lblInGameAction.setText("Current Attack: " + player.getName() + " casts " + flowerHealing.getName() + ".");
        if(player.checkEnergy(flowerHealing.getEnergyRequirement())) {

            player.addHealth(flowerHealing.getValue());
            player.addEnergy(flowerHealing.getValue() + 2);
            gameUpdater();
            disableOptions();
            buttonEnemy.setDisable(false);
        }

    }

    public void stunOpponent(Battle enemy) {
        lblInGameAction.setText("Current Attack: " + player.getName() + " casts " + stunner.getName() + " on " + enemy.getName() + ".");
        if(player.checkEnergy(stunner.getEnergyRequirement())) {

            enemy.checkDamage(determinePlayerCrit(stunner));
            enemy.stunned = true;
            lblDamageMessage.setText(enemy.getName() + " is all out of sorts and stunned! It is damaged by " + determinePlayerCrit(stunner) + " points");
            lblEnergy.setText(player.getName() + " has used " + stunner.getEnergyRequirement() + " energy point.");
        }
    }

    public void enforceLightningStrike(Battle enemy) {
        lblInGameAction.setText("Current Attack: " + player.getName() + " casts " + lightningStrike.getName() + " on " + enemy.getName() + ".");
        if(player.checkEnergy(lightningStrike.getEnergyRequirement())) {

            enemy.checkDamage(determinePlayerCrit(lightningStrike));
            lblDamageMessage.setText(enemy.getName() + " is damaged by " + determinePlayerCrit(lightningStrike) + " points\n");
            lblEnergy.setText(player.getName() + " has used " + lightningStrike.getEnergyRequirement() + " energy point.");
        }
    }

    public void enforceWaterDragon(Battle enemy) {
        lblInGameAction.setText("Current Attack: " + player.getName() + " casts " + waterDragon.getName() + " on " + enemy.getName() + ".");
        if(player.checkEnergy(waterDragon.getEnergyRequirement())) {
            enemy.checkDamage(determinePlayerCrit(waterDragon));
            lblDamageMessage.setText(enemy.getName() + " is damaged by " + determinePlayerCrit(waterDragon) + " points\n");
            lblEnergy.setText(player.getName() + " has used " + waterDragon.getEnergyRequirement() + " energy point.");

        }
    }

    public void swingIceScythe(Battle enemy) {

        lblInGameAction.setText("Current Attack: " + player.getName() + " swings the " + iceScythe.getName() + " on " + enemy.getName() + ".");
        if(player.checkEnergy(iceScythe.getEnergyRequirement())) {

            enemy.checkDamage(determinePlayerCrit(iceScythe));
            lblDamageMessage.setText(enemy.getName() + " is damaged by " + determinePlayerCrit(iceScythe) + " points\n");
            lblEnergy.setText(player.getName() + " has used " + iceScythe.getEnergyRequirement() + " energy point.");

        }
    }

    public void enforceDreamInvader(Battle enemy) {
        lblInGameAction.setText("Current Attack: " + player.getName() + " casts " + dreamInvader.getName() + " on " + enemy.getName() + ".");
        if(player.checkEnergy(dreamInvader.getEnergyRequirement())) {

            enemy.checkDamage(determinePlayerCrit(dreamInvader));

            lblDamageMessage.setText(enemy.getName() + " is damaged by " + determinePlayerCrit(dreamInvader) + " points\n");
            lblEnergy.setText(player.getName() + " has used " + dreamInvader.getEnergyRequirement() + " energy point.");

        }
    }

    public void chooseDarkMagic(Battle enemy) {
        lblInGameAction.setText("Current Attack: " + player.getName() + " casts " + darkMagic.getName() + " on " + enemy.getName() + ".");
        if(player.checkEnergy(darkMagic.getEnergyRequirement())) {

            enemy.checkDamage(determinePlayerCrit(darkMagic));
            lblDamageMessage.setText(enemy.getName() + " is damaged by " + determinePlayerCrit(darkMagic) + " points\n");

            lblEnergy.setText(player.getName() + " has used " + darkMagic.getEnergyRequirement() + " energy point.");
        }
    }

    public void throwHarmPotion(Battle enemy){
        lblInGameAction.setText("Current Attack: " + player.getName() + " casts " + harmPotion.getName() + " on " + enemy.getName() + ".");
        if(player.checkEnergy(harmPotion.getEnergyRequirement())) {
            enemy.checkDamage(determinePlayerCrit(harmPotion));
            lblDamageMessage.setText(enemy.getName() + " is damaged by " + determinePlayerCrit(harmPotion) + " points\n");

            lblEnergy.setText(player.getName() + " has used " + harmPotion.getEnergyRequirement() + " energy point.");
        }
    }

    public void drinkBeserkPotion(Battle enemy){
        lblInGameAction.setText("Current Attack: " + player.getName() + " casts " + beserkPotion.getName() + " on " + enemy.getName() + ".");
        if(player.checkEnergy(beserkPotion.getEnergyRequirement())) {
            enemy.checkDamage(determinePlayerCrit(beserkPotion));
            lblDamageMessage.setText(enemy.getName() + " is damaged by " + determinePlayerCrit(beserkPotion) + " points\n");

            lblEnergy.setText(player.getName() + " has used " + beserkPotion.getEnergyRequirement() + " energy point.");
        }
    }

    @FXML
    public void chooseHarmPotion(){
        throwHarmPotion(opponent);
        gameUpdater();
        disableOptions();
        buttonEnemy.setDisable(false);
    }
    @FXML
    public void chooseBeserkPotion(){
        drinkBeserkPotion(opponent);
        gameUpdater();
        disableOptions();
        buttonEnemy.setDisable(false);
    }

    @FXML
    public void chooseFireStorm(){
        enforceFireStorm(opponent);
        gameUpdater();
        disableOptions();
        buttonEnemy.setDisable(false);
    }

    @FXML
    public void chooseStunner(){
        stunOpponent(opponent);
        gameUpdater();
        ///cannot keep repeating the stunning effect for multiple turns
        buttonAttack4.setDisable(true);
    }
    @FXML
    public void chooseLightningStrike(){

        enforceLightningStrike(opponent);
        gameUpdater();
        disableOptions();
        buttonEnemy.setDisable(false);
    }
    @FXML
    public void chooseWaterDragon(){
        enforceWaterDragon(opponent);
        gameUpdater();
        disableOptions();
        buttonEnemy.setDisable(false);
    }

    @FXML
    public void chooseIceScythe(){
        swingIceScythe(opponent);
        gameUpdater();
        disableOptions();
        buttonEnemy.setDisable(false);
    }
    @FXML
    public void chooseDreamInvader(){
        enforceDreamInvader(opponent);
        gameUpdater();
        disableOptions();
        buttonEnemy.setDisable(false);
    }

    @FXML
    public void chooseDarkMagic(){
        chooseDarkMagic(opponent);
        gameUpdater();
        disableOptions();
        buttonEnemy.setDisable(false);
    }

    @FXML
    public void enemyAttack(){
        enemyOutput(opponent);
        gameUpdater();
        enableOptions();

    }

    public void enemyOutput(Battle monster) {

        if(monster.isStunned()) {
            lblDamageMessage.setText(monster.getName() + " is bambooozled and can't cast skills this round\n");
            return;
        }
        if (monster.currentEnergy == 0) {
            monster.lifeActual = 0;
            lblInGameAction.setText(monster.getName() + " gives up because he has no energy.");
            return;
        }

        int randAttack;
        while(true) {

            randAttack = ThreadLocalRandom.current().nextInt(0, 5);
            if((randAttack == 1 && flowerHealing.getEnergyRequirement() > monster.lifeActual)
                    || (randAttack == 1 && monster.lifeMax == monster.lifeActual)
                    || (randAttack == 2 && stunner.getEnergyRequirement() > monster.currentEnergy)
                    || (randAttack == 3 && lightningStrike.getEnergyRequirement() > monster.currentEnergy)) {
                continue;
            } else {
                break;
            }
        }

        // cast the skill
        if(randAttack == 0) {

            lblInGameAction.setText("Current Attack: " + monster.getName()+ " casts " + firestorm.getName() + " on " + player.getName() + ".");
            if(monster.checkEnergy(firestorm.getEnergyRequirement())) {
                player.checkDamage(determineEnemyCrit(firestorm));
                lblDamageMessage.setText(player.getName() + " is damaged by " + determineEnemyCrit(firestorm) + " points\n");

                lblEnergy.setText(monster.getName() + " has used " + firestorm.getEnergyRequirement() + " energy point.");

            }


        } else if (randAttack == 1) {

            lblInGameAction.setText("Current Attack: " + monster.getName() + " casts " + flowerHealing.getName() + ".");
            if(monster.checkEnergy(flowerHealing.getEnergyRequirement())) {
                monster.addHealth(flowerHealing.getValue());
                monster.addEnergy(flowerHealing.getValue() + 2);
                lblEnergy.setText(monster.getName() + " has used " + flowerHealing.getEnergyRequirement() + " energy point.");
                gameUpdater();

            }

        } else if (randAttack == 2) {

            lblInGameAction.setText("Current Attack: " + monster.getName() + " casts " + stunner.getName() + " on " + player.getName() + ".");
            if(monster.checkEnergy(stunner.getEnergyRequirement())) {

                player.checkDamage(determineEnemyCrit(stunner));
                lblDamageMessage.setText(player.getName() + " is bamboozled by " + determineEnemyCrit(stunner) + " points\n");

                player.stunned = true;
                lblEnergy.setText(monster.getName() + " has used " + stunner.getEnergyRequirement() + " energy point.");

            }

        } else if (randAttack == 3) {

            lblInGameAction.setText("Current Attack: " + monster.getName()+ " casts " + lightningStrike.getName() + " on " + player.getName() + ".");
            if(monster.checkEnergy(lightningStrike.getEnergyRequirement())) {
                player.checkDamage(determineEnemyCrit(lightningStrike));
                lblDamageMessage.setText(player.getName() + " is damaged by " + determineEnemyCrit(lightningStrike) + " points\n");

                lblEnergy.setText(monster.getName() + " has used " + lightningStrike.getEnergyRequirement() + " energy point.");

            }
        }  else if (randAttack == 4) {

                lblInGameAction.setText("Current Attack: " + monster.getName()+ " casts " + waterDragon.getName() + " on " + player.getName() + ".");
                if(monster.checkEnergy(waterDragon.getEnergyRequirement())) {
                    player.checkDamage(determineEnemyCrit(waterDragon));
                    lblDamageMessage.setText(player.getName() + " is damaged by " + determineEnemyCrit(waterDragon) + " points\n");
                    lblEnergy.setText(monster.getName() + " has used " + waterDragon.getEnergyRequirement() + " energy point.");
                }
    }
    }

    public void gameUpdater(){

        lblStart.setText(player.getName() + " fights against " + opponent.getName()+ "\n");
        if(player.getLife() > 0 && opponent.getLife() > 0) {

            lblPlayerStats.setText(player.getName() + " Stats: "+ "\n" + "Current Health: " + player.getLife() + " | Start Health: " + player.getLifeMax()+ "\n"
                    + "Current Energy: " + player.getEnergy() + " | Start Energy: " +  player.getStartEnergy()+ "\n");

            lblEnemyStats.setText(opponent.getName() + " Stats: "+ "\n" + "Current Health: "
                    +  opponent.getLife()+ " | Start Health: " + opponent.getLifeMax()+ "\n" +
                    "Current Energy " + opponent.getEnergy()+ " Start Energy: " + opponent.getStartEnergy()+ "\n");

        }
        battle();
    }

    public boolean battle() {

        if(player.getLife() > 0 && opponent.getLife() <= 0) {
            lblVictory.setText("VICTORY!!\n" + "PREPARE FOR THE NEXT ROUND OF FIGHTING!\n");
            opponent.increaseStats(1.2);
            player.increaseStats();
            ///updates score and round after winning
            score += 5;
            rounds++;
            ///display
            lblRounds.setText("ROUND # ----> " +   String.valueOf(rounds));
            lblPlayerScore.setText("PLAYER SCORE: " + String.valueOf(score));
            buttonShop.setVisible(true);
            lblMoneyBalance.setVisible(true);
            if(player.getLife() >= 20){
                player.moneyBalance += 10;
                lblBoost.setText(" You have recieved a + 10$ bonus");
            }else if(player.getLife() >= 15){
                player.moneyBalance += 6;
                lblBoost.setText(" You have recieved a + 6$ bonus");
            }else if(player.getLife() >= 10){
                player.moneyBalance += 3;
                lblBoost.setText(" You have recieved a + 3$ bonus");
            }

            lblMoneyBalance.setText("Money Balance: " + player.getMoneyBalance()+  "$");
            enableOptions();
            buttonAttack1.setVisible(false);
            buttonAttack2.setVisible(false);
            buttonAttack3.setVisible(false);
            buttonAttack4.setVisible(false);
            buttonAttack5.setVisible(false);
            btnDarkMagicAttack.setVisible(false);
            btnDreamInvaderAttack.setVisible(false);
            btnIceScytheAttack.setVisible(false);
            btnHarmPotionAttack.setVisible(false);
            btnBeserkPotionAttack.setVisible(false);
            buttonEnemy.setVisible(false);
            lblInGameAction.setVisible(false);
            lblEnemyStats.setVisible(false);
            lblPlayerStats.setVisible(false);
            lblScoreboard.setVisible(false);
            lblStart.setVisible(false);
            lblRounds.setVisible(false);
            lblPlayerScore.setVisible(false);
            imgBattleBackground.setVisible(false);
            lblCritical.setVisible(false);
            buttonShop.setVisible(true);
            buttonContinueGame.setVisible(true);
            lblDamageMessage.setVisible(false);
            lblEnergy.setVisible(false);
            lblPurchaseInfo.setVisible(true);
            imgBattleBackground.setVisible(true);
            return true;

        } else if(player.getLife()<=0 && opponent.getLife() >=0) {
            rounds++;
            disableOptions();
            endScreen();
            return false;

        }
        else if(rounds==2){
            lblCongratulations.setText("CONGRATULATIONS! YOU HAVE COMPLETED ALL 5 LEVELS!!!");
            winScreen();
            return false;
        }else{
            return true;
        }
    }
    ///enables buttons so user can use them after enemy turn
    public void enableOptions(){

        buttonAttack1.setDisable(false);
        buttonAttack2.setDisable(false);
        buttonAttack3.setDisable(false);
        buttonAttack4.setDisable(false);
        buttonAttack5.setDisable(false);
        btnIceScytheAttack.setDisable(false);
        btnDreamInvaderAttack.setDisable(false);
        btnDarkMagicAttack.setDisable(false);
        btnHarmPotionAttack.setDisable(false);
        btnBeserkPotionAttack.setDisable(false);
        buttonEnemy.setDisable(true);
    }

    ///disables the buttons and waits for enemy's turn
    public void disableOptions() {
        buttonAttack1.setDisable(true);
        buttonAttack2.setDisable(true);
        buttonAttack3.setDisable(true);
        buttonAttack4.setDisable(true);
        buttonAttack5.setDisable(true);
        btnIceScytheAttack.setDisable(true);
        btnDreamInvaderAttack.setDisable(true);
        btnDarkMagicAttack.setDisable(true);
        btnHarmPotionAttack.setDisable(true);
        btnBeserkPotionAttack.setDisable(true);

    }
    @FXML
    public void start() {
        gameUpdater();
        loadImages();
        lblScoreboard.setVisible(true);
        startButton.setVisible(false);
        buttonAttack1.setDisable(false);
        buttonAttack2.setDisable(false);
        buttonAttack3.setDisable(false);
        buttonAttack4.setDisable(false);
        buttonAttack5.setDisable(false);
        buttonEnemy.setDisable(false);

    }

    @FXML
    public void showUpgradeShop(){
        shopIntroduction.setText(" Welcome To The Upgrade Shop!");

        try {
            FileInputStream input = new FileInputStream("C:\\Users\\pradh\\IdeaProjects\\battleProj\\src\\main\\resources\\pics\\shopBackground.jpg");
            imgBattleBackground.setImage(new Image(input));
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
        try {
            FileInputStream input = new FileInputStream("C:\\Users\\pradh\\IdeaProjects\\battleProj\\src\\main\\resources\\pics\\harmPotion.jpg");
            imgSpecialAttack.setImage(new Image(input));
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
        try {
            FileInputStream input = new FileInputStream("C:\\Users\\pradh\\IdeaProjects\\battleProj\\src\\main\\resources\\pics\\specialAttack.png");
            imgPotion.setImage(new Image(input));
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
        lblVictory.setVisible(false);
        btnBeserkPotion.setVisible(true);
        btnHarmPotion.setVisible(true);
        btnDreamInvader.setVisible(true);
        btnDarkMagic.setVisible(true);
        btnIceScythe.setVisible(true);
        buttonContinueGame.setVisible(true);
    }

    @FXML
    public void buyIceScythe(){
        if(player.getMoneyBalance() >= iceScythe.getPurchasePrice() ) {
            player.moneyBalance -= iceScythe.getPurchasePrice();
            attacks.add(iceScythe);
            lblMoneyBalance.setText("Money Balance: " + player.getMoneyBalance());
            lblPurchaseInfo.setText(" You have just purchased the strong ice scythe that can cut just about anything!");
            btnIceScythe.setDisable(true);
        }
        else{
            btnIceScythe.setDisable(true);
            lblPurchaseInfo.setText("You do not have the funds necessary to buy this attack");
        }
    }
    @FXML
    public void buyDarkMagic(){
        if(player.getMoneyBalance() >= darkMagic.getPurchasePrice()) {
            player.moneyBalance -= darkMagic.getPurchasePrice();
            attacks.add(darkMagic);
            lblMoneyBalance.setText("Money Balance: " + player.getMoneyBalance());
            lblPurchaseInfo.setText(" You have just purchased dark Magic, an unknown entity that opponents struggle to deal with!");
            btnDarkMagic.setDisable(true);
        }
        else{
            btnDarkMagic.setDisable(true);
            lblPurchaseInfo.setText("You do not have the funds necessary to buy this attack");
        }
    }
    @FXML
    public void buyDreamInvader(){
        if(player.getMoneyBalance() >= dreamInvader.getPurchasePrice()) {
            attacks.add(dreamInvader);
            player.moneyBalance -= dreamInvader.getPurchasePrice();
            lblMoneyBalance.setText("Money Balance: " + player.getMoneyBalance());
            lblPurchaseInfo.setText(" The dream invader attack stuns your opponent and allows you to attack them with no defense!");
            btnDreamInvader.setDisable(true);
        }
        else{
            btnDreamInvader.setDisable(true);
            lblPurchaseInfo.setText("You do not have the funds necessary to buy this attack");
        }
    }
    @FXML
    public void buyHarmingPotion() {

        if (player.getMoneyBalance() >= harmPotion.getPurchasePrice()) {
            attacks.add(harmPotion);
            player.moneyBalance -= iceScythe.getPurchasePrice();
            lblMoneyBalance.setText("Money Balance: " + player.getMoneyBalance());
            lblPurchaseInfo.setText(" You have just purchased the deadly harming potion!");
            btnHarmPotion.setDisable(true);
        }else{
            btnHarmPotion.setDisable(true);
            lblPurchaseInfo.setText("You do not have the funds necessary to buy this attack");
        }
    }

    @FXML
    public void buyBeserkPotion() {
        if (player.getMoneyBalance() >= beserkPotion.getPurchasePrice()) {
            attacks.add(beserkPotion);
            player.moneyBalance -= beserkPotion.getPurchasePrice();
            lblMoneyBalance.setText("Money Balance: " + player.getMoneyBalance());
            lblPurchaseInfo.setText(" You have just purchased the beserk potion!");
            player.critChance += 0.2;
            btnBeserkPotion.setDisable(true);
        }else{
            lblPurchaseInfo.setText("You do not have the funds necessary to buy this attack");
            btnBeserkPotion.setDisable(true);
        }
    }

    @FXML
    public void moveToNextRound(){

        btnIceScythe.setVisible(false);
        lblVictory.setVisible(false);
        btnDreamInvader.setVisible(false);
        btnDarkMagic.setVisible(false);
        btnHarmPotion.setVisible(false);
        btnBeserkPotion.setVisible(false);
        lblPurchaseInfo.setText(" ");
        buttonAttack1.setVisible(true);
        buttonAttack2.setVisible(true);
        buttonAttack3.setVisible(true);
        buttonAttack4.setVisible(true);
        buttonAttack5.setVisible(true);
        btnIceScytheAttack.setDisable(true);
        btnDarkMagicAttack.setDisable(true);
        btnDreamInvaderAttack.setDisable(true);
        btnHarmPotionAttack.setDisable(true);
        buttonEnemy.setVisible(true);
        imgPotion.setVisible(false);
        imgSpecialAttack.setVisible(false);
        lblDamageMessage.setVisible(true);
        lblEnergy.setVisible(true);
        lblInGameAction.setVisible(true);
        lblEnemyStats.setVisible(true);
        lblPlayerStats.setVisible(true);
        lblScoreboard.setVisible(true);
        lblStart.setVisible(true);
        lblRounds.setVisible(true);
        lblPlayerScore.setVisible(true);
        shopIntroduction.setText(" ");
        imgBattleBackground.setVisible(true);
        buttonShop.setVisible(false);
        buttonContinueGame.setVisible(false);
        loadImages();
        if(attacks.contains(iceScythe)){
            btnIceScytheAttack.setVisible(true);
        }
        if(attacks.contains(dreamInvader)){
            btnDreamInvaderAttack.setVisible(true);
        }
        if(attacks.contains(darkMagic)){
            btnDarkMagicAttack.setVisible(true);
        }
        if(attacks.contains(harmPotion)){
            btnHarmPotionAttack.setVisible(true);
        }

        if(attacks.contains(beserkPotion)){
            btnBeserkPotionAttack.setVisible(true);
        }
        if(rounds ==1){
            opponent = listVillians[0];
        }else if(rounds ==2){
            opponent = listVillians[1];
        }
        else if(rounds ==3){
            opponent = listVillians[2];
        }
        else if(rounds ==4){
            opponent = listVillians[3];
        }
        else if(rounds ==5){
            opponent = listVillians[4];
        }

        gameUpdater();
        battle();

    }
    @FXML
    public void winScreen(){
        buttonAttack1.setVisible(false);
        buttonAttack2.setVisible(false);
        buttonAttack3.setVisible(false);
        buttonAttack4.setVisible(false);
        buttonAttack5.setVisible(false);
        buttonEnemy.setVisible(false);
        lblInGameAction.setVisible(false);
        lblEnemyStats.setVisible(false);
        lblPlayerStats.setVisible(false);
        lblScoreboard.setVisible(false);
        lblStart.setVisible(false);
        lblRounds.setVisible(true);
        lblPlayerScore.setVisible(true);
        buttonShop.setVisible(false);
        buttonContinueGame.setVisible(false);
        lblDamageMessage.setVisible(false);
        lblEnergy.setVisible(false);
        lblPurchaseInfo.setVisible(false);
        try {
            FileInputStream input = new FileInputStream("C:\\Users\\pradh\\IdeaProjects\\battleProj\\src\\main\\resources\\pics\\Win.jpg");
            imgBattleBackground.setImage(new Image(input));
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
        lblFinalStats.setText("You completed all 5 levels of battle and scored a grand total of " + score +" points!" + "\n You did a great job!" );

    }

    @FXML
    public void saveStats(){
        String outfile = "C:\\Users\\pradh\\IdeaProjects\\battleProj\\src\\main\\resources\\com\\example\\uuuu\\Stats.txt";

        try{
            PrintWriter out = new PrintWriter(outfile);
            out.println(player.getEnergy());
            out.println(player.getLife());
            out.println(player.getMoneyBalance());
            out.println(opponent.getEnergy());
            out.println(opponent.getLife());
            out.println(opponent.getName());
            out.close();

        }
        catch (FileNotFoundException var3){
            System.out.println("No file");
        }

        lblSave.setText(" You have saved your progress!");
    }

    @FXML
    public void loadStats(){

        ArrayList<Integer> stats = new ArrayList<>();
        FileReader reader2;
        Scanner in2;
        try{
            reader2 = new FileReader("C:\\Users\\pradh\\IdeaProjects\\battleProj\\src\\main\\resources\\com\\example\\uuuu\\Stats.txt");
            in2 = new Scanner(reader2);

             while(in2.hasNextInt()) {
                 System.out.println("ccccc");
                 stats.add(in2.nextInt());


             }
        }
        catch (FileNotFoundException ex) {
        System.out.println("Something is very wrong 2");
    }

        player.currentEnergy = stats.get(0);
        player.lifeActual = stats.get(1);
        player.moneyBalance = stats.get(2);
        opponent.currentEnergy = stats.get(3);
        opponent.lifeActual = stats.get(4);
        start();
        enableOptions();

    }

    @FXML
    public void endScreen(){

        buttonAttack1.setVisible(false);
        buttonAttack2.setVisible(false);
        buttonAttack3.setVisible(false);
        buttonAttack4.setVisible(false);
        buttonAttack5.setVisible(false);
        btnDarkMagicAttack.setVisible(false);
        btnDreamInvaderAttack.setVisible(false);
        btnIceScytheAttack.setVisible(false);
        btnHarmPotionAttack.setVisible(false);
        btnBeserkPotionAttack.setVisible(false);
        buttonEnemy.setVisible(false);
        lblInGameAction.setVisible(false);
        lblEnemyStats.setVisible(false);
        lblPlayerStats.setVisible(false);
        lblScoreboard.setVisible(false);
        lblStart.setVisible(false);
        lblRounds.setVisible(false);
        lblPlayerScore.setVisible(false);
        imgBattleBackground.setVisible(true);
        buttonShop.setVisible(false);
        buttonContinueGame.setVisible(false);
        lblDamageMessage.setVisible(false);
        lblEnergy.setVisible(false);
        lblPurchaseInfo.setVisible(false);
        lblMoneyBalance.setVisible(false);
        btnIceScytheAttack.setVisible(false);
        btnDreamInvaderAttack.setVisible(false);
        try {
            FileInputStream input = new FileInputStream("C:\\Users\\pradh\\IdeaProjects\\battleProj\\src\\main\\resources\\pics\\gameOver.png");
            imgBattleBackground.setImage(new Image(input));
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }

        lblFinalStats.setText("You completed " + level + " levels of battle and scored a grand total " +
                "of " + score +" points!" + "\n Better luck next time!" );
    }

}


