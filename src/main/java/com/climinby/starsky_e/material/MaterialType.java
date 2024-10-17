package com.climinby.starsky_e.material;

import com.climinby.starsky_e.item.SSEItems;
import com.climinby.starsky_e.item.ScrollItem;

import java.io.Serializable;
import java.util.Objects;

public class MaterialType implements Serializable {
    public static final MaterialType EMPTY = new MaterialType((ScrollItem) SSEItems.RESEARCH_SCROLL, "empty");
    private transient final ScrollItem scrollItem;
    private final String nbtKey;

    public MaterialType(ScrollItem scrollItem, String nbtKey) {
        this.scrollItem = scrollItem;
        this.nbtKey = nbtKey;
    }

    public ScrollItem getScrollItem() {
        return scrollItem;
    }

    public String getNbtKey() {
        return nbtKey;
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        MaterialType that = (MaterialType) o;
//        return Objects.equals(scrollItem, that.scrollItem) && Objects.equals(nbtKey, that.nbtKey);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(scrollItem, nbtKey);
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MaterialType that = (MaterialType) o;
        return Objects.equals(nbtKey, that.nbtKey);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(nbtKey);
    }

    public boolean isEmpty() {
        if(this == EMPTY) {
            return true;
        }
        return false;
    }
}
