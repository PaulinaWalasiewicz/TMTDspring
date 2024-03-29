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
        type: 'Water',
        unit: 'MILLILITER',
        count: 0,
        limit: 0
    },
    {
        type: 'Coffee',
        unit: 'MILLILITER',
        count: 0,
        limit: 0
    },
    {
        type: 'EnergyDrink',
        unit: 'MILLILITER',
        count: 0,
        limit: 0
    },
    {
        type: 'Water',
        unit: 'LITER',
        count: 0,
        limit: 0
    },
    {
        type: 'Coffee',
        unit: 'LITER',
        count: 0,
        limit: 0
    },
    {
        type: 'EnergyDrink',
        unit: 'LITER',
        count: 0,
        limit: 0
    },
    {
        type: 'Water',
        unit: 'OUNCE',
        count: 0,
        limit: 0
    },
    {
        type: 'Coffee',
        unit: 'OUNCE',
        count: 0,
        limit: 0
    },
    {
        type: 'EnergyDrink',
        unit: 'OUNCE',
        count: 0,
        limit: 0
    }];
let today;
let todaysDate;

flatpickr("input[type=datetime-local]", {enableTime: true,
                                         dateFormat: "Y-m-d H:i",});



function fetchEvents() {
    fetch('http://localhost:8080/api/users/1/events')
        .then(res => res.json()) // the .json() method parses the JSON response into a JS object literal
        .then(data => {
            events = data;
            fetchAllDrinks()
        });

}
fetchEvents()

function fetchDescription() {
            openModal("5666")

}

function fetchDrinks(type_id, unit_id, date, limit) {
    debugger;
    fetch('http://localhost:8080/api/users/1/drinks?drink_type='+type_id+'&unit='+unit_id)

        .then(res => res.json()) // the .json() method parses the JSON response into a JS object literal
        .then(data => {
            drinks = data;
            let drinkType = drinkTypes.find(t => t.id == type_id)
            let drinkUnit = drinkUnits.find(u => u.id == unit_id);
            let newValue = 0;
            let newLimit = 0;
            for (const val of drinks) {
                if (val.drink_date == today) {
                    newValue += val.count;
                }
            }

            for (const val of allDrinks) {
                //let newValue = 0;
                if (val.type == drinkType.content && val.unit == drinkUnit.content) {
                    debugger;
                    val.count = newValue
                    if(val.count>=val.limit){
                        if(val.type=="Water"){
                            var msg= "Congratulation you drank enought water!"
                        }else {
                            var msg= "Your "+val.type+" reached it's limit. Don't drink it anymore today!"
                        }
                        callNotif(msg)

                    }
                    //val.limit = limit
                }
            }
            //localStorage.setItem(drinkType.type, newValue + " " + drinkUnit.unit);

            console.log(drinkType);
            load();
        });

}

function fetchAllDrinks() {
    fetch('http://localhost:8080/api/users/1/drinks')
        .then(res => res.json()) // the .json() method parses the JSON response into a JS object literal
        .then(data => {
            // debugger;
            drinks2 = data;
            // console.log(data)
                for (const val of drinks2) {
                    if (val.drink_date == today) {
                        console.log(val.unit.unit)
                        console.log(val.count)
                        for (const val2 of allDrinks) {
                            if (val2.type == val.drink_type && val2.unit == val.unit) {
                                val2.count = +val.count;
                                val2.limit = val.limit
                                console.log(val.drink_type)
                            }

                        }

                    }
                }

            load()
        });

}


function fetchDrinkTypes() {
    drinkTypes = [{content:"Water",id:"Water"},{content:"Coffee",id:"Coffee"},{content:"EnergyDrink",id:"EnergyDrink"}];
            // console.log(data)
            fetchDrinkUnits()

}

