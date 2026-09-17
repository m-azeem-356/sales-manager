const viewSalesButton = document.getElementById("viewSales");

if (viewSalesButton) {
    viewSalesButton.addEventListener("click", () => {
        window.location.href = "view-sales.html";
    });
}

let backButton = document.getElementById("backButton");
if (backButton) {
    backButton.addEventListener("click", () => {
        window.history.back();
    });
}

const addSaleButton = document.getElementById("addSale");

if (addSaleButton) {
    addSaleButton.addEventListener("click", () => {
        window.location.href = "add-sale.html";
    });
}

const logOutButton = document.getElementById("logout");

if (logOutButton) {
    logOutButton.addEventListener("click", () => {
        window.location.href = "index.html";
    });
}

const forgPass = document.getElementById("forgotpass");

if (forgPass) {
    forgPass.addEventListener("click", () => {
        window.location.href = "reset-password.html";
    });
}

const sendCodeButton = document.getElementById("sendCode");
let resetUsername;
let newPassword;
let resePassCode = -1;
if (sendCodeButton) {
    sendCodeButton.addEventListener("click", () => {
        sendCodeButton.disabled = true;
        resetUsername = document.getElementById("username").value;
        if (resetUsername === "") {
            return;
        }
        fetch(`/reset/password/${encodeURIComponent(resetUsername)}`, {
            method: "GET"
        })
            .then(response => response.json())
            .then(data => {
                if (data === -1) {
                    document.getElementById("usernameStep").style.display = "block";
                    document.getElementById("username").value = "";
                    document.getElementById("username").placeholder = "User Not Found";
                    sendCodeButton.disabled = false;
                    return;
                }
                else {
                    resePassCode = data;
                    document.getElementById("codeStep").style.display = "block";
                    document.getElementById("usernameStep").style.display = "none";
                }
            })
            .catch(error => {
                console.error(error);
            });

    });
}

const verifyCodeButton = document.getElementById("verifyCode");

if (verifyCodeButton) {
    verifyCodeButton.addEventListener("click", () => {
        let userEnteredCode = Number(document.getElementById("verificationCode").value);
        if (userEnteredCode !== resePassCode) {
            document.getElementById("user-error-message").textContent = "Invalid Code";
            errorWindow.style.display = "flex";
            return;
        }
        document.getElementById("codeStep").style.display = "none";
        document.getElementById("passwordStep").style.display = "block";
    });
}

const resetPassButton = document.getElementById("resetPassword");

if (resetPassButton) {
    resetPassButton.addEventListener("click", () => {
        let psp1 = document.getElementById("newPassword").value;
        let psp2 = document.getElementById("confirmPassword").value;
        if (psp1 === "" || psp2 === "") {
            return;
        }
        else if (psp1 !== psp2) {
            document.getElementById("user-error-message").textContent = "Passwords do not match";
            errorWindow.style.display = "flex";
            return;
        }
        fetch(`/change/Password?username=${encodeURIComponent(resetUsername)}&password=${encodeURIComponent(psp1)}`, {
            method: "GET"
        })
            .then(response => response.json())
            .then(data => {

                if (data === true) {
                    document.getElementById("success").style.display = "block";
                } else {
                    document.getElementById("passwordStep").style.display = "block";
                    document.getElementById("user-error-message").textContent = "Failed to change password.";
                    errorWindow.style.display = "flex";
                }

            })
            .catch(error => {
                console.error(error);
            });
        document.getElementById("passwordStep").style.display = "none";
    });
}

const backLogInButton = document.getElementById("backLogIn");

if (backLogInButton) {
    backLogInButton.addEventListener("click", () => {
        window.history.back();
    });
}

const searchNameButton = document.getElementById("searchName");

if (searchNameButton) {
    searchNameButton.addEventListener("click", () => {
        window.location.href = "view-by-name.html";
    });
}


const searchDateButton = document.getElementById("searchDate");

if (searchDateButton) {
    searchDateButton.addEventListener("click", () => {
        document.getElementById("dateModal").style.display = "flex";
    });
}

const cancelDateButton = document.getElementById("cancelDate");

if (cancelDateButton) {
    cancelDateButton.addEventListener("click", () => {
        document.getElementById("dateModal").style.display = "none";
    });
}

