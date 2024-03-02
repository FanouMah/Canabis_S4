/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function editSalleCulture(button){
    var td = button.parentNode;
    var tr = td.parentNode;
    
    var childTr = tr.childNodes;
    
    for (var i = 2; i < childTr.length - 2; i++) {
        var value = childTr[i].textContent.trim();
        childTr[i].innerHTML = "<input type='text' class='form-control'  value='"+value+"'></input>";
    }
    
    
    
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

