        const idUser = "404";
        function getTasks() {
        fetch(`http://localhost:8080/api/users/${idUser}/tasks`)
            .then(response => response.json())
            .then(data => {
                var ul = document.getElementById("taskList");
                data.forEach(task => {
                    console.log(task);
                    console.log(task.title);
                    var tasks = data;
                    // document.querySelector("#tasks-items-container").innerHTML = task.title;
                    var divTask = document.createElement("div");
                    divTask.appendChild(document.createTextNode(task.title));
                    divTask.appendChild(document.createTextNode(task.dueDate));
                    divTask.appendChild(document.createTextNode(task.completed));

                    //create the edit button
                    var editButton = document.createElement("button");
                    editButton.innerHTML = "Edit";
                    editButton.onclick = function() {
                         editTask(task.id);
                    };
                    divTask.appendChild(editButton);
                    
                    //create the delete button
                    // var deleteButton = document.createElement("button");
                    // deleteButton.innerHTML = "Delete";
                    // deleteButton.onclick = function() {
                    //     deleteTask(task.id);
                    // };
                    // divTask.appendChild(deleteButton);
                    //
                    // //create the done button
                    // var doneButton = document.createElement("button");
                    // doneButton.innerHTML = "Done";
                    // doneButton.onclick = function() {
                    //     markTaskAsDone(task.id);
                    // };
                    // divTask.appendChild(doneButton);
                    //
                    ul.appendChild(divTask);
                });
            });
    }

        function editTask(taskId) {
        var taskName = prompt("Enter new task name: ");
        if (taskName) {
        fetch(`http://localhost:8080/api/tasks/${taskId}`, {
        method: "PUT",
        body: JSON.stringify({ name: taskName }),
        headers: { "Content-Type": "application/json" }
    })
        .then(response => response.json())
        .then(data => {
        console.log("Task updated: ", data);
        getTasks();
    });
    }
    }

        function deleteTask(taskId) {
        if (confirm("Are you sure you want to delete this task?")) {
        fetch(`http://localhost:8080/api/tasks/${taskId}`, {
        method: "DELETE"
    })
        .then(response => response.json())
        .then(data => {
        console.log("Task deleted: ", data);
        getTasks();
    });
    }
    }

        // function markTaskAsDone(taskId) {
        //     fetch("https://example.com/api/tasks/" + taskId + "/done", {
        //         method: "PUT"
        //     })
        //         .then(response => response.json())
        //         .then(data => {
        //             console.log("Task marked as done: ", data);
        //             getTasks();
        //         });
        // }
