package models;

import com.github.javafaker.Faker;
import lombok.Data;
import models.data.ApiData;

import java.util.ArrayList;

@Data
public class StepBodyModel {

//    public ArrayList<StepData> steps;
//    public ArrayList<Integer> workPath;

    public  String stepTestCaseBody =
       "{\"steps\":[{\"name\":\"1232\",\"spacing\":\"\"},{\"name\":\"432\",\"spacing\":\"\"}],\"workPath\":[1]}";

}