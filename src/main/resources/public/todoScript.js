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
            console.log(tasks[0].id)
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
// function postTask(_title, _description, _dueDate, _idUser, _category, _priority ) {
function postTask(idUser,_title ) {
    const newTask = {
        title: _title,
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

    fetch(`http://localhost:8080/api/users/${idUser}/tasks?user_id=${idUser}&description_id=4304&priority_id=2002&category_id=2052`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(newTask)
    })
        .then(res => {
            if(!res.ok){
                console.log("problem");
                return;
            }
            return res.json();
        })
        .then(data => {
            console.log("Succes")
        })
        .catch(error => {
            console.log(error)
        });
}
// function postTask(_title, _description, _dueDate, _idUser, _category, _priority ) {
// function putTask(taskId, _title) {
//     const putTask = {
//         title: _title,
//         description: {
//             content: "descriptionContent"
//         },
//         startTime: "2016-03-04 11:08",
//         completed: false,
//         user: {
//             username: "admin",
//             password: "admin",
//             email: "dfghj",
//             firstName: "dfgh",
//             lastName: "sdfghj"
//         },
//         category: {
//             content: "categoryContent"
//         },
//         priority: {
//             content: "priorityContent"
//         }
//     };
//
//     fetch(`http://localhost:8080/api/tasks/${taskId}`, {
//         method: 'POST',
//         headers: {
//             'Content-Type': 'application/json'
//         },
//         body: JSON.stringify(newTask)
//     })
//         .then(res => {
//             if(!res.ok){
//                 console.log("problem");
//                 return;
//             }
//             return res.json();
//         })
//         .then(data => {
//             console.log("Succes")
//         })
//         .catch(error => {
//             console.log(error)
//         });
// }
function deleteTasks(idUser) {
    fetch(`http://localhost:8080/api/users/${idUser}/tasks`, {
        method: 'DELETE',
    })
        .then(res => {
            if(!res.ok){
                console.log("problem");
                return;
            }
            return res.json();
        })
        .then(data => {
            console.log("Succes delete")
        })
        .catch(error => {
            console.log(error)
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
        postTask(idUser, inputValue);
    }
}