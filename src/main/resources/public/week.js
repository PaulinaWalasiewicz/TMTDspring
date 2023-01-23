let nav = 0; //current week
let clicked = null;

const calendar = document.getElementById('calendar');
const newEventModal = document.getElementById('newEventModal');
const editEventModal = document.getElementById('editEventModal');
const deleteEventModal = document.getElementById('deleteEventModal');
const drinkModal = document.getElementById('newDrinkModal');
const backDrop = document.getElementById('modalBackDrop');
const eventTitleInput = document.getElementById('eventTitleInput');
const eventStartDate = document.getElementById('eventStartDate');
const eventEndDate = document.getElementById('eventEndDate');
const eventDescription = document.getElementById('eventDescription');
const editEventTitleInput = document.getElementById('editEventTitleInput');
const editEventStartDate = document.getElementById('editEventStartDate');
const editEventEndDate = document.getElementById('editEventEndDate');
const editEventDescription = document.getElementById('editEventDescription');
const drinkTypeInput = document.getElementById('drinkType');
const drinkUnitInput = document.getElementById('unit');
const drinkCountInput = document.getElementById('countInput');
const drinkLimitInput = document.getElementById('limitInput');
const weekdays = ["Time",'Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'];
localStorage.setItem("current_day", "22:00");

let events = [];
let items = [];
let drinkTypes = [];
let drinkUnits = [];
let drinks = [];

flatpickr("input[type=datetime-local]", {enableTime: true,
    dateFormat: "Y-m-d H:i",});

function fetchEvents() {
    fetch('http://localhost:8080/api/users/404/events')
        .then(res => res.json()) // the .json() method parses the JSON response into a JS object literal
        .then(data => {
            events = data;
            let date = new Date();
            console.log(date.getWeek()-nav)
            load(date.getWeek()-nav)
        });

}
fetchEvents()

function fetchDescription() {
    fetch('http://localhost:8080/api/descriptions')
        .then(res => res.json()) // the .json() method parses the JSON response into a JS object literal
        .then(data => {
            items.length = 0;
            items = data;
            openModal("5666")
        });

}

function fetchDrinks(type_id, unit_id) {
    fetch('http://localhost:8080/api/users/404/drink?drink_type_id='+type_id+'&drink_unit_id='+unit_id)
        .then(res => res.json()) // the .json() method parses the JSON response into a JS object literal
        .then(data => {
            drinks = data;
            let drinkType = drinkTypes.find(t => t.id == type_id)
            let drinkUnit = drinkUnits.find(u => u.id == unit_id);
            let newValue = 0;
            for (const val of drinks) {
                //val.count
                newValue += val.count;
            }
            localStorage.setItem(drinkType.type, newValue + " " + drinkUnit.unit);
            console.log(drinkType.type);
            let date = new Date();
            console.log(date.getWeek()+nav+1)
            load(date.getWeek()+nav+1)
        });

}

function fetchDrinkTypes() {
    fetch('http://localhost:8080/api/drinktypes')
        .then(res => res.json()) // the .json() method parses the JSON response into a JS object literal
        .then(data => {
            drinkTypes = data;
            console.log(data)
            fetchDrinkUnits();
        });

}

function fetchDrinkUnits() {
    fetch('http://localhost:8080/api/units')
        .then(res => res.json()) // the .json() method parses the JSON response into a JS object literal
        .then(data => {
            drinkUnits = data;
            openAddDrinkModal();
        });

}

function fetchAnotherDescription(id) {
    fetch('http://localhost:8080/api/descriptions')
        .then(res => res.json()) // the .json() method parses the JSON response into a JS object literal
        .then(data => {
            items.length = 0;
            items = data;
            openEditModal(id);
        });

}

function prevDate(date) {
    let copy = new Date(date.valueOf());
    return new Date(copy.setDate(copy.getDate() - 1));
}

