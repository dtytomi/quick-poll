package com.labsandware.quickpoll.dto;

public class OptionCount {

    private  Long optionId;
    private int count;

    public void setCount(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setOptionId(Long optionId) {
        this.optionId = optionId;
    }

    public Long getOptionId() {
        return optionId;
    }
}
