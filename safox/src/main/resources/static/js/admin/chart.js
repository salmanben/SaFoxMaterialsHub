/********** Start Chart **********/

const months = [
    "January",
    "February",
    "March",
    "April",
    "May",
    "June",
    "July",
    "August",
    "September",
    "October",
    "November",
    "December"
];

var bgYear = [
    '#9381ff',
    '#06d6a0',
    '#4361ee',
    '#ef476f',
    '#ffd60a',
    '#03045e',
    '#e1e5f2',
    '#9381ff',
    '#06d6a0',
    '#4361ee',
    '#ef476f',
    '#ffd60a'
];


var todayEarnings = document.querySelector('.today-earnings');
var todayReservations = document.querySelector('.today-reservations');
var todayReservedMaterials = document.querySelector('.today-reserved-materials');

fetch("/admin/dashboard/today-statistics")
.then(response => response.json())
.then(data => {
    todayReservations.innerHTML = data[0]
    todayReservedMaterials.innerHTML = data[1]
    todayEarnings.innerHTML ='$' + data[2]

})


fetch("/admin/dashboard/year-statistics")
.then(response => response.json())
.then(data => {
    var canvasYearEarnings = document.getElementById('canvas-year-earnings');
var ctxYearEarnings = canvasYearEarnings.getContext('2d');
var chartYearEarnings = new Chart(
    ctxYearEarnings, {
        type: 'bar',
        data: {
            labels: months,
            datasets: [{
                label: 'Earnings',
                data: data[0],
                backgroundColor: bgYear,
            }]
        }
    }
);

var canvasYearReservations = document.getElementById('canvas-year-reservations');
var ctxYearReservations = canvasYearReservations.getContext('2d');
var chartYearReservations = new Chart(
    ctxYearReservations, {
        type: 'bar',
        data: {
            labels: months,
            datasets: [{
                label: 'Reservations',
                data: data[1],
                backgroundColor: bgYear,
            }]
        }
    }
);




var canvasYearReservedMaterials = document.getElementById('canvas-year-reserved-materials');
var ctxYearReservedMaterials = canvasYearReservedMaterials.getContext('2d');

var chartYearReservedMaterials = new Chart(
    ctxYearReservedMaterials, {
        type: 'bar',
        data: {
            labels: months,
            datasets: [{
                label: 'Reserved Materials',
                data:data[2],
                backgroundColor: bgYear,
            }]
        }
    }
);

   

})