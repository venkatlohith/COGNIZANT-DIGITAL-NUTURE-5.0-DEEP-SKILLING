import './App.css';
import src from './office-space.svg';

const element = "Office Space";

const jsxatt = <img src={src} width="25%" height="25%" alt="Office Space" />;

const ItemName = { Name: "DBS", Rent: 50000, Address: "Chennai" };

const officeList = [
  { Name: "DBS", Rent: 50000, Address: "Chennai" },
  { Name: "TCS", Rent: 75000, Address: "Bangalore" },
  { Name: "Infosys", Rent: 45000, Address: "Pune" },
  { Name: "Wipro", Rent: 68000, Address: "Hyderabad" }
];

function App() {
  return (
    <div className="App">
      <h1>{element} , at Affordable Range </h1>

      {jsxatt}

      <h1>Name: {ItemName.Name}</h1>
      <h3> Rent: Rs. {ItemName.Rent}</h3>
      <h3> Address: {ItemName.Address}</h3>

      <hr />

      <h2>All Office Spaces</h2>
      {officeList.map((itemName, index) => {
        let colors = [];
        if (itemName.Rent <= 60000) {
          colors.push('textRed');
        } else {
          colors.push('textGreen');
        }
        return (
          <div key={index} className={colors.join(' ')}>
            <h3>Name: {itemName.Name}</h3>
            <h4> Rent: Rs. {itemName.Rent}</h4>
            <h4> Address: {itemName.Address}</h4>
          </div>
        );
      })}
    </div>
  );
}

export default App;
