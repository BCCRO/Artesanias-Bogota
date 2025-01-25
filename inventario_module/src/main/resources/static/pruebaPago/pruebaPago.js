console.log('hola mundo')
const mp = new MercadoPago('APP_USR-8cd121fa-5fc8-4570-8acb-f3a7d9430bae');
const bricksBuilder = mp.bricks();


mp.bricks().create("wallet", "wallet_container", {
    initialization: {
        preferenceId: "2079614828-68c72db4-4932-4c0f-91e0-0f9f738822f8",
    },
    customization: {
        texts: {
            valueProp: 'smart_option',
        },
    },
});
