import React from 'react';
import {IndexRoute, Route} from 'react-router';

import App from './app/App';
import CurrencyConversionPage from './currency-conversion/CurrencyConversionPage';
import NotFoundPage from './app/NotFoundPage';

export default (
    <Route path="/" component={App}>
        <IndexRoute component={CurrencyConversionPage}/>
        <Route path="*" component={NotFoundPage}/>
    </Route>
);
