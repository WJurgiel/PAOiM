package com.example.teachers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SharedData {
    private static final ObservableList<String> groupList = FXCollections.observableArrayList();

    public static ObservableList<String> getGroupList() {
        return groupList;
    }
}
