const express = require("express")
const path = require("path")
const bodyParser = require("body-parser")
const moment = require("moment")
const uuidv4 = require('uuid/v4')

const app = express();

const MAX_TOP_GREAT_QUOTES = 2;

let quotes = [
    {
        id: uuidv4(),
        author: "helton",
        quote: "hahaha, it's funny",
        likes: 0,
        createdAt: new Date()
    },
    {
        id: uuidv4(),
        author: "joao",
        quote: "very funny",
        likes: 10,
        createdAt: new Date()
    },
    {
        id: uuidv4(),
        author: "felipe",
        quote: "lol",
        likes: 5,
        createdAt: new Date()
    }
]

app.use(express.static('public'))
app.use(bodyParser.json())
app.use(bodyParser.urlencoded({ extended: true }))

app.set("views", path.join(__dirname, "views"));
app.set("view engine", "ejs");

const utils = {
    formatDate: date => 
        moment(date).format('DD/MM/YYYY HH:mm:ss')
}

const render = (response, view, data={}) => {
    response.render(view, Object.assign(data, { utils, top: MAX_TOP_GREAT_QUOTES }))
}

app.get("/", (req, res) => {
    render(res, "home")
});

app.get("/all-great-quotes", (req, res) => {
    render(res, "list-great-quotes", { quotes, title: "All Great Quotes" })
});

app.get("/top-great-quotes", (req, res) => {
    render(res, "list-great-quotes", { 
        quotes: quotes.filter((v, i) => i + 1 <= MAX_TOP_GREAT_QUOTES),
        title: `Top ${MAX_TOP_GREAT_QUOTES} Great Quotes`
    })
});

app.get('/new-great-quote', (req, res) => {
    render(res, "new-great-quote")
})

app.post("/like-great-quote", (req, res) => {
    const { id } = req.body
    const quote = quotes.find(quote => quote.id === id)
    quote.likes++;
    res.redirect("all-great-quotes")
})

app.post("/remove-great-quote", (req, res) => {
    const { id } = req.body
    quotes = quotes.filter(quote => quote.id !== id)
    res.redirect("all-great-quotes")
})

app.get("/edit-great-quote", (req, res) => {
    const { id } = req.query
    render(res, "edit-great-quote", { quote: quotes.find(quote => quote.id === id) })
})

app.post("/edit-great-quote", (req, res) => {
    const { id, author, quote } = req.body
    const currentQuote = quotes.find(quote => quote.id === id)
    currentQuote.author = author
    currentQuote.quote = quote
    res.redirect("all-great-quotes")
})

app.post('/new-great-quote', (req, res) => {
    const quote = {
        id: uuidv4(),
        author: req.body.author,
        quote: req.body.quote,
        likes: 0,
        createdAt: new Date()
    }
    console.log(quote)
    quotes.push(quote)
    res.redirect("all-great-quotes")
})

app.listen(3000, () => console.log("Front-end server running...."));
