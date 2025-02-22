/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */



document.addEventListener("DOMContentLoaded", function () {
    let errorToast = document.getElementById("errorToast");
    if (errorToast.innerText.trim() !== "") {
        let toast = new bootstrap.Toast(errorToast);
        toast.show();
    }
});
