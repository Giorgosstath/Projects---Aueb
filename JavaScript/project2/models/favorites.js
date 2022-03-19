let favorites = [];


class Favorite{
	constructor(id,title,author,description){
		this.id = id;
		this.title=title;
		this.author=author;
		this.description=description;
	}
}

function add(workId,workTitle,workAuthor){
	for(var i=0; i<favorites.length; i++){
		if(favorites[i].id == workId){
			console.log("Book ",favorites[i].title," is already in favorites.");
			return true;
		}
	}
	favorites.push(new Favorite(workId,workTitle,workAuthor));
	console.log("Book ",favorites[favorites.length-1].title," added to favorites.");
	return false;
}

function findAll(){
	return favorites;
}

function findFavorite(workId){
	let value;
	for(let i in favorites){
		if(favorites[i].id == workId){
			value = favorites[i];
		}
	}
	return value;
}

function findAll(){
	return favorites;
}

function remove(workId){
	var exist = false;
	for(var i=0; i<favorites.length; i++)
	{
		if(favorites[i].id == workId){
			exist = true;
		}
	}

	if(!exist){
		console.log("Book with id : ", workId," doesn't belong to favorites.");
		return 0;
	}
	
	favorites = favorites.filter(function(item){
		return item.id != workId
	})
	console.log("Book with id: ", workId ," removed from favorites.");
	return 1;
}

function update(workId,workTitle,workAuthor,workDescription){
	for(let i=0; i<favorites.length; i++){
		if(favorites[i].id == workId){
			favorites[i].title = workTitle;
			favorites[i].author = workAuthor;
			favorites[i].description = workDescription;
		}
	}
}

module.exports = {
	"add":add,
	"findAll":findAll,
	"remove":remove,
	"update":update,
	"findFavorite":findFavorite
}