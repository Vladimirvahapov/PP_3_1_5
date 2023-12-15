const table = $('#usersTable');
tableOfAllUsers();

function tableOfAllUsers() {
    table.empty()
    fetch("localhost:8080/admin/getAllUsers")
        .then(res => res.json())
        .then(data => {
            data.forEach(user => {
                let usersTable = `$(
                        <tr>
                            <td>${user.id}</td>
                            <td>${user.username}</td>
                            <td>${user.secondName}</td>
                            <td>${user.age}</td>   
                            <td>${user.username}</td>
                            <td>${user.username}</td>
                            <td>${user.username}</td>
                            <td>${user.username}</td>
                        </tr>)`;
                table.append(usersTable);
            })
        })
}