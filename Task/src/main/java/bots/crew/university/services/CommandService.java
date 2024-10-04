package bots.crew.university.services;

import bots.crew.university.bom.StatisticDepartment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class CommandService {

    private final LectorService lectorService;

    private final DepartmentService departmentService;

    public void writeCommand(String input, List<String> patternCommands) {
        String firstCommand = "Who is head of department (.+)";
        String secondCommand = "Show (.+) statistics.";
        String thirdCommand = "Show the average salary for the department (.+).";
        String fourthCommand = "Show count of employee for (.+).";
        String fifthCommand = "Global search by (.+).";

        for (String patternCommand : patternCommands) {
            Pattern compiledPattern = Pattern.compile(patternCommand);
            input = input.trim();
            Matcher matcher = compiledPattern.matcher(input);
            if (matcher.matches()) {
                String inputData = matcher.group(1);
                if (patternCommand.equals(firstCommand)) {
                    System.out.println("Head of " + inputData + " department is " + departmentService.getHeadOfDepartment(inputData));
                } else if (patternCommand.equals(secondCommand)) {
                    StatisticDepartment statistic = departmentService.getStatistic(inputData);
                    System.out.println("assistans - " + statistic.assistans
                            + "\n" + "associate professors - " + statistic.associateProfessors + "\n"
                            + "professors - " + statistic.professors);
                } else if (patternCommand.equals(thirdCommand)) {
                    System.out.println("The average salary of " + inputData + " is " + departmentService.getAverageSalary(inputData));
                } else if (patternCommand.equals(fourthCommand)) {
                    System.out.println(lectorService.getCountLectorInDepartment(inputData));
                } else if (patternCommand.equals(fifthCommand)) {
                    List<String> globalSearchLector = lectorService.globalSearch(inputData);
                    List<String> globalSearchDepartment = departmentService.globalSearch(inputData);
                    if (globalSearchLector == null) {
                        globalSearchLector = new ArrayList<>();
                    }
                    globalSearchLector.addAll(globalSearchDepartment);
                    StringBuilder resultString = new StringBuilder();
                    for (int i = 0; i < globalSearchLector.size(); i++) {
                        resultString.append(globalSearchLector.get(i));
                        if (i < globalSearchLector.size() - 1) {
                            resultString.append(", ");
                        }
                    }
                    if(resultString.toString().isEmpty()){
                        System.out.println("Nothing Found!");
                    }else{
                        System.out.println(resultString.toString());
                    }
                }
            }
        }
    }
}
