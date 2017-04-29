import React from 'react';
import {render} from 'react-dom';
import {Provider} from 'react-redux';
import {browserHistory, Router} from 'react-router';
import {syncHistoryWithStore} from 'react-router-redux';
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
import injectTapEventPlugin from 'react-tap-event-plugin';

import routes from './routes';
import configureStore from './configure-store';

injectTapEventPlugin();

const store = configureStore();

// Create an enhanced history that syncs navigation events with the store
const history = syncHistoryWithStore(browserHistory, store);

render(
    <MuiThemeProvider>
        <Provider store={store}>
            <Router history={history} routes={routes}/>
        </Provider>
    </MuiThemeProvider>
    , document.getElementById('app')
);
