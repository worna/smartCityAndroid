import React from "react";
import {
    BrowserRouter as Router,
    Switch,
    Route,
} from "react-router-dom";
import SearchForm from '../component/SearchForm';
import SportHall from '../component/SportHall';

export default function Routes(){
    return(
        <Router>
            <Switch>
                <Route path="/sportHall/:id" component={SportHall}>
                </Route>
                <Route path="/">
                    <SearchForm/>
                </Route>
            </Switch>
        </Router>
    );
}