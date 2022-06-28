const app = Vue.createApp({
    data() {
      return {
        datos: [],
        clientCards: [],
        creditCards: [],
        debitCards: [],
        date: "",
        error: "",
        expireYear: 0,
        expireMonth: 0,
      };
    },
  
    created() {

      axios.get("/api/clients/current").then((data) => {

        console.log(data)
        this.datos = data.data
        console.log(this.datos)

        this.clientCards = this.datos.cards.filter(card => card.active == true).sort((a, b) => a.id - b.id)
        console.log(this.clientCards)

        this.creditCards = this.clientCards.filter(card => card.type == "CREDIT").sort((a, b) => a.id - b.id)
        this.debitCards = this.clientCards.filter(card => card.type == "DEBIT").sort((a, b) => a.id - b.id)

        console.log(this.creditCards)
        console.log(this.debitCards)

        this.date = new Date();
        this.date.setMonth(this.date.getMonth() + 1)
        console.log(this.date)

      });

    },
        
    methods: {

      logOut() {
        axios.post('/api/logout')
        .then(response => location.href="/web/index.html")
        .then(response => console.log('signed out!!!'))
      },

      getDate (dateInput) {
        const date = new Date(dateInput)
        const year = date.getFullYear().toString()
        const yearTwo = year.substr(year.length - 2)
        const formatDate = (date.getMonth() + 1) + "/" + yearTwo
        return formatDate
      },

      add_card() {
        location.href="/web/create-cards.html"
      },

      desactive_card() {
        axios.patch('/api/clients/current/cards')
      },

      moreInfo(card) {
        
        this.expireYear = Number(card.fromDate.substr(0,4))
        this.expireMonth = Number(card.fromDate.substr(5,2))
        
        if(this.expireYear <=  this.date.getFullYear() && this.expireMonth <= this.date.getMonth()) {
          Swal.fire ({
            icon: 'warning',
            title: 'Your card has expired',
            showCancelButton: true,
            cancelButtonText: 'Desactivate',
            cancelButtonColor: 'red'
          }).then((result)=> {
            if(result.isDismissed) {
              Swal.fire({
                title: 'Are you sure?',
                text: "You won't be able to revert this!",
                icon: 'warning',
                showCancelButton: true
              }).then((result_2) => {
                if(result_2.isConfirmed) {
                  Swal.fire({
                    title: 'Wait a second...',
                    showConfirmButton: false,
                    timerProgressBar: true,
                    icon: 'question',
                    timer: 2500
                  })
                  axios.patch("/api/clients/current/cards", `id=${card.id}`)
                  .then((data) => {console.log(data)})
                  .catch(function(error) {
                    this.error = error.response.data
                    console.log(this.error)
                  })
                  .then(
                    setTimeout(function() {

                      if(this.error == "The card doesn't exist" || this.error == "The card does not belong to the client") {
                        Swal.fire({
                          title: 'Has been an error',
                          text: `${this.error}`,
                          icon: "error"
                        })
                        this.error == ""
                      } else {
                        Swal.fire({
                          title: "Success",
                          text: "The card has been complete successfully",
                          icon: "success",
                          showConfirmButton: false
                        })
                      }
                    }, 2500),

                    setTimeout(function() {
                      location.reload()
                    }, 4000)
                  )
                }
              })
            }
          })
        } else {
          Swal.fire ({
            icon: 'success',
            title: `Your card is in order!`,
            showCancelButton: true,
            cancelButtonText: 'Desactivate',
            cancelButtonColor: 'red'
          }).then((result)=> {
            if(result.isDismissed) {
              Swal.fire({
                title: 'Are you sure?',
                text: "You won't be able to revert this!",
                icon: 'warning',
                showCancelButton: true
              }).then((result_2) => {
                if(result_2.isConfirmed) {
                  Swal.fire({
                    title: 'Wait a second...',
                    showConfirmButton: false,
                    timerProgressBar: true,
                    icon: 'question',
                    timer: 2500
                  })
                  axios.patch("/api/clients/current/cards", `id=${card.id}`)
                  .then((data) => {console.log(data)})
                  .catch(function(error) {
                    this.error = error.response.data
                    console.log(this.error)
                  })
                  .then(
                    setTimeout(function() {

                      if(this.error == "The card doesn't exist" || this.error == "The card does not belong to the client") {
                        Swal.fire({
                          title: 'Has been an error',
                          text: `${this.error}`,
                          icon: "error"
                        })
                        this.error == ""
                      } else {
                        Swal.fire({
                          title: "Success",
                          text: "The card has been complete successfully",
                          icon: "success",
                          showConfirmButton: false
                        })
                      }
                    }, 2500),

                    setTimeout(function() {
                      location.reload()
                    }, 4000)
                  )
                }
              })
            }
          })
        }
      }
    },
    computed: {


    },
  }).mount("#app");

$("#alert").click(function() {
  Swal.fire('Any fool can use a computer')
})