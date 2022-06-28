const app = Vue.createApp({
    data() {
        return {
            arrayYears: [],
            amount: 0,
            num1: 0,
            num1: 0,
            num1: 0,
            num1: 0,
            cvv: 0,
            completeNumber: "",
            description: "",
            dateFormat: "",
            number1: "",
            number2: "",
            number3: "",
            number4: "",
            dateNow: "",
            month: "",
            year: "",
            name: "",
        };
    },

    created(){

        this.dateNow = new Date();
        this.dateNow = this.dateNow.getFullYear();
        console.log(this.dateNow)

        let i = 0

        while(i<=10) {
            this.arrayYears.push(this.dateNow + i)
            i ++
        }

    },

    mounted() {

    },

    methods: {

        send() {

            Swal.fire({

                title: 'Are you sure?',
                text: 'You will not be able to reverse it',
                icon: 'question',
                showCancelButton: true,
                cancelButtonColor: 'red'

            }).then((response) => {

                if(response.isConfirmed) {

                    Swal.fire({
                        title: 'Wait a second...',
                        showConfirmButton: false,
                        timerProgressBar: true,
                        icon: 'question',
                        timer: 2500
                    })

                    let cardPostnetDTO = {

                        number: this.completeNumber,
                        cvv: this.cvv,
                        amount: this.amount,
                        description: this.description

                    }

                    axios.post("https://app-homegank.herokuapp.com/api/transactions/postnet", cardPostnetDTO, 
                    {
                        headers:{

                            "Access-Control-Allow-Headers" : "Content-Type",
                            "Access-Control-Allow-Origin" : "*",
                            "Access-Control-Allow-Methods": "OPTIONS,POST,GET"
                            
                        }
                    })
                    .then((data) => {console.log(data)})
                    .catch(function(error) {

                        this.error = error.response
                        console.log(this.error)

                    })
                    .then(() => {

                        setTimeout(function() {

                            if(this.error == "The card is expired" || this.error == "No account has sufficient balance" || this.error == "Card number is empty" || this.error == "Description is empty" || this.error == "Cvv can't be 0 or minus" || this.error == "Amount can't be 0 or minus" || this.error == "The card number does not belong a card of client") {
                              
                                Swal.fire({
                                    title: "An error has occurred",
                                    icon: "error",
                                    text: `${this.error}`
                                })

                                this.error = ""

                            } else {

                                Swal.fire({
                                    title: 'Transfer has been complete',
                                    icon: 'success',
                                    showConfirmButton: false
                                })

                                // setTimeout(function(){
                                //     location.reload()
                                // }, 2500)

                            }
                        }, 2500)
                    })
                }
            })

            axios.post()
        },

        limitador() {

            this.num1 = document.getElementById("num1").value;
            this.num2 = document.getElementById("num2").value;
            this.num3 = document.getElementById("num3").value;
            this.num4 = document.getElementById("num4").value;
            
            if((this.number1).toString().length > 4) {
                
                this.number1 = Number((this.number1).toString().substr(1, 4));
                
            }
            if((this.number2).toString().length > 4) {
                
                this.number2 = Number((this.number2).toString().substr(1, 4));
                
            }
            if((this.number3).toString().length > 4) {
                
                this.number3 = Number((this.number3).toString().substr(1, 4));
                
            }
            if((this.number4).toString().length > 4) {
                
                this.number4 = Number((this.number4).toString().substr(1, 4));
                
            }
            
            if(this.number1 == "" ) {
                this.number1 = 0
            }
            if(this.number2 == "" ) {
                this.number2 = 0
            }
            if(this.number3 == "" ) {
                this.number3 = 0
            }
            if(this.number4 == "" ) {
                this.number4 = 0
            }
            
            this.completeNumber = this.number1 + " " + this.number2 + " " + this.number3 + " " + this.number4;

            // this.array.forEach(i => {
                
            //     let variable = "num" + this.array[i - 1]

            //     let aux3 = "number" + this.array[i - 1]
                
            //     var str = String(this);

            //     var o = Object(this);

            //     this.aux2 = aux3

            //     // console.log(this.aux)
            //     console.log(this.aux2)

            //     this.num = document.getElementById(`${this.aux}`).value;



            //     // console.log(this.num)

            //     if((this.num).toString().length > 4) {
                    
            //         this.aux2 = Number((this.aux2).toString().substr(1, 4));
                    
            //     }

            // });
        }

    },

    computed: {

    }

}).mount("#app");