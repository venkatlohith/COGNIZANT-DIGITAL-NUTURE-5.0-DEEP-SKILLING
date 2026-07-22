import React from 'react';

class CurrencyConvertor extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      amount: ''
    };
    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  handleChange(event) {
    this.setState({ amount: event.target.value });
  }

  handleSubmit(event) {
    event.preventDefault();
    const rupees = parseFloat(this.state.amount) || 0;
    const euro = (rupees * 0.011).toFixed(2);
    alert('Converting to Euro Amount is ' + euro);
  }

  render() {
    return (
      <div>
        <h2>Currency Convertor!!!</h2>
        <form onSubmit={this.handleSubmit}>
          <label>
            Amount:
            <input
              type="text"
              value={this.state.amount}
              onChange={this.handleChange}
            />
          </label>
          <button type="submit">Convert</button>
        </form>
      </div>
    );
  }
}

export default CurrencyConvertor;
