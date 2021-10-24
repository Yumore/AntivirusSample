package com.nathaniel.sample.module;

import java.util.List;

/**
 * @author Nathaniel
 * @version V1.0.0
 * @contact <a href="mailto:nathanwriting@126.com">contact me</a>
 * @package com.nathaniel.sample.module
 * @datetime 2021/10/17 - 10:22
 */
public class DetailEntity {
    private String itemName;
    private List<String> itemValue;

    public DetailEntity() {
    }

    public DetailEntity(String itemName, List<String> itemValue) {
        this.itemName = itemName;
        this.itemValue = itemValue;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public List<String> getItemValue() {
        return itemValue;
    }

    public void setItemValue(List<String> itemValue) {
        this.itemValue = itemValue;
    }
}
