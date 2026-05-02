//importing express
const express = require("express")

//router, route, mapper (all the same function)
const routeBook = require("./routes/book")

//express instance
const app = express()

//now our app can receive .json objects
app.use(express.json())

app.use('/books', routeBook)

const port = 8000

app.listen(port, ()=> {
    console.log(`Receiving data from: ${port}`)
})