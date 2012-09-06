App.Routers.Main = Backbone.Router.extend({
  routes: {
    "":    "home",
    "query" : "query"
  },
  
  home : function() {
    var mainView = new App.Views.mainView ({el: $("#mainContainer")});
    mainView.render();
  },
  
  query: function(){
    var queryView = new App.Views.queryView ({el: $("#mainContainer")});
    queryView.render();
  }
});

