App.Views.mainView = Backbone.View.extend({
    render : function(rects, edges) {
        var content = Mustache.to_html($("#buttonPictureTemplate").html(),{});
        $(this.el).html(content);
        var paper = Raphael("mainContainer", 1500, 600);
        paper.rect(1,1,1490,590,10);
        paper.add(rects);
        if(edges){
            for(var i = 0; i < edges.length; i++){
                var path = paper.path('\"' + edges[i].pathString + '\"');
                path.attr({"stroke-width" : edges[i].weight}); //TODO: isommilla n:llä logaritmisena
                console.log(JSON.stringify(edges[i].weightText));
                paper.add([edges[i].weightText]);//add vaatii taulukon
                path.mouseover(function(){
                   
                });
console.log(edges[i].weight);
            }    
        }       
    },
   
    events: {
        "click #visualizeButton" : "visualizeAction"
    },

    visualizeAction: function() {
        var self = this;
        $.getJSON("../visualize",function(rects){    
            $.getJSON("../paths",function(edges){
                self.render(rects, edges);
            });
        });


    }
});
