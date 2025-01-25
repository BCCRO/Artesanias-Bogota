## Webhook

* Link de redirección
``http://127.0.0.1:8081/pruebaPago/pruebaPago.html?collection_id=96718866362&collection_status=approved&payment_id=96718866362&status=approved&external_reference=null&payment_type=account_money&merchant_order_id=26262399173&preference_id=2079614828-d15159b1-a463-4006-9e91-5c8e92df2420&site_id=MCO&processing_mode=aggregator&merchant_account_id=null``

* Payload del webhook
```
{resource=https://api.mercadolibre.com/merchant_orders/26262399173, topic=merchant_order}
{resource=96718866362, topic=payment}
{action=payment.created, api_version=v1, data={id=96718866362}, date_created=2024-12-18T13:33:59Z, id=117889882382, live_mode=true, type=payment, user_id=2079614828}
{resource=https://api.mercadolibre.com/merchant_orders/26262399173, topic=merchant_order}
```