const confirmDateButton = document.getElementById("confirmDate");

if (confirmDateButton) {
    confirmDateButton.addEventListener("click", () => {
        let date = document.getElementById("saleDate").value;
        if (date == "") return;
        window.location.href = `sale-result.html?date=${date}`;
    });
}


const allSaleButton = document.getElementById("searchAll");

if (allSaleButton) {
    allSaleButton.addEventListener("click", () => {
        window.location.href = "sale-result.html?type=all";
    });
}

const unpaidSaleButton = document.getElementById("searchUnpaid");

if (unpaidSaleButton) {
    unpaidSaleButton.addEventListener("click", () => {
        window.location.href = "sale-result.html?type=unpaid";
    });
}

const paidSaleButton = document.getElementById("searchPaid");

if (paidSaleButton) {
    paidSaleButton.addEventListener("click", () => {
        window.location.href = "sale-result.html?type=paid";
    });
}

const addUserButton = document.getElementById("addUser");

if (addUserButton) {
    addUserButton.addEventListener("click", () => {
        window.location.href = "add-user.html";
    });
}

const confirmAccountButton = document.getElementById("nextUserDetails");
let errorWindow = document.getElementById("user-error-modal");
let username;
let pass1;
let role = "user";
let email;
let confirmationCode = -1;
if (confirmAccountButton) {
    confirmAccountButton.addEventListener("click", () => {
        username = document.getElementById("newUsername").value;
        pass1 = document.getElementById("newPassword").value;
        let pass2 = document.getElementById("confirmNewPassword").value;

        if (username === "" || pass1 === "" || pass2 === "") {
            return;
        }

        if (pass1 !== pass2) {
            document.getElementById("user-error-message").textContent = "Password should match";
            errorWindow.style.display = "flex";
            return;
        }
        if (pass1.length < 6) {
            document.getElementById("user-error-message").textContent = "Password must be atleast 6 characters.";
            errorWindow.style.display = "flex";
            return;
        }

        fetch(`/user/exist/${encodeURIComponent(username)}`)
            .then(response => response.json())
            .then(exists => {
                if (exists) {
                    document.getElementById("user-error-message").textContent = "Username already exists";
                    errorWindow.style.display = "flex";
                    return;
                }
                else {
                    let adminCheck = document.getElementById("adminChecked");
                    if (adminCheck.checked) {
                        role = "admin";
                    }
                    document.getElementById("userDetailsStep").style.display = "none";
                    document.getElementById("userEmailStep").style.display = "block";
                    console.log(role);
                }
            });
    });
}

const closeUserError = document.getElementById("close-user-error");

if (closeUserError) {
    closeUserError.addEventListener("click", () => {
        document.getElementById("user-error-modal").style.display = "none";
    });
}

const confirmEmailButton = document.getElementById("sendUserCode");
if (confirmEmailButton) {
    confirmEmailButton.addEventListener("click", () => {
        email = document.getElementById("userEmail").value;
        if (email === "") {
            return;
        }
        const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!emailPattern.test(email)) {
            document.getElementById("user-error-message").textContent =
                "Invalid Email Address.";

            errorWindow.style.display = "flex";
            console.log(JSON.stringify(email));

            return;
        }

        fetch(`/user/email/${encodeURIComponent(email)}`)
            .then(response => response.json())
            .then(exists => {
                if (exists) {
                    document.getElementById("user-error-message").textContent = "Email already exists";
                    errorWindow.style.display = "flex";
                    return;
                }
                else {
                    fetch(`/send/code/${encodeURIComponent(email)}`)
                        .then(response => response.json())
                        .then(code => {
                            confirmationCode = code;
                        });
                    document.getElementById("userEmailStep").style.display = "none";
                    document.getElementById("userCodeStep").style.display = "block";
                }
            });
    });
}

const confirmUserButton = document.getElementById("confirmUser");

