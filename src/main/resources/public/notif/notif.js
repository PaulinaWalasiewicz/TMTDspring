
/* TUTOTIAL FOR NOTIFICATION PART
 https://www.youtube.com/watch?v=hm79I2JpwJw&ab_channel=CodingNepal */

// Create a button element
// const button = document.createElement("button");
// button.textContent = "Show alert";

// Create a div element with class "alert" and "hide"
const alertDiv = document.createElement("div");
alertDiv.classList.add("alert", "hide");

// Create a span element with class "fas fa-exclamation-circle"
const exclamationSpan = document.createElement("span");
exclamationSpan.classList.add("fas", "fa-exclamation-circle");

// Create a span element with class "msg" and text "Warning: This is alert!"
const messageSpan = document.createElement("span");
messageSpan.classList.add("msg");
messageSpan.textContent = "Warning: This is alert!";

// Create a span element with class "close-btn" and a child span with class "fas fa-times"
const closeSpan = document.createElement("span");
closeSpan.classList.add("close-btn");
const closeIconSpan = document.createElement("span");
closeIconSpan.classList.add("fas", "fa-times");
closeSpan.addEventListener('click',function(){
    alertDiv.classList.remove("show");
    alertDiv.classList.add("hide");
})
closeSpan.appendChild(closeIconSpan);

// Add the child elements to the alertDiv
alertDiv.appendChild(exclamationSpan);
alertDiv.appendChild(messageSpan);
alertDiv.appendChild(closeSpan);

document.body.appendChild(alertDiv);

function callNotif(msg){
    messageSpan.textContent = msg;
    alertDiv.classList.remove("hide");
    alertDiv.classList.add("show");
    alertDiv.classList.add('showAlert');
    console.log(alertDiv)
}

//-----------FUNCTIONS FOR EVENTS
let eventsforNotif = [];
function fetchEventss() {
    fetch('http://localhost:8080/api/users/404/events')
        .then(res => res.json()) // the .json() method parses the JSON response into a JS object literal
        .then(data => {
            eventsforNotif = data;
            console.log(eventsforNotif)
        });

}


function checkForUpcomingEvents() {
    console.log('start')
    // debugger;
    // Call the REST endpoint to get the upcoming event(s)
    fetchEventss();
    console.log("ddd")
    // If there is an upcoming event, show a notification
    for (const e of eventsforNotif){
        console.log("ddd")
        const  st = new Date(e.startTime);
        const current = Date.now();
        console.log(st);
        console.log(current)
        var diff = Math.abs(date1 - date2)/60000;
        if (diff<=30){
            const message = `Upcoming event: ${e.title}`;
            console.log(message);
            callNotif(message);
        }

    }


}

// Check for upcoming events every 5 minutes
// setInterval(checkForUpcomingEvents, 5 * 60 * 1000);
