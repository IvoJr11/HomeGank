const app = Vue.createApp({

  data() {
    return {
      datos: [],
      datosCliente: [],
      nombreCliente: "",
      loans: [],
      error: "",
      accountType: "",
      email: "",
      cards: [],
      transactions: [],
      lastTransactions: [],
      date: ""
    };
  },

  created() {

    axios.get("/api/clients/current").then((data) => {     

      console.log(data)
      this.datosCliente = data.data
      this.datos = this.datosCliente.accountsDTO.filter(account => account.active == true).sort((a, b) => a.id - b.id)
      this.nombreCliente = this.datosCliente.firstName + " " + data.data.lastName 
      this.loans = data.data.loans.sort((a, b) => b.id - a.id)
      console.log(this.datosCliente)
      console.log(this.datos)
      console.log(this.loans)
      this.datos.forEach(acc => acc.transactionsDTO.forEach(transaction => this.transactions.push(transaction)))
      this.transactions.sort((a,b) => a.id - b.id).reverse()
      console.log(this.transactions)
      let i = 0
      if(this.transactions.length > 10) {

        while(i <= 10) {

          this.lastTransactions.push(this.transactions[i])

          i++

        }

      } else {

        let j = 0
        while(j < this.transactions.length) {
  
          this.lastTransactions.push(this.transactions[j])
          
          j++
  
        }
      }
      console.log(this.lastTransactions)
    });

    axios.get("/api/clients/current/cards").then((data2) => {
      console.log(data2)
      this.cards = data2.data.sort((a, b) => a.id - b.id)
      console.log(this.cards)
    })

  },

  methods: {

    logOut() {
      axios.post('/api/logout')
      .then(response => location.href="/web/index.html")
      .then(response => console.log('signed out!!!'))
    },

  createAccounts() {


    const inputOptions = {

        'SAVING': 'Saving Account',
        'REGULAR': 'Regular Account',

    }
    
    const { value: accountType } = Swal.fire({
      title: 'Select the account type',
      input: 'radio',
      inputOptions: inputOptions,
      inputValidator: (value) => {
        if (!value) {
          return 'You need to choose something!'
        }
      }
    }).then((result) => {
      console.log(result)

      Swal.fire({
        title: 'Wait a second...',
        showConfirmButton: false,
        timerProgressBar: true,
        icon: 'question',
        timer: 2500
      })
      if(result.isConfirmed) {

        axios.post('/api/clients/current/accounts', `type=${result.value}`)
        .then((data) => {console.log(data)})
        .catch(function(error) {
        this.error = error.response.data
        console.log(this.error)
        })
        .then(
          setTimeout(function() {
            if(this.error == "Account type is empty" || this.error == "The client can't have more of 3 accounts" || this.error == "The account has money, you can't eliminate it") {
              Swal.fire({
                title: 'An error has occured',
                text: `${this.error}`,
                icon: 'error'
              })
              this.error = ""
              
            } else {
              Swal.fire({
                title: 'The account has been created successfully',
                icon: 'success',
                showConfirmButton: false
              })
              
              // setTimeout(function(){
              //   location.reload()
              // }, 2500)
            }
          }, 2500)
        )    
      }
    })
  },

  toTransfers() {
    location.href="/web/loan-application.html"
  },

  deleting(account) {
    console.log(account.id)
    Swal.fire({
      title: 'Are you sure?',
      text: "You can't revert this!",
      icon: "warning",
      showCancelButton: true,
      cancelButtonColor: '#d33',
      confirmButtonText: 'Yes'
    }).then((result) => {
      if(result.isConfirmed) {
        Swal.fire({
          title: 'Wait a second...',
          showConfirmButton: false,
          timerProgressBar: true,
          icon: 'question',
          timer: 2500
        })
        axios.patch("/api/clients/current/accounts", `id=${account.id}`)
        .then((data) => {console.log(data)})
        .catch(function(error) {
          this.error = error.response.data
          console.log(this.error)
        })
        .then(
          setTimeout(function() {

            if(this.error == "The account doesn't exist" || this.error == "The account does not belong to the client" || this.error == "The account has money, you can't eliminate it") {
              
              Swal.fire({
                title: 'An error has occurred!',
                text: `${this.error}`,
                icon: 'error'
              })

              this.error == ""

            } else {

              Swal.fire({
                title: 'Success!',
                text: 'The account has been successfully deleted',
                icon: 'success',
                showConfirmButton: false
              })
              setTimeout(function() {
                location.reload()
              }, 4000)

            }
          }, 2500),

        )
      }
    })
  },

  formatDate(date) {

    let newDate = new Date(date)

    return newDate.getDay() + "/" + newDate.getMonth() + "/" + String(newDate.getFullYear()).substr(2,2) + " " + newDate.getHours() + ":" + newDate.getMinutes()
    
  }

  },

  computed: {},
  
}).mount("#app");

$('article.tile').click(function() {
    $('article.tile').animate({width: "600px"}, 200)
})
  