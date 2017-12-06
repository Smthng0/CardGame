package dreamfactory.cardgame.player;

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

        for (int i = 0; i < 3; i++) {
            this.drawCard();
        }
    }

    public void equipWeapon(WeaponCard weapon) {
        this.weapon = weapon;
        this.attack = weapon.getAttack();
        this.resetAttacks();
    }

    private void destroyWeapon() {
        if (hasWeapon()) {
            this.weapon = null;
            this.attack = 0;
            this.remainingAttacks = 0;
        }
    }

    @Override
    public void attack(Attackable target) {
        super.attack(target);
        weapon.setDurability(weapon.getDurability()-1);

        if (weapon.getDurability() == 0) {
            destroyWeapon();
        }
    }

    @Override
    public boolean hasWindfury() {
        return weapon.hasAbility(Ability.WINDFURY);
    }

    @Override
    public String getName() {
        return getPlayerName();
    }

    @Override
    public String asString() {
        String result = (playerName +
                ", Health: " + health);
        if (canAttack()) {
            result += ", Remaining Attacks: " + remainingAttacks;
        }
        if (hasWeapon()) {
            result += ", Weapon Equipped: \n" + weapon.asString();
        } else {
            result += "\n";
        }
        return result;
    }

    @Override
    public void resetAttacks() {
        if (!hasWeapon()){
            return;
        }

        super.resetAttacks();
        if (hasWindfury() && weapon.getDurability() == 1) {
            remainingAttacks = 1;
        }
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

    private boolean checkMana(HearthstoneCard card) {
        return (card.getManaCost() <= getRemainingMana());
    }

    public HearthstoneCard drawCard() {
        HearthstoneCard card = deck.drawCard();
        hand.addCard(card);
        return card;
    }

    public boolean playCard(int index){
        HearthstoneCard card = hand.getCard(index);

        if (!checkMana(card)){
           return false;
        }

        hand.removeCard(index);
        remainingMana -= card.getManaCost();

        if (card instanceof MinionCard){
            board.summonMinion((MinionCard)card);
        } else if (card instanceof WeaponCard){
            equipWeapon((WeaponCard)card);
        }

        if (card.hasAbility(Ability.ADD_MANA)){
            remainingMana++;
        }

        //TODO: something with spellcard
        return true;
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

    public String viewHand() {
        // TODO: mozda dobit string i njega printat... pa nazvat printhand
        return null;
    }

    public String viewBoard() {
        //isto ko za view hand
        return null;
    }

    public void summonMinion(MinionCard mirror_image) {
        board.summonMinion(mirror_image);
    }

    public void resetMinionAttacks() {
        board.resetAttacks();
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
