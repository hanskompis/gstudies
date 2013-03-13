var CSVUtils = {
    response : null,
    PASSED : 0, 
    FAILED : 1, 
    ALL : 2,
    firstCourse : null,
    secondCourse : null,
    firstYear : null,
    secondYear : null,
    
    composeSingleCourseCSV : function(){
        var csv = '';
        csv += "course number\n";
        csv += this.response.models[0].get("courseId")+("\n\n");
        for(var i = 0; i < this.response.models.length; i++){
            csv += this.composeSingleIterationCSV(i);
        }
        return csv;
    },
    
    composeSingleIterationCSV : function(i){
        var csv = '';
        csv += "date of accomplishment\n"
        csv += this.response.models[i].get("dateOfAccomplishment")+"\n\n";
        csv += "basic stats\n"
        csv += "# students,# passed,# failed, %failed\n";
        csv += this.response.models[i].get("amountCSStuds")+","+this.response.models[i].get("cspassed")+","+this.response.models[i].get("csfailed")+","+this.response.models[i].get("cspercentageFailed")+"\n\n";
        csv += "distribution of course grades \n"
        csv += "1,2,3,4,5 \n"
        csv += this.response.models[i].get("cscourseGrades")[0]+","+this.response.models[i].get("cscourseGrades")[1]+","+this.response.models[i].get("cscourseGrades")[2]+","
        +this.response.models[i].get("cscourseGrades")[3]+","+this.response.models[i].get("cscourseGrades")[4]+"\n\n";
        csv += "average credit yield per student\n";
        csv += "passed 7 months, passed 13 months, passed 19 months\n";
        csv += this.response.models[i].get("courseStatsObjs")[this.PASSED].averageCreditsSevenMonths+","+this.response.models[i].get("courseStatsObjs")[this.PASSED].averageCreditsThirteenMonths+","+this.response.models[i].get("courseStatsObjs")[this.PASSED].averageCreditsNineteenMonths+"\n\n";
        csv += "failed 7 months, failed 13 months, failed 19 months\n";
        csv += this.response.models[i].get("courseStatsObjs")[this.FAILED].averageCreditsSevenMonths+","+this.response.models[i].get("courseStatsObjs")[this.FAILED].averageCreditsThirteenMonths+","+this.response.models[i].get("courseStatsObjs")[this.FAILED].averageCreditsNineteenMonths+"\n\n";      
        csv += "combined 7 months, combined 13 months, combined 19 months\n";
        csv += this.response.models[i].get("courseStatsObjs")[this.ALL].averageCreditsSevenMonths+","+this.response.models[i].get("courseStatsObjs")[this.ALL].averageCreditsThirteenMonths+","+this.response.models[i].get("courseStatsObjs")[this.ALL].averageCreditsNineteenMonths+"\n\n";      
        csv += "total credit yield\n";
        csv += "passed 7 months, passed 13 months, passed 19 months\n";
        csv += this.response.models[i].get("courseStatsObjs")[this.PASSED].amountCreditsSevenMonths+","+this.response.models[i].get("courseStatsObjs")[this.PASSED].amountCreditsThirteenMonths+","+this.response.models[i].get("courseStatsObjs")[this.PASSED].amountCreditsNineteenMonths+"\n\n";
        csv += "failed 7 months, failed 13 months, failed 19 months\n";
        csv += this.response.models[i].get("courseStatsObjs")[this.FAILED].amountCreditsSevenMonths+","+this.response.models[i].get("courseStatsObjs")[this.FAILED].amountCreditsThirteenMonths+","+this.response.models[i].get("courseStatsObjs")[this.FAILED].amountCreditsNineteenMonths+"\n\n";      
        csv += "combined 7 months, combined 13 months, combined 19 months\n";
        csv += this.response.models[i].get("courseStatsObjs")[this.ALL].amountCreditsSevenMonths+","+this.response.models[i].get("courseStatsObjs")[this.ALL].amountCreditsThirteenMonths+","+this.response.models[i].get("courseStatsObjs")[this.ALL].amountCreditsNineteenMonths+"\n\n";
        csv += "zero achievers\n";
        csv += "passed 7 months, passed 13 months, passed 19 months\n";
        csv += this.response.models[i].get("courseStatsObjs")[this.PASSED].amountZeroAchieversSevenMonths+","+this.response.models[i].get("courseStatsObjs")[this.PASSED].amountZeroAchieversThirteenMonths+","+this.response.models[i].get("courseStatsObjs")[this.PASSED].amountZeroAchieversNineteenMonths+"\n\n";
        csv += "failed 7 months, failed 13 months, failed 19 months\n";
        csv += this.response.models[i].get("courseStatsObjs")[this.FAILED].amountZeroAchieversSevenMonths+","+this.response.models[i].get("courseStatsObjs")[this.FAILED].amountZeroAchieversThirteenMonths+","+this.response.models[i].get("courseStatsObjs")[this.FAILED].amountZeroAchieversNineteenMonths+"\n\n";
        csv += "combined 7 months, combined 13 months, combined 19 months\n";
        csv += this.response.models[i].get("courseStatsObjs")[this.ALL].amountZeroAchieversSevenMonths+","+this.response.models[i].get("courseStatsObjs")[this.ALL].amountZeroAchieversThirteenMonths+","+this.response.models[i].get("courseStatsObjs")[this.ALL].amountZeroAchieversNineteenMonths+"\n\n";
        csv += "average grades per student\n";
        csv += "passed 7 months, passed 13 months, passed 19 months\n";
        csv += this.response.models[i].get("courseStatsObjs")[this.PASSED].averageGradeSevenMonths+","+this.response.models[i].get("courseStatsObjs")[this.PASSED].averageGradeThirteenMonths+","+this.response.models[i].get("courseStatsObjs")[this.PASSED].averageGradeNineteenMonths+"\n\n";
        csv += "failed 7 months, failed 13 months, failed 19 months\n";
        csv += this.response.models[i].get("courseStatsObjs")[this.FAILED].averageGradeSevenMonths+","+this.response.models[i].get("courseStatsObjs")[this.FAILED].averageGradeThirteenMonths+","+this.response.models[i].get("courseStatsObjs")[this.FAILED].averageGradeNineteenMonths+"\n\n";
        csv += "combined 7 months, combined 13 months, combined 19 months\n";
        csv += this.response.models[i].get("courseStatsObjs")[this.ALL].averageGradeSevenMonths+","+this.response.models[i].get("courseStatsObjs")[this.ALL].averageGradeThirteenMonths+","+this.response.models[i].get("courseStatsObjs")[this.ALL].averageGradeNineteenMonths+"\n\n";
        csv += "SD of grades\n";
        csv += "passed 7 months, passed 13 months, passed 19 months\n";
        csv += this.response.models[i].get("courseStatsObjs")[this.PASSED].standardDeviationGradesSevenMonths+","+this.response.models[i].get("courseStatsObjs")[this.PASSED].standardDeviationGradesThirteenMonths+","+this.response.models[i].get("courseStatsObjs")[this.PASSED].standardDeviationGradesNineteenMonths+"\n\n";
        csv += "failed 7 months, failed 13 months, failed 19 months\n";
        csv += this.response.models[i].get("courseStatsObjs")[this.FAILED].standardDeviationGradesSevenMonths+","+this.response.models[i].get("courseStatsObjs")[this.FAILED].standardDeviationGradesThirteenMonths+","+this.response.models[i].get("courseStatsObjs")[this.FAILED].standardDeviationGradesNineteenMonths+"\n\n";
        csv += "combined 7 months, combined 13 months, combined 19 months\n";
        csv += this.response.models[i].get("courseStatsObjs")[this.ALL].standardDeviationGradesSevenMonths+","+this.response.models[i].get("courseStatsObjs")[this.ALL].standardDeviationGradesThirteenMonths+","+this.response.models[i].get("courseStatsObjs")[this.ALL].standardDeviationGradesNineteenMonths+"\n\n";

        
       
        return csv;
    },
        
    composeCSV : function(){
        var csv = '';
        csv += "first course, year, second course, year\n";
        csv += this.firstCourse+","+this.firstYear+","+this.secondCourse+","+this.secondYear+"\n\n"
        csv += "students passed\n";
        csv += this.response.get("amountStudents")+("\n\n");
        csv += "total credits 7 months, total credits 13 months, total credits 19 months \n"
        csv += this.response.get("amountCreditsSevenMonths")+(",")+this.response.get("amountCreditsThirteenMonths")+(",")+this.response.get("amountCreditsNineteenMonths")+"\n\n"
        csv += "average credits 7 months, average credits 13 months, average credits 19 months \n"
        csv += this.response.get("averageCreditsSevenMonths")+(",")+this.response.get("averageCreditsThirteenMonths")+(",")+this.response.get("averageCreditsNineteenMonths")+"\n\n"
        csv += "average grade 7 months, average grade 13 months, average grade 19 months \n"
        csv += this.response.get("averageGradeSevenMonths")+(",")+this.response.get("averageGradeThirteenMonths")+(",")+this.response.get("averageGradeNineteenMonths")+"\n\n"
        csv += "SD of grades 7 months, SD of grades 13 months, SD of grades 19 months \n"
        csv += this.response.get("standardDeviationGradesSevenMonths")+(",")+this.response.get("standardDeviationGradesThirteenMonths")+(",")+this.response.get("standardDeviationGradesNineteenMonths")+"\n\n"
        return csv;
    }

}
