package xenoframium.genetics.gui;

import xenoframium.ecs.Entity;
import xenoframium.ecs.event.*;
import xenoframium.genetics.Genetics;
import xenoframium.genetics.player.Player;

/**
 * Created by chrisjung on 25/12/17.
 */
public class MoneyUIInput implements GlobalEventListener {
    private final Entity textBox;

    MoneyUIInput(Entity textBox) {
        this.textBox = textBox;
        EventBus.subscribe(this, Player.PLAYER_MONEY_CHANGED);
    }

    @Override
    public void onEvent(GlobalEvent event) {
        textBox.getComponent(TextBoxComponent.class).setText(String.format("%,d", Genetics.player.getMoney()));
    }
}
