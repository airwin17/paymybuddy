var form = document.getElementById("post-form");
form.addEventListener("submit", e => {
    e.preventDefault();
    var amount = document.getElementById("amount").value;
    var description = document.getElementById("description").value;
    var relationship = document.getElementById("relationship").value;
    var transactionDto = {
        relationship: relationship,
        amount: amount,
        description: description
    }
    fetch("/api/transaction/saveTransaction", {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(transactionDto)
    }).then(response => {
        if (response.ok) {
            alert("Transaction added");
            window.location.reload();
        } else {
            alert(response.statusText);
        }
    })
})