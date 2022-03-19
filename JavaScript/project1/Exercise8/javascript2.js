function myFun(){
	let btn = document.querySelector("form>p>input");
	let valid = true;
	
	//password check
	let pass = document.getElementById("pass");
	let pass2 = document.getElementById("pass_val");
	let par1 = document.getElementById("pass_field");
	if(pass.value != pass2.value)
		{
			valid=false;
			par1.innerHTML = "! Οι κωδικοί δεν ταιριάζουν !";
			pass.style.backgroundColor = "red";
			pass2.style.backgroundColor = "red";
			par1.style.color = "red";
			par1.style.fontSize = "1.2em";
			par1.style.fontWeight = "bold";
		}
	else
	{
		pass.style.backgroundColor = "white";
		pass2.style.backgroundColor = "white";
		par1.innerHTML = "";
	}
	
	//age check
	let age = document.querySelector("#age");
	let par2 = document.getElementById("age_field");
	if(!age.checkValidity())
	{
		valid=false;
		age.style.backgroundColor = "red";
		par2.innerHTML = "!Η ηλικία πρέπει να ανήκει στο διάστημα 16-100 !";
		par2.style.color = "red";
		par2.style.fontSize = "1.2em";
		par2.style.fontWeight = "bold";
	}
	else
	{
		age.style.backgroundColor = "white";
		par2.innerHTML = "";
	}
	
	
	//telephone check
	let telephone = document.querySelectorAll(".telephone");
	for(let i=0; i<3; i++)
	{
		let length = telephone[i].value.length;
		if(length!=0 && length!=10)
		{
			telephone[i].style.backgroundColor = "red";
			valid=false;
			alert("Βεβαιωθείτε ότι το τηλέφωνο που δώσατε είναι σωστό");
		}
		else
			telephone[i].style.backgroundColor = "white";
	}
	
	//name check
	let name = document.querySelector("input");
	if(name.value.length < 5 || name.value.length>30)
	{
		name.setCustomValidity("Το όνομα πρέπει να καταλαμβάνει τουλάχιστον 5 χαρακτήρες και το πολύ 30");
		name.reportValidity();
	}
	else
	{
		name.setCustomValidity("");
		name.reportValidity();
	}
	
	//username check
	let username = document.getElementById("username");
	if(username.value[0] === username.value[0].toUpperCase())
	{
		username.setCustomValidity("");
		username.reportValidity();
	}
	else
	{
		username.setCustomValidity("Το username πρέπει να ξεκινάει με κεφαλαίο");
		username.reportValidity();
	}
	
	return valid;
}