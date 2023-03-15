let tasks = [];
let users = [];
let items = [];
var idUser = '404';

function getTasks(idUser) {
    fetch(`http://localhost:8080/api/users/${idUser}/tasks`)
        .then(res => {
            if(!res.ok){
                console.log("getTasks(): Problem");
                return;
            }
            return res.json();
        })
        .then(data => {
            tasks = data;
            console.log("getTasks(): " + data)
            if(tasks && tasks.length > 0){
                console.log("getTasks(): Loaded tasks to array");
                tasks.map((task) => {
                    console.log("Task: " + task.priority);
                    createTask(task);
                })
                countTasks();
            }
            else{
                console.log("getTasks(): No Tasks found");
            }
        })
        .catch(error => {
            console.log("getTasks(): error " + tasks.length)
        });
}
getTasks(idUser);

function postTask(idUser, _title, _priority, _dueDate) {
    const newTask = {
        title: _title,
        description: {
            content: "descriptionContent"
        },
        startTime: _dueDate,
        completed: false,
        user: {
            username: "admin",
            password: "admin",
            email: "email@adnim",
            firstName: "Adam",
            lastName: "Kowalski"
        },
        category: {
            content: "categoryContent"
        },
        priority: _priority
    };
    console.log("postTask(): ---Priority "+newTask.priority)
    let priorityId = 11952
    if(_priority === "medium"){
        priorityId = 11953;
    }
    if(_priority === "high"){
        priorityId = 11954;
    }
    // console.log("priorityId: " + priorityId)

    fetch(`http://localhost:8080/api/users/${idUser}/tasks?user_id=${idUser}&description_id=4304&category_id=2052`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(newTask)
    })
        .then(res => {
            if(!res.ok){
                console.log("postTask(): Problem with fetch");
                return;
            }
            return res.json();
        })
        .then(data => {
            countTasks();
            console.log("postTask(): Success " + data.toString())
        })
        .catch(error => {
            console.log("postTask(): Problem")
        });
    console.log("How do new task look: "+ newTask.priority +" string "+ JSON.stringify(newTask))
    tasks.push(newTask);
    countTasks();

}

function putTask(taskId, _title, _completed, _priority) {
    const putTask = {
        title: _title,
        description: {
            content: "descriptionContent"
        },
        dueDate: '2016-03-04 11:08',
        completed: _completed,
        user: {
            username: "admin",
            password: "admin",
            email: "email@adnim",
            firstName: "Adam",
            lastName: "Kowalski"
        },
        category: {
            content: "categoryContent"
        },
        priority: _priority
    };
    console.log("putTask(): ---Priority"+putTask.priority +" string " + JSON.stringify(putTask) )
    let priorityId = 11952
    if(_priority === "medium"){
        priorityId = 11953;
    }
    if(_priority === "high"){
        priorityId = 11954;
    }
    console.log("priorityId: " + priorityId)

    fetch(`http://localhost:8080/api/tasks/${taskId}?user_id=${idUser}&description_id=4304&category_id=2052`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(putTask)
    })
        .then(res => {
            if(!res.ok){
                console.log("putTask(): Problem with fetch");
                return;
            }
            return res.json();
        })
        .then(data => {
            console.log("putTask(): Success")
            countTasks();
        })
        .catch(error => {
            console.log("putTask(): Problem")
        });
}

function deleteTasks(idUser) {
    fetch(`http://localhost:8080/api/users/${idUser}/tasks`, {
        method: 'DELETE'
    })
        .then(res => {
            if(!res.ok){
                console.log("deleteTasks(): Problem with fetch");
                return;
            }
            return res.json();
        })
        .then(data => {
            console.log("deleteTasks(): Success   delete all User's task ")
        })
        .catch(error => {
            console.log("deleteTasks(): Problem")
        });
}

function deleteTask(idTask) {
    fetch(`http://localhost:8080/api/tasks/${idTask}`, {
        method: 'DELETE'
    })
        .then(res => {
            if(!res.ok){
                console.log("deleteTask(): Problem with fetch");
                return;
            }
            return;
        })
        .then(data => {
            console.log("deleteTask(): Success delete task")
        })
        .catch(error => {
            console.log("deleteTask(): Problem")
        });
}

