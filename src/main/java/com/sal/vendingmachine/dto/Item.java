
package com.sal.vendingmachine.dto;

import java.math.BigDecimal;
import java.util.Objects;

/**
 *
 * @author salajrawi
 */
public class Item {

    // Declare Item's variables
//    private String itemId;
    private String name;
    private BigDecimal cost;
    private int numInventoryItems;

    public Item() {
    }

    // Initialize the constructor and create a new Item object
    public Item( String name, BigDecimal cost, int numInventoryItems) {
        //implement
//        this.itemId = itemId;
        this.name = name;
        this.cost = cost;
        this.numInventoryItems = numInventoryItems;
    }

    public Item(String itemName) {
        this.name = itemName;
    }

    //    public String getItemId() {
//        return itemId;
//    }
    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public BigDecimal getCost() {

        return cost;
    }

    public void setCost(BigDecimal cost) {

        this.cost = cost;
    }

    public int getNumInventoryItems() {

        return numInventoryItems;
    }

    public void setNumInventoryItems(int numInventoryItems) {

        this.numInventoryItems = numInventoryItems;
    }

    @Override
    public String toString() {
        return "Item{" + "name=" + name + ", cost=" + cost + ", numInventoryItems=" + numInventoryItems + '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return numInventoryItems == item.numInventoryItems && Objects.equals(name, item.name) && Objects.equals(cost, item.cost);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, cost, numInventoryItems);
    }
}
