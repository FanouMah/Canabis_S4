/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function editPlante(button){
    var inputEspece = document.getElementById('editpespece');
    var inputId = document.getElementById('editpid');
    var inputVariete = document.getElementById('editpvariete');
    var selectSalleCulture = document.getElementById('selecteditpsc');
    
    var td = button.parentNode;
    var tr = td.parentNode;
    
    var childTr = tr.childNodes;
    
    inputId.value = childTr[1].textContent.trim();
    inputEspece.value = childTr[3].textContent.trim();
    inputVariete.value = childTr[5].textContent.trim();
    selectSalleCulture.value = childTr[7].querySelector('span').textContent.trim();
}

function editSalleCulture(button){
    var inputNom = document.getElementById('editslnom');
    var inputTemperature = document.getElementById('editsltemperature');
    var inputHumidite = document.getElementById('editslhumidite');
    var inputId = document.getElementById('editslid');
    
    var td = button.parentNode;
    var tr = td.parentNode;
    
    var childTr = tr.childNodes;
    
    inputId.value = childTr[1].textContent.trim();
    inputNom.value = childTr[3].textContent.trim();
    inputTemperature.value = childTr[5].textContent.trim();
    inputHumidite.value = childTr[7].textContent.trim();
}

function alert(message, type) {
    var wrapper = document.createElement('div');
    wrapper.innerHTML = '<div class="alert alert-' + type + ' alert-dismissible" role="alert">' + message + '<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button></div>';

    alertPlaceholder = document.getElementById('alertPlaceholder');
    alertPlaceholder.append(wrapper);
}
function showCollapse(id){
    var collapses = document.getElementsByClassName('collapse');
    for (var i = 0; i < collapses.length; i++) {
        var collapse = collapses[i];
        if (collapse.id === id){
            collapse.classList.add('show');
        }
        else{
            collapse.classList.remove('show');
        }
    }
}

