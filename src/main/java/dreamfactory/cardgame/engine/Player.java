package dreamfactory.cardgame.engine;

import dreamfactory.cardgame.cards.HearthstoneCard;
import dreamfactory.cardgame.cards.MinionCard;
import dreamfactory.cardgame.cards.WeaponCard;

public class Player implements Attackable {
    private String playerName;
    private int attack = 0;
    private int maxAttacks = 0;
    private int remainingAttacks = 0;
    private int health = 30;
    private int manaPool = 0;
    private int remainingMana = 0;
    private WeaponCard weapon = null;
    private Deck deck;
    private Hand hand;
    private Board board;

    public Player(String name, Deck deck) {
        this.playerName = name;
        this.deck = deck;
        this.board = new Board();
        this.hand = new Hand();
    }

    @Override
    public void attack(Attackable target) {
        if (hasWeapon()){
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
        health -= damage;
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
        //mozda dobit string i njega printat... pa nazvat printhand
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
        //isto ko za view hand
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