const todoForm = document.querySelector('#todo-form');
const todoList = document.querySelector('.todos');
const totalTask = document.querySelector('#total-task');
const completedTask = document.querySelector('#completed-task');
const remainingTask = document.querySelector('#remaining-task');
const titleInput = document.querySelector('#task-name');
const priorityInput = document.querySelector('#task-priority');
const dueDateInput = document.querySelector('#task-date');
const deleteAllBtn = document.querySelector('#delete-todo');


todoForm.addEventListener('submit', (e)=>{
    e.preventDefault();

    const inputValue_title = titleInput.value;
    const inputValue_priority = priorityInput.value;
    console.log("Priority: " + inputValue_priority);
    const inputValue_dueDate = dueDateInput.value + " 12:00";
    console.log("DueDate: " + inputValue_dueDate);


    if(inputValue_title == " "){
        return
    }
    const newTask = {
        title: inputValue_title,
        description: {
            content: "descriptionContent"
        },
        dueDate: inputValue_dueDate,
        completed: false,
        user: {
            username: "admin",
            password: "admin",
            email: "email@adnim",
            firstName: "Adam",
            lastName: "Kowalski"
        },
        category: {
            content: "categoryContent"
        },
        priority: inputValue_priority
    };
    // console.log("---Priority"+ newTask.priority)
    console.log("New task: " + newTask.title, newTask.completed, newTask.priority)
    postTask(idUser, inputValue_title, inputValue_priority, inputValue_dueDate);
    // getTasks(idUser);
    createTask(newTask);

    todoForm.reset();
    titleInput.focus();
    priorityInput.focus();
    dueDateInput.focus();
    })

todoList.addEventListener("click", (e) => {
    if (e.target.classList.contains("remove-task")) {
        const taskId = e.target.closest('li').id;
        console.log("Removing task: "+ taskId)
        removeTask(taskId);
    }
})

todoList.addEventListener('keydown', (e) => {
    if(e.keyCode ==13){
        e.preventDefault();
        console.log("Clicking on task : ")
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

    if(task.completed){
        taskEl.classList.add('complete');
    }

    console.log("createTask(): "+ JSON.stringify(task));

    const taskElMarkup =
    `<div >
        <input type="checkbox" name="tasks" id="${task.id}" ${task.completed ? 'checked' : ''} >
        <span ${!task.completed ? 'contenteditable' : ''} > ${task.title}</span>
</div>
    <div style="display:flex; width: 100% !important;">
        <button title="remove the '${task.title}' task" class="remove-task" style="margin-top:5px;background-color:#ffeae7;color: #D33A2C;border-radius: 11px;padding: 5px;border-radius: 11px;border: 2px solid #FEE0E0;">
            remove </button>
        <h5 class="priority-task" style="margin-top:5px;margin-left: 20px;margin-bottom: 10px;display:inline;align-items: center;background-color: #fde7ff;border-radius: 11px;border: 2px solid #fde7ff;box-sizing: border-box;color: #d32cc5;cursor: default;padding: 2px;text-align: center;word-break: break-word;">
            priority ${task.priority}</h5>
        <h5 class="date-task" style="margin-top:5px;margin-left: 20px;margin-bottom: 10px;display:inline;align-items: center;background-color: #fdffd8;border-radius: 11px;border: 2px solid #fdffd8;box-sizing: border-box;color: #d3c82c;cursor: default;padding: 2px;text-align: center;word-break: break-word;
      ">due date: ${task.dueDate}</h5>
    </div>`

    taskEl.innerHTML = taskElMarkup;

    todoList.appendChild(taskEl)
}

function countTasks(){
    const completedTasksArray = []

    for (let i=0; i < tasks.length; i++){
        const task = tasks[i];
        if(task.completed) {
            completedTasksArray.push(task)
        }
    }
    // console.log("completedTasksArray length: " + completedTasksArray.length)
    // console.log("countTasks(): Counting tasks")
    totalTask.textContent = tasks.length
    completedTask.textContent = completedTasksArray.length
    remainingTask.textContent = tasks.length - completedTasksArray.length
}
function removeTask(taskId){
    tasks = tasks.filter((task) =>
        task.id !== parseInt(taskId)
    )
    deleteTask(taskId);
    console.log("removeTask(): Clicked to remove: "+ taskId);
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
        console.log("updateTask(): dueDate: "+ task.dueDate);
        console.log("updateTask(): Priority: "+ task.priority);
        console.log("updateTask(): " + JSON.stringify(task));
        putTask(taskId, task.title, task.completed, task.priority)
        countTasks();
    }
}