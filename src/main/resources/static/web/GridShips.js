function GridShips() {

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

};
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
var gridShips = new GridShips();
gridShips.init();

function Ships() {

        this.ships = {
            destroyer: {
                width: 1,
                height: 3
            },
            Carrier: {
                width: 1,
                height: 5

            },
            submarine: {
                width: 1,
                height: 3
            },
            battleship: {
                width: 1,
                height: 4
            },
            patrol: {
                width: 1,
                height: 2
            }
        };
        this.box = $("#grid1 .box");
        this.modal = $("#ship-modal");

        this.url;

        this.init = function() {
            var that = this;
            /*this.loadShips();*/
            this.getModal();
        }

        this.getModal = function() {
            var that = this;
            that.box.on("click", function() {
            console.log("hey")
                $("#ship-modal").modal('show');
               var start = $(this).attr('data-cell');
               console.log(start);

               that.placeShip(start);

            })
        }
        this.placeShip = function(start) {
        var that = this;
        $(".ship-button").on("click", function() {
            $("#ship-modal").modal('hide');
            var type = $(this).attr('data-ship');
            var lage = $(this).attr('data-orientation');
                        console.log(start);
                        console.log(type);
                        console.log(lage);
        })




        }



}

var ships = new Ships();
ships.init();