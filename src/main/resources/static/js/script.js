function listAllUsers() {
    const table = document.getElementById("allUsers");
    fetch("http://localhost:8080/api/users").then(res => res.json().then(data => {
        table.innerHTML = "";
        data.forEach(user => {
            let row = table.insertRow();
            row.insertCell(0).innerHTML = user.firstName;
            row.insertCell(1).innerHTML = user.lastName;
            row.insertCell(2).innerHTML = user.yearOfBirth;
            row.insertCell(3).innerHTML = user.username;
            row.insertCell(4).innerHTML = user.roles.join(", ")
            row.insertCell(5).innerHTML = "<button type='button' class='btn btn-sm btn-primary' data-bs-toggle='modal' data-bs-target='#editModal' onclick='fillEditModal("+user.id+")'>Edit</button>"
            row.insertCell(6).innerHTML = "<button type='button' class='btn btn-sm btn-primary' data-bs-toggle='modal' data-bs-target='#deleteModal' onclick='fillDeleteModal("+user.id+")'>Delete</button>"
        });
    }));
}

function fillAuth() {
    fetch("http://localhost:8080/api/user").then(res => res.json().then(data => {
        document.getElementById("title-username").innerHTML = data.username;
        document.getElementById("title-roles").innerHTML = data.roles.join(", ");
        document.getElementById("auth-firstName").innerHTML = data.firstName;
        document.getElementById("auth-lastName").innerHTML = data.lastName;
        document.getElementById("auth-yearOfBirth").innerHTML = data.yearOfBirth;
        document.getElementById("auth-username").innerHTML = data.username;
        document.getElementById("auth-roles").innerHTML = data.roles.join(", ");
    }));
}

function listRoles() {
    const select1 = document.getElementById("newRoles");
    const select2 = document.getElementById("roles");
    fetch("http://localhost:8080/api/admin").then(res => res.json().then(data => {
        select1.innerHTML = "";
        select2.innerHTML = "";
        data.forEach(role => {
            let option = document.createElement("option");
            option.value = role.id;
            option.text = role.name;
            select1.appendChild(option);
            select2.appendChild(option);
        })
    }));
}

function fillEditModal(id) {
    document.getElementById("editFirstNameError").innerText = "";
    document.getElementById("editLastNameError").innerText = "";
    document.getElementById("editYearOfBirthError").innerText = "";
    document.getElementById("editUsernameError").innerText = "";
    document.getElementById("editPasswordError").innerText = "";
    document.getElementById("editRolesError").innerText = "";
    document.getElementById("editPassword").value = "";

    fetch("http://localhost:8080/api/user/"+id).then(res => res.json().then(user => {
        document.getElementById("editForm").action = "/api/admin";
        document.getElementById("editId").value = user.id;
        document.getElementById("editFirstName").value = user.firstName;
        document.getElementById("editLastName").value = user.lastName;
        document.getElementById("editYearOfBirth").value = user.yearOfBirth;
        document.getElementById("editUsername").value = user.username;

        let options = document.getElementById("roles").options;
        for (let i = 0; i < options.length; i++) {
            for (let j = 0; j < user.roles.length; j++) {
                if (user.roles[j] === options[i].text) {
                    options[i].selected = true;
                    break;
                }
            }
        }
    }))
}

function sendEdit() {
    let roleList = [];
    let options = document.getElementById("roles").options;
    for (let i = 0; i < options.length; i++) {
        if (options[i].selected) {
            roleList.push(options[i].text);
        }
    }

    const user = {
        id: document.getElementById("editId").value,
        firstName: document.getElementById("editFirstName").value,
        lastName: document.getElementById("editLastName").value,
        yearOfBirth: document.getElementById("editYearOfBirth").value,
        username: document.getElementById("editUsername").value,
        password: document.getElementById("editPassword").value,
        roles: roleList
    }

    let requestData = {
        method: "PATCH",
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json;charset=UTF-8"
        },
        body: JSON.stringify(user)
    };

    fetch(document.getElementById("editForm").action, requestData).then(res => res.json().then(data => {
        let hasErrors = data.length > 0;

        data.forEach(error => {
            let elementId = "editRolesError";
            if (error.field === "firstName") {
                elementId = "editFirstNameError";
            } else if (error.field === "lastName") {
                elementId = "editLastNameError";
            } else if (error.field === "yearOfBirth") {
                elementId = "editYearOfBirthError";
            } else if (error.field === "username") {
                elementId = "editUsernameError";
            } else if (error.field === "password") {
                elementId = "editPasswordError";
            }
            document.getElementById(elementId).innerText = error.defaultMessage;
        });

        if (!hasErrors) {
            document.getElementById("closeEdit").click();
            listAllUsers();
        }
    }));
}

function fillDeleteModal(id) {
    fetch("http://localhost:8080/api/user/"+id).then(res => res.json().then(user => {
        document.getElementById("deleteForm").action = "/api/admin/"+id;
        document.getElementById("deleteId").value = user.id;
        document.getElementById("deleteFirstName").value = user.firstName;
        document.getElementById("deleteLastName").value = user.lastName;
        document.getElementById("deleteYearOfBirth").value = user.yearOfBirth;
        document.getElementById("deleteUsername").value = user.username;
    }))
}

function sendDelete() {
    let requestData = {
        method: "DELETE",
        headers: {
            "Content-Type": "application/json"
        }
    };

    fetch(document.getElementById("deleteForm").action, requestData).then(() => {
        document.getElementById("closeDelete").click();
        listAllUsers();
    });
}

function addUser() {
    let roleList = [];
    let options = document.getElementById("newRoles").options;
    for (let i = 0; i < options.length; i++) {
        if (options[i].selected) {
            roleList.push(options[i].text);
        }
    }

    let newUser = {
        firstName: document.getElementById("newFirstName").value,
        lastName: document.getElementById("newLastName").value,
        yearOfBirth: document.getElementById("newYearOfBirth").value,
        username: document.getElementById("newUsername").value,
        password: document.getElementById("newPassword").value,
        roles: roleList
    }

    let requestData = {
        method: "POST",
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json;charset=UTF-8"
        },
        body: JSON.stringify(newUser)
    }

    fetch("/api/admin", requestData).then(res => res.json().then(data => {
        let hasErrors = data.length > 0;

        data.forEach(error => {
            let elementId = "newRolesError";
            if (error.field === "firstName") {
                elementId = "newFirstNameError";
            } else if (error.field === "lastName") {
                elementId = "newLastNameError";
            } else if (error.field === "yearOfBirth") {
                elementId = "newYearOfBirthError";
            } else if (error.field === "username") {
                elementId = "newUsernameError";
            } else if (error.field === "password") {
                elementId = "newPasswordError";
            }
            document.getElementById(elementId).innerText = error.defaultMessage;
        });

        if (!hasErrors) {
            document.getElementById("newFirstName").value = "";
            document.getElementById("newLastName").value = "";
            document.getElementById("newYearOfBirth").value = "";
            document.getElementById("newUsername").value = "";
            document.getElementById("newPassword").value = "";
            for (let i = 0; i < options.length; i++) {
                options[i].selected = false;
            }
            listAllUsers();
            alert("User added successfully");
        }
    }));
}

let btn= document.getElementById("v-pills-admin-tab");
if (btn) {
    btn.click();
} else {
    document.getElementById("v-pills-user-tab").click();
}