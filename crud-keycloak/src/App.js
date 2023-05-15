import React, { useState } from 'react'
import './App.less'
import Book from './components/Screens/Book'
import CreateNewBook from './components/Screens/CreateNewBook'
import EditBook from './components/Screens/EditBook'
import BookTable from './components/Screens/BookTable'
import {
    BrowserRouter as Router,
    Switch, Route,
} from 'react-router-dom'
import Keycloak from 'keycloak-js';

// Routing to the different screens
const App = () => {
    const [books, setBooks] = useState([])
/*    useEffect(()=>{
        const keycloak = new Keycloak('/keycloak.json');
        keycloak.init({ onLoad: 'login-required' }).then(authenticated => {
          setKeycloak(keycloak)
          setAuthenticated(authenticated)
        })
      }, [])
    const [keycloak, setKeycloak] = useState(null)
    const [authenticated, setAuthenticated] = useState(false)
  */
    
//    if (keycloak) {
            return (
            <Router>
                <Switch>
                    <Route exact path='/books'>
                        <BookTable books={books} setBooks={setBooks}/>
                    </Route>
                    <Route exact path='/books/create'>
                        <CreateNewBook books={books} setBooks={setBooks}/>
                    </Route>
                    <Route exact path='/books/edit/:isbn'>
                        <EditBook />
                    </Route>
                    <Route exact path='/books/:isbn'>
                        <Book books={books} setBooks={setBooks}/>
                    </Route>
                </Switch>
            </Router>
        );
//    }
}

export default App;
