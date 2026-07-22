import React from 'react';
import './App.css';
import CurrencyConvertor from './CurrencyConvertor';

class App extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      count: 0
    };
    this.handleIncrement = this.handleIncrement.bind(this);
    this.handleDecrement = this.handleDecrement.bind(this);
    this.sayMessage = this.sayMessage.bind(this);
    this.handleClick = this.handleClick.bind(this);
  }

  increment() {
    this.setState((prevState) => ({ count: prevState.count + 1 }));
  }

  decrement() {
    this.setState((prevState) => ({ count: prevState.count - 1 }));
  }

  sayHello() {
    alert('Hello Member!');
  }

  handleIncrement() {
    this.increment();
    this.sayHello();
  }

  handleDecrement() {
    this.decrement();
  }

  sayMessage(message) {
    alert(message);
  }

  handleClick(event) {
    alert('I was clicked');
  }

  render() {
    return (
      <div className="App">
        <h1>Event Handling Examples</h1>

        <h2>Count: {this.state.count}</h2>
        <button onClick={this.handleIncrement}>Increment</button>
        <button onClick={this.handleDecrement}>Decrement</button>

        <br />
        <br />

        <button onClick={() => this.sayMessage('welcome')}>Say Welcome</button>

        <br />
        <br />

        <button onClick={this.handleClick}>Click on me</button>

        <hr />

        <CurrencyConvertor />
      </div>
    );
  }
}

export default App;
