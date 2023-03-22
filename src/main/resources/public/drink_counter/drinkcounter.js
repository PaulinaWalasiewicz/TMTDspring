
//https://www.youtube.com/watch?v=sI2Oe7EkKMI&ab_channel=OnlineTutorials

//-----------------FUNCTIONS  FOR DB------------ from script.js
// let drinkTypes = [];
// let drinkUnits = [];
// let drinks = [];
// let drinks2 = [];
// let allDrinks = [
//     {
//         type: 'water',
//         unit: 'ml',
//         count: 0,
//         limit: 0
//     },
//     {
//         type: 'coffee',
//         unit: 'ml',
//         count: 0,
//         limit: 0
//     },
//     {
//         type: 'energy drink',
//         unit: 'ml',
//         count: 0,
//         limit: 0
//     },
//     {
//         type: 'water',
//         unit: 'l',
//         count: 0,
//         limit: 0
//     },
//     {
//         type: 'coffee',
//         unit: 'l',
//         count: 0,
//         limit: 0
//     },
//     {
//         type: 'energy drink',
//         unit: 'l',
//         count: 0,
//         limit: 0
//     },
//     {
//         type: 'water',
//         unit: 'oz',
//         count: 0,
//         limit: 0
//     },
//     {
//         type: 'coffee',
//         unit: 'oz',
//         count: 0,
//         limit: 0
//     },
//     {
//         type: 'energy drink',
//         unit: 'oz',
//         count: 0,
//         limit: 0
//     }];
// function fetchDrinks(type_id, unit_id, date) {
//     fetch('http://localhost:8080/api/users/404/drink?drink_type_id='+type_id+'&drink_unit_id='+unit_id+'&drink_date='+date)
//         .then(res => res.json()) // the .json() method parses the JSON response into a JS object literal
//         .then(data => {
//             drinks = data;
//             let drinkType = drinkTypes.find(t => t.id == type_id)
//             let drinkUnit = drinkUnits.find(u => u.id == unit_id);
//             let newValue = 0;
//             let newLimit = 0;
//             for (const val of drinks) {
//                 val.type = drinkType.type
//                 val.unit = drinkUnit.unit
//                 newValue += val.count;
//                 newLimit = val.limit;
//             }
//
//             for (const val of allDrinks) {
//                 let newValue = 0;
//                 if (val.type == drinkType && val.unit == drinkUnit) {
//                     val.count = newValue
//                     val.limit = newLimit
//                 }
//             }
//             //localStorage.setItem(drinkType.type, newValue + " " + drinkUnit.unit);
//             console.log(drinkType.type);
//             load();
//         });
//
// }
// function fetchAllDrinks() {
//     fetch('http://localhost:8080/api/users/404/drinks')
//         .then(res => res.json()) // the .json() method parses the JSON response into a JS object literal
//         .then(data => {
//             drinks2 = data;
//             console.log(data)
//             for (const type of drinkTypes) {
//                 let newValue = 0;
//                 for (const unit of drinkUnits) {
//                     let newValue = 0;
//                     let newLimit = 0;
//                     for (const val of drinks2) {
//                         console.log(val.unit.unit)
//                         if (val.drinkType.type == type.type && val.unit.unit == unit.unit) {
//                             console.log(val.count)
//                             newValue += val.count;
//                             newLimit = val.limit
//                             for (const val2 of allDrinks) {
//                                 if (val2.type == type.type && val2.unit == unit.unit) {
//                                     val2.count = newValue
//                                     val2.limit = newLimit
//                                     console.log(newValue)
//                                 }
//                             }
//                         }
//                     }
//
//
//                 }
//             }
//             load()
//         });
//
// }
//
// function fetchDrinkUnits() {
//     fetch('http://localhost:8080/api/units')
//         .then(res => res.json()) // the .json() method parses the JSON response into a JS object literal
//         .then(data => {
//             drinkUnits = data;
//             console.log(data)
//             fetchAllDrinks()
//         });
//
// }
// function fetchDrinkTypes() {
//     fetch('http://localhost:8080/api/drinktypes')
//         .then(res => res.json()) // the .json() method parses the JSON response into a JS object literal
//         .then(data => {
//             drinkTypes = data;
//             console.log(data)
//             fetchDrinkUnits()
//         });
//
// }
// function saveDrink() {
//     if (drinkCountInput.value) {
//         drinkCountInput.classList.remove('error');
//
//         const newDrink = {
//             user: {
//                 username: "ddd",
//                 password: "567fgg",
//                 email: "dfghj",
//                 firstName: "dfgh",
//                 lastName: "sdfghj"
//             },
//             "count": drinkCountInput.value,
//             "limit": drinkLimitInput.value,
//             "type": {
//                 "type": "fdsa"
//             },
//             "unit": {
//                 "unit": "gfdsa"
//             },
//             "drink_date": today
//         };
//
//         console.log(eventDescription.value);
//         console.log(today)
//
//         fetch('http://localhost:8080/api/users/404/drinks?drink_type_id=' + drinkTypeInput.value + '&drink_unit_id=' + drinkUnitInput.value + '&drink_date=' + today, {
//             method: 'POST',
//             headers: {
//                 'Content-Type': 'application/json'
//             },
//             body: JSON.stringify(newDrink)
//         })
//             .then(res => res.json())
//             .then(data => {
//                 console.log(data)
//                 console.log(data.type)
//                 fetchDrinks(drinkTypeInput.value, drinkUnitInput.value);
//             })
//             .catch(err => {
//                 console.error(err);
//             });
//         closeModal();
//     } else {
//         drinkCountInput.classList.add('error');
//     }
// }
//
// fetchDrinkTypes()