if (confirmUserButton) {
    confirmUserButton.addEventListener("click", () => {
        let enteredCode = document.getElementById("userVerificationCode").value;
        if (Number(enteredCode) !== confirmationCode) {
            document.getElementById("user-error-message").textContent = "Entered code is incorrect.";
            errorWindow.style.display = "flex";
            return;
        }
        fetch(`/signup?username=${encodeURIComponent(username)}&password=${encodeURIComponent(pass1)}&email=${encodeURIComponent(email)}&role=${encodeURIComponent(role)}`, {
            method: "POST"
        })
            .then(response => response.json())
            .then(data => {
                if (data) {
                    document.getElementById("success").style.display = "block";
                }
                else {
                    document.getElementById("user-error-message").textContent = "Fail to save the user.";
                    errorWindow.style.display = "flex";
                }
            })
            .catch(error => {
                console.error(error);
            });
        document.getElementById("userCodeStep").style.display = "none";
    });
}


function display() {
    const container = document.getElementById("sales-container");

    const tableBody = document.getElementById("sales-body");

    if (!tableBody) {
        return;
    }

    const params = new URLSearchParams(window.location.search);
    const type = params.get("type");
    const date = params.get("date");

    let url = "/sales";
    if (type === "unpaid") {
        url = "/sales/unpaid";
    }

    else if (type === "paid") {
        url = "/sales/paid";
    }
    else if (date) {
        url = `/sales/customer/date/${date}`;
    }

    if (tableBody) {

        fetch(url, {
            method: "GET"
        })
            .then(response => response.json())
            .then(data => {

                data.forEach(sale => {

                    const row = document.createElement("tr");

                    // Green if paid, red if unpaid
                    if (sale.paidStatus) {
                        row.classList.add("paid");
                    } else {
                        row.classList.add("unpaid");
                    }

                    row.innerHTML = `
    <td class="checkbox-column">
        <input
            type="checkbox"
            class="sale-checkbox"
            data-id="${sale.id}"
            style="display: none;"
        >
    </td>

    <td>${sale.id}</td>

    <td>${sale.saleDate}</td>

    <td>${sale.saleTime}</td>

    <td class="customer-name">
        ${sale.customerName}
    </td>

    <td>${sale.totalBill}</td>

    <td>${sale.amountPaid}</td>

    <td>${sale.remaining}</td>

    <td>
        <span class="status-badge">
            ${sale.paidStatus ? "PAID" : "UNPAID"}
        </span>
    </td>

    <td class="actions">
    <button
        class="action-button edit-button"
        data-id="${sale.id}"
        data-name="${sale.customerName}"
        data-paid="${sale.amountPaid}"
        data-remaining="${sale.remaining}">
        Edit
    </button>

    <button class="action-button delete-button" data-id="${sale.id}">
        Delete
    </button>
</td>
`;

                    tableBody.appendChild(row);
                });

            })
            .catch(error => {
                console.error("Error loading sales:", error);
            });
    }
}



const selectAll = document.getElementById("select-all");
const multipleDelete = document.getElementById("multiple-delete");

let selectedSaleIds = [];


if (selectAll) {

    selectAll.addEventListener("change", function () {

        const checkboxColumns =
            document.querySelectorAll(".checkbox-column");

        const saleCheckboxes =
            document.querySelectorAll(".sale-checkbox");


        // Show / hide checkbox columns
        checkboxColumns.forEach(column => {
            column.style.display =
                this.checked ? "table-cell" : "none";
        });


        // Show / hide individual checkboxes
        saleCheckboxes.forEach(checkbox => {
            checkbox.style.display =
                this.checked ? "inline-block" : "none";
        });


        // Show / hide Multiple Delete button
        multipleDelete.style.display =
            this.checked ? "block" : "none";


        // If multiple selection is turned OFF,
        // clear the selected IDs
        if (!this.checked) {
            selectedSaleIds = [];

            saleCheckboxes.forEach(checkbox => {
                checkbox.checked = false;
            });
        }

    });

}

document.addEventListener("change", function (event) {

    if (!event.target.classList.contains("sale-checkbox")) {
        return;
    }

    const id = Number(event.target.dataset.id);


    if (event.target.checked) {

        selectedSaleIds.push(id);

    } else {

        selectedSaleIds = selectedSaleIds.filter(
            saleId => saleId !== id
        );

    }

});

let changeData = {};

