// ##################################################DRINK WEEK#############################################################

fetch('http://localhost:8080/api/analysisData/DrinkWeek')
    .then(response => response.json())
    .then(data => {
        var drinkData = {};
        console.log(data)

        debugger;
        data.body.forEach(drink => {
            if (drink.drink_type in drinkData) {
                drinkData[drink.drink_type]+= parseInt(drink.count);
            } else {
                drinkData[drink.drink_type] = parseInt(drink.count);
            }
        });
        // Convert object properties into an array of key-value pairs
        var sortedArray = Object.entries(drinkData).sort();

        // Create a new object from the sorted array
        var sortedObject = Object.fromEntries(sortedArray);
        const drinkLabels = Object.keys(sortedObject);
        const drinkCounts = Object.values(sortedObject);
        var title = "Drinks you drank this week: "
        createChart(drinkLabels, drinkCounts,title,"DrinkWeek");
    });

// #################################################DRINK MONTH##############################################################
fetch('http://localhost:8080/api/analysisData/DrinkMonth')
    .then(response => response.json())
    .then(data2 => {
        var drinkdata2 = {};
        console.log(data2)
        data2.body.forEach(drink => {
            if (drink.drink_type in drinkdata2) {
                drinkdata2[drink.drink_type]+= parseInt(drink.count);
            } else {
                drinkdata2[drink.drink_type] = parseInt(drink.count);
            }
        });
        // Convert object properties into an array of key-value pairs
        var sortedArray = Object.entries(drinkdata2).sort();

        // Create a new object from the sorted array
        var sortedObject = Object.fromEntries(sortedArray);

        const drinkLabels2 = Object.keys(sortedObject);
        const drinkCounts2 = Object.values(sortedObject);
        const title = 'Drinks you drank this month: ';
        createChart(drinkLabels2, drinkCounts2,title,"DrinkMonth");
    });
function createChart(labels, data2,title,chartID) {

    const chartData = {
        labels: labels,
        datasets: [{
            label: title,
            data: data2,
            backgroundColor: ['rgba(156, 89, 15, 0.7)','rgba(39, 174, 96, 0.7)','rgba(52, 152, 219, 0.7)'],
            borderColor: ['rgba(156, 89, 15, 0.7)'.replace(/[^,]+(?=\))/, '1'),'rgba(39, 174, 96, 0.7)'.replace(/[^,]+(?=\))/, '1'),'rgba(52, 152, 219, 0.7)'.replace(/[^,]+(?=\))/, '1')],
            borderWidth: 1
        }]
    };
    const chartOptions = {
        scales: {
            // yAxes: [{
            //     ticks: {
            //         beginAtZero: true
            //     }
            // }]
        }
    };
    const ctx = document.getElementById(chartID).getContext('2d');
    const chart = new Chart(ctx, {
        type: 'bar',
        data: chartData,
        options: chartOptions
    });
}
// #################################################TASKS WEEK##############################################################
fetch('http://localhost:8080/api/analysisData/TaskWeek')
    .then(response => response.json())
    .then(data => {
        var completedCount = 0;
        var incompleteCount = 0;
        data.body.forEach(task => {
            if (task.completed) {
                completedCount++;
            } else {
                incompleteCount++;
            }
        });
        const chartData = {
            datasets: [{

                data: [completedCount, incompleteCount],
                backgroundColor: [
                    'rgba(39, 174, 96, 0.7)',
                    'rgba(231, 76, 60, 0.7)'
                ],
                borderColor: [
                    'rgba(39, 174, 96, 1)',
                    'rgba(231, 76, 60, 1)'
                ],
                borderWidth: 1
            }],
            labels: [
                'Completed',
                'Incomplete'
            ]
        };
        const chartOptionsTW = {
            responsive: true,
            legend: {
                position: 'top',
            },
            animation: {
                animateScale: true,
                animateRotate: true
            },
            plugins:{
                title:{
                    display: true,
                    text:"Weekly"
                }
            }
        };
        const ctx = document.getElementById('TaskWeek').getContext('2d');
        const chart = new Chart(ctx, {
            type: 'doughnut',
            data: chartData,
            options: chartOptionsTW
        });
    });
// #################################################TASKS WEEK##############################################################
fetch('http://localhost:8080/api/analysisData/TaskMonth')
    .then(response => response.json())
    .then(data => {
        let completedCount = 0;
        let incompleteCount = 0;
        data.body.forEach(task => {
            if (task.completed) {
                completedCount++;
            } else {
                incompleteCount++;
            }
        });
        const chartData2 = {
            datasets: [{
                data: [completedCount, incompleteCount],
                backgroundColor: [
                    'rgba(39, 174, 96, 0.7)',
                    'rgba(231, 76, 60, 0.7)'
                ],
                borderColor: [
                    'rgba(39, 174, 96, 1)',
                    'rgba(231, 76, 60, 1)'
                ],
                borderWidth: 1
            }],
            labels: [
                'Completed',
                'Incomplete'
            ]
        };
        const chartOptionsTM = {
            responsive: true,
            legend: {
                position: 'top',
            },
            animation: {
                animateScale: true,
                animateRotate: true
            },
            plugins:{
                title:{
                    display: true,
                    text:"Monthly"
                }
            }
        };
        const ctx = document.getElementById('TaskMonth').getContext('2d');
        const chart = new Chart(ctx, {
            type: 'doughnut',
            data: chartData2,
            options: chartOptionsTM
        });
    });
