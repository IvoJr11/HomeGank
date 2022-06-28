Vue.createApp({
    data() {
      return {
        datos: [],
        datosCliente: [],
        nombreCliente: "",
        transactions: [],
        dataAccount: [],
        
      };
    },
  
    created() {

        const urlParams = new URLSearchParams(window.location.search);
        const id = urlParams.get('id');

        axios.get("/api/clients/current").then((data) => {
            console.log(data)
            this.datos = data.data
            console.log(this.datos)
        });
        
        axios.get(`/api/accounts/${id}`).then((data2) => {     

            this.dataAccount = data2.data
            console.log(this.dataAccount)
            this.transactions = this.dataAccount.transactionsDTO.sort((a, b) => a-b).reverse()
            console.log(this.transactions)

        });
    },
        
    methods: {

      formatDate(date) {

        let newDate = new Date(date)
    
        return newDate.getDay() + "/" + newDate.getMonth() + "/" + String(newDate.getFullYear()).substr(2,2) + " " + newDate.getHours() + ":" + newDate.getMinutes()
        
      },

      logOut() {
        axios.post('/api/logout')
        .then(response => location.href="/web/index.html")
        .then(response => console.log('signed out!!!'))
      },

    },
    computed: {

    },
  }).mount("#app");