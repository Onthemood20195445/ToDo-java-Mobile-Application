package com.example.todo_list;

public class TodoItem {
    private String text;
    private boolean isChecked;
    private boolean isChecked2; // add isChecked2 property

    public TodoItem(String text) {
        this.text = text;
        isChecked = false;
        isChecked2 = false; // set isChecked2 to false by default
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public boolean isChecked2() {
        return isChecked2;
    }

    public void setChecked2(boolean checked2) {
        isChecked2 = checked2;
    }
}