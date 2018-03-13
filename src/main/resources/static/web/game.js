

//The OOP way of styling the javascript necessities to create the grid and fetch the JSON API data
function DataHandler() {

    var that = this;

    this.getParameterByName;
    this.myGrid = {
      0: "",
      1: "A",
      2: "B",
      3: "C",
      4: "D",
      5: "E",
      6: "F",
      7: "G",
      8: "H",
      9: "I",
      10: "J"
 }

//Init function to initialize the chain of events

this.init = function() {
    this.createGrid("#container", "grid1");
    this.createGrid("#container", "grid2");
    this.getGameData();
    this.getParameterByName('gp');
};

//In order to get the params with the player id (e.g. jack) and thereby get the respective data(ships and salvoes)
//from the respective game_view api
this.getParameterByName = function(name, url) {
    if (!url) url = window.location.href;
    name = name.replace(/[\[\]]/g, "\\$&");
    var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
        results = regex.exec(url);
    if (!results) return null;
    if (!results[2]) return '';
    return decodeURIComponent(results[2].replace(/\+/g, " "));
}

this.gamePlayerId = this.getParameterByName('gp');

this.getGameData = function() {
 	fetch('../api/game_view/' + this.getParameterByName("gp"))
 	.then(function(res) {
 		//this catches the response
 		return res.json();
 	})
 	.then(function(data) {
 		//this .then shows the data, the text in this case
 		console.log(data);
 		//Save data to variables
 		//var keys = Object.keys(data); Acces "data" which is not a JSON but a JavaScript Object
        console.log( data.game.gamePlayers )
        var date = new Date( data.game.created );
        var li = document.createElement("li");
        document.querySelector("#game-created").textContent = date.toLocaleString();
        //gamePlayers is an array inside the JS Object data, so I can use a forEach Loop
        data.game.gamePlayers.forEach(function(gamePlayer, i) {
            console.log(gamePlayer);
            if(i == 0) {
                li.textContent += gamePlayer.player.username +" vs ";
            } else
            li.textContent += gamePlayer.player.username;


        })
        details.appendChild(li);

        //Place the ships in their locations:
 	    data.ships.forEach(function(ship,i) {
        //Ships is an array inside the JS Object data, so I can use a forEach Loop
        //to assign the ships to their respective cells
 	        ship.location.forEach(function(loc,x){
 	        //We put the ships in grid 1, grid2 is for the salvoes
 	            var cell = document.querySelector("#grid1 [data-cell='"+loc+"']");
                   //If one of the ships is placed in a cell, it's background turns white
 	            if(cell){
 	            cell.style.backgroundColor = "white";
 	            }
 	        })
        })

        //Implementing the salvoes
        data.salvoes.forEach(function(salvo, i) {
        console.log(salvo);

            salvo.turns.forEach(function(turn, x) {

                //Turns are an object, not an array
                var turns = Object.keys(turn);

                 //again, loop through turns
                turns.forEach(function(turnKey, x) {

                    var locationArray = turn[turnKey];
                    //Loop through the locations Array
                    locationArray.forEach(function(loc,i){
                     if(salvo.gamePlayerID == that.gamePlayerId) {
                             //Own Salvos in grid 2 and the opponent's Salvos in Grid1
                             var cell = document.querySelector("#grid2 [data-cell='"+loc+"']");
                             console.log(cell)
                              cell.classList.add("salvo"); }
                              else {
                                var cell = document.querySelector("#grid1 [data-cell='"+loc+"']");
                                   cell.classList.add("salvo");
                                //If one of the opponent's salvoes is placed in the same  cell,
                                // as one of own ships, its background turns green and the turn number gets displayed

                                /*if(cell == indexOf(data.ships.location)){
                                    cell.classList.add("hit"); }*/
                                }
                            })
                        })
                    })


                })

            })

 	.catch(function(err) {
 		//shows the error in case there is one (wrong api etc.!!)
 		console.log(err);
 	})
 }


 //The gridid to create 2 divs, one for each grid
 this.createGrid = function(container, gridid) {

    //Here the parent-divs for the 2 grids are created
    var grid = document.createElement('div');
    //Set attribute to assign grid numbers one and two
    grid.setAttribute("id",gridid);

    //Select the container
    var containerElement = document.querySelector(container)
    //Two nested loops for rows and columns
 for(var row = 0; row < 11; row ++){
  for(var col = 0; col < 11; col ++){
    //String.fromCharcode(65+row) starts rowIndex from letter A+the row number
    var rowIndex = String.fromCharCode(65+row);
    var colIndex = col;

    //Here I created the small divs, the cells and assign classes and attributes;
    var div = document.createElement("div");
    div.classList.add("box");
   /* div.classList.add("row"+rowIndex);
    div.classList.add("col"+colIndex);
    div.setAttribute("data-row",rowIndex);
    div.setAttribute("data-col",colIndex);*/


    //Assign class header to the left columns to assign the letters (myGrid) specified in the DataHandler
    if(colIndex == 0) {
        div.classList.add("header");
        var letter = document.createElement("p");
            letter.textContent = this.myGrid[row];
            div.appendChild(letter);

    } else if (row == 0) {
        var letter = document.createElement("p");
                    letter.textContent = col;
                    div.appendChild(letter);
    } else {
        div.setAttribute("data-cell",this.myGrid[row]+colIndex);
    }


    //Append the cells to the respective grid
    grid.appendChild(div);

  }
 }
   //Append the 2 grids to the html parent Container
    containerElement.appendChild(grid)
}






}
//Instantiate the whole process with the 2 following lines and let the show begin!
var dataHandler = new DataHandler();
dataHandler.init();





function CreateGrid() {

}



