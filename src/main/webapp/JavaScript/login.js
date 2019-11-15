let urlbase = 'http://localhost:8080/P1/Views/api/';

function getUnfunnyJokes()
{
  let xhr = new XMLHttpRequest(); //ready state is 0.
  let url = urlbase + 'login';

  let orderedList = document.querySelector('ol');
  xhr.onreadystatechange = function()
  {
    if(xhr.readyState === 4 && xhr.status === 200)
    {
      let jokes = JSON.parse(xhr.responseText);
      for(let j of jokes["value"])
      {
        let newListItem = document.createElement('li');
        newListItem.innerHTML = j["joke"];
        orderedList.appendChild(newListItem);
      }
    }
  }
  xhr.open('GET', url); //ready state is 1.
  xhr.send(); //ready state is 2.
}

window.onload = function()
{
  getUnfunnyJokes();
  console.log("here");
}