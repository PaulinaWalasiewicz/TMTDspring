let tasks = [];
let users = [];
let items = [];
var idUser = '404';

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
            console.log(tasks.length)
            if(tasks && tasks.length > 0){
                console.log("Załadowano taski do tablicy");
                tasks.map((task) => {
                    createTask(task);
                })
                countTasks();
            }
            else{
                console.log("No Tasks found");
            }
        })
        .catch(error => {
            console.log("error")
        });
}
getTasks(idUser);
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
function putTask(taskId, _title, _completed) {
    const putTask = {
        title: _title,
        description: {
            content: "descriptionContent"
        },
        dueDate: "2016-03-04 11:08",
        completed: _completed,
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

    fetch(`http://localhost:8080/api/tasks/${taskId}?user_id=${idUser}&description_id=4304&priority_id=2002&category_id=2052`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(putTask)
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
            countTasks();
        })
        .catch(error => {
            console.log(error)
        });
}

function deleteTasks(idUser) {
    fetch(`http://localhost:8080/api/users/${idUser}/tasks`, {
        method: 'DELETE'
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

function deleteTask(idTask) {
    fetch(`http://localhost:8080/api/tasks/${idTask}`, {
        method: 'DELETE'
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

const todoForm = document.querySelector('#todo-form');
const todoList = document.querySelector('.todos');
const totalTask = document.querySelector('#total-task');
const completedTask = document.querySelector('#completed-task');
const remainingTask = document.querySelector('#remaining-task');
const mainInput = document.querySelector('#task-name');
const deleteAllBtn = document.querySelector('#delete-todo');


todoForm.addEventListener('submit', (e)=>{
    e.preventDefault();

    const inputValue = mainInput.value;

    if(inputValue == " "){
        return
    }
    const newTask = {
        title: inputValue,
        // description: {
        //     content: "descriptionContent"
        // },
        dueDate: "2016-03-04 11:08",
        completed: false,
        // user: {
        //     username: "admin",
        //     password: "admin",
        //     email: "dfghj",
        //     firstName: "dfgh",
        //     lastName: "sdfghj"
        // },
        // category: {
        //     content: "categoryContent"
        // },
        // priority: {
        //     content: "priorityContent"
        // }
    };
    console.log(newTask)
    postTask(idUser, inputValue);
    // getTasks(idUser);
    createTask(newTask);
    todoForm.reset();
    mainInput.focus();
    })

todoList.addEventListener("click", (e) => {
    if (e.target.classList.contains("remove-task")) {
        const taskId = e.target.closest('li').id;
        console.log("Clic "+ taskId)
        removeTask(taskId);
    }
})

todoList.addEventListener('keydown', (e) => {
    if(e.keyCode ==13){
        e.preventDefault();

        e.target.blur()
    }
})

todoList.addEventListener("input", (e) => {
    const taskId = e.target.closest('li').id;
    updateTask(taskId, e.target)
})

function createTask(task){
    const taskEl = document.createElement('li');

    taskEl.setAttribute('id', task.id)

    if( task.completed){
        taskEl.classList.add('complete');
    }

    const taskElMarkup =
    `<div>
        <input type="checkbox" name="tasks" id="${task.id}" ${task.completed ? 'checked' : ''} >
        <span ${!task.completed ? 'contenteditable' : ''} > ${task.title}</span>
    </div>
    <button title="remove the '${task.title}' task" class="remove-task" > remove </button> `

    taskEl.innerHTML = taskElMarkup;

    todoList.appendChild(taskEl)
}

function countTasks(){
    const completedTasksArray = tasks.filter((task) =>{
        task.completed === "true"
        console.log(task.completed);
    })
    console.log("liczy taski")
    totalTask.textContent = tasks.length
    completedTask.textContent = completedTasksArray.length
    remainingTask.textContent = tasks.length - completedTasksArray.length
}

function removeTask(taskId){
    tasks = tasks.filter((task) =>
        task.id !== parseInt(taskId)
    )
    deleteTask(taskId);
    console.log("cliccck: "+ taskId);
    document.getElementById(taskId).remove();
    countTasks();
}

function updateTask(taskId, el){
    const task = tasks.find((task) => task.id == parseInt(taskId))

    if(el.hasAttribute('contenteditable')){
        task.title = el.textContent;
    } else {
        const span = el.nextElementSibling;
        const parent = el.closest('li');

        task.completed = !task.completed;

        if(task.completed) {
            span.removeAttribute('contenteditable')
            parent.classList.add('complete')
        } else {
            span.setAttribute('contenteditable', 'true')
            parent.classList.remove('complete')
        }
        console.log("spanV: "+ task.title);
        console.log("completed: "+ task.completed)
        putTask(taskId, task.title, task.completed)
        countTasks();
    }
}