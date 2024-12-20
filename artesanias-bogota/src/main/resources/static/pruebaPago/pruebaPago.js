
console.log('hola mundo')
const mp = new MercadoPago('APP_USR-8cd121fa-5fc8-4570-8acb-f3a7d9430bae');
const bricksBuilder = mp.bricks();


mp.bricks().create("wallet", "wallet_container", {
    initialization: {
        preferenceId: "2079614828-2921f86a-cb24-4ba1-a89b-f9183d3a9063",
    },
 customization: {
  texts: {
   valueProp: 'smart_option',
  },
  },
 });
 