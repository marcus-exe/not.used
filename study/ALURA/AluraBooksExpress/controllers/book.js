// importing service function
const { getAllBooks, getBookById, insertBook, modifyBook, deleteBookById } = require("../services/book")

//controller -> the bridge between services and routers
function getBooks(req, res) {
    try {
        const books = getAllBooks()
        res.send(books)

    } catch (error) {
        res.status(500)
        res.send(error.message)
    }
}
function getBook(req, res) {
    try {
        const id = req.params.id
        if( id && Number(id)){
            const book = getBookById(id)
            res.send(book)
        } else {
            res.status(422)
            res.send("Invalid Id")
        }
    } catch (error) {
        res.status(500)
        res.send(error.message)
    }
}
function postBook(req, res) {
    try {
        const newBook = req.body
        if (req.body.name) {
            insertBook(newBook)
            res.send(201)
            res.send("Book Inserted Successfully")
        } else {
            res.status(422)
            res.send("Name field is mandatory")
        }

    } catch (error) {
        res.status(500)
        res.send(error.message)
    }
}
function patchBook(req, res) {
    try {       
        const id = req.params.id
        if( id && Number(id)){
            const body = req.body
            modifyBook(body, id)
            res.send("Successfully modified")
        } else {
            res.status(422)
            res.send("Invalid Id")
        }
    } catch (error) {
        res.status(500)
        res.send(error.message)
    }
}

function deleteBook(req, res) {
    try {
        const id = req.params.id
        if( id && Number(id)){
            deleteBookById(id)
            res.send("Successfully deleted")
        } else {
            res.status(422)
            res.send("Invalid Id")
        }

    } catch (error) {
        res.status(500)
        res.send(error.message)
    }
}

module.exports = {
    getBooks,
    getBook,
    postBook,
    patchBook,
    deleteBook
}