function nextDate(date) {
    let copy = new Date(date.valueOf());
    return new Date(copy.setDate(copy.getDate() + 1));
}
function getweekDates(dt,nav){
    let copy = new Date(dt.valueOf());

    if (nav!==0){
        dt = new Date(copy.setDate(copy.getDate()-nav*7))
    }
    let d = new Date(dt.setDate(dt.getDate()));
    let weekD = dt.getDay();
    let resultTab = []
    while(weekD!==0){
        d = prevDate(d);
        resultTab.push(d);
        weekD-=1;
    }
    resultTab.push(dt);
    let wd2 = dt.getDay();
    d = new Date(dt.setDate(dt.getDate()));
    while(wd2!==6){
        d = nextDate(d)
        resultTab.push(d);
        wd2+=1;
    }
    console.log(resultTab)
    return resultTab;
}

Date.prototype.getWeek = function() {
    var onejan = new Date(this.getFullYear(),0,1);
    return Math.ceil((((this - onejan) / 86400000) + onejan.getDay()+1)/7);
}
//load the calendar
function load(week){
    const dt = new Date();

    //get month to display
    dt.setMonth(new Date().getMonth());


    const hours = [];
    // Create an array of week objects
    for (let i = 0; i < 1; i++) {
        // Create an array of hour objects for each week

        for (let j = 0; j < 24; j++) {
            const hour = {
                label: j + ':00'
            };
            hours.push(hour);
        }
    }
    const day = dt.getDate();
    //Jenuary is going to be a 0 - like index in an array
    const month = dt.getMonth();
    const year = dt.getFullYear();
    let weekDay = dt.getDay();
    const hour = dt.getHours();
    const hourString = hour.toString()+":00"

    /*if (nav!==0){
        week+=nav;
    }*/
    //make table with week dates
    const weekDatesTable = getweekDates(dt,nav);




    const weekString = "Week: "+weekDatesTable[0].getWeek().toString();
    //for polish locales:pl-PL
    const dateString = dt.toLocaleDateString('en-US', {
        weekday: 'long',
        year: 'numeric',
        month: 'numeric',
        week: 'numeric',
        day: 'numeric',
    });
    const paddingSquares = 1

    //assign month name to view
    document.getElementById('monthDisplay').innerText =
        `${weekDatesTable[0].getFullYear()} ${" "}${weekString}`;

    //assign dates name to view
    dayDates=document.getElementById('weekdays').getElementsByTagName("div");
    const dayDate = document.createElement('div');
    //empty view for each month at the beginning so the calendar don't show under current
    calendar.innerHTML = '';
    for(x=0;x<weekDatesTable.length;x++){
        let weekDString = weekDatesTable[x].toLocaleDateString('en-us', { month: 'long' });
        weekDString+=" "+ weekDatesTable[x].getDate().toString();
        dayDates[x+1].innerText =weekdays[x+1] + ' '+weekDString;

    }
    for(let i = 1; i <= 24; i++) {
        for(let j=1;j<weekdays.length+1;j++){

            const hourSquare = document.createElement('div');
            hourSquare.classList.add('day');

            const dayString = `${month + 1}/${i - paddingSquares}/${year}`;

            //else create empty for day in previous month, go to if if day is in current month
            if (j > paddingSquares) {
                //hourSquare.innerText = i - paddingDays;

                TODO: 'change so it can also find events by hour'


                if ( i === hour && nav === 0) {
                    hourSquare.id = 'currentDay';
                }
                if (hourSquare.id === 'currentDay') {
                    if (localStorage.getItem("current_day") !== hourSquare.innerText) {
                        //delete all drinks of user
                        deleteDrinks();
                        localStorage.setItem("current_day", hourSquare.innerText);
                    }
                }
                const eventsForDay = events.filter(e => {
                    const date = new Date(e.startTime);
                    return (date.getMonth()+1)+"/"+(date.getHours()-1)+"/"+date.getFullYear() === dayString;
                });

                for (const e of eventsForDay) {
                    let date1 = new Date(e.startTime)
                    const eventDiv = document.createElement('button');
                    eventDiv.classList.add('event');
                    eventDiv.id = e.id;
                    eventDiv.innerText = e.title;
                    eventDiv.addEventListener('click', () => {
                        document.getElementById('eventText').innerText = e.title;
                        document.getElementById('deleteButton').addEventListener('click', () => {deleteEvent(e.id)});
                        document.getElementById('editButton').addEventListener('click', () => {
                            fetchAnotherDescription(e.id);
                        });
                        deleteEventModal.style.display = 'block';
                    })
                    if (week == date1.getWeek()&&j==(date1.getDay()+2)) {
                        hourSquare.appendChild(eventDiv);
                    }
                }

            } else {
                //print time squares
                hourSquare.classList.add('padding');
                hourSquare.innerText=hours[i].label
                const drinkDiv = document.createElement('button');
                drinkDiv.classList.add('drink');
                drinkDiv.innerText = "Drinks\nWater: " + localStorage.getItem("water") + "\nCoffee: " + localStorage.getItem("coffee") + "\nEnergy Drinks: " + localStorage.getItem("energy drink");
                if (i === hour && nav===0){
                    hourSquare.appendChild(drinkDiv);
                }
            }

            calendar.appendChild(hourSquare);
        }
    }
}
function openModal(date) {

    console.log(events);

    for (const val of items) {
        let option = document.createElement("option");
        option.value = val.id;
        option.text = val.content.toString();

        if (eventDescription.childElementCount >= 4) {
            eventDescription.replaceChild(option, eventDescription.lastChild)
        } else {
            eventDescription.appendChild(option);
        }
    }

    clicked = date;

    const eventForDay = events.find(e => e.startTime === clicked);

    if (eventForDay) {
        document.getElementById('eventText').innerText = eventForDay.title;
        deleteEventModal.style.display = 'block';
    } else {
        newEventModal.style.display = 'block';
    }

    backDrop.style.display = 'block';
}
function openAddDrinkModal() {
    //let drink = drinks.find(e => e.id === id);
    for (const val of drinkTypes) {
        let option = document.createElement("option");
        option.value = val.id;
        option.text = val.type.toString();

        if (drinkTypeInput.childElementCount >= 3) {
            drinkTypeInput.replaceChild(option, drinkTypeInput.lastChild)
        } else {
            drinkTypeInput.appendChild(option);
        }
    }

    for (const val of drinkUnits) {
        let option = document.createElement("option");
        option.value = val.id;
        option.text = val.unit.toString();

        if (drinkUnitInput.childElementCount >= 3) {
            drinkUnitInput.replaceChild(option, drinkUnitInput.lastChild)
        } else {
            drinkUnitInput.appendChild(option);
        }
    }

    drinkModal.style.display = "block";
    backDrop.style.display = 'block';

    //editEventTitleInput.value = event.title.toString();
}

