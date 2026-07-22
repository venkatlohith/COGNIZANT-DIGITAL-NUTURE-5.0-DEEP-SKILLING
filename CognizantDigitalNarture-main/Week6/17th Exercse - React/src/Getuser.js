import React from 'react';

class Getuser extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      person: null,
      loading: true
    };
  }

  async componentDidMount() {
    const url = "https://api.randomuser.me/";
    const response = await fetch(url);
    const data = await response.json();
    this.setState({ person: data.results[0], loading: false });
    console.log(data.results[0]);
  }

  render() {
    if (this.state.loading) {
      return <h2>Loading...</h2>;
    }

    const { person } = this.state;

    return (
      <div>
        <img src={person.picture.large} alt={person.name.first} />
        <h2>
          {person.name.title} {person.name.first}
        </h2>
      </div>
    );
  }
}

export default Getuser;
