package com.climinby.starsky_e.recipe;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.*;

public class AnalysisRecipe {
    private final Item material;
    private final int sumWeight;
    private final Set<AnalysisResult> results;

    public AnalysisRecipe(Settings settings) {
        this.material = settings.material;
        this.results = settings.results;
        this.sumWeight = settings.sumWeight;
    }

    public Item getMaterial() {
        return material;
    }

    public Set<AnalysisResult> getResults() {
        return results;
    }

    public int getSumWeight() {
        return sumWeight;
    }

    public static class Settings {
        Item material;
        Set<AnalysisResult> results = new TreeSet<>();
        int sumWeight = 0;

        public Settings setMaterial(Item material) {
            this.material = material;
            return this;
        }

        public Settings setResults(Set<AnalysisResult> results) {
            this.results.clear();
            for(AnalysisResult result : results) {
                sumWeight = sumWeight + result.getWeight();
                this.results.add(result);
            }
            return this;
        }

        public Settings addResult(AnalysisResult newResult) {
            for(AnalysisResult originalResult : results) {
                if(ItemStack.areEqual(originalResult.getItemStack(), newResult.getItemStack())) {
                    if(newResult.getWeight() == 0) {
                        results.remove(originalResult);
                        sumWeight = sumWeight - originalResult.getWeight();
                        return this;
                    }
                    originalResult.setWeight(newResult.getWeight());
                    sumWeight = sumWeight - originalResult.getWeight() + newResult.getWeight();
                    return this;
                }
            }
            if(results.size() < 5) {
                results.add(newResult);
                sumWeight = sumWeight + newResult.getWeight();
            }
            return this;
        }
    }
}
