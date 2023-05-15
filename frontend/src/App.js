import React, { useState } from 'react'
import Book from './components/Screens/Book'
import CreateNewBook from './components/Screens/CreateNewBook'
import EditBook from './components/Screens/EditBook'
import BookTable from './components/Screens/BookTable'
import {
    BrowserRouter as Router,
    Routes, Route,
} from 'react-router-dom'

// Routing to the different screens
const App = () => {
  const [books, setBooks] = useState([])
            return ( 
              
                  <Router>
                      <Routes>
                          <Route path='/' element={<BookTable books={books} setBooks={setBooks}/>} />
                          <Route path='/create' element={<CreateNewBook books={books} setBooks={setBooks}/>} />
                          <Route path='/edit/:isbn' element={<EditBook />}/>
                          <Route path='/:isbn' element={<Book books={books} setBooks={setBooks}/>} />
                      </Routes>
                  </Router>
              
        );
    }

export default App;