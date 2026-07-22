import React from 'react';

class ComplaintRegister extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      ename: '',
      complaint: '',
      NumberHolder: ''
    };
    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  handleChange(event) {
    this.setState({ [event.target.name]: event.target.value });
  }

  handleSubmit(event) {
    event.preventDefault();
    const refNumber = Math.floor(Math.random() * 1000);
    this.setState({ NumberHolder: refNumber }, () => {
      var msg =
        'Thanks ' +
        this.state.ename +
        '! \n Your Complaint was Submitted. \n Transaction ID is: ' +
        this.state.NumberHolder;
      alert(msg);
    });
  }

  render() {
    return (
      <div>
        <h2>Register your complaints here!!!</h2>
        <form onSubmit={this.handleSubmit}>
          <label>
            Name:
            <input
              type="text"
              name="ename"
              value={this.state.ename}
              onChange={this.handleChange}
            />
          </label>
          <br />
          <br />
          <label>
            Complaint:
            <textarea
              name="complaint"
              value={this.state.complaint}
              onChange={this.handleChange}
            />
          </label>
          <br />
          <br />
          <button type="submit">Submit</button>
        </form>
      </div>
    );
  }
}

export default ComplaintRegister;
