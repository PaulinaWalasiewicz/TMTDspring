let tasks = [];
let users = [];
let items = [];
var idUser = '404';

var pomodoro = {
    started : false,
    minutes : 0,
    seconds : 0,
    fillerHeight : 0,
    fillerIncrement : 0,
    interval : null,
    minutesDom : null,
    secondsDom : null,
    fillerDom : null,
    init : function(){
        var self = this;
        this.minutesDom = document.querySelector('#minutes');
        this.secondsDom = document.querySelector('#seconds');
        this.fillerDom = document.querySelector('#filler');
        this.interval = setInterval(function(){
            self.intervalCallback.apply(self);
        }, 1000);
        document.querySelector('#work').onclick = function(){
            self.startWork.apply(self);
        };
        document.querySelector('#shortBreak').onclick = function(){
            self.startShortBreak.apply(self);
        };
        document.querySelector('#longBreak').onclick = function(){
            self.startLongBreak.apply(self);
        };
        document.querySelector('#stop').onclick = function(){
            self.stopTimer.apply(self);
        };
    },
    resetVariables : function(mins, secs, started){
        this.minutes = mins;
        this.seconds = secs;
        this.started = started;
        this.fillerIncrement = 200/(this.minutes*60);
        this.fillerHeight = 0;
    },
    startWork: function() {
        this.resetVariables(25, 0, true);
    },
    startShortBreak : function(){
        this.resetVariables(5, 0, true);
    },
    startLongBreak : function(){
        this.resetVariables(15, 0, true);
    },
    stopTimer : function(){
        this.resetVariables(25, 0, false);
        this.updateDom();
    },
    toDoubleDigit : function(num){
        if(num < 10) {
            return "0" + parseInt(num, 10);
        }
        return num;
    },
    updateDom : function(){
        this.minutesDom.innerHTML = this.toDoubleDigit(this.minutes);
        this.secondsDom.innerHTML = this.toDoubleDigit(this.seconds);
        this.fillerHeight = this.fillerHeight + this.fillerIncrement;
        this.fillerDom.style.height = this.fillerHeight + 'px';
    },
    intervalCallback : function(){
        if(!this.started) return false;
        if(this.seconds == 0) {
            if(this.minutes == 0) {
                this.timerComplete();
                return;
            }
            this.seconds = 59;
            this.minutes--;
        } else {
            this.seconds--;
        }
        this.updateDom();
    },
    timerComplete : function(){
        this.started = false;
        this.fillerHeight = 0;
    }
};
window.onload = function(){
    pomodoro.init();
};

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
    debugger;
    const newTask = {
        title: _title,
        description:  "descriptionContent",
        dueDate: _dueDate,
        completed: false,
        user: {
            username: "admin",
            password: "admin",
            email: "email@adnim",
            firstName: "Adam",
            lastName: "Kowalski"
        },
        category: "TRAVEL",
        priority: _priority
    };

    fetch(`http://localhost:8080/api/users/${idUser}/tasks?user_id=${idUser}&description=text&category=TRAVEL`, {
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
    console.log("How do new task look: string "+ JSON.stringify(newTask))
    tasks.push(newTask);
    countTasks();

}

function putTask(taskId, _title, _completed, _priority) {
    debugger;
    const putTask = {
        title: _title,
        description:  "descriptionContent",
        dueDate: '2016-03-04 11:08',
        completed: _completed,
        user: {
            username: "admin",
            password: "admin",
            email: "email@adnim",
            firstName: "Adam",
            lastName: "Kowalski"
        },
        category: "TRAVEL",
        priority: _priority
    };
    console.log("putTask(): ---Priority"+putTask.priority +" string " + JSON.stringify(putTask) )

    fetch(`http://localhost:8080/api/tasks/${taskId}?user_id=${idUser}&description=text&category=TRAVEL`, {
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
    const inputValue_dueDate = dueDateInput.value + " 12:00";


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
    debugger;
    if (e.target.classList.contains("remove-task")) {
        const taskId = e.target.closest('li').id;
        console.log("Removing task: "+ taskId)
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

    if(task.completed){
        taskEl.classList.add('complete');
    }


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
        // console.log("updateTask(): dueDate: "+ task.dueDate);
        // console.log("updateTask(): Priority: "+ task.priority);
        // console.log("updateTask(): " + JSON.stringify(task));
        putTask(taskId, task.title, task.completed, task.priority)
        countTasks();
    }
}