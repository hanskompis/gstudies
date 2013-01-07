App.Views.mainView = Backbone.View.extend({
    render : function(rects, edges) {
        var content = Mustache.to_html($("#buttonPictureTemplate").html(),{});
        $(this.el).html(content);
        var paper = Raphael("pictureContainer", 2300, 600);
        paper.rect(1,1,2290,590,10);
        paper.add(rects);
        if(edges){
            for(var i = 0; i < edges.length; i++){
                var path = paper.path('\"' + edges[i].pathString + '\"');
                path.attr({
                    "stroke-width" : (Math.floor(Math.log(edges[i].weight)))+1
                });
                //             paper.add([edges[i].weightText]);//add vaatii taulukon
                console.log(edges[i].weight);
            }    
        }       
    },
   
    events: {
        "click #visualizeButton" : "visualizeAction",
        "click #toQueryButton"   : "toQueryAction"
    },

    visualizeAction: function() {
        var self = this;
        $.getJSON("../visualize",function(rects){    
            $.getJSON("../paths",function(edges){
                self.render(rects, edges);
            });
        });
    },
    
    toQueryAction: function(){
        Backbone.history.navigate("query", true);
    }
}),

App.Views.queryView = Backbone.View.extend({
    render: function(resultSet){
        var content = Mustache.to_html($("#queryTemplate").html(),{});
        $(this.el).html(content);
        if(resultSet){
            $("#simpleTextSpan").text("rivejä:" +resultSet.length);
            resultSet.each(function(result){
                $("#resultsTable tbody").append("<tr>"); //TODO: tee template tästä 
                for(var key in result.attributes){
                    $("#resultsTable tbody tr:last").append(("<td>"+ result.get(key)) +"</td>");
                }
                $("#resultsTable tbody").append("</tr>");
            });
        }
    }, 
    
    events : {
        "click #submitQueryButton" : "submitQueryAction"
    },
    
    submitQueryAction : function (){
        $("#resultsTable tbody").empty();
        var qs = $("#queryString").val();
        var query = new App.Models.Query({
            queryString : $("#queryString").val()
        });
        var self = this;
        query.save({},{
            success : function (model, response){
                var resultSet = new Backbone.Collection(response);
                self.render(resultSet);
            }
        })       
    }
}),
App.Views.courseStatsView = Backbone.View.extend({

    response :null,    
    render: function (){
        var content = Mustache.to_html($("#courseStatsBaseTemplate").html(),{});
        $(this.el).html(content);
        if(this.response){
            console.log(this.response);
            var courseBasicStats = Mustache.to_html($("#courseBasicStatsTemplate").html(),
            {
                courseId : this.response.models[0].get("courseId")
            });  
            $("#statsContainer").append(courseBasicStats);
            this.renderRowsToCourseBasicStatsTable(this.response);
            this.renderRowsToGradesTable(this.response);
            this.renderRowsToCreditsGainTable(this.response);
            this.renderRowsToGradeSDTable(this.response);
        }

    }, 
    
    events : {
        "click #submitCourseButton" : "submitCourseAction",
        "click #passedGraphButton" : "passedGraphAction",
        "click #failedGraphButton" : "failedGraphAction",
        "click #combinedGraphButton" : "combinedGraphAction"

    },
    
    submitCourseAction : function (){
        var course = new App.Models.Course({
            courseId: $("#courseId").val(),
            startYear : $("#startYear").val(),
            endYear : $("#endYear").val()
        });
        

        this.response = new Backbone.Collection(testResponse);
        this.render();
    //        this.render(courseResponse);
    //    kommentti veke, kun rendaus valmis
    //                var self = this;
    //                course.save({},{
    //                    success : function (model,response){
    //                        var courseResponse = new Backbone.Collection(response);
    //                        self.response = courseResponse;
    //                        //        console.log(self.response);
    //                        self.render();  
    //                    }
    //                })
    },
    
    passedGraphAction : function (){
        var content = Mustache.to_html($("#graphStatsTemplate").html(),{
            studentGroup : "Passed"
        });
        
        $("#graphsContainer").html(content);

        //muutetttaaavaaa!!!!!1
       
        //  var passedResponse = new Backbone.Collection(testResponse);
        //  var passedResponse = this.response;
        //muutetttaaavaaa!!!!!1
        var dataSeries = [];
        var amountYears = this.response.models.length;
        for(var i = 0; i < amountYears; i++){
            dataSeries.push({
                data : this.response.models[i].get("courseStatsObjs")[0].creditGainsSevenMonthsCategorizedArr, 
                label :   this.response.models[i].get("dateOfAccomplishment")
            } );
        }
        var options = {
            series: {
                curvedLines: {
                    active: true,
                    show: true,
                    fit: true,
                    lineWidth: 3
                }
            },
 
            xaxis: {
                min: 0, 
                max: this.findLargestCategoryFromThemAll(7)+10
            },
            yaxis: {
                min: -1, 
                max: this.findLargestValueFromThemAll(7)+1
            }
        }
        $.plot($("#placeholderForCreditGains7Months"), dataSeries, options);
    },
    
    failedGraphAction : function (){
        var content = Mustache.to_html($("#graphStatsTemplate").html(),{
            studentGroup : "Failed"
        });
        $("#graphsContainer").html(content);
        //muutetttaaavaaa!!!!!1
       
        //  var passedResponse = new Backbone.Collection(testResponse);
        //  var passedResponse = this.response;
        //muutetttaaavaaa!!!!!1
        var dataSeries = [];
        var amountYears = this.response.models.length;
        for(var i = 0; i < amountYears; i++){
            dataSeries.push({
                data : this.response.models[i].get("courseStatsObjs")[1].creditGainsSevenMonthsCategorizedArr, 
                label :   this.response.models[i].get("dateOfAccomplishment")
            } );
        }
        var options = {
            series: {
                curvedLines: {
                    active: true,
                    show: true,
                    fit: true,
                    lineWidth: 3
                }
            },
 
            xaxis: {
                min: 0, 
                max: this.findLargestCategoryFromThemAll(7)+10
            },
            yaxis: {
                min: -1, 
                max: this.findLargestValueFromThemAll(7)+1
            }
        }
        $.plot($("#placeholderForCreditGains7Months"), dataSeries, options);
    },
    
    combinedGraphAction : function (){
        var content = Mustache.to_html($("#graphStatsTemplate").html(),{
            studentGroup : "Combined"
        });
        $("#graphsContainer").html(content);        //muutetttaaavaaa!!!!!1
       
        //  var passedResponse = new Backbone.Collection(testResponse);
        //  var passedResponse = this.response;
        //muutetttaaavaaa!!!!!1
        var dataSeries = [];
        var amountYears = this.response.models.length;
        for(var i = 0; i < amountYears; i++){
            dataSeries.push({
                data : this.response.models[i].get("courseStatsObjs")[2].creditGainsSevenMonthsCategorizedArr, 
                label :   this.response.models[i].get("dateOfAccomplishment")
            } );
        }
        var options = {
            series: {
                curvedLines: {
                    active: true,
                    show: true,
                    fit: true,
                    lineWidth: 3
                }
            },
 
            xaxis: {
                min: 0, 
                max: this.findLargestCategoryFromThemAll(7)+10
            },
            yaxis: {
                min: -1, 
                max: this.findLargestValueFromThemAll(7)+1
            }
        }
        $.plot($("#placeholderForCreditGains7Months"), dataSeries, options);
    },
    
    renderRowsToCourseBasicStatsTable : function(response){
        var amountYears = response.models.length;
        for(var i = 0; i < amountYears; i++){
            var rowContent = Mustache.to_html($("#courseBasicStatsRowTemplate").html(),{
                dateOfAccomplishment : response.models[i].get("dateOfAccomplishment"),
                amountStudents : response.models[i].get("amountCSStuds"),
                amountPassedStudents : response.models[i].get("cspassed"),
                amountFailedStudents : response.models[i].get("csfailed"),
                percentageFailedStudents : response.models[i].get("cspercentageFailed").toFixed(1)
            });
            $("#courseBasicStatsTable").append(rowContent);
        }

    },
    
    renderRowsToGradesTable : function(response){
        var amountYears = response.models.length;
        for(var i = 0; i < amountYears; i++){
            var rowContent = Mustache.to_html($("#courseGradesRowTemplate").html(),{
                dateOfAccomplishment : response.models[i].get("dateOfAccomplishment"),
                ones : response.models[i].get("cscourseGrades")[0],
                twos : response.models[i].get("cscourseGrades")[1],
                threes : response.models[i].get("cscourseGrades")[2],
                fours : response.models[i].get("cscourseGrades")[3],
                fives : response.models[i].get("cscourseGrades")[4]
            });
            $("#courseGradesTable").append(rowContent);
        }    
    },
    
    renderRowsToCreditsGainTable : function(response){
        var amountYears = response.models.length;
        for(var i = 0; i < amountYears; i++){
            var rowContent = Mustache.to_html($("#creditGainsRowTemplate").html(),{
                dateOfAccomplishment : response.models[i].get("dateOfAccomplishment"),
                passedCredits7 : response.models[i].get("courseStatsObjs")[0].averageCreditsSevenMonths.toFixed(1),
                passedCredits13 : response.models[i].get("courseStatsObjs")[0].averageCreditsThirteenMonths.toFixed(1),
                passedCredits19 : response.models[i].get("courseStatsObjs")[0].averageCreditsNineteenMonths.toFixed(1),
                failedCredits7 : response.models[i].get("courseStatsObjs")[1].averageCreditsSevenMonths.toFixed(1),
                failedCredits13 : response.models[i].get("courseStatsObjs")[1].averageCreditsThirteenMonths.toFixed(1),
                failedCredits19 : response.models[i].get("courseStatsObjs")[1].averageCreditsNineteenMonths.toFixed(1),
                allCredits7 : response.models[i].get("courseStatsObjs")[2].averageCreditsSevenMonths.toFixed(1),
                allCredits13 : response.models[i].get("courseStatsObjs")[2].averageCreditsThirteenMonths.toFixed(1),
                allCredits19 : response.models[i].get("courseStatsObjs")[2].averageCreditsNineteenMonths.toFixed(1)
            });
            $("#creditGainsTable").append(rowContent);
        }
    },
    renderRowsToGradeSDTable : function(response){
        var amountYears = response.models.length;
        for(var i = 0; i < amountYears; i++){
            var rowContent = Mustache.to_html($("#gradesSDRowTemplate").html(),{
                dateOfAccomplishment : response.models[i].get("dateOfAccomplishment"),
                passedSD7 : response.models[i].get("courseStatsObjs")[0].standardDeviationGradesSevenMonths.toFixed(2),
                passedSD13 : response.models[i].get("courseStatsObjs")[0].standardDeviationGradesThirteenMonths.toFixed(2),
                passedSD19 : response.models[i].get("courseStatsObjs")[0].standardDeviationGradesNineteenMonths.toFixed(2),
                failedSD7 : response.models[i].get("courseStatsObjs")[1].standardDeviationGradesSevenMonths.toFixed(2),
                failedSD13 : response.models[i].get("courseStatsObjs")[1].standardDeviationGradesThirteenMonths.toFixed(2),
                failedSD19 : response.models[i].get("courseStatsObjs")[1].standardDeviationGradesNineteenMonths.toFixed(2),
                allSD7 : response.models[i].get("courseStatsObjs")[2].standardDeviationGradesSevenMonths.toFixed(2),
                allSD13 : response.models[i].get("courseStatsObjs")[2].standardDeviationGradesThirteenMonths.toFixed(2),
                allSD19 : response.models[i].get("courseStatsObjs")[2].standardDeviationGradesNineteenMonths.toFixed(2)
            });
            $("#gradesSDTable").append(rowContent);
        }
    },
    findLargestCategory : function (arrayOfArrays){
        var largest = 0;
        for(var i = 0; i < arrayOfArrays.length; i++){
            if(arrayOfArrays[i][0]>largest){
                largest = arrayOfArrays[i][0];
            }
        }
        return largest;
    },
    findLargestValue : function (arrayOfArrays){
        var largest = 0;
        for(var i = 0; i < arrayOfArrays.length; i++){
            if(arrayOfArrays[i][1]>largest){
                largest = arrayOfArrays[i][1];
            }
        }
        return largest;
    },
    findLargestCategoryFromSet : function (setOfArraysOfArrays){
        var largest = 0;
        for(var i = 0; i < setOfArraysOfArrays.length; i++){
            var largestOfThisArray = this.findLargestCategory(setOfArraysOfArrays[i].data);
            if(largestOfThisArray>largest){
                largest = largestOfThisArray;
            }
        }
        return largest;
    },
    findLargestValueFromSet : function (setOfArraysOfArrays){
        var largest = 0;
        for(var i = 0; i < setOfArraysOfArrays.length; i++){
            var largestOfThisArray = this.findLargestValue(setOfArraysOfArrays[i].data);
            if(largestOfThisArray>largest){
                largest = largestOfThisArray;
            }
        }
        return largest;
    },
    
    findLargestValueNMonthsFromResponseModels : function (i,n){
        var largest = 0;
        var currentValue;
        this.response.each(function(result){
            if(n==7){
                currentValue = result.get("courseStatsObjs")[i].largestValue7Months;
            }
            else if(n==13){
                currentValue = result.get("courseStatsObjs")[i].largestValue13Months;
            }
            else if(n==19){
                currentValue = result.get("courseStatsObjs")[i].largestValue19Months;
            }
            else{
                alert("fail!!!")
            }
            if(currentValue > largest){
                largest = currentValue;
            }
        });
        return largest;
    },
        
    findLargestCategoryNMonthsFromResponseModels : function (i,n){
        var largest = 0;
        var currentCategory;
        this.response.each(function(result){
            if(n==7){
                currentCategory = result.get("courseStatsObjs")[i].largestCategory7Months;
            }
            else if(n==13){
                currentCategory = result.get("courseStatsObjs")[i].largestCategory13Months;
            }
            else if(n==19){
                currentCategory = result.get("courseStatsObjs")[i].largestCategory19Months;
            }
            else{
                alert("fail!!!")
            }
            if(currentCategory > largest){
                largest = currentCategory;
            }
        });
        return largest;
    },
    
    findLargestCategoryFromThemAll : function (n){
        var largest = 0;
        var CurrentCategory;
        for(var i = 0; i < 3; i++){
            CurrentCategory = this.findLargestCategoryNMonthsFromResponseModels(i,n);
            if(CurrentCategory > largest){
                largest = CurrentCategory;
            }
        }
        return largest;
    },
    
    findLargestValueFromThemAll : function (n){
        var largest = 0;
        var CurrentValue;
        for(var i = 0; i < 3; i++){
            CurrentValue = this.findLargestValueNMonthsFromResponseModels(i,n);
            if(CurrentValue > largest){
                largest = CurrentValue;
            }
        }
        return largest;
    }
    
});
