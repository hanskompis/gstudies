App.Routers.Main = Backbone.Router.extend({
  routes: {
    "":    "home"
  },
  
  home : function() {
    var mainView = new App.Views.mainView ({el: $("#mainContainer")});
    mainView.render();
  }
});