function fetchDrinkTypesAndOpenModal() {
    // debugger;
            drinkTypes = [{content:"Water",id:"Water"},{content:"Coffee",id:"Coffee"},{content:"EnergyDrink",id:"EnergyDrink"}];
            // console.log(data)
            fetchDrinkUnitsAndOpenModal();
}

function fetchDrinkUnitsAndOpenModal() {
            drinkUnits = [{content:"LITER",id:"LITER"},{content:"MILLILITER",id:"MILLILITER"},{content:"GALLON",id:"GALLON"},{content:"OUNCE",id:"OUNCE"},{content:"PINT",id:"PINT"}];
            openAddDrinkModal();
}

function fetchDrinkUnits() {
    drinkUnits = [{content:"LITER",id:"LITER"},{content:"MILLILITER",id:"MILLILITER"},{content:"GALLON",id:"GALLON"},{content:"OUNCE",id:"OUNCE"},{content:"PINT",id:"PINT"}];
            // console.log(data)
            fetchAllDrinks()
}


function openModal(date) {

    // console.log(events);

    for (const val of items) {
        let option = document.createElement("option");
        option.value = val.id;
        option.text = val.content.toString();

        // if (eventDescription.childElementCount >= 4) {
        //     eventDescription.replaceChild(option, eventDescription.lastChild)
        // } else {
        //     eventDescription.appendChild(option);
        // }
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
    // debugger;
    //let drink = drinks.find(e => e.id === id);
    for (const val of drinkTypes) {
        let option = document.createElement("option");
        option.value = val.id;
        option.text = val.content;

        if (drinkTypeInput.childElementCount >= 3) {
            drinkTypeInput.replaceChild(option, drinkTypeInput.lastChild)
        } else {
            drinkTypeInput.appendChild(option);
        }
    }

    for (const val of drinkUnits) {
        let option = document.createElement("option");
        option.value = val.id;
        option.text = val.content;

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

    console.log(today)
    todaysDate = new Date(year, month, day);
    console.log(todaysDate.toLocaleDateString())
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
                let water = allDrinks.filter(drink => drink.type == 'Water' && drink.unit == 'MILLILITER');
                let energy_drinks = allDrinks.filter(drink => drink.type == 'EnergyDrink' && drink.unit == 'MILLILITER');
                let coffee = allDrinks.filter(drink => drink.type == 'Coffee' && drink.unit == 'MILLILITER');
                // console.log(coffee[0].limit)
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
                        openEditModal(e.id);
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
            endTime: eventEndDate.value
        };
        // console.log(eventDescription.value);



        fetch('http://localhost:8080/api/users/1/events', {

            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(newEvent)
        })
            .then(res => res.json())
            .then(data => {
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
    // debugger;
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
            "drink_type": drinkTypeInput.value,
            "unit": drinkUnitInput.value,
            "drink_date": today.toString()
        };

        // console.log(eventDescription.value);
        // console.log(today)

        fetch('http://localhost:8080/api/users/1/drinks?drink_type=' + drinkTypeInput.value + '&unit=' + drinkUnitInput.value, {

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
                let drinkType = drinkTypes.find(t => t.id == drinkTypeInput.value)
                let drinkUnit = drinkUnits.find(u => u.id == drinkUnitInput.value);

                for (const val of allDrinks) {
                    //let newValue = 0;
                    if (val.type == drinkType.type && val.unit == drinkUnit.unit) {
                        val.limit = drinkLimitInput.value
                        console.log("yes")
                    }
                }
                fetchDrinks(drinkTypeInput.value, drinkUnitInput.value, today, drinkLimitInput.value);
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
            endTime: editEventEndDate.value
        };

        // console.log(eventDescription.value);

        fetch('http://localhost:8080/api/events/' + id, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(editedEvent)
        })
            .then(res => res.json())
            .then(data => {
                // console.log(data)
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
            // console.log(id+ " deleted")
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
checkForUpcomingEvents();
// Check for upcoming events every 5 minutes
setInterval(checkForUpcomingEvents, 5 * 60 * 1000);
//load();