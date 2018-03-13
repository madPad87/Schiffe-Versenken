function Game() {
 this.data = [];

    this.init = function() {

        this.getScoreData();

    };

    this.getScoreData = function() {
    var that = this;
 	fetch('../api/leaderboard')
 	.then(function(res) {
 		//this catches the response
 		return res.json();
 	})
 	.then(function(data) {
 	//log the data
 	console.log(data);
 	//Store data in variable
 	that.data = data;
 	//Call the functions to build LeaderBoard and GameList
 	that.buildLeaderBoard();
 	that.buildGameList();
 	})
 	.catch(function(err) {
 		//shows the error in case there is one (wrong api etc.!!)
 		console.log(err);
 	})

    }

    this.buildLeaderBoard = function() {
    var that = this;
    var table = document.querySelector("tbody");
     	console.log(table);
     	    //loop over the data to build the table
     	    that.data.scores.forEach(function(score) {
     	    console.log(score.userName);

     	        var tr = document.createElement("tr");
     	        var td1 = document.createElement("td")
     	        td1.innerHTML = score.userName;
     	        var td2 = document.createElement("td")
     	        td2.innerHTML = score.total;
     	        var td3 = document.createElement("td")
     	        td3.innerHTML = score.won;
     	        var td4 = document.createElement("td")
     	        td4.innerHTML = score.lost;
     	        var td5 = document.createElement("td")
                td5.innerHTML = score.tied;

     	        tr.appendChild(td1);
     	        tr.appendChild(td2);
     	        tr.appendChild(td3);
     	        tr.appendChild(td4);
     	        tr.appendChild(td5);



     	        table.appendChild(tr);
     	    })

    }

    this.buildGameList = function() {
        var that = this;


        /*that.data.games.forEach(function(game) {
        console.log(game);


        })*/
    }

 }

 var game = new Game();
game.init();

var btn = document.getElementById("new-game");

btn.addEventListener("click", function() {

    $.get("../api/games/new")
    .done((res)=>console.log(res))
    location.href = "game.html";

    /*console.log("hello")
    fetch("../api/games/new", {credentials: 'same-origin'})
  .then(function(data) {
  console.log(data);
  })

  .catch(error => console.log("ink"))*/
  })





