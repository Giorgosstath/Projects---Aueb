let input_value;
let object;
let data;
var templates = {};

templates.findBooks = Handlebars.compile(`
	<h2> Search results </h2>
	<ul class = "books">
		{{#each object}}
			<li> ID: {{workid}}, Title: {{titleAuth}}, Author: {{authorweb}} <input type="button" value="Add to favorites" class="btn"> <input type="button" value="Remove from favorites" class="remove_btn"></li>
		{{/each}}
		<p> Δείτε την λίστα αγαπημένων σας βιβλίων <a href = "http://localhost:8080/favorites"> εδώ. </a></p>
	</ul>
`);

let myHeaders1 = new Headers();
myHeaders1.append('Accept','application/json');

let myHeaders2 = new Headers();
myHeaders2.append('Content-Type','application/json');


function search_Func(){
	let get_init = {
		method: "GET",
		headers: myHeaders1
	}

	let search_field = document.querySelector("#search");
	input_value = search_field.value;
	
	let url = 'https://reststop.randomhouse.com/resources/works?search='+input_value;
	fetch(url,get_init)
		.then(response => response.json())
		.then(obj => {
			object = obj.work;
			data = {object};
		})
		.catch(err => console.log(err))
		
	setTimeout(function(){	
		let content = templates.findBooks(data);
		let field = document.querySelector("#find");
		field.innerHTML = content;
		
		
		
		let add_buttons = document.querySelectorAll(".btn");
		
		for(var i=0; i<add_buttons.length; i++){
			let fav = object[i];
			let btn = add_buttons[i];
			btn.onclick = function(){
				let id = fav.workid;
				let author = fav.authorweb;
				let title = fav.titleAuth;
				let json_obj = {"id":id,"author":author,"title":title}
				
				let post_init = {
					method : "POST",
					headers: myHeaders2,
					body: JSON.stringify(json_obj)
				}
				
				fetch("http://localhost:8080/favorites",post_init)
					.then(response => response.json())
					.then(obj => {
						alert(obj.message);
					})
					.catch(err => console.log(err))
			}
		}
		
		
		let rem_buttons = document.querySelectorAll(".remove_btn");
		
		for(var i=0; i<rem_buttons.length; i++){
			let fav = object[i];
			let btn = rem_buttons[i];
			btn.onclick = function(){
				let id = fav.workid;
				let json_obj = {"id":id}
				
				let delete_init = {
					method : "DELETE",
					headers: myHeaders2,
					body: JSON.stringify(json_obj)
				}
				
				fetch("http://localhost:8080/favorites/:"+id,delete_init)
					.then(response => response.json())
					.then(obj => {
						alert(obj.message);
					})
					.catch(err => console.log(err))
			}
		}
		
	},1000)
	
	return false;
}