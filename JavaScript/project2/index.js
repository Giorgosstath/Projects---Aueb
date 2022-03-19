const express = require('express')
const path = require('path')
const app = express()
const port = 8080
const favorites = require('./models/favorites')

var exphbs = require('express-handlebars');
app.engine('handlebars',exphbs());
app.set('view engine','handlebars');

app.listen(port)


app.use('/static', express.static(__dirname + '/public'))

app.use(express.urlencoded({ extended: false }))

app.use(express.json())

app.get('/', function(req, res){

    var options = {
        root: path.join(__dirname, 'public')
    }

    res.sendFile('index.html', options, function(err){
        console.log(err)
    })
})

app.post('/favorites',function(req,res){
	let body = req.body;
	let id = body.id;
	let author = body.author;
	let title = body.title;
	let bool = favorites.add(id,title,author);
	if(bool)
		res.send('{"message":"Book: '+title+' is already in favorites."}');
	else
		res.send('{"message":"Book: '+title+' added to favorites."}');
})

app.delete('/favorites/:id',function(req,res){
	let id = req.body.id;
	let exist = favorites.remove(id);
	if(exist==0){
		res.send('{"message":"Book with id: '+id+' doesn t belong to favorites."}');
	}
	else{
		res.send('{"message":"Book with id: '+id+' removed from favorites."}');
	}
})

app.get('/favorites',function(req,res){
	let favorite_obj = favorites.findAll();
	let favorite_list = {favorite_obj};
	
	res.render('favorite-list',{
		favorite:favorite_list.favorite_obj
	})
})

app.get('/modify',function(req,res){
	let id = req.query.id;
	let obj = favorites.findFavorite(id);
	
	res.render('modify-list',{
		id: obj.id,
		title: obj.title,
		author: obj.author
	})
})

app.put('/modify/:id',function(req,res){
	let body = req.body;
	let id = body.id;
	let author = body.author;
	let title = body.title;
	let description  = body.description;
	favorites.update(id,title,author,description);
	res.send("OK");
})