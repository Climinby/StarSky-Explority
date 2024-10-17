package com.climinby.starsky_e.recipe;

import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class AnalysisResult implements Comparable<AnalysisResult> {
    public static final String ODDS_KEY = "odds";
    private final ItemStack itemStack;
    private int weight;

    public AnalysisResult(ItemStack result, int weight) {
        this.itemStack = result;
        if(weight < 0) {
            this.weight = 0;
        } else {
            this.weight = weight;
        }
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        if(weight < 0) {
            this.weight = 0;
        } else {
            this.weight = weight;
        }
    }

    public ItemStack getItemStack() {
        return itemStack.copy();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnalysisResult that = (AnalysisResult) o;
        return Objects.equals(itemStack.copy(), that.itemStack.copy());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(itemStack.copy());
    }

    @Override
    public int compareTo(@NotNull AnalysisResult o) {
        return this.weight - o.weight;
    }
}