//---------------------------CREATING MAIN COUNTER VIEW-------------------------

var div = document.createElement("div");
div.classList.add("navigation");
div.id = "nav_drink"
// create the toggle element
var toggle = document.createElement("div");
toggle.classList.add("toggle");
div.appendChild(toggle);

// create the ul element
var ul = document.createElement("ul");
ul.id = "drinks"
// create the li elements and add them to the ul element
//ADD ENERGY DRINK
let energy_drinks = allDrinks.filter(drink => drink.type == 'energy drink' && drink.unit == 'ml');

var name1 = 'Energy drink'
var li1 = document.createElement("li");
var a1 = document.createElement("a");
a1.href = "#";
var icon1 = document.createElement("span");
icon1.classList.add("icon");
var i1 = document.createElement("i");
i1.classList.add("fa", "fa-bolt");
icon1.appendChild(i1);
var title1 = document.createElement("span");
title1.classList.add("title");
title1.innerText = name1 + ": " + energy_drinks[0].count + energy_drinks[0].unit +"/" + energy_drinks[0].limit + energy_drinks[0].unit;
a1.appendChild(icon1);
a1.appendChild(title1);
li1.appendChild(a1);
li1.addEventListener('click',function(){drinkClick(name1)})
ul.appendChild(li1);


//ADD COFFEE
let coffee = allDrinks.filter(drink => drink.type == 'coffee' && drink.unit == 'ml');
var name2 = "Coffee"
var li2 = document.createElement("li");
var a2 = document.createElement("a");
a2.href = "#";
var icon2 = document.createElement("span");
icon2.classList.add("icon");
var i2 = document.createElement("i");
i2.classList.add("fa", "fa-coffee");
icon2.appendChild(i2);
var title2 = document.createElement("span");
title2.classList.add("title");
title2.innerText = name2+": "+ coffee[0].count + coffee[0].unit +  "/" + coffee[0].limit + coffee[0].unit;
a2.appendChild(icon2);
a2.appendChild(title2);
li2.appendChild(a2);
li2.addEventListener('click',function(){drinkClick(name2)})
ul.appendChild(li2);



//ADD WATER
let water = allDrinks.filter(drink => drink.type == 'water' && drink.unit == 'ml');
var name3="Water"
var li3 = document.createElement("li");
var a3 = document.createElement("a");
a3.href = "#";
var icon3 = document.createElement("span");
icon3.classList.add("icon");
var i3 = document.createElement("i");
i3.classList.add("fa", "fa-tint");
icon3.appendChild(i3);
var title3 = document.createElement("span");
title3.classList.add("title");
title3.innerText = name3+": " + water[0].count + water[0].unit + "/" + water[0].limit + water[0].unit ;
a3.appendChild(icon3);
a3.appendChild(title3);
li3.appendChild(a3);
li3.addEventListener('click',function(){drinkClick(name3)})
ul.appendChild(li3);


