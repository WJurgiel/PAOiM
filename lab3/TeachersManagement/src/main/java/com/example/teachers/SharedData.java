package com.example.teachers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.HashMap;
import java.util.Map;

public class SharedData {
    private static final ObservableList<String> groupList = FXCollections.observableArrayList();
    private static final ObservableList<ClassTeacher> classesList = FXCollections.observableArrayList(); // probably wil be deleted
    private static final Map<String, ObservableList<ClassTeacher>> classesInGroup = new HashMap<>();


    public static ObservableList<String> getGroupList() {
        return groupList;
    }
    public static ObservableList<ClassTeacher> getClassesList() {
        return classesList;
    }

    public static Map<String, ObservableList<ClassTeacher>> getClassesInGroup() {
        return classesInGroup;
    }
    public static void addGroup(String groupName){
        groupList.add(groupName);
        classesInGroup.put(groupName, FXCollections.observableArrayList());
    }
    public static void addClassToGroup(String groupName, ClassTeacher classTeacher){
        ObservableList<ClassTeacher> classes = classesInGroup.get(groupName);
        if(classes != null) classes.add(classTeacher);
    }
}
