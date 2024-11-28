package com.example.teachers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeacherCondChart {
    public static ObservableList<PieChart.Data> getTeacherConditionData(){
        Map<TeacherConditions, Integer> conditionCounts = new HashMap<>();

        for(TeacherConditions condition : TeacherConditions.values()){
            conditionCounts.put(condition, 0);
        }

        for(ObservableList<ClassTeacher> classList : SharedData.getClassesInGroup().values()){
            for(ClassTeacher classTeacher : classList){
                List<Teacher> teachers = classTeacher.getTeachers();
                for(Teacher t: teachers){
                    TeacherConditions cond = t.getCondition();
                    conditionCounts.put(cond, conditionCounts.get(cond) + 1);
                }
            }
        }
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        for (Map.Entry<TeacherConditions, Integer> entry : conditionCounts.entrySet()) {
            pieChartData.add(new PieChart.Data(entry.getKey().name(), entry.getValue()));
        }

        return pieChartData;
    }
}
