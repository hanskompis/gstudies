App.Routers.Main = Backbone.Router.extend({
    routes: {
        "":    "home",
        "query" : "query",
        "courseStats" : "courseStats",
        "coursePairStats" : "coursePairStats"
    },
  
    home : function() {
        var mainView = new App.Views.mainView ({
            el: $("#pictureContainer")
        });
        mainView.render();
    },
  
    query: function(){
        $("#pictureContainer").empty();
        var queryView = new App.Views.queryView ({
            el: $("#mainContainer")
        });
        queryView.render();
    },
  
    courseStats: function(){
        $("#pictureContainer").empty();
        $("#mainContainer").empty();
        var courseStatsView = new App.Views.courseStatsView ({
            el: $("#mainContainer")
        });
        courseStatsView.render();
    },
    
    coursePairStats: function(){
        $("#pictureContainer").empty();
        $("#mainContainer").empty();
        var coursePairStatsView = new App.Views.coursePairStatsView ({
            el: $("#mainContainer")
        });
        coursePairStatsView.render();
    }
});