function openEditModal(id) {
    let event = events.find(e => e.id === id);
    for (const val of items) {
        let option = document.createElement("option");
        option.value = val.id;
        option.text = val.content.toString();

        if (editEventDescription.childElementCount >= 4) {
            editEventDescription.replaceChild(option, editEventDescription.lastChild)
        } else {
            editEventDescription.appendChild(option);
        }
    }
    editEventModal.style.display = "block";
    backDrop.style.display = 'block';

    editEventTitleInput.value = event.title.toString();
    editEventStartDate.value = event.startTime.toString();
    editEventEndDate.value = event.endTime.toString();

    document.getElementById('saveEditButton').addEventListener('click', () => {
        editEvent(id);
    });
}

function closeModal() {
    eventTitleInput.classList.remove('error');
    newEventModal.style.display = 'none';
    deleteEventModal.style.display = 'none';
    editEventModal.style.display = 'none';
    drinkModal.style.display = 'none';
    backDrop.style.display = 'none';
    eventTitleInput.value = '';
    clicked = null;
    fetchEvents();
}
function saveEvent() {
    if (eventTitleInput.value && eventStartDate.value && eventEndDate.value) {
        eventTitleInput.classList.remove('error');
        eventEndDate.classList.remove('error');
        eventStartDate.classList.remove('error');

        const newEvent = {
            user: {
                username: "ddd",
                password: "567fgg",
                email: "dfghj",
                firstName: "dfgh",
                lastName: "sdfghj"
            },
            title: eventTitleInput.value,
            startTime: eventStartDate.value,
            endTime: eventEndDate.value,
            description: {
                content: "dfghj"
            }
        };

        console.log(eventDescription.value);

        fetch('http://localhost:8080/api/users/404/events?description_id=' + eventDescription.value, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(newEvent)
        })
            .then(res => res.json())
            .then(data => {
                console.log(data.toString())
                fetchEvents();
            })
            .catch(err => {
                console.error(err);
            });
        closeModal();
    } else {
        eventTitleInput.classList.add('error');
    }
}

