$(function() {

  // display text in the output area
  function showOutput(text) {
    $("#output").text(text);
  }

  // load and display JSON sent by server for /players

  function loadData() {
    $.get("/players")
    .done(function(data) {
      showOutput(JSON.stringify(data, null, 2));
    })
    .fail(function( jqXHR, textStatus ) {
      showOutput( "Failed: " + textStatus );
    });
  }




  // handler for when user clicks add person

  function addPlayer() {
    var name = $("#email").val();
    if (name) {
      postPlayer(name);
    }
  }

/*
  function addPlayer() {
  var name = document.querySelector('#email').value;
  if(name){
    postPlayer(name);
   }
  }
*/

  // code to post a new player using AJAX
  // on success, reload and display the updated data from the server

/*  function postPlayer(userName) {
    document.querySelector('#email').addEventListener('submit', function() {

    })
   }*/


  function postPlayer(userName) {
    $.post({
      headers: {
          'Content-Type': 'application/json'
      },
      dataType: "text",
      url: "/players",
      data: JSON.stringify({ "userName": userName })
    })
    .done(function( ) {
      showOutput( "Saved -- reloading");
      loadData();
    })
    .fail(function( jqXHR, textStatus ) {
      showOutput( "Failed: " + textStatus );
    });
  }

  $("#add_player").on("click", addPlayer);

  loadData();
});


class EasyHTTP {

  // Make an HTTP GET Request
  // get(url) {
  //   return new Promise((resolve, reject) => {
  //     fetch(url)
  //     .then(res => res.json())
  //     .then(data => resolve(data))
  //     .catch(err => reject(err));
  //   });
  // }

  // Make an HTTP POST Request
  post(url, data) {
    return new Promise((resolve, reject) => {
      fetch(url, {
        method: 'POST',
        headers: {
          'Content-type': 'application/json'
        },
        body: JSON.stringify(data)
      })
      .then(res => res.json())
      .then(data => resolve(data))
      .catch(err => reject(err));
    });
  }

   // Make an HTTP PUT Request
  //  put(url, data) {
  //   return new Promise((resolve, reject) => {
  //     fetch(url, {
  //       method: 'PUT',
  //       headers: {
  //         'Content-type': 'application/json'
  //       },
  //       body: JSON.stringify(data)
  //     })
  //     .then(res => res.json())
  //     .then(data => resolve(data))
  //     .catch(err => reject(err));
  //   });
  // }

  // Make an HTTP DELETE Request
 //  delete(url) {
 //    return new Promise((resolve, reject) => {
 //      fetch(url, {
 //        method: 'DELETE',
 //        headers: {
 //          'Content-type': 'application/json'
 //        }
 //      })
 //      .then(res => res.json())
 //      .then(() => resolve('Resource Deleted...'))
 //      .catch(err => reject(err));
 //    });
 //  }

 }

 const http = new EasyHTTP;

// Get Users
// http.get('https://jsonplaceholder.typicode.com/users')
//   .then(data => console.log(data))
//   .catch(err => console.log(err));

// User Data
const data = {
  name: 'John Doe',
  username: 'johndoe',
  email: 'jdoe@gmail.com'
}

// Create User
http.post('https://jsonplaceholder.typicode.com/users', data)
  .then(data => console.log(data))
  .catch(err => console.log(err));

// Update Post
// http.put('https://jsonplaceholder.typicode.com/users/2', data)
//   .then(data => console.log(data))
//   .catch(err => console.log(err));

// Delete User
// http.delete('https://jsonplaceholder.typicode.com/users/2')
// .then(data => console.log(data))
// .catch(err => console.log(err));






