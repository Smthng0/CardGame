package dreamfactory.cardgame.player;

import dreamfactory.cardgame.cards.*;
import dreamfactory.cardgame.cards.spells.ManaCookie;

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

    public void startsSecond() {
        hand.addCard(new ManaCookie());
    }

    public boolean equipWeapon(WeaponCard weapon) {
        if (hasWeapon()){
            return false;
        }
        this.weapon = weapon;
        this.attack = weapon.getAttack();
        this.resetAttacks();
        return true;
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
        return weapon.hasAbility(Ability.EXTRA_ATTACK);
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

    public String viewManaPool() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < getManaPool(); i++) {
            result.append("");
        }
        return result.toString();
    }

    public void setManaPool(int manaPool) {
        this.manaPool = manaPool;
    }

    public String viewRemainingMana() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < getRemainingMana(); i++) {
            result.append("•");
        }
        if (result.length() == 0) {
            return "No mana!";
        }
        return result.toString();
    }

    public int getRemainingMana() {
        return remainingMana;
    }


    public void setRemainingMana(int remainingMana) {
        this.remainingMana = remainingMana;
    }

    public void incrementRemainingMana() {
        remainingMana++;
    }

    private boolean checkMana(Card card) {
        return (card.getManaCost() <= getRemainingMana());
    }

    public Card drawCard() {
        Card card = deck.drawCard();
        hand.addCard(card);
        return card;
    }

    public int getDeckDmgCounter() {
        return deck.getDmgCounter();
    }

    public Card playCard(int index){
        Card card = hand.getCard(index);

        if (!cardPlayed(card)) return null;

        remainingMana -= card.getManaCost();
        hand.removeCard(index);

        //TODO: something with spellcard
        return card;
    }

    private boolean cardPlayed(Card card) {
        if (card == null) return false;
        if (!checkMana(card)) return false;

        if (card instanceof MinionCard){
            return board.summonMinion((MinionCard) card);
        } else if (card instanceof WeaponCard) {
            return equipWeapon((WeaponCard) card);
        } else if (card instanceof SpellCard) {
            return ((SpellCard) card).effect(this);
        }

        return true;
    }

    public void removeCard(int index) {
        if (validIndex(index)){
            hand.removeCard(index);
        }
    }

    public void addCard(Card card) {
        hand.addCard(card);
    }

    public boolean validIndex(int index) {
        return ((index >= 0) && (index < hand.getNumberOfCards()));
    }

    public int getNumberOfCards () {
        return hand.getNumberOfCards();
    }

    public boolean isHandFull() {
        return (hand.isFull());
    }

    public String viewHand() {
        return hand.asString();
    }

    public String viewPlayableCards() {
        return hand.asStringPlayable(remainingMana);
    }

    public boolean hasPlayableCards() {
        return hand.hasPlayableCards(remainingMana);
    }

    public String viewBoard() {
        return board.asString();
    }

    public String viewBoardWithTaunt() {
        return board.asStringWithTaunt();
    }

    public String viewBoardCanAttack() {
        return board.asStringCanAttack();
    }

    public void summonMinion(MinionCard card) {
        board.summonMinion(card);
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

    public boolean hasTauntMinion() {
        return board.hasTauntMinion();
    }

    public boolean hasAttackableMinion() {
        return board.hasAttackableMinion();
    }

    public String getPlayerName() {
        return playerName;
    }

    public WeaponCard getWeapon() {
        return weapon;
    }

    public Deck getDeck() {
        return deck;
    }

    public Hand getHand() {
        return hand;
    }

    public Board getBoard() {
        return board;
    }
}