function saveDrink() {
    if (drinkCountInput.value) {
        drinkCountInput.classList.remove('error');

        const newDrink = {
            user: {
                username: "ddd",
                password: "567fgg",
                email: "dfghj",
                firstName: "dfgh",
                lastName: "sdfghj"
            },
            "count": drinkCountInput.value,
            "limit": drinkLimitInput.value,
            "type": {
                "type": "fdsa"
            },
            "unit": {
                "unit": "gfdsa"
            }
        };

        console.log(eventDescription.value);

        fetch('http://localhost:8080/api/users/404/drinks?drink_type_id=' + drinkTypeInput.value + '&drink_unit_id=' + drinkUnitInput.value, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(newDrink)
        })
            .then(res => res.json())
            .then(data => {
                console.log(data)
                console.log(data.type)
                fetchDrinks(drinkTypeInput.value, drinkUnitInput.value);
            })
            .catch(err => {
                console.error(err);
            });
        closeModal();
    } else {
        drinkCountInput.classList.add('error');
    }
}

function editEvent(id) {

    const editedEvent = {
        user: {
            username: "ddd",
            password: "567fgg",
            email: "dfghj",
            firstName: "dfgh",
            lastName: "sdfghj"
        },
        title: editEventTitleInput.value,
        startTime: editEventStartDate.value,
        endTime: editEventEndDate.value,
        description: {
            content: "dfghj"
        }
    };

    console.log(eventDescription.value);

    fetch('http://localhost:8080/api/events/' + id, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(editedEvent)
    })
        .then(res => res.json())
        .then(data => {
            console.log(data)
            fetchEvents()
        })
        .catch(err => {
            console.error(err);
        });
    closeModal();
}

function deleteEvent(id) {
    fetch('http://localhost:8080/api/events/' + id, {
        method: 'DELETE'
    })
        .then(() => {
            console.log(id+ " deleted")
            fetchEvents()
        })

    closeModal();
}

function deleteDrinks() {
    fetch('http://localhost:8080/api/drinks', {
        method: 'DELETE'
    })
        .then(() => {
            load()
        })

    closeModal();
}

function initButtons() {
    document.getElementById('nextButton').addEventListener('click', () => {
        nav--;
        fetchEvents();
    });

    document.getElementById('backButton').addEventListener('click', () => {
        nav++;
        fetchEvents();
    });

    document.getElementById('saveButton').addEventListener('click', saveEvent);
    document.getElementById('cancelButton').addEventListener('click', closeModal);
    document.getElementById('closeButton').addEventListener('click', closeModal);
    document.getElementById('addButton').addEventListener('click', fetchDescription);
    document.getElementById('addDrinkButton').addEventListener('click', fetchDrinkTypes);
    document.getElementById('saveDrinkButton').addEventListener('click', saveDrink);
}

initButtons();
load();