Vue.createApp({
    data() {
      return {

        email: "",
        password: "",

        firstName:"",
        lastName:"",
        email_r: "",
        password_r: "",

      };
    },
  
    created() {

    },
    methods: {

        login() {
            axios.post('/api/login',`email=${this.email_r}&password=${this.password_r}`,{headers:{'content-type':'application/x-www-form-urlencoded'}})
            .then(response => location.href = "/web/accounts.html")
            .then(response => console.log('signed in!!!'))
            .catch(function(error) {
                if (error.response) {
                    console.log(error.response.data);
                    console.log(error.response.status);
                    console.log(error.response.headers);
                  } else if (error.request) {
                    console.log(error.request);
                  } else {
                    console.log('Error', error.message);
                  }
                  console.log(error.config);
            })
          },
          
          signup() {
            axios.post('/api/clients',`firstName=${this.firstName}&lastName=${this.lastName}&email=${this.email_r}&password=${this.password_r}`,{headers:{'content-type':'application/x-www-form-urlencoded'}})
            .then(this.login())
        },

    },

    computed: {

    },
}).mount("#app");