div.appendChild(ul);

// add the div element to the document body
document.body.appendChild(div);


const navigation = document.querySelector('.navigation');

document.querySelector('.toggle').ondblclick = function (){
    //on doubleclick
    this.classList.toggle('active');
    navigation.classList.toggle('active');
}


//----------------------------CREATING VIEW FOR LIMITS AND UNITS CHANGE----------------------------------
//div
var d_div = document.createElement("div");
d_div.id = "form_drink";
d_div.classList.add('form_drink')
d_div.style.visibility = 'hidden'

// Create a break line element
var br = document.createElement("br");
const units_list = ["l",'ml']

//create display for drink name
var d_name = document.createElement('h1');
d_name.id = 'drinkTitle'
d_name.innerText = "";


//display editing view for limits and units
var form = document.createElement("div");
// form.setAttribute("method", "post");
form.classList.add('form')

// Create label element for limit input
const limitLabel = document.createElement('label');
limitLabel.textContent = 'Limit: ';
limitLabel.setAttribute('for', 'limit');
form.appendChild(limitLabel);

// Create input element for limit
const limitInput = document.createElement('input');
limitInput.setAttribute('type', 'number');
limitInput.setAttribute('id', 'limit');
limitInput.setAttribute('name', 'limit');
limitInput.setAttribute('required', true);
form.appendChild(limitInput);

// Create select element for units
const unitSelect = document.createElement('select');
unitSelect.setAttribute('id', 'unit');
unitSelect.setAttribute('name', 'unit');

// var units_list = ["l","ml"]

// Create option elements for select

for (let j = 0; j < units_list.length;j++){
    console.log(units_list[j])
    const lOption = document.createElement('option');
    lOption.setAttribute('value', units_list[j]);
    lOption.textContent = units_list[j];
    unitSelect.appendChild(lOption);
}



form.appendChild(unitSelect);
form.appendChild(br.cloneNode());
form.appendChild(br.cloneNode());

// Create submit button element
const submitButton = document.createElement('button');
submitButton.setAttribute('type', 'submit');
submitButton.classList.add('btnFrom');
submitButton.textContent = 'Submit';
form.appendChild(submitButton);
// form.appendChild(br.cloneNode());
// form.appendChild(br.cloneNode());

// Create cancel button element
const cancelButton = document.createElement('button');
cancelButton.classList.add('btn')
// cancelButton.setAttribute('type', 'submit');
cancelButton.textContent = 'ᗕ';
cancelButton.addEventListener('click',function(){
    hideShowDrinks();
    hideShowFormView();
    // console.log('back button clicked');
})

form.appendChild(br.cloneNode());
form.appendChild(br.cloneNode());

form.style = "text-align: center"

form.onsubmit = function(){
    alert("Changes saved");
    hideShowDrinks();
    hideShowFormView();

};

d_div.appendChild(cancelButton);
d_div.appendChild(d_name);
d_div.appendChild(form)

document.getElementById("nav_drink").appendChild(d_div)


//-----------------------------FUNCTIONS---------------------------------------
function drinkClick(elem){
    let name = elem
    console.log(name)
    document.getElementById('drinkTitle').innerText=name;
    hideShowDrinks();
    hideShowFormView();
}

function hideShowFormView(){
    var p = document.getElementById("form_drink").style.visibility
    if (p == 'hidden'){
        document.getElementById("form_drink").style.visibility = 'visible'
    }else{
        document.getElementById("form_drink").style.visibility ='hidden'
    }
}
function hideShowDrinks(){
    var d = document.getElementById("drinks").style.visibility
    if (d == 'hidden'){
        document.getElementById("drinks").style.visibility = 'visible'
        fetchDrinkTypes()
    }else{
        document.getElementById("drinks").style.visibility ='hidden'
    }
}
