import React from 'react';
import {IndexRoute, Route} from 'react-router';

import App from './app/App';
import CurrencyConversionPage from './app/CurrencyConversionPage';

export default (
    <Route path="/" component={App}>
        <IndexRoute component={CurrencyConversionPage}/>
    </Route>
);
