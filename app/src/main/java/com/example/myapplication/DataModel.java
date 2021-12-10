package com.example.myapplication;
 import java.util.List;

public class DataModel {

    private List<String> nestedList1;
    public List<DataModel2> nestedList2;
    private String itemText;
    private boolean isExpandable;

    public DataModel(List<DataModel2> itemList, String itemText , List<String> nestedList1) {
        this.nestedList2 = itemList;
        this.itemText = itemText;
        this.nestedList1= nestedList1;
        isExpandable = false;
    }

    public void setExpandable(boolean expandable) {
        isExpandable = expandable;
    }

    public List<String> getNestedList() {
        return nestedList1;
    }

    public String getItemText() {
        return itemText;
    }

    public boolean isExpandable() {
        return isExpandable;
    }
}