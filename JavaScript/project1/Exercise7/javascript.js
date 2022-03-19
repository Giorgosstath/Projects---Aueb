window.onload = function(){
	//Variables
	let html = document.childNodes[1];
	let body = html.lastChild;
	var btn1 = document.querySelector("article button");
	var btn2 = document.querySelector("footer button");
	let date = new Date();
	let date_par = body.querySelector("#article1>p");
	let day = date.getDay();
	
	//Query1
	let dds = body.querySelectorAll("#article2 section dl dd");
	for(let dd of dds)
	{
		dd.style.textDecoration = "underline";
		dd.style.color = "#9CD811";
	}
	
	//Query 2
	let fig = body.querySelector("article #intro figure");
	fig.style.border = "3pt solid red";
	fig.style.margin = "3em 3em";
	
	//Query3
	let h1 = body.querySelector("article #intro>h1");
	h1.style.marginTop = "3em";
	h1.style.fontSize = "2em";
	

	//Event1
	btn1.addEventListener("click",function(){
		let body = document.body;
		body.style.backgroundColor = "#6DC8F6";
		let paragraps = document.querySelectorAll("p");
		for(let par of paragraps)
			par.style.fontSize = "1.2em";
		date_par.style.fontSize = "2em";
	})
	
	//Event2
	btn2.addEventListener("click",function(){
		let par = document.querySelector("#links");
		par.innerHTML = '<a href="https://www.guru99.com/software-testing-introduction-importance.html">'+"Link1"+'</a> <br> <a href="https://www.guru99.com/software-testing.html">'+"Link2"+'</a> <br> <a href="https://www.ibm.com/topics/software-testing">'+"Link3"+'</a> <br> <a href="https://www.softwaretestingmaterial.com/software-testing/">'+"Link4"+'</a> <br> <button type="button" id="less">'+"See less" +'</button>';
		let btn3 = document.querySelector("#less");
		btn3.addEventListener("click",function(){
			par.innerHTML = "";
		})
	})
	
	//Date
	switch(day){
		case 0:
			date_par.innerHTML = "Happy Sunday!";
			break;
		case 1:
			date_par.innerHTML = "Happy Monday!";
			body.style.backgroundColor = "#00D088";
			break;
		case 2:
			date_par.innerHTML = "Happy Tuesday!";
			body.style.backgroundColor = "#00D088";
			break;
		case 3:
			date_par.innerHTML = "Happy Wednesday!";
			body.style.backgroundColor = "#AC6098";
			break;
		case 4:
			date_par.innerHTML = "Happy Thursday!";
			body.style.backgroundColor = "#AC6098";
			break;
		case 5:
			date_par.innerHTML = "Happy Friday!";
			body.style.backgroundColor = "#BFD903";
			break;
		case 6:
			date_par.innerHTML = "Happy Saturday!";
			body.style.backgroundColor = "#BFD903";
			break;
	}
	date_par.style.textAlign = "center";
	date_par.style.fontSize = "2em";
}