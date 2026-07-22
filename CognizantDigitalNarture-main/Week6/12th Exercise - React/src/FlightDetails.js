function FlightDetails(props) {
  const flights = [
    { flightNo: 'AI-202', from: 'Chennai', to: 'Delhi', departure: '06:30 AM', price: 4500 },
    { flightNo: 'IN-405', from: 'Chennai', to: 'Mumbai', departure: '09:15 AM', price: 5200 },
    { flightNo: 'SG-118', from: 'Chennai', to: 'Bangalore', departure: '01:00 PM', price: 2100 }
  ];

  return (
    <div>
      <h2>Available Flights</h2>
      <table>
        <thead>
          <tr>
            <th>Flight No</th>
            <th>From</th>
            <th>To</th>
            <th>Departure</th>
            <th>Price</th>
          </tr>
        </thead>
        <tbody>
          {flights.map((flight) => (
            <tr key={flight.flightNo}>
              <td>{flight.flightNo}</td>
              <td>{flight.from}</td>
              <td>{flight.to}</td>
              <td>{flight.departure}</td>
              <td>Rs. {flight.price}</td>
            </tr>
          ))}
        </tbody>
      </table>
      <p><i>Please login to book tickets.</i></p>
    </div>
  );
}

export default FlightDetails;
