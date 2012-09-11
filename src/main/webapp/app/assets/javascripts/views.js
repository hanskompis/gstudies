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
                path.attr({
                    "stroke-width" : edges[i].weight
                }); //TODO: isommilla n:llä logaritmisena
                console.log(JSON.stringify(edges[i].weightText));
                paper.add([edges[i].weightText]);//add vaatii taulukon
                path.mouseover(function(){
                   
                    });
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
//            $("#resultsTable").append("<theader><tr>");
//            $("#resultsTable").append("<td>asdasd</td>");
//            $("#resultsTable").append("</tr></theader>");

            resultSet.each(function(result){
                $("#resultsTable tbody").append("<tr>");
                for(var key in result.attributes){
                    console.log(key+ " " +result.get(key));
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
                //alert("responsee:" + JSON.stringify(response));
                var resultSet = new Backbone.Collection(response);
                //console.log(resultSet);
                self.render(resultSet);
            }
        })
        
    }
});
