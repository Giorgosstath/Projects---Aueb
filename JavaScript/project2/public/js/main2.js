function myFunction(){
    var input, input_upper, li, txtValue, btn;
    input = document.getElementById("list_search");
    input_upper = input.value.toUpperCase();
    li = document.querySelectorAll("li");
	btn = document.querySelectorAll("li input");
    for (var i = 0; i < li.length; i++) {
        txtValue = li[i].textContent;
        if (txtValue.toUpperCase().indexOf(input_upper) > -1) {
			sleep(200);
			li[i].style.display = "";
			btn[i].style.display="";
        } else {
			sleep(200);
			li[i].style.display = "none";
			btn[i].style.display="none";
        }
    }
}


function sleep(milliseconds) {
  const date = Date.now();
  let currentDate = null;
  do {
    currentDate = Date.now();
  } while (currentDate - date < milliseconds);
}


window.onload = function(){
	let myHeader = new Headers();
	myHeader.append('Content-Type','application/json');
	
	let btn = document.querySelectorAll(".done");
	let ids = document.querySelectorAll(".book_id");
	let titles = document.querySelectorAll(".book_title");
	let authors = document.querySelectorAll(".book_author");
	let descriptions = document.querySelectorAll(".book_description");
	
	for(let i=0; i<btn.length; i++){
		btn[i].addEventListener("click",function(){
			let json_obj = {id:ids[i].value,title:titles[i].value,author:authors[i].value,description:descriptions[i].value}
			
			let put_init = {
				method: "PUT",
				headers: myHeader,
				body: JSON.stringify(json_obj)
			}
		
			fetch("http://localhost:8080/modify/:"+btn[i].id,put_init)
				.then(response => {
					if(response.status == 200){
						console.log("Request done.")
					}
				})
				.catch(error => {
					console.log(error)
				})
		})
	}
}

/*function modify(){
	let myHeader = new Headers();
	myHeader.append('Accept','application/json');
	
	let get_init = {
		method: "GET",
		headers: myHeader
	}
	let btn = document.querySelectorAll("li input");
	for(var i=0; i<btn.lengt; i++){
		btn[i].addEventListener("click",function(){
			fetch("http://localhost:8080/favorites/modify",get_init)
				.then(response => {
					if(response.status == 200){
						console.log("Request done.")
					}
				})
			.catch(error => {
				console.log(error)
			})
		})
	}
}*/