package xenoframium.genetics.gui;

import xenoframium.ecs.Component;
import xenoframium.ecs.Entity;

/**
 * Created by chrisjung on 25/12/17.
 */
class MoneyUIComponent implements Component {
    final Entity bg;
    final Entity text;

    MoneyUIComponent(Entity bg, Entity text) {
        this.bg = bg;
        this.text = text;
    }
}
