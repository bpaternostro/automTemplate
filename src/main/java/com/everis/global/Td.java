package com.everis.global;

import org.openqa.selenium.WebElement;

public class Td {

    WebElement object;
    int index;
    String value;
    Boolean wasFilled;

    public Td(WebElement object, int index, String value,Boolean wasFilled) {
        this.object = object;
        this.index = index;
        this.value = value;
        this.wasFilled = wasFilled;
    }

    public WebElement getObject() {
        return object;
    }

    public void setObject(WebElement object) {
        this.object = object;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Boolean getWasFilled() {
        return wasFilled;
    }

    public void setWasFilled(Boolean wasFilled) {
        this.wasFilled = wasFilled;
    }
}
