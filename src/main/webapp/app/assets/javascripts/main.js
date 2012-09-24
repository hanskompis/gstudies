App.Routers.Main = Backbone.Router.extend({
    routes: {
        "":    "home",
        "query" : "query",
        "courseStats" : "courseStats"
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
    }
});

