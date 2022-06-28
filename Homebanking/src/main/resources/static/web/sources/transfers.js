const app = Vue.createApp({
    data() {
      return {
        objeto: {},
        clientDates: {},
        showedAmount: {},
        accounts: [],
        allAccounts: [],
        accountsDTO: [],
        accountsNumbers: [],
        error: "",
        since: "",
        until: "",
        pdfAccount: "",
        description: "",
        accountOrigin: "",
        anotherAccount: "",
        accountSelected:"",
        amount: 0,
        balance: 0,
        changer1: true,
        changer2: false,
      };
    },
  
    created() {
        axios.get("/api/clients/current").then((data) => {
            this.clientDates = data.data
            console.log(this.clientDates)
            this.accounts = this.clientDates.accountsDTO.filter(account => account.active == true).sort((a,b) => a.id - b.id)
            console.log(this.accounts)
        });
    },
    methods: {

      own() {
        this.changer1 = true;
        this.changer2 = false;
        this.anotherAccount = ""
      },
      
      another() {
        this.changer1 = false;
        this.changer2 = true;
        this.accountSelected = ""
      },

      logOut() {
        axios.post('/api/logout')
        .then(response => location.href="/web/index.html")
        .then(response => console.log('signed out!!!'))
    },

    showAmount() {
      if (this.accountOrigin == "") {
        this.balance = 0
      } else {
        this.showedAmount = this.accounts.filter(account => account.number == this.accountOrigin)
        this.objeto = this.showedAmount
        this.balance = this.objeto[0].balance
        console.log(this.showedAmount)
        console.log(this.objeto)
        console.log(this.balance)
      }
    },

    transfers() {
      console.log(this.error)
      Swal.fire({
        title: 'Are you sure?',
        text: "You won't be able to revert this!",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Yes'
      }).then((result) => {
        if (result.isConfirmed) {
          if (this.accountSelected != "") {
            axios.post("/api/transactions", `amount=${this.amount}&description=${this.description}&source_number=${this.accountOrigin}&destination_number=${this.accountSelected}`,{headers:{'content-type':'application/x-www-form-urlencoded'}})
            .catch(function(error) {
              this.error = error.response.data
              console.log(this.error)
            })
            setTimeout(function() {
              if(this.error == "Amount can't be 0 or minus" || this.error == "Description is empty" || this.error == "Origin account is empty" || this.error == "Destination account is empty") {
                Swal.fire({
                  title: `${this.error}`,
                  icon: 'error'
                })
                this.error = ""
            } else {
              Swal.fire({
                title: 'Transfer has been successfully completed',
                icon: 'success'
              })
              setTimeout(function(){
                location.reload()
              }, 2000)
            }
          },3500)
          } else {
            axios.post("/api/transactions", `amount=${this.amount}&description=${this.description}&source_number=${this.accountOrigin}&destination_number=${this.anotherAccount}`,{headers:{'content-type':'application/x-www-form-urlencoded'}})
            .catch(function(error) {
              this.error = error.response.data
              console.log(this.error)
            })
            setTimeout(function() {
              if(this.error == "Amount can't be 0 or minus" || this.error == "Description is empty" || this.error == "Origin account is empty" || this.error == "Destination account is empty") {
                Swal.fire({
                  title: `${this.error}`,
                  icon: 'error'
                })
                this.error = ""
            } else {
              Swal.fire({
                title: 'Transfer has been successfully completed',
                icon: 'success'
              })
              setTimeout(function(){
                location.reload()
              }, 2000)
            }
          },3500)
          }
          Swal.fire({
            title: 'Wait a second...',
            icon: 'question',
            showConfirmButton: false,
          })
        }
      })
    },

    accountBalance() {
      Swal.fire({
        title: `Amount available: $${this.balance}`,
        allowOutsideClick: true,
        width: 300,
        height: 100,
        opacity: 0,
        toast: true,
    })
    },

    downloadPDF() {

      let pdfParamsDTO = {
        numberAccount : this.pdfAccount,
        dateSince : this.since,
        dateUntil : this.until 
      }

      axios.post("/api/transactions/create", pdfParamsDTO,{'responseType':'blob'})
      .then((response) => {
        const url = window.URL.createObjectURL(new Blob([response.data]));
        const link = document.createElement('a');
        link.href = url;
        link.setAttribute('download', `${this.pdfAccount}_HomeGank.pdf`); //or any other extension
        document.body.appendChild(link);
        link.click();
      })
    }

    },

    computed: {

      toUpper() {
        this.anotherAccount = this.anotherAccount.toUpperCase();
        console.log(this.anotherAccount)
      },

    },
}).mount("#app");

// $('article.tile').click(function() {
//     $('article.tile').animate({width: "600px"}, 200)
// })