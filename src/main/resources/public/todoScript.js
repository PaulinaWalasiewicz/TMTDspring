let users = [];
var tasks_ls;
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
    getTasks();
};


function generateId() {
    const timestamp = Date.now();
    const randomNum = Math.random();
    return `${timestamp}-${randomNum}`;
}
function getTasks() {
    tasks_ls = JSON.parse(localStorage.getItem("tasks_ls")) || [];
    console.log("getTasks(): " + tasks_ls.length);
    tasks_ls.forEach(function (task) {
        console.log("[]: " + task.id);
        createTask(task);
    });
}

function putTask(_taskId, _title, _completed, _priority,_dueDate) {
    console.log("putTask(): taskId: " + _taskId);
    const putTask = {
        id: _taskId,
        title: _title,
        description: {
            content: "descriptionContent",
        },
        dueDate: _dueDate,
        completed: _completed,
        user: {
            username: "admin",
            password: "admin",
            email: "email@adnim",
            firstName: "Adam",
            lastName: "Kowalski",
        },
        category: {
            content: "categoryContent",
        },
        priority: _priority,
    };
    const taskIndex = tasks_ls.findIndex((task) => task.id === _taskId);
    if (taskIndex !== -1) {
        tasks_ls[taskIndex] = putTask;
    }

    localStorage.setItem("tasks_ls", JSON.stringify(tasks_ls));
}

function deleteTask(idTask) {
    let tasks_ls = JSON.parse(localStorage.getItem("tasks_ls"));
    const taskIndex = tasks_ls.findIndex((task) => task.id === idTask);
    if (taskIndex !== -1) {
        tasks_ls.splice(taskIndex, 1);
    }
    console.log("deleteTask(): " + taskIndex);
    localStorage.removeItem(taskIndex);
    localStorage.setItem("tasks_ls", JSON.stringify(tasks_ls));
    console.log("deleteTask(): " + tasks_ls.length);
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
const sortDueDateBtn = document.querySelector("#sort-by-duedate");

todoForm.addEventListener('submit', (e)=>{
    e.preventDefault();

    const inputValue_title = titleInput.value;
    const inputValue_priority = priorityInput.value;
    const inputValue_dueDate = dueDateInput.value + " 12:00";


    if(inputValue_title == " "){
        return
    }
    const newTask = {
        id: generateId(),
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
    tasks_ls.push(newTask);
    localStorage.setItem("tasks_ls", JSON.stringify(tasks_ls));    createTask(newTask);

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
        e.target.blur()
    }
})

todoList.addEventListener("input", (e) => {
    const taskId = e.target.closest('li').id;
    updateTask(taskId, e.target)
})

sortDueDateBtn.addEventListener("click", (e) => {
    removeTasksHTML();
    console.log("addEventListener: sortDueDateBtn");
    tasks_ls.sort(sortByDueDate);
    tasks_ls.forEach(function (task) {
        console.log("[]: " + task.id);
        createTask(task);
    });
    console.log(tasks_ls);
});

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

    for (let i=0; i < tasks_ls.length; i++){
        const task = tasks_ls[i];
        if(tasks_ls.completed) {
            completedTasksArray.push(task)
        }
    }
    totalTask.textContent = tasks_ls.length
    completedTask.textContent = completedTasksArray.length
    remainingTask.textContent = tasks_ls.length - completedTasksArray.length
}
function removeTask(taskId){
    console.log("removeTask(): " + taskId);
    tasks = tasks_ls.filter((task) => task.id !== parseInt(taskId));
    deleteTask(taskId);
    document.getElementById(taskId).remove();
    countTasks();
}
function removeTasksHTML() {
    console.log("removeTasksHTML(): ");
    tasks_ls.forEach(function (task) {
        console.log("removeTasksHTML(): " + task.id);
        document.getElementById(task.id).remove();
    });
}

function updateTask(taskId, el){
    console.log("updateTask(): ", taskId);
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
        putTask(task.id, task.title, task.completed, task.priority, task.dueDate)
        countTasks();
    }
}

function sortByDueDate(taskA, taskB) {
    const dueDateA = new Date(taskA.dueDate);
    const dueDateB = new Date(taskB.dueDate);

    if (dueDateA < dueDateB) {
        return -1;
    } else if (dueDateA > dueDateB) {
        return 1;
    } else {
        return 0;
    }
}