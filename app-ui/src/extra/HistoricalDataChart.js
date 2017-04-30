import React, {PropTypes} from 'react';
import {Chart} from 'react-google-charts';

const propTypes = {
    exchangeRateHistory: PropTypes.object.isRequired
};

class HistoricalDataChart extends React.Component {
    render() {
        const {exchangeRateHistory} = this.props;

        if (!exchangeRateHistory) {
            return (
                <div style={{
                    display: 'flex',
                    alignItems: 'center',
                    justifyContent: 'center'
                }}>
                    <h3>No historical data available</h3>
                </div>
            );
        }

        const data = [];
        data.push([exchangeRateHistory.currencyCode, 'Rate']);
        exchangeRateHistory.quotes.map((quote) => {
            data.push([quote.date, Number.parseFloat(quote.high)]);
        });

        return (
            <div style={{
                display: 'flex'
            }}>
                <Chart
                    chartType="LineChart"
                    data={data}
                    options={{
                        title: 'Exchange rate USD/' + exchangeRateHistory.currencyCode + ' (10 days)'
                    }}
                    graph_id="exchangeRateHistory"
                    width="100%"
                    height="400px"
                    legend_toggle
                />
            </div>
        );
    }
}

HistoricalDataChart.propTypes = propTypes;

export default HistoricalDataChart;
