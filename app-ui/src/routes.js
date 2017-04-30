import React from 'react';
import {IndexRoute, Route} from 'react-router';

import App from './app/App';
import HomePage from './app/HomePage';
import NotFoundPage from './app/NotFoundPage';

export default (
    <Route path="/" component={App}>
        <IndexRoute component={HomePage}/>
        <Route path="*" component={NotFoundPage}/>
    </Route>
);
