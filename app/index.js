const express = require("express")
const path = require("path")
const bodyParser = require("body-parser")
const moment = require("moment")
const axios = require("axios")

const MAX_TOP_GREAT_QUOTES = 3
const BASE_URL = process.env.API_HOST

//configuration
const app = express()

app.use(express.static('public'))
app.use(bodyParser.json())
app.use(bodyParser.urlencoded({ extended: true }))

app.set("views", path.join(__dirname, "views"))
app.set("view engine", "ejs")

//utils
const utils = {
    formatDate: date => moment(date, 'MMMM DD, YYYY LTS').format('DD/MM/YYYY HH:mm:ss')
}

const render = (response, view, data={}) => {
    response.render(view, Object.assign(data, { utils, top: MAX_TOP_GREAT_QUOTES }))
}

const handleError = error => console.error(error)

//routing
app.get("/", (req, res) => {
    render(res, "home")
})

app.get("/all-great-quotes", async (req, res) => {
    try {
        const quotes = (await axios.get(`${BASE_URL}/quote`)).data
        render(res, "list-great-quotes", { quotes, title: "All Great Quotes" })
    } catch (e) {
        handleError(e)
    }
})

app.get("/top-great-quotes", async (req, res) => {
    try {
        const quotes = (await axios.get(`${BASE_URL}/quote`))
            .data
            .sort(
                (left, right) => right.likes - left.likes
            ).filter(
                (v, i) => i + 1 <= MAX_TOP_GREAT_QUOTES
            )
        render(res, "list-great-quotes", { 
            quotes: quotes,
            title: `Top ${MAX_TOP_GREAT_QUOTES} Great Quotes`
        })
    } catch (e) {
        handleError(e)
    }
})

app.get('/new-great-quote', (req, res) => {
    render(res, "new-great-quote")
})

app.post("/like-great-quote", async (req, res) => {
    try {
        const { id } = req.body
        await axios.post(`${BASE_URL}/quote/${id}/like`)
        res.redirect("all-great-quotes")
    } catch (e) {
        handleError(e)
    }
})

app.post("/remove-great-quote", async (req, res) => {
    try {
        const { id } = req.body
        await axios.delete(`${BASE_URL}/quote/${id}`)
        res.redirect("all-great-quotes")
    } catch (e) {
        handleError(e)
    }
})

app.get("/edit-great-quote", async (req, res) => {
    try {
        const { id } = req.query
        const quote = (await axios.get(`${BASE_URL}/quote/${id}`)).data
        render(res, "edit-great-quote", { quote })
    } catch (e) {
        handleError(e)
    }
})

app.post("/edit-great-quote", async (req, res) => {
    try {
        const { id, author, quote } = req.body
        const greatQuote = (await axios.get(`${BASE_URL}/quote/${id}`)).data
        await axios.put(`${BASE_URL}/quote`, {
            ...greatQuote,
            author,
            quote
        })
        res.redirect("all-great-quotes")
    } catch (e) {
        handleError(e)
    }
})

app.post('/new-great-quote', async (req, res) => {
    try {
        await axios.post(`${BASE_URL}/quote`, {
            author: req.body.author,
            quote: req.body.quote
        })
        res.redirect("all-great-quotes")
    } catch (e) {
        handleError(e)
    }
})

app.listen(process.env.PORT, () => console.log(`Front-end server running on port ${process.env.PORT} pointing to API at ${process.env.API_HOST} ...`))
