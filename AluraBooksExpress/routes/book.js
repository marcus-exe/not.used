//get router from express
const { Router } = require("express")

//get all the controller functions
const { getBooks, getBook, postBook, patchBook, deleteBook } = require("../controllers/book")

//create a router instance
const router = Router() 

//here are all the routes
router.get('/', getBooks)

router.get('/:id', getBook)

router.post('/', postBook)

router.patch('/:id', patchBook)

router.delete('/:id', deleteBook)

module.exports = router 