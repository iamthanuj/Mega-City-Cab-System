/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */




document.addEventListener("DOMContentLoaded", function () {
    // Toast notification (optional)
    let errorToast = document.getElementById("errorToast");
    if (errorToast && errorToast.innerText.trim() !== "") {
        let toast = new bootstrap.Toast(errorToast);
        toast.show();
    }

    // Vehicle selection
    document.querySelectorAll('.vehicle-option').forEach(option => {
        option.addEventListener('click', function () {
            document.querySelectorAll('.vehicle-option').forEach(opt => opt.classList.remove('selected'));
            this.classList.add('selected');
            document.getElementById('selectedVehicle').value = this.getAttribute('data-vehicle');
        });
    });

    // Town and vehicle data
    const towns = {
        "Colombo": {lat: 6.9271, lon: 79.8612},
        "Kandy": {lat: 7.2906, lon: 80.6337},
        "Galle": {lat: 6.0326, lon: 80.2170},
        "Jaffna": {lat: 9.6615, lon: 80.0255},
        "Nuwara Eliya": {lat: 6.9497, lon: 80.7891},
        "Anuradhapura": {lat: 8.3122, lon: 80.4131},
        "Ratnapura": {lat: 6.7056, lon: 80.3847},
        "Kurunegala": {lat: 7.4863, lon: 80.3647},
        "Negombo": {lat: 7.2090, lon: 79.8381},
        "Trincomalee": {lat: 8.5874, lon: 81.2152}
    };

    const vehicles = {
        "Flex": {name: "Suzuki Alto", seats: 4, ac: true, rate: 30},
        "Car": {name: "Toyota Prius", seats: 4, ac: true, rate: 50},
        "Van": {name: "Toyota HiAce", seats: 9, ac: true, rate: 80}
    };

    // Get elements
    const startSelect = document.getElementById("start-location");
    const endSelect = document.getElementById("end-location");
    const startLocationInput = document.getElementById("startLocationInput");
    const endLocationInput = document.getElementById("endLocationInput");
    const distanceInput = document.getElementById("distance");
    const vehicleButtons = document.querySelectorAll(".vehicle-option");
    const selectedVehicleInput = document.getElementById("selectedVehicle");
    const vehicleName = document.getElementById("vehicle-name");
    const vehicleSeats = document.getElementById("vehicle-seats");
    const vehicleAC = document.getElementById("vehicle-ac");
    const estimatedCost = document.getElementById("estimated-cost");
    const estimatedCostInput = document.getElementById("estimatedCostInput");

    // Populate towns
    function populateTowns(selectElement) {
        for (const town in towns) {
            const option = document.createElement("option");
            option.value = town;
            option.textContent = town;
            selectElement.appendChild(option);
        }
    }

    populateTowns(startSelect);
    populateTowns(endSelect);

    function toRadians(degrees) {
        return degrees * (Math.PI / 180);
    }

    function calculateDistance(start, end) {
        const R = 6371;
        const startCoords = towns[start];
        const endCoords = towns[end];

        if (!startCoords || !endCoords) {
            console.error("Invalid town selected.");
            return 0;
        }

        const lat1 = startCoords.lat;
        const lon1 = startCoords.lon;
        const lat2 = endCoords.lat;
        const lon2 = endCoords.lon;

        const dLat = toRadians(lat2 - lat1);
        const dLon = toRadians(lon2 - lon1);

        const a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(toRadians(lat1)) * Math.cos(toRadians(lat2)) *
                Math.sin(dLon / 2) * Math.sin(dLon / 2);

        const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return (R * c).toFixed(2);
    }

    vehicleButtons.forEach(button => {
        button.addEventListener("click", function () {
            vehicleButtons.forEach(btn => btn.classList.remove("selected"));
            this.classList.add("selected");
            const vehicleType = this.getAttribute("data-vehicle");
            selectedVehicleInput.value = vehicleType;

            const vehicle = vehicles[vehicleType];
            vehicleName.innerHTML = `${vehicle.name} <a href="#" class="text-decoration-none">View</a>`;
            vehicleSeats.innerHTML = `👤 ${vehicle.seats} passengers`;
            vehicleAC.innerHTML = vehicle.ac ? "✔ Air Conditioned" : "❌ No A/C";

            updateDistance();
        });
    });

    function updateDistance() {
        const start = startSelect.value;
        const end = endSelect.value;
        const selectedVehicle = selectedVehicleInput.value;

        if (start && end && selectedVehicle) {
            const distance = calculateDistance(start, end);
            distanceInput.value = distance;
            startLocationInput.value = start;
            endLocationInput.value = end;

            const vehicleRate = vehicles[selectedVehicle].rate;
            const cost = (distance * vehicleRate).toFixed(2);
            estimatedCost.innerText = cost;
            estimatedCostInput.value = cost;
        }
    }

    startSelect.addEventListener("change", updateDistance);
    endSelect.addEventListener("change", updateDistance);
});


function printReceipt() {
    window.print();
}




function printBill(bookingId) {
    var modalContent = document.querySelector('#viewBillModal' + bookingId + ' .modal-content');
    var printWindow = window.open('', '', 'height=500,width=800');
    printWindow.document.write('<html><head><title>Booking Bill</title>');
    printWindow.document.write('<style>.receipt-details dt { font-weight: bold; }</style>');
    printWindow.document.write('</head><body>');
    printWindow.document.write(modalContent.innerHTML);
    printWindow.document.write('</body></html>');
    printWindow.document.close();
    printWindow.print();
}
