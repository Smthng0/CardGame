package dreamfactory.cardgame.player;

import dreamfactory.cardgame.cards.*;
import dreamfactory.cardgame.cards.spells.TheCoin;
import dreamfactory.cardgame.engine.Engine;

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
        hand.addCard(new TheCoin());
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

    public void setManaPool(int manaPool) {
        this.manaPool = manaPool;
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

    public Card playCard(int index, Engine engine){
        Card card = hand.getCard(index);

        if (!cardPlayed(card, engine)) return null;

        remainingMana -= card.getManaCost();
        hand.removeCard(index);

        //TODO: something with spellcard
        return card;
    }

    private boolean cardPlayed(Card card, Engine engine) {
        if (card == null) return false;
        if (!checkMana(card)) return false;

        if (card instanceof MinionCard){
            if (!board.summonMinion((MinionCard)card)) {
                return false;
            }
        } else if (card instanceof WeaponCard) {
            if (!equipWeapon((WeaponCard)card)) {
                return false;
            }
        } else if (card instanceof SpellCard) {
            ((SpellCard) card).effect(engine);
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
