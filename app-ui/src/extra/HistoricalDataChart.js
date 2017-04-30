import React, {PropTypes} from 'react';
import {Chart} from 'react-google-charts';

const propTypes = {
    exchangeRateHistory: PropTypes.object,
    currentAmount: PropTypes.number
};

class HistoricalDataChart extends React.Component {
    render() {
        const {exchangeRateHistory, currentAmount} = this.props;

        if (!exchangeRateHistory) {
            return (
                <div style={{
                    display: 'flex',
                    alignItems: 'center',
                    justifyContent: 'center'
                }}>
                    <h3>No historical data available - try changing currencies</h3>
                </div>
            );
        }

        const rows = [];
        exchangeRateHistory.quotes.map((quote) => {
            let highRate = Number.parseFloat(quote.high);
            let tooltip = currentAmount ? 'Could have bought ' + highRate * currentAmount + ' (USD)' : '' + highRate;
            rows.push([quote.date, highRate, tooltip]);
        });

        return (
            <div style={{
                display: 'flex'
            }}>
                <Chart
                    chartType="LineChart"
                    columns={[
                        {
                            type: 'date',
                            label: 'Date',
                        },
                        {
                            type: 'number',
                            label: 'Rate',
                        },
                        {
                            type: 'string',
                            role: 'tooltip'
                        }
                    ]}
                    rows={rows}
                    options={{
                        title: 'Exchange rate ' + exchangeRateHistory.currencyCode + '/USD (10 days)'
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
