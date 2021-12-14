function changed() {
  const select = document.getElementById("filter-select");
  const cardPane = document.getElementById("card-pane");
  const options = select.options;
  const value = select.value;

  if (value == "all") {
    Array.prototype.forEach.call(cardPane.children, e => {
      const list = e.classList;
      const idxn = Array.prototype.indexOf.call(list, "none");
      if (idxn >= 0) {
        list.toggle("none");
      }
    });
  } else {
    Array.prototype.forEach.call(cardPane.children, e => {
      const list = e.classList;
      const idxv = Array.prototype.indexOf.call(list, value);
      const idxn = Array.prototype.indexOf.call(list, "none");
      if (idxv >= 0 && idxn >= 0) {
        list.toggle("none");
      } else if (idxv < 0 && idxn < 0) {
        list.toggle("none");
      }
    });
  }
}
