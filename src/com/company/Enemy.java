package com.company;

public class Enemy {
    public static final String purple = "\u001B[35m";
    public static final String red = "\u001B[31m";
    public static final String blue = "\u001B[34m";
    public static final String bold = "\u001B[1m";
    public static final String italic = "\033[3m";
    public static final String reset = "\u001B[0m";
    protected String name;
    protected int health;
    protected int attack;
    protected String description;
    // stores all the possible enemies and their info (useful for hashmaps later)
    private static Enemy[] possibleEnemies = {
            new Enemy("Enemy", 10*Floor.floorLevel, Floor.floorLevel, """
            Enemies are people who have been corrupted by the pollution"""),
            new Vampire("Vampire", 15*Floor.floorLevel, 3*Floor.floorLevel, """
                    Vampires are creatures that suck your blood
                    They steal your hp and heal themselves
                    (scaled from your hp)"""),
            new Golem("Golem", 20*Floor.floorLevel, 2*Floor.floorLevel, 5*Floor.floorLevel, """
                    Golems are creatures made of rock and stone that have become sentient due to the pollution
                    They have strong defence, so attacks will deal less damage than usual
                    (scaled from golem defence)"""),
            new Troll("Troll", 5*Floor.floorLevel, 2*Floor.floorLevel, """
                    Trolls are mischievous mythical creatures that love to play tricks
                    Trolls will steal an item from your inventory when they die, so equip any items you want to keep safe"""),
            new Dragon("Dragon", 50*Floor.floorLevel, 10*Floor.floorLevel, 10*Floor.floorLevel, """
                    Dragons are extremely powerful creatures
                    Dragons have lots of health, attack, and defence, so they're difficult to defeat
                    However, once defeated, you can gain stat bonuses to your health, defence or attack""")
    };

    // method to generate random enemies when creating a floor
    public static Enemy generateRandomEnemy(){
        possibleEnemies = new Enemy[]{
                new Enemy("Enemy", 10 * Floor.floorLevel, Floor.floorLevel, """
                        Enemies are people who have been corrupted by the pollution"""),
                new Vampire("Vampire", 15 * Floor.floorLevel, 3 * Floor.floorLevel, """
                        Vampires are creatures that suck your blood
                        They steal your hp and heal themselves
                        (scaled from your hp)"""),
                new Golem("Golem", 20 * Floor.floorLevel, 2 * Floor.floorLevel, 5 * Floor.floorLevel, """
                        Golems are creatures made of rock and stone that have become sentient due to the pollution
                        They have strong defence, so attacks will deal less damage than usual
                        (scaled from golem defence)"""),
                new Troll("Troll", 5 * Floor.floorLevel, 2 * Floor.floorLevel, """
                        Trolls are mischievous mythical creatures that love to play tricks
                        Trolls will steal an item from your inventory when they die, so equip any items you want to keep safe"""),
                new Dragon("Dragon", 50 * Floor.floorLevel, 10 * Floor.floorLevel, 10 * Floor.floorLevel, """
                        Dragons are extremely powerful creatures
                        Dragons have lots of health, attack, and defence, so they're difficult to defeat
                        However, once defeated, you can gain stat bonuses to your health, defence or attack""")
        };
        int index;
        if (Floor.floorLevel <= 5){
            index = (int) (Math.random() * possibleEnemies.length - 1);
        }
        else{
            index = (int) (Math.random() * possibleEnemies.length);
        }
        return possibleEnemies[index];
    }

    // constructor for creating an enemy
    public Enemy(String name, int health, int attack, String description){
        this.name = name;
        this.health = health;
        this.attack = attack;
        this.description = description;
    }

    // prints out enemies and their descriptions
    public static void enemyInfo(){
        for (Enemy enemy : possibleEnemies){
            System.out.println();
            System.out.println(bold + enemy.name + reset);
            System.out.println(italic + enemy.description + reset);
        }
    }

    // get enemy name
    public String getName(){
        return name;
    }

    // get enemy attack
    public int getAttack(){
        return attack;
    }

    // battling in a floor
    public void battle(Player player, Floor floor){
        health -= player.getAttack();
        System.out.println("You have dealt " + purple + player.getAttack() + " damage " + reset + "to " + name);
        if (health <= 0){
            died(player, floor);
        }
    }

    // dying in a floor (drop random item)
    public void died(Player player, Floor floor){
        System.out.println(name + " has died");
        Item item = Item.generateRandomDrop();
        player.defeatedMonster(item);
        System.out.println(name + " dropped " + item.getName());
        floor.addDeadEnemy(this);
    }

    // battling in a dungeon
    public void battle(Player player, Dungeon dungeon){
        health -= player.getAttack();
        System.out.println("You have dealt " + purple + player.getAttack() + " damage " + reset + "to " + name);
        if (health <= 0){
            died(player, dungeon);
        }
    }

    // when enemy dies in a dungeon, only drop enemy materials
    public void died(Player player, Dungeon dungeon){
        System.out.println(name + " has died");
        player.defeatedMonster(Item.materialDrops[0]);
        System.out.println(name + " dropped " + Item.materialDrops[0].getName());
        dungeon.addDeadEnemy(this);
    }

    // overload toString() method to return the necessary info about enemy
    public String toString(){
        return this.name + " has " + red + this.health + " hp" + reset;
    }
}