document.addEventListener("click", (event) => {

    if (!event.target.classList.contains("edit-button")) {
        return;
    }
    const button = event.target;
    document.getElementById("edit-modal").style.display = "flex";
    document.getElementById("edit-customer-name").innerHTML = button.dataset.name;
    document.getElementById("edit-remaining").innerHTML = button.dataset.remaining;
    changeData.id = button.dataset.id;
    changeData.remaining = button.dataset.remaining;
    changeData.paid = button.dataset.paid;
    console.log(changeData);
});


const cancelEditButton = document.getElementById("cancel-edit");
if (cancelEditButton) {
    cancelEditButton.addEventListener("click", () => {
        document.getElementById("edit-modal").style.display = "none";
    });
}

const saveEditButton = document.getElementById("save-edit");
if (saveEditButton) {
    saveEditButton.addEventListener("click", () => {
        let amount = Number(document.getElementById("new-payment").value);
        if (amount > changeData.remaining) {
            document.getElementById("error-amount").style.display = "block";
            return;
        }

        fetch(`/sales/${changeData.id}/payment?amount=${amount}&remaining=${changeData.remaining}&prevPaid=${changeData.paid}`, {
            method: "PUT"
        })
            .then(response => response.text())
            .then(data => {
                const tableBody = document.getElementById("sales-body");
                if (tableBody) {
                    tableBody.innerHTML = "";
                    display();
                }
                document.getElementById("edit-modal").style.display = "none";
            })
            .catch(error => {
                document.getElementById("error-amount").innerHTML = "Failed to save data";
                document.getElementById("error-amount").style.display = "block";
            });

    });
}

if (window.location.pathname.includes("sale-result.html")) {
    display();
}


let confirmDeleteButton = document.getElementById("confirm-delete");
let cancelDeleteButton = document.getElementById("cancel-delete");
let deleteId;

document.addEventListener("click", (event) => {

    if (!event.target.classList.contains("delete-button")) {
        return;
    }
    document.getElementById("delete-modal").style.display = "flex";
    deleteId = event.target.dataset.id;
    console.log(deleteId);
});

if (cancelDeleteButton) {
    cancelDeleteButton.addEventListener("click", () => {
        document.getElementById("delete-modal").style.display = "none";
    });
}
if (confirmDeleteButton) {
    confirmDeleteButton.addEventListener("click", () => {
        fetch(`/sales/${deleteId}`, {
            method: "DELETE"
        })
            .then(response => response.text())
            .then(data => {
                const tableBody = document.getElementById("sales-body");
                if (tableBody) {
                    tableBody.innerHTML = "";
                    display();
                    document.getElementById("delete-modal").style.display = "none";
                }
            })
            .catch(error => {
                console.error(error);
            });
    });
}

let multipleDeleteButton = document.getElementById("multiple-delete");
if (multipleDeleteButton) {
    multipleDeleteButton.addEventListener("click", () => {
        if (selectedSaleIds.length > 0) {
            document.getElementById("delete-modal2").style.display = "flex";
        }
    });
}
let confirmDeleteButton2 = document.getElementById("confirm-delete-m");
let cancelDeleteButton2 = document.getElementById("cancel-delete-m");

if (cancelDeleteButton2) {
    cancelDeleteButton2.addEventListener("click", () => {
        document.getElementById("delete-modal2").style.display = "none";
    });
}


if (confirmDeleteButton2) {
    confirmDeleteButton2.addEventListener("click", () => {
        fetch("/sales", {
            method: "DELETE",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(selectedSaleIds)
        })
            .then(response => response.text())
            .then(data => {
                const tableBody = document.getElementById("sales-body");
                if (tableBody) {
                    tableBody.innerHTML = "";
                    display();
                    let selectAllButton = document.getElementById("select-all");
                    selectAllButton.checked = false;
                    selectAllButton.dispatchEvent(new Event("change"));
                    document.getElementById("delete-modal2").style.display = "none";
                }
            })
            .catch(error => {
                console.error(error);
            });
    });
}

