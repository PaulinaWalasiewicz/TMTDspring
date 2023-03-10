let nav = 0;
let clicked = null;
// getting events from local storage, if there is none empty array is returned
let list = [1,2,3];
const calendar = document.getElementById('calendar');
const newEventModal = document.getElementById('newEventModal');
const deleteEventModal = document.getElementById('deleteEventModal');
const editEventModal = document.getElementById('editEventModal');
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
//const drinkDate = document.getElementById('drinkDate');
const weekdays = ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'];
let events = [];
let items = [];
let drinkTypes = [];
let drinkUnits = [];
let drinks = [];
let drinks2 = [];
let allDrinks = [
    {
        type: 'water',
        unit: 'ml',
        count: 0,
        limit: 0
    },
    {
        type: 'coffee',
        unit: 'ml',
        count: 0,
        limit: 0
    },
    {
        type: 'energy drink',
        unit: 'ml',
        count: 0,
        limit: 0
    },
    {
        type: 'water',
        unit: 'l',
        count: 0,
        limit: 0
    },
    {
        type: 'coffee',
        unit: 'l',
        count: 0,
        limit: 0
    },
    {
        type: 'energy drink',
        unit: 'l',
        count: 0,
        limit: 0
    },
    {
        type: 'water',
        unit: 'oz',
        count: 0,
        limit: 0
    },
    {
        type: 'coffee',
        unit: 'oz',
        count: 0,
        limit: 0
    },
    {
        type: 'energy drink',
        unit: 'oz',
        count: 0,
        limit: 0
    }];
let today;

flatpickr("input[type=datetime-local]", {enableTime: true,
                                         dateFormat: "Y-m-d H:i",});



