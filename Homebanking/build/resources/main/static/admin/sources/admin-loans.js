const app = Vue.createApp({

    data() {
      return {

        paymentsString: "",
        error: "",
        name: "",
        amount: 0,
        percentage: 0,
        paymentsArray: [],
        paymentsArray2: []

      };
    },
  
    created() {
    },
  
    methods: {

      createLoan() {

        Swal.fire({
          title: "Are you sure?",
          icon: 'question',
          showCancelButton: true
        }).then((response) => {
          
          if(response.isConfirmed) {

            Swal.fire({
              title: 'Wait a second...',
              showConfirmButton: false,
              timerProgressBar: true,
              icon: 'question',
              timer: 2500
            })

            let loan = {

              'name': this.name,
              'maxAmount': this.amount,
              'payments': this.paymentsArray2.sort((a,b) => a - b),
              'percent': this.percentage
    
            }
    
            axios.post("/api/loans/create", loan)
            .then((data) => {console.log(data)})
            .catch(function(error) {
              this.error = error.response.data
              console.log(this.error)
            }).then( () =>{

              setTimeout(function() {

                if(this.error == "Name of loan is empty" || this.error == "Max amount can't be 0 or minus" || this.error == "Payments can't be empty" || this.error == "The percent can't be 0 or minus") {
                  Swal.fire({
                    title: "An error has occurred",
                    icon: "error",
                    text: `${this.error}`
                  })
                  this.error = ""
                } else {
  
                  Swal.fire({
                    title: `${this.name} has been created`,
                    icon: 'success'
                  })
                }
              }, 2500)
            })
          }
        })

        


      },
  
      logOut() {
      axios.post('/api/logout')
      .then(response => location.href="/web/index.html")
      .then(response => console.log('signed out!!!'))
      },
  
    },
  
    computed: {
      
      toArray() {

        this.paymentsArray = this.paymentsString.split(',');

        this.paymentsArray2 = []
          
        this.paymentsArray.forEach( i => {

          this.paymentsArray2.push(Number(i))

        })

      }
      
    },

    
}).mount("#app");
  

    