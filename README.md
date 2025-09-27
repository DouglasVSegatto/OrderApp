# SEGATTO Next Steps

## 1. Cart
- Add item to cart per click
    - * Cart doesnt exist until Item is append
      - if cart already exists, append cart item 
      - else create cart and add cart itemn
    - * Cart already contains item
      - append item
    - * Exclude items
      * Exclude the cart item
    - * IF all cart items deleted
      - Cart is deleted
    - * update cart items quantity - Plus or Less
    Total not needed
    * total value should be recalculated in every action
      * NOT SAVED/STATIC
      * ``
    * Create Enum for cart status (created, completed, pending,)
* Add item to cart - From page perspective
* 
- [ ] Create cart
- [ ] Add cart validation
- [ ] Link to User

## 2. CartItem
- [ ] Add/remove items
- [ ] Update quantities
- [ ] Link to Product
- [ ] Calculate subtotals

## 3. Order
- [ ] Convert Cart to Order
- [ ] Order status management
- [ ] Payment integration
- [ ] Order validation

## 4. OrderLineItem
- [ ] Transfer CartItems to OrderLineItems
- [ ] Price freezing (store price at time of order)
- [ ] Order item status
