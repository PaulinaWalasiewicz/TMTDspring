let tasks = [];
let users = [];
let items = [];
let idUser = '404';

function getTasks(idUser) {
    fetch(`http://localhost:8080/api/users/${idUser}/tasks`)
        .then(res => {
            if(!res.ok){
                console.log("problem");
                return;
            }
            return res.json();
        })
        .then(data => {
            tasks = data;
            console.log(tasks)
            if(tasks && tasks.length > 0){
                console.log("Załadowano taski do tablicy");
            }
            else{
                console.log("No Tasks found");
            }
        })
        .catch(error => {
            console.log("error")
        });
}
getTasks(idUser)

function postTask(idUser) {
    fetch(`http://localhost:8080/api/users/${idUser}/tasks`)
        .then(res => {
            if(!res.ok){
                console.log("problem");
                return;
            }
            return res.json();
        })
        .then(data => {
            tasks = data;
            console.log(tasks)
            if(tasks && tasks.length > 0){
                console.log("Załadowano taski do tablicy");
            }
            else{
                console.log("No Tasks found");
            }
        })
        .catch(error => {
            console.log("error")
        });
}

todoMain();
function todoMain() {
    let inputElem
    let ulElem
    getElements();
    addListeners();

    function getElements(){
        inputElem = document.getElementsByTagName('input')[0];
        ulElem = document.getElementsByTagName('ul')[0];
    }

    function addListeners(){
        inputElem.addEventListener("change", onChange, false);
    }

    function onChange(event){
        let inputValue = inputElem.value;
        inputElem.value = "";
        console.log(inputValue);
        let liElem = document.createElement("li");
        liElem.innerText = inputValue;
        ulElem.appendChild(liElem);

        const newTask = {
            title: inputValue,
            description: {
                content: "descriptionContent"
            },
            startTime: "2016-03-04 11:08",
            completed: false,
            user: {
                username: "admin",
                password: "admin",
                email: "dfghj",
                firstName: "dfgh",
                lastName: "sdfghj"
            },
            category: {
                content: "categoryContent"
            },
            priority: {
                content: "priorityContent"
            }
        };

        const options = {
            method: 'POST',
            body: JSON.stringify(newTask),
            headers: {
                'Content-Type': 'application/json'
            }
        };
        fetch('http://localhost:8080/api/users/404/tasks', options)
        console.log(newTask.value);
    }
}