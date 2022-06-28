Vue.createApp({
  data() {
    return {
      datos: [],
      clientes: [],
      firstname: "",
      lastname: "",
      email: "",
      firstnameEdit: "",
      lastnameEdit: "",
      emailEdit: "",
      urlClient: "",
      clienteSeleccionado: {},
      urlClienteSeleccionado: "",
    };
  },

  created() {
    axios.get("/api/clients")
    .then((data) => {
      this.clientes = data.data;
      console.log(this.clientes);
      this.firstname = document.querySelector("#firstname");
      this.lastname = document.querySelector("#lastname");
      this.email = document.querySelector("#email");

    });
  },

  methods: {

    llenandoTabla() {
      if (
        this.firstname.value != "" &&
        this.lastname.value != "" &&
        this.email.value != ""
      ){
        axios.post("h/rest/clients", {
            firstName: this.firstname.value,
            lastName: this.lastname.value,
            email: this.email.value,
          })
          .then(function (response) {
            console.log(response);
          });
        }

      console.log(this.firstnameEdit)
      console.log(this.lastnameEdit)
      console.log(this.emailEdit)
    },

    deleteClient(url) {
      this.clienteSeleccionado.accounts.forEach(account=> {
        axios.delete('/rest/accounts/' + account.id)
      });
        function eliminando() {
          axios.delete(url)
          location.reload();
        }
        setTimeout(eliminando, 100)
    },

    editarCliente(url) {

      this.firstnameEdit = document.querySelector("#firstName").value
      this.lastnameEdit = document.querySelector("#lastName").value
      this.emailEdit = document.querySelector("#Email").value

      console.log(this.firstnameEdit) 
      console.log(this.lastnameEdit)
      console.log(this.emailEdit)

      // this.client = {
      //   firstName: this.firstnameEdit,
      //   lastName: this.lastnameEdit,
      //   email: this.emailEdit,
      // }
      // axios.patch(url, this.client)
      // .then(location.reload)

      axios.patch(url, {
        firstName: this.firstnameEdit,
        lastName: this.lastnameEdit,
        email: this.emailEdit,
      })
      .then(function (response) {
        console.log(response);
      });

    },

    tomandoUrl(client) {

      this.clienteSeleccionado = client
      console.log(this.clienteSeleccionado)
      this.urlClient = '/rest/clients/' + client.id
      console.log(this.urlClient)

      this.urlClienteSeleccionado = "/rest/clients/" + client.id

    }
  },

  computed: {},
}).mount("#app");
