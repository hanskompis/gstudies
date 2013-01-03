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
    render: function (response){
        var content = Mustache.to_html($("#courseStatsBaseTemplate").html(),{});
        $(this.el).html(content);
        if(response){
            console.log(response);
            var courseBasicStats = Mustache.to_html($("#courseBasicStatsTemplate").html(),
            {
                courseId : response.models[0].get("courseId")
            });  
            $("#statsContainer").append(courseBasicStats);
            this.renderRowsToCourseBasicStatsTable(response);
            this.renderRowsToGradesTable(response);
            this.renderRowsToCreditsGainTable(response);
            this.renderRowsToGradeSDTable(response);
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
        
        var courseResponse = new Backbone.Collection(testResponse);
        this.render(courseResponse);
    //    kommentti veke, kun rendaus valmis
    //            var self = this;
    //            course.save({},{
    //                success : function (model,response){
    //                    var courseResponse = new Backbone.Collection(response);
    //                    self.response = courseResponse;
    //                    //        console.log(self.response);
    //                    self.render(courseResponse);  
    //                }
    //            })
    },
    
    passedGraphAction : function (){
        var content = Mustache.to_html($("#graphStatsTemplate").html(),{
            studentGroup : "Passed"
        });
        
        $("#graphsContainer").html(content);
        
        //var options = { series: { curvedLines: { active: true }}}; 
        // var data = [graphdata, graphdata2, graphdata3];
       
        //muutetttaaavaaa!!!!!1
       
        var passedResponse = new Backbone.Collection(testResponse);
       
        //muutetttaaavaaa!!!!!1

        var dataSeries = [];
        var amountYears = passedResponse.models.length;
        for(var i = 0; i < amountYears; i++){
            dataSeries.push( passedResponse.models[i].get("courseStatsObjs")[0].creditGainsSevenMonthsCategorizedArr);
        }
        var options = {
            series: {
                //                              lines: {
                //                                  show : true
                //                              }  
                curvedLines: {
                    active: true,
                    show: true,
                    fit: true,
                    lineWidth: 3
                }
            },
 
            xaxis: {
                min: 0, 
                max: 150
            },
            yaxis: {
                min: 0, 
                max: 11 
            }
        }
        $.plot($("#placeholderForCreditGains"), dataSeries, options);
    },
    
    failedGraphAction : function (){
        var content = Mustache.to_html($("#graphStatsTemplate").html(),{
            studentGroup : "Failed"
        });
        $("#graphsContainer").html(content);
    },
    
    combinedGraphAction : function (){
        var content = Mustache.to_html($("#graphStatsTemplate").html(),{
            studentGroup : "Combined"
        });
        $("#graphsContainer").html(content);
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
    }
});
