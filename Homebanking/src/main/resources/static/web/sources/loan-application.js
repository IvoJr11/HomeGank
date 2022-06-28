const app = Vue.createApp({
    data() {
      return {
        loans: [],
        client: [],
        accounts: [],
        payments: [],
        error:"",
        name: "",
        destination_account: "",
        id: 0,
        plus: 0,
        amount: 0,
        maxAmount: 0,
        currentPayment: 0,
        showAmount: false,
      };
    },
  
    created() {

        axios.get("/api/loans").then((data) => {
            this.loans = data.data
            console.log(this.loans)
        });

        axios.get("/api/clients/current").then((data) => {
            this.client = data.data
            console.log(this.client)
            this.accounts = this.client.accountsDTO.filter(account => account.active == true).sort((a,b) => a.id - b.id)
            console.log(this.accounts)
        })

        
    },
    
    methods: {

        logOut() {
            axios.post('/api/logout')
            .then(response => location.href="/web/index.html")
            .then(response => console.log('signed out!!!'))
        },
        
        select(loan) {

            this.payments = loan.payments
            console.log(this.payments)

            this.maxAmount = loan.maxAmount

            this.name = loan.name;
            console.log(this.name);

            this.id = loan.id;
            console.log(this.id);

            let bttn1 = document.querySelector(".Mortgage")
            let bttn2 = document.querySelector(".Personal")
            let bttn3 = document.querySelector(".Car")

            bttn1.style.background = "rgb(234,232,238)";
            bttn1.style.color = "black";
            bttn2.style.background = "rgb(234,232,238)";
            bttn2.style.color = "black";
            bttn3.style.background = "rgb(234,232,238)";
            bttn3.style.color = "black";

            let bttn = document.querySelector(`.${(this.name).replace(" Loan", "")}`)

            if(bttn.classList.contains("Mortgage")) {
                bttn.style.background = "rgb(236, 112, 99)";
                bttn.style.color = "white";
            } else if(bttn.classList.contains("Personal")) {
                bttn.style.background = "rgb(35, 155, 86)";
                bttn.style.color = "white";
            } else if(bttn.classList.contains("Car")) {
                bttn.style.background = "rgb(161, 70, 197)";
                bttn.style.color = "white";
            }

        },

        disabled() {
            const uwu = document.querySelector("#default")
            uwu.setAttribute("disabled", "")
        },

        apply() {
            Swal.fire({
                title: 'Are you sure?',
                text: "You won't be able to revert this!",
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Yes'
            }).then((result)=> {
                if(result.isConfirmed) {
                    Swal.fire({
                        title: 'Wait a second...',
                        icon: 'question',
                        showConfirmButton: false,
                    })
                    let loan = {
                        id: this.id,
                        amount: this.amount,
                        payments: this.currentPayment,
                        destination_account: this.destination_account
                    }
                    axios.post("/api/loans", loan)
                    .then((data) => {console.log(data)})
                    .catch(function(error) {
                        this.error = error.response.data
                        console.log(this.error)
                    })
                    .then(
                        setTimeout(function() {
                            if(this.amount == "") {
                                Swal.fire ({
                                    title: "Amount can't be equal or minus to 0",
                                    icon: 'error',
                                })
                            } else if(this.error == "The client has already applied for this loan" || this.error == "Destination account empty" || this.error == "Amount can't be equal or minus to 0" || this.error == "Payments can't be equal or minus to 0" || this.error == "The requested amount exceeds the maximum allowed" || this.error == "Requested quotas are not a valid option") {
                                Swal.fire ({
                                    title: `${this.error}`,
                                    icon: 'error',
                                })
                                this.error = ""
                            } else {
                                Swal.fire ({
                                    title: 'The Loan has been complete',
                                    icon: 'success',
                                    showConfirmButton: false
                                })
                                setTimeout(function(){
                                    location.reload()
                                }, 2500)
                            }
                        }, 2000)
                    )
                }
            })
        }

    },
    computed: {

        Plus() {
            if(this.amount != 0 && this.currentPayment != 0) {
                this.plus = ((this.amount / this.currentPayment) * 1.2).toFixed(2)
            }
            return this.plus
        },

    },
  }).mount("#app");
