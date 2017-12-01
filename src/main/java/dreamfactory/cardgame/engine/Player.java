package dreamfactory.cardgame.engine;

import dreamfactory.cardgame.cards.*;

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
        this.board = new Board();
        this.hand = new Hand();
    }

    public void equipWeapon(WeaponCard weapon) {
        this.weapon = weapon;
        this.attack = weapon.getAttack();
        this.resetAttacks();
    }

    public void destroyWeapon() {
        if (hasWeapon()) {
            this.weapon = null;
            this.attack = 0;
            this.remainingAttacks = 0;
        }
    }

    @Override
    public boolean hasWindfury() {
        return weapon.hasAbility(Ability.WINDFURY);
    }

    public boolean hasWeapon() {
        return weapon != null;
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

    public boolean checkMana(HearthstoneCard card) {
        return (card.getManaCost() <= getRemainingMana());
    }

    public HearthstoneCard drawCard() {
        HearthstoneCard card = deck.drawCard();
        hand.addCard(card);
        return card;
    }

    public void playCard(int index){
        HearthstoneCard card = hand.getCard(index);
        if (checkMana(card)){
            hand.removeCard(index);

            String instance = card.getClass().toString();

            if (card instanceof MinionCard){
                board.summonMinion((MinionCard)card);
            } else if (card instanceof WeaponCard){
                equipWeapon((WeaponCard)card);
            }
            //TODO: something with spellcard
        }
    }

    public HearthstoneCard getCard(int index) {
        if (hand.getNumberOfCards() > index){
            return hand.getCard(index);
        }
        return null;
    }

    public void removeCard(int index) {
        hand.removeCard(index);
    }

    public int getNumberOfCards () {
        return hand.getNumberOfCards();
    }

    public boolean isHandFull() {
        return (hand.isFull());
    }

    public void viewHand() {
        // TODO: mozda dobit string i njega printat... pa nazvat printhand
    }

    public void viewBoard() {
        //isto ko za view hand
    }

    public void summonMinion(MinionCard mirror_image) {
        board.summonMinion(mirror_image);
    }

    public MinionCard getMinion(int index){
        return board.getMinion(index);
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

    public String getPlayerName() {
        return playerName;
    }

}
