package xenoframium.genetics.player;

import xenoframium.ecs.event.*;

/**
 * Created by chrisjung on 24/12/17.
 */
public class Player {
    public static GlobalEventID PLAYER_MONEY_CHANGED = new GlobalEventID();

    private static final int STARTING_MONEY = 100;

    int money = STARTING_MONEY;

    public void addMoney(int profit) {
        money += profit;
        EventBus.post(new GlobalEvent(PLAYER_MONEY_CHANGED, new NullEventData()));
    }

    public void removeMoney(int cost) {
        money -= cost;
        EventBus.post(new GlobalEvent(PLAYER_MONEY_CHANGED, new NullEventData()));
    }

    public boolean hasMoney(int amount) {
        return money >= amount;
    }

    public int getMoney() {
        return money;
    }
}
