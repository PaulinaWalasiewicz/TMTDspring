fetch("https://localhost:8080/api/users/404/events")
    .then(response => response.json())
    .then(data => {
        console.log(data);
    });