function displayByName(name) {
    let url = `/sales/customer/${name}`;
    const container = document.getElementById("sales-container");

    const tableBody = document.getElementById("sales-body");

    if (!tableBody) {
        return;
    }
    if (tableBody) {

        fetch(url, {
            method: "GET"
        })
            .then(response => response.json())
            .then(data => {
                tableBody.innerHTML = "";
                data.forEach(sale => {
                    const row = document.createElement("tr");
                    // Green if paid, red if unpaid
                    if (sale.paidStatus) {
                        row.classList.add("paid");
                    } else {
                        row.classList.add("unpaid");
                    }

                    row.innerHTML = `
    <td class="checkbox-column">
        <input
            type="checkbox"
            class="sale-checkbox"
            data-id="${sale.id}"
            style="display: none;"
        >
    </td>

    <td>${sale.id}</td>

    <td>${sale.saleDate}</td>

    <td>${sale.saleTime}</td>

    <td class="customer-name">
        ${sale.customerName}
    </td>

    <td>${sale.totalBill}</td>

    <td>${sale.amountPaid}</td>

    <td>${sale.remaining}</td>

    <td>
        <span class="status-badge">
            ${sale.paidStatus ? "PAID" : "UNPAID"}
        </span>
    </td>

    <td class="actions">
    <button
        class="action-button edit-button"
        data-id="${sale.id}"
        data-name="${sale.customerName}"
        data-paid="${sale.amountPaid}"
        data-remaining="${sale.remaining}">
        Edit
    </button>

    <button class="action-button delete-button" data-id="${sale.id}">
        Delete
    </button>
</td>
`;

                    tableBody.appendChild(row);
                });

            })
            .catch(error => {
                console.error("Error loading sales:", error);
            });
    }

}
let searchCustomerNameField = document.getElementById("customerName");
if (searchCustomerNameField) {
    searchCustomerNameField.addEventListener("input", () => {
        let name = searchCustomerNameField.value;
        if (name == "") {
            return;
        }
        displayByName(name);
    });
}

let saveSaleButton = document.getElementById("saveSale");
if (saveSaleButton) {
    saveSaleButton.addEventListener("click", () => {
        let customerName = document.getElementById("customerName").value;
        let itemPrice = document.getElementById("itemPrice").value;
        let quantity = document.getElementById("quantity").value;
        let amountPaid = document.getElementById("amountPaid").value;
        if (customerName === "" || itemPrice === "" || quantity === "" || amountPaid === "") {
            return;
        }
        itemPrice = Number(itemPrice);
        quantity = Number(quantity);
        amountPaid = Number(amountPaid);
        let totalBill = quantity * itemPrice;
        if (totalBill < amountPaid) {
            document.getElementById("errorModal").style.display = "flex";
            return;
        }

        document.getElementById("totalBill").textContent = totalBill + " PKR";
        fetch(`/sales?quantity=${quantity}&customerName=${encodeURIComponent(customerName)}&unitPrice=${itemPrice}&amountPaid=${amountPaid}`, {
            method: "POST"
        })
            .then(response => response.text())
            .then(data => {
                document.getElementById("successModal").style.display = "flex";
                console.log(data);
            })
            .catch(error => {
                console.error(error);
            });
    });
}

let closeError = document.getElementById("closeError");
if (closeError) {
    closeError.addEventListener("click", () => {
        document.getElementById("errorModal").style.display = "none";
    });
}

let successBack = document.getElementById("successBack");
if (successBack) {
    successBack.addEventListener("click", () => {
        window.history.back();
    });
}

let loginUsername;
let loginPassword;
let loginButton = document.getElementById("login");
if (loginButton) {
    loginButton.addEventListener("click", () => {
        loginUsername = document.getElementById("username").value;
        loginPassword = document.getElementById("password").value;
        if (loginUsername === "" || loginPassword === "") {
            return;
        }
        fetch(`/login?username=${encodeURIComponent(loginUsername)}&password=${encodeURIComponent(loginPassword)}`, {
            method: "POST"
        })
            .then(response => response.text())
            .then(data => {
                if (data === "Username not found" || data === "Wrong Password or Username") {
                    document.getElementById("user-error-message").textContent = "Invalid Username or Password.";
                    errorWindow.style.display = "flex";
                }
                else {
                    if (data === "admin") {
                        window.location.href = "admin.html";
                    }
                    else {
                        window.location.href = "user.html";
                    }
                }
            })
            .catch(error => {
                console.error(error);
            });
    });
}