package card.game;

import card.game.cards.HearthstoneCard;
import card.game.cards.MinionCard;
import card.game.cards.WeaponCard;

public class Player implements Attackable {
    private String playerName;
    private int attack = 0;
    private int armor = 0;
    private int maxAttacks = 0;
    private int remainingAttacks = 0;
    private int health = 30;
    private int manaPool = 0;
    private int remainingMana = 0;
    private Ability classAbility = null;
    private WeaponCard weapon = null;
    private Deck deck;
    private Hand hand;
    private Board board;

    public Player(String name, Deck deck, boolean playsFirst) {
        this.playerName = name;
        this.deck = deck;
        this.board = new Board();
        this.hand = new Hand(deck, playsFirst, this.board);
    }

    @Override
    public void attack(Attackable target) {
        if (this.hasWeapon()){
            if (target == null) {
                System.out.println("No target!");
            } else {
                if (remainingAttacks > 0) {
                    target.takeDamage(this.attack);

                    if (target instanceof MinionCard) {
                        this.takeDamage(target.getAttack());
                    }

                    remainingAttacks--;
                    weapon.setDurability(weapon.getDurability()-1);

                    if (weapon.getDurability() == 0) {
                        weapon = null;
                        remainingAttacks = 0;
                        System.out.println("Weapon used up!");
                    }
                }
            }
        } else {
            System.out.println("No weapon!");
        }
    }

    @Override
    public void takeDamage(int damage){
        if (this.armor < damage) {
            damage -= this.armor;
            this.armor = 0;
            this.health -= damage;
        } else if (this.armor >= damage){
            this.armor -= damage;
        }

        if (this.isDead()){
            System.out.println("");
            System.out.println("I won!!! <3");
            System.out.println("Wooohooo");
            System.out.println(Engine.getFriendlyPlayer().getPlayerName() + " is th3 b3st!!! 3vah!!!!");
            System.out.println("");
            System.out.println("");
            System.out.println("     /(|");
            System.out.println("    (  :");
            System.out.println("   __\\  \\  _____");
            System.out.println(" (____)  `|");
            System.out.println("(____)|   |");
            System.out.println(" (____).__|");
            System.out.println("  (___)__.|_____");
        }
    }

    public void goToGraveyard(int index){
        if ((index < board.getNumberOfMinions())
                && (index >= 0)) {
            board.removeMinion(index);
        }
    }

    @Override
    public int getAttack() {
        return this.attack;
    }

    @Override
    public boolean isDead() {
        return this.health <= 0;
    }

    public boolean hasWeapon() {
        return weapon != null;
    }

    public String getPlayerName() {
        return playerName;
    }

    public Deck getDeck() {
        return deck;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getArmor() {
        return armor;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

    public int getMaxAttacks() {
        return maxAttacks;
    }

    public void setMaxAttacks(int maxAttacks) {
        this.maxAttacks = maxAttacks;
    }

    public int getRemainingAttacks() {
        return remainingAttacks;
    }

    public void setRemainingAttacks(int remainingAttacks) {
        this.remainingAttacks = remainingAttacks;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getManaPool() {
        return manaPool;
    }

    public void setManaPool(int manaPool) {
        this.manaPool = manaPool;
    }

    public int getRemainingMana() {
        return remainingMana;
    }

    public void setRemainingMana(int remainingMana) {
        this.remainingMana = remainingMana;
    }

    public Ability getClassAbility() {
        return classAbility;
    }

    public void setClassAbility(Ability classAbility) {
        this.classAbility = classAbility;
    }

    public WeaponCard getWeapon() {
        return weapon;
    }

    public void setWeapon(WeaponCard weapon) {
        this.weapon = weapon;
    }

    public boolean fullHand() {
        return (hand.getNumberOfCards() >= hand.getLimit());
    }

    public void addCard(HearthstoneCard card) {
        hand.addCard(card);
    }

    public void discardCard(HearthstoneCard card) {
        hand.discardCard(card);
    }

    public HearthstoneCard drawCard() {
        return hand.drawCard();
    }

    public boolean playCard(HearthstoneCard card) {
        if (card.getManaCost() > remainingMana){
            return false;
        }

        remainingMana -= card.getManaCost();
        hand.playCard(card);

        return true;
    }

    public int playCard(String title) {
        if (hand.checkMana(title, remainingMana)) {
            return hand.playCard(title);
        }

        return -1;
    }

    public int playCard(int index) {
        if (hand.checkMana(index, remainingMana)) {
            return hand.playCard(index);
        }

        return -1;
    }

    public HearthstoneCard getCard(int index) {
        if (hand.getNumberOfCards() > index){
            return hand.getCard(index);
        }
        return null;
    }

    public void viewHand() {
        hand.viewHand();
    }

    public void discardCard(int index) {
        hand.discardCard(index);
    }

    public int getNumberOfCards () {
        return hand.getNumberOfCards();
    }

    public int getCardLimit () {
        return hand.getLimit();
    }

    public MinionCard getMinion(int index){
        return board.getMinion(index);
    }

    public void viewBoard() {
        board.printBoard();
    }

}
