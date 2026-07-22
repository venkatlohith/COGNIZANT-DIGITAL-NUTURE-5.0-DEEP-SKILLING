function ListofIndianPlayers({ IndianPlayers }) {
  return (
    <div>
      {IndianPlayers.map((item, index) => {
        return <li key={index}>Mr. {item}</li>;
      })}
    </div>
  );
}

export default ListofIndianPlayers;
