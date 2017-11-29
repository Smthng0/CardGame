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
    private WeaponCard weapon = null;
    private Deck deck;
    private Hand hand;
    private Board board;

    public Player(String name, Deck deck, boolean playsFirst) {
        this.playerName = name;
        this.deck = deck;
        this.board = new Board();
        this.hand = new Hand(playsFirst);
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

    @Override
    public int getHealth() {
        return health;
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

    public WeaponCard getWeapon() {
        return weapon;
    }

    public void setWeapon(WeaponCard weapon) {
        this.weapon = weapon;
    }

    public boolean fullHand() {
        return (hand.getNumberOfCards() >= hand.getLimit());
    }

    public HearthstoneCard drawCard() {
        HearthstoneCard card = deck.drawCard();
        hand.addCard(card);
        return card;
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

    public void removeCard(int index) {
        hand.removeCard(index);
    }

    public void killMinion(int index) {
        board.removeMinion(index);
    }

    public int getNumberOfMinions() {
        return board.getNumberOfMinions();
    }

    public boolean hasMinions() {
        return !board.isEmpty();
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


    public void resetAttacks() {
        board.resetAttacks();
        remainingAttacks = maxAttacks;
    }

    public void summonMinion(MinionCard mirror_image) {
        board.summonMinion(mirror_image);
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }
}
