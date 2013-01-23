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
    PASSED : 0, 
    FAILED : 1, 
    ALL : 2,
    dataSeries2 : null, 

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
            this.renderRowsToGradeTable(this.response);
        }

    }, 
    
    events : {
        "click #submitCourseButton" : "submitCourseAction",
        "click #credits7GraphButton" : "credits7GraphAction",
        "click #credits13GraphButton" : "credits13GraphAction",
        "click #credits19GraphButton" : "credits19GraphAction",
        "click #credits7NormGraphButton" : "credits7NormGraphAction",
        "click #credits13NormGraphButton" : "credits13NormGraphAction",
        "click #credits19NormGraphButton" : "credits19NormGraphAction",
        "click #credits7CumulGraphButton" : "credits7CumulGraphAction",
        "click #credits13CumulGraphButton" : "credits13CumulGraphAction",
        "click #credits19CumulGraphButton" : "credits19CumulGraphAction",
        "click #credits7CumulReverGraphButton" : "credits7CumulReverGraphAction",
        "click #credits13CumulReverGraphButton" : "credits13CumulReverGraphAction",
        "click #credits19CumulReverGraphButton" : "credits19CumulReverGraphAction"
    //    "click #credits7HistoGraphButton" : "credits7HistoGraphAction"//,
    //        "click #credits13HistoGraphButton" : "credits13HistoGraphAction",
    //        "click #credits19HistoGraphButton" : "credits19HistoGraphAction"

    },
    
    credits7GraphAction : function (){
        $("#graphsContainer").empty();
        var content = Mustache.to_html($("#credits7Template").html(),{});
        $(("#graphsContainer")).html(content);
        $.plot($("#placeholderForCreditGains7MonthsPassed"), dataSeriesUtils.DSCreditGains7MonthsPass, this.getOptions(dataSeriesUtils.largestCategoryOfCreditGains7Months, dataSeriesUtils.largestValueOfCreditGains7Months));
        $.plot($("#placeholderForCreditGains7MonthsFailed"), dataSeriesUtils.DSCreditGains7MonthsFail, this.getOptions(dataSeriesUtils.largestCategoryOfCreditGains7Months, dataSeriesUtils.largestValueOfCreditGains7Months));
        $.plot($("#placeholderForCreditGains7MonthsAll"), dataSeriesUtils.DSCreditGains7MonthsAll, this.getOptions(dataSeriesUtils.largestCategoryOfCreditGains7Months, dataSeriesUtils.largestValueOfCreditGains7Months));

    },
    
    credits13GraphAction : function (){
        $("#graphsContainer").empty();
        var content = Mustache.to_html($("#credits13Template").html(),{});
        $(("#graphsContainer")).html(content);
        $.plot($("#placeholderForCreditGains13MonthsPassed"), dataSeriesUtils.DSCreditGains13MonthsPass, this.getOptions(dataSeriesUtils.largestCategoryOfCreditGains13Months, dataSeriesUtils.largestValueOfCreditGains13Months));
        $.plot($("#placeholderForCreditGains13MonthsFailed"), dataSeriesUtils.DSCreditGains13MonthsFail, this.getOptions(dataSeriesUtils.largestCategoryOfCreditGains13Months, dataSeriesUtils.largestValueOfCreditGains13Months));
        $.plot($("#placeholderForCreditGains13MonthsAll"), dataSeriesUtils.DSCreditGains13MonthsAll, this.getOptions(dataSeriesUtils.largestCategoryOfCreditGains13Months, dataSeriesUtils.largestValueOfCreditGains13Months));

    },
    credits19GraphAction : function (){
        $("#graphsContainer").empty();
        var content = Mustache.to_html($("#credits19Template").html(),{});
        $(("#graphsContainer")).html(content);
        $.plot($("#placeholderForCreditGains19MonthsPassed"), dataSeriesUtils.DSCreditGains19MonthsPass, this.getOptions(dataSeriesUtils.largestCategoryOfCreditGains19Months, dataSeriesUtils.largestValueOfCreditGains19Months));
        $.plot($("#placeholderForCreditGains19MonthsFailed"), dataSeriesUtils.DSCreditGains19MonthsFail, this.getOptions(dataSeriesUtils.largestCategoryOfCreditGains19Months, dataSeriesUtils.largestValueOfCreditGains19Months));
        $.plot($("#placeholderForCreditGains19MonthsAll"), dataSeriesUtils.DSCreditGains19MonthsAll, this.getOptions(dataSeriesUtils.largestCategoryOfCreditGains19Months, dataSeriesUtils.largestValueOfCreditGains19Months));
    },
    credits7NormGraphAction : function () {
        $("#graphsContainer").empty();
        var content = Mustache.to_html($("#credits7normTemplate").html(),{});
        $(("#graphsContainer")).html(content);
        $.plot($("#placeholderForCreditGains7MonthsPassedNorm"), dataSeriesUtils.DSCreditGains7MonthsNormPass, this.getOptions(dataSeriesUtils.largestCategoryOfCreditGains7MonthsNorm, dataSeriesUtils.largestValueOfCreditGains7MonthsNorm));
        $.plot($("#placeholderForCreditGains7MonthsFailedNorm"), dataSeriesUtils.DSCreditGains7MonthsNormFail, this.getOptions(dataSeriesUtils.largestCategoryOfCreditGains7MonthsNorm, dataSeriesUtils.largestValueOfCreditGains7MonthsNorm));
        $.plot($("#placeholderForCreditGains7MonthsAllNorm"), dataSeriesUtils.DSCreditGains7MonthsNormAll, this.getOptions(dataSeriesUtils.largestCategoryOfCreditGains7MonthsNorm, dataSeriesUtils.largestValueOfCreditGains7MonthsNorm));
    },
    credits13NormGraphAction : function () {
        $("#graphsContainer").empty();
        var content = Mustache.to_html($("#credits13normTemplate").html(),{});
        $(("#graphsContainer")).html(content);
        $.plot($("#placeholderForCreditGains13MonthsPassedNorm"), dataSeriesUtils.DSCreditGains13MonthsNormPass, this.getOptions(dataSeriesUtils.largestCategoryOfCreditGains13MonthsNorm, dataSeriesUtils.largestValueOfCreditGains13MonthsNorm));
        $.plot($("#placeholderForCreditGains13MonthsFailedNorm"), dataSeriesUtils.DSCreditGains13MonthsNormFail, this.getOptions(dataSeriesUtils.largestCategoryOfCreditGains13MonthsNorm, dataSeriesUtils.largestValueOfCreditGains13MonthsNorm));
        $.plot($("#placeholderForCreditGains13MonthsAllNorm"), dataSeriesUtils.DSCreditGains13MonthsNormAll, this.getOptions(dataSeriesUtils.largestCategoryOfCreditGains13MonthsNorm, dataSeriesUtils.largestValueOfCreditGains13MonthsNorm));
    },
    credits19NormGraphAction : function () {
        $("#graphsContainer").empty();
        var content = Mustache.to_html($("#credits19normTemplate").html(),{});
        $(("#graphsContainer")).html(content);
        $.plot($("#placeholderForCreditGains19MonthsPassedNorm"), dataSeriesUtils.DSCreditGains19MonthsNormPass, this.getOptions(dataSeriesUtils.largestCategoryOfCreditGains19MonthsNorm, dataSeriesUtils.largestValueOfCreditGains19MonthsNorm));
        $.plot($("#placeholderForCreditGains19MonthsFailedNorm"), dataSeriesUtils.DSCreditGains19MonthsNormFail, this.getOptions(dataSeriesUtils.largestCategoryOfCreditGains19MonthsNorm, dataSeriesUtils.largestValueOfCreditGains19MonthsNorm));
        $.plot($("#placeholderForCreditGains19MonthsAllNorm"), dataSeriesUtils.DSCreditGains19MonthsNormAll, this.getOptions(dataSeriesUtils.largestCategoryOfCreditGains19MonthsNorm, dataSeriesUtils.largestValueOfCreditGains19MonthsNorm));
    },
    credits7CumulGraphAction: function () {
        $("#graphsContainer").empty();
        var content = Mustache.to_html($("#credits7cumulTemplate").html(),{});
        $(("#graphsContainer")).html(content);
        $.plot($("#placeholderForCreditGains7MonthsPassedCumul"), dataSeriesUtils.DSCreditGains7MonthsNormCumulPass, this.getOptions(dataSeriesUtils.largestCategoryOfCreditGains7MonthsNormCumulPass, dataSeriesUtils.largestValueOfCreditGains7MonthsNormCumulPass));
        $.plot($("#placeholderForCreditGains7MonthsFailedCumul"), dataSeriesUtils.DSCreditGains7MonthsNormCumulFail, this.getOptions(dataSeriesUtils.largestCategoryOfCreditGains7MonthsNormCumulFail, dataSeriesUtils.largestValueOfCreditGains7MonthsNormCumulFail));
        $.plot($("#placeholderForCreditGains7MonthsAllCumul"), dataSeriesUtils.DSCreditGains7MonthsNormCumulAll, this.getOptions(dataSeriesUtils.largestCategoryOfCreditGains7MonthsNormCumulAll, dataSeriesUtils.largestValueOfCreditGains7MonthsNormCumulAll));
    //    $.plot($("#placeholderForCreditGains7MonthsPassedCumulRever"),dataSeriesUtils.DSCreditGains7MonthsNormCumulReverPass,this.getOptions(dataSeriesUtils.largestCategoryOfCreditGains7MonthsReverNorm, 100));
    },
    credits13CumulGraphAction: function () {
        $("#graphsContainer").empty();
        var content = Mustache.to_html($("#credits13cumulTemplate").html(),{});
        $(("#graphsContainer")).html(content);
        $.plot($("#placeholderForCreditGains13MonthsPassedCumul"), dataSeriesUtils.DSCreditGains13MonthsNormCumulPass, this.getOptions(dataSeriesUtils.largestCategoryOfCreditGains13MonthsNormCumulPass, dataSeriesUtils.largestValueOfCreditGains13MonthsNormCumulPass));
        $.plot($("#placeholderForCreditGains13MonthsFailedCumul"), dataSeriesUtils.DSCreditGains13MonthsNormCumulFail, this.getOptions(dataSeriesUtils.largestCategoryOfCreditGains13MonthsNormCumulFail, dataSeriesUtils.largestValueOfCreditGains13MonthsNormCumulFail));
        $.plot($("#placeholderForCreditGains13MonthsAllCumul"), dataSeriesUtils.DSCreditGains13MonthsNormCumulAll, this.getOptions(dataSeriesUtils.largestCategoryOfCreditGains13MonthsNormCumulAll, dataSeriesUtils.largestValueOfCreditGains13MonthsNormCumulAll));
    },
    
    credits19CumulGraphAction: function () {
        // $("#graphsContainer").empty();
        var content = Mustache.to_html($("#credits19cumulTemplate").html(),{});
        $(("#graphsContainer")).html(content);
        $.plot($("#placeholderForCreditGains19MonthsPassedCumul"), dataSeriesUtils.DSCreditGains19MonthsNormCumulPass, this.getOptions(dataSeriesUtils.largestCategoryOfCreditGains19MonthsNormCumulPass, dataSeriesUtils.largestValueOfCreditGains19MonthsNormCumulPass));
        $.plot($("#placeholderForCreditGains19MonthsFailedCumul"), dataSeriesUtils.DSCreditGains19MonthsNormCumulFail, this.getOptions(dataSeriesUtils.largestCategoryOfCreditGains19MonthsNormCumulFail, dataSeriesUtils.largestValueOfCreditGains19MonthsNormCumulFail));
        $.plot($("#placeholderForCreditGains19MonthsAllCumul"), dataSeriesUtils.DSCreditGains19MonthsNormCumulAll, this.getOptions(dataSeriesUtils.largestCategoryOfCreditGains19MonthsNormCumulAll, dataSeriesUtils.largestValueOfCreditGains19MonthsNormCumulAll));
    },
    credits7CumulReverGraphAction: function () {
        $("#graphsContainer").empty();
        var content = Mustache.to_html($("#credits7cumulReverTemplate").html(),{});
        $(("#graphsContainer")).html(content);
        $.plot($("#placeholderForCreditGains7MonthsPassedCumulRever"),dataSeriesUtils.DSCreditGains7MonthsNormCumulReverPass,this.getOptions(dataSeriesUtils.largestCategoryOfCreditGains7MonthsReverNormPass, 100));
        $.plot($("#placeholderForCreditGains7MonthsFailedCumulRever"),dataSeriesUtils.DSCreditGains7MonthsNormCumulReverFail,this.getOptions(dataSeriesUtils.largestCategoryOfCreditGains7MonthsReverNormFail, 100));
        $.plot($("#placeholderForCreditGains7MonthsAllCumulRever"),dataSeriesUtils.DSCreditGains7MonthsNormCumulReverAll,this.getOptions(dataSeriesUtils.largestCategoryOfCreditGains7MonthsReverNormAll, 100));
    },
    credits13CumulReverGraphAction: function () {
        $("#graphsContainer").empty();
        var content = Mustache.to_html($("#credits13cumulReverTemplate").html(),{});
        $(("#graphsContainer")).html(content);
        $.plot($("#placeholderForCreditGains13MonthsPassedCumulRever"),dataSeriesUtils.DSCreditGains13MonthsNormCumulReverPass,this.getOptions(dataSeriesUtils.largestCategoryOfCreditGains13MonthsReverNormPass, 100));
        $.plot($("#placeholderForCreditGains13MonthsFailedCumulRever"),dataSeriesUtils.DSCreditGains13MonthsNormCumulReverFail,this.getOptions(dataSeriesUtils.largestCategoryOfCreditGains13MonthsReverNormFail, 100));
        $.plot($("#placeholderForCreditGains13MonthsAllCumulRever"),dataSeriesUtils.DSCreditGains13MonthsNormCumulReverAll,this.getOptions(dataSeriesUtils.largestCategoryOfCreditGains13MonthsReverNormAll, 100));
    },
    credits19CumulReverGraphAction: function () {
        $("#graphsContainer").empty();
        var content = Mustache.to_html($("#credits19cumulReverTemplate").html(),{});
        $(("#graphsContainer")).html(content);
        $.plot($("#placeholderForCreditGains19MonthsPassedCumulRever"),dataSeriesUtils.DSCreditGains19MonthsNormCumulReverPass,this.getOptions(dataSeriesUtils.largestCategoryOfCreditGains19MonthsReverNormPass, 100));
        $.plot($("#placeholderForCreditGains19MonthsFailedCumulRever"),dataSeriesUtils.DSCreditGains19MonthsNormCumulReverFail,this.getOptions(dataSeriesUtils.largestCategoryOfCreditGains19MonthsReverNormFail, 100));
        $.plot($("#placeholderForCreditGains19MonthsAllCumulRever"),dataSeriesUtils.DSCreditGains19MonthsNormCumulReverAll,this.getOptions(dataSeriesUtils.largestCategoryOfCreditGains19MonthsReverNormAll, 100));
   
 },
    
    credits7HistoGraphAction: function (){
        $("#graphsContainer").empty();
        var content = Mustache.to_html($("#credits7histoTemplate").html(),{});
        $(("#graphsContainer")).html(content);
        $.plot($("#placeholderForCreditGainsHisto"),[{
            data : dataSeriesUtils.DSCreditGains7MonthsHistoPass[0].data , 
            bars: {
                show: true
            }
        }] );
    },
    
    submitCourseAction : function (){
        var course = new App.Models.Course({
            courseId: $("#courseId").val(),
            startYear : $("#startYear").val(),
            endYear : $("#endYear").val()
        });
        $("#statsContainer").empty();
        var content = Mustache.to_html($("#InfoTemplate").html(),{});
        $(("#graphsContainer")).html(content);
        

        this.response = new Backbone.Collection(testResponse);
        dataSeriesUtils.response = this.response;
        dataSeriesUtils.setDataSeries();
        this.render();
    //        this.render(courseResponse);
    //        kommentti veke, kun rendaus valmis
    //                        var self = this;
    //                        course.save({},{
    //                            success : function (model,response){
    //                                var courseResponse = new Backbone.Collection(response);
    //                                self.response = courseResponse;
    //                                //        console.log(self.response);
    //                                dataSeriesUtils.response = courseResponse;
    //                                dataSeriesUtils.setDataSeries();
    //                                self.render();  
    //                            }
    //                        })
    },
      
    getOptions : function(xmax, ymax) {
        return         {
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
                max : xmax+10
            },
            yaxis: {
                min: -1, 
                max : ymax+1
            }
        }
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
    
    renderRowsToGradeTable : function (response) {
        var amountYears = response.models.length;
        for(var i = 0; i < amountYears; i++){
            var rowContent = Mustache.to_html($("#gradesRowTemplate").html(),{
                dateOfAccomplishment : response.models[i].get("dateOfAccomplishment"),
                passedGrade7 : response.models[i].get("courseStatsObjs")[this.PASSED].averageGradeSevenMonths.toFixed(1),
                passedGrade13 : response.models[i].get("courseStatsObjs")[this.PASSED].averageGradeThirteenMonths.toFixed(1),
                passedGrade19 : response.models[i].get("courseStatsObjs")[this.PASSED].averageGradeNineteenMonths.toFixed(1),
                failedGrade7 : response.models[i].get("courseStatsObjs")[this.FAILED].averageGradeSevenMonths.toFixed(1),
                failedGrade13 : response.models[i].get("courseStatsObjs")[this.FAILED].averageGradeThirteenMonths.toFixed(1),
                failedGrade19 : response.models[i].get("courseStatsObjs")[this.FAILED].averageGradeNineteenMonths.toFixed(1),
                allGrade7 : response.models[i].get("courseStatsObjs")[this.ALL].averageGradeSevenMonths.toFixed(1),
                allGrade13 : response.models[i].get("courseStatsObjs")[this.ALL].averageGradeThirteenMonths.toFixed(1),
                allGrade19 : response.models[i].get("courseStatsObjs")[this.ALL].averageGradeNineteenMonths.toFixed(1)
            });
            $("#gradesTable").append(rowContent);
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
