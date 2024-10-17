package com.climinby.starsky_e.block;

import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

import java.util.Objects;

public class InkType {
    private Item item;
    private int incre;
    private Identifier texture;
    private int analysisTime;

    public InkType(Item item, int incre, Identifier texture, int analysisTime) {
        this.item = item;
        this.incre = incre;
        this.texture = texture;
        this.analysisTime = analysisTime;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InkType inkType = (InkType) o;
        return Objects.equals(item, inkType.item);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(item);
    }

    public int getAnalysisTime() {
        return analysisTime;
    }

    public void setAnalysisTime(int analysisTime) {
        this.analysisTime = analysisTime;
    }
}
