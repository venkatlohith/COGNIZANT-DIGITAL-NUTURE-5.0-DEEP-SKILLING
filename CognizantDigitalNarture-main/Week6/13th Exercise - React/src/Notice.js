function Notice(props) {
  // Demonstrates "preventing a component from rendering":
  // if there is no message to show, render nothing at all.
  if (!props.message) {
    return null;
  }
  return <div className="notice">{props.message}</div>;
}

export default Notice;
