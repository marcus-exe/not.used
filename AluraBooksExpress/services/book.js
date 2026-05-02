const fs = require("fs")

// here are all the services -> the actual function implementations
function getAllBooks(){
    return JSON.parse( fs.readFileSync("books.json") )
}

function getBookById(id){
    const books = JSON.parse( fs.readFileSync("books.json") )

    const filteredBooks = books.filter( book => book.id == id )[0]
    return filteredBooks
}

function insertBook(newBook){
    const books = JSON.parse( fs.readFileSync("books.json") )
    const newBookList = [...books, newBook]
    fs.writeFileSync("books.json", JSON.stringify(newBookList))
}

function modifyBook(update, id) {
    let books = JSON.parse( fs.readFileSync("books.json") )
    const modifiedIndex = books.findIndex( book => book.id == id)
    // will build object based on a pre-existing one
    const modifiedContent = {...books[modifiedIndex], ...update} 
    // will add object
    books[modifiedIndex] = modifiedContent 
    fs.writeFileSync("books.json", JSON.stringify(books))
}

function deleteBookById(id) {
    let books = JSON.parse(fs.readFileSync("books.json"))
    const index = books.findIndex( book => book.id == id)
    books.splice(index, 1)
    fs.writeFileSync("books.json", JSON.stringify(books))
}


module.exports = {
    getAllBooks,
    getBookById,
    insertBook,
    modifyBook,
    deleteBookById
}