function fetchEvents() {
    fetch('http://localhost:8080/api/users/404/events')
        .then(res => res.json()) // the .json() method parses the JSON response into a JS object literal
        .then(data => {
            events = data;
            fetchDrinkTypes()
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

function fetchDrinks(type_id, unit_id, date) {
    fetch('http://localhost:8080/api/users/404/drink?drink_type_id='+type_id+'&drink_unit_id='+unit_id+'&drink_date='+date)
        .then(res => res.json()) // the .json() method parses the JSON response into a JS object literal
        .then(data => {
            drinks = data;
            let drinkType = drinkTypes.find(t => t.id == type_id)
            let drinkUnit = drinkUnits.find(u => u.id == unit_id);
            let newValue = 0;
            let newLimit = 0;
            for (const val of drinks) {
                val.type = drinkType.type
                val.unit = drinkUnit.unit
                newValue += val.count;
                newLimit = val.limit;
            }

            for (const val of allDrinks) {
                let newValue = 0;
                if (val.type == drinkType && val.unit == drinkUnit) {
                   val.count = newValue
                    val.limit = newLimit
                }
            }
            //localStorage.setItem(drinkType.type, newValue + " " + drinkUnit.unit);
            console.log(drinkType.type);
            load();
        });

}

function fetchAllDrinks() {
    fetch('http://localhost:8080/api/users/404/drinks')
        .then(res => res.json()) // the .json() method parses the JSON response into a JS object literal
        .then(data => {
            drinks2 = data;
            console.log(data)
            for (const type of drinkTypes) {
                let newValue = 0;
                for (const unit of drinkUnits) {
                    let newValue = 0;
                    let newLimit = 0;
                    for (const val of drinks2) {
                        console.log(val.unit.unit)
                        if (val.drinkType.type == type.type && val.unit.unit == unit.unit) {
                            console.log(val.count)
                            newValue += val.count;
                            newLimit = val.limit
                            for (const val2 of allDrinks) {
                                if (val2.type == type.type && val2.unit == unit.unit) {
                                    val2.count = newValue
                                    val2.limit = newLimit
                                    console.log(newValue)
                                }
                            }
                        }
                    }


                }
            }
            load()
        });

}


function fetchDrinkTypes() {
    fetch('http://localhost:8080/api/drinktypes')
        .then(res => res.json()) // the .json() method parses the JSON response into a JS object literal
        .then(data => {
            drinkTypes = data;
            console.log(data)
            fetchDrinkUnits()
        });

}

function fetchDrinkTypesAndOpenModal() {
    fetch('http://localhost:8080/api/drinktypes')
        .then(res => res.json()) // the .json() method parses the JSON response into a JS object literal
        .then(data => {
            drinkTypes = data;
            console.log(data)
            fetchDrinkUnitsAndOpenModal();
        });

}

function fetchDrinkUnitsAndOpenModal() {
    fetch('http://localhost:8080/api/units')
        .then(res => res.json()) // the .json() method parses the JSON response into a JS object literal
        .then(data => {
            drinkUnits = data;
            openAddDrinkModal();
        });

}

function fetchDrinkUnits() {
    fetch('http://localhost:8080/api/units')
        .then(res => res.json()) // the .json() method parses the JSON response into a JS object literal
        .then(data => {
            drinkUnits = data;
            console.log(data)
            fetchAllDrinks()
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

function load(){
    const dt = new Date();

    //get month to display
    if (nav !== 0) {
        dt.setMonth(new Date().getMonth() + nav);
    }


    const day = dt.getDate();
    //Jenuary is going to be a 0 - like index in an array
    const month = dt.getMonth();
    const year = dt.getFullYear();
    const realMonth = month + 1;
    const formattedMonth = realMonth.toLocaleString('en-US', {
        minimumIntegerDigits: 2,
        useGrouping: false
    })
    const formattedDay = day.toLocaleString('en-US', {
        minimumIntegerDigits: 2,
        useGrouping: false
    })

    today = year + "-" + formattedMonth + "-" + formattedDay

    const firstDayOfMonth = new Date(year, month, 1);
    const daysInMonth = new Date(year, month + 1, 0).getDate(); //get last day in current month (for nb if days)

    //for polish locales:pl-PL
    const dateString = firstDayOfMonth.toLocaleDateString('en-US', {
        weekday: 'long',
        year: 'numeric',
        month: 'numeric',
        day: 'numeric',
    });
    const paddingDays = weekdays.indexOf(dateString.split(', ')[0]);

    //assign month name to view
    document.getElementById('monthDisplay').innerText =
        `${dt.toLocaleDateString('en-us', { month: 'long' })} ${year}`;
    //empty view for each month at the beginning so the calendar don't show under current
    calendar.innerHTML = '';


    for(let i = 1; i <= paddingDays + daysInMonth; i++) {
        const daySquare = document.createElement('div');
        daySquare.classList.add('day');


        const dayString = `${month + 1}/${i - paddingDays}/${year}`;

        //else create empty for day in previous month, go to if if day is in current month
        if (i > paddingDays) {
            daySquare.innerText = (i - paddingDays).toString();

            const eventsForDay = events.filter(e => {
                const date = new Date(e.startTime);
                return (date.getMonth()+1)+"/"+date.getDate()+"/"+date.getFullYear() === dayString;
            });

            if (i - paddingDays === day && nav === 0) {
                daySquare.id = 'currentDay';
            }
            if (daySquare.id === 'currentDay') {
                const drinkDiv = document.createElement('button');
                drinkDiv.classList.add('drink');
                let water = allDrinks.filter(drink => drink.type == 'water' && drink.unit == 'ml');
                let energy_drinks = allDrinks.filter(drink => drink.type == 'energy drink' && drink.unit == 'ml');
                let coffee = allDrinks.filter(drink => drink.type == 'coffee' && drink.unit == 'ml');
                console.log(coffee[0].limit)
                drinkDiv.innerText = "Drinks\nWater: " + water[0].count + water[0].unit + "/" + water[0].limit + water[0].unit + "\nCoffee: " + coffee[0].count + coffee[0].unit +  "/" + coffee[0].limit + coffee[0].unit + "\nEnergy Drinks: " + energy_drinks[0].count + energy_drinks[0].unit +  "/" + energy_drinks[0].limit + energy_drinks[0].unit;

                daySquare.appendChild(drinkDiv);
            }

            for (const e of eventsForDay) {
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

                daySquare.appendChild(eventDiv);
            }

            //daySquare.addEventListener('click', () => fetchDescription());
        } else {
            daySquare.classList.add('padding');
        }

        calendar.appendChild(daySquare);
    }

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
    //load();
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
            },
            "drink_date": today
        };

        console.log(eventDescription.value);
        console.log(today)

        fetch('http://localhost:8080/api/users/404/drinks?drink_type_id=' + drinkTypeInput.value + '&drink_unit_id=' + drinkUnitInput.value + '&drink_date=' + today, {
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
        nav++;
        fetchEvents()
        //load();
    });

    document.getElementById('backButton').addEventListener('click', () => {
        nav--;
        fetchEvents()
        //load();
    });

    document.getElementById('saveButton').addEventListener('click', saveEvent);
    document.getElementById('cancelButton').addEventListener('click', closeModal);
    document.getElementById('closeButton').addEventListener('click', closeModal);
    document.getElementById('addButton').addEventListener('click', fetchDescription);
    document.getElementById('addDrinkButton').addEventListener('click', fetchDrinkTypesAndOpenModal);
    document.getElementById('saveDrinkButton').addEventListener('click', saveDrink);

}

initButtons();
fetchEvents();
//load();