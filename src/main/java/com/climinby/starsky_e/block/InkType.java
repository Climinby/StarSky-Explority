package com.climinby.starsky_e.block;

import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

public class InkType {
    private Item item;
    private int incre;
    private Identifier texture;

    public InkType(Item item, int incre, Identifier texture) {
        this.item = item;
        this.incre = incre;
        this.texture = texture;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getIncre() {
        return incre;
    }

    public void setIncre(int incre) {
        this.incre = incre;
    }

    public Identifier getTexture() {
        return texture;
    }
}
