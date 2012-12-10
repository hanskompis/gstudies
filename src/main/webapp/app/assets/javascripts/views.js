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
            var courseId = Mustache.to_html($("#courseBasicStatsTemplate").html(),
            {
                courseId : response.courseId,
                dateOfAccomplishment : response.dateOfAccomplishment,
                studentsPassed : response.cspassed, 
                studentsFailed : response.csfailed,
                studentsOnCourse : response.amountAllStudents,
                passedPers : response.cspercentagePassed.toFixed(1), 
                failedPers : response.cspercentageFailed.toFixed(1), 
                ones : response.cscourseGrades[0],
                twos : response.cscourseGrades[1],
                threes : response.cscourseGrades[2],
                fours : response.cscourseGrades[3],
                fives : response.cscourseGrades[4]
            });  
            $("#statsContainer").append(courseId);
        }
        
    //        $.getJSON("../coursesforinspection",function(courses){
    //            var resultSet = new Backbone.Collection(courses);
    //            resultSet.each(function (course){
    //                var selectionRow = Mustache.to_html($("#courseSelectRowTemplate").html(),{
    //                    "TUNNISTE" : course.get("TUNNISTE"),
    //                    "NIMI":course.get("NIMI")
    //                });
    //                console.log(selectionRow);
    //                $("#courseSelect").append(selectionRow);                
    //            })
    //        });
    }, 
    
    events : {
        "click #submitCourseButton" : "submitCourseAction",
        "click #passedGraphButton" : "passedGraphAction",
        "click #failedGraphButton" : "failedGraphAction",
        "click #combinedGraphButton" : "combinedGraphAction"

    },
    
    submitCourseAction : function (){
        var course = new App.Models.Course({
            tunniste: $("#courseId").val(),
            suorpvm : $("#courseDate").val()
        });
        var self = this;
        course.save({},{
            success : function (model,response){
                self.response = response;
        //        console.log(self.response);
                self.render(response);  
            }
        })
    },
    
    passedGraphAction : function (){
        console.log(this.response.courseStatsObjs[0]);
        var content = Mustache.to_html($("#graphStatsTemplate").html(),{
            studentGroup : "Passed"
        });
        $("#graphsContainer").html(content);
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
    }
});
