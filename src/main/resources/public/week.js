let nav = 0; //current week
let clicked = null;
// getting events from local storage, if there is none empty array is returned
let events = localStorage.getItem('events') ? JSON.parse(localStorage.getItem('events')) : [];

const calendar = document.getElementById('calendar');
const newEventModal = document.getElementById('newEventModal');
const deleteEventModal = document.getElementById('deleteEventModal');
const backDrop = document.getElementById('modalBackDrop');
const eventTitleInput = document.getElementById('eventTitleInput');
const weekdays = ["Time",'Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'];

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
function load(){
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
    let week = dt.getWeek();
    let weekDay = dt.getDay();
    const hour = dt.getHours();
    const hourString = hour.toString()+":00"

    if (nav!==0){
        week+=nav;
    }
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
        for(let j=1;j<weekdays.length;j++){

            const hourSquare = document.createElement('div');
            hourSquare.classList.add('day');

            const dayString = `${month + 1}/${i - paddingSquares}/${year}`;

            //else create empty for day in previous month, go to if if day is in current month
            if (j > paddingSquares) {
                //hourSquare.innerText = i - paddingDays;

                TODO: 'change so it can also find events by hour'
                const eventForDay = events.find(e => e.date === dayString);

                if ( i === hour && nav === 0) {
                    hourSquare.id = 'currentDay';
                }

                if (eventForDay) {
                    const eventDiv = document.createElement('div');
                    eventDiv.classList.add('event');
                    eventDiv.innerText = eventForDay.title;
                    hourSquare.appendChild(eventDiv);
                }

                hourSquare.addEventListener('click', () => openModal(dayString));
            } else {
                //print time squares
                hourSquare.classList.add('padding');
                hourSquare.innerText=hours[i].label
            }

            calendar.appendChild(hourSquare);
        }
    }
}
function openModal(date) {
    clicked = date;

    const eventForDay = events.find(e => e.date === clicked);

    if (eventForDay) {
        document.getElementById('eventText').innerText = eventForDay.title;
        deleteEventModal.style.display = 'block';
    } else {
        newEventModal.style.display = 'block';
    }

    backDrop.style.display = 'block';
}
function closeModal() {
    eventTitleInput.classList.remove('error');
    newEventModal.style.display = 'none';
    deleteEventModal.style.display = 'none';
    backDrop.style.display = 'none';
    eventTitleInput.value = '';
    clicked = null;
    load();
}
function saveEvent() {
    if (eventTitleInput.value) {
        eventTitleInput.classList.remove('error');

        events.push({
            date: clicked,
            title: eventTitleInput.value,
        });

        localStorage.setItem('events', JSON.stringify(events));
        closeModal();
    } else {
        eventTitleInput.classList.add('error');
    }
}

function deleteEvent() {
    events = events.filter(e => e.date !== clicked);
    localStorage.setItem('events', JSON.stringify(events));
    closeModal();
}

function initButtons() {
    document.getElementById('nextButton').addEventListener('click', () => {
        nav--;
        load();
    });

    document.getElementById('backButton').addEventListener('click', () => {
        nav++;
        load();
    });

    document.getElementById('saveButton').addEventListener('click', saveEvent);
    document.getElementById('cancelButton').addEventListener('click', closeModal);
    document.getElementById('deleteButton').addEventListener('click', deleteEvent);
    document.getElementById('closeButton').addEventListener('click', closeModal);
}

initButtons();
load();