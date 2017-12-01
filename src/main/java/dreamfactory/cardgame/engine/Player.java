package dreamfactory.cardgame.engine;

import dreamfactory.cardgame.cards.HearthstoneCard;
import dreamfactory.cardgame.cards.MinionCard;
import dreamfactory.cardgame.cards.WeaponCard;

public class Player extends Attackable {
    private String playerName;
    private int manaPool = 0;
    private int remainingMana = 0;
    private WeaponCard weapon = null;
    private Deck deck;
    private Hand hand;
    private Board board;

    public Player(String name, Deck deck) {
        this.playerName = name;
        this.deck = deck;
        this.maxAttacks = 0;
        this.board = new Board();
        this.hand = new Hand();
    }

    public boolean hasWeapon() {
        return weapon != null;
    }

    public String getPlayerName() {
        return playerName;
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

    public void equipWeapon(WeaponCard weapon) {
        this.weapon = weapon;
        this.attack = weapon.getAttack();
        this.maxAttacks = weapon.getDurability();
    }

    public boolean isHandFull() {
        return (hand.isFull());
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
        // TODO: mozda dobit string i njega printat... pa nazvat printhand
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

    public MinionCard getMinion(int index){
        return board.getMinion(index);
    }

    public void viewBoard() {
        //isto ko za view hand
    }

    public void summonMinion(MinionCard mirror_image) {
        board.summonMinion(mirror_image);
    }
}
