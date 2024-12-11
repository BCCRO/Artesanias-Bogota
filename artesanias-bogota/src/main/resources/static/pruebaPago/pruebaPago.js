
console.log('hola mundo')
const mp = new MercadoPago('APP_USR-8cd121fa-5fc8-4570-8acb-f3a7d9430bae');
const bricksBuilder = mp.bricks();


mp.bricks().create("wallet", "wallet_container", {
    initialization: {
        preferenceId: "2079614828-78fa9137-9296-4502-ae4d-40a572cff09a",
    },
 customization: {
  texts: {
   valueProp: 'smart_option',
  },
  },
 });
 