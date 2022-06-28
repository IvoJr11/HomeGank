const vue = Vue.createApp({
    data() {
      return {
        type: "",
        color: "",
        color_selected: "",
      };
    },
  
    created() {

    },

    mounted() {
      // SE EJECUTA LUEGO DEL CREATED, Y EL COMPUTED ANTES DE ELLO
    },

    methods: {
        create() {
          axios.post('/api/clients/current/cards',`type=${this.type}&color=${this.color}`,{headers:{'content-type':'application/x-www-form-urlencoded'}})
          .then(response => console.log(response))
          .then(response => location.href="/web/cards.html")
        },

        back() {
          location.href="/web/cards.html"
        },

        titanium() {
          this.color_selected = "TITANIUM"
          console.log(this.color_selected)
        },

        gold() {
          this.color_selected = "GOLD"
          console.log(this.color_selected)
        },

        silver() {
          this.color_selected = "SILVER"
          console.log(this.color_selected)
        }
    },
    computed: {
        
    },
  }).mount("